package com.collect.it.fragments;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.collect.it.R;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.utils.UtilityMethods;

/**
 * This fragment used to interact with notifications
 */
public class NotificationFragment extends CollectItAbstractFragment {
	/**
	 * Declare class variables
	 */
	private FragmentActivity context;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_notification, null);
		return view;
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

		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		// Google analytics
		// Send a screen view when the Activity is displayed to the user.
		GoogleAnalyticModel
				.getInstance()
				.getTracker(context)
				.sendView(
						getResources().getString(
								R.string.ga_notification_screen));

		// hide setting icon on top bar
		UtilityMethods.setSettingIconVisibility(context, View.GONE);

		// hide back button
		UtilityMethods.setBackButtonVisibility(context, View.INVISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.NOTIFICATION_SCREEN_ID);
	}
}
