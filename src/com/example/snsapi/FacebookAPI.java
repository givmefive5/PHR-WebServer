package com.example.snsapi;

import java.util.List;

import com.example.exceptions.SNSException;

public interface FacebookAPI {

	public List<Object> getPosts(String accessToken) throws SNSException;
}
