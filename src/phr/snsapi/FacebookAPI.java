package phr.snsapi;

import java.util.List;

import phr.exceptions.SNSException;

public interface FacebookAPI {

	public List<Object> getPosts(String accessToken) throws SNSException;
}
