package com.collect.it.model;

import android.app.Activity;

import com.collect.it.R;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

/**
 * This class used to define methods and variables for the Google analytic
 * functionality
 * 
 * 
 * Google analytic ref : 1) https://www.google.com/analytics/web/?hl=en#realtime
 * /rt-screen/a41729480w71298711p73537615/%3Fmetric.type%3D2/ 2)
 * https://developers.google.com/analytics/devguides/collection/android/v2/
 */
public class GoogleAnalyticModel {
	/**
	 * Declare class variables
	 */

	private GoogleAnalytics mGaInstance;

	private static GoogleAnalyticModel instance;
	private static Tracker mGaTracker;

	/**
	 * Functionality to create class object as a singleton that not to create
	 * multiple instance of a class
	 * 
	 * @return GoogleAnalyticModel class object
	 */
	public static GoogleAnalyticModel getInstance() {
		if (instance == null) {
			instance = new GoogleAnalyticModel();
		}
		return instance;
	}

	/**
	 * Functionality to get tracker class object which also need to define
	 * singleton
	 * 
	 * @param context
	 *            Activity object
	 * 
	 * @return Tracker class object
	 */
	public Tracker getTracker(Activity context) {
		if (mGaTracker == null) {
			// Get the GoogleAnalytics singleton. Note that the SDK uses
			// the application context to avoid leaking the current context.
			mGaInstance = GoogleAnalytics.getInstance(context);

			// Use the GoogleAnalytics singleton to get a Tracker.
			mGaTracker = mGaInstance.getTracker(context.getResources()
					.getString(R.string.google_analytic_tracking_id)); // Placeholder
																		// tracking
																		// ID.

		}
		return mGaTracker;
	}

}
