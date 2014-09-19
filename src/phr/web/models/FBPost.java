package phr.web.models;

import java.util.Date;

public class FBPost {

	public Integer id;
	public String status;
	public Date datetime;
	public FBPostType postType;
	public String imageFilePath;
	public String[] extractedWords;

	public FBPost(Integer id) {
		super();
		this.id = id;
	}

	public FBPost(Integer id, String status, Date datetime, FBPostType postType,
			String imageFilePath, String[] extractedWords) {
		super();
		this.id = id;
		this.status = status;
		this.datetime = datetime;
		this.postType = postType;
		this.imageFilePath = imageFilePath;
		this.extractedWords = extractedWords;
	}

	public FBPost(String status, Date datetime, FBPostType postType,
			String imageFilePath, String[] extractedWords) {
		super();
		this.status = status;
		this.datetime = datetime;
		this.postType = postType;
		this.imageFilePath = imageFilePath;
		this.extractedWords = extractedWords;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public FBPostType getPostType() {
		return postType;
	}

	public void setPostType(FBPostType postType) {
		this.postType = postType;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public String[] getExtractedWords() {
		return extractedWords;
	}

	public void setExtractedWords(String[] extractedWords) {
		this.extractedWords = extractedWords;
	}

}
