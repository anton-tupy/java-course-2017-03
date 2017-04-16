import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by Anatoliy on 09.04.2017.
 */

	/*Методы класса должны выбрасывать соответствующие исключения (по имени метода).
		Для первых 5-и методов нельзя использовать директиву throw. Надо написать такой код,
		который будет сам генерировать исключение. В последнем методе использовать throw
		для выбрасывания собственного исключения MyException, который содержит message
		, заданный при вызове метода. Класс MyException	написать самим.
		Добавить метод main(), в котором тестируются все методы класса. Каждый вызов
		оборачивается try/catch, и stack trace исключения выводится на экран с помощью функции
		printStackTrace().

		Работа с файлами. Написать генератор index.html	файла. То есть для заданной директории создать
		HTML файл, который содержит кликабельный список всех директорий и файлов внутри заданной
		директории. При этом:
		•В начале идёт директория .. которая указывает на верх дерева файловой системы.
		•Затем идёт список директорий отсортированный по именам
		•Потом список файлов отсортированный по именам
		•Кроме того показывать информацию о дате модификации и размере (для файлов)
		*/
public class Main {
	public static void main(String[] args) throws IOException {
		MyExceptionGenerator g = new MyExceptionGenerator();
		try {
			g.generateNullPointerException();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		try {
			g.generateClassCastException();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}

		try {
			g.generateNumberFormatException();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			g.generateStackOverflowError();
		} catch (StackOverflowError e) {
			e.printStackTrace();
		}

		try {
			g.generateOutOfMemoryError();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}

		try {
			g.generateMyException("my exception generated");
		} catch (MyException e) {
			e.printStackTrace();
		}


		try (
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File("Index.html")));
		) {
			writer.write(generateHTML("C:\\src\\java-course-2017-03\\kladov.anatoliy"));
		} catch (IOException e) {
			e.printStackTrace();
		}


		ServerSocket serverSocket = new ServerSocket(8080);
		while(true){
			out.println("Waiting for clients");
			Socket socket = serverSocket.accept();
			new Thread(new Runnable() {
				@Override
				public void run() {
					out.println("Client accepted");
					try (InputStream inputStream = socket.getInputStream();
						 Reader reader = new InputStreamReader(inputStream);
						 BufferedReader bufferedReader = new BufferedReader(reader)) {
//читаем первую строку запроса, игнорируем все заголовки которые идут дальше первой строки
						StringBuilder sb = new StringBuilder();
						String line = bufferedReader.readLine();
						String decodedLine = URLDecoder.decode(line, "UTF-8");
						sb.append(decodedLine);
//получаем команду и ее аргументы
						String data = sb.toString();
						String args[] = data.split(" ");
						String cmd = args[1].trim().toUpperCase();
						if (cmd.length() == 1)
							cmd = URLEncoder.encode("C:/SRC", "UTF-8");
// пишем ответ  Hello world
						try {
							String decoded = URLDecoder.decode(cmd, "UTF-8");
							if (decoded.startsWith("/"))
								decoded = decoded.substring(1);
							Path dir = new File(decoded).toPath();
							if (Files.isDirectory(dir)) {
								String reply = generateHTML(decoded);
								socket.getOutputStream().write("HTTP/1.0 200 OK\r\n".getBytes());
								socket.getOutputStream().write("Content-Type: text/html\r\n".getBytes());
								socket.getOutputStream().write(("Content-Length: "+reply.toString().length()+"\r\n").getBytes());
								socket.getOutputStream().write("\r\n".getBytes());
								socket.getOutputStream().write(reply.toString().getBytes());

							} else {
								sendFile(dir.toFile(), socket.getOutputStream());

							}
							socket.getOutputStream().flush();

						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
					out.println("Done");
				}
			}, "client thread").start();
		}
	}

	static String generateHTML(String strPath) throws IOException {
		final Path currentPath = Paths.get(strPath);
		final DirectoryStream<Path> dirIterator = Files.newDirectoryStream(currentPath);
		StringBuilder sb = new StringBuilder();
		sb.append("<html><title>test</title><body><br>\n");
		List<Path> dirs = new LinkedList<>();
		List<Path> files = new LinkedList<>();
		for(Path it: dirIterator) {
			if (it.toFile().isDirectory())
				dirs.add(it);
			else
				files.add(it);
		}
		Collections.sort(dirs);
		Collections.sort(files);

		if (currentPath.getRoot() != null && !currentPath.getRoot().toString().equals(currentPath.toString()))
			sb.append(String.format("dir: <a href=\"%s\">..</a><br>\n", URLEncoder.encode(currentPath.getRoot().toString(), "UTF-8")));
		for (Path dirItem: dirs) {
			File file = dirItem.toFile();
			sb.append(String.format("dir: <a href=\"%s\">%s</a> modified %s<br>\n", URLEncoder.encode(file.getAbsolutePath(), "UTF-8"), file.getName(), new Date(file.lastModified())));
		}
		for (Path file: files) {
			File f = file.toFile();
			sb.append(String.format("file: <a href=\"%s\">%s</a> modified %s, size %s<br>\n", URLEncoder.encode(f.getAbsolutePath(), "UTF-8"), f.getName(), new Date(f.lastModified()), readableFileSize(f.length())));
		}
		sb.append("</body></html>");
		return sb.toString();
	}

	public static String readableFileSize(long size) {
		if(size <= 0) return "0";
		final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
		int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

	private static void sendFile(File file, OutputStream stream) throws IOException {
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		String mimeType = mimeTypesMap.getContentType(file);
		stream.write("HTTP/1.0 200 OK\r\n".getBytes());
		stream.write(("Content-Type: " + mimeType + "\r\n").getBytes());
		stream.write(("Content-Length: " + file.length() + "\r\n").getBytes());

		//пустая строка отделяет заголовки от тела
		stream.write("\r\n".getBytes());

		try (InputStream inputStream = new FileInputStream(file);
			 BufferedInputStream in = new BufferedInputStream(inputStream);
		) {
			byte buf[] = new byte[4096];
			int count;
			while ((count = in.read(buf)) >= 0) {
				stream.write(buf, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
