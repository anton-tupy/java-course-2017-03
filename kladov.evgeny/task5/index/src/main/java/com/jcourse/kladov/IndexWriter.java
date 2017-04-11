package com.jcourse.kladov;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class IndexWriter {
	private String arg;

	IndexWriter(String arg) {
		this.arg = arg;
	}

	public void writeIndexFile(String fileName) {
		try {
			PrintStream html = new PrintStream(new FileOutputStream(fileName));
			printIndex(html);
			html.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void printIndex(PrintStream html) {
		File path = new File(arg);
		File parent = path.getParentFile();
		File[] listOfFiles = path.listFiles();

		Set<File> files = new TreeSet<>(), folders = new TreeSet<>();

		for (File item : listOfFiles) {
			if (item.isFile()) {
				files.add(item);
			} else if (item.isDirectory()) {
				folders.add(item);
			}
		}
		html.printf("<html><h2>Contents of %s</h2><body><br/>\n", arg);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		html.println("<table><tr><th>Folder</th><th>Modification time</th></tr>");
		if (parent != null)
			html.printf("<tr align=\"center\"><td><a href=\"%s\">..</a></td><td>%s</td></tr>\n", parent.toString(), sdf.format(parent.lastModified()));
		folders.forEach(file -> html.printf("<tr align=\"center\"><td><a href=\"%s\">%s</a></td><td>%s</td></tr>\n", file.toString(), file.getName(), sdf.format(file.lastModified())));

		html.println("</table><br/><table><tr><th>File</th><th>Modification time</th><th>Size in bytes</th></tr>");
		files.forEach(file -> html.printf("<tr align=\"center\"><td><a href=\"file:///%s\">%s</a></td><td>%s</td><td>%d</td></tr>\n", file.toString(), file.getName(), sdf.format(file.lastModified()), file.length()));

		html.println("</table></body></html>");
	}
}
