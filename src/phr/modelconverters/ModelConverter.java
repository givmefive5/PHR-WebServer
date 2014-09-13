package phr.modelconverters;

import java.util.ArrayList;

public abstract class ModelConverter<WebModel, MobileModel> {

	public abstract MobileModel convertFromWebToMobile(WebModel webModel);

	public abstract WebModel convertFromMobileToWeb(MobileModel mobileModel);

	public final ArrayList<MobileModel> convertWebListToMobileList(
			ArrayList<WebModel> webModels) {
		ArrayList<MobileModel> mobileModels = new ArrayList<>();
		for (WebModel w : webModels)
			mobileModels.add(convertFromWebToMobile(w));
		return mobileModels;
	}

	public final ArrayList<WebModel> convertMobileListToWebList(
			ArrayList<MobileModel> mobileModels) {
		ArrayList<WebModel> webModels = new ArrayList<>();
		for (MobileModel m : mobileModels)
			webModels.add(convertFromMobileToWeb(m));
		return webModels;
	}
}
