package phr.sns.datamining.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import phr.sns.datamining.dao.HealthCorpusDao;

@Repository("healthCorpusDao")
public class HealthCorpusDaoImpl implements HealthCorpusDao {

	@Override
	public List<String> getFoodWords() {
		List<String> list = new ArrayList<>();
		list.add("Mcdonald's");
		list.add("Bacon and Pancakes");
		list.add("Eggs");
		list.add("Bacon");
		list.add("Pancakes");
		return list;
	}

	@Override
	public List<String> getActivityWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRestaurantNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getSportsEstablishmentNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
