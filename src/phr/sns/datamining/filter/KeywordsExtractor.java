package phr.sns.datamining.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import phr.exceptions.DataAccessException;
import phr.sns.datamining.daoimpl.HealthCorpusDaoImpl;

public class KeywordsExtractor {

	@Autowired
	HealthCorpusDaoImpl healthCorpusDao;
	List<String> foodCorpus;

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
}
