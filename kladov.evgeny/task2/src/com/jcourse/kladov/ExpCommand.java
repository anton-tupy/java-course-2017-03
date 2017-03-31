package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = CommandArgs.STACK)
public class ExpCommand implements Command {
	private Stack<Double> stack;

	@Override
	public void execute(StreamTokenizer st) throws IOException {
		if (stack.empty())
			throw new RuntimeException("EXP: Stack is empty");
		else {
			Double a = stack.pop();
			stack.push(java.lang.Math.exp(a));
		}
	}
}
