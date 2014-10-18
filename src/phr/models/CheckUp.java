package phr.models;

import java.sql.Timestamp;

public class CheckUp extends TrackerEntry {

	String purpose;
	String doctorsName;
	String notes;
	
	public CheckUp(Integer entryID, User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image, String purpose,
			String doctorsName, String notes) {
		super(entryID, user, facebookID, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.notes = notes;
	}

	public CheckUp(User user, String facebookID, Timestamp timestamp,
			String status, PHRImage image, String purpose, String doctorsName,
			String notes) {
		super(user, facebookID, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.notes = notes;
	}

	public CheckUp(Integer entryID, String facebookID, Timestamp timestamp,
			String status, PHRImage image, String purpose, String doctorsName,
			String notes) {
		super(entryID, facebookID, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.notes = notes;
	}

	public CheckUp(Integer entryID, User user, Timestamp timestamp,
			String status, PHRImage image, String purpose, String doctorsName,
			String notes) {
		super(entryID, user, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.notes = notes;
	}

	public CheckUp(String facebookID, Timestamp timestamp, String status,
			PHRImage image, String purpose, String doctorsName,
			String notes) {
		super(facebookID, timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
		this.notes = notes;
	}

	public CheckUp(Timestamp timestamp, String status, PHRImage image,
			String purpose, String doctorsName, String notes) {
		super(timestamp, status, image);
		this.purpose = purpose;
		this.doctorsName = doctorsName;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
