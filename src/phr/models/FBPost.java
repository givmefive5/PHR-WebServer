package phr.models;

import java.sql.Timestamp;

public class FBPost {

	public Integer id;
	public String facebookID;
	public String status;
	public Timestamp timestamp;
	public FBPostType postType;
	public PHRImage image;
	public String[] extractedWords;

	public FBPost(Integer id) {
		super();
		this.id = id;
	}

	public FBPost(Integer id, String facebookId, String status, Timestamp timestamp,
			FBPostType postType, PHRImage image, String[] extractedWords) {
		super();
		this.id = id;
		this.facebookID = facebookId;
		this.status = status;
		this.timestamp = timestamp;
		this.postType = postType;
		this.image = image;
		this.extractedWords = extractedWords;
	}

	public FBPost(String facebookId, String status, Timestamp timestamp, FBPostType postType,
			PHRImage image, String[] extractedWords) {
		super();
		this.facebookID = facebookId;
		this.status = status;
		this.timestamp = timestamp;
		this.postType = postType;
		this.image = image;
		this.extractedWords = extractedWords;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFacebookId() {
		return facebookID;
	}

	public void setFacebookId(String facebookId) {
		this.facebookID = facebookId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public FBPostType getPostType() {
		return postType;
	}

	public void setPostType(FBPostType postType) {
		this.postType = postType;
	}

	public PHRImage getImage() {
		return image;
	}

	public void setImage(PHRImage image) {
		this.image = image;
	}

	public String[] getExtractedWords() {
		return extractedWords;
	}

	public void setExtractedWords(String[] extractedWords) {
		this.extractedWords = extractedWords;
	}

}
