package phr.sns.datamining.dao;

import java.util.List;

public interface HealthCorpusDao {

	List<String> getFoodWords();

	List<String> getActivityWords();

	List<String> getRestaurantNames();

	List<String> getSportsEstablishmentNames();
}
