package phr.mobile.models;

import java.sql.Date;

public abstract class MobileTrackerEntry {
	
	Integer entryID;
	Integer fbPostID;
	Date dateAdded;
	String status;
	String encodedImage;
	
	public MobileTrackerEntry(Integer entryID, Integer fbPostID,
			Date dateAdded, String status, String encodedImage) {
		super();
		this.entryID = entryID;
		this.fbPostID = fbPostID;
		this.dateAdded = dateAdded;
		this.status = status;
		this.encodedImage = encodedImage;
	}
	
	public MobileTrackerEntry(Integer fbPostID, Date dateAdded, String status,
			String encodedImage) {
		super();
		this.fbPostID = fbPostID;
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
	public Integer getFbPostID() {
		return fbPostID;
	}
	public void setFbPostID(Integer fbPostID) {
		this.fbPostID = fbPostID;
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
