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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TabHost.OnTabChangeListener;

import com.collect.it.alerts.CustomizedDoubleOptionAlert;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.DoubleOptionAlertWithoutTitle;
import com.collect.it.application.CollectItAbstractFragmentActivity;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.fragments.AddItemFragment;
import com.collect.it.fragments.EditItemDetailFragment;
import com.collect.it.fragments.EditProfileFragment;
import com.collect.it.fragments.HomeFragment;
import com.collect.it.fragments.NotificationFragment;
import com.collect.it.fragments.ProfileFragment;
import com.collect.it.fragments.SearchFragment;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.utils.DecorderImage;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.UtilityMethods;
import com.facebook.Session;

/**
 * This fragment activity used to define the functionality for the Add items in
 * the application
 */
public class HomeTabFragmentActivity extends CollectItAbstractFragmentActivity
		implements OnTabChangeListener {
	/**
	 * Declare class variables
	 */
	// Fragment TabHost as mTabHost
	private static FragmentTabHost tabHost;
	/**
	 * First time login boolean
	 */
	private boolean isFirstTime = false;

	private DecorderImage imageDecorder;

	@Override
	public void initialization() {
		setContentView(R.layout.tab_fragment);

		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);
		tabHost.setOnTabChangedListener(this);

		isFirstTime = true;

		// add tabs
		addTab();
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
		super.onCreate(savedInstanceState);

		imageDecorder = new DecorderImage(this);

		// check for extra
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle
					.containsKey(CollectItConstants.BUNDLE_AFTER_SIGNUP_START_APP_KEY)) {
				new CustomizedDoubleOptionAlert(this,
						CollectItConstants.HOME_SCREEN_ID);
			}

		}
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

				// Image pick functionality for the sign up screen initially
				imagePick(requestCode, data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		try {
			// add context to further use in the fragments
			CollectItSharedDataModel.getInstance()
					.setCurrentFragmentActivityContext(this);

			// show back button
			UtilityMethods.setBackButtonVisibility(this, View.VISIBLE);

		} catch (Exception e) {
			e.printStackTrace();
		}

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

	/**
	 * Functionality to add tabs data for onCreate();
	 */
	private void addTab() {
		/* Home tab */
		tabHost.addTab(
				tabHost.newTabSpec(FragmentTagNames.HOME_TAB.name())
						.setIndicator(
								null,
								getResources().getDrawable(
										R.drawable.tab_home_image_selector)),
				HomeFragment.class, null);

		/* Search tab */
		tabHost.addTab(
				tabHost.newTabSpec(FragmentTagNames.SEARCH_TAB.name())
						.setIndicator(
								null,
								getResources().getDrawable(
										R.drawable.tab_search_image_selector)),
				SearchFragment.class, null);

		/* Add Item tab */
		tabHost.addTab(
				tabHost.newTabSpec(FragmentTagNames.ADD_ITEM_TAB.name())
						.setIndicator(
								null,
								getResources().getDrawable(
										R.drawable.tab_additem_image_selector)),
				AddItemFragment.class, null);

		/* Notification tab */
		tabHost.addTab(
				tabHost.newTabSpec(FragmentTagNames.NOTIFICATION_TAB.name())
						.setIndicator(
								null,
								getResources()
										.getDrawable(
												R.drawable.tab_notification_image_selector)),
				NotificationFragment.class, null);

		/* User Profile tab */
		tabHost.addTab(
				tabHost.newTabSpec(FragmentTagNames.PROFILE_TAB.name())
						.setIndicator(
								null,
								getResources().getDrawable(
										R.drawable.tab_profile_image_selector)),
				ProfileFragment.class, null);

	}

	@Override
	public void onTabChanged(String tabTag) {
		Log.e("", "TAB_CHANGED = " + tabTag);

		/*
		 * android.support.v4.app.FragmentTransaction ft =
		 * getSupportFragmentManager() .beginTransaction();
		 * 
		 * if (tabTag.equals(CollectItConstants.TAG_FRAGMENT_HOME)) {
		 * EditProfileFragment fragment = (EditProfileFragment)
		 * getSupportFragmentManager()
		 * .findFragmentByTag(CollectItConstants.TAG_FRAGMENT_SETTING);
		 * SettingsFragment settingFragment = (SettingsFragment)
		 * getSupportFragmentManager()
		 * .findFragmentByTag(CollectItConstants.TAG_FRAGMENT_HOME);
		 * ChangePasswordFragment passFrag =
		 * (ChangePasswordFragment)getSupportFragmentManager()
		 * .findFragmentByTag(CollectItConstants.TAG_FRAGMENT_CHANGE_PASSWORD);
		 * 
		 * if(lastFragmentTab1!= null && fragment == lastFragmentTab1){
		 * lastFragmentTab1 = fragment;
		 * 
		 * ft.add(R.id.tab_content, fragment,
		 * CollectItConstants.TAG_FRAGMENT_SETTING);
		 * 
		 * ft.attach(fragment); }else if(lastFragmentTab1!= null &&
		 * settingFragment == lastFragmentTab1){ lastFragmentTab1 =
		 * settingFragment;
		 * 
		 * ft.add(R.id.tab_content, settingFragment,
		 * CollectItConstants.TAG_FRAGMENT_HOME);
		 * 
		 * ft.attach(settingFragment); }else if(lastFragmentTab1!= null &&
		 * passFrag == lastFragmentTab1){ lastFragmentTab1 = passFrag;
		 * 
		 * ft.add(R.id.tab_content, passFrag,
		 * CollectItConstants.TAG_FRAGMENT_CHANGE_PASSWORD);
		 * 
		 * ft.attach(passFrag); }else{
		 * 
		 * 
		 * ft.add(R.id.tab_content, new SettingsFragment(),
		 * CollectItConstants.TAG_FRAGMENT_HOME); lastFragmentTab1 =
		 * settingFragment; ft.attach(settingFragment); }
		 * 
		 * //ft.commit();
		 * 
		 * if (settingFragment != null) { Log.d("", "home frag ment not null");
		 * 
		 * Log.e("","Home frag isAdded "+fragment.isAdded());
		 * Log.e("","Home frag isDetached "+fragment.isDetached());
		 * Log.e("","Home frag isHidden "+fragment.isHidden());
		 * Log.e("","Home frag isInLayout "+fragment.isInLayout());
		 * Log.e("","Home frag isRemoving "+fragment.isRemoving());
		 * Log.e("","Home frag isResumed "+fragment.isResumed());
		 * Log.e("","Home frag isVisible "+fragment.isVisible());
		 * 
		 * } else { lastFragmentTab1 = fragment; ft.attach(fragment); }
		 * 
		 * } else if (tabTag.equals(CollectItConstants.TAG_FRAGMENT_SEARCH)) {
		 * SettingsFragment fragment = (SettingsFragment)
		 * getSupportFragmentManager()
		 * .findFragmentByTag(CollectItConstants.TAG_FRAGMENT_SEARCH);
		 * 
		 * if (fragment != null) { Log.d("", "search frag ment not null"); }
		 * else { ft.attach(fragment); }
		 * 
		 * } else if (tabTag.equals(CollectItConstants.TAG_FRAGMENT_ADD_ITEM)) {
		 * Fragment fragment = getSupportFragmentManager().findFragmentByTag(
		 * CollectItConstants.TAG_FRAGMENT_ADD_ITEM); } else if
		 * (tabTag.equals(CollectItConstants.TAG_FRAGMENT_NOTIFICATION)) {
		 * Fragment fragment = getSupportFragmentManager().findFragmentByTag(
		 * CollectItConstants.TAG_FRAGMENT_HOME); } else { Fragment fragment =
		 * getSupportFragmentManager().findFragmentByTag(
		 * CollectItConstants.TAG_FRAGMENT_HOME); }
		 */

		/*
		 * Fragment homeFragment =
		 * getSupportFragmentManager().findFragmentByTag(
		 * CollectItConstants.TAG_FRAGMENT_HOME); Fragment searchFragment =
		 * getSupportFragmentManager()
		 * .findFragmentByTag(CollectItConstants.TAG_FRAGMENT_SEARCH); Fragment
		 * addItemFragment = getSupportFragmentManager()
		 * .findFragmentByTag(CollectItConstants.TAG_FRAGMENT_ADD_ITEM);
		 * Fragment notificationFragment = getSupportFragmentManager()
		 * .findFragmentByTag(CollectItConstants.TAG_FRAGMENT_NOTIFICATION);
		 * Fragment profileFragment = getSupportFragmentManager()
		 * .findFragmentByTag(CollectItConstants.TAG_FRAGMENT_PROFILE);
		 * android.support.v4.app.FragmentTransaction ft =
		 * getSupportFragmentManager() .beginTransaction();
		 *//** Detaches the androidfragment if exists */
		/*
		 * if (homeFragment != null) ft.detach(homeFragment);
		 *//** Detaches the applefragment if exists */
		/*
		 * if (searchFragment != null) ft.detach(searchFragment);
		 *//** Detaches the androidfragment if exists */
		/*
		 * if (addItemFragment != null) ft.detach(addItemFragment);
		 *//** Detaches the applefragment if exists */
		/*
		 * if (notificationFragment != null) ft.detach(notificationFragment);
		 *//** Detaches the androidfragment if exists */
		/*
		 * if (profileFragment != null) ft.detach(profileFragment);
		 *//** If current tab is android */
		/*
		 * if (tabTag.equalsIgnoreCase(CollectItConstants.TAG_FRAGMENT_HOME)) {
		 * 
		 * if (homeFragment == null) {
		 *//** Create AndroidFragment and adding to fragmenttransaction */
		/*
		 * ft.add(R.id.tab_content, new SettingsFragment(),
		 * CollectItConstants.TAG_FRAGMENT_HOME); } else {
		 *//**
		 * Bring to the front, if already exists in the fragmenttransaction
		 */
		/*
		 * ft.attach(homeFragment); }
		 * 
		 * } else if (tabTag
		 * .equalsIgnoreCase(CollectItConstants.TAG_FRAGMENT_SEARCH)) {
		 * 
		 * if (searchFragment == null) {
		 *//** Create AndroidFragment and adding to fragmenttransaction */
		/*
		 * ft.add(R.id.tab_content, new SettingsFragment(),
		 * CollectItConstants.TAG_FRAGMENT_SEARCH); } else {
		 *//**
		 * Bring to the front, if already exists in the fragmenttransaction
		 */
		/*
		 * ft.attach(searchFragment); }
		 * 
		 * } else if (tabTag
		 * .equalsIgnoreCase(CollectItConstants.TAG_FRAGMENT_ADD_ITEM)) {
		 * 
		 * if (addItemFragment == null) {
		 *//** Create AndroidFragment and adding to fragmenttransaction */
		/*
		 * ft.add(R.id.tab_content, new SettingsFragment(),
		 * CollectItConstants.TAG_FRAGMENT_ADD_ITEM); } else {
		 *//**
		 * Bring to the front, if already exists in the fragmenttransaction
		 */
		/*
		 * ft.attach(addItemFragment); }
		 * 
		 * } else if (tabTag
		 * .equalsIgnoreCase(CollectItConstants.TAG_FRAGMENT_NOTIFICATION)) {
		 * 
		 * if (notificationFragment == null) {
		 *//** Create AndroidFragment and adding to fragmenttransaction */
		/*
		 * ft.add(R.id.tab_content, new SettingsFragment(),
		 * CollectItConstants.TAG_FRAGMENT_NOTIFICATION); } else {
		 *//**
		 * Bring to the front, if already exists in the fragmenttransaction
		 */
		/*
		 * ft.attach(notificationFragment); }
		 * 
		 * } else {
		 *//** If current tab is apple */
		/*
		 * if (profileFragment == null) {
		 *//** Create AppleFragment and adding to fragmenttransaction */
		/*
		 * ft.add(R.id.tab_content, new SettingsFragment(),
		 * CollectItConstants.TAG_FRAGMENT_PROFILE); } else {
		 *//**
		 * Bring to the front, if already exists in the fragmenttransaction
		 */
		/*
		 * ft.attach(profileFragment); } } ft.commit();
		 */
		try {
			if (!isFirstTime) {
				FragmentTagNames frag = FragmentTagNames.valueOf(tabTag);
				// clear back stack
				try {
					getSupportFragmentManager().popBackStack(null,
							FragmentManager.POP_BACK_STACK_INCLUSIVE);
				} catch (Exception e) {
					e.printStackTrace();
				}
				switch (frag) {
				case HOME_TAB:
					UtilityMethods.replaceFragment(new HomeFragment(), this,
							R.id.tab_content, true, false,
							FragmentTagNames.HOME_TAB.name(), null);
					break;
				case SEARCH_TAB:
					UtilityMethods.replaceFragment(new SearchFragment(), this,
							R.id.tab_content, true, false,
							FragmentTagNames.SEARCH_TAB.name(), null);
					break;
				case ADD_ITEM_TAB:
					/*
					 * Fragment addFrag = getSupportFragmentManager()
					 * .findFragmentByTag(FragmentTagNames.ADD_ITEM_TAB.name());
					 * if (addFrag != null&& !addFrag.isDetached()) {
					 * FragmentTransaction ft = getSupportFragmentManager()
					 * .beginTransaction(); ft.detach(addFrag); ft.commit(); }
					 */
					UtilityMethods.replaceFragment(new AddItemFragment(), this,
							R.id.tab_content, true, false,
							FragmentTagNames.ADD_ITEM_TAB.name(), null);
					break;
				case NOTIFICATION_TAB:
					UtilityMethods.replaceFragment(new NotificationFragment(),
							this, R.id.tab_content, true, false,
							FragmentTagNames.NOTIFICATION_TAB.name(), null);
					break;
				case PROFILE_TAB:
					UtilityMethods.replaceFragment(new ProfileFragment(), this,
							R.id.tab_content, true, false,
							FragmentTagNames.PROFILE_TAB.name(), null);
					break;
				default:
					UtilityMethods.replaceFragment(new HomeFragment(), this,
							R.id.tab_content, true, false,
							FragmentTagNames.HOME_TAB.name(), null);
					break;
				}
			} else {
				isFirstTime = false;
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
							HomeTabFragmentActivity.this, true);
					InputStream picture = getContentResolver().openInputStream(
							selectedImage);
					mBitmap = ImageUtils.decodeSampledBitmapFromResourceMemOpt(
							HomeTabFragmentActivity.this, picture, imageWidth,
							imageHeight);
					DialogProgressCustom.getInstance().stopProgressDialog();
				} else {
					if (picturePath.startsWith("http")) {
						mBitmap = ImageUtils.decodeScaledBitmapFromUrl(
								HomeTabFragmentActivity.this, picturePath,
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

				/*
				 * Uri capturedImageUri = Uri.fromFile(CollectItSharedDataModel
				 * .getInstance().getCameraImageUriFile());
				 */// data.getData();

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
								HomeTabFragmentActivity.this,
								new String[] { file.getAbsolutePath() }, null,
								null);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				/*
				 * Bitmap bitmap = ImageUtils.getBitmapFromDeviceImage(this,
				 * imageWidth, imageHeight, uri); if (bitmap != null) { mBitmap
				 * = bitmap; }
				 */

				// mBitmap = ImageUtils.setPic(uri.getPath(), this, imageWidth,
				// imageHeight);
				if (imageDecorder == null) {
					imageDecorder = new DecorderImage(this);
				}
				mBitmap = imageDecorder.decode(this, uri, imageWidth,
						imageHeight);
			}

			// finally update UI
			if (mBitmap != null) {
				EditProfileFragment editFrag = (EditProfileFragment) HomeTabFragmentActivity.this
						.getSupportFragmentManager().findFragmentByTag(
								FragmentTagNames.EDIT_PROFILE.name());
				AddItemFragment addFrag = (AddItemFragment) HomeTabFragmentActivity.this
						.getSupportFragmentManager().findFragmentByTag(
								FragmentTagNames.ADD_ITEM_TAB.name());

				EditItemDetailFragment editItemFrag = (EditItemDetailFragment) HomeTabFragmentActivity.this
						.getSupportFragmentManager().findFragmentByTag(
								FragmentTagNames.EDIT_ITEM_DETAIL.name());

				if (editFrag != null) {
					editFrag.updateUserImage(mBitmap);
				} else if (addFrag != null) {
					addFrag.updateItemImage(mBitmap);
				} else if (editItemFrag != null) {
					editItemFrag.updateItemImage(mBitmap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mBitmap = null;
		}
	}

	/**
	 * Functionality to set current tab on fore ground
	 */
	public static void setCurrentTab(int tabNumber) {
		try {
			tabHost.setCurrentTab(tabNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// super.onSaveInstanceState(outState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
	}

}
