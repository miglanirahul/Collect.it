package com.collect.it.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This broadcast class is used to detect the Internet connectivity for the
 * application to perform related tasks
 * 
 * a) Upload added item images on to server if available onto local database
 */
public class InternetConnectivityBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		boolean isWifiConnected = false;
		boolean isMobileInternetConnected = false;

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					isWifiConnected = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					isMobileInternetConnected = true;
		}

		if (isWifiConnected || isMobileInternetConnected) {
			/**
			 * upload image data for the added items that have not been uploaded
			 * due to some errors
			 */
			new UploadData(context);
		}
	}
}
