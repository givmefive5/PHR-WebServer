package phr.dao.sqlimpl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import phr.dao.WeightDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.web.models.Weight;

@Repository("weightDao")
public class WeightDaoImpl implements WeightDao {

	@Override
	public void add(Weight object) throws DataAccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void edit(Weight object) throws DataAccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Weight object) throws DataAccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public Weight get(Integer entryID) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer getUserID(String userAccessToken)
			throws DataAccessException, EntryNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Weight> getAll(String username) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEntryId(Weight object) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


}
