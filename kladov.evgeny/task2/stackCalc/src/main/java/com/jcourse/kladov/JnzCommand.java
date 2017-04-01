package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = {CommandArgs.STACK, CommandArgs.CONTEXT})
public class JnzCommand implements Command {
	private Stack<Double> stack;
	private Context context;
	private String label;

	@Override
	public void parseArguments(StreamTokenizer st) throws IOException {
		int token = st.nextToken();

		if (token != StreamTokenizer.TT_WORD)
			throw new RuntimeException("JNZ: Identifier expected");

		label = st.sval;
	}

	@Override
	public void execute() {
		if (stack.empty())
			throw new RuntimeException("JNZ: Stack is empty");
		else {
			if (stack.peek() != 0)
				context.gotoLabel(label);
		}
	}
}
