package phr.models;

import java.sql.Timestamp;

public class BloodSugar extends TrackerEntry {

	double bloodSugar;
	String type;
	
	public BloodSugar(Integer entryID, User user, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image,
			double bloodSugar, String type) {
		super(entryID, user, fbPost, timestamp, status, image);
		this.bloodSugar = bloodSugar;
		this.type = type;
	}

	public BloodSugar(User user, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, double bloodSugar, String type) {
		super(user, fbPost, timestamp, status, image);
		this.bloodSugar = bloodSugar;
		this.type = type;
	}

	public BloodSugar(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, double bloodSugar, String type) {
		super(entryID, fbPost, timestamp, status, image);
		this.bloodSugar = bloodSugar;
		this.type = type;
	}

	public BloodSugar(Integer entryID, User user, Timestamp timestamp,
			String status, PHRImage image, double bloodSugar, String type) {
		super(entryID, user, timestamp, status, image);
		this.bloodSugar = bloodSugar;
		this.type = type;
	}

	public BloodSugar(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, double bloodSugar, String type) {
		super(fbPost, timestamp, status, image);
		this.bloodSugar = bloodSugar;
		this.type = type;
	}

	public BloodSugar(Timestamp timestamp, String status, PHRImage image,
			double bloodSugar, String type) {
		super(timestamp, status, image);
		this.bloodSugar = bloodSugar;
		this.type = type;
	}

	public double getBloodSugar() {
		return bloodSugar;
	}

	public void setBloodSugar(double bloodSugar) {
		this.bloodSugar = bloodSugar;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
