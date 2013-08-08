package com.collect.it.fragments;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.collect.it.R;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.DoubleOptionAlertWithoutTitle;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.interfaces.OnUserDetailProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.model.UserDataModel;
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;
import com.collect.it.utils.GetUserDetail;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.UtilityMethods;
import com.google.android.gms.internal.em;

/**
 * This fragment class is used to define the functionality to edit user profile
 */
public class EditProfileFragment extends CollectItAbstractFragment implements
		OnUserDetailProcess {
	/**
	 * Declare class variables
	 */
	private ImageView userImageView;
	private EditText fNameEditText, lNameEditText, emailEditText,
			genderEditText, aboutmeEditText, nickNameEditText;
	private ProgressBar imageProgressBar;

	private FragmentActivity context;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_edit_profile, null);

		// initiate components
		view.findViewById(R.id.edit_profile_top_frame);
		userImageView = (ImageView) view.findViewById(R.id.edit_profile_img);

		view.findViewById(R.id.edit_profile_img_txt).setOnClickListener(this);

		fNameEditText = (EditText) view
				.findViewById(R.id.edit_profile_fname_edit);
		lNameEditText = (EditText) view
				.findViewById(R.id.edit_profile_lname_edit);
		emailEditText = (EditText) view
				.findViewById(R.id.edit_profile_email_edit);
		genderEditText = (EditText) view
				.findViewById(R.id.edit_profile_gender_edit);
		aboutmeEditText = (EditText) view
				.findViewById(R.id.edit_profile_aboutme_edit);
		nickNameEditText = (EditText) view
				.findViewById(R.id.edit_profile_nickname_edit);

		imageProgressBar = (ProgressBar) view
				.findViewById(R.id.edit_profile_imgprogressBar);

		view.findViewById(R.id.edit_profile_change_pass_rel)
				.setOnClickListener(this);
		view.findViewById(R.id.edit_profile_save_btn).setOnClickListener(this);

		try {
			// check if user details are not available then fetch user details
			UserDataModel userDetails = null;
			if (CollectItSharedDataModel.getInstance().getUserDetailList() != null
					&& CollectItSharedDataModel.getInstance()
							.getUserDetailList().size() > 0) {
				userDetails = CollectItSharedDataModel.getInstance()
						.getUserDetailList().get(0);
			}
			if (null == userDetails) {
				// start progress dialog
				DialogProgressCustom.getInstance().startProgressDialog(context,
						true);
				new GetUserDetail(context, this,
						CollectItConstants.EDIT_PROFILE_SCREEN_ID);
			} else {
				// display user details
				setValues();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return view;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		// hide soft keyboard if displayed
		UtilityMethods.hideKeyboard(context);
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
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		/* Image edit text */
		case R.id.edit_profile_img_txt:

			new DoubleOptionAlertWithoutTitle(context, getResources()
					.getString(R.string.image_pick_msg), getResources()
					.getString(R.string.camera), getResources().getString(
					R.string.gallery),
					DoubleOptionAlertWithoutTitle.IMAGE_PICK, null);

			break;
		/* Change password relative layout */
		case R.id.edit_profile_change_pass_rel:
			UtilityMethods.replaceFragment(new ChangePasswordFragment(),
					context, R.id.tab_content, true, true,
					FragmentTagNames.CHANGE_PASSWORD.name(), null);
			break;
		/* Save button */
		case R.id.edit_profile_save_btn:
			saveButtonFunc();
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
		try {
			// save parent activity context into class variable
			context = CollectItSharedDataModel.getInstance()
					.getCurrentFragmentActivityContext();

			// Google analytics
			// Send a screen view when the Activity is displayed to the user.
			GoogleAnalyticModel
					.getInstance()
					.getTracker(context)
					.sendView(
							getResources().getString(
									R.string.ga_edit_profile_screen));

		} catch (Exception e) {
			e.printStackTrace();
		}

		// hide setting icon on top bar
		UtilityMethods.setSettingIconVisibility(context, View.GONE);

		// show back button
		UtilityMethods.setBackButtonVisibility(context, View.VISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.EDIT_PROFILE_SCREEN_ID);

		// hide keyboard
		UtilityMethods.hideKeyboard(context);

	}

	/**
	 * Functionality of save button
	 */
	void saveButtonFunc() {
		try {
			String fNameString = fNameEditText.getText().toString().trim();
			String lNameString = lNameEditText.getText().toString().trim();
			String emailString = emailEditText.getText().toString().trim();
			String genderString = genderEditText.getText().toString().trim();
			String aboutString = aboutmeEditText.getText().toString().trim();
			String nickNameString = nickNameEditText.getText().toString()
					.trim();

			if (lNameString == null || lNameString.equalsIgnoreCase("null")) {
				lNameString = "";
			}

			if (aboutString == null || aboutString.equalsIgnoreCase("null")) {
				aboutString = "";
			}

			if (nickNameString == null
					|| nickNameString.equalsIgnoreCase("null")) {
				nickNameString = "";
			}

			/* if first name is entered by user */
			if (fNameString != null && !fNameString.equals("")
					&& !fNameString.equalsIgnoreCase("null")) {
				/* if gender is entered by user */
				/*
				 * if (genderString != null && !genderString.equals("") &&
				 * !genderString.equalsIgnoreCase("null")) {
				 */
				/* if email is entered by user */
				if (emailString != null && !emailString.equals("")
						&& !emailString.equalsIgnoreCase("null")) {
					/* if email is entered by user */
					if (UtilityMethods.checkForValidEmail(emailString)) {
						// finally hit the server for user signup
						// start progress dialog
						DialogProgressCustom.getInstance().startProgressDialog(
								context, true);
						new WebServiceAsyncHttpPostJson(
								context,
								CollectItServerConstants.WEBSERVICE_EDIT_PROFILE,
								CollectItConstants.EDIT_PROFILE_SCREEN_ID,
								this, createEditProfileJson(fNameString,
										lNameString, emailString, genderString,
										aboutString, nickNameString)).execute();

					} else {// valid email message
						new SingleOptionAlertWithoutTitle(context,
								getResources().getString(
										R.string.enter_email_valid),
								getResources().getString(R.string.ok), 0);
					}
				} else {// email message
					new SingleOptionAlertWithoutTitle(context, getResources()
							.getString(R.string.enter_email), getResources()
							.getString(R.string.ok), 0);
				}
				/*
				 * } else {// gender message new
				 * SingleOptionAlertWithoutTitle(context, getResources()
				 * .getString(R.string.enter_gender), getResources()
				 * .getString(R.string.ok), 0); }
				 */

			} else {// first name message
				new SingleOptionAlertWithoutTitle(context, getResources()
						.getString(R.string.enter_fname), getResources()
						.getString(R.string.ok), 0);
			}
		} catch (Exception e) {
			e.printStackTrace();

			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();
		}
	}

	/**
	 * Functionality to create json packet for the server parameters
	 * 
	 * @param fNameString
	 *            first name
	 * @param lNameString
	 *            last name
	 * @param emailString
	 *            email
	 * @param genderString
	 *            gender
	 * @param aboutString
	 *            about me
	 * @param nickNameString
	 *            nick name
	 */
	JSONObject createEditProfileJson(String fNameString, String lNameString,
			String emailString, String genderString, String aboutString,
			String nickNameString) {
		JSONObject json = new JSONObject();
		try {
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_FNAME,
					fNameString);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_LNAME,
					lNameString);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_EMAIL,
					emailString);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_ABOUTME,
					aboutString);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_GENDER,
					genderString);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID,
					CollectItSharedDataModel.getInstance().getUserDetailList()
							.get(0).getUserId());
			json.put(CollectItServerConstants.WEBSERVICE_KEY_FACEBOOK_ID,
					CollectItSharedDataModel.getInstance().getUserDetailList()
							.get(0).getfbId());
			json.put(CollectItServerConstants.WEBSERVICE_KEY_GOOGLEPLUS_ID,
					CollectItSharedDataModel.getInstance().getUserDetailList()
							.get(0).getgId());
			json.put(
					CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_IMAGE,
					ImageUtils.getBase64String(ImageUtils
							.getByteArrayFromBitmap(((BitmapDrawable) userImageView
									.getDrawable()).getBitmap())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
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
					parseServerResponse(response);
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
					if (json.has("msg")) {
						String messageString = json.getString("msg");
						CollectItSharedDataModel.getInstance()
								.getHomeItemList().clear();
						new SingleOptionAlertWithoutTitle(
								context,
								messageString,
								getResources().getString(R.string.ok),
								SingleOptionAlertWithoutTitle.CLOSE_CURRENT_FRAGMENT);

						// update user details for local access on the
						// background process
						new GetUserDetail(context, null, 0);
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

	/**
	 * Method to listen user data fetched or not
	 */
	@Override
	public void onUserDetailStoredSuccessfully(int taskId) {
		try {
			// after getting user details
			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();

			// display information
			setValues();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUserDetailError(String errorMsg) {
		new SingleOptionAlertWithoutTitle(context, errorMsg, getResources()
				.getString(R.string.ok), 0);
	}

	/**
	 * Functionality to display user records on the respective fields
	 */
	void setValues() {
		UserDataModel userDetails = CollectItSharedDataModel.getInstance()
				.getUserDetailList().get(0);

		fNameEditText.setText(userDetails.getfName());
		lNameEditText.setText(userDetails.getlName());
		emailEditText.setText(userDetails.getEmail());
		genderEditText.setText(userDetails.getGender());
		aboutmeEditText.setText(userDetails.getAbout());

		// set user name
		nickNameEditText.setText(userDetails.getUserName());
		nickNameEditText.setEnabled(false);

		ImageUtils.getInstance(context).setImageUrlToView(
				userDetails.getimageUrl(), userImageView, imageProgressBar,
				R.drawable.signup_image_placeholder, false, true);
	}

	/**
	 * Functionality to update user image
	 */
	public void updateUserImage(Bitmap bitmap) {
		if (bitmap != null) {
			ImageUtils.getInstance(context).setImageBitmapToView(bitmap,
					userImageView, imageProgressBar,
					R.drawable.signup_image_placeholder, false, true);

		}
	}

}
