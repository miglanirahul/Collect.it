package com.collect.it.application;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * This Fragment activity would be the base activity for Collect.it
 * FragmentActivities to define the common functionality
 * 
 * 1) set the screen orientation to portrait 2) remove the title bar from the
 * screen 3) initialize variables in onCreate() FragmentActivity method, using
 * initialization()
 * 
 * 
 */
public abstract class CollectItAbstractFragmentActivity extends
		FragmentActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set activity in portrait mode
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// set the screen without title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// initiate variables/components for the screen/class
		initialization();
	}

	/**
	 * Abstract method to initialize class variables/components for onCreate()
	 */
	public abstract void initialization();

}
