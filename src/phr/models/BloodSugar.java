package phr.models;

import java.sql.Timestamp;

public class BloodSugar extends TrackerEntry {

	double bloodSugar;
	String type;
	
	public BloodSugar(Integer entryID, User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			double bloodSugar, String type) {
		super(entryID, user, facebookID, timestamp, status, image);
		this.bloodSugar = bloodSugar;
		this.type = type;
	}

	public BloodSugar(User user, String facebookID, Timestamp timestamp,
			String status, PHRImage image, double bloodSugar, String type) {
		super(user, facebookID, timestamp, status, image);
		this.bloodSugar = bloodSugar;
		this.type = type;
	}

	public BloodSugar(Integer entryID, String facebookID, Timestamp timestamp,
			String status, PHRImage image, double bloodSugar, String type) {
		super(entryID, facebookID, timestamp, status, image);
		this.bloodSugar = bloodSugar;
		this.type = type;
	}

	public BloodSugar(Integer entryID, User user, Timestamp timestamp,
			String status, PHRImage image, double bloodSugar, String type) {
		super(entryID, user, timestamp, status, image);
		this.bloodSugar = bloodSugar;
		this.type = type;
	}

	public BloodSugar(String facebookID, Timestamp timestamp, String status,
			PHRImage image, double bloodSugar, String type) {
		super(facebookID, timestamp, status, image);
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
