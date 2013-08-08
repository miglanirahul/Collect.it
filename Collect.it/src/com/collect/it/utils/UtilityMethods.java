package com.collect.it.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;

import com.collect.it.HomeTabFragmentActivity;
import com.collect.it.R;
import com.collect.it.SplashActivity;
import com.collect.it.alerts.DoubleOptionAlertWithoutTitle;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.fragments.LoginHomeFragment;
import com.collect.it.fragments.SignUpFragment;
import com.collect.it.fragments.TitleBalFragment;
import com.collect.it.googleplus.GooglePlusModel;
import com.collect.it.model.CollectItSharedDataModel;

/**
 * This class used to define methods that are used for the multiple
 * functionality and for multiple classes commonly
 */
public class UtilityMethods {

	/**
	 * Functionality to start activity or new screen within the application
	 * 
	 * @param Activity
	 *            context
	 * @param activity
	 *            id that is defined in Constants class
	 * @param true if need to set flag (Intent.FLAG_ACTIVITY_CLEAR_TASK |
	 *        Intent.FLAG_ACTIVITY_NEW_TASK))
	 */
	public static void startActivity(Context context, int activityId,
			boolean isFlagRequired) {
		Class newActivity = null;
		switch (activityId) {
		// splash screen
		case CollectItConstants.SPLASH_SCREEN_ID:
			newActivity = SplashActivity.class;
			break;
		// login home screen
		case CollectItConstants.LOGIN_HOME_SCREEN_ID:
			newActivity = LoginHomeFragment.class;
			break;
		// sign up screen
		case CollectItConstants.SIGNUP_SCREEN_ID:
			newActivity = SignUpFragment.class;
			break;
		// if nay id matched
		default:
			break;

		}

		// finally start activity
		if (newActivity != null) {
			if (isFlagRequired) {
				context.startActivity(new Intent(context, newActivity)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_NEW_TASK));
			} else {
				context.startActivity(new Intent(context, newActivity));
			}
		}
	}

	/**
	 * Functionality to replace fragment according to requirement
	 * 
	 * @param fragmentClassObj
	 *            fragment activity context that need to open
	 * @param context
	 *            current fragment Class object
	 * @param fragmentId
	 *            current fragment id that need to be replaced with new fragment
	 *            class
	 * @param isAnimationRequired
	 *            true if need to animate
	 * @param isBackstackRequired
	 *            true if need to add on back stack of fragment manager
	 * @param fragmentTag
	 *            tag name for the fragment
	 * @param bundle
	 *            if bundle need to pass through the fragment
	 */
	public static synchronized void replaceFragment(Fragment fragmentClassObj,
			FragmentActivity context, int fragmentId,
			boolean isAnimationRequired, boolean isBackstackRequired,
			String fragmentTag, Bundle bundle) {
		try {
			if (fragmentClassObj != null && context != null && fragmentId > 0) {
				// SignUpFragment farg = new SignUpFragment();
				FragmentTransaction fragmentTransaction = context
						.getSupportFragmentManager().beginTransaction();

				if (bundle != null) {
					fragmentClassObj.setArguments(bundle);
				}

				if (fragmentTag != null && !fragmentTag.equals("")
						&& !fragmentTag.equalsIgnoreCase("null")) {
					fragmentTransaction.replace(fragmentId, fragmentClassObj,
							fragmentTag);
				} else {
					fragmentTransaction.replace(fragmentId, fragmentClassObj);
				}

				// animation during fragment replacement
				if (isAnimationRequired) {
					fragmentTransaction.setCustomAnimations(
							android.R.anim.fade_in, android.R.anim.fade_out,
							android.R.anim.fade_in, android.R.anim.fade_out);
				}

				// add fragment to back stack
				if (isBackstackRequired) {
					fragmentTransaction.addToBackStack(null);
				}

				fragmentTransaction.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to add fragment according to requirement for the current
	 * layout
	 * 
	 * @param fragmentClassObj
	 *            fragment activity context that need to open
	 * @param context
	 *            current fragment Class object
	 * @param fragmentId
	 *            current fragment id that need to be replaced with new fragment
	 *            class
	 * @param isAnimationRequired
	 *            true if need to animate
	 * @param isBackstackRequired
	 *            true if need to add on back stack of fragment manager
	 * @param fragmentTag
	 *            tag name for the fragment
	 * @param bundle
	 *            if bundle need to pass through the fragment
	 */
	public static void addFragment(Fragment fragmentClassObj,
			FragmentActivity context, int fragmentId,
			boolean isAnimationRequired, boolean isBackstackRequired,
			String fragmentTag, Bundle bundle) {
		try {
			if (fragmentClassObj != null && context != null && fragmentId > 0) {
				// SignUpFragment farg = new SignUpFragment();
				FragmentTransaction fragmentTransaction = context
						.getSupportFragmentManager().beginTransaction();

				if (bundle != null) {
					fragmentClassObj.setArguments(bundle);
				}

				if (fragmentTag != null && !fragmentTag.equals("")
						&& !fragmentTag.equalsIgnoreCase("null")) {
					fragmentTransaction.add(fragmentId, fragmentClassObj,
							fragmentTag);
				} else {
					fragmentTransaction.add(fragmentId, fragmentClassObj);
				}

				// animation during fragment replacement
				if (isAnimationRequired) {
					fragmentTransaction.setCustomAnimations(
							android.R.anim.fade_in, android.R.anim.fade_out,
							android.R.anim.fade_in, android.R.anim.fade_out);
				}

				// add fragment to back stack
				if (isBackstackRequired) {
					fragmentTransaction.addToBackStack(null);
				}

				fragmentTransaction.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to close the current screen or close the application with a
	 * message, if first screen is on top
	 * 
	 * if fragment is available on fragment stack then reopen previous fragment
	 * other wise it is a first fragment and close this fragment activity
	 * 
	 * 
	 * @param FragmentActivity
	 *            context
	 */
	public static void closeCurrentScreen(FragmentActivity context) {
		if (context.getSupportFragmentManager().getBackStackEntryCount() > 0) {
			/*
			 * Fragment frag = context.getSupportFragmentManager()
			 * .findFragmentByTag(CollectItConstants.TAG_FRAGMENT_HOME); if
			 * (frag != null) { // nothing to do } else {
			 */
			context.getSupportFragmentManager().popBackStack();
			// }
		} else {
			new DoubleOptionAlertWithoutTitle(context,
					// message to be displayed
					context.getResources().getString(R.string.app_exit_msg),
					context.getResources().getString(R.string.yes_btn), context
							.getResources().getString(R.string.no_btn),
					// id to detect app exit requirement
					DoubleOptionAlertWithoutTitle.FINISH_ACTIVITY, null);

			/*
			 * AlertDialogWithFragments obj = new AlertDialogWithFragments();
			 * obj.setDialogArguments(null,
			 * context.getResources().getString(R.string.app_exit_msg),
			 * R.string.yes_btn, R.string.no_btn, 0, AlertType.DOUBLEOPTION,
			 * listener, 0); obj.show(context.getSupportFragmentManager(),
			 * "close");
			 */
		}
	}

	/**
	 * Functionality to check valid email address through a defined pattern
	 */
	private static Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");

	public static boolean checkForValidEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	/**
	 * Functionality to reset values for Google plus
	 */
	public static void resetGooglePlusValuesForCollectIt() {
		CollectItSharedDataModel.getInstance().setSignupThroughId(0);
		GooglePlusModel.getInstance().setmPerson(null);
	}

	/**
	 * Functionality to check the Internet availability
	 * 
	 * @param context
	 *            Activity context
	 * 
	 * @return true if Internet is connected
	 */
	public static boolean isInternetConnected(Context context) {
		boolean isWifiConnected = false;
		boolean isMobileInternetConnected = false;
		try {

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isWifiConnected || isMobileInternetConnected;
	}

	/**
	 * Functionality to parse json for user id after login. This userId is saved
	 * for further use in the application.
	 * 
	 * @param context
	 *            FragmentActivity context
	 * @param response
	 *            server response
	 * @param taskIdErrorMsg
	 *            if any task id that will be associated for button click event
	 *            listener for the respective process for error message that is
	 *            fetched from server
	 * 
	 * @return true if userId successfully parsed
	 */
	public static String isUserIdParsed(FragmentActivity context,
			String response, int taskIdErrorMsg) {
		String msgToPass = "";
		try {
			JSONObject json = new JSONObject(response);
			String responseString = "";
			if (json.has("response")) {
				responseString = json.getString("response");

				if (responseString.equalsIgnoreCase("true")) {

					if (json.has("responseData")) {
						JSONObject responseDataJson = json
								.getJSONObject("responseData");

						String userId = "";

						if (responseDataJson.has("User")) {
							JSONObject userJson = responseDataJson
									.getJSONObject("User");

							// this will be the user Id as discussed with Munish
							// Sir
							if (userJson.has("id")) {
								// userData.setId(userJson.getString("id"));
								userId = userJson.getString("id");
							}

						}

						if (responseDataJson.has("UserDetail")) {
							JSONObject userDetailJson = responseDataJson
									.getJSONObject("UserDetail");

							// this is a foreign key as per discussed with
							// Munish Sir. It'll be same as 'id' key in the
							// responseData json packet as defined above
							if (userId == null || userId.equals("")
									|| userId.equalsIgnoreCase("null")) {
								if (userDetailJson.has("user_id")) {

									userId = userDetailJson
											.getString("user_id");

								}
							}

						}

						if (userId != null && !userId.equals("")
								&& !userId.equalsIgnoreCase("null")) {
							// save userid for application
							CollectItSharedDataModel.getInstance().setUserId(
									userId);
							// save userid into preferences
							SharedPreferences sharedPreference = CollectItSharedDataModel
									.getInstance().getPreferences(context);
							sharedPreference
									.edit()
									.putString(
											CollectItConstants.SHARED_PREFERENCES_USED_ID,
											userId).commit();

							msgToPass = context.getResources().getString(
									R.string.success);
						}

					} else {
						if (json.has("msg")) {
							String messageString = json.getString("msg");
							msgToPass = context.getResources().getString(
									R.string.success_error);
							new SingleOptionAlertWithoutTitle(context,
									messageString, context.getResources()
											.getString(R.string.ok), 0);

						}
					}

				} else {
					if (json.has("errorText")) {
						String errorString = json.getString("errorText");
						msgToPass = context.getResources().getString(
								R.string.success_error);

						if (taskIdErrorMsg == SingleOptionAlertWithoutTitle.START_SIGNUP_FRAGMENT) {
							// create bundle
							Bundle bundle = new Bundle();
							bundle.putString(
									CollectItConstants.BUNDLE_SIGNUP_KEY, ""
											+ CollectItSharedDataModel
													.getInstance()
													.getSignupThroughId());

							// replace the login home fragment with sign up
							// fragment for main
							// fragment activity
							UtilityMethods.replaceFragment(
									new SignUpFragment(), context,
									R.id.main_frag_loginhome_frag, true, true,
									FragmentTagNames.SIGNUP.name(), bundle);
						} else {
							new SingleOptionAlertWithoutTitle(context,
									errorString, context.getResources()
											.getString(R.string.ok),
									taskIdErrorMsg);
						}

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return msgToPass;
	}

	/**
	 * Functionality to finish current activity
	 */
	public static void finishCurrentActivity(FragmentActivity context) {
		context.finish();
	}

	/**
	 * Functionality to display or hide back button for the title bar in android
	 * 
	 * @param context
	 *            Fragment activity context
	 * @param visibility
	 *            pass View.VISIBLE to display back button or pass
	 *            View.INVISIBLE to hide back button
	 */
	public static void setBackButtonVisibility(FragmentActivity context,
			int visibility) {
		try {
			TitleBalFragment titleFragment = (TitleBalFragment) context
					.getSupportFragmentManager().findFragmentById(
							R.id.main_frag_title_frag);

			if (titleFragment != null) {
				titleFragment.setVisibilityBackButton(visibility);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Functionality to display or hide setting icon for the title bar in
	 * android
	 * 
	 * @param context
	 *            Fragment activity context
	 * @param visibility
	 *            pass View.VISIBLE to display setting icon or pass
	 *            View.INVISIBLE to hide setting icon
	 */
	public static void setSettingIconVisibility(FragmentActivity context,
			int visibility) {
		try {
			TitleBalFragment titleFragment = (TitleBalFragment) context
					.getSupportFragmentManager().findFragmentById(
							R.id.main_frag_title_frag);

			if (titleFragment != null) {
				titleFragment.setVisibilitySettingImage(visibility);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Functionality to start tab home screen and close current activity
	 * 
	 * @param context
	 *            FragmentActivity context
	 * @param bundle
	 *            Bundle that need to pass for the activity
	 */
	public static void startTabScreenAndCloseCurrentActivity(
			FragmentActivity context, Bundle bundle) {
		// start tab fragments
		if (bundle != null) {
			context.startActivity(new Intent(context,
					HomeTabFragmentActivity.class).setFlags(
					Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK).putExtras(bundle));
		} else {
			context.startActivity(new Intent(context,
					HomeTabFragmentActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK));
		}

		UtilityMethods.finishCurrentActivity(context);
	}

	/**
	 * Functionality to set title on title bar of the application
	 * 
	 * @param context
	 *            Fragment activity context
	 * @param screenId
	 *            screenId used to determine particular foreground screen
	 */
	public static void setTitleBarTitle(FragmentActivity context, int screenId) {
		try {
			TitleBalFragment topFragment = (TitleBalFragment) context
					.getSupportFragmentManager().findFragmentById(
							R.id.main_frag_title_frag);
			if (topFragment != null) {
				topFragment.setShareIconVisibility(false);
				switch (screenId) {
				case CollectItConstants.SIGNUP_SCREEN_ID:
					topFragment.setTitle(false, null);
					break;
				case CollectItConstants.LOGIN_SCREEN_ID:
					topFragment.setTitle(false, null);
					break;
				case CollectItConstants.CHANGE_PASSWORD_SCREEN_ID:
					topFragment.setTitle(true, context.getResources()
							.getString(R.string.change_password));
					break;
				case CollectItConstants.EDIT_PROFILE_SCREEN_ID:
					topFragment.setTitle(true, context.getResources()
							.getString(R.string.edit_account));
					break;
				case CollectItConstants.SETTING_SCREEN_ID:
					topFragment.setTitle(true, context.getResources()
							.getString(R.string.settings));
					break;
				case CollectItConstants.PROFILE_SCREEN_ID:
					topFragment.setTitle(true, context.getResources()
							.getString(R.string.my_account));
					break;
				case CollectItConstants.SEARCH_SCREEN_ID:
					topFragment.setTitle(true, context.getResources()
							.getString(R.string.search));
					break;
				case CollectItConstants.ADD_ITEM_SCREEN_ID:
					topFragment.setTitle(true, context.getResources()
							.getString(R.string.add_item));
					break;
				case CollectItConstants.NOTIFICATION_SCREEN_ID:
					topFragment.setTitle(true, context.getResources()
							.getString(R.string.notifications));
					break;
				case CollectItConstants.HOME_SCREEN_ID:
					topFragment.setTitle(false, null);
					break;
				case CollectItConstants.LOGIN_HOME_SCREEN_ID:
					topFragment.setTitle(false, null);
					break;
				case CollectItConstants.ITEM_DETAIL_SCREEN_ID:
					topFragment.setTitle(false, null);
					topFragment.setShareIconVisibility(true);
					break;
				case CollectItConstants.EDIT_ITEM_SCREEN_ID:
					topFragment.setTitle(true, context.getResources()
							.getString(R.string.edit_item));
					break;
				default:
					topFragment.setTitle(false, null);
					topFragment.setShareIconVisibility(false);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to hide soft keyboard of the device
	 * 
	 * @param context
	 *            Fragment activity object
	 */
	public static void hideKeyboard(FragmentActivity context) {
		try {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Activity.INPUT_METHOD_SERVICE);
			/* imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); */
			imm.hideSoftInputFromWindow(context.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to create uri from bitmap
	 * 
	 * @param context
	 *            Activity context
	 * @param bitmap
	 *            bitmap that need to create uri from
	 * @param imageTitle
	 *            the title for the image/bitmap
	 * 
	 * @return Uri for the required bitmap
	 */
	public Uri getImageUri(Context context, Bitmap bitmap, String imageTitle) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(context.getContentResolver(),
				bitmap, imageTitle, null);
		return Uri.parse(path);
	}

	/**
	 * Functionality to get message to post on social sharing sites for an item
	 * 
	 * @param context
	 *            Activity context
	 * @param collectionString
	 *            collection string
	 * 
	 * @return message string
	 */
	public static String getMessage(Context context, String collectionString) {
		String message = "";
		try {
			message = context.getResources().getString(
					R.string.sharing_message1)
					+ " "
					+ collectionString
					+ context.getResources().getString(
							R.string.sharing_message2);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Collect.it";
		}
		return message;
	}

	/**
	 * Functionality to create a file from bitmap
	 * 
	 * @param context
	 *            Activity context
	 * @param bitmap
	 *            image bitmap
	 * @param fileName
	 *            name of the file
	 * */
	public static File createFileBitmap(Context context, Bitmap bitmap,
			String fileName) {
		File f = null;
		try {
			File rootSdDirectory = Environment.getExternalStorageDirectory();
			f = new File(rootSdDirectory, fileName + ".jpg");
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();

			// Convert bitmap to byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100 /* ignored for PNG */, bos);
			byte[] bitmapdata = bos.toByteArray();

			// write the bytes in file
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bitmapdata);
			fos.flush();
			fos.close();
			bitmap = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
}
