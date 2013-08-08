package com.collect.it.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.collect.it.R;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.interfaces.OnCollectionItemProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.model.ItemDataModel;
import com.collect.it.utils.GetUserCollectionItemList;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.UtilityMethods;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

/**
 * This fragment is used to display as a landing screen for the tabs
 */
public class HomeFragment extends CollectItAbstractFragment implements
		OnCollectionItemProcess, OnItemClickListener {
	/**
	 * Declare class variables
	 */
	private FragmentActivity context;

	private GridView itemGridView;
	private TextView textView;

	private DisplayMetrics displayMatrices;

	private int width, height;
	private int offset = 0;

	private GridAdapter gridAdapter;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_home, null);

		itemGridView = (GridView) view.findViewById(R.id.home_gridview);
		itemGridView.setOnItemClickListener(this);
		boolean pauseOnScroll = false; // or true
		boolean pauseOnFling = true; // or false
		itemGridView.setOnScrollListener(new EndlessScrollListener(
				pauseOnScroll, pauseOnFling));

		displayMatrices = CollectItSharedDataModel.getInstance()
				.getDisplayMetrics();

		// set height and width of item image
		width = (displayMatrices.widthPixels / 2 - 20);
		height = (displayMatrices.widthPixels / 2 - 20);

		textView = (TextView) view.findViewById(R.id.frag_home_msg_txt);

		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();

		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		// Google analytics
		// Send a screen view when the Activity is displayed to the user.
		GoogleAnalyticModel.getInstance().getTracker(context)
				.sendView(getResources().getString(R.string.ga_home_screen));

		// hide setting icon on top bar
		UtilityMethods.setSettingIconVisibility(context, View.GONE);

		// hide back button
		UtilityMethods.setBackButtonVisibility(context, View.INVISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.HOME_SCREEN_ID);

		if (CollectItSharedDataModel.getInstance().getHomeItemList() != null
				&& CollectItSharedDataModel.getInstance().getHomeItemList()
						.size() > 0) {
			offset = CollectItSharedDataModel.getInstance().getHomeItemList()
					.size() - 10;
			setAdapter();
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
			textView.setVisibility(View.GONE);
			setAdapter();
		} else {
			textView.setVisibility(View.VISIBLE);
		}
	}

	/** Functionality to set adapter */
	void setAdapter() {
		if (gridAdapter == null) {
			gridAdapter = new GridAdapter();
			itemGridView.setAdapter(gridAdapter);
		} else {
			gridAdapter.notifyDataSetChanged();
		}

	}

	/** if any error with collection item list */
	@Override
	public void onCollectionItemError(String errorMsg) {
		DialogProgressCustom.getInstance().stopProgressDialog();
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
		gridAdapter = null;
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
			try {
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
						.setLayoutParams(new FrameLayout.LayoutParams(width,
								height));

				// set values
				String itemImageUrl = CollectItSharedDataModel.getInstance()
						.getHomeItemList().get(position).getItemImage();
				if (itemImageUrl != null
						&& !itemImageUrl.equalsIgnoreCase("null")
						&& !itemImageUrl.equals("")) {
					ImageUtils.getInstance(context).setImageUrlToView(
							itemImageUrl, viewHolder.itemImageView,
							viewHolder.itemImageProgressBar,
							R.drawable.app_icon_gold, false, true);
				} else {
					viewHolder.itemImageView
							.setImageResource(R.drawable.app_icon_gold);
				}

				String userImageUrl = CollectItSharedDataModel.getInstance()
						.getHomeItemList().get(position).getUserImage();
				if (userImageUrl != null
						&& !userImageUrl.equalsIgnoreCase("null")
						&& !userImageUrl.equals("")) {
					ImageUtils.getInstance(context).setImageUrlToView(
							userImageUrl, viewHolder.userImageView, null,
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
			} catch (Exception e) {
				e.printStackTrace();
			}
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
						HomeFragment.this);
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
