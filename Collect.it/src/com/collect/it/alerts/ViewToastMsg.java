package com.collect.it.alerts;

import android.content.Context;
import android.widget.Toast;

/**
 * Functionality to view toast message
 */
public class ViewToastMsg {
	/**
	 * @param Activity
	 *            context
	 * @param message
	 *            to be displayed
	 */
	public ViewToastMsg(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}
