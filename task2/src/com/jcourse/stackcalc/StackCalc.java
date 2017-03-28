package com.jcourse.stackcalc;

import com.jcourse.stackcalc.commands.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anatoliy on 25.03.2017.
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

		for (String line : lines) {
			int spaceIndex = line.indexOf(' ');
			String cmd;
			String arg;
			if (spaceIndex > 0) {
				cmd = line.substring(0, spaceIndex);
				arg = line.substring(spaceIndex + 1);
			}
			else {
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

	public Map<String, Command> getCommands() {
		HashMap<String, Command> result = new HashMap<>();

		PushCommand pushCommand = new PushCommand();
		result.put(pushCommand.getName(), pushCommand);

		PrintCommand printCommand = new PrintCommand();
		result.put(printCommand.getName(), printCommand);

		PopCommand popCommand = new PopCommand();
		result.put(popCommand.getName(), popCommand);

		CommentCommand commentCommand = new CommentCommand();
		result.put(commentCommand.getName(), commentCommand);

		DefineCommand defineCommand = new DefineCommand();
		result.put(defineCommand.getName(), defineCommand);

		SQRTCommand sqrtCommand = new SQRTCommand();
		result.put(sqrtCommand.getName(), sqrtCommand);

		SubtractionCommand subtractionCommand = new SubtractionCommand();
		result.put(subtractionCommand.getName(), subtractionCommand);

		AdditionCommand additionCommand = new AdditionCommand();
		result.put(additionCommand.getName(), additionCommand);

		MultiplicationCommand multiplicationCommand = new MultiplicationCommand();
		result.put(multiplicationCommand.getName(), multiplicationCommand);

		DivisionCommand divisionCommand = new DivisionCommand();
		result.put(divisionCommand.getName(), divisionCommand);


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
