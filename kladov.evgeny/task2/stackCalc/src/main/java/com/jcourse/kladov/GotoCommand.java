package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;

@In(args = CommandArgs.CONTEXT)
public class GotoCommand implements Command {
	private Context context;
	private String label;

	@Override
	public void parseArguments(StreamTokenizer st) throws IOException {
		int token = st.nextToken();

		if (token != StreamTokenizer.TT_WORD)
			throw new RuntimeException("GOTO: Identifier expected");

		label = st.sval;
	}

	@Override
	public void execute() {
		context.gotoLabel(label);
	}
}
