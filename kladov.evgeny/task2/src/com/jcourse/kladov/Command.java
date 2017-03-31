package com.jcourse.kladov;

import java.io.IOException;
import java.io.StreamTokenizer;

public interface Command {
	void execute(StreamTokenizer st) throws IOException;
}
