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
	public void serialize(MetricSerializer s) throws IOException {
		s.serialize(this);
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

			Stats stats = it.next();

			return new Row() {
				@Override
				public Column[] getCols() {
					Column word = new Column() {
						@Override
						public Class type() {
							return String.class;
						}

						@Override
						public String title() {
							return "Word";
						}

						@Override
						public Object value() {
							return stats.word;
						}
					};

					Column freq = new Column() {
						@Override
						public Class type() {
							return Integer.class;
						}

						@Override
						public String title() {
							return "Freq";
						}

						@Override
						public Object value() {
							return stats.counter;
						}
					};

					Column freqPercent = new Column() {
						@Override
						public Class type() {
							return Double.class;
						}

						@Override
						public String title() {
							return "Freq%";
						}

						@Override
						public Object value() {
							return (double)stats.counter/processedWords*100;
						}
					};
					return new Column[] {word, freq, freqPercent};
				}
			};
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
