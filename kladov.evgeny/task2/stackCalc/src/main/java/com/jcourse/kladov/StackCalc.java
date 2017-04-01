package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;
import sun.rmi.runtime.Log;

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
	private PrintStream debugStream;
	private Stack<Double> stack = new Stack<>();
	private Context context = new Context();
	private int instructionPointer = 0;
	private Vector<Command> program = new Vector<>();
	private StackCalcCommandFactory commandFactory = new StackCalcCommandFactory("commands.properties");

	public StackCalc(BufferedReader reader, PrintStream printStream, PrintStream debugStream) {
		this.reader = reader;
		this.printStream = printStream;
		this.debugStream = debugStream;
	}

	public static void main(String[] args) {
		printUsage();

		try {
			BufferedReader reader;

			if (args.length > 0)
				reader = new BufferedReader(new FileReader(args[0]));
			else
				reader = new BufferedReader(new InputStreamReader(System.in));

			new StackCalc(reader, System.out, System.out).evaluate();
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException: ", e);
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
			parseProgram();
			runProgram();
		} catch (IOException e) {
			printStream.printf("IOException: " + e.toString());
		} catch (RuntimeException e) {
			printStream.println("Runtime exception: " + e.toString());
		}
	}

	public void start() {
		try {
			parseProgram();
			context.resetInstructionPointer();
		} catch (IOException e) {
			printStream.printf("IOException: " + e.toString());
		} catch (RuntimeException e) {
			printStream.println("Runtime exception: " + e.toString());
		}
	}

	private void parseProgram() throws IOException {
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

			context.incrementInstructionPointer(); // for support goto jumps in forward direction

			program.add(cmd);
		}
	}

	private void runProgram() {
		for (context.resetInstructionPointer(); step(); );
	}

	public boolean step() {
		int ip = context.getInstructionPointer();

		if (ip >= program.size())
			return false;

		Command cmd = program.get(ip);

		if (commandFactory.isDebugMode()) {
			Command finalCmd = cmd;

			Object proxy = Proxy.newProxyInstance(StackCalc.class.getClassLoader(), new Class[]{Command.class},
					new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							ByteArrayOutputStream stream = new ByteArrayOutputStream();
							dumpInfo(new PrintStream(stream));

							if (debugStream != null)
								debugStream.println(stream.toString());

							log.info(stream.toString());

							return Command.class.getMethod("execute").invoke(finalCmd, args);
						}

						private void dumpInfo(PrintStream stream) {
							stream.printf("INSTRUCTION: %s<br>\n", cmd.getClass().toString());
							stream.printf("INSTRUCTION POINTER: %d<br>\n", ip);
							stream.printf("CONTEXT:%s<br>\n", context.toString());
							for (int i = 0, n = stack.size(); i < n; ++i)
								stream.printf("STACK[%d]: %f<br>\n", i, stack.get(i));
						}
					}
			);
			((Command) proxy).execute();
		} else
			cmd.execute();

		context.incrementInstructionPointer();

		return true;
	}
}
