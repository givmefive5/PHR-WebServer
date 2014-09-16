package phr.dao.sqlimpl;

import java.util.ArrayList;

import phr.web.models.Note;
import phr.dao.NotesDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;

public class NotesDaoSqlImpl implements NotesDao {

	@Override
	public void add(Note object) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(Note object) throws DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Note object) throws DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Note get(Integer entryID) throws DataAccessException {
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
	public ArrayList<Note> getAll(String userAccessToken)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getEntryId(Note object) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
