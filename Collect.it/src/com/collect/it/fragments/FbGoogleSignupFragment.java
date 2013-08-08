package com.collect.it.fragments;

import java.util.List;

import com.collect.it.R;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.facebook.FacebookUtil;
import com.collect.it.facebook.FacebookUserDataModel;
import com.collect.it.facebook.OnFacebookListener;
import com.collect.it.googleplus.GooglePlusConstants;
import com.collect.it.googleplus.GooglePlusLoginActivity;
import com.collect.it.googleplus.GooglePlusModel;
import com.collect.it.googleplus.GooglePlusUtils;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.utils.UtilityMethods;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * This fragment class is used to display two buttons Facebook Sign up and
 * Google plus sign up
 */
public class FbGoogleSignupFragment extends CollectItAbstractFragment implements
		OnFacebookListener {

	/**
	 * Declare class variables
	 */
	private FragmentActivity context;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_login_fb_google_btn, null);

		view.findViewById(R.id.signup_googleplus_btn).setOnClickListener(this);
		view.findViewById(R.id.signup_facebook_btn).setOnClickListener(this);

		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

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
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.collect.it.application.CollectItAbstractFragment#onClick(android.
	 * view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		/* Facebook signup button */
		case R.id.signup_facebook_btn:
			replaceFragment(null);

			CollectItSharedDataModel.getInstance().setSignupThroughId(
					CollectItConstants.SIGNUP_THROUGH_FACEBOOK);

			// login with facebook account
			FacebookUtil.getInstance(getActivity()).login(getActivity(),
					FbGoogleSignupFragment.this);

			break;
		/* Google plus sign up button */
		case R.id.signup_googleplus_btn:
			replaceFragment(null);

			CollectItSharedDataModel.getInstance().setSignupThroughId(
					CollectItConstants.SIGNUP_THROUGH_GOOGLEPLUS);

			startActivity(new Intent(getActivity(),
					GooglePlusLoginActivity.class));

			break;
		default:
			break;
		}
	}

	/**
	 * Functionality to replace sign up button fragment with connectivity
	 * animation fragment
	 */
	void replaceFragment(Bundle bundle) {
		// replace the Facebook and Google plus button fragment with animated
		// Connectivity fragment

		UtilityMethods
				.replaceFragment(new SignupConnectAnimFragment(),
						getActivity(),
						R.id.signup_facebook_googleplus_btn_frame, true, false,
						FragmentTagNames.SIGNUP_CONNECTIVITY_ANIM.name(),
						bundle);

		// start image view animation
		try {
			SignUpFragment frag = (SignUpFragment) context
					.getSupportFragmentManager().findFragmentByTag(
							FragmentTagNames.SIGNUP.name());

			if (frag != null) {
				frag.startConnectivityImageAnimation();
				frag.clearValues();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**********************
	 * Facebook listeners
	 **********************/

	@Override
	public void OnLoginSuccess(FacebookUserDataModel loginDetail) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnLoginError(String error) {
		// reset the value for signup
		CollectItSharedDataModel.getInstance().setSignupThroughId(0);
	}

	@Override
	public void OnLogoutSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnLoginUserDetail(FacebookUserDataModel user) {
		if (user != null) {
			/* add facebook logged in user data */
			CollectItSharedDataModel.getInstance().getUserDetailListFacebook()
					.clear();
			CollectItSharedDataModel.getInstance().getUserDetailListFacebook()
					.add(user);

			fillDetails(user);
		}

		// stop connectivity animation
		stopConnectivityAnimation();
	}

	@Override
	public void OnWallPostSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnImagePostSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnFriendList(List<GraphUser> friends) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnRequestSuccess(Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnSessionExpires() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnFacebookError(String errorMessage, String errorType,
			int errorCode) {
		// reset the value for signup
		CollectItSharedDataModel.getInstance().setSignupThroughId(0);
	}

	/**
	 * Functionality to fill details
	 */
	void fillDetails(FacebookUserDataModel userDetail) {
		// SignUpFragment.updateView(userDetail);

		try {
			SignUpFragment fragment = (SignUpFragment) context
					.getSupportFragmentManager().findFragmentByTag(
							FragmentTagNames.SIGNUP.name());

			if (fragment != null) {
				fragment.updateView(userDetail);
			}

		} catch (Exception e) {
			e.printStackTrace();

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
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		Bundle bundle = getArguments();

		if (bundle != null
				&& bundle.containsKey(CollectItConstants.BUNDLE_SIGNUP_KEY)) {
			try {
				CollectItSharedDataModel.getInstance().setSignupThroughId(
						Integer.valueOf(bundle.getString(
								CollectItConstants.BUNDLE_SIGNUP_KEY, "0")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			replaceFragment(bundle);
		}

		// show back button
		if (this.isVisible()) {
			UtilityMethods.setBackButtonVisibility(context, View.VISIBLE);
		}

	}

}
