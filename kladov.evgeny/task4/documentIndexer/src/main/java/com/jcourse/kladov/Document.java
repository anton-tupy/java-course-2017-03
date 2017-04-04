package com.jcourse.kladov;

import java.io.InputStream;
import java.util.List;

public interface Document {
	public WordTokenizer getWords();

	public List<ImageDescriptor> getImages();
}
