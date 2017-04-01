package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = {CommandArgs.CONTEXT, CommandArgs.STACK})
public class PushCommand implements Command {
	private double value = 0;
	private String varName = new String();
	private Stack<Double> stack;
	private Context context;

	@Override
	public void execute() {
		if (varName.isEmpty()) {
			stack.push(value);
			return;
		}

		Double val = context.getVar(varName);
		if (val == null)
			throw new RuntimeException("PUSH: Variable not found " + varName);
		stack.push(val);
	}

	@Override
	public void parseArguments(StreamTokenizer st) throws IOException {
		int token = st.nextToken();

		if (token == StreamTokenizer.TT_WORD) {
			varName = st.sval;
		} else if (token == StreamTokenizer.TT_NUMBER) {
			value = st.nval;
		} else {
			throw new RuntimeException("PUSH: Number expected");
		}
	}
}
