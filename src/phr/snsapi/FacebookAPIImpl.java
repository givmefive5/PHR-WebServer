package phr.snsapi;

import java.util.List;

import phr.exceptions.SNSException;
import phr.web.models.FacebookPost;

public class FacebookAPIImpl implements FacebookAPI {

	private static final String fbGraphUrl = "https://www.graph.facebook.com/";
	private static final String appId = "458502710946706";
	private static final String appSecret = "c41ccfbd5ff58c87342f4df5911d2d88";

	@Override
	public List<FacebookPost> getPosts(String fbAccessToken)
			throws SNSException {
		return null;
	}

}
