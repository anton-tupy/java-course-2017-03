package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

@In(args = {CommandArgs.STACK, CommandArgs.CONTEXT})
public class DefineCommand implements Command {
	private Stack stack;
	private Context context;

	@Override
	public void execute() {
	}

	@Override
	public void parseArguments(StreamTokenizer st) throws IOException {
		int token = st.nextToken();

		if (token != StreamTokenizer.TT_WORD)
			throw new RuntimeException("DEFINE: Identifier expected");

		String varName = st.sval;
		token = st.nextToken();

		if (token != StreamTokenizer.TT_NUMBER)
			throw new RuntimeException("DEFINE: Number expected");

		context.setVar(varName, st.nval);
	}
}
