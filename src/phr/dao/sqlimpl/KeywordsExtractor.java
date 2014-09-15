package phr.dao.sqlimpl;

import java.util.ArrayList;

public class KeywordsExtractor {

	public static boolean hasFoodNames(String message) {
		return extractFoodNames(message).length > 0;
	}

	public static boolean hasRestaurantNames(String message) {
		return extractRestaurantNames(message).length > 0;
	}

	public static boolean hasActivityNames(String message) {
		return extractActivityNames(message).length > 0;
	}

	public static boolean hasSportsEstablishmentsNames(String message) {
		return extractSportsEstablishmentsNames(message).length > 0;
	}

	public static String[] extractFoodNames(String message) {
		ArrayList<String> extractedWords = new ArrayList<>();
		if (message.contains("tocino"))
			extractedWords.add("tocino");
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

	public static String[] extractRestaurantNames(String message) {
		ArrayList<String> extractedWords = new ArrayList<>();
		if (message.contains("Jollibee"))
			extractedWords.add("Jollibee");
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

	public static String[] extractActivityNames(String message) {
		ArrayList<String> extractedWords = new ArrayList<>();
		if (message.contains("Swimming"))
			extractedWords.add("Swimming");
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

	public static String[] extractSportsEstablishmentsNames(String message) {
		ArrayList<String> extractedWords = new ArrayList<>();
		if (message.contains("Fitness First"))
			extractedWords.add("Fitness First");
		return extractedWords.toArray(new String[extractedWords.size()]);
	}

}
