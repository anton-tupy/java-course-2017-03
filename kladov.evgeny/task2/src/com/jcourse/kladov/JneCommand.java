package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = {CommandArgs.STACK, CommandArgs.CONTEXT})
public class JneCommand implements Command {
	private Stack<Double> stack;
	private Context context;
	private String label;

	@Override
	public void parseArguments(StreamTokenizer st) throws IOException {
		int token = st.nextToken();

		if (token != StreamTokenizer.TT_WORD)
			throw new RuntimeException("JNE: Identifier expected");

		label = st.sval;
	}

	@Override
	public void execute() {
		if (stack.empty())
			throw new RuntimeException("JNE: Stack is empty");
		else if (stack.size() < 2)
			throw new RuntimeException("JNE: Stack has only one element");
		else {
			double a = stack.pop();
			double b = stack.pop();

			if (a != b)
				context.gotoLabel(label);

			stack.push(b);
			stack.push(a);
		}
	}
}
