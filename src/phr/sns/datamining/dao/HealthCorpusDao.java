package phr.sns.datamining.dao;

import java.util.HashSet;

public interface HealthCorpusDao {

	HashSet<String> getFoodWords();

	HashSet<String> getActivityWords();

	HashSet<String> getRestaurantNames();

	HashSet<String> getSportsEstablishmentNames();
}
