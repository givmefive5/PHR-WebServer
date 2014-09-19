package phr.sns.datamining.filter;

import java.util.HashSet;
import java.util.List;

public class NGramFilteringThread extends Thread {

	private List<String> corpus;
	private String message;
	private HashSet<String> foundWords;
	private int nStart;
	private int nEnd;

	public NGramFilteringThread(List<String> corpus2, String message,
			int nStart, int nEnd) {
		foundWords = new HashSet<>();
		this.corpus = corpus2;
		this.message = message;
		this.nStart = nStart;
		this.nEnd = nEnd;
	}

	@Override
	public void run() {
		String[] words = message.split(" ");
		List<String> ngramList;
		for (int n = nEnd; n >= nStart; n--) {
			ngramList = Assist.createNGrams(n, message);
			for (String corpusWord : corpus)
				for (String ngram : ngramList)
					if (Assist.onlyLettersDigitsAndSpaces(corpusWord).equals(
							Assist.onlyLettersDigitsAndSpaces(ngram))) {
						foundWords.add(corpusWord);
						message = message.replace(ngram, "*");
						message = message.replaceAll("[*][ *][ *]*", "* ");

						System.out.println("T-- Found " + corpusWord
								+ " In Text Remaining : " + message);
					}
		}
	}

	public HashSet<String> getFoundWords() {
		return foundWords;
	}
}
