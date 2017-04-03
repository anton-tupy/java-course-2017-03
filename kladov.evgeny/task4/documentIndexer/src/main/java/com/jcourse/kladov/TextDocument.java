package com.jcourse.kladov;

import java.io.InputStream;

public class TextDocument implements Document {
	private InputStream stream;

	public TextDocument(InputStream stream) {
		this.stream = stream;
	}

	public WordTokenizer getWords() {
		return new WordTokenizer(stream);
	}
}
