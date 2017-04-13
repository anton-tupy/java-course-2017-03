package com.jcourse.kladov;

import java.util.List;

public interface DataProvider {
	AbstractFile getFile(String path);

	String getSeparator();
}
