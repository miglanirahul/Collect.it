package com.collect.it;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;

import com.collect.it.application.CollectItAbstractActivity;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.utils.GetUserDetail;
import com.collect.it.utils.UploadData;
import com.crittercism.app.Crittercism;

/**
 * This activity used to display company's information for a time interval and
 * start the application home screen afterwards
 */
public class SplashActivity extends CollectItAbstractActivity {

	/**
	 * Declare class variables
	 */
	private final int DELAY_INTERVAL = 3 * 1000;

	/**
	 * Functionality of server response
	 */
	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		// TODO Auto-generated method stub

	}

	/* on server error */
	@Override
	public void setServerError(int id, String msg) {
		// TODO Auto-generated method stub

	}

	/**
	 * initialize components and variables
	 */
	@Override
	public void initialization() {
		setContentView(R.layout.activity_splash);

		// open next activity with time delay
		CountDownTimer();

		// find the screen's height/width
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		CollectItSharedDataModel.getInstance().setDisplayMetrics(dm);

		// initiate crash reporter
		Crittercism.init(getApplicationContext(), "51bf07594002052ede000008");
		// custom user name
		// Crittercism.setUsername("Collect.it");

	}

	/**
	 * Functionality to delay time and start with login view
	 */

	void CountDownTimer() {
		new CountDownTimer(DELAY_INTERVAL, DELAY_INTERVAL) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {

				SharedPreferences sharedPreference = CollectItSharedDataModel
						.getInstance().getPreferences(SplashActivity.this);

				if (sharedPreference
						.contains(CollectItConstants.SHARED_PREFERENCES_USED_ID)) {
					String userId = sharedPreference.getString(
							CollectItConstants.SHARED_PREFERENCES_USED_ID, "");
					if (userId != null && !userId.equals("")
							&& !userId.equalsIgnoreCase("null")) {
						CollectItSharedDataModel.getInstance()
								.setUserId(userId);
						// start the home screen
						startActivity(new Intent(SplashActivity.this,
								HomeTabFragmentActivity.class));

						// get the user details
						new GetUserDetail(SplashActivity.this, null, 0);
					} else {
						// start the login home screen
						startActivity(new Intent(SplashActivity.this,
								LoginSignupFragmentActivity.class));
					}
				} else {
					// start the login home screen
					startActivity(new Intent(SplashActivity.this,
							LoginSignupFragmentActivity.class));
				}
				finish();
			}
		}.start();
	}

}
