package com.collect.it.alerts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.collect.it.R;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.fragments.HomeFragment;
import com.collect.it.fragments.SignUpFragment;
import com.collect.it.utils.UtilityMethods;

/**
 * This class used to display Alert box without title
 */
public class SingleOptionAlertWithoutTitle {
	/**
	 * Declare variables
	 */

	public static final int CLOSE_CURRENT_FRAGMENT = 100;
	public static final int START_SIGNUP_FRAGMENT = 101;
	public static final int DELETE_ITEM_FRAGMENT = 102;

	/**
	 * Constructor Definition
	 * 
	 * @param Activity
	 *            object
	 * @param messages
	 *            to be displayed
	 * @param text
	 *            for the button to displayed
	 * @param activity
	 *            /task id to match the button functionality
	 */
	public SingleOptionAlertWithoutTitle(final FragmentActivity context,
			String message, String buttonText, final int taskID) {
		// Log.i("VIAMO","in single option alert class");
		// alert dialog functionality
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		// hide title bar
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// set the message
		alertDialog.setMessage(message);
		// set button1 functionality
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, buttonText, // setButton(buttonText,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						switch (taskID) {
						case CLOSE_CURRENT_FRAGMENT:
							UtilityMethods.closeCurrentScreen(context);
							break;
						case START_SIGNUP_FRAGMENT:
							// create bundle
							Bundle bundle = new Bundle();
							bundle.putString(
									CollectItConstants.BUNDLE_SIGNUP_KEY,
									CollectItConstants.BUNDLE_SIGNUP_THROUGH_FB_G);

							// replace the login home fragment with sign up
							// fragment for main
							// fragment activity
							UtilityMethods.replaceFragment(
									new SignUpFragment(), context,
									R.id.main_frag_loginhome_frag, true, true,
									FragmentTagNames.SIGNUP.name(), bundle);
							break;
						case DELETE_ITEM_FRAGMENT:
							//clear back stack
							context.getSupportFragmentManager().popBackStack(
									null,
									FragmentManager.POP_BACK_STACK_INCLUSIVE);
							//start home screen on tab
							UtilityMethods.replaceFragment(new HomeFragment(),
									context, R.id.tab_content, true, false,
									FragmentTagNames.HOME_TAB.name(), null);
							break;
						default:
							// nothing to do
							break;
						}
						// close dialog
						dialog.dismiss();

					}
				});
		// show the alert dialog
		alertDialog.show();
	}
}
