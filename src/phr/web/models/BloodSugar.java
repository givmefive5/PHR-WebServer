package phr.web.models;

import java.sql.Timestamp;

public class BloodSugar extends TrackerEntry {

	double bloodSugar;

	public BloodSugar(Integer entryID, User user, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image,
			double bloodSugar) {
		super(entryID, user, fbPost, timestamp, status, image);
		this.bloodSugar = bloodSugar;
	}

	public BloodSugar(User user, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, double bloodSugar) {
		super(user, fbPost, timestamp, status, image);
		this.bloodSugar = bloodSugar;
	}

	public BloodSugar(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, double bloodSugar) {
		super(entryID, fbPost, timestamp, status, image);
		this.bloodSugar = bloodSugar;
	}

	public BloodSugar(Integer entryID, User user, Timestamp timestamp,
			String status, PHRImage image, double bloodSugar) {
		super(entryID, user, timestamp, status, image);
		this.bloodSugar = bloodSugar;
	}

	public BloodSugar(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, double bloodSugar) {
		super(fbPost, timestamp, status, image);
		this.bloodSugar = bloodSugar;
	}

	public BloodSugar(Timestamp timestamp, String status, PHRImage image,
			double bloodSugar) {
		super(timestamp, status, image);
		this.bloodSugar = bloodSugar;
	}

	public double getBloodSugar() {
		return bloodSugar;
	}

	public void setBloodSugar(double bloodSugar) {
		this.bloodSugar = bloodSugar;
	}

}
