package phr.sns.datamining.daoimpl;

import java.awt.Image;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import phr.exceptions.DataAccessException;
import phr.exceptions.ImageHandlerException;
import phr.models.FBPost;
import phr.models.FBPostType;
import phr.models.PHRImage;
import phr.models.PHRImageType;
import phr.sns.datamining.dao.FacebookFetcherDao;
import phr.sns.datamining.filter.KeywordsExtractor;
import phr.tools.ImageHandler;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

@Repository("facebookFetcherDao")
public class FacebookFetcherDaoImpl implements FacebookFetcherDao {

	private KeywordsExtractor keywordsExtractor = new KeywordsExtractor();
	private static final String appID = "458502710946706";
	private static final String appSecret = "c41ccfbd5ff58c87342f4df5911d2d88";

	private ResponseList<Post> getAllPostsFromFB(String userFBAccessToken)
			throws DataAccessException {
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(appID, appSecret);
		String permissions = "email,user_groups,user_status,read_stream";
		facebook.setOAuthPermissions(permissions);

		facebook.setOAuthAccessToken(new AccessToken(userFBAccessToken, null));

		try {

			ResponseList<Post> feed = facebook.getPosts();
			System.out.println("Number of Posts Retrieved: " + feed.size());
			return feed;
		} catch (FacebookException e) {
			throw new DataAccessException(
					"An error has occured while retrieving posts", e);
		}
	}

	@Override
	public List<FBPost> getAllPosts(String userFBAccessToken)
			throws DataAccessException {
		ResponseList<Post> feed = getAllPostsFromFB(userFBAccessToken);
		List<FBPost> filteredPosts = new ArrayList<>();
		try {
			filteredPosts = filterPostsList(feed);
		} catch (ImageHandlerException e) {
			throw new DataAccessException(
					"An error has occured while retrieving posts", e);
		}

		return filteredPosts;
	}

	private List<FBPost> filterPostsList(List<Post> feed)
			throws ImageHandlerException, DataAccessException {

		List<FBPost> posts = new ArrayList<>();

		for (Post p : feed) {
			if (p != null) {
				FBPost post;
				String encodedImage = null;
				if (p.getPicture() != null) {
					Image imageFromPost = ImageHandler.getImageFromURL(p
							.getPicture());
					encodedImage = ImageHandler
							.encodeImageToBase64(imageFromPost);
				}
				if (p.getMessage() != null) {
					String[] foodWordsFound = keywordsExtractor
							.extractFoodNames(p.getMessage());
					if (foodWordsFound.length > 0) {
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								FBPostType.FOOD, new PHRImage(encodedImage,
										PHRImageType.IMAGE), foodWordsFound);
						posts.add(post);
						continue;
					}
					String[] restaurantsWordsFound = keywordsExtractor
							.extractRestaurantNames(p.getMessage());
					if (restaurantsWordsFound.length > 0) {
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								FBPostType.RESTAURANT, new PHRImage(
										encodedImage, PHRImageType.IMAGE),
								restaurantsWordsFound);
						posts.add(post);
						continue;
					}
					String[] activityWordsFound = keywordsExtractor
							.extractActivityNames(p.getMessage());
					if (activityWordsFound.length > 0) {
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								FBPostType.ACTIVITY, new PHRImage(encodedImage,
										PHRImageType.IMAGE), activityWordsFound);
						posts.add(post);
						continue;
					}
					String[] sportsEstablishmentsWordsFound = keywordsExtractor
							.extractSportsEstablishmentsNames(p.getMessage());
					if (sportsEstablishmentsWordsFound.length > 0) {
						post = new FBPost(p.getMessage(), p.getCreatedTime(),
								FBPostType.SPORTS_ESTABLISHMENTS, new PHRImage(
										encodedImage, PHRImageType.IMAGE),
								sportsEstablishmentsWordsFound);
						posts.add(post);
						continue;
					}

					post = new FBPost(p.getMessage(), p.getCreatedTime(),
							FBPostType.UNRELATED, new PHRImage(encodedImage,
									PHRImageType.IMAGE), null);
					posts.add(post);
				}
			}
		}
		return posts;
	}

	@Override
	public List<FBPost> getNewPostsAfterDate(Timestamp timestamp,
			String userFBAccessToken) throws DataAccessException {
		ResponseList<Post> feed = getAllPostsFromFB(userFBAccessToken);

		List<Post> newFeed = new ArrayList<>();
		for (Post p : feed) {
			if (p != null) {
				Date dateLastUpdated = p.getUpdatedTime();
				Timestamp timeLastUpdated = new Timestamp(
						dateLastUpdated.getTime());
				if (timeLastUpdated.after(timestamp)) {
					newFeed.add(p);
				}
			}
		}
		try {
			List<FBPost> filteredPosts = filterPostsList(newFeed);
			return filteredPosts;
		} catch (ImageHandlerException e) {
			throw new DataAccessException(
					"An error has occured while retrieving posts", e);
		}

	}
}
