package com.jcourse.kladov;


import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class IndexWriter {
	private String root;
	private String arg;

	IndexWriter(String root, String arg) {
		this.arg = arg;
		this.root = root;
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
		File path = new File(root + arg);
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

		if (folders.size() > 0 || parent != null) {
			html.println("<table><tr><th>Folder</th><th>Modification time</th></tr>");
			if (parent != null)
				html.printf("<tr align=\"center\"><td><a href=\"%s\">..</a></td><td>%s</td></tr>\n", encodeUrl(parent), sdf.format(parent.lastModified()));
			folders.forEach(file -> html.printf("<tr align=\"center\"><td><a href=\"%s\">%s</a></td><td>%s</td></tr>\n", encodeUrl(file), file.getName(), sdf.format(file.lastModified())));
			html.println("</table>");
		}

		if (files.size() > 0) {
			html.println("<br/><table><tr><th>File</th><th>Modification time</th><th>Size in bytes</th></tr>");
			files.forEach(file -> html.printf("<tr align=\"center\"><td><a href=\"%s\">%s</a></td><td>%s</td><td>%d</td></tr>\n", encodeUrl(file), file.getName(), sdf.format(file.lastModified()), file.length()));
			html.println("</table>");
		}

		html.println("</body></html>");
	}

	private String encodeUrl(File file) {
		String out = new String();
		try {
			Path absolute = Paths.get(file.getAbsolutePath());
			Path base = Paths.get(root);
			out = URLEncoder.encode(base.relativize(absolute).toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return out.isEmpty() ? "/" : out;
	}
}
