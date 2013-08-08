package com.collect.it.googleplus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ShareCompat;
import android.util.Log;

import com.collect.it.utils.UtilityMethods;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.GooglePlusUtil;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.OnPersonLoadedListener;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;

/**
 * This class having methods that are need during the different operations for
 * Google plus through the application
 */
public class GooglePlusUtils implements ConnectionCallbacks,
		OnConnectionFailedListener {

	/**
	 * Declare class variables
	 */
	private static GooglePlusUtils instanceGooglePlus;

	private final String ME_GOOGLE = "me";

	private PlusClient mPlusClient;
	private Person personInfo;
	private Activity context;
	private OnGooglePlusProcess googlePlusProcessListener;

	private File pictureFile;

	/**
	 * Functionality to get singleton instance for GooglePlusUtils class so that
	 * the associated functions can be utilized
	 * 
	 * @param Activity
	 *            context
	 * 
	 * @return GooglePlusUtils class object
	 */
	public static GooglePlusUtils getInstance(Context context) {
		if (instanceGooglePlus == null) {
			instanceGooglePlus = new GooglePlusUtils();
		}
		return instanceGooglePlus;
	}

	/**
	 * Functionality to login for the Google account This function will navigate
	 * on the onConnected() method after the connection will established
	 * 
	 * @param Activity
	 *            context
	 * @param OnGooglePlusProcess
	 *            listener
	 * 
	 */
	public void loginGooglePlus(Activity context,
			OnGooglePlusProcess googlePlusProcessListener) {
		this.context = context;
		this.googlePlusProcessListener = googlePlusProcessListener;
		try {
			mPlusClient = new PlusClient.Builder(context, this, this)
					.setVisibleActivities(
							"http://schemas.google.com/AddActivity",
							"http://schemas.google.com/BuyActivity").build();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// GooglePlusModel.getInstance().setmPlusClient(mPlusClient);
		}
	}

	/**
	 * Functionality to stop Google account connectivity with the application.
	 * Generally used in onStop() of the activity
	 */
	public void stopGoogleAccountConnection() {
		try {
			mPlusClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to get Loggedin user information from Google plus account
	 * Ref : https://developers.google.com/+/mobile/android/people
	 * 
	 * @return Google plsu librarie's Person interface
	 */
	public Person getMyGooglePlusInfo() {
		personInfo = null;
		mPlusClient.loadPerson(new OnPersonLoadedListener() {

			@Override
			public void onPersonLoaded(ConnectionResult status, Person person) {
				if (status.getErrorCode() == ConnectionResult.SUCCESS) {
					personInfo = person;
				}
			}
		}, ME_GOOGLE);

		return personInfo;
	}

	/**
	 * Functionality to share on Google plus Ref:
	 * https://developers.google.com/+/mobile/android/share
	 * 
	 * @param Activity
	 *            object
	 */
	public void shareWithGooglePlus(Activity context) {
		final int errorCode = GooglePlusUtil.checkGooglePlusApp(context);
		if (errorCode == GooglePlusUtil.SUCCESS) {
			PlusShare.Builder builder = new PlusShare.Builder(context,
					mPlusClient);

			// Set call-to-action metadata.
			builder.addCallToAction("CREATE_ITEM", /**
			 * call-to-action button
			 * label
			 */
			Uri.parse("http://plus.google.com/pages/create"), /**
			 * call-to-action
			 * url (for desktop use)
			 */
			"/pages/create"/**
			 * call to action deep-link ID (for mobile use), 512
			 * characters or fewer
			 */
			);

			// Set the content url (for desktop use).
			builder.setContentUrl(Uri.parse("https://plus.google.com/pages/"));

			// Set the target deep-link ID (for mobile use).
			builder.setContentDeepLinkId("/pages/", null, null, null);

			// Set the share text.
			builder.setText("Create your Google+ Page too!");

			context.startActivityForResult(builder.getIntent(), 0);
		} else {
			// Prompt the user to install the Google+ app.
			GooglePlusUtil.getErrorDialog(errorCode, context, 0).show();
		}

		// simple share option
		/*
		 * Intent shareIntent = new PlusShare.Builder(this)
		 * .setType("text/plain") .setText("Welcome to the Google+ platform.")
		 * .setContentUrl(Uri.parse("https://developers.google.com/+/"))
		 * .getIntent();
		 * 
		 * startActivityForResult(shareIntent, 0);
		 */

		// Using a full URL identifier
		/*
		 * Intent shareIntent = new PlusShare.Builder(this)
		 * .setText("Check out: http://example.com/cheesecake/lemon")
		 * .setType("text/plain")
		 * .setContentUrl(Uri.parse("http://example.com/cheesecake/lemon"))
		 * .getIntent();
		 * 
		 * startActivityForResult(shareIntent, 0);
		 */

		// Using a URI path identifier
		/*
		 * Intent shareIntent = new PlusShare.Builder(this)
		 * .setText("Lemon Cheesecake recipe") .setType("text/plain")
		 * .setContentDeepLinkId("/cheesecake/lemon",
		 *//** Deep-link identifier */
		/*
		 * "Lemon Cheesecake recipe",
		 *//** Snippet title */
		/*
		 * "A tasty recipe for making lemon cheesecake.",
		 *//** Snippet description */
		/*
		 * Uri.parse("http://example.com/static/lemon_cheesecake.png"))
		 * .getIntent();
		 * 
		 * startActivityForResult(shareIntent, 0);
		 */

	}

	public void shareImageTextGplus(final Activity context,
			final String messageToPost, final String fileName,
			final Bitmap bitmap) {

		new AsyncTask<Void, Void, Void>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.AsyncTask#doInBackground(Params[])
			 */
			@Override
			protected Void doInBackground(Void... params) {
				try {
					/*
					 * File rootSdDirectory = Environment
					 * .getExternalStorageDirectory();
					 * 
					 * pictureFile = new File(rootSdDirectory,
					 * "attachment.jpg"); if (pictureFile.exists()) {
					 * pictureFile.delete(); } pictureFile.createNewFile();
					 * 
					 * FileOutputStream fos = new FileOutputStream(pictureFile);
					 * 
					 * URL url = new URL(
					 * "http://img.youtube.com/vi/AxeOPU6n1_M/0.jpg");
					 * HttpURLConnection connection = (HttpURLConnection) url
					 * .openConnection(); connection.setRequestMethod("GET");
					 * connection.setDoOutput(true); connection.connect();
					 * InputStream in = connection.getInputStream();
					 * 
					 * byte[] buffer = new byte[1024]; int size = 0; while
					 * ((size = in.read(buffer)) > 0) { fos.write(buffer, 0,
					 * size); } fos.close();
					 */

					pictureFile = UtilityMethods.createFileBitmap(context,
							bitmap, fileName);

				} catch (Exception e) {

					System.out.print(e);
					// e.printStackTrace();

				}
				return null;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				Uri pictureUri = Uri.fromFile(pictureFile);

				Intent shareIntent = ShareCompat.IntentBuilder.from(context)
						.setText(messageToPost).setType("image/jpeg")
						.setStream(pictureUri).getIntent()
						.setPackage("com.google.android.apps.plus");
				context.startActivity(shareIntent);
			}

		}.execute();

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
			try {
				result.startResolutionForResult(context,
						GooglePlusConstants.GOOGLE_REQUEST_CODE_RESOLVE_ERR);
			} catch (SendIntentException e) {
				mPlusClient.connect();
			}
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.d("", "" + connectionHint);
		// GooglePlusModel.getInstance().setmPlusClient(mPlusClient);
		// googlePlusProcessListener.onGooglePlusConnected(connectionHint);
	}

	@Override
	public void onDisconnected() {
		Log.d("", "");

	}

}
