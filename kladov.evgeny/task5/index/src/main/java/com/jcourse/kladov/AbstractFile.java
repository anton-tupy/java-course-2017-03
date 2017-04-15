package com.jcourse.kladov;

import javax.activation.MimetypesFileTypeMap;
import java.io.FileNotFoundException;
import java.io.InputStream;

public abstract class AbstractFile implements Comparable<AbstractFile> {
	protected final String path; // relative

	AbstractFile() {
		this.path = new String ();
	}

	AbstractFile(String path) {
		this.path = path;
	}

	abstract long getLength();

	abstract long getModificationTime();

	abstract boolean isExists();

	abstract boolean isFile();

	abstract boolean isDirectory();

	abstract AbstractFile[] getChildren();

	abstract AbstractFile getParent();

	abstract AbstractFile getChild(String name);

	String getContentType() {
		return new MimetypesFileTypeMap().getContentType(path);
	}

	abstract InputStream getContent();

	abstract String getName();

	String getRelativePath() {
		return path;
	}

	public int compareTo(AbstractFile o) {
		return path.compareTo(o.path);
	}
}
