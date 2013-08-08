package com.collect.it.fragments;

import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.collect.it.R;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.DoubleOptionAlertWithoutTitle;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.facebook.FacebookUserDataModel;
import com.collect.it.facebook.FacebookUtil;
import com.collect.it.facebook.OnFacebookListener;
import com.collect.it.googleplus.GooglePlusLoginActivity;
import com.collect.it.interfaces.OnUserDetailProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.model.UserDataModel;
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;
import com.collect.it.utils.GetUserDetail;
import com.collect.it.utils.SignOut;
import com.collect.it.utils.UtilityMethods;
import com.facebook.Response;
import com.facebook.model.GraphUser;

/**
 * This fragment class is used to display and provide functionality for the
 * setting screen of the application
 */
public class SettingsFragment extends CollectItAbstractFragment implements
		OnFacebookListener, OnUserDetailProcess {
	/**
	 * Declare class variables
	 */
	private FragmentActivity context;

	private ImageView googlePlusCheckImageView, facebookCheckImageView,
			twitterCheckImageView;

	private boolean isGooglePlusChecked, isFacebookChecked, isTwitterChecked;

	private final int TWITTER_ID = 4;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_setting, null);

		// initiate components
		view.findViewById(R.id.setting_edit_profile_rel).setOnClickListener(
				this);
		view.findViewById(R.id.setting_find_friend_rel)
				.setOnClickListener(this);
		view.findViewById(R.id.setting_notification_rel).setOnClickListener(
				this);
		view.findViewById(R.id.setting_privacy_rel).setOnClickListener(this);

		googlePlusCheckImageView = (ImageView) view
				.findViewById(R.id.setting_googleplus_checkbox_img);
		googlePlusCheckImageView.setOnClickListener(this);
		facebookCheckImageView = (ImageView) view
				.findViewById(R.id.setting_facebook_checkbox_img);
		facebookCheckImageView.setOnClickListener(this);
		twitterCheckImageView = (ImageView) view
				.findViewById(R.id.setting_twitter_checkbox_img);
		twitterCheckImageView.setOnClickListener(this);

		view.findViewById(R.id.setting_googleplus_rel).setOnClickListener(this);
		view.findViewById(R.id.setting_facebook_rel).setOnClickListener(this);
		view.findViewById(R.id.setting_twitter_rel).setOnClickListener(this);

		view.findViewById(R.id.setting_help_center_rel)
				.setOnClickListener(this);
		view.findViewById(R.id.setting_feedback_rel).setOnClickListener(this);
		view.findViewById(R.id.setting_terms_services_rel).setOnClickListener(
				this);
		view.findViewById(R.id.setting_logout_btn).setOnClickListener(this);

		view.findViewById(R.id.setting_delete_account_btn).setOnClickListener(
				this);

		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.collect.it.application.CollectItAbstractFragment#onClick(android.
	 * view.View)
	 */
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		/* Edit profile relative layout */
		case R.id.setting_edit_profile_rel:
			UtilityMethods.replaceFragment(new EditProfileFragment(), context,
					R.id.tab_content, true, true,
					FragmentTagNames.EDIT_PROFILE.name(), null);
			break;
		/* Find friends relative layout */
		case R.id.setting_find_friend_rel:
			break;
		/* Notification relative layout */
		case R.id.setting_notification_rel:
			break;
		/* Privacy relative layout */
		case R.id.setting_privacy_rel:
			break;
		/* Feedback relative layout */
		case R.id.setting_feedback_rel:
			break;
		/* Terms and services relative layout */
		case R.id.setting_terms_services_rel:
			break;
		/* Google plus relative layout */
		case R.id.setting_googleplus_rel:
			break;
		/* Facebook relative layout */
		case R.id.setting_facebook_rel:
			break;
		/* Twitter relative layout */
		case R.id.setting_twitter_rel:
			break;
		/* Logout Button */
		case R.id.setting_logout_btn:

			new DoubleOptionAlertWithoutTitle(context, getResources()
					.getString(R.string.signout_msg), getResources().getString(
					R.string.yes_btn), getResources()
					.getString(R.string.no_btn),
					DoubleOptionAlertWithoutTitle.SIGN_OUT, null);
			break;
		/* google plus check box image */
		case R.id.setting_googleplus_checkbox_img:
			googlePlusCheckFunc();
			break;
		/* facebook check box image */
		case R.id.setting_facebook_checkbox_img:
			facebookCheckFunc();
			break;
		/* twitter check box image */
		case R.id.setting_twitter_checkbox_img:
			twitterCheckFunc();
			break;
		/* Delete account button */
		case R.id.setting_delete_account_btn:
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// save parent activity context into class variable
		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		// Google analytics
		// Send a screen view when the Activity is displayed to the user.
		GoogleAnalyticModel.getInstance().getTracker(context)
				.sendView(getResources().getString(R.string.ga_setting_screen));

		// hide setting icon on top bar
		UtilityMethods.setSettingIconVisibility(context, View.GONE);

		// show back button
		UtilityMethods.setBackButtonVisibility(context, View.VISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.SETTING_SCREEN_ID);

		setValues();

		switch (CollectItSharedDataModel.getInstance().getSignupThroughId()) {
		case CollectItConstants.SIGNUP_GOOGLEPLUS_ACTIVITY_FINISH:
		case CollectItConstants.SIGNUP_THROUGH_GOOGLEPLUS:
			try {
				// call web service here for facebook login
				DialogProgressCustom.getInstance().startProgressDialog(context,
						true);
				// reset google plus variables
				CollectItSharedDataModel.getInstance().setSignupThroughId(0);
				isGooglePlusChecked = true;
				new WebServiceAsyncHttpPostJson(
						context,
						CollectItServerConstants.WEBSERVICE_SET_SHARING_STATUS,
						CollectItConstants.SETTING_SCREEN_ID,
						this,
						createJsonToSocialData(
								CollectItSharedDataModel.getInstance()
										.getUserDetailListGooglePlus().get(0)
										.getId(),
								CollectItServerConstants.REGISTRATION_TYPE_GOOGLEPLUS))
						.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.collect.it.application.CollectItAbstractFragment#getServerValues(
	 * java.lang.String, int, boolean, java.lang.String)
	 */
	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		// TODO Auto-generated method stub
		super.getServerValues(response, id, isOk, exception);
		try {
			if (isOk) {
				if (response != null) {
					switch (id) {
					case CollectItConstants.SETTING_SCREEN_ID:
						parseServerResponse(response);
						break;
					default:
						break;
					}
				} else {
					new ViewToastMsg(context, getResources().getString(
							R.string.connection_error_collectit));
				}
			} else if (exception.equalsIgnoreCase(getResources().getString(
					R.string.connection_error_internet))) {
				new ViewToastMsg(context, exception);
			} else {
				new ViewToastMsg(context, getResources().getString(
						R.string.connection_error));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally close the progress dialog
			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();
		}

	}

	/**
	 * Functionality to parse server response
	 * 
	 * @param server
	 *            response json string
	 */
	void parseServerResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String responseString = "";
			if (json.has("response")) {
				responseString = json.getString("response");

				if (responseString.equalsIgnoreCase("true")) {
					// fetch loggedin user details
					new GetUserDetail(context, SettingsFragment.this,
							CollectItConstants.SETTING_SCREEN_ID);
				}

			} else {
				if (json.has("errorText")) {
					String errorString = json.getString("errorText");
					new SingleOptionAlertWithoutTitle(context, errorString,
							getResources().getString(R.string.ok), 0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	/**
	 * Functionality to set values for the screen
	 */
	void setValues() {
		try {
			DialogProgressCustom.getInstance().startProgressDialog(context,
					true);
			if (CollectItSharedDataModel.getInstance().getUserDetailList() != null
					&& CollectItSharedDataModel.getInstance()
							.getUserDetailList().size() > 0) {
				UserDataModel userDetails = CollectItSharedDataModel
						.getInstance().getUserDetailList().get(0);

				if (userDetails != null) {
					if (userDetails.getFacebookSharingStatus().equals("1")) {
						facebookCheckImageView
								.setImageResource(R.drawable.check_box_select);
						isFacebookChecked = true;
					} else {
						facebookCheckImageView
								.setImageResource(R.drawable.check_box_unselect);
						isFacebookChecked = false;
					}

					if (userDetails.getGooglePlusSharingStatus().equals("1")) {
						googlePlusCheckImageView
								.setImageResource(R.drawable.check_box_select);
						isGooglePlusChecked = true;
					} else {
						googlePlusCheckImageView
								.setImageResource(R.drawable.check_box_unselect);
						isGooglePlusChecked = false;
					}

					if (userDetails.getTwitterSharingStatus().equals("1")) {
						twitterCheckImageView
								.setImageResource(R.drawable.check_box_select);
						isTwitterChecked = true;
					} else {
						twitterCheckImageView
								.setImageResource(R.drawable.check_box_unselect);
						isTwitterChecked = false;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DialogProgressCustom.getInstance().stopProgressDialog();
		}
	}

	/**
	 * Functionality of Google plus check image
	 */
	void googlePlusCheckFunc() {
		if (isGooglePlusChecked) {
			isGooglePlusChecked = false;
			// call web service here for facebook login
			DialogProgressCustom.getInstance().startProgressDialog(context,
					true);
			new WebServiceAsyncHttpPostJson(context,
					CollectItServerConstants.WEBSERVICE_SET_SHARING_STATUS,
					CollectItConstants.SETTING_SCREEN_ID, this,
					createJsonToSocialData(null, 0)).execute();
		} else {
			isGooglePlusChecked = true;
			if (CollectItSharedDataModel.getInstance().getUserDetailList() != null
					&& CollectItSharedDataModel.getInstance()
							.getUserDetailList().size() > 0) {
				UserDataModel userDetails = CollectItSharedDataModel
						.getInstance().getUserDetailList().get(0);

				if (userDetails.getGooglePlusSharingStatus().equals("0")) {
					startActivity(new Intent(context,
							GooglePlusLoginActivity.class));
				} else {

					// call web service here for facebook login
					DialogProgressCustom.getInstance().startProgressDialog(
							context, true);
					new WebServiceAsyncHttpPostJson(
							context,
							CollectItServerConstants.WEBSERVICE_SET_SHARING_STATUS,
							CollectItConstants.SETTING_SCREEN_ID, this,
							createJsonToSocialData(null, 0)).execute();
				}
			} else {
				// start progress dialog
				DialogProgressCustom.getInstance().startProgressDialog(context,
						true);
				new GetUserDetail(context, SettingsFragment.this,
						CollectItServerConstants.REGISTRATION_TYPE_GOOGLEPLUS);
			}
		}
	}

	/**
	 * Functionality of Facebook check image
	 */
	void facebookCheckFunc() {
		if (isFacebookChecked) {
			isFacebookChecked = false;
			// call web service here for facebook login
			DialogProgressCustom.getInstance().startProgressDialog(context,
					true);
			new WebServiceAsyncHttpPostJson(context,
					CollectItServerConstants.WEBSERVICE_SET_SHARING_STATUS,
					CollectItConstants.SETTING_SCREEN_ID, this,
					createJsonToSocialData(null, 0)).execute();
		} else {
			isFacebookChecked = true;
			if (CollectItSharedDataModel.getInstance().getUserDetailList() != null
					&& CollectItSharedDataModel.getInstance()
							.getUserDetailList().size() > 0) {
				UserDataModel userDetails = CollectItSharedDataModel
						.getInstance().getUserDetailList().get(0);

				if (userDetails.getFacebookSharingStatus().equals("0")) {
					FacebookUtil.getInstance(context).login(context,
							SettingsFragment.this);
				} else {

					// call web service here for facebook login
					DialogProgressCustom.getInstance().startProgressDialog(
							context, true);
					new WebServiceAsyncHttpPostJson(
							context,
							CollectItServerConstants.WEBSERVICE_SET_SHARING_STATUS,
							CollectItConstants.SETTING_SCREEN_ID, this,
							createJsonToSocialData(null, 0)).execute();
				}
			} else {
				// start progress dialog
				DialogProgressCustom.getInstance().startProgressDialog(context,
						true);
				new GetUserDetail(context, SettingsFragment.this,
						CollectItServerConstants.REGISTRATION_TYPE_FACEBOOK);
			}
		}
	}

	/**
	 * Functionality of Twitter check image
	 */
	void twitterCheckFunc() {
		if (isTwitterChecked) {
			isTwitterChecked = false;

		} else {
			isTwitterChecked = true;

		}

		// call web service here for facebook login
		DialogProgressCustom.getInstance().startProgressDialog(context, true);
		new WebServiceAsyncHttpPostJson(context,
				CollectItServerConstants.WEBSERVICE_SET_SHARING_STATUS,
				CollectItConstants.SETTING_SCREEN_ID, this,
				createJsonToSocialData(null, 0)).execute();
	}

	/**
	 * Functionality to create json to post facebookGoogle+/Twitter data against
	 * user id
	 * 
	 * @param id
	 *            this is the id of user for respective social network
	 * @param socialId
	 *            this identify which login credential has been accessed for
	 *            social network login
	 */
	private JSONObject createJsonToSocialData(String id, int socialId) {
		JSONObject json = new JSONObject();
		try {

			if (isTwitterChecked) {
				json.put(
						CollectItServerConstants.WEBSERVICE_KEY_TWITTER_SHARING_STATUS,
						"1");
			} else {
				json.put(
						CollectItServerConstants.WEBSERVICE_KEY_TWITTER_SHARING_STATUS,
						"0");
			}

			if (isFacebookChecked) {
				json.put(
						CollectItServerConstants.WEBSERVICE_KEY_FACEBOOK_SHARING_STATUS,
						"1");
			} else {
				json.put(
						CollectItServerConstants.WEBSERVICE_KEY_FACEBOOK_SHARING_STATUS,
						"0");
			}

			if (isGooglePlusChecked) {
				json.put(
						CollectItServerConstants.WEBSERVICE_KEY_GOOGLE_PLUS_SHARING_STATUS,
						"1");
			} else {
				json.put(
						CollectItServerConstants.WEBSERVICE_KEY_GOOGLE_PLUS_SHARING_STATUS,
						"0");
			}

			if (CollectItSharedDataModel.getInstance().getUserDetailList() != null
					&& CollectItSharedDataModel.getInstance()
							.getUserDetailList().size() > 0) {
				UserDataModel userDetails = CollectItSharedDataModel
						.getInstance().getUserDetailList().get(0);
				if (userDetails != null) {
					json.put(
							CollectItServerConstants.WEBSERVICE_KEY_FACEBOOK_ID,
							userDetails.getfbId());

					json.put(
							CollectItServerConstants.WEBSERVICE_KEY_GOOGLEPLUS_ID,
							userDetails.getgId());
					json.put(
							CollectItServerConstants.WEBSERVICE_KEY_TWITTER_ID,
							"");
				}
			} else {
				json.put(CollectItServerConstants.WEBSERVICE_KEY_FACEBOOK_ID,
						"");

				json.put(CollectItServerConstants.WEBSERVICE_KEY_GOOGLEPLUS_ID,
						"");
				json.put(CollectItServerConstants.WEBSERVICE_KEY_TWITTER_ID, "");
			}

			json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID,
					CollectItSharedDataModel.getInstance().getUserId());

			// check passed id is associated with
			switch (socialId) {
			case CollectItServerConstants.REGISTRATION_TYPE_FACEBOOK:
				json.put(CollectItServerConstants.WEBSERVICE_KEY_FACEBOOK_ID,
						id);
				break;
			case CollectItServerConstants.REGISTRATION_TYPE_GOOGLEPLUS:
				json.put(CollectItServerConstants.WEBSERVICE_KEY_GOOGLEPLUS_ID,
						id);
				break;
			case TWITTER_ID:
				json.put(CollectItServerConstants.WEBSERVICE_KEY_TWITTER_ID, id);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/********************************
	 **** Facebook listeners**************
	 *******************************/

	@Override
	public void OnLoginSuccess(FacebookUserDataModel loginDetail) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnLoginError(String error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnLogoutSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnLoginUserDetail(FacebookUserDataModel user) {
		try {
			// call web service here for facebook login
			DialogProgressCustom.getInstance().startProgressDialog(context,
					true);
			new WebServiceAsyncHttpPostJson(
					context,
					CollectItServerConstants.WEBSERVICE_SET_SHARING_STATUS,
					CollectItConstants.SETTING_SCREEN_ID,
					this,
					createJsonToSocialData(user.getId(),
							CollectItServerConstants.REGISTRATION_TYPE_FACEBOOK))
					.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void OnWallPostSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnImagePostSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnFriendList(List<GraphUser> friends) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnRequestSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnSessionExpires() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnFacebookError(String errorMessage, String errorType,
			int errorCode) {
		// TODO Auto-generated method stub

	}

	/*****************************************
	 * User detail fetch listener from Collect.it server
	 *****************************************/

	@Override
	public void onUserDetailError(String errorMsg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUserDetailStoredSuccessfully(int taskId) {
		try {
			UserDataModel userDetails = null;
			if (CollectItSharedDataModel.getInstance().getUserDetailList() != null
					&& CollectItSharedDataModel.getInstance()
							.getUserDetailList().size() > 0) {
				userDetails = CollectItSharedDataModel.getInstance()
						.getUserDetailList().get(0);
			}

			switch (taskId) {
			case CollectItServerConstants.REGISTRATION_TYPE_FACEBOOK:

				if (userDetails != null
						&& userDetails.getFacebookSharingStatus().equals("0")) {
					FacebookUtil.getInstance(context).login(context,
							SettingsFragment.this);
				} else {
					isFacebookChecked = true;
					facebookCheckImageView
							.setImageResource(R.drawable.check_box_select);

				}
				break;
			case CollectItServerConstants.REGISTRATION_TYPE_GOOGLEPLUS:
				if (userDetails != null
						&& userDetails.getGooglePlusSharingStatus().equals("0")) {
					startActivity(new Intent(context,
							GooglePlusLoginActivity.class));
				} else {
					isGooglePlusChecked = true;
					googlePlusCheckImageView
							.setImageResource(R.drawable.check_box_select);
				}
				break;
			default:
				setValues();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();
		}

	}
}
