package phr.sns.datamining.filter;

import java.util.ArrayList;
import java.util.List;

public class DMFilter {

	public List<String> findMatches(String message, List<String> corpus)
			throws InterruptedException {
		List<String> hashtags = getHashtagsFromMessage(message);
		message = removeHashtags(message);
		System.out.println("After removing hashtags: " + message);
		long startTime = System.currentTimeMillis();
		List<String> wordsFoundInHashtags = findWordsInHashtags(hashtags,
				corpus);
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Hashtag " + elapsedTime);

		startTime = System.currentTimeMillis();
		List<String> wordsFoundInText = findWordsInText(message, corpus);
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println("Text " + elapsedTime);

		List<String> allWordsFound = new ArrayList<>();

		if (wordsFoundInHashtags != null)
			allWordsFound.addAll(wordsFoundInHashtags);
		if (wordsFoundInText != null)
			allWordsFound.addAll(wordsFoundInText);

		allWordsFound = removeSubwordsFromWordsFound(allWordsFound);
		return allWordsFound;

	}

	private List<String> findWordsInText(String message, List<String> corpus)
			throws InterruptedException {
		List<String> foundWords = new ArrayList<>();
		int n = message.split(" ").length;

		int quotient = n / 5;
		List<NGramFilteringThread> threads = new ArrayList<>();
		for (int i = 1; i <= n; i += (quotient + 1)) {
			int end = i + quotient;
			if (end > n)
				end = n;
			threads.add(new NGramFilteringThread(corpus, message, i, end));
		}
		for (NGramFilteringThread t : threads) {
			t.start();
		}
		for (NGramFilteringThread t : threads) {
			t.join();
		}
		for (NGramFilteringThread t : threads) {
			foundWords.addAll(t.getFoundWords());
		}

		return foundWords;
	}

	private List<String> removeSubwordsFromWordsFound(List<String> allWordsFound) {
		List<String> newList = new ArrayList<>();
		for (String s : allWordsFound) {
			boolean toAdd = true;
			for (String s1 : allWordsFound) {
				String stemp = Assist.cleanWord(s);
				String s1temp = Assist.cleanWord(s1);
				if (s1temp.contains(stemp) && !s1temp.equals(stemp))
					toAdd = false;
			}
			if (toAdd == true)
				newList.add(s);
		}
		return newList;
	}

	private List<String> findWordsInHashtags(List<String> hashtags,
			List<String> corpus2) {
		List<String> foundWords = new ArrayList<>();

		for (String hashtag : hashtags) {
			String lowerCaseHashtag = Assist.cleanWord(hashtag);
			for (String corpusWord : corpus2) {
				String lowerCaseCorpusWord = Assist.cleanWord(corpusWord);
				if (lowerCaseHashtag.contains(lowerCaseCorpusWord)) {
					foundWords.add(corpusWord);
					lowerCaseHashtag = lowerCaseHashtag.replace(
							lowerCaseCorpusWord, "*");
					System.out.println("H--Found " + corpusWord
							+ " Remaining text in hashtag : "
							+ lowerCaseHashtag);
					if (lowerCaseHashtag.equals("*")) {
						break;
					}
				}
			}
		}
		return foundWords;
	}

	private String removeHashtags(String message) {
		String[] words = message.split(" ");
		String newMessage = "";
		for (int i = 0; i < words.length; i++) {
			String s = words[i];
			if (s.charAt(0) == '#')
				s = "*";

			if (i == words.length - 1)
				newMessage += s;
			else
				newMessage += s + " ";
		}

		newMessage = newMessage.replaceAll("[*][ *][ *]*", "* ");
		return newMessage;
	}

	private List<String> getHashtagsFromMessage(String message) {
		String[] tokens = message.split(" ");
		List<String> hashtags = new ArrayList<>();
		for (String s : tokens) {
			if (s.charAt(0) == '#')
				hashtags.add(s);
		}
		return hashtags;
	}
}
