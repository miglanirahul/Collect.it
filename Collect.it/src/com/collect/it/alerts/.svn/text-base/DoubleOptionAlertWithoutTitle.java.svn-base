package com.collect.it.alerts;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Window;

import com.collect.it.HomeTabFragmentActivity;
import com.collect.it.interfaces.OnAlertButtonProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.utils.SignOut;

/**
 * This class displays the alert dialog with two buttons for selection
 */
public class DoubleOptionAlertWithoutTitle {

	/** Declare variables */

	private Activity mContext;

	private AlertDialog mAlertDialog;

	private OnAlertButtonProcess onAlertButtonListener;

	public static final int FINISH_ACTIVITY = 001;
	public static final int IMAGE_PICK = 002;
	public static final int SIGN_OUT = 003;
	public static final int ADD_MORE_ITEM = 004;
	public static final int DELETE_ITEM = 005;

	public static final int CAMERA_ACTIVITY_INTENT_KEY = 0010;
	public static final int SELECT_PICTURE_INTENT_KEY = 0020;

	/**
	 * Constructor Definition
	 * 
	 * @param Activity
	 *            object
	 * @param message
	 *            to be print
	 * @param button1
	 *            text
	 * @param button2
	 *            text
	 * @param activity
	 *            id to identify functionality of first button
	 */
	public DoubleOptionAlertWithoutTitle(final Activity cxt, String msg,
			String button1, String button2, final int activityId,
			OnAlertButtonProcess onAlertButtonListener) {

		this.onAlertButtonListener = onAlertButtonListener;

		// save context instance temporary
		mContext = cxt;

		// alert dialog functionality
		mAlertDialog = new AlertDialog.Builder(cxt).create();
		// hide title bar
		mAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// set the message
		mAlertDialog.setMessage(msg);
		// set button1 functionality
		mAlertDialog.setButton(button1, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				switch (activityId) {
				case FINISH_ACTIVITY:
					mContext.finish();
					break;
				case IMAGE_PICK:
					openCameraIntent();
					// close the alert dialog
					dialog.dismiss();
					break;
				case SIGN_OUT:
					new SignOut(mContext);
					// close the alert dialog
					dialog.dismiss();
					break;
				case ADD_MORE_ITEM:
					CollectItSharedDataModel.getInstance().getHomeItemList()
							.clear();
					HomeTabFragmentActivity.setCurrentTab(0);
					break;
				case DELETE_ITEM:

					break;
				default:
					// close the alert dialog
					dialog.dismiss();
					break;

				}

			}
		});
		mAlertDialog.setButton2(button2, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				switch (activityId) {
				case IMAGE_PICK:
					openGalleryIntent();
					break;
				case ADD_MORE_ITEM:
					if (DoubleOptionAlertWithoutTitle.this.onAlertButtonListener != null) {
						CollectItSharedDataModel.getInstance()
								.getHomeItemList().clear();
						DoubleOptionAlertWithoutTitle.this.onAlertButtonListener
								.onRightButton(activityId);
					}
					break;
				default:
					break;

				}

				// close the alert dialog
				dialog.dismiss();

			}
		});
		// show the alert dialog
		mAlertDialog.show();

	}

	/**
	 * Functionality to open device's camera
	 */
	void openCameraIntent() {

		Uri mUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), "Collectit.jpg"));
		/*
		 * File dir = Environment
		 * .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		 * 
		 * File output = new File(dir, "Collect.it_" +
		 * System.currentTimeMillis() + ".jpeg");
		 */
		Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		mIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
		// MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
		mContext.startActivityForResult(mIntent, CAMERA_ACTIVITY_INTENT_KEY);
	}

	/**
	 * Functionality to open device's camera gallery to pick an image
	 */
	void openGalleryIntent() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		mContext.startActivityForResult(
				Intent.createChooser(intent, "Select Picture"),
				SELECT_PICTURE_INTENT_KEY);
	}
}
