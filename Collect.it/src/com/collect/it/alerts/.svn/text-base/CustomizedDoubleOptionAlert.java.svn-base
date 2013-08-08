package com.collect.it.alerts;

import com.collect.it.HomeTabFragmentActivity;
import com.collect.it.R;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.fragments.AddItemFragment;
import com.collect.it.fragments.EditProfileFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.Theme;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class displays the alert dialog with two buttons for selection
 */
public class CustomizedDoubleOptionAlert implements OnClickListener {

	/** Declare variables */

	private Dialog mDialog;
	private final int mActivityTaskId;
	private FragmentActivity mContext;

	/**
	 * Constructor Definition
	 * 
	 * @param Activity
	 *            object
	 * @param activity
	 *            task id to identify functionality of components
	 */
	public CustomizedDoubleOptionAlert(final FragmentActivity cxt, int taskId) {
		mActivityTaskId = taskId;
		mContext = cxt;
		// alert dialog functionality
		mDialog = new Dialog(mContext, R.style.Theme_Dialog_Double_Alert);
		// set layout
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.custom_alert_two_button, null);
		mDialog.setContentView(view);

		mDialog.setCanceledOnTouchOutside(false);

		// initiate components
		TextView mTextView = (TextView) view
				.findViewById(R.id.custom_alert_two_btn_txt);
		Button mButton1 = (Button) view
				.findViewById(R.id.custom_alert_two_btn1);
		Button mButton2 = (Button) view
				.findViewById(R.id.custom_alert_two_btn2);

		// set text and buttons according to task id
		switch (mActivityTaskId) {
		case CollectItConstants.HOME_SCREEN_ID:

			break;
		default:
			break;
		}

		// set click event enable
		mButton1.setOnClickListener(this);
		mButton2.setOnClickListener(this);

		// show the alert dialog
		mDialog.show();

	}

	/**
	 * Functionality of click events
	 */

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/* Button 1 */
		case R.id.custom_alert_two_btn1:
			break;

		/* Button2 */
		case R.id.custom_alert_two_btn2:
			HomeTabFragmentActivity.setCurrentTab(2);
			break;
		default:
			break;

		}
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}
}
