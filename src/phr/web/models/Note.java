package phr.web.models;

import java.sql.Timestamp;

public class Note extends TrackerEntry {

	String note;

	public Note(Integer entryID, User user, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, String note) {
		super(entryID, user, fbPost, timestamp, status, image);
		this.note = note;
	}

	public Note(User user, FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, String note) {
		super(user, fbPost, timestamp, status, image);
		this.note = note;
	}

	public Note(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, String note) {
		super(entryID, fbPost, timestamp, status, image);
		this.note = note;
	}

	public Note(Integer entryID, User user, Timestamp timestamp, String status,
			PHRImage image, String note) {
		super(entryID, user, timestamp, status, image);
		this.note = note;
	}

	public Note(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, String note) {
		super(fbPost, timestamp, status, image);
		this.note = note;
	}

	public Note(Timestamp timestamp, String status, PHRImage image,
			 String note) {
		super(timestamp, status, image);
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
