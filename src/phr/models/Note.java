package phr.models;

import java.sql.Timestamp;

public class Note extends TrackerEntry {

	String note;

	public Note(Integer entryID, User user, String facebookID, Timestamp timestamp,
			String status, PHRImage image, String note) {
		super(entryID, user, facebookID, timestamp, status, image);
		this.note = note;
	}

	public Note(User user, String facebookID, Timestamp timestamp, String status,
			PHRImage image, String note) {
		super(user, facebookID, timestamp, status, image);
		this.note = note;
	}

	public Note(Integer entryID, String facebookID, Timestamp timestamp,
			String status, PHRImage image, String note) {
		super(entryID, facebookID, timestamp, status, image);
		this.note = note;
	}

	public Note(Integer entryID, User user, Timestamp timestamp, String status,
			PHRImage image, String note) {
		super(entryID, user, timestamp, status, image);
		this.note = note;
	}

	public Note(String facebookID, Timestamp timestamp, String status,
			PHRImage image, String note) {
		super(facebookID, timestamp, status, image);
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
