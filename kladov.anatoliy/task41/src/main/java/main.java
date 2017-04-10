import java.io.*;
import java.util.*;

/**
 * Created by Anatoliy on 04.04.2017.
 */

public class main {
	public static void main(String[] args) throws IOException {
		String path = args[0] != null ? args[0] : "text.txt";
		Map<String, WordCounter> container = new HashMap<>();
		processFile(path, container);

		int[] countRefs = new int[1];
		List<WordCounter> list = sort(container, countRefs);
		makeCsv(list, countRefs);

		HibernateWorker worker = new HibernateWorker();
		worker.save(list, countRefs);
		worker.close();
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
			writer.write(String.format("%s, %d, %s\n", c.getWord(), c.getRefCount(), ((float)c.getRefCount())/countRefs[0]*100));
			System.out.printf("'%s', %d, %s\n", c.getWord(), c.getRefCount(), ((float)c.getRefCount())/countRefs[0]*100);
		}
		writer.flush();
		writer.close();
	}
}

