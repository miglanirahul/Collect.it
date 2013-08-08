package com.collect.it.application;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.collect.it.constants.CollectItConstants;
import com.collect.it.googleplus.GooglePlusConstants;
import com.collect.it.interfaces.OnWebServiceProcess;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.GooglePlusUtil;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.OnPersonLoadedListener;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;

/**
 * This activity used as a common base activity that can be used to inherit
 * common functionality for the Activity
 * 
 * 1) set portrait orientation for the activity 2) hide the title bar that used
 * to display application name 3) initialize variables in onCreate() Activity
 * method using initialization() 4) loginGooglePlus() to login in google plus
 * account 5) onGoogleAccountConnected() to get response after Google plus login
 * 
 * @implements 1) onClick listener for views, 2) onWebServiceProcess for server
 *             hit/response, 3) ConnectionCallbacks for Google plus connection
 *             caLL BACK, 4) OnConnectionFailedListener for Google plus
 *             connection failed
 */
public abstract class CollectItGooglePlusAbstractActivity extends Activity
		implements
		// click event listener
		OnClickListener,
		// web service call listener
		OnWebServiceProcess,
		// Google account connection listener
		ConnectionCallbacks,
		// on Google connection failed listener
		OnConnectionFailedListener,
		// logged in user profile listener
		PlusClient.OnPersonLoadedListener{

	/**
	 * Declare class variables
	 */
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;

	// public static String ACCEPT,
	// ACCEPT_GIFT,
	// ADD,
	// ADD_FRIEND,
	// ADD_ME,
	// ADD_TO_CALENDAR,
	// ADD_TO_CART,
	// ADD_TO_FAVORITES,
	// ADD_TO_QUEUE,
	// ADD_TO_WISH_LIST,
	// ANSWER,
	// ANSWER_QUIZ,
	// APPLY,
	// ASK,
	// ATTACK,
	// BEAT,
	// BID,
	// BOOK,
	// BOOKMARK,
	// BROWSE,
	// BUY,
	// CAPTURE,
	// CHALLENGE,
	// CHANGE,
	// CHAT,
	// CHECKIN,
	// COLLECT,
	// COMMENT,
	// COMPARE,
	// COMPLAIN,
	// CONFIRM,
	// CONNECT,
	// CONTRIBUTE,
	// COOK,
	// CREATE,
	// DEFEND,
	// DINE,
	// DISCOVER,
	// DISCUSS,
	// DONATE,
	// DOWNLOAD,
	// EARN,
	// EAT,
	// EXPLAIN,
	// FIND,
	// FIND_A_TABLE,
	// FOLLOW,
	// GET,
	// GIFT,
	// GIVE,
	// GO,
	// HELP,
	// IDENTIFY,
	// INSTALL,
	// INSTALL_APP,
	// INTRODUCE,
	// INVITE,
	// JOIN,
	// JOIN_ME,
	// LEARN,
	// LEARN_MORE,
	// LISTEN,
	// MAKE,
	// MATCH,
	// MESSAGE,
	// OPEN,
	// OPEN_APP,
	// OWN,
	// PAY,
	// PIN,
	// PIN_IT,
	// PLAN,
	// PLAY,
	// PURCHASE,
	// RATE,
	// READ,
	// READ_MORE,
	// RECOMMEND,
	// RECORD,
	// REDEEM,
	// REGISTER,
	// REPLY,
	// RESERVE,
	// REVIEW,
	// RSVP,
	// SAVE,
	// SAVE_OFFER,
	// SEE_DEMO,
	// SELL,
	// SEND,
	// SIGN_IN,
	// SIGN_UP,
	// START,
	// STOP,
	// SUBSCRIBE,
	// TAKE_QUIZ,
	// TAKE_TEST,
	// TRY_IT,
	// UPVOTE,
	// USE,
	// VIEW,
	// VIEW_ITEM,
	// VIEW_MENU,
	// VIEW_PROFILE,
	// VISIT,
	// VOTE,
	// WANT,
	// WANT_TO_SEE;

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

	/***********************************
	 * Google account login functionality
	 ************************************/

	/**
	 * This abstract function is used after the successful connection with
	 * Google account in the app. Use this function after the successful login
	 * in the activities to do further process
	 * 
	 * @param GooglePlus
	 *            client
	 */
	public abstract void onGoogleAccountConnected(PlusClient plusClient);

	public void loginGooglePlus() {
		mPlusClient = new PlusClient.Builder(this, this, this)
				.setVisibleActivities("http://schemas.google.com/AddActivity",
						"http://schemas.google.com/BuyActivity").build();
	}

	public void stopGoogleAccountConnection() {
		mPlusClient.disconnect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
			try {
				result.startResolutionForResult(this, GooglePlusConstants.GOOGLE_REQUEST_CODE_RESOLVE_ERR);
			} catch (SendIntentException e) {
				mPlusClient.connect();
			}
		}
		// Save the result and resolve the connection failure upon a user click.
		mConnectionResult = result;
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == GooglePlusConstants.GOOGLE_REQUEST_CODE_RESOLVE_ERR
				&& responseCode == RESULT_OK) {
			mConnectionResult = null;
			mPlusClient.connect();
		}
	}

	/**
	 * if Google account successful logged in
	 */
	@Override
	public void onConnected(Bundle bundle) {
		onGoogleAccountConnected(mPlusClient);
	}

	/**
	 * Functionality to share on Google plus Ref:
	 * https://developers.google.com/+/mobile/android/share
	 */
	public void shareWithGooglePlus() {
		final int errorCode = GooglePlusUtil.checkGooglePlusApp(this);
		if (errorCode == GooglePlusUtil.SUCCESS) {
			PlusShare.Builder builder = new PlusShare.Builder(this, mPlusClient);

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

			startActivityForResult(builder.getIntent(), 0);
		} else {
			// Prompt the user to install the Google+ app.
			GooglePlusUtil.getErrorDialog(errorCode, this, 0).show();
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

	/**
	 * Functionality to get Loggedin user information from Google plus account
	 * Ref : https://developers.google.com/+/mobile/android/people
	 */
	public void getMyGooglePlusInfo() {
		mPlusClient.loadPerson(new OnPersonLoadedListener() {

			@Override
			public void onPersonLoaded(ConnectionResult status, Person person) {
				if (status.getErrorCode() == ConnectionResult.SUCCESS) {
					person.getDisplayName();
				}
			}
		}, "me");
	}

}
