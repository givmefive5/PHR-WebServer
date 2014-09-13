package phr.web.models;

import java.sql.Date;

public class BloodSugar extends TrackerEntry {

	double bloodSugar;

	public BloodSugar(Integer entryID, Integer userID, Integer fbPostID,
			Date dateAdded, String status, String encodedImage,
			double bloodSugar) {
		super(entryID, userID, fbPostID, dateAdded, status, encodedImage);
		this.bloodSugar = bloodSugar;
	}

	public BloodSugar(Date dateAdded, String status, String encodedImage,
			double bloodSugar) {
		super(dateAdded, status, encodedImage);
		this.bloodSugar = bloodSugar;
	}

	public double getBloodSugar() {
		return bloodSugar;
	}

	public void setBloodSugar(double bloodSugar) {
		this.bloodSugar = bloodSugar;
	}

}
