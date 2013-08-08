package com.collect.it.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.collect.it.R;
import com.collect.it.adapter.SearchCollectionListAdapter;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.interfaces.OnCollectionItemProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.model.ItemDataModel;
import com.collect.it.model.SearchItemDataModel;
import com.collect.it.utils.GetUserCollectionItemList;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.UtilityMethods;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

/**
 * This fragment used to display the search results for the items
 */
public class SearchItemListFragment extends CollectItAbstractFragment implements
		OnItemClickListener {
	/**
	 * Declare class variables
	 */
	private FragmentActivity context;

	private GridView itemGridView;
	private ListView collectionListView;

	// private SearchItemDataModel searchItemList;

	private GridAdapter itemListAdapter;
	private SearchCollectionListAdapter collectionListAdapter;

	private Button itemsButton, collectionsButton, usersButton;

	private DisplayMetrics displayMatrices;
	private int height, width;
	private int offset = 0;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_search_list, null);

		itemGridView = (GridView) view.findViewById(R.id.home_gridview);
		itemGridView.setOnItemClickListener(this);
		// itemGridView.setOnScrollListener(new EndlessScrollListener());
		boolean pauseOnScroll = false; // or true
		boolean pauseOnFling = true; // or false
		itemGridView.setOnScrollListener(new EndlessScrollListener(
				pauseOnScroll, pauseOnFling));

		collectionListView = (ListView) view
				.findViewById(R.id.frag_search_collection_listview);

		itemsButton = (Button) view.findViewById(R.id.search_items_btn);
		collectionsButton = (Button) view
				.findViewById(R.id.search_collections_btn);
		usersButton = (Button) view.findViewById(R.id.search_users_btn);

		itemsButton.setOnClickListener(this);
		collectionsButton.setOnClickListener(this);
		usersButton.setOnClickListener(this);

		displayMatrices = CollectItSharedDataModel.getInstance()
				.getDisplayMetrics();

		// set height and width of item image
		width = (displayMatrices.widthPixels / 2 - 20);
		height = (displayMatrices.widthPixels / 2 - 20);

		return view;
	}

	/**
	 * Functionality to set image backgrounds for the buttons
	 * 
	 * @param isItems
	 *            true if item button is selected
	 * @param isCollections
	 *            true if collection button is selected
	 * @param isUsers
	 *            true if user button is selected
	 * */
	private void setButtonBackground(boolean isItems, boolean isCollections,
			boolean isUsers) {
		itemsButton.setBackgroundResource(R.drawable.tab_inactive_btn);
		usersButton.setBackgroundResource(R.drawable.tab_inactive_btn);
		collectionsButton.setBackgroundResource(R.drawable.tab_inactive_btn);
		if (isItems) {
			itemsButton.setBackgroundResource(R.drawable.tab_active_btn);
		} else if (isCollections) {
			collectionsButton.setBackgroundResource(R.drawable.tab_active_btn);
		} else if (isUsers) {
			usersButton.setBackgroundResource(R.drawable.tab_active_btn);
		}

	}

	/**
	 * Functionality to set visibility of item list, collection list, userlist
	 * 
	 * @param isItemListVisible
	 *            true if need to set visible item list
	 * @param isCollectionListVisible
	 *            true if need to set collection list visible to user
	 * */
	void setVisibilityItemCollectionUser(boolean isItemListVisible,
			boolean isCollectionListVisible) {

		if (isItemListVisible) {
			itemGridView.setVisibility(View.VISIBLE);
			collectionListView.setVisibility(View.GONE);
		} else if (isCollectionListVisible) {
			itemGridView.setVisibility(View.GONE);
			collectionListView.setVisibility(View.VISIBLE);
		}
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
		/*
		 * try { SearchItemListFragment searchListFrag =
		 * (SearchItemListFragment) context
		 * .getSupportFragmentManager().findFragmentByTag(
		 * FragmentTagNames.SEARCH_ITEM_LIST.name());
		 * 
		 * if (searchListFrag != null) { FragmentTransaction ft =
		 * context.getSupportFragmentManager() .beginTransaction();
		 * ft.detach(searchListFrag); ft.commit(); } } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
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
		case R.id.search_items_btn:
			setButtonBackground(true, false, false);
			setVisibilityItemCollectionUser(true, false);
			// set values
			notifyItemListAdapter();
			break;
		case R.id.search_collections_btn:
			if ((CollectItSharedDataModel.getInstance()
					.getSearchCollectionList() != null)
					&& (CollectItSharedDataModel.getInstance()
							.getSearchCollectionList().size() > 0)
					&& (CollectItSharedDataModel.getInstance()
							.getSearchCollectionList().get(0)
							.getCollectionList().size() > 0)) {
				setButtonBackground(false, true, false);
				setVisibilityItemCollectionUser(false, true);
				setCollectionListAdapter();
			} else {
				new ViewToastMsg(context, getResources().getString(
						R.string.no_result_founds));
			}
			break;
		case R.id.search_users_btn:
			// setButtonBackground(false, false, true);
			new SingleOptionAlertWithoutTitle(context, getResources()
					.getString(R.string.coming_soon), getResources().getString(
					R.string.ok), 0);
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
		super.onResume();

		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		// Google analytics
		// Send a screen view when the Activity is displayed to the user.
		GoogleAnalyticModel
				.getInstance()
				.getTracker(context)
				.sendView(
						getResources()
								.getString(R.string.ga_search_item_screen));

		// hide setting icon on top bar
		UtilityMethods.setSettingIconVisibility(context, View.GONE);

		// hide back button
		UtilityMethods.setBackButtonVisibility(context, View.VISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.SEARCH_SCREEN_ID);

		// set values
		setItemListAdapter();
	}

	/**
	 * Adapter class that will display the list of items added
	 */
	private class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			/*
			 * if (searchItemList != null && searchItemList.getItemSearchList()
			 * != null && searchItemList.getItemSearchList().size() > 0) {
			 * return searchItemList.getItemSearchList().size(); } else { return
			 * 0; }
			 */

			if (CollectItSharedDataModel.getInstance().getItemSearchList() != null
					&& CollectItSharedDataModel.getInstance()
							.getItemSearchList().size() > 0) {
				return CollectItSharedDataModel.getInstance()
						.getItemSearchList().size();
			} else {
				return 0;
			}

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

				viewHolder.itemImageProgressBar = (ProgressBar) rowView
						.findViewById(R.id.collection_item_data_itemimage_progressBar);

				viewHolder.itemImageFrameLayout = (FrameLayout) rowView
						.findViewById(R.id.collection_item_data_item_img_frame);

				rowView.setTag(viewHolder);
			}

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();

			// set values

			viewHolder.itemImageFrameLayout
					.setLayoutParams(new FrameLayout.LayoutParams(width, height));

			ItemDataModel itemListData = CollectItSharedDataModel.getInstance()
					.getItemSearchList().get(position);

			String itemImageUrl = itemListData.getItemImage();
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

			String userImageUrl = itemListData.getUserImage();
			if (userImageUrl != null && !userImageUrl.equalsIgnoreCase("null")
					&& !userImageUrl.equals("")) {
				ImageUtils.getInstance(context).setImageUrlToView(userImageUrl,
						viewHolder.userImageView, null,
						R.drawable.signup_image_placeholder, false, true);
			} else {
				viewHolder.userImageView
						.setImageResource(R.drawable.signup_image_placeholder);
			}

			String userName = itemListData.getUserName();
			if (userName == null || userName.equalsIgnoreCase("null")) {
				userName = "";
			}
			viewHolder.userNameTextView.setText(userName);

			String itemDesc = itemListData.getItemDescription();
			if (itemDesc == null || itemDesc.equalsIgnoreCase("null")) {
				itemDesc = "";
			}
			viewHolder.itemDescTextView.setText(itemDesc);

			return rowView;
		}

		private class ViewHolder {
			ImageView itemImageView, touchImageView, userImageView;
			TextView touchCountTextView, itemDescTextView, userNameTextView;
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
				CollectItSharedDataModel.getInstance().getItemSearchList()
						.get(position).getItemId());
		UtilityMethods.replaceFragment(new ItemDetailMainFragment(), context,
				R.id.tab_content, true, true,
				FragmentTagNames.ITEM_DETAIL_MAIN.name(), bundle);
	}

	/**
	 * Functionality to set adapter
	 */
	public void setItemListAdapter() {

		// set item list
		setSearchArrayList();

		notifyItemListAdapter();

		if (CollectItSharedDataModel.getInstance().getItemSearchList() != null
				&& CollectItSharedDataModel.getInstance().getItemSearchList()
						.size() > 0) {
			offset = CollectItSharedDataModel.getInstance().getItemSearchList()
					.size() - 20;
		}

	}

	/** Functionality to set adapter for the item list */
	void notifyItemListAdapter() {
		if (itemListAdapter == null) {
			itemListAdapter = new GridAdapter();
			itemGridView.setAdapter(itemListAdapter);
		} else {
			itemListAdapter.notifyDataSetChanged();
		}
	}

	/** Functionality to set adapter for the collection list */
	void setCollectionListAdapter() {
		if (collectionListAdapter == null) {
			collectionListAdapter = new SearchCollectionListAdapter();
			collectionListView.setAdapter(collectionListAdapter);
		} else {
			collectionListAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * Functionality to set array list
	 */
	void setSearchArrayList() {

		/*
		 * if (CollectItSharedDataModel.getInstance().getSearchItemList() !=
		 * null) { if
		 * (CollectItSharedDataModel.getInstance().getSearchItemList() .size() >
		 * 0) { searchItemList = CollectItSharedDataModel.getInstance()
		 * .getSearchItemList().get(0); } else { searchItemList = new
		 * SearchItemDataModel(); }
		 * 
		 * }
		 */

		try {
			if (CollectItSharedDataModel.getInstance().getSearchItemList() != null
					&& CollectItSharedDataModel.getInstance()
							.getSearchItemList().size() > 0) {
				String count = CollectItSharedDataModel.getInstance()
						.getSearchItemList().get(0).getTotalItemCount();
				if (count != null && !count.equals("")
						&& !count.equalsIgnoreCase("null")) {
					itemsButton.setText(getResources()
							.getString(R.string.items) + "\n" + count);
				} else {
					itemsButton.setText(getResources()
							.getString(R.string.items) + "\n0");
				}
			} else {
				itemsButton.setText(getResources().getString(R.string.items)
						+ "\n0");
			}

			if (CollectItSharedDataModel.getInstance().getSearchUserList() != null
					&& CollectItSharedDataModel.getInstance()
							.getSearchUserList().size() > 0) {
				String count = CollectItSharedDataModel.getInstance()
						.getSearchUserList().get(0).getTotalUserCount();
				if (count != null && !count.equals("")
						&& !count.equalsIgnoreCase("null")) {
					usersButton.setText(getResources()
							.getString(R.string.users) + "\n" + count);
				} else {
					usersButton.setText(getResources()
							.getString(R.string.users) + "\n0");
				}
			} else {
				usersButton.setText(getResources().getString(R.string.users)
						+ "\n0");
			}

			if (CollectItSharedDataModel.getInstance()
					.getSearchCollectionList() != null
					&& CollectItSharedDataModel.getInstance()
							.getSearchCollectionList().size() > 0) {
				String count = CollectItSharedDataModel.getInstance()
						.getSearchCollectionList().get(0)
						.getTotalCollectionCount();
				if (count != null && !count.equals("")
						&& !count.equalsIgnoreCase("null")) {
					collectionsButton.setText(getResources().getString(
							R.string.collections_tab)
							+ "\n" + count);
				} else {
					collectionsButton.setText(getResources().getString(
							R.string.collections_tab)
							+ "\n0");
				}
			} else {
				collectionsButton.setText(getResources().getString(
						R.string.collections_tab)
						+ "\n0");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to get more items and collection and users if available
	 */
	private class EndlessScrollListener extends PauseOnScrollListener implements
			OnScrollListener {

		public EndlessScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
			super(pauseOnScroll, pauseOnFling);
			// TODO Auto-generated constructor stub
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
					if (totalItemCount >= 18) {
						loading = false;
					}

					previousTotal = totalItemCount;
					offset += 20;
				}
			}
			if (!loading
					&& (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
				// I load the next page of gigs using a background task,
				// but you can call any function here.
				// new LoadGigsTask().execute(currentPage + 1);
				// parseData();
				// fetch item list array from server
				loading = true;
				SearchFragment searchFrag = (SearchFragment) context
						.getSupportFragmentManager().findFragmentByTag(
								FragmentTagNames.SEARCH_TAB.name());
				if (searchFrag != null) {
					searchFrag.hitServer(null, offset);
				}
			}
			/*
			 * } isScroll = true;
			 */
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		}
	}

	/**
	 * Functionality to clear button counts
	 */
	public void clearCounts() {
		itemsButton.setText(getResources().getString(R.string.items) + "\n0");
		usersButton.setText(getResources().getString(R.string.users) + "\n0");
		collectionsButton.setText(getResources().getString(
				R.string.collections_tab)
				+ "\n0");
	}

}
