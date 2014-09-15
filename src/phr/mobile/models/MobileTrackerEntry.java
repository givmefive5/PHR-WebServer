package phr.mobile.models;

import java.sql.Date;

import phr.web.models.FBPost;

public abstract class MobileTrackerEntry {

	Integer entryID;
	FBPost fbPost;
	Date dateAdded;
	String status;
	String encodedImage;

	public MobileTrackerEntry(Integer entryID, FBPost fbPost, Date dateAdded,
			String status, String encodedImage) {
		super();
		this.entryID = entryID;
		this.fbPost = fbPost;
		this.dateAdded = dateAdded;
		this.status = status;
		this.encodedImage = encodedImage;
	}

	public MobileTrackerEntry(FBPost fbPost, Date dateAdded, String status,
			String encodedImage) {
		super();
		this.fbPost = fbPost;
		this.dateAdded = dateAdded;
		this.status = status;
		this.encodedImage = encodedImage;
	}

	public MobileTrackerEntry(Integer entryID, Date dateAdded, String status,
			String encodedImage) {
		super();
		this.entryID = entryID;
		this.dateAdded = dateAdded;
		this.status = status;
		this.encodedImage = encodedImage;
	}

	public MobileTrackerEntry(Date dateAdded, String status, String encodedImage) {
		super();
		this.dateAdded = dateAdded;
		this.status = status;
		this.encodedImage = encodedImage;
	}

	public Integer getEntryID() {
		return entryID;
	}

	public void setEntryID(Integer entryID) {
		this.entryID = entryID;
	}

	public FBPost getFbPost() {
		return fbPost;
	}

	public void setFbPost(FBPost fbPost) {
		this.fbPost = fbPost;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEncodedImage() {
		return encodedImage;
	}

	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}
}
