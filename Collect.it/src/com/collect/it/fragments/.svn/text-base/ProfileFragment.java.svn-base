package com.collect.it.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collect.it.R;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.interfaces.OnCollectionItemProcess;
import com.collect.it.interfaces.OnUserDetailProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.model.ItemDataModel;
import com.collect.it.model.UserDataModel;
import com.collect.it.utils.GetUserCollectionItemList;
import com.collect.it.utils.GetUserDetail;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.UtilityMethods;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

/**
 * This fragment used to display user profile screen
 */
public class ProfileFragment extends CollectItAbstractFragment implements
		OnUserDetailProcess, OnCollectionItemProcess, OnItemClickListener {
	/**
	 * Declare class variables
	 */
	private FragmentActivity context;

	private ImageView userImageView, googlePlusShareImageView,
			facebookShareImageView, twitterShareImageView,
			collectitShareImageView;
	private TextView userNameTextView, aboutmeTextView;
	private Button itemsButton, collectionsButton, touchesButton;
	private ProgressBar imageProgressBar;
	private GridView itemGridView;

	private DisplayMetrics displayMatrices;
	private int height, width;
	private int offset = 0;

	private GridAdapter itemGridAdapter;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_profile, null);

		userImageView = (ImageView) view.findViewById(R.id.profile_img);
		userNameTextView = (TextView) view
				.findViewById(R.id.profile_username_txt);
		aboutmeTextView = (TextView) view
				.findViewById(R.id.profile_aboutme_txt);

		view.findViewById(R.id.profile_img_txt).setOnClickListener(this);

		imageProgressBar = (ProgressBar) view
				.findViewById(R.id.profile_imgprogressBar);

		itemGridView = (GridView) view.findViewById(R.id.profile_gridview);
		itemGridView.setOnItemClickListener(this);
		// itemGridView.setOnScrollListener(new EndlessScrollListener());
		boolean pauseOnScroll = false; // or true
		boolean pauseOnFling = true; // or false
		itemGridView.setOnScrollListener(new EndlessScrollListener(
				pauseOnScroll, pauseOnFling));

		displayMatrices = CollectItSharedDataModel.getInstance()
				.getDisplayMetrics();

		itemsButton = (Button) view.findViewById(R.id.profile_items_btn);
		collectionsButton = (Button) view
				.findViewById(R.id.profile_collections_btn);
		touchesButton = (Button) view.findViewById(R.id.profile_touches_btn);

		itemsButton.setOnClickListener(this);
		collectionsButton.setOnClickListener(this);
		touchesButton.setOnClickListener(this);

		googlePlusShareImageView = (ImageView) view
				.findViewById(R.id.profile_googleplus_share_img);
		facebookShareImageView = (ImageView) view
				.findViewById(R.id.profile_facebook_share_img);
		twitterShareImageView = (ImageView) view
				.findViewById(R.id.profile_twitter_share_img);
		collectitShareImageView = (ImageView) view
				.findViewById(R.id.profile_collectit_share_img);

		displayMatrices = CollectItSharedDataModel.getInstance()
				.getDisplayMetrics();

		// set height and width of item image
		width = (displayMatrices.widthPixels / 2 - 20);
		height = (displayMatrices.widthPixels / 2 - 20);

		return view;
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
		case R.id.profile_img_txt:
			/* start edit profile fragment */
			UtilityMethods.replaceFragment(new EditProfileFragment(), context,
					R.id.tab_content, true, true,
					FragmentTagNames.EDIT_PROFILE.name(), null);
			break;
		case R.id.profile_collections_btn:
		case R.id.profile_touches_btn:
			new SingleOptionAlertWithoutTitle(context, getResources()
					.getString(R.string.coming_soon), getResources().getString(
					R.string.ok), 0);
			break;
		case R.id.profile_items_btn:
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
		try {
			context = CollectItSharedDataModel.getInstance()
					.getCurrentFragmentActivityContext();

			// Google analytics
			// Send a screen view when the Activity is displayed to the user.
			GoogleAnalyticModel
					.getInstance()
					.getTracker(context)
					.sendView(
							getResources()
									.getString(R.string.ga_profile_screen));

			// check if user details are not available then fetch user details
			UserDataModel userDetails = null;
			if (CollectItSharedDataModel.getInstance().getUserDetailList() != null
					&& CollectItSharedDataModel.getInstance()
							.getUserDetailList().size() > 0) {
				userDetails = CollectItSharedDataModel.getInstance()
						.getUserDetailList().get(0);
			}
			if (null == userDetails) {
				// start progress dialog
				DialogProgressCustom.getInstance().startProgressDialog(context,
						true);
				new GetUserDetail(context, this,
						CollectItConstants.PROFILE_SCREEN_ID);
			} else {
				// display user details
				setValues();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();
		}

		// display setting icon on top bar
		UtilityMethods.setSettingIconVisibility(context, View.VISIBLE);

		// hide back button
		UtilityMethods.setBackButtonVisibility(context, View.INVISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.PROFILE_SCREEN_ID);

		if (CollectItSharedDataModel.getInstance().getHomeItemList() != null
				&& CollectItSharedDataModel.getInstance().getHomeItemList()
						.size() > 0) {
			offset = CollectItSharedDataModel.getInstance().getHomeItemList()
					.size() - 10;
			setItemGridAdapter();
		} else {
			offset = 0;
			CollectItSharedDataModel.getInstance().getHomeItemList().clear();
			// fetch item list array from server
			DialogProgressCustom.getInstance().startProgressDialog(context,
					true);
			new GetUserCollectionItemList(context, offset,
					CollectItSharedDataModel.getInstance().getUserId(), this);
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		itemGridAdapter = null;
	}

	/**
	 * Functionality to set values for the logged in user
	 */
	void setValues() {
		UserDataModel userDetails = CollectItSharedDataModel.getInstance()
				.getUserDetailList().get(0);

		String uName = userDetails.getUserName();
		if (uName == null || uName.equalsIgnoreCase("null")) {
			uName = "";
		}
		userNameTextView.setText(uName);
		aboutmeTextView.setText(userDetails.getAbout());

		ImageUtils.getInstance(context).setImageUrlToView(
				userDetails.getimageUrl(), userImageView, imageProgressBar,
				R.drawable.signup_image_placeholder, false, true);

		// set the visibility of share icons
		try {
			if (userDetails != null) {
				if (userDetails.getFacebookSharingStatus().equals("1")) {
					facebookShareImageView.setVisibility(View.VISIBLE);
				} else {
					facebookShareImageView.setVisibility(View.GONE);
				}

				if (userDetails.getGooglePlusSharingStatus().equals("1")) {
					googlePlusShareImageView.setVisibility(View.VISIBLE);
				} else {
					googlePlusShareImageView.setVisibility(View.GONE);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUserDetailError(String errorMsg) {
		new SingleOptionAlertWithoutTitle(context, errorMsg, getResources()
				.getString(R.string.ok), 0);
	}

	@Override
	public void onUserDetailStoredSuccessfully(int taskId) {
		try {
			// after getting user details
			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();

			// display information
			setValues();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** After fetching the collection item list for the server */
	@Override
	public void getCollectionItemList(
			ArrayList<ItemDataModel> collectionItemList) {
		DialogProgressCustom.getInstance().stopProgressDialog();

		if (CollectItSharedDataModel.getInstance().getHomeItemList() == null
				|| CollectItSharedDataModel.getInstance().getHomeItemList()
						.size() < 1) {
			CollectItSharedDataModel.getInstance().setHomeItemList(
					collectionItemList);
		} else {
			CollectItSharedDataModel.getInstance().getHomeItemList()
					.addAll(collectionItemList);
		}

		if (CollectItSharedDataModel.getInstance().getHomeItemList().size() > 0) {
			setItemGridAdapter();
		}
	}

	/** if any error with collection item list */
	@Override
	public void onCollectionItemError(String errorMsg) {
		DialogProgressCustom.getInstance().stopProgressDialog();
	}

	/** Functionality to set adapter */
	void setItemGridAdapter() {
		if (itemGridAdapter == null) {
			itemGridAdapter = new GridAdapter();
			itemGridView.setAdapter(itemGridAdapter);
		} else {
			itemGridAdapter.notifyDataSetChanged();
		}

	}

	/**
	 * Adapter class that will display the list of items added
	 */
	private class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return CollectItSharedDataModel.getInstance().getHomeItemList()
					.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			View rowView = view;
			if (rowView == null) {
				rowView = LayoutInflater.from(context).inflate(
						R.layout.collection_item_data, null);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.itemDescTextView = (TextView) rowView
						.findViewById(R.id.collection_item_data_item_desc_txt);
				viewHolder.touchCountTextView = (TextView) rowView
						.findViewById(R.id.collection_item_data_touches_count_txt);
				viewHolder.userNameTextView = (TextView) rowView
						.findViewById(R.id.collection_item_data_user_name_txt);
				viewHolder.itemImageView = (ImageView) rowView
						.findViewById(R.id.collection_item_data_item_img);
				viewHolder.touchImageView = (ImageView) rowView
						.findViewById(R.id.collection_item_data_touches_img);
				viewHolder.userImageView = (ImageView) rowView
						.findViewById(R.id.collection_item_data_user_img);
				viewHolder.userAboutMeTextView = (TextView) rowView
						.findViewById(R.id.collection_item_data_user_aboutme_txt);

				viewHolder.itemImageProgressBar = (ProgressBar) rowView
						.findViewById(R.id.collection_item_data_itemimage_progressBar);

				viewHolder.itemImageFrameLayout = (FrameLayout) rowView
						.findViewById(R.id.collection_item_data_item_img_frame);

				rowView.setTag(viewHolder);
			}

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();

			viewHolder.itemImageFrameLayout
					.setLayoutParams(new FrameLayout.LayoutParams(width, height));

			// set values
			String itemImageUrl = CollectItSharedDataModel.getInstance()
					.getHomeItemList().get(position).getItemImage();
			if (itemImageUrl != null && !itemImageUrl.equalsIgnoreCase("null")
					&& !itemImageUrl.equals("")) {
				ImageUtils.getInstance(context).setImageUrlToView(itemImageUrl,
						viewHolder.itemImageView,
						viewHolder.itemImageProgressBar,
						R.drawable.app_icon_gold, false, true);
			} else {
				viewHolder.itemImageView
						.setImageResource(R.drawable.app_icon_gold);
			}

			String userImageUrl = CollectItSharedDataModel.getInstance()
					.getHomeItemList().get(position).getUserImage();
			if (userImageUrl != null && !userImageUrl.equalsIgnoreCase("null")
					&& !userImageUrl.equals("")) {
				ImageUtils.getInstance(context).setImageUrlToView(userImageUrl,
						viewHolder.userImageView, null,
						R.drawable.signup_image_placeholder, false, true);
			} else {
				viewHolder.userImageView
						.setImageResource(R.drawable.signup_image_placeholder);
			}

			String userName = CollectItSharedDataModel.getInstance()
					.getHomeItemList().get(position).getUserName();
			if (userName == null || userName.equalsIgnoreCase("null")) {
				userName = "";
			}
			viewHolder.userNameTextView.setText(userName);

			String itemDesc = CollectItSharedDataModel.getInstance()
					.getHomeItemList().get(position).getItemDescription();
			if (itemDesc == null || itemDesc.equalsIgnoreCase("null")) {
				itemDesc = "";
			}
			viewHolder.itemDescTextView.setText(itemDesc);

			String aboutMe = CollectItSharedDataModel.getInstance()
					.getHomeItemList().get(position).getUserAboutMe();
			if (aboutMe == null || aboutMe.equalsIgnoreCase("null")) {
				aboutMe = "";
			}
			viewHolder.userAboutMeTextView.setText(aboutMe);

			return rowView;
		}

		private class ViewHolder {
			ImageView itemImageView, touchImageView, userImageView;
			TextView touchCountTextView, itemDescTextView, userNameTextView,
					userAboutMeTextView;
			ProgressBar itemImageProgressBar;
			FrameLayout itemImageFrameLayout;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Bundle bundle = new Bundle();
		bundle.putString(
				CollectItConstants.BUNDLE_ITEM_ID_KEY,
				CollectItSharedDataModel.getInstance().getHomeItemList()
						.get(position).getItemId());
		UtilityMethods.replaceFragment(new ItemDetailMainFragment(), context,
				R.id.tab_content, true, true,
				FragmentTagNames.ITEM_DETAIL_MAIN.name(), bundle);
	}

	/**
	 * Functionality to get more items if available
	 */
	private class EndlessScrollListener extends PauseOnScrollListener implements
			OnScrollListener {

		public EndlessScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
			super(pauseOnScroll, pauseOnFling);
		}

		private int visibleThreshold = 8;
		private int previousTotal = 0;
		private boolean loading = true;

		/*
		 * public EndlessScrollListener() {
		 * 
		 * }
		 * 
		 * public EndlessScrollListener(int visibleThreshold) {
		 * this.visibleThreshold = visibleThreshold; }
		 */

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			/*
			 * if (isScroll) { if (isListCompleted) visibleThreshold =
			 * -1000000000;
			 */
			if (loading) {
				if (totalItemCount > previousTotal) {
					loading = false;
					previousTotal = totalItemCount;
					offset += 10;
				}
			}
			if (!loading
					&& (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
				// I load the next page of gigs using a background task,
				// but you can call any function here.
				// new LoadGigsTask().execute(currentPage + 1);
				// parseData();
				// fetch item list array from server
				DialogProgressCustom.getInstance().startProgressDialog(context,
						true);
				new GetUserCollectionItemList(context, offset,
						CollectItSharedDataModel.getInstance().getUserId(),
						ProfileFragment.this);
				loading = true;
			}
			/*
			 * } isScroll = true;
			 */
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}
	}
}
