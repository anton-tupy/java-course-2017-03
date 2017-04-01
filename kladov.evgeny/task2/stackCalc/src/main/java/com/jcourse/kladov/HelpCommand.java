package com.jcourse.kladov;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StreamTokenizer;
import java.util.Set;

@In(args = {CommandArgs.COMMANDS, CommandArgs.PRINT_STREAM})
public class HelpCommand implements Command {
	private PrintStream printStream;
	private Set<String> commands;

	@Override
	public void parseArguments(StreamTokenizer st) throws IOException {
	}

	@Override
	public void execute() {
		printStream.println("List of available commands:");
		commands.forEach(c -> printStream.println(c));
	}
}
