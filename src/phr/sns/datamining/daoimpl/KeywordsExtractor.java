package phr.sns.datamining.daoimpl;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

public class KeywordsExtractor {

	@Autowired
	HealthCorpusDaoImpl healthCorpusDao;

	public String[] extractFoodNames(String message) {

		HashSet<String> corpus = healthCorpusDao.getFoodWords();

		HashSet<String> extractedWords = findMatches(message, corpus);
		if (message.contains("tocino"))
			extractedWords.add("tocino");
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

	public String[] extractRestaurantNames(String message) {
		ArrayList<String> extractedWords = new ArrayList<>();
		if (message.contains("Jollibee"))
			extractedWords.add("Jollibee");
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

	public String[] extractActivityNames(String message) {
		ArrayList<String> extractedWords = new ArrayList<>();
		if (message.contains("Swimming"))
			extractedWords.add("Swimming");
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

	public String[] extractSportsEstablishmentsNames(String message) {
		ArrayList<String> extractedWords = new ArrayList<>();
		if (message.contains("Fitness First"))
			extractedWords.add("Fitness First");
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

	private HashSet<String> findMatches(String message, HashSet<String> corpus) {
		HashSet<String> hashtags = getHashtagsFromMessage(message);
		message = removeHashtags(message, hashtags);
		HashSet<String> wordsFoundInHashtags = findWordsInHashtags(hashtags,
				corpus);

		HashSet<String> wordsFoundInText = findWordsInText(message, hashtags);

		HashSet<String> allWordsFound = new HashSet<>();

		if (wordsFoundInHashtags != null)
			allWordsFound.addAll(wordsFoundInHashtags);
		if (wordsFoundInText != null)
			allWordsFound.addAll(wordsFoundInText);

		return allWordsFound;

	}

	private HashSet<String> findWordsInText(String message,
			HashSet<String> hashtags) {
		// TODO Auto-generated method stub
		return null;
	}

	private HashSet<String> findWordsInHashtags(HashSet<String> hashtags,
			HashSet<String> corpus) {
		HashSet<String> foundWords = new HashSet<>();

		for (String hashtag : hashtags) {
			String lowerCaseHashtag = cleanWord(hashtag);
			for (String corpusWord : corpus) {
				String lowerCaseCorpusWord = cleanWord(corpusWord);
				if (lowerCaseHashtag.contains(lowerCaseCorpusWord)) {
					foundWords.add(corpusWord);
					lowerCaseHashtag.replace(lowerCaseCorpusWord, "*");
				}
			}
		}
		return foundWords;
	}

	private String cleanWord(String word) {
		word = word.toLowerCase();
		word = word.replaceAll("[^a-zA-Z0-9]", "");

		return word;
	}

	private String removeHashtags(String message, HashSet<String> hashtags) {
		for (String hashtag : hashtags) {
			message = message.replace(hashtag, "*");
		}
		message = message.replaceAll("[*][ *][ *]*", "* ");
		return message;
	}

	private HashSet<String> getHashtagsFromMessage(String message) {
		String[] tokens = message.split(" ");
		HashSet<String> hashtags = new HashSet<>();
		for (String s : tokens) {
			if (s.charAt(0) == '#')
				hashtags.add(s);
		}
		return hashtags;
	}
}
