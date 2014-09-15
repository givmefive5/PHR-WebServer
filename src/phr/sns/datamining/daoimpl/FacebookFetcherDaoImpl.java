package phr.sns.datamining.daoimpl;

import java.awt.Image;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import phr.exceptions.DataAccessException;
import phr.exceptions.ImageHandlerException;
import phr.sns.datamining.dao.FacebookFetcherDao;
import phr.tools.ImageHandler;
import phr.web.models.FBPost;
import phr.web.models.PostType;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

@Repository("facebookFetcherDao")
public class FacebookFetcherDaoImpl implements FacebookFetcherDao {

	ImageHandler imageHandler = new ImageHandler();

	private static final String appID = "458502710946706";
	private static final String appSecret = "c41ccfbd5ff58c87342f4df5911d2d88";

	@Override
	public ArrayList<FBPost> getAllPosts(String userAccessToken)
			throws DataAccessException {
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(appID, appSecret);
		String permissions = "email,user_groups,user_status,read_stream";
		facebook.setOAuthPermissions(permissions);

		facebook.setOAuthAccessToken(new AccessToken(userAccessToken, null));

		ArrayList<FBPost> posts = new ArrayList<>();

		try {

			ResponseList<Post> feed = facebook.getPosts();
			posts = getPostsList(feed);

		} catch (FacebookException | ImageHandlerException e) {
			throw new DataAccessException(
					"An error has occured while retrieving posts", e);
		}
		return posts;
	}

	private ArrayList<FBPost> getPostsList(ResponseList<Post> feed)
			throws ImageHandlerException {

		ArrayList<FBPost> posts = new ArrayList<>();

		for (Post p : feed) {
			if (p != null) {
				FBPost post;
				String encodedImage = null;
				if (p.getPicture() != null) {
					Image imageFromPost = imageHandler.getImageFromURL(p
							.getPicture());
					encodedImage = imageHandler
							.encodeImageToBase64(imageFromPost);
				}
				String[] extractedWords;
				if (p.getMessage() != null) {
					if (KeywordsExtractor.hasFoodNames(p.getMessage())) {
						extractedWords = KeywordsExtractor.extractFoodNames(p
								.getMessage());
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								PostType.FOOD, encodedImage, extractedWords);
					} else if (KeywordsExtractor.hasRestaurantNames(p
							.getMessage())) {
						extractedWords = KeywordsExtractor
								.extractRestaurantNames(p.getMessage());
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								PostType.RESTAURANT, encodedImage,
								extractedWords);
					} else if (KeywordsExtractor.hasActivityNames(p
							.getMessage())) {
						extractedWords = KeywordsExtractor
								.extractActivityNames(p.getMessage());
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								PostType.ACTIVITY, encodedImage, extractedWords);
					} else if (KeywordsExtractor.hasSportsEstablishmentsNames(p
							.getMessage())) {
						extractedWords = KeywordsExtractor
								.extractSportsEstablishmentsNames(p
										.getMessage());
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								PostType.SPORTS_ESTABLISHMENTS, encodedImage,
								extractedWords);
					} else
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								PostType.UNRELATED, encodedImage, null);
					posts.add(post);
				}
			}
		}
		return posts;
	}

	@Override
	public ArrayList<FBPost> getFoodRelatedPosts(String userAccessToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<FBPost> getActivityRelatedPosts(String userAccessToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
