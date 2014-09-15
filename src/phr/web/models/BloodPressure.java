package phr.web.models;

import java.sql.Date;

public class BloodPressure extends TrackerEntry {

	int systolic;
	int diastolic;

	public BloodPressure(Integer entryID, User user, FBPost fbPost,
			Date dateAdded, String status, String encodedImage, int systolic,
			int diastolic) {
		super(entryID, user, fbPost, dateAdded, status, encodedImage);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(User user, FBPost fbPost, Date dateAdded,
			String status, String encodedImage, int systolic, int diastolic) {
		super(user, fbPost, dateAdded, status, encodedImage);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Integer entryID, FBPost fbPost, Date dateAdded,
			String status, String encodedImage, int systolic, int diastolic) {
		super(entryID, fbPost, dateAdded, status, encodedImage);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Integer entryID, User user, Date dateAdded,
			String status, String encodedImage, int systolic, int diastolic) {
		super(entryID, user, dateAdded, status, encodedImage);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(FBPost fbPost, Date dateAdded, String status,
			String encodedImage, int systolic, int diastolic) {
		super(fbPost, dateAdded, status, encodedImage);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Date dateAdded, String status, String encodedImage,
			int systolic, int diastolic) {
		super(dateAdded, status, encodedImage);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public int getSystolic() {
		return systolic;
	}

	public void setSystolic(int systolic) {
		this.systolic = systolic;
	}

	public int getDiastolic() {
		return diastolic;
	}

	public void setDiastolic(int diastolic) {
		this.diastolic = diastolic;
	}

}
