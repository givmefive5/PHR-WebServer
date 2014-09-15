package phr.web.models;

import java.util.Date;

public class FBPost {

	public String status;
	public Date datetime;
	public PostType postType;
	public String encodedImage;
	public String[] extractedWords;

	public FBPost(String status, Date datetime, PostType postType,
			String encodedImage, String[] extractedWords) {
		super();
		this.status = status;
		this.datetime = datetime;
		this.postType = postType;
		this.encodedImage = encodedImage;
		this.extractedWords = extractedWords;
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

	public PostType getPostType() {
		return postType;
	}

	public void setPostType(PostType postType) {
		this.postType = postType;
	}

	public String getEncodedImage() {
		return encodedImage;
	}

	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}

	public String[] getExtractedWords() {
		return extractedWords;
	}

	public void setExtractedWords(String[] extractedWords) {
		this.extractedWords = extractedWords;
	}

}
