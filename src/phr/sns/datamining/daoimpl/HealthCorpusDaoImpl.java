package phr.sns.datamining.daoimpl;

import java.util.HashSet;

import org.springframework.stereotype.Repository;

import phr.sns.datamining.dao.HealthCorpusDao;

@Repository("healthCorpusDao")
public class HealthCorpusDaoImpl implements HealthCorpusDao {

	@Override
	public HashSet<String> getFoodWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<String> getActivityWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<String> getRestaurantNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<String> getSportsEstablishmentNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
