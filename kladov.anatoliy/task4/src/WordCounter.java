/**
 * Created by Anatoliy on 04.04.2017.
 */


public class WordCounter implements Comparable<WordCounter> {
	public WordCounter(String _strWord) {
		strWord = _strWord;
		refCount++;
	}

	public void increaseCount() {
		refCount++;
	}

	public int getRefCount() {
		return refCount;
	}

	public String getWord() {
		return strWord;
	}

	@Override
	public int compareTo(WordCounter o) {
		if (o.refCount == this.refCount)
			return o.strWord.compareTo(this.strWord);
		if (o.refCount < this.refCount)
			return -1;
		return 1;
	}

	private int refCount = 0;
	private String strWord = "";
}
