package com.collect.it.fragments;

import java.util.List;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.collect.it.HomeTabFragmentActivity;
import com.collect.it.R;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.facebook.FacebookUserDataModel;
import com.collect.it.facebook.FacebookUtil;
import com.collect.it.facebook.OnFacebookListener;
import com.collect.it.googleplus.GooglePlusLoginActivity;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.model.UserDataModel;
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;
import com.collect.it.utils.GetUserDetail;
import com.collect.it.utils.UtilityMethods;
import com.facebook.Response;
import com.facebook.model.GraphUser;

/**
 * This fragment class is used for the login screen functionality
 */
public class LoginFragment extends CollectItAbstractFragment implements
		OnFacebookListener {

	/**
	 * Declare class variables
	 */
	private EditText userNameEditText, passwordEditText;

	private FragmentActivity context;

	private GooglePlusBroadcast googlePlusBroadcastClassObj = new GooglePlusBroadcast();

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_login, null);

		view.findViewById(R.id.login_btn).setOnClickListener(this);
		userNameEditText = (EditText) view
				.findViewById(R.id.login_username_edit);
		passwordEditText = (EditText) view
				.findViewById(R.id.login_password_edit);
		passwordEditText.setTypeface(Typeface.DEFAULT);
		passwordEditText
				.setTransformationMethod(new PasswordTransformationMethod());

		view.findViewById(R.id.frag_login_google_plus_btn).setOnClickListener(
				this);
		view.findViewById(R.id.frag_login_facebook_btn)
				.setOnClickListener(this);

		view.findViewById(R.id.frag_login_forgot_pass_txt).setOnClickListener(
				this);

		return view;
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
					String userIdParsedMsg = "";
					switch (id) {
					case CollectItConstants.LOGIN_SCREEN_ID:
						// save user id for logged in user
						userIdParsedMsg = UtilityMethods.isUserIdParsed(
								context, response, 0);
						/* if user id has been parsed successfully */
						if (userIdParsedMsg.equalsIgnoreCase(getResources()
								.getString(R.string.success))) {

							// fetch user data with background process
							new GetUserDetail(context, null, 0);

							UtilityMethods
									.startTabScreenAndCloseCurrentActivity(
											context, null);
						}
						/*
						 * if user id not parsed successfully and any error
						 * message not been displayed
						 */
						else if (!userIdParsedMsg
								.equalsIgnoreCase(getResources().getString(
										R.string.success))
								&& !userIdParsedMsg
										.equalsIgnoreCase(getResources()
												.getString(
														R.string.success_error))) {
							new SingleOptionAlertWithoutTitle(context,
									getResources()
											.getString(R.string.try_again),
									getResources().getString(R.string.ok), 0);
						} else {
							// nothing to do
						}
						break;
					// Login through facebook/Google+ response
					case CollectItConstants.LOGIN_SCREEN_ID_SOCIAL:
						// save user id for logged in user
						userIdParsedMsg = UtilityMethods
								.isUserIdParsed(
										context,
										response,
										SingleOptionAlertWithoutTitle.START_SIGNUP_FRAGMENT);
						/* if user id has been parsed successfully */
						if (userIdParsedMsg.equalsIgnoreCase(getResources()
								.getString(R.string.success))) {
							// user id has been parsed successfully
							// fetch user data with background process
							new GetUserDetail(context, null, 0);

							// start tab fragments
							startActivity(new Intent(context,
									HomeTabFragmentActivity.class)
									.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
											| Intent.FLAG_ACTIVITY_NEW_TASK));
							UtilityMethods.finishCurrentActivity(context);
						}
						/*
						 * if user id not parsed successfully and any error
						 * message not been displayed
						 */
						else if (!userIdParsedMsg
								.equalsIgnoreCase(getResources().getString(
										R.string.success))
								&& !userIdParsedMsg
										.equalsIgnoreCase(getResources()
												.getString(
														R.string.success_error))) {
							new SingleOptionAlertWithoutTitle(context,
									getResources()
											.getString(R.string.try_again),
									getResources().getString(R.string.ok), 0);
						} else {
							// nothing to do
						}
						break;
					case CollectItConstants.FORGOT_PASSWORD_ID:
						parseForgotPasswordServerResponse(response);
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
			// reset values for signup through id
			CollectItSharedDataModel.getInstance().setSignupThroughId(0);
		}
	}

	/**
	 * Functionality to parse web service response against forgot password
	 * 
	 * @param response
	 *            web service response
	 */
	void parseForgotPasswordServerResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String responseString = "";
			if (json.has("response")) {
				responseString = json.getString("response");

				if (responseString.equalsIgnoreCase("true")) {

					if (json.has("msg")) {
						String messageString = json.getString("msg");
						new SingleOptionAlertWithoutTitle(context,
								messageString, getResources().getString(
										R.string.ok), 0);
					}

				} else {
					if (json.has("errorText")) {
						String errorString = json.getString("errorText");
						new SingleOptionAlertWithoutTitle(context, errorString,
								getResources().getString(R.string.ok), 0);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.collect.it.application.CollectItAbstractFragment#onClick(android.
	 * view.View)
	 * 
	 * click event functionality on the associated components
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.login_btn:
			loginFunc();
			break;
		// google+ login button
		case R.id.frag_login_google_plus_btn:

			CollectItSharedDataModel.getInstance().setSignupThroughId(
					CollectItConstants.SIGNUP_THROUGH_GOOGLEPLUS);
			startActivity(new Intent(getActivity(),
					GooglePlusLoginActivity.class));

			break;
		// facebook login button
		case R.id.frag_login_facebook_btn:
			CollectItSharedDataModel.getInstance().setSignupThroughId(
					CollectItConstants.SIGNUP_THROUGH_FACEBOOK);
			FacebookUtil.getInstance(context)
					.login(context, LoginFragment.this);
			break;
		// forgot password
		case R.id.frag_login_forgot_pass_txt:
			forgotPassFunc();
			break;
		default:
			break;
		}
	}

	/**
	 * Functionality of login button
	 */
	void loginFunc() {
		try {
			String userName = userNameEditText.getText().toString().trim();
			String password = passwordEditText.getText().toString().trim();

			if (userName != null && !userName.equals("")
					&& !userName.equalsIgnoreCase("null")) {
				if (password != null && !password.equals("")
						&& !password.equalsIgnoreCase("null")) {
					// hit web service
					DialogProgressCustom.getInstance().startProgressDialog(
							context, true);
					new WebServiceAsyncHttpPostJson(context,
							CollectItServerConstants.WEBSERVICE_LOGIN,
							CollectItConstants.LOGIN_SCREEN_ID, this,
							createLoginJson(userName, password)).execute();
				} else {
					new SingleOptionAlertWithoutTitle(getActivity(),
							getResources().getString(
									R.string.password_not_entered),
							getResources().getString(R.string.ok), 0);
				}
			} else {
				new SingleOptionAlertWithoutTitle(getActivity(), getResources()
						.getString(R.string.username_not_entered),
						getResources().getString(R.string.ok), 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
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

		// store fragment activity context into class variables
		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		// Google analytics
		// Send a screen view when the Activity is displayed to the user.
		/*
		 * if (CollectItSharedDataModel.getInstance().getGaTracker() != null) {
		 * CollectItSharedDataModel .getInstance() .getGaTracker() .sendView(
		 * getResources().getString(R.string.ga_login_screen)); }
		 */

		GoogleAnalyticModel.getInstance().getTracker(context)
				.sendView(getResources().getString(R.string.ga_login_screen));

		// Google plus broadcast registration for this fragment
		context.registerReceiver(googlePlusBroadcastClassObj, new IntentFilter(
				GooglePlusLoginActivity.GOOGLE_PLUS_LOGIN_BROADCAST_KEY));

		// call web service for google+ login
		if ((CollectItSharedDataModel.getInstance().getSignupThroughId() == CollectItConstants.SIGNUP_GOOGLEPLUS_ACTIVITY_FINISH)) {
			try {

				DialogProgressCustom.getInstance().startProgressDialog(context,
						true);
				new WebServiceAsyncHttpPostJson(
						context,
						CollectItServerConstants.WEBSERVICE_LOGIN_SOCIAL_SITES,
						CollectItConstants.LOGIN_SCREEN_ID_SOCIAL,
						this,
						createJsonForLoginThroughSocial(
								CollectItSharedDataModel.getInstance()
										.getUserDetailListGooglePlus().get(0)
										.getId(),
								CollectItServerConstants.REGISTRATION_TYPE_GOOGLEPLUS))
						.execute();
			} catch (Exception e) {
				e.printStackTrace();
				DialogProgressCustom.getInstance().stopProgressDialog();
			}
		}

		// show back button
		UtilityMethods.setBackButtonVisibility(context, View.VISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.LOGIN_SCREEN_ID);

		// hide keyboard
		UtilityMethods.hideKeyboard(context);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStop()
	 */
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

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
		// unregister broadcast receiver
		context.unregisterReceiver(googlePlusBroadcastClassObj);

		// hide soft keyboard if displayed
		UtilityMethods.hideKeyboard(context);
	}

	/**
	 * Functionality to create json packet for web service to login into the
	 * application
	 * 
	 * @param userName
	 * @param password
	 * 
	 * @return json object for web service
	 */
	JSONObject createLoginJson(String userName, String password) {
		JSONObject json = new JSONObject();
		try {
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USERNAME, userName);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_PASSWORD, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Functionality to create json to login through web service
	 * 
	 * @param id
	 *            facebook id OR Google+ id
	 * @param loginThrough
	 *            id to determine the requested login (Facebook/Google+)(2/1)
	 * 
	 * @return json object to send on server through web service
	 */
	private JSONObject createJsonForLoginThroughSocial(String id,
			int loginThrough) {
		JSONObject json = new JSONObject();
		try {
			switch (loginThrough) {
			case CollectItServerConstants.REGISTRATION_TYPE_FACEBOOK:
				json.put(CollectItServerConstants.WEBSERVICE_KEY_FACEBOOK_ID,
						id);
				break;
			case CollectItServerConstants.REGISTRATION_TYPE_GOOGLEPLUS:
				json.put(CollectItServerConstants.WEBSERVICE_KEY_GOOGLEPLUS_ID,
						id);
				break;
			default:
				break;
			}
			json.put(CollectItServerConstants.WEBSERVICE_KEY_REGISTRATION_TYPE,
					loginThrough);

			CollectItSharedDataModel.getInstance().setSignupThroughId(
					loginThrough);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Broadcast receiver to detect the Google+ login/Error and hit web server
	 * if successful login
	 */
	private class GooglePlusBroadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			String action = arg1.getAction();
			if (action != null) {
				if (action
						.contains(GooglePlusLoginActivity.GOOGLE_PLUS_LOGIN_BROADCAST_KEY)
						&& action
								.equalsIgnoreCase(GooglePlusLoginActivity.GOOGLE_PLUS_LOGIN_BROADCAST_KEY)) {
					Bundle bundle = arg1.getExtras();
					if (bundle
							.containsKey(CollectItConstants.BUNDLE_GOOGLE_PLUS_LOGIN_KEY)) {
						int loginKey = bundle
								.getInt((CollectItConstants.BUNDLE_GOOGLE_PLUS_LOGIN_KEY));

						// call web service for google+ login
						if ((loginKey == CollectItConstants.SIGNUP_GOOGLEPLUS_ACTIVITY_FINISH)) {
							DialogProgressCustom.getInstance()
									.startProgressDialog(context, true);
							new WebServiceAsyncHttpPostJson(
									context,
									CollectItServerConstants.WEBSERVICE_LOGIN_SOCIAL_SITES,
									CollectItConstants.LOGIN_SCREEN_ID_SOCIAL,
									LoginFragment.this,
									createJsonForLoginThroughSocial(
											CollectItSharedDataModel
													.getInstance()
													.getUserDetailListGooglePlus()
													.get(0).getId(),
											CollectItServerConstants.REGISTRATION_TYPE_GOOGLEPLUS))
									.execute();
						}
					}
				}
			}
		}
	}

	/**
	 * Functionality of Forgot password
	 */
	void forgotPassFunc() {
		final Dialog forgotDialog = new Dialog(context);
		// hide the title bar of the dialog
		forgotDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		forgotDialog.setContentView(R.layout.dialog_forgot_password);

		final EditText forgotPassEditText = (EditText) forgotDialog
				.findViewById(R.id.forgot_pass_username_edit);
		Button submitButton = (Button) forgotDialog
				.findViewById(R.id.forgot_pass_submit_btn);

		submitButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String userNameVal = forgotPassEditText.getText().toString()
						.trim();

				if (userNameVal != null
						&& !userNameVal.equalsIgnoreCase("null")
						&& !userNameVal.equals("")) {
					DialogProgressCustom.getInstance().startProgressDialog(
							context, true);
					/* hit web service to send user id for forgot password */
					new WebServiceAsyncHttpPostJson(
							context,
							CollectItServerConstants.WEBSERVICE_FORGOT_PASSWORD,
							CollectItConstants.FORGOT_PASSWORD_ID,
							LoginFragment.this,
							createJsonForgotPassword(userNameVal)).execute();

					// close the dialog
					forgotDialog.dismiss();
				} else {
					new SingleOptionAlertWithoutTitle(context, getResources()
							.getString(R.string.enter_uname_email),
							getResources().getString(R.string.ok), 0);
				}
			}
		});

		forgotDialog.show();
	}

	/**
	 * Functionality to create json for forgot password
	 * 
	 * @param userName
	 *            user name for collect.it entered by user
	 */
	JSONObject createJsonForgotPassword(String userName) {
		JSONObject json = new JSONObject();
		try {

			json.put(CollectItServerConstants.WEBSERVICE_KEY_USERNAME, userName);

			// check for user email address
			/*
			 * if (CollectItSharedDataModel.getInstance().getUserDetailList() !=
			 * null) { UserDataModel userDetails = CollectItSharedDataModel
			 * .getInstance().getUserDetailList().get(0);
			 * 
			 * if (userDetails != null) { json.put(
			 * CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_EMAIL,
			 * userDetails.getEmail()); } else { json.put(
			 * CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_EMAIL, ""); }
			 * 
			 * } else {
			 * json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_EMAIL,
			 * ""); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/********************************
	 * Facebook Functionality listeners
	 *********************************/

	@Override
	public void OnLoginSuccess(FacebookUserDataModel loginDetail) {
		Log.d("", "" + loginDetail);
	}

	@Override
	public void OnLoginError(String error) {
		Log.d("", "" + error);
	}

	@Override
	public void OnLogoutSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnLoginUserDetail(FacebookUserDataModel user) {
		Log.d("", "" + user);
		try {
			/*new ViewToastMsg(getActivity(), user.getFirstname() + " "
					+ user.getLastname() + " is now connected");*/

			/* add facebook logged in user data */
			CollectItSharedDataModel.getInstance().getUserDetailListFacebook()
					.clear();
			CollectItSharedDataModel.getInstance().getUserDetailListFacebook()
					.add(user);

			// call web service here for facebook login
			DialogProgressCustom.getInstance().startProgressDialog(context,
					true);
			new WebServiceAsyncHttpPostJson(
					context,
					CollectItServerConstants.WEBSERVICE_LOGIN_SOCIAL_SITES,
					CollectItConstants.LOGIN_SCREEN_ID_SOCIAL,
					this,
					createJsonForLoginThroughSocial(user.getId(),
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
		Log.d("", "" + response);

	}

	@Override
	public void OnSessionExpires() {
		Log.d("", "");

	}

	@Override
	public void OnFacebookError(String errorMessage, String errorType,
			int errorCode) {
		Log.d("", errorMessage);
	}

}
