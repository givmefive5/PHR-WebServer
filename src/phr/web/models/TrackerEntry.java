package phr.web.models;

import java.sql.Timestamp;
import java.sql.Timestamp;

public abstract class TrackerEntry {

	Integer entryID;
	User user;
	FBPost fbPost;
	Timestamp timestamp;
	String status;
	String imageFilePath;

	public TrackerEntry(Integer entryID, User user, FBPost fbPost,
			Timestamp timestamp, String status, String imageFilePath) {
		super();
		this.entryID = entryID;
		this.user = user;
		this.fbPost = fbPost;
		this.timestamp = timestamp;
		this.status = status;
		this.imageFilePath = imageFilePath;
	}

	public TrackerEntry(User user, FBPost fbPost, Timestamp timestamp,
			String status, String imageFilePath) {
		super();
		this.user = user;
		this.fbPost = fbPost;
		this.timestamp = timestamp;
		this.status = status;
		this.imageFilePath = imageFilePath;
	}

	public TrackerEntry(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, String imageFilePath) {
		super();
		this.entryID = entryID;
		this.fbPost = fbPost;
		this.timestamp = timestamp;
		this.status = status;
		this.imageFilePath = imageFilePath;
	}

	public TrackerEntry(Integer entryID, User user, Timestamp timestamp,
			String status, String imageFilePath) {
		super();
		this.entryID = entryID;
		this.user = user;
		this.timestamp = timestamp;
		this.status = status;
		this.imageFilePath = imageFilePath;
	}

	public TrackerEntry(FBPost fbPost, Timestamp timestamp, String status,
			String imageFilePath) {
		super();
		this.fbPost = fbPost;
		this.timestamp = timestamp;
		this.status = status;
		this.imageFilePath = imageFilePath;
	}

	public TrackerEntry(Timestamp timestamp, String status, String imageFilePath) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.imageFilePath = imageFilePath;
	}

	public Integer getEntryID() {
		return entryID;
	}

	public void setEntryID(Integer entryID) {
		this.entryID = entryID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FBPost getFbPost() {
		return fbPost;
	}

	public void setFbPost(FBPost fbPost) {
		this.fbPost = fbPost;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public int getUserID() {
		return user.getId();
	}
}
