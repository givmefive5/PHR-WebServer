package phr.sns.datamining.dao;

import java.util.List;

import phr.corpus.models.Activity;
import phr.corpus.models.Food;
import phr.corpus.models.Restaurant;
import phr.corpus.models.SportsEstablishment;

public interface HealthCorpusDao {

	List<Food> getFoodWords();

	List<Activity> getActivityWords();

	List<Restaurant> getRestaurantNames();

	List<SportsEstablishment> getSportsEstablishmentNames();
}
