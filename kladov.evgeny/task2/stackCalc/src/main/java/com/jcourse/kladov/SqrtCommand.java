package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = CommandArgs.STACK)
public class SqrtCommand implements Command {
	private Stack<Double> stack;

	@Override
	public void parseArguments(StreamTokenizer st) throws IOException {
	}

	@Override
	public void execute() {
		if (stack.empty())
			throw new RuntimeException("SQRT: Stack is empty");

		Double val = stack.pop();
		if (val < 0)
			throw new RuntimeException("SQRT: Negative argument");

		stack.push(Math.sqrt(val));
	}
}
