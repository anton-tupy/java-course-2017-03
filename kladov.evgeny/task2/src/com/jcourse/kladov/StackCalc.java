package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

@Log4j
public class StackCalc {

	private BufferedReader reader;
	private PrintStream printStream;
	private Stack<Double> stack = new Stack<>();
	private Context context = new Context();
	private Vector<Command> program = new Vector<>();
	private StackCalcCommandFactory commandFactory = new StackCalcCommandFactory("commands.list");

	public StackCalc(BufferedReader reader, PrintStream printStream) {
		this.reader = reader;
		this.printStream = printStream;
	}

	public static void main(String[] args) {
		printUsage();

		try {
			BufferedReader reader;

			if (args.length > 0)
				reader = new BufferedReader(new FileReader(args[0]));
			else
				reader = new BufferedReader(new InputStreamReader(System.in));

			new StackCalc(reader, System.out).evaluate();
		} catch (FileNotFoundException e) {
			System.err.printf("FileNotFoundException: \n" + e.toString());
		}
	}

	private static void printUsage() {
		System.out.println("Stack calculator. Reads commands from provided file and print result.");
		System.out.println("Use HELP for list of available commands");
	}

	private void parseCommandAnnotations(Object cmdInstance) {
		In in = (In) cmdInstance.getClass().getAnnotation(In.class);

		for (CommandArgs arg : in.args()) {
			switch (arg) {
				case STACK:
					assignField(cmdInstance, "stack", stack);
					break;

				case CONTEXT:
					assignField(cmdInstance, "context", context);
					break;

				case COMMANDS:
					assignField(cmdInstance, "commands", commandFactory.getCommandNames());
					break;

				case PRINT_STREAM:
					assignField(cmdInstance, "printStream", printStream);
					break;

				default:
					printStream.printf("CommandArg %s is not supported yet\n", arg.toString());
			}
		}
	}

	private void assignField(Object obj, String name, Object value) {
		Field field = null;
		List<Field> fields = Arrays.asList(obj.getClass().getDeclaredFields());
		for (Field f : fields) {
			if (f.getName() == name)
				field = f;
		}
		if (field == null) {
			printStream.printf("Field '%s' is not found within class %s\n", name, obj.getClass());
			return;
		}

		field.setAccessible(true);

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			printStream.printf("Cannot assign '%s' variable for class %s\n", name, obj.getClass());
		}
	}

	public void evaluate() {
		try {
			process();
			runProgram();
		} catch (IOException e) {
			printStream.printf("IOException: " + e.toString());
		} catch (RuntimeException e) {
			printStream.println("Runtime exception: " + e.toString());
		}
	}

	private void process() throws IOException {
		StreamTokenizer tokenizer = new StreamTokenizer(reader);
		tokenizer.commentChar('#');

		for (boolean eof = false; !eof; ) {
			Command cmd = null;

			switch (tokenizer.nextToken()) {
				case StreamTokenizer.TT_EOF:
					eof = true;
					break;

				case StreamTokenizer.TT_WORD:
					cmd = commandFactory.getCommandByName(tokenizer.sval);
					break;
			}
			if (eof)
				break;
			if (cmd == null) {
				throw new RuntimeException("Unknown command: " + tokenizer.sval);
			}

			parseCommandAnnotations(cmd);

			cmd.parseArguments(tokenizer);

			program.add(cmd);
		}
	}

	private void runProgram() {
		for (int i = 0, n = program.size(); i < n; ++i) {
			Command cmd = program.get(i);

			if (commandFactory.isDebugMode()) {
				Command finalCmd = cmd;

				Object proxy = Proxy.newProxyInstance(StackCalc.class.getClassLoader(), new Class[]{Command.class},
						new InvocationHandler() {
							@Override
							public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
								dumpInfo("BEFORE");
								Object result = Command.class.getMethod("execute").invoke(finalCmd, args);
								dumpInfo("AFTER");
								return result;
							}

							private void dumpInfo(String label) {
								log.info(String.format("** %s", label));
								log.info(String.format("   CONTEXT: %s", context.toString()));
								final String[] strStack = {new String()};
								stack.forEach(v -> strStack[0] += String.format("%f ", v));
								log.info(String.format("   STACK: %s", strStack[0]));
							}
						}
				);
				((Command) proxy).execute();
			} else
				cmd.execute();
		}
	}
}
