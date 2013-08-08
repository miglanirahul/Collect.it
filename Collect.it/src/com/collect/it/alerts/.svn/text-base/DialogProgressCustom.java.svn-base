package com.collect.it.alerts;

import com.collect.it.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class DialogProgressCustom {
	private static Dialog mDialog = null;
	private static DialogProgressCustom customDialog = null;

	public DialogProgressCustom() {
		// do Nothing
	}

	public static DialogProgressCustom getInstance() {
		if (customDialog == null) {
			customDialog = new DialogProgressCustom();
		}
		return customDialog;
	}

	/**
	 * Functionality to display dialog
	 * 
	 * @param Activity
	 *            context
	 * @param boolean to set cancel enable/disable for this dialog
	 */
	public void startProgressDialog(Context mContext, boolean cancellable) {
		stopProgressDialog();
		try {
			mDialog = new Dialog(mContext,
					android.R.style.Theme_Translucent_NoTitleBar);

			LayoutInflater mInflater = LayoutInflater.from(mContext);
			View layout = mInflater.inflate(R.layout.dialog_progress_custom,
					null);
			mDialog.setContentView(layout);
			mDialog.setCancelable(cancellable);

			mDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to stop progress dialog if running
	 */
	public void stopProgressDialog() {

		if (mDialog != null)
			mDialog.dismiss();
	}

}
