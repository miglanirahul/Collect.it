package com.collect.it.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.collect.it.R;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.restore.RestoreSignupData;
import com.collect.it.utils.HorizontalListView;
import com.collect.it.utils.UtilityMethods;

/**
 * This activity used to display login home screen where user can sign up /
 * login to use application
 */
public class LoginHomeFragment extends CollectItAbstractFragment {
	/**
	 * Declare class variables
	 */

	private FragmentActivity context;
	private HorizontalListView imageHorizonatalListView;

	private int[] imageListArray = { R.drawable.image1, R.drawable.image_2,
			R.drawable.image_3, R.drawable.image_4_locked, R.drawable.image_5 };

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// initiate context
		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		// Google analytics
		// Send a screen view when the Activity is displayed to the user.
		GoogleAnalyticModel.getInstance().getTracker(context)
				.sendView(getResources().getString(R.string.ga_landing_screen));

		// hide back button
		UtilityMethods.setBackButtonVisibility(context, View.INVISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.LOGIN_HOME_SCREEN_ID);

		// hide keyboard
		UtilityMethods.hideKeyboard(context);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		imageHorizonatalListView = null;
	}

	@Override
	public void onClick(View v) {
		// reset variables
		new RestoreSignupData();
		switch (v.getId()) {
		// sign up button
		case R.id.login_home_signup_btn:

			// reset values for Google+
			UtilityMethods.resetGooglePlusValuesForCollectIt();

			// replace the login home fragment with sign up fragment for main
			// fragment activity
			UtilityMethods.replaceFragment(new SignUpFragment(), getActivity(),
					R.id.main_frag_loginhome_frag, true, true,
					FragmentTagNames.SIGNUP.name(), null);

			break;
		// login button
		case R.id.login_home_login_btn:
			UtilityMethods.replaceFragment(new LoginFragment(), getActivity(),
					R.id.main_frag_loginhome_frag, true, true, null, null);

			break;
		// if no any match found
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
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

	@Override
	public View initialization(LayoutInflater inflater) {
		View v = inflater.inflate(R.layout.frag_login_home, null);
		// initiate components
		Button googlePlusButton = (Button) v
				.findViewById(R.id.login_home_google_plus_btn);
		Button facebookButton = (Button) v
				.findViewById(R.id.login_home_facebook_btn);
		Button signUpButton = (Button) v
				.findViewById(R.id.login_home_signup_btn);
		Button loginButton = (Button) v.findViewById(R.id.login_home_login_btn);

		imageHorizonatalListView = (HorizontalListView) v
				.findViewById(R.id.login_home_image_horizontalview);
		imageHorizonatalListView.setAdapter(new GridAdapter());

		TextView welcomeTextView = (TextView)v.findViewById(R.id.login_home_desc_txt);
		welcomeTextView.setMovementMethod(LinkMovementMethod
				.getInstance());
		
		// attach click event listener on components
		googlePlusButton.setOnClickListener(this);
		facebookButton.setOnClickListener(this);
		signUpButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		return v;
	}

	/**
	 * Functionality of server response
	 */
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

		/*
		 * TODO: functionality has been transfer on Login screen as per the
		 * client comments on 28/06/2013
		 * 
		 * 
		 * try { if (isOk) { if (response != null) { switch (id) { case
		 * CollectItConstants.LOGIN_SCREEN_ID: // save user id for logged in
		 * user String userIdParsedMsg = UtilityMethods .isUserIdParsed(
		 * context, response,
		 * SingleOptionAlertWithoutTitle.START_SIGNUP_FRAGMENT); if user id has
		 * been parsed successfully if
		 * (userIdParsedMsg.equalsIgnoreCase(getResources()
		 * .getString(R.string.success))) { // user id has been parsed
		 * successfully // fetch user data with background process new
		 * GetUserDetail(context, null, 0);
		 * 
		 * // start tab fragments startActivity(new Intent(context,
		 * HomeTabFragmentActivity.class)
		 * .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		 * Intent.FLAG_ACTIVITY_NEW_TASK));
		 * UtilityMethods.finishCurrentActivity(context); }
		 * 
		 * if user id not parsed successfully and any error message not been
		 * displayed
		 * 
		 * else if (!userIdParsedMsg .equalsIgnoreCase(getResources().getString(
		 * R.string.success)) && !userIdParsedMsg
		 * .equalsIgnoreCase(getResources() .getString(
		 * R.string.success_error))) { new
		 * SingleOptionAlertWithoutTitle(context, getResources()
		 * .getString(R.string.try_again),
		 * getResources().getString(R.string.ok), 0); } else { // nothing to do
		 * } break; default: break; } } else { new ViewToastMsg(context,
		 * getResources().getString( R.string.connection_error_collectit)); } }
		 * else if (exception.equalsIgnoreCase(getResources().getString(
		 * R.string.connection_error_internet))) { new ViewToastMsg(context,
		 * exception); } else { new ViewToastMsg(context,
		 * getResources().getString( R.string.connection_error)); } } catch
		 * (Exception e) { e.printStackTrace(); } finally { // finally close the
		 * progress dialog // stop progress dialog
		 * DialogProgressCustom.getInstance().stopProgressDialog();
		 * 
		 * // reset values for signup through id
		 * CollectItSharedDataModel.getInstance().setSignupThroughId(0); }
		 */

	}

	/** Functionality to display image gallery */
	private class GridAdapter extends BaseAdapter {
		class ViewHolder {
			ImageView imageView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageListArray.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			View rowView = view;
			if (rowView == null) {
				rowView = LayoutInflater.from(context).inflate(
						R.layout.frag_lagin_home_image_row, null);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.imageView = (ImageView) rowView
						.findViewById(R.id.login_home_image_gallery_img);
				rowView.setTag(viewHolder);
			}

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			viewHolder.imageView.setImageResource(imageListArray[position]);

			return rowView;
		}
	}

}
