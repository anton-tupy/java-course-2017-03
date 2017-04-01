package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = CommandArgs.STACK)
public class LogCommand implements Command {
	private Stack<Double> stack;

	@Override
	public void parseArguments(StreamTokenizer st) throws IOException {
	}

	@Override
	public void execute() {
		if (stack.empty())
			throw new RuntimeException("LOG: Stack is empty");
		else {
			Double a = stack.pop();
			stack.push(java.lang.Math.log(a));
		}
	}
}
