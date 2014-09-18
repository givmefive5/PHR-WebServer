package phr.dao.sqlimpl;

import java.util.ArrayList;

import phr.dao.FacebookPostDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.web.models.FBPost;

public class FacebookPostDaoSqlImpl implements FacebookPostDao {

	@Override
	public void add(FBPost object) throws DataAccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void edit(FBPost object) throws DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(FBPost object) throws DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<FBPost> getAll(String userAccessToken)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEntryId(FBPost object) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FBPost get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
