package com.collect.it.restore;

import com.collect.it.googleplus.GooglePlusModel;
import com.collect.it.model.CollectItSharedDataModel;

/**
 * This class responsible to reset the variables, arrays and clear the data for
 * the requested data types
 */
public class RestoreSignupData {

	public RestoreSignupData() {
		/** clear facebook logged in user data array list */
		CollectItSharedDataModel.getInstance().getUserDetailListFacebook()
				.clear();

		/** clear Google+ logged in user data array list */
		CollectItSharedDataModel.getInstance().getUserDetailListGooglePlus()
				.clear();
		GooglePlusModel.getInstance().setmPerson(null);
	}
}
