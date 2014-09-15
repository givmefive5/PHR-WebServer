package phr.web.models;

import java.sql.Date;

public class BloodSugar extends TrackerEntry {

	double bloodSugar;

	public BloodSugar(Integer entryID, User user, FBPost fbPost,
			Date dateAdded, String status, String encodedImage,
			double bloodSugar) {
		super(entryID, user, fbPost, dateAdded, status, encodedImage);
		this.bloodSugar = bloodSugar;
	}

	public BloodSugar(User user, FBPost fbPost, Date dateAdded, String status,
			String encodedImage, double bloodSugar) {
		super(user, fbPost, dateAdded, status, encodedImage);
		this.bloodSugar = bloodSugar;
	}

	public BloodSugar(Integer entryID, FBPost fbPost, Date dateAdded,
			String status, String encodedImage, double bloodSugar) {
		super(entryID, fbPost, dateAdded, status, encodedImage);
		this.bloodSugar = bloodSugar;
	}

	public BloodSugar(Integer entryID, User user, Date dateAdded,
			String status, String encodedImage, double bloodSugar) {
		super(entryID, user, dateAdded, status, encodedImage);
		this.bloodSugar = bloodSugar;
	}

	public BloodSugar(FBPost fbPost, Date dateAdded, String status,
			String encodedImage, double bloodSugar) {
		super(fbPost, dateAdded, status, encodedImage);
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
