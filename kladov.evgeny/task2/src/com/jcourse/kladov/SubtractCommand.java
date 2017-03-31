package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = CommandArgs.STACK)
public class SubtractCommand implements Command {
	private Stack<Double> stack;

	@Override
	public void execute(StreamTokenizer st) throws IOException {
		if (stack.empty())
			throw new RuntimeException("-: Stack is empty");
		else if (stack.size() == 1) {
			stack.push(-stack.pop());
		} else {
			Double a = stack.pop();
			Double b = stack.pop();
			stack.push(a - b);
		}
	}
}
