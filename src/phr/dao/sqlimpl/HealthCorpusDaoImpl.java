package phr.dao.sqlimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import phr.corpus.models.Activity;
import phr.corpus.models.Food;
import phr.corpus.models.Restaurant;
import phr.corpus.models.SportsEstablishment;
import phr.dao.HealthCorpusDao;

@Repository("healthCorpusDao")
public class HealthCorpusDaoImpl implements HealthCorpusDao {

	@Override
	public List<Food> getFoodWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> getActivityWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Restaurant> getRestaurantNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SportsEstablishment> getSportsEstablishmentNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
