package com.collect.it.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.collect.it.LoginSignupFragmentActivity;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.model.CollectItSharedDataModel;

/**
 * This class is used to reset variables when user select the signout option
 */
public class SignOut {
	/**
	 * @param context
	 *            Activity context
	 */
	public SignOut(Activity context) {
		// start progress dialog
		DialogProgressCustom.getInstance().startProgressDialog(context, true);

		// clear the preferences
		CollectItSharedDataModel.getInstance().getPreferences(context).edit()
				.clear().commit();

		// clear user details
		CollectItSharedDataModel.getInstance().setUserId(null);
		CollectItSharedDataModel.getInstance().getUserDetailList().clear();
		CollectItSharedDataModel.getInstance().getUserDetailListFacebook()
				.clear();
		CollectItSharedDataModel.getInstance().getUserDetailListGooglePlus()
				.clear();

		CollectItSharedDataModel.getInstance().setSignupThroughId(0);

		CollectItSharedDataModel.getInstance().getHomeItemList().clear();
		CollectItSharedDataModel.getInstance().getItemSearchList().clear();

		// start login/signup fragment
		context.startActivity(new Intent(context,
				LoginSignupFragmentActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK));

		// stop progress dialog
		DialogProgressCustom.getInstance().stopProgressDialog();

		// close current parent fragment and its associated fragments
		context.finish();
	}
}
