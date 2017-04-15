package com.jcourse.kladov;


import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.TreeSet;

public class IndexWriter {
	String arg;
	DataProvider dataProvider;

	IndexWriter(DataProvider dataProvider, String arg) {
		this.arg = arg;
		this.dataProvider = dataProvider;
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
		AbstractFile path = dataProvider.getFile(arg);
		AbstractFile parent = path.getParent();
		AbstractFile[] listOfFiles = path.getChildren();

		Set<AbstractFile> files = new TreeSet<>(), folders = new TreeSet<>();

		for (AbstractFile item : listOfFiles) {
			if (item.isFile()) {
				files.add(item);
			} else if (item.isDirectory()) {
				folders.add(item);
			}
		}
		html.printf("<html><meta charset=\"UTF-8\"></meta><h2>Contents of %s</h2><body><br/>\n", arg);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		if (folders.size() > 0 || parent != null) {
			html.println("<table><tr><th>Folder</th><th>Modification time</th></tr>");
			if (parent != null)
				html.printf("<tr align=\"center\"><td><a href=\"%s\">..</a></td><td>%s</td></tr>\n", encodeUrl(parent), sdf.format(parent.getModificationTime()));
			folders.forEach(file -> html.printf("<tr align=\"center\"><td><a href=\"%s\">%s</a></td><td>%s</td></tr>\n", encodeUrl(file), file.getName(), sdf.format(file.getModificationTime())));
			html.println("</table>");
		}

		if (files.size() > 0) {
			html.println("<br/><table><tr><th>File</th><th>Modification time</th><th>Size in bytes</th></tr>");
			files.forEach(file -> html.printf("<tr align=\"center\"><td><a href=\"%s\">%s</a></td><td>%s</td><td>%d</td></tr>\n", encodeUrl(file), file.getName(), sdf.format(file.getModificationTime()), file.getLength()));
			html.println("</table>");
		}

		html.println("</body></html>");
	}

	private String encodeUrl(AbstractFile file) {
		String out = new String();
		try {
			out = URLEncoder.encode(file.getRelativePath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return out.isEmpty() ? "/" : out;
	}
}
