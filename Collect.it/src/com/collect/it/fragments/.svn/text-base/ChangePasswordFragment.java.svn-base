package com.collect.it.fragments;

import org.json.JSONObject;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.collect.it.R;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;
import com.collect.it.utils.UtilityMethods;
import com.google.android.gms.internal.cu;

/**
 * This fragment class is used for the change password functionality into the
 * application
 */
public class ChangePasswordFragment extends CollectItAbstractFragment {
	/**
	 * Declare class variables
	 */
	private EditText userNameEditText, currentPasswordEditText,
			newPasswordEditText;

	private FragmentActivity context;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_change_password, null);

		// initiate components
		userNameEditText = (EditText) view
				.findViewById(R.id.change_password_username_edit);
		currentPasswordEditText = (EditText) view
				.findViewById(R.id.change_password_old_password_edit);
		newPasswordEditText = (EditText) view
				.findViewById(R.id.change_password_new_password_edit);

		currentPasswordEditText.setTypeface(Typeface.DEFAULT);
		currentPasswordEditText
				.setTransformationMethod(new PasswordTransformationMethod());

		newPasswordEditText.setTypeface(Typeface.DEFAULT);
		newPasswordEditText
				.setTransformationMethod(new PasswordTransformationMethod());

		view.findViewById(R.id.change_password_btn).setOnClickListener(this);

		// set user name
		try {
			userNameEditText.setText(CollectItSharedDataModel.getInstance()
					.getUserDetailList().get(0).getUserName());
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
		GoogleAnalyticModel
				.getInstance()
				.getTracker(context)
				.sendView(
						getResources().getString(
								R.string.ga_change_password_screen));

		// hide setting icon on top bar
		UtilityMethods.setSettingIconVisibility(context, View.GONE);

		// show back button
		UtilityMethods.setBackButtonVisibility(context, View.VISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.CHANGE_PASSWORD_SCREEN_ID);

		// hide keyboard
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
		case R.id.change_password_btn:
			submitButtonFunc();
			break;
		default:
			// nothing to do
			break;
		}
	}

	/**
	 * Functionality of submit button
	 */
	void submitButtonFunc() {
		String currentPassword = currentPasswordEditText.getText().toString()
				.trim();
		String newPassword = newPasswordEditText.getText().toString().trim();

		if (currentPassword != null && !currentPassword.equals("")
				&& !currentPassword.equalsIgnoreCase("null")) {
			if (newPassword != null && !newPassword.equals("")
					&& !newPassword.equalsIgnoreCase("null")) {
				if (isPasswordMatchedMinimumCharacterLength(newPassword)) {
					if (isPasswordMatchedMinimumCharacterLength(currentPassword)) {
						// call web service here for face book login
						DialogProgressCustom.getInstance().startProgressDialog(
								context, true);
						new WebServiceAsyncHttpPostJson(
								context,
								CollectItServerConstants.WEBSERVICE_CHANGE_PASSWORD,
								CollectItConstants.CHANGE_PASSWORD_SCREEN_ID,
								this, createChangePasswordJson(currentPassword,
										newPassword)).execute();
					} else {
						new SingleOptionAlertWithoutTitle(context,
								getResources().getString(
										R.string.enter_old_min_password),
								getResources().getString(R.string.ok), 0);
					}
				} else {
					new SingleOptionAlertWithoutTitle(context, getResources()
							.getString(R.string.enter_new_min_password),
							getResources().getString(R.string.ok), 0);
				}
			} else {
				new SingleOptionAlertWithoutTitle(context, getResources()
						.getString(R.string.enter_new_password), getResources()
						.getString(R.string.ok), 0);
			}
		} else {
			new SingleOptionAlertWithoutTitle(context, getResources()
					.getString(R.string.enter_old_password), getResources()
					.getString(R.string.ok), 0);
		}

	}

	/**
	 * Functionality to create change password json packet for server parameters
	 * 
	 * @param currentPassword
	 *            users current password
	 * @param newPassword
	 *            users new password
	 */
	JSONObject createChangePasswordJson(String currentPassword,
			String newPassword) {
		JSONObject json = new JSONObject();
		try {
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID,
					CollectItSharedDataModel.getInstance().getUserDetailList()
							.get(0).getUserId());
			json.put(CollectItServerConstants.WEBSERVICE_KEY_CURRENT_PASSWORD,
					currentPassword);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_NEW_PASSWORD,
					newPassword);
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
					new ViewToastMsg(context, context.getResources().getString(
							R.string.connection_error_collectit));
				}
			} else if (exception.equalsIgnoreCase(context.getResources()
					.getString(R.string.connection_error_internet))) {
				new ViewToastMsg(context, exception);
			} else {
				new ViewToastMsg(context, context.getResources().getString(
						R.string.connection_error));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();
		}
	}

	/**
	 * Functionality to parse server response
	 * 
	 * @param response
	 *            server response string
	 */
	private void parseServerResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String responseString = "";
			if (json.has("response")) {
				responseString = json.getString("response");

				if (responseString.equalsIgnoreCase("true")) {
					if (json.has("msg")) {
						String messageString = json.getString("msg");
						new SingleOptionAlertWithoutTitle(
								context,
								messageString,
								getResources().getString(R.string.ok),
								SingleOptionAlertWithoutTitle.CLOSE_CURRENT_FRAGMENT);
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
	 * Functionality to find that user has entered the password with the minimum
	 * length of characters
	 * 
	 * @param password
	 *            string
	 * 
	 * @return true if password match the criteria of minimum length
	 */
	private boolean isPasswordMatchedMinimumCharacterLength(
			String passwordString) {

		return passwordString.length() >= CollectItConstants.PASSWORD_MIN_CHARACTER_LENGTH;
	}

}
