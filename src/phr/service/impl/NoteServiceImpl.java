package phr.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.NoteDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;
import phr.service.NoteService;
import phr.web.models.Note;
import phr.web.models.User;

@Service("noteService")
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	NoteDao noteDao;

	@Autowired
	UserDao userDao;

	@Override
	public int addReturnEntryID(String accessToken, Note note) throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			note.setUser(new User(userID));
			return noteDao.addReturnsEntryID(note);
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
			noteDao.edit(note);
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
			noteDao.delete(note);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while deleting a note entry", e);
		}
	}

	@Override
	public ArrayList<Note> getAll(String accessToken) throws ServiceException {
		
		ArrayList<Note> notes = new ArrayList<Note>();
		
		try{
			notes = noteDao.getAll(accessToken);
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
				return noteDao.getEntryId(note);
			} catch (DataAccessException e) {
				throw new ServiceException(
						"Error has occurred while getting the entry id of a note entry",
						e);
			}
	}
}
