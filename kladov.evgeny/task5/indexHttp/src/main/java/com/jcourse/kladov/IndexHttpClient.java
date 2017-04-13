package com.jcourse.kladov;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;

public class IndexHttpClient implements Runnable {
	private final Socket socket;
	private final DataProvider dataProvider;
	private InputStream in;
	private OutputStream out;
	private Command cmdType = Command.UNKNOWN;

	public IndexHttpClient(DataProvider dataProvider, Socket socket) throws IOException {
		this.dataProvider = dataProvider;
		this.socket = socket;
		this.in = socket.getInputStream();
		this.out = socket.getOutputStream();
	}

	@Override
	public void run() {
		try {
			handleRequest();
		} catch (IOException e) {
			try {
				sendError(500, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void copyToStream(InputStream in, OutputStream out) throws IOException {
		int count;
		byte buf[] = new byte[4096];

		while ((count = in.read(buf)) >= 0){
			out.write(buf, 0, count);
		}
	}

	enum Command {
		GET, HEAD, UNKNOWN
	}

	private void handleRequest() throws IOException {
		//получаем команду и ее аргументы
		String args[] = new BufferedReader(new InputStreamReader(in)).readLine().split(" ");

		if (parseCommandType(args[0].trim().toUpperCase()) == Command.UNKNOWN) {
			sendError(501, "Not implemented");
			return;
		}

		final String decodedArg = URLDecoder.decode(args[1], "UTF-8");
		final AbstractFile absolutePath = dataProvider.getFile(decodedArg);

		if (absolutePath.isExists()) {
			if (absolutePath.isFile()) {
				sendFile(absolutePath);
			} else if (absolutePath.isDirectory()) {
				final AbstractFile indexHtml = absolutePath.getChild("index.html");

				if (indexHtml.isExists())
					sendFile(indexHtml);
				else
					sendIndex(decodedArg);
			}
		} else {
			sendError(404, "Not found");
			return;
		}
	}

	private Command parseCommandType(String cmd) {
		if (cmd.equals("GET")) {
			cmdType = Command.GET;
		} else if (cmd.equals("HEAD")) {
			cmdType = Command.HEAD;
		} else
			return Command.UNKNOWN;
		return cmdType;
	}

	private void sendError(int errorCode, String errMessage) throws IOException {
		out.write(("HTTP/1.0 " + String.valueOf(errorCode) + "\r\n").getBytes());
		out.write("Content-Type: none\r\n".getBytes());
		out.write(("Content-Length: " + errMessage.length() + "\r\n").getBytes());
		out.write("\r\n".getBytes());
		out.write(errMessage.getBytes());
		out.flush();
	}

	private void sendIndex(String file) throws IOException {
		IndexWriter indexWriter = new IndexWriter(dataProvider, file);
		ByteArrayOutputStream htmlOut = new ByteArrayOutputStream();
		indexWriter.printIndex(new PrintStream(htmlOut));
		//пишем статус ответа
		out.write("HTTP/1.0 200 OK\r\n".getBytes());
		//минимально необходимые заголовки, тип и длина
		out.write("Content-Type: text/html\r\n".getBytes());
		out.write(("Content-Length: " + htmlOut.size() + "\r\n").getBytes());
		//тело
		if (cmdType == Command.GET) {
			//пустая строка отделяет заголовки от тела
			out.write("\r\n".getBytes());
			out.write(htmlOut.toByteArray());
		}
		out.flush();
	}

	private void sendFile(AbstractFile file) throws IOException {
		//пишем статус ответа
		out.write("HTTP/1.0 200 OK\r\n".getBytes());
		//минимально необходимые заголовки, тип и длина
		out.write(("Content-Type: " + file.getContentType() + "\r\n").getBytes());
		out.write(("Content-Length: " + file.getLength() + "\r\n").getBytes());
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MM,dd HH:mm:ss yyyy");
		out.write(("Last-Modified: " + sdf.format(file.getModificationTime()) + "\r\n").getBytes());
		//тело
		if (cmdType == Command.GET) {
			//пустая строка отделяет заголовки от тела
			out.write("\r\n".getBytes());
			copyToStream(file.getContent(), out);
		}
		out.flush();
	}
}
