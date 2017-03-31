package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = {CommandArgs.CONTEXT, CommandArgs.STACK})
public class PushCommand implements Command {
	private Stack<Double> stack;
	private Context context;

	@Override
	public void execute(StreamTokenizer st) throws IOException {
		int token = st.nextToken();

		if (token == StreamTokenizer.TT_WORD) {
			Double val = context.getVar(st.sval);
			if (val == null)
				throw new RuntimeException("PUSH: Variable not found " + st.sval);
			stack.push(val);
		} else if (token == StreamTokenizer.TT_NUMBER) {
			stack.push(st.nval);
		} else {
			throw new RuntimeException("PUSH: Number expected");
		}
	}
}
