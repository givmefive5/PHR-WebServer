package phr.web.models;

import java.sql.Timestamp;

public class CheckUp extends TrackerEntry {

	String purpose;
	String doctorsName;
	String location;
	String notes;
	
	public CheckUp(Integer entryID, User user, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image, String purpose,
			String doctorsName, String location, String notes) {
		super(entryID, user, fbPost, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.location = location;
		this.notes = notes;
	}

	public CheckUp(User user, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, String purpose, String doctorsName,
			String location, String notes) {
		super(user, fbPost, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.location = location;
		this.notes = notes;
	}

	public CheckUp(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, String purpose, String doctorsName,
			String location, String notes) {
		super(entryID, fbPost, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.location = location;
		this.notes = notes;
	}

	public CheckUp(Integer entryID, User user, Timestamp timestamp,
			String status, PHRImage image, String purpose, String doctorsName,
			String location, String notes) {
		super(entryID, user, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.location = location;
		this.notes = notes;
	}

	public CheckUp(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, String purpose, String doctorsName,
			String location, String notes) {
		super(fbPost, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.location = location;
		this.notes = notes;
	}

	public CheckUp(Timestamp timestamp, String status, PHRImage image,
			String purpose, String doctorsName, String location, String notes) {
		super(timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.location = location;
		this.notes = notes;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDoctorsName() {
		return doctorsName;
	}

	public void setDoctorsName(String doctorsName) {
		this.doctorsName = doctorsName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
