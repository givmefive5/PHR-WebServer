package phr.sns.datamining.daoimpl;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

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
	KeywordsExtractor keywordsExtractor = new KeywordsExtractor();
	private static final String appID = "458502710946706";
	private static final String appSecret = "c41ccfbd5ff58c87342f4df5911d2d88";

	@Override
	public List<FBPost> getAllPosts(String userAccessToken)
			throws DataAccessException {
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(appID, appSecret);
		String permissions = "email,user_groups,user_status,read_stream";
		facebook.setOAuthPermissions(permissions);

		facebook.setOAuthAccessToken(new AccessToken(userAccessToken, null));

		List<FBPost> posts = new ArrayList<>();

		try {

			ResponseList<Post> feed = facebook.getPosts();
			posts = getPostsList(feed);

		} catch (FacebookException | ImageHandlerException e) {
			throw new DataAccessException(
					"An error has occured while retrieving posts", e);
		}
		return posts;
	}

	private List<FBPost> getPostsList(ResponseList<Post> feed)
			throws ImageHandlerException {

		List<FBPost> posts = new ArrayList<>();

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
				if (p.getMessage() != null) {
					String[] foodWordsFound = keywordsExtractor
							.extractFoodNames(p.getMessage());
					if (foodWordsFound.length > 0) {
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								PostType.FOOD, encodedImage, foodWordsFound);
						posts.add(post);
						continue;
					}
					String[] restaurantsWordsFound = keywordsExtractor
							.extractRestaurantNames(p.getMessage());
					if (restaurantsWordsFound.length > 0) {
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								PostType.RESTAURANT, encodedImage,
								restaurantsWordsFound);
						posts.add(post);
						continue;
					}
					String[] activityWordsFound = keywordsExtractor
							.extractRestaurantNames(p.getMessage());
					if (activityWordsFound.length > 0) {
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								PostType.ACTIVITY, encodedImage,
								activityWordsFound);
						posts.add(post);
						continue;
					}
					String[] sportsEstablishmentsWordsFound = keywordsExtractor
							.extractRestaurantNames(p.getMessage());
					if (sportsEstablishmentsWordsFound.length > 0) {
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								PostType.SPORTS_ESTABLISHMENTS, encodedImage,
								sportsEstablishmentsWordsFound);
						posts.add(post);
						continue;
					}

					post = new FBPost(p.getMessage(), p.getCreatedTime(),
							PostType.UNRELATED, encodedImage, null);
					posts.add(post);
				}
			}
		}
		return posts;
	}

	@Override
	public List<FBPost> getFoodRelatedPosts(String userAccessToken)
			throws DataAccessException {
		List<FBPost> posts = getAllPosts(userAccessToken);

		List<FBPost> foodPosts = new ArrayList<>();
		for (FBPost p : posts) {
			if (p.getPostType().equals(PostType.FOOD))
				foodPosts.add(p);
		}
		return foodPosts;
	}

	@Override
	public List<FBPost> getActivityRelatedPosts(String userAccessToken)
			throws DataAccessException {
		List<FBPost> posts = getAllPosts(userAccessToken);

		List<FBPost> activityPosts = new ArrayList<>();
		for (FBPost p : posts) {
			if (p.getPostType().equals(PostType.ACTIVITY))
				activityPosts.add(p);
		}
		return activityPosts;
	}

}
