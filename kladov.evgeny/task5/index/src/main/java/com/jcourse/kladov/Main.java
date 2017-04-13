package com.jcourse.kladov;

public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please provide folder path for indexing.");
			return;
		}

		new IndexWriter(new FileSystemDataProvider(args[0]), "").writeIndexFile("index.html");
	}
}
