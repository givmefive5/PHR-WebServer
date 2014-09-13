package phr.snsapi;

import java.util.List;

import phr.exceptions.SNSException;
import phr.web.models.FacebookPost;

public interface FacebookAPI {

	public List<FacebookPost> getPosts(String accessToken) throws SNSException;
}
