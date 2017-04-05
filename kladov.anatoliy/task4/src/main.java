import java.io.*;
import java.util.*;

/**
 * Created by Anatoliy on 04.04.2017.
 */
/*
Написать программу, которая будет принимать в качестве аргумента имя текстового файла и выводить CSV файл с колонками:
		1. Слово
		2. Частота
		3. Частота%
		CSV файл должен быть упорядочен по убыванию частоты, то есть самые частые слова должны идти в начале.
		Для чтения использовать: java.io.InputStreamReader, например:
		Reader r = new  InputStreamReader(new BuffredInputStream(new FileInputStream(“имя файла”)));
		Использовать StringBuilder класс для построения слова. Разделителями считать все символы кроме букв и цифр
		(использовать метод Character.isLetterOrDigit). Для хранения в памяти можно использовать Map, который должен
		будет хранить объекты специального класса (например WordCounter).
		Для сортировки (после заполнения) можно создать List и скопировать в него map.values. Класс WordCounter должен
		реализовать интерфейс Comporable.
	*/
public class main {
	public static void main(String[] args) throws IOException {
		String path = args[0];
		Map<String, WordCounter> container = new HashMap<>();
		processFile(path, container);

		int[] countRefs = new int[1];
		List<WordCounter> list = sort(container, countRefs);
		makeCsv(list, countRefs);
	}

	public static void processFile(String path, Map<String, WordCounter> container) throws IOException {
		InputStreamReader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(path)));
		int res = -1;
		StringBuilder builder = new StringBuilder();
		do {
			res = reader.read();
			if (Character.isLetterOrDigit(res)) {
				builder.append(Character.toChars(res));
			} else {
				String strKey = builder.toString();
				if (strKey.isEmpty())
					continue;
				builder.delete(0, builder.length());
				if (container.containsKey(strKey)) {
					container.get(strKey).increaseCount();
				} else {
					container.put(strKey, new WordCounter(strKey));
				}
			}
		}
		while(res != -1);
	}

	public static List<WordCounter> sort(Map<String, WordCounter> container, int[] countRefs) {
		List<WordCounter> list = new LinkedList<>();

		Collection<WordCounter> collection = container.values();
		Iterator<WordCounter> it = collection.iterator();
		while (it.hasNext()) {
			WordCounter w = it.next();
			list.add(w);
			countRefs[0] += w.getRefCount();
		}

		Collections.sort(list);

		return list;
	}

	public static void makeCsv(List<WordCounter> list, int[] countRefs) throws IOException {
		String strFile = "res.csv";
		File f = new File(strFile);
		if(f.exists() && !f.isDirectory()) {
			f.delete();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		for (WordCounter c : list) {
			writer.write(String.format("%s, %d, %f\n", c.getWord(), c.getRefCount(), ((float)c.getRefCount())/countRefs[0]));
			System.out.printf("'%s', %d, %f\n", c.getWord(), c.getRefCount(), ((float)c.getRefCount())/countRefs[0]);
		}
		writer.flush();
		writer.close();
	}
}

