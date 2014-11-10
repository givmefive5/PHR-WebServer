package phr.sns.datamining.filter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class NGramFilteringThread extends Thread {

	private final HashMap<String, String> corpus;
	private String message;
	private final HashSet<String> foundWords;
	private final int nStart;
	private final int nEnd;

	public NGramFilteringThread(HashMap<String, String> corpus, String message,
			int nStart, int nEnd) {
		foundWords = new HashSet<>();
		this.corpus = corpus;
		this.message = message;
		this.nStart = nStart;
		this.nEnd = nEnd;
	}

	@Override
	public void run() {
		List<String> ngramList;
		for (int n = nEnd; n >= nStart; n--) {
			ngramList = Assist.createNGrams(n, message);
			for (String ngram : ngramList) {
				String cleaned = Assist.onlyLettersDigitsAndSpaces(ngram);
				if (corpus.get(cleaned) != null) {
					System.out.println(ngram);
					foundWords.add(corpus.get(cleaned));
					message = message.replace(ngram, "*");
					message = message.replaceAll("[*][ *][ *]*", "* ");
				}
			}
		}
	}

	public HashSet<String> getFoundWords() {
		return foundWords;
	}
}
