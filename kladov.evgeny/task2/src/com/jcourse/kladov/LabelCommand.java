package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;

@In(args = {CommandArgs.CONTEXT})
public class LabelCommand implements Command {
	private Context context;

	@Override
	public void parseArguments(StreamTokenizer st) throws IOException {
		int token = st.nextToken();

		if (token != StreamTokenizer.TT_WORD)
			throw new RuntimeException("LABEL: Identifier expected");

		context.setLabel(st.sval);
	}

	@Override
	public void execute() {

	}
}
