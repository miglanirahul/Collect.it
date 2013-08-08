package com.collect.it;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;

import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.DoubleOptionAlertWithoutTitle;
import com.collect.it.application.CollectItAbstractFragmentActivity;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.fragments.LoginHomeFragment;
import com.collect.it.fragments.SignUpFragment;
import com.collect.it.googleplus.GooglePlusConstants;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.utils.DecorderImage;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.UtilityMethods;
import com.facebook.Session;
import com.google.analytics.tracking.android.EasyTracker;

;

public class LoginSignupFragmentActivity extends
		CollectItAbstractFragmentActivity {

	/**
	 * Declare class variables
	 */
	private DecorderImage imageDecorder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStart()
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		EasyTracker.getInstance().activityStart(this);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// super.onSaveInstanceState(outState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.collect.it.application.CollectItAbstractFragmentActivity#onCreate
	 * (android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		imageDecorder = new DecorderImage(this);
	}

	@Override
	public void initialization() {
		setContentView(R.layout.activity_main_frag);

		// add login home fragment for this screen
		UtilityMethods.replaceFragment(new LoginHomeFragment(), this,
				R.id.main_frag_loginhome_frag, false, false, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		// stop Google analytics
		EasyTracker.getInstance().activityStop(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// add context to further use in the fragments
		CollectItSharedDataModel.getInstance()
				.setCurrentFragmentActivityContext(this);

		// hide setting icon on top bar
		UtilityMethods.setSettingIconVisibility(this, View.GONE);

		// hide keyboard
		UtilityMethods.hideKeyboard(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {

		UtilityMethods.closeCurrentScreen(this);

		// super.onBackPressed();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			// must be implemented, facebook login call back when the status
			// will be
			// OPENING, CANCELLED,
			// etc. 64206/0
			if (requestCode == 64206) {
				Session.getActiveSession().onActivityResult(this, requestCode,
						resultCode, data);
			}

			if (resultCode == RESULT_OK) {
				// Google plus login functionality
				if (requestCode == GooglePlusConstants.GOOGLE_REQUEST_CODE_RESOLVE_ERR) {
					// GooglePlusModel.getInstance().getmPlusClient().connect();
				}

				// Image pick functionality for the sign up screen initially
				imagePick(requestCode, data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Functionality to get image from camera/gallery and update UI accordingly
	 * 
	 * @param requestCode
	 *            value to match the related task
	 * @param data
	 *            intent data to get the image
	 */
	void imagePick(int requestCode, Intent data) {
		Bitmap mBitmap = null;
		try {

			// get device' height and width
			DisplayMetrics displayMatrics = CollectItSharedDataModel
					.getInstance().getDisplayMetrics();
			int imageHeight = 700;
			int imageWidth = 700;
			try {
				if (displayMatrics != null) {

					if (displayMatrics.widthPixels > 0) {
						imageWidth = displayMatrics.widthPixels;
						imageHeight = displayMatrics.widthPixels;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (requestCode == DoubleOptionAlertWithoutTitle.SELECT_PICTURE_INTENT_KEY
					&& null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA,
						MediaStore.Images.Media.ORIENTATION };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);

				int columnIndex1 = cursor.getColumnIndex(filePathColumn[1]);
				String orientation = cursor.getString(columnIndex1);

				cursor.close();
				if (picturePath == null) {
					DialogProgressCustom.getInstance().startProgressDialog(
							LoginSignupFragmentActivity.this, true);
					InputStream picture = getContentResolver().openInputStream(
							selectedImage);
					mBitmap = ImageUtils.decodeSampledBitmapFromResourceMemOpt(
							LoginSignupFragmentActivity.this, picture,
							imageWidth, imageHeight);
					DialogProgressCustom.getInstance().stopProgressDialog();
				} else {
					if (picturePath.startsWith("http")) {
						mBitmap = ImageUtils.decodeScaledBitmapFromUrl(
								LoginSignupFragmentActivity.this, picturePath,
								imageWidth, imageHeight);
					} else {
						mBitmap = ImageUtils.decodeScaledBitmapFromSdCard(
								picturePath, imageWidth, imageHeight);
					}

					mBitmap = ImageUtils.rotateBitmap(mBitmap, orientation);
				}
			}

			else if (requestCode == DoubleOptionAlertWithoutTitle.CAMERA_ACTIVITY_INTENT_KEY) {
				// Bundle b = data.getExtras();
				// mBitmap = (Bitmap) b.get("data");

				File cameraFile = new File(
						Environment.getExternalStorageDirectory(),
						"Collectit.jpg");

				Uri uri = Uri.parse(cameraFile.toString());

				if (cameraFile.getPath() != null) {
					File dir = new File(
							Environment.getExternalStorageDirectory()
									+ "/Collect.it");
					if (!dir.exists()) {
						dir.mkdirs();
					}
					String imageName = "Collectit_"
							+ String.valueOf(System.currentTimeMillis());
					File file = new File(dir, imageName + ".jpg");
					try {
						FileUtils.copyFile(new File(uri.toString()), file);
						MediaScannerConnection.scanFile(
								LoginSignupFragmentActivity.this,
								new String[] { file.getAbsolutePath() }, null,
								null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				/*
				 * Bitmap bitmap = ImageUtils.getBitmapFromDeviceImage(this,
				 * imageWidth, imageHeight, uri); if (bitmap != null) { mBitmap
				 * = bitmap; }
				 */
				/*
				 * mBitmap = ImageUtils.setPic(uri.getPath(), this, imageWidth,
				 * imageHeight);
				 */
				if (imageDecorder == null) {
					imageDecorder = new DecorderImage(this);
				}
				mBitmap = imageDecorder.decode(this, uri, imageWidth,
						imageHeight);

			}

			// finally update UI
			if (mBitmap != null) {
				SignUpFragment frag = (SignUpFragment) LoginSignupFragmentActivity.this
						.getSupportFragmentManager().findFragmentByTag(
								FragmentTagNames.SIGNUP.name());
				if (frag != null) {
					frag.updateUserImage(mBitmap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
