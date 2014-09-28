package phr.sns.datamining.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assist {

	public static String cleanWord(String word) {
		word = word.toLowerCase();
		word = word.replaceAll("[^a-zA-Z0-9]", "");

		return word;
	}

	public static String onlyLettersDigitsAndSpaces(String word) {
		word = word.toLowerCase();
		word = word.replaceAll("[^a-zA-Z0-9]", "");

		return word;
	}

	public static String replaceSymbolsWithSpace(String word) {
		word = word.toLowerCase();
		word = word.replaceAll("[^a-zA-Z0-9]", " ");

		return word;
	}

	public static List<String> createNGrams(int n, String message) {
		List<String> ngrams = new ArrayList<String>();
		String[] words = message.split(" ");
		for (int i = 0; i < words.length - n + 1; i++) {
			String ngram = concat(words, i, i + n);
			if (!(ngram.contains(" * ") || ngram.equals("*") || ngram
					.contains(" *")))
				ngrams.add(ngram);
		}
		Collections.sort(ngrams);
		return ngrams;
	}

	public static String concat(String[] words, int start, int end) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; i++)
			sb.append((i > start ? " " : "") + words[i]);
		return sb.toString();
	}
}
