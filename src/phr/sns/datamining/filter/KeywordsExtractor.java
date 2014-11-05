package phr.sns.datamining.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import phr.exceptions.DataAccessException;
import phr.sns.datamining.dao.HealthCorpusDao;
import phr.sns.datamining.daoimpl.HealthCorpusDaoImpl;

public class KeywordsExtractor {

	HealthCorpusDao healthCorpusDao = new HealthCorpusDaoImpl();
	List<String> foodCorpus;
	List<String> restaurantCorpus;
	List<String> activityCorpus;
	List<String> sportsEstablishmentCorpus;

	DMFilter filter = new DMFilter();

	public String[] extractFoodNames(String message) throws DataAccessException {
		if (foodCorpus == null)
			foodCorpus = healthCorpusDao.getFoodWords();

		List<String> extractedWords;
		try {
			message = cleanMessage(message, foodCorpus);
			extractedWords = filter.findMatches(message, foodCorpus);
		} catch (InterruptedException e) {
			throw new DataAccessException(
					"An error occured in the while accessing and processing SNS posts",
					e);
		}
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

	public String[] extractRestaurantNames(String message)
			throws DataAccessException {
		if (restaurantCorpus == null)
			restaurantCorpus = healthCorpusDao.getRestaurantNames();

		List<String> extractedWords;
		try {
			message = cleanMessage(message, restaurantCorpus);
			extractedWords = filter.findMatches(message, restaurantCorpus);
		} catch (InterruptedException e) {
			throw new DataAccessException(
					"An error occured in the while accessing and processing SNS posts",
					e);
		}
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

	public String[] extractActivityNames(String message)
			throws DataAccessException {
		if (activityCorpus == null)
			activityCorpus = healthCorpusDao.getActivityWords();

		List<String> extractedWords;
		try {
			message = cleanMessage(message, activityCorpus);
			extractedWords = filter.findMatches(message, activityCorpus);
		} catch (InterruptedException e) {
			throw new DataAccessException(
					"An error occured in the while accessing and processing SNS posts",
					e);
		}
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

	public String[] extractSportsEstablishmentsNames(String message)
			throws DataAccessException {
		if (sportsEstablishmentCorpus == null)
			sportsEstablishmentCorpus = healthCorpusDao
					.getSportsEstablishmentNames();

		List<String> extractedWords;
		try {
			message = cleanMessage(message, sportsEstablishmentCorpus);
			extractedWords = filter.findMatches(message,
					sportsEstablishmentCorpus);
		} catch (InterruptedException e) {
			throw new DataAccessException(
					"An error occured in the while accessing and processing SNS posts",
					e);
		}
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

	private String cleanMessage(String message, List<String> corpus) {
		List<String> singleWordsFromCorpus = new ArrayList<>();
		for (String corpusWord : corpus) {
			String[] words = corpusWord.split(" ");
			for (int i = 0; i < words.length; i++)
				words[i] = Assist.cleanWord(words[i]);
			singleWordsFromCorpus.addAll(Arrays.asList(words));
		}

		// for (String w : singleWordsFromCorpus)
		// System.out.println(w);
		String newMessage = "";
		for (String s : message.split(" ")) {
			if (s.length() > 0 && s.charAt(0) == '#') {
				s = Assist.cleanWord(s);
				if (hasAMatchedWord(s, singleWordsFromCorpus))
					newMessage += s + " ";
				else
					newMessage += "* ";
			} else {
				s = Assist.cleanWord(s);
				if (singleWordsFromCorpus.contains(s))
					newMessage += s + " ";
				else
					newMessage += "*";
			}

		}
		return newMessage;
	}

	private boolean hasAMatchedWord(String s, List<String> singleWordsFromCorpus) {
		for (String word : singleWordsFromCorpus) {
			if (s.contains(word))
				return true;
		}
		return false;
	}
}
