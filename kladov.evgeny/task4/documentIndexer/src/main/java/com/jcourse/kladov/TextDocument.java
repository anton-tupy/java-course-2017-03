package com.jcourse.kladov;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TextDocument implements Document {
	private InputStream stream;

	public TextDocument(String content) { this.stream = new ByteArrayInputStream(content.getBytes()); }

	public TextDocument(InputStream stream) {
		this.stream = stream;
	}

	public WordTokenizer getWords() {
		return new WordTokenizer(stream);
	}
}
