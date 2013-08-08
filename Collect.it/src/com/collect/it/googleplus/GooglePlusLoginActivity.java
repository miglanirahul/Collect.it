package com.collect.it.googleplus;

import com.collect.it.R;
import com.collect.it.R.string;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItGooglePlusAbstractActivity;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.utils.UtilityMethods;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * This activity used to login on Google account through application
 */
public class GooglePlusLoginActivity extends
		CollectItGooglePlusAbstractActivity {
	private static final String TAG = "GooglePlusLoginActivity";
	private static final int REQUEST_CODE_RESOLVE_ERR = 99999;

	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;

	/**
	 * Broadcast key that will detect the google plus login successful with
	 * logged user details
	 */
	public static final String GOOGLE_PLUS_LOGIN_BROADCAST_KEY = "Google plus login broadcast key";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Progress bar to be displayed if the connection failure is not
		// resolved.
		DialogProgressCustom.getInstance().startProgressDialog(this, true);

		try {
			mPlusClient = new PlusClient.Builder(this, this, this)
					.setVisibleActivities(
							"http://schemas.google.com/AddActivity",
							"http://schemas.google.com/BuyActivity").build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		mPlusClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mPlusClient.disconnect();

		// logout from google plus
		/*
		 * if (mPlusClient.isConnected()) { mPlusClient.clearDefaultAccount();
		 * mPlusClient.disconnect(); mPlusClient.connect(); }
		 */

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// stop progress dialog
		DialogProgressCustom.getInstance().stopProgressDialog();
	}

	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServerError(int id, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
			try {
				result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
			} catch (SendIntentException e) {
				mPlusClient.connect();
			}
		}
		// Save the result and resolve the connection failure upon a user click.
		mConnectionResult = result;
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == REQUEST_CODE_RESOLVE_ERR
				&& responseCode == RESULT_OK) {
			mConnectionResult = null;
			mPlusClient.connect();
		} else {
			new ViewToastMsg(this, getResources().getString(
					R.string.connection_error));

			// set the value to finish the related tasks
			CollectItSharedDataModel.getInstance().setSignupThroughId(
					CollectItConstants.SIGNUP_GOOGLEPLUS_ACTIVITY_FINISH_ERROR);

			// close this screen
			closeAndSendBroadcast(CollectItConstants.SIGNUP_GOOGLEPLUS_ACTIVITY_FINISH_ERROR);
		}

	}

	@Override
	public void onConnected(Bundle bundle) {
		String accountName = mPlusClient.getAccountName();
		/*Toast.makeText(this, accountName + " is connected.", Toast.LENGTH_LONG)
				.show();*/
		mPlusClient.loadPerson(this, "me");
	}

	@Override
	public void onDisconnected() {
		Log.d(TAG, "disconnected");
	}

	@Override
	public void initialization() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGoogleAccountConnected(PlusClient plusClient) {
		Log.d("", "" + plusClient);
	}

	@Override
	public void onPersonLoaded(ConnectionResult status, Person person) {
		if (status.getErrorCode() == ConnectionResult.SUCCESS) {
			Log.d(TAG, "Display Name: " + person.getDisplayName());

			GooglePlusModel.getInstance().setmPerson(person);
		}

		// save detail in collect.it application
		CollectItSharedDataModel.getInstance().getUserDetailListGooglePlus()
				.clear();
		CollectItSharedDataModel.getInstance().getUserDetailListGooglePlus()
				.add(person);

		// set the value to finish the related tasks
		CollectItSharedDataModel.getInstance().setSignupThroughId(
				CollectItConstants.SIGNUP_GOOGLEPLUS_ACTIVITY_FINISH);

		// close this screen
		closeAndSendBroadcast(CollectItConstants.SIGNUP_GOOGLEPLUS_ACTIVITY_FINISH);

	}

	/**
	 * Functionality to close this activity and send broadcast for the
	 * foreground activity to further process
	 * 
	 * @param key
	 *            that need to pass through intent that will detect either the
	 *            Google+ login success or login failed
	 */

	void closeAndSendBroadcast(int key) {
		finish();

		// send broadcast for the activities
		sendBroadcast(new Intent(GOOGLE_PLUS_LOGIN_BROADCAST_KEY).putExtra(
				CollectItConstants.BUNDLE_GOOGLE_PLUS_LOGIN_KEY, key));
	}
}
