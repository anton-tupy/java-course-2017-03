package com.jcourse.kladov;

import java.io.IOException;
import java.util.*;

public class WordCounter implements Metric {
	private long processedWords = 0;
	private Map<String, Stats> wordsMap = new HashMap<>();

	@Override
	public void countOn(Document doc) throws IOException {
		WordTokenizer wt = doc.getWords();

		if (wt == null)
			return;

		for (String word = wt.nextWord(); word != null; word = wt.nextWord()) {
			Stats stats = wordsMap.getOrDefault(word, new Stats(word));
			stats.counter += 1;
			processedWords += 1;
			wordsMap.put(word, stats);
		}
	}

	@Override
	public String getName() {
		return "WordFreq";
	}

	@Override
	public Iterator<Row> getIterator() {
		return new IteratorImpl();
	}

	class IteratorImpl implements Iterator<Row> {
		List<Stats> rows = new Vector<>();
		Iterator<Stats> it;

		IteratorImpl() {
			rows.addAll(wordsMap.values());
			Collections.sort(rows);
			it = rows.iterator();
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public Row next() {
			if (!hasNext())
				return null;

			final Stats stats = it.next();

			return new Row( new Column[] {
					new Column(String.class, "Word", stats.word),
					new Column(Integer.class, "Freq", stats.counter),
					new Column(Double.class, "Freq%", (double)stats.counter/processedWords*100)
			});
		}
	}

	private class Stats implements Comparable<Stats> {
		String word;
		int counter;

		Stats(String word) {
			this.counter = 0;
			this.word = word;
		}

		@Override
		public int compareTo(Stats o) {
			return o.counter - counter;
		}
	}
}
