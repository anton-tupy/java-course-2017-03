package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = CommandArgs.STACK)
public class PopCommand implements Command {
	private Stack<Double> stack;

	@Override
	public void execute(StreamTokenizer st) throws IOException {
		if (stack.empty())
			throw new RuntimeException("POP: Stack is empty");
		stack.pop();
	}
}
