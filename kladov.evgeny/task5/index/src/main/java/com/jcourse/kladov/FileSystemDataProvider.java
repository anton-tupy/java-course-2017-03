package com.jcourse.kladov;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemDataProvider implements DataProvider {
	private String root;

	@Override
	public AbstractFile getFile(String path) {
		return new FileImpl(path);
	}

	@Override
	public String getSeparator() {
		return File.separator;
	}

	class FileImpl extends AbstractFile {
		private File file;

		public FileImpl() {

		}

		public FileImpl(String path) {
			super(path);
			this.file = new File(root + getSeparator() + path);
		}

		@Override
		long getLength() {
			return file.length();
		}

		@Override
		long getModificationTime() {
			return file.lastModified();
		}

		@Override
		boolean isExists() {
			return file.exists();
		}

		@Override
		boolean isFile() {
			return file.isFile();
		}

		@Override
		boolean isDirectory() {
			return file.isDirectory();
		}

		@Override
		AbstractFile[] getChildren() {
			File [] files = file.listFiles();
			FileImpl [] impls = new FileImpl[files.length];
			for (int i = 0, n = files.length; i < n; ++i)
				impls[i] = new FileImpl(relativize(files[i].toString()));
			return impls;
		}

		@Override
		AbstractFile getParent() {
			return new FileImpl(relativize(file.getParentFile().toString()));
		}

		@Override
		AbstractFile getChild(String name) {
			return new FileImpl(path + getSeparator() + name);
		}

		@Override
		InputStream getContent() {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				System.out.println("File not found " + file);
			}
			return null;
		}

		@Override
		String getName() {
			return file.getName();
		}

		private String relativize(String path) {
			final Path absolute = Paths.get(path);
			final Path base = Paths.get(root);
			return base.relativize(absolute).toString();
		}
	}

	FileSystemDataProvider(String root) {
		this.root = root;
	}

}
