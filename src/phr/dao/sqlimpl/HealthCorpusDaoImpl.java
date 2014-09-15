package phr.dao.sqlimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import phr.dao.HealthCorpusDao;

@Repository("healthCorpusDao")
public class HealthCorpusDaoImpl implements HealthCorpusDao {

	@Override
	public List<String> getFoodWords() {
		// TODO Auto-generated method stub
		return null;
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
