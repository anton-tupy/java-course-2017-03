package com.jcourse.kladov;

import java.io.PrintStream;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = {CommandArgs.PRINT_STREAM, CommandArgs.STACK})
public class PrintCommand implements Command {
	private Stack<Double> stack;
	private PrintStream printStream;

	@Override
	public void execute(StreamTokenizer st) {
		if (stack.empty())
			throw new RuntimeException("PRINT: stack is empty");
		printStream.println(stack.peek());
	}
}
