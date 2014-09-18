package phr.sns.datamining.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class KeywordsExtractor {

	@Autowired
	HealthCorpusDaoImpl healthCorpusDao;

	public boolean hasFoodNames(String message) {
		return extractFoodNames(message).length > 0;
	}

	public boolean hasRestaurantNames(String message) {
		return extractRestaurantNames(message).length > 0;
	}

	public boolean hasActivityNames(String message) {
		return extractActivityNames(message).length > 0;
	}

	public boolean hasSportsEstablishmentsNames(String message) {
		return extractSportsEstablishmentsNames(message).length > 0;
	}

	public String[] extractFoodNames(String message) {

		List<String> corpus = healthCorpusDao.getFoodWords();

		ArrayList<String> extractedWords = findMatches(message, corpus);
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

	private ArrayList<String> findMatches(String message, List<String> corpus) {
		// TODO Auto-generated method stub
		return null;
	}
}
