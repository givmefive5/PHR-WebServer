package phr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.NoteTrackerDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.service.NoteTrackerService;
import phr.web.models.Note;
import phr.web.models.User;

@Service("noteService")
public class NoteTrackerServiceImpl implements NoteTrackerService {
	
	@Autowired
	NoteTrackerDao noteTrackerDao;

	@Autowired
	UserDao userDao;

	@Override
	public int addReturnEntryID(String accessToken, Note note) throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			note.setUser(new User(userID));
			return noteTrackerDao.addReturnsEntryID(note);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a note entry", e);
		}
	}

	@Override
	public void edit(String accessToken, Note note) throws ServiceException, EntryNotFoundException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			note.setUser(new User(userID));
			noteTrackerDao.edit(note);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while editng a note entry", e);
		}

	}

	@Override
	public void delete(String accessToken, Note note) throws ServiceException, EntryNotFoundException {
		
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			note.setUser(new User(userID));
			noteTrackerDao.delete(note);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a note entry", e);
		}
	}

	@Override
	public List<Note> getAll(String accessToken) throws ServiceException {
		
		List<Note> notes = new ArrayList<Note>();
		
		try{
			notes = noteTrackerDao.getAll(accessToken);
		}catch(DataAccessException e){
			e.printStackTrace();
			throw new ServiceException(
					"Error has occured while getting all entries in note tracker", e);
		}
		
		return notes;
	}

	@Override
	public Integer getEntryId(Note note) throws ServiceException {
		if (note.getUser() != null)
			return note.getEntryID();
		else
			try {
				return noteTrackerDao.getEntryId(note);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while getting the entry id of a note entry",
						e);
			}
	}
}
