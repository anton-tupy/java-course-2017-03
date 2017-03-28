package com.jcourse.stackcalc;

import com.jcourse.stackcalc.commands.*;
import com.sun.org.apache.xpath.internal.operations.Plus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Разбор файла
 */
public class StackCalc {

	private String path;

	public StackCalc(String path) {
		this.path = path;
	}

	public void execute() throws IOException {
        Map<String, Double> environment = new HashMap<>();
        Stack stack = new Stack();
        Map<String, Command> commands = getCommands();

        List<String> lines = readLines();

        for (final String line : lines) { //разбор из файла
            if (line.toCharArray()[0] == '#') { continue;}            //Комментарии
            int spaceIndex = line.indexOf(' ');
            String cmd;
            String arg;

            if (spaceIndex > 0) {
                cmd = line.substring(0, spaceIndex);
                String parts[] = line.split(" ");
                if (parts.length > 2) { // define command
                    arg = parts[0];
                    String variable = parts[1];
                    String varValue = parts[2];
                    environment.put(variable, Double.valueOf(varValue));
                } else {
                    arg = line.substring(spaceIndex + 1);
                }
            } else {
                cmd = line;
                arg = "";
            }
            Command command = commands.get(cmd);
            if (command == null) {
                throw new RuntimeException("Command " + cmd + " not found");
            }
            command.execute(arg, stack, environment);
        }
    }



	private Map<String, Command> getCommands() {
		HashMap<String, Command> result = new HashMap<>();

		PushCommand pushCommand = new PushCommand();
		result.put(pushCommand.getName(), pushCommand);

		PopCommand popCommand = new PopCommand();
		result.put(popCommand.getName(), popCommand);

		PrintCommand printCommand = new PrintCommand();
		result.put(printCommand.getName(), printCommand);

		DefineCommand defineCommand = new DefineCommand ();
		result.put(defineCommand.getName(), defineCommand);

		PlusCommand plusCommand = new PlusCommand ();
		result.put(plusCommand.getName(), plusCommand);

		MinusCommand minusCommand = new MinusCommand ();
		result.put(minusCommand.getName(), minusCommand);

		DivCommand divCommand = new DivCommand ();
		result.put(divCommand.getName(), divCommand);

		SqrtCommand sqrtCommand = new SqrtCommand ();
		result.put(sqrtCommand.getName(), sqrtCommand);

        MultCommand multCommand = new MultCommand ();
        result.put(multCommand.getName(), multCommand);


        return result;
	}

	private List<String> readLines() throws IOException {
		ArrayList<String> result = new ArrayList<>();

		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		do {
			line = reader.readLine();
			if (line != null) {
				result.add(line.trim());
			}
		}
		while(line != null);

		return result;
	}
}
