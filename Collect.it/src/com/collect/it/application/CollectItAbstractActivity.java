package com.collect.it.application;

import com.collect.it.interfaces.OnWebServiceProcess;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

/**
 * This activity used as a common base activity that can be used to inherit
 * common functionality for the Activity
 * 
 * 1) set portrait orientation for the activity 2) hide the title bar that used
 * to display application name 3) initialize variables in onCreate() Activity
 * method using initialization()
 * 
 * @implements 1) onClick listener for views, 2) onWebServiceProcess for server
 *             hit/response
 */
public abstract class CollectItAbstractActivity extends Activity implements
		// click event listener
		OnClickListener,
		// web service call listener
		OnWebServiceProcess {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set activity in portrait mode
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// set the screen without title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		initialization();
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

	/**
	 * Click events for activity
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	/**
	 * This function is used to initialize components and variables that is used
	 * in onCreate() in the associated activity
	 */
	public abstract void initialization();

}
