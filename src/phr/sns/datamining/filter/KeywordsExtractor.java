package phr.sns.datamining.filter;

import java.util.ArrayList;
import java.util.List;

import phr.exceptions.DataAccessException;
import phr.sns.datamining.dao.HealthCorpusDao;
import phr.sns.datamining.daoimpl.HealthCorpusDaoImpl;

public class KeywordsExtractor {

	HealthCorpusDao healthCorpusDao = new HealthCorpusDaoImpl();
	List<String> foodCorpus;
	List<String> restaurantCorpus;

	DMFilter filter = new DMFilter();

	public String[] extractFoodNames(String message) throws DataAccessException {
		if (foodCorpus == null)
			foodCorpus = healthCorpusDao.getFoodWords();

		List<String> extractedWords;
		try {
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
			extractedWords = filter.findMatches(message, restaurantCorpus);
		} catch (InterruptedException e) {
			throw new DataAccessException(
					"An error occured in the while accessing and processing SNS posts",
					e);
		}
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
}
