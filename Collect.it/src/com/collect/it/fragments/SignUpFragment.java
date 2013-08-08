package com.collect.it.fragments;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.collect.it.R;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.DoubleOptionAlertWithoutTitle;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.facebook.FacebookUserDataModel;
import com.collect.it.googleplus.GooglePlusModel;
import com.collect.it.googleplus.OnGooglePlusProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;
import com.collect.it.utils.GetUserDetail;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.UtilityMethods;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Emails;
import com.google.android.gms.plus.model.people.Person.Image;
import com.google.android.gms.plus.model.people.Person.Name;

/**
 * This activity used to display sign up screen where user need to fill their
 * required details to use the app. This detail needs to submitted on server
 * database as well
 */

public class SignUpFragment extends CollectItAbstractFragment implements
		OnGooglePlusProcess {

	/**
	 * Declare class variables
	 */
	private ImageView userImageView, animImageView;
	private EditText fNameEditText, lNameEditText, emailEditText,
			genderEditText, userNameEditText, passwordEditText,
			aboutmeEditText;

	private static View view;
	private static FragmentActivity context;

	private AnimationDrawable frameAnimation;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d("", "onActivityCreated");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		// hide soft keyboard if displayed
		UtilityMethods.hideKeyboard(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.d("", "onAttach");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onDetach()
	 */
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		// reset values for Google+
		UtilityMethods.resetGooglePlusValuesForCollectIt();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signup_startcollectit_btn:
			singupFunc();
			break;
		case R.id.signup_img_txt:
			new DoubleOptionAlertWithoutTitle(context, getResources()
					.getString(R.string.image_pick_msg), getResources()
					.getString(R.string.camera), getResources().getString(
					R.string.gallery),
					DoubleOptionAlertWithoutTitle.IMAGE_PICK, null);
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("", "onResume");

		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		// fill details with Google plus logged in user
		updateViewGoogle();

		// Google analytics
		// Send a screen view when the Activity is displayed to the user.

		/*
		 * if (CollectItSharedDataModel.getInstance().getGaTracker() != null) {
		 * CollectItSharedDataModel .getInstance() .getGaTracker() .sendView(
		 * getResources().getString(R.string.ga_signup_screen)); }
		 */
		GoogleAnalyticModel.getInstance().getTracker(context)
				.sendView(getResources().getString(R.string.ga_signup_screen));

		Bundle bundle = getArguments();

		if (bundle != null
				&& bundle.containsKey(CollectItConstants.BUNDLE_SIGNUP_KEY)) {

			int signUpThroughId = Integer.valueOf(bundle
					.getString(CollectItConstants.BUNDLE_SIGNUP_KEY));

			switch (signUpThroughId) {
			case CollectItServerConstants.REGISTRATION_TYPE_FACEBOOK:
				bundle.putString(
						CollectItConstants.BUNDLE_SIGNUP_KEY,
						""
								+ CollectItConstants.SIGNUP_USER_DETAIL_ALREADY_FILLED_FACEBOOK);
				break;
			case CollectItServerConstants.REGISTRATION_TYPE_GOOGLEPLUS:
				bundle.putString(
						CollectItConstants.BUNDLE_SIGNUP_KEY,
						""
								+ CollectItConstants.SIGNUP_USER_DETAIL_ALREADY_FILLED_GOOGLEPLUS);
				break;
			default:
				break;
			}
			addFragment(bundle);

			List<FacebookUserDataModel> facebookLoginUserData = CollectItSharedDataModel
					.getInstance().getUserDetailListFacebook();

			List<Person> googleLoginUserData = CollectItSharedDataModel
					.getInstance().getUserDetailListGooglePlus();

			if (facebookLoginUserData != null
					&& facebookLoginUserData.size() > 0) {
				updateView(facebookLoginUserData.get(0));
			} else if (googleLoginUserData != null
					&& googleLoginUserData.size() > 0) {
				updateViewGoogle();
			}

			try {
				CollectItSharedDataModel.getInstance().setSignupThroughId(
						signUpThroughId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			stopConnectivityAnimation();

		} else {
			SignupConnectAnimFragment fragment = (SignupConnectAnimFragment) context
					.getSupportFragmentManager().findFragmentByTag(
							FragmentTagNames.SIGNUP_CONNECTIVITY_ANIM.name());
			if (fragment != null) {
				replaceFragment(null);

			} else {
				addFragment(null);
			}

		}

		// show back button
		UtilityMethods.setBackButtonVisibility(context, View.VISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.SIGNUP_SCREEN_ID);

		// hide keyboard
		UtilityMethods.hideKeyboard(context);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("", "onStart");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onStop()
	 */
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View,
	 * android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		Log.d("", "onViewCreated");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.collect.it.application.CollectItAbstractFragment#onCreateView(android
	 * .view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("", "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public View initialization(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.frag_signup, null);

		view.findViewById(R.id.signup_facebook_googleplus_btn_frame);
		view.findViewById(R.id.signup_startcollectit_btn).setOnClickListener(
				this);
		userImageView = (ImageView) view.findViewById(R.id.signup_img);
		animImageView = (ImageView) view.findViewById(R.id.signup_anim_img);
		view.findViewById(R.id.signup_img_txt).setOnClickListener(this);

		fNameEditText = (EditText) view.findViewById(R.id.signup_fname_edit);
		lNameEditText = (EditText) view.findViewById(R.id.signup_lname_edit);
		userNameEditText = (EditText) view
				.findViewById(R.id.signup_username_edit);
		emailEditText = (EditText) view.findViewById(R.id.signup_email_edit);
		passwordEditText = (EditText) view
				.findViewById(R.id.signup_password_edit);
		passwordEditText.setTypeface(Typeface.DEFAULT);
		passwordEditText
				.setTransformationMethod(new PasswordTransformationMethod());
		genderEditText = (EditText) view.findViewById(R.id.signup_gender_edit);
		aboutmeEditText = (EditText) view
				.findViewById(R.id.signup_aboutme_edit);

		view.findViewById(R.id.signup_imgprogressBar);

		view.findViewById(R.id.signup_startcollectit_btn).setOnClickListener(
				this);

		// reset login through value
		CollectItSharedDataModel.getInstance().setSignupThroughId(0);

		return view;
	}

	/**
	 * Functionality to add sign up buttons fragment
	 */
	void addFragment(Bundle bundle) {

		UtilityMethods.replaceFragment(new FbGoogleSignupFragment(),
				getActivity(), R.id.signup_facebook_googleplus_btn_frame,
				false, false,
				FragmentTagNames.SIGNUP_FACEBOOK_GOOGLEPLUS.name(), bundle);

	}

	/**
	 * Functionality to replace with old fragment sign up buttons fragment
	 */
	void replaceFragment(Bundle bundle) {

		UtilityMethods.replaceFragment(new FbGoogleSignupFragment(),
				getActivity(), R.id.signup_facebook_googleplus_btn_frame,
				false, false,
				FragmentTagNames.SIGNUP_FACEBOOK_GOOGLEPLUS.name(), bundle);

	}

	void updateView(FacebookUserDataModel userDetail) {
		try {
			fNameEditText.setText(userDetail.getFirstname());
			lNameEditText.setText(userDetail.getLastname());
			emailEditText.setText(userDetail.getEmail());
			genderEditText.setText(userDetail.getGender());

			ImageUtils.getInstance(context)
					.setImageUrlToView(
							userDetail.getPicture(),
							userImageView,
							(ProgressBar) view
									.findViewById(R.id.signup_imgprogressBar),
							R.drawable.signup_image_placeholder, false, true);

			stopConnectivityImageAnimation();

			CollectItSharedDataModel
					.getInstance()
					.setSignupThroughId(
							CollectItConstants.SIGNUP_USER_DETAIL_ALREADY_FILLED_FACEBOOK);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to update view with Google plus account details
	 */
	void updateViewGoogle() {
		Person person = GooglePlusModel.getInstance().getmPerson();

		try {
			if (person != null) {

				if (person.hasName()) {
					Name name = person.getName();
					if (name.hasGivenName()) {
						fNameEditText.setText(name.getGivenName().toString());
					}
					if (name.hasFamilyName()) {
						lNameEditText.setText(name.getFamilyName().toString());
					}
				}

				if (person.hasGender()) {

					int genderVal = person.getGender();
					String gender = "";
					switch (genderVal) {
					case 0:
						gender = "Male";
						break;
					case 1:
						gender = "Female";
						break;
					default:
						gender = "";
						break;
					}
					genderEditText.setText(gender);
				}

				if (person.hasEmails()) {

					List<Emails> emails = person.getEmails();
					if (emails != null && emails.size() > 0) {

					}
					emailEditText.setText(emails.get(0).toString());
				}

				if (person.hasAboutMe()) {
					aboutmeEditText.setText(person.getAboutMe());
				}

				if (person.hasImage()) {
					Image image = person.getImage();
					if (image.hasUrl()) {
						ImageUtils
								.getInstance(context)
								.setImageUrlToView(
										image.getUrl(),
										userImageView,
										(ProgressBar) view
												.findViewById(R.id.signup_imgprogressBar),
										R.drawable.signup_image_placeholder,
										false, true);
					}
				}

				CollectItSharedDataModel
						.getInstance()
						.setSignupThroughId(
								CollectItConstants.SIGNUP_USER_DETAIL_ALREADY_FILLED_GOOGLEPLUS);

			}

			stopConnectivityImageAnimation();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onGooglePlusPerson(Person person) {

	}

	/**
	 * Functionality to start animation
	 */
	void startConnectivityImageAnimation() {
		try {
			if (animImageView != null) {

				animImageView.setVisibility(View.VISIBLE);

				// Get the background, which has been compiled to an
				// AnimationDrawable object.
				frameAnimation = (AnimationDrawable) animImageView
						.getDrawable();

				// Start the animation (looped playback by default).
				frameAnimation.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to stop animation
	 */
	void stopConnectivityImageAnimation() {
		if (frameAnimation != null && frameAnimation.isRunning()) {
			frameAnimation.stop();

			if (animImageView != null) {
				animImageView.setVisibility(View.GONE);
			}

		}
	}

	/**
	 * Functionality to stop animation after getting user details
	 */
	private void stopConnectivityAnimation() {
		try {
			SignupConnectAnimFragment fragment = (SignupConnectAnimFragment) context
					.getSupportFragmentManager().findFragmentByTag(
							FragmentTagNames.SIGNUP_CONNECTIVITY_ANIM.name());

			if (fragment != null) {
				fragment.stopConnectivityAnimation();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/*
	 * fileData = Utilities.getBase64String(Utilities .getByteArray(filePath));
	 */

	/**
	 * Functionality to create json for the Signup
	 * 
	 * @param fName
	 *            First name edit text value
	 * @param lName
	 *            Last name edit text value
	 * @param userName
	 *            User name edit text value
	 * @param password
	 *            Password edit text value
	 * @param email
	 *            Email edit text value
	 * @param gender
	 *            Gender edit text value
	 * @param aboutMe
	 *            About me edit text value
	 * 
	 * @return json object
	 */
	JSONObject createSignupJson(String fName, String lName, String userName,
			String password, String email, String gender, String aboutMe) {
		JSONObject json = new JSONObject();
		try {
			/* About me */
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_ABOUTME,
					aboutMe);
			/* email */
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_EMAIL,
					email);

			/* first name */
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_FNAME,
					fName);
			/* gender */
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_GENDER,
					gender);

			/* last name */
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_LNAME,
					lName);
			/* password */
			json.put(CollectItServerConstants.WEBSERVICE_KEY_PASSWORD, password);
			/* user name */
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USERNAME, userName);

			// image data
			String base64ImageString = "";

			// registration type value according to selected signup option
			int signupThroughId = CollectItSharedDataModel.getInstance()
					.getSignupThroughId();
			switch (signupThroughId) {
			case CollectItConstants.SIGNUP_THROUGH_FACEBOOK:
			case CollectItConstants.SIGNUP_USER_DETAIL_ALREADY_FILLED_FACEBOOK:
				json.put(
						CollectItServerConstants.WEBSERVICE_KEY_REGISTRATION_TYPE,
						CollectItServerConstants.REGISTRATION_TYPE_FACEBOOK);
				/* facebook id , if signup through facebook */
				json.put(CollectItServerConstants.WEBSERVICE_KEY_FACEBOOK_ID,
						CollectItSharedDataModel.getInstance()
								.getUserDetailListFacebook().get(0).getId());

				/*
				 * base64ImageString = ImageUtils
				 * .getBase64String(ImageUtils.getByteArrayFromBitmap(ImageUtils
				 * .decodeScaledBitmapFromUrl(CollectItSharedDataModel
				 * .getInstance() .getUserDetailListFacebook().get(0)
				 * .getPicture())));
				 */

				break;
			case CollectItConstants.SIGNUP_GOOGLEPLUS_ACTIVITY_FINISH:
			case CollectItConstants.SIGNUP_THROUGH_GOOGLEPLUS:
			case CollectItConstants.SIGNUP_USER_DETAIL_ALREADY_FILLED_GOOGLEPLUS:
				json.put(
						CollectItServerConstants.WEBSERVICE_KEY_REGISTRATION_TYPE,
						CollectItServerConstants.REGISTRATION_TYPE_GOOGLEPLUS);
				/* google plus id, if signup through google plus */
				json.put(CollectItServerConstants.WEBSERVICE_KEY_GOOGLEPLUS_ID,
						CollectItSharedDataModel.getInstance()
								.getUserDetailListGooglePlus().get(0).getId());

				/*
				 * base64ImageString = ImageUtils
				 * .getBase64String(ImageUtils.getByteArrayFromBitmap(ImageUtils
				 * .decodeScaledBitmapFromUrl(CollectItSharedDataModel
				 * .getInstance() .getUserDetailListGooglePlus().get(0)
				 * .getImage().getUrl())));
				 */

				break;
			default:
				json.put(
						CollectItServerConstants.WEBSERVICE_KEY_REGISTRATION_TYPE,
						CollectItServerConstants.REGISTRATION_TYPE_COLLECTIT);
				json.put(CollectItServerConstants.WEBSERVICE_KEY_GOOGLEPLUS_ID,
						"");

				break;
			}

			/* Image base 64 string */
			base64ImageString = ImageUtils.getBase64String(ImageUtils
					.getByteArrayFromBitmap(((BitmapDrawable) userImageView
							.getDrawable()).getBitmap()));
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SIGNUP_IMAGE,
					base64ImageString);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Functionality to signup for new user
	 */
	void singupFunc() {
		try {

			String fName = fNameEditText.getText().toString().trim();
			String lName = lNameEditText.getText().toString().trim();
			String userName = userNameEditText.getText().toString().trim();
			String password = passwordEditText.getText().toString().trim();
			String email = emailEditText.getText().toString().trim();
			String gender = genderEditText.getText().toString().trim();
			String aboutMe = aboutmeEditText.getText().toString().trim();

			if (lName == null || lName.equalsIgnoreCase("null")) {
				lName = "";
			}

			if (aboutMe == null || aboutMe.equalsIgnoreCase("null")) {
				aboutMe = "";
			}

			/* if first name is entered by user */
			if (fName != null && !fName.equals("")
					&& !fName.equalsIgnoreCase("null")) {
				/* if user name is entered by user */
				if (userName != null && !userName.equals("")
						&& !userName.equalsIgnoreCase("null")) {
					/* if email is entered by user */
					if (email != null && !email.equals("")
							&& !email.equalsIgnoreCase("null")) {
						/* if password is entered by user */
						if (password != null && !password.equals("")
								&& !password.equalsIgnoreCase("null")) {
							/* if password has minimu character length */
							if (isPasswordMatchedMinimumCharacterLength(password)) {

								/* if gender is entered by user */
								/*
								 * if (gender != null && !gender.equals("") &&
								 * !gender.equalsIgnoreCase("null")) {
								 */

								/* if email is entered by user */
								if (UtilityMethods.checkForValidEmail(email)) {
									// finally hit the server for user signup
									// start progress dialog
									DialogProgressCustom.getInstance()
											.startProgressDialog(context, true);
									new WebServiceAsyncHttpPostJson(
											context,
											CollectItServerConstants.WEBSERVICE_SIGNUP,
											CollectItConstants.SIGNUP_SCREEN_ID,
											this, createSignupJson(fName,
													lName, userName, password,
													email, gender, aboutMe))
											.execute();

								} else {// valid email message
									new SingleOptionAlertWithoutTitle(
											context,
											getResources().getString(
													R.string.enter_email_valid),
											getResources().getString(
													R.string.ok), 0);
								}

								/*
								 * } else {// gender message new
								 * SingleOptionAlertWithoutTitle(context,
								 * getResources().getString(
								 * R.string.enter_gender),
								 * getResources().getString(R.string.ok), 0); }
								 */
							} else {// minimum length password message
								new SingleOptionAlertWithoutTitle(context,
										getResources().getString(
												R.string.enter_min_password),
										getResources().getString(R.string.ok),
										0);
							}
						} else {// password message
							new SingleOptionAlertWithoutTitle(context,
									getResources().getString(
											R.string.enter_password),
									getResources().getString(R.string.ok), 0);
						}
					} else {// email message
						new SingleOptionAlertWithoutTitle(context,
								getResources().getString(R.string.enter_email),
								getResources().getString(R.string.ok), 0);
					}
				} else {// user name message
					new SingleOptionAlertWithoutTitle(context, getResources()
							.getString(R.string.enter_uname), getResources()
							.getString(R.string.ok), 0);
				}
			} else {// first name message
				new SingleOptionAlertWithoutTitle(context, getResources()
						.getString(R.string.enter_fname), getResources()
						.getString(R.string.ok), 0);
			}

		} catch (Exception e) {
			e.printStackTrace();

			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.collect.it.application.CollectItAbstractFragment#getServerValues(
	 * java.lang.String, int, boolean, java.lang.String)
	 */
	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		// TODO Auto-generated method stub
		super.getServerValues(response, id, isOk, exception);
		try {
			if (isOk) {
				if (response != null) {
					switch (id) {
					case CollectItConstants.SIGNUP_SCREEN_ID:
						parseSignupServerResponse(response);
						break;
					default:
						break;
					}
				} else {
					new ViewToastMsg(context, getResources().getString(
							R.string.connection_error_collectit));
				}
			} else if (exception.equalsIgnoreCase(getResources().getString(
					R.string.connection_error_internet))) {
				new ViewToastMsg(context, exception);
			} else {
				new ViewToastMsg(context, getResources().getString(
						R.string.connection_error));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally close the progress dialog
			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();
		}

	}

	/**
	 * Functionality to parse server response
	 * 
	 * @param server
	 *            response json string
	 */
	void parseSignupServerResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String responseString = "";
			if (json.has("response")) {
				responseString = json.getString("response");

				if (responseString.equalsIgnoreCase("true")) {
					/*
					 * no need to check just fetch user logged in data and open
					 * tab screen
					 * 
					 * if (json.has("msg")) { String messageString =
					 * json.getString("msg"); new SingleOptionAlertWithoutTitle(
					 * context, messageString,
					 * getResources().getString(R.string.ok),
					 * SingleOptionAlertWithoutTitle.CLOSE_CURRENT_FRAGMENT); }
					 */

					/**
					 * fetch logged in user data
					 */
					if (json.has("responseData")) {
						String userId = json.getString("responseData");

						if (userId != null && !userId.equals("")
								&& !userId.equalsIgnoreCase("null")) {
							CollectItSharedDataModel.getInstance().setUserId(
									userId);

							// fetch loggedin user details
							new GetUserDetail(context, null, 0);

							// start tab screen
							// add bundle to show the popup
							Bundle bundle = new Bundle();
							bundle.putString(
									CollectItConstants.BUNDLE_AFTER_SIGNUP_START_APP_KEY,
									"");
							UtilityMethods
									.startTabScreenAndCloseCurrentActivity(
											context, bundle);
						}

					}

				} else {
					if (json.has("errorText")) {
						String errorString = json.getString("errorText");
						new SingleOptionAlertWithoutTitle(context, errorString,
								getResources().getString(R.string.ok), 0);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to update user image
	 */
	public void updateUserImage(final Bitmap bitmap) {
		if (bitmap != null) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					ImageUtils.getInstance(context).setImageBitmapToView(
							bitmap,
							userImageView,
							(ProgressBar) view
									.findViewById(R.id.signup_imgprogressBar),
							R.drawable.signup_image_placeholder, false, true);
				}
			}, 1000);

		}
	}

	/**
	 * Functionality to find that user has entered the password with the minimum
	 * length of characters
	 * 
	 * @param password
	 *            string
	 * 
	 * @return true if password match the criteria of minimum length
	 */
	private boolean isPasswordMatchedMinimumCharacterLength(
			String passwordString) {

		return passwordString.length() >= CollectItConstants.PASSWORD_MIN_CHARACTER_LENGTH;
	}

	/** Functionality to clear values */
	public void clearValues() {
		try {
			fNameEditText.getText().clear();
			lNameEditText.getText().clear();
			emailEditText.getText().clear();
			genderEditText.getText().clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
