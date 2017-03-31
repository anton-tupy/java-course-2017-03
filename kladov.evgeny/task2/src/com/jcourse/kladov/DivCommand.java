package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = {CommandArgs.STACK})
public class DivCommand implements Command {
	private Stack<Double> stack;

	@Override
	public void execute(StreamTokenizer st) throws IOException {
		if (stack.empty())
			throw new RuntimeException("/: Stack is empty");
		else if (stack.size() < 2)
			throw new RuntimeException("/: Stack has only one element");
		else {
			Double a = stack.pop();
			Double b = stack.pop();

			if (b.equals(0.))
				throw new RuntimeException("/: Second arg is 0");

			stack.push(a / b);
		}
	}
}
