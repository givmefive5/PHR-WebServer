package phr.dao.sqlimpl;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import phr.dao.BloodSugarDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.web.models.BloodSugar;

@Repository("bloodSugar")
public class BloodSugarDaoImpl implements BloodSugarDao {

	@Override
	public void add(BloodSugar object) throws DataAccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void edit(BloodSugar object) throws DataAccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BloodSugar object) throws DataAccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public BloodSugar get(Integer entryID) throws DataAccessException {
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
	public ArrayList<BloodSugar> getAll(String username)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEntryId(BloodSugar object) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


}
