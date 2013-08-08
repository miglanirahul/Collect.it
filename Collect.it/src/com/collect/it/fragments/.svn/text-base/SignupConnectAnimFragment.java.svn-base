package com.collect.it.fragments;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.collect.it.R;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.googleplus.GooglePlusModel;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.google.android.gms.plus.model.people.Person;

/**
 * This fragment class is used to display the connection components on the
 * signup screen
 */
public class SignupConnectAnimFragment extends CollectItAbstractFragment {

	/**
	 * Declare class variables
	 */
	private ImageView middleImageView, rightImageView;
	private TextView aboveAnimationTextView, belowAnimationTextView;
	private AnimationDrawable frameAnimation;

	private FragmentActivity context;

	private final int STOP_ANIMATION_DELAY_INTERVAL = 5000;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// store locally parent fragment activity context
		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		// set visibility of texts
		aboveAnimationTextView.setVisibility(View.INVISIBLE);
		belowAnimationTextView.setVisibility(View.INVISIBLE);

		startConnectivityAnimation();

		// check which sign up is being processed for and make functionality
		// accordingly
		switch (CollectItSharedDataModel.getInstance().getSignupThroughId()) {
		case CollectItConstants.SIGNUP_THROUGH_FACEBOOK:
			rightImageView.setImageDrawable(getResources().getDrawable(
					R.drawable.connect_fb));

			// Google analytics
			// Send a screen view when the Activity is displayed to the user.
			/*
			 * if (CollectItSharedDataModel.getInstance().getGaTracker() !=
			 * null) { CollectItSharedDataModel .getInstance() .getGaTracker()
			 * .sendView(
			 * getResources().getString(R.string.ga_signup_facebook_screen)); }
			 */
			GoogleAnalyticModel
					.getInstance()
					.getTracker(context)
					.sendView(
							getResources().getString(
									R.string.ga_signup_facebook_screen));

			break;
		case CollectItConstants.SIGNUP_THROUGH_GOOGLEPLUS:
			rightImageView.setImageDrawable(getResources().getDrawable(
					R.drawable.connect_googleplus));

			// Google analytics
			// Send a screen view when the Activity is displayed to the user.
			/*
			 * if (CollectItSharedDataModel.getInstance().getGaTracker() !=
			 * null) { CollectItSharedDataModel .getInstance() .getGaTracker()
			 * .sendView(
			 * getResources().getString(R.string.ga_signup_googleplus_screen));
			 * }
			 */
			GoogleAnalyticModel
					.getInstance()
					.getTracker(context)
					.sendView(
							getResources().getString(
									R.string.ga_signup_googleplus_screen));

			break;
		case CollectItConstants.SIGNUP_GOOGLEPLUS_ACTIVITY_FINISH:
			stopConnectivityAnimation();
			break;
		case CollectItConstants.SIGNUP_GOOGLEPLUS_ACTIVITY_FINISH_ERROR:
			stopConnectivityAnimation();
			closeCurrentFrgament();
			break;
		case CollectItConstants.SIGNUP_USER_DETAIL_ALREADY_FILLED_FACEBOOK:
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					stopConnectivityAnimation();
					stopUserImageAnimation();
				}
			}, STOP_ANIMATION_DELAY_INTERVAL);

			rightImageView.setImageDrawable(getResources().getDrawable(
					R.drawable.connect_fb));
			break;
		case CollectItConstants.SIGNUP_USER_DETAIL_ALREADY_FILLED_GOOGLEPLUS:
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					stopConnectivityAnimation();
					stopUserImageAnimation();
				}
			}, STOP_ANIMATION_DELAY_INTERVAL);
			rightImageView.setImageDrawable(getResources().getDrawable(
					R.drawable.connect_googleplus));
			break;
		default:
			break;
		}

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

		View view = super.onCreateView(inflater, container, savedInstanceState);

		return view;
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
		// stop animation if started
		// stopConnectivityAnimation();
	}

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_signup_connect_anim, null);

		middleImageView = (ImageView) view
				.findViewById(R.id.signup_face_google_connect_anim_middle_img);
		rightImageView = (ImageView) view
				.findViewById(R.id.signup_face_google_connect_anim_right_img);

		aboveAnimationTextView = (TextView) view
				.findViewById(R.id.signup_face_google_connect_txt);
		belowAnimationTextView = (TextView) view
				.findViewById(R.id.signup_face_google_create_password_txt);

		return view;
	}

	/**
	 * Functionality to start animation
	 */
	void startConnectivityAnimation() {
		try {
			if (middleImageView != null) {
				// Get the background, which has been compiled to an
				// AnimationDrawable object.
				frameAnimation = (AnimationDrawable) middleImageView
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
	void stopConnectivityAnimation() {
		if (frameAnimation != null && frameAnimation.isRunning()) {
			frameAnimation.stop();

			// set default image of animation
			if (middleImageView != null) {
				middleImageView.setImageDrawable(frameAnimation.getFrame(3));
			}

			// set visibility of texts
			if (aboveAnimationTextView != null) {
				aboveAnimationTextView.setVisibility(View.VISIBLE);
			}
			if (belowAnimationTextView != null) {
				belowAnimationTextView.setVisibility(View.VISIBLE);
			}
		}
	}

	/**
	 * Functionality to stop user image animation
	 */
	void stopUserImageAnimation() {
		try {
			SignUpFragment fragment = (SignUpFragment) context
					.getSupportFragmentManager().findFragmentByTag(
							FragmentTagNames.SIGNUP.name());

			if (fragment != null) {
				fragment.stopConnectivityImageAnimation();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Functionality to close this fragment
	 */
	void closeCurrentFrgament() {
		context.getSupportFragmentManager().popBackStack();
	}
}
