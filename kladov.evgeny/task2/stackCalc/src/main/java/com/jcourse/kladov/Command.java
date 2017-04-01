package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;

public interface Command {
	void parseArguments(StreamTokenizer st) throws IOException;

	void execute();
}
