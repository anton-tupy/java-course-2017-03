package com.jcourse.kladov;

import java.io.*;

public class WordTokenizer {
	private InputStream stream;
	private Reader reader;

	public WordTokenizer(InputStream stream) {
		this.stream = stream;
		this.reader = new InputStreamReader(new BufferedInputStream(this.stream));
	}

	public String nextWord() throws IOException {
		StringBuilder stringBuilder = new StringBuilder();

		boolean wordIsFound = false;
		for (int c = reader.read(); c != -1; c = reader.read()) {
			if (!Character.isLetterOrDigit(c)) {
				if (wordIsFound)
					break;
				continue;
			}
			stringBuilder.appendCodePoint(c);
			wordIsFound = true;
		}

		return wordIsFound ? stringBuilder.toString() : null;
	}
}
