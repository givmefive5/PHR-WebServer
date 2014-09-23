package phr.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import phr.dao.NoteDao;
import phr.dao.UserDao;
import phr.exceptions.DataAccessException;
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
	public void add(String accessToken, Note note) throws ServiceException {
		try {
			int userID = userDao.getUserIDGivenAccessToken(accessToken);
			note.setUser(new User(userID));
			noteDao.add(note);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Error has occurred while adding a note entry", e);
		}
	}

	@Override
	public void edit(String accessToken, Note object) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String accessToken, Note object) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Note> getAll(String accessToken) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
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
						"Error has occurred while adding a note entry",
						e);
			}
	}
}
