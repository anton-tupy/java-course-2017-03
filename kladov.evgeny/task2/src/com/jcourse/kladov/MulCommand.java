package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = CommandArgs.STACK)
public class MulCommand implements Command {
	private Stack<Double> stack;

	@Override
	public void parseArguments(StreamTokenizer st) throws IOException {
	}

	@Override
	public void execute() {
		if (stack.empty())
			throw new RuntimeException("*: Stack is empty");
		else if (stack.size() < 2)
			throw new RuntimeException("*: Stack has only one element");
		else {
			Double a = stack.pop();
			Double b = stack.pop();
			stack.push(a * b);
		}
	}
}
