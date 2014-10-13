package phr.dao.sqlimpl;

import java.util.List;

import phr.dao.VerificationDao;
import phr.models.FBPost;

public class VerificationDaoImpl implements VerificationDao {

	@Override
	public void setNewUnverifiedPosts(String userAccessToken,
			List<FBPost> newFbPosts) {

		for (FBPost fbPost : newFbPosts) {
			switch (fbPost.getPostType()) {

			// continue on...
			case FOOD:
				createNewFoodVerificationEntry();
				break;
			case RESTAURANT:
				createNewRestaurantEntry();
				break;
			case ACTIVITY:
				createNewActivityEntry();
				break;
			case SPORTS_ESTABLISHMENTS:
				createNewSportsEstablishmentEntry();
				break;
			default:
				break;
			}
		}
	}

	private void createNewSportsEstablishmentEntry() {
		// TODO Auto-generated method stub

	}

	private void createNewActivityEntry() {
		// TODO Auto-generated method stub

	}

	private void createNewRestaurantEntry() {
		// TODO Auto-generated method stub

	}

	private void createNewFoodVerificationEntry() {
		// TODO Auto-generated method stub

	}
}
