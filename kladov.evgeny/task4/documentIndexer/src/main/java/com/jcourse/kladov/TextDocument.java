package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@Log4j
public class TextDocument implements Document {
	private String fileName;

	public TextDocument(String fileName) {
		this.fileName = fileName;
	}

	public WordTokenizer getWords() {
		try {
			return new WordTokenizer(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			log.warn("File not found " + fileName);
		}
		return null;
	}

	@Override
	public List<ImageDescriptor> getImages() {
		return null;
	}
}
