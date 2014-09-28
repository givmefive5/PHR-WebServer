package phr.sns.datamining.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DMFilter {

	public List<String> findMatches(String message, List<String> corpus)
			throws InterruptedException {
		LengthComparator comparator = new LengthComparator();
		Collections.sort(corpus, comparator);

		List<String> hashtags = getHashtagsFromMessage(message);
		message = removeHashtags(message);
		List<String> wordsFoundInHashtags = findWordsInHashtags(hashtags,
				corpus);

		List<String> wordsFoundInText = findWordsInText(message, corpus);
		List<String> allWordsFound = new ArrayList<>();

		if (wordsFoundInHashtags != null)
			allWordsFound.addAll(wordsFoundInHashtags);
		if (wordsFoundInText != null)
			allWordsFound.addAll(wordsFoundInText);

		allWordsFound = removeSubwordsFromWordsFound(allWordsFound);
		allWordsFound = removeDuplicateEntries(allWordsFound);
		return allWordsFound;

	}

	private List<String> removeDuplicateEntries(List<String> list) {
		Set<String> s = new LinkedHashSet<String>(list);
		list.clear();
		list.addAll(s);
		return list;
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
				if ((s1temp.contains(stemp) && !s1temp.equals(stemp)))
					toAdd = false;
				if (equivalentWordExistsInList(newList, s))
					toAdd = false;
			}
			if (toAdd == true)
				newList.add(s);
		}
		return newList;
	}

	private boolean equivalentWordExistsInList(List<String> list,
			String wordToMatch) {
		for (String s : list) {
			if (s.toLowerCase().equals(wordToMatch.toLowerCase()))
				return true;
			String sTemp = Assist.replaceSymbolsWithSpace(s);
			String wordToMatchTemp = Assist
					.replaceSymbolsWithSpace(wordToMatch);
			if (sTemp.equals(wordToMatchTemp))
				return true;
		}

		return false;
	}

	private List<String> findWordsInHashtags(List<String> hashtags,
			List<String> corpus) {
		List<String> foundWords = new ArrayList<>();

		for (String hashtag : hashtags) {
			String lowerCaseHashtag = Assist.cleanWord(hashtag);
			for (String corpusWord : corpus) {
				String lowerCaseCorpusWord = Assist.cleanWord(corpusWord);
				if (lowerCaseHashtag.contains(lowerCaseCorpusWord)) {
					foundWords.add(corpusWord);
					lowerCaseHashtag = lowerCaseHashtag.replace(
							lowerCaseCorpusWord, "*");
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
