package com.collect.it.fragments;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.collect.it.R;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.model.ItemDataModel;
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.ParsingUtils;
import com.collect.it.utils.UtilityMethods;

public class CollectionItemListFragment extends CollectItAbstractFragment
		implements OnItemClickListener {
	/**
	 * Declare class variables
	 */
	private FragmentActivity context;

	private GridView itemGridView;
	private TextView textView;

	private ArrayList<ItemDataModel> collectionItemList;

	private DisplayMetrics displayMatrices;

	private int width, height;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_home, null);

		itemGridView = (GridView) view.findViewById(R.id.home_gridview);
		itemGridView.setOnItemClickListener(this);

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
		GoogleAnalyticModel
				.getInstance()
				.getTracker(context)
				.sendView(
						getResources().getString(
								R.string.ga_collection_item_list_screen));

		// hide setting icon on top bar
		UtilityMethods.setSettingIconVisibility(context, View.GONE);

		// show back button
		UtilityMethods.setBackButtonVisibility(context, View.VISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.COLLECTION_ITEM_LIST_SCREEN_ID);

		Bundle bundle = getArguments();

		if (bundle != null) {
			if (bundle.containsKey(CollectItConstants.BUNDLE_COLLECTION_ID_KEY)) {
				collectionItemList = new ArrayList<ItemDataModel>();
				// fetch item list array from server
				DialogProgressCustom.getInstance().startProgressDialog(context,
						true);
				// hit web service to get collections item list
				new WebServiceAsyncHttpPostJson(
						context,
						CollectItServerConstants.WEBSERVICE_GET_COLLECTION_ITEM,
						CollectItConstants.COLLECTION_ITEM_LIST_SCREEN_ID,
						this,
						createParameterJSon(bundle
								.getString(CollectItConstants.BUNDLE_COLLECTION_ID_KEY)))
						.execute();
			}
		} else {
			new SingleOptionAlertWithoutTitle(context, getResources()
					.getString(R.string.collection_item_list_error_msg),
					getResources().getString(R.string.ok),
					SingleOptionAlertWithoutTitle.CLOSE_CURRENT_FRAGMENT);
		}

	}

	/**
	 * Functionality to create json for server parameters
	 * 
	 * @param collectionId
	 *            collection id to get associated items
	 * 
	 * @return json object
	 * */
	private JSONObject createParameterJSon(String collectionId) {
		JSONObject json = new JSONObject();
		try {
			json.put(
					CollectItServerConstants.WEBSERVICE_KEY_ITEM_COLLECTION_ID,
					collectionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Adapter class that will display the list of items added
	 */
	private class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return collectionItemList.size();
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
				String itemImageUrl = collectionItemList.get(position)
						.getItemImage();
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

				String userImageUrl = collectionItemList.get(position)
						.getUserImage();
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

				String userName = collectionItemList.get(position)
						.getUserName();
				if (userName == null || userName.equalsIgnoreCase("null")) {
					userName = "";
				}
				viewHolder.userNameTextView.setText(userName);

				String itemDesc = collectionItemList.get(position)
						.getItemDescription();
				if (itemDesc == null || itemDesc.equalsIgnoreCase("null")) {
					itemDesc = "";
				}
				viewHolder.itemDescTextView.setText(itemDesc);

				String aboutMe = collectionItemList.get(position)
						.getUserAboutMe();
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
		bundle.putString(CollectItConstants.BUNDLE_ITEM_ID_KEY,
				collectionItemList.get(position).getItemId());
		UtilityMethods.replaceFragment(new ItemDetailMainFragment(), context,
				R.id.tab_content, true, true,
				FragmentTagNames.ITEM_DETAIL_MAIN.name(), bundle);
	}

	/** server response */
	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		// TODO Auto-generated method stub
		super.getServerValues(response, id, isOk, exception);

		try {
			if (isOk) {
				if (response != null) {
					collectionItemList = ParsingUtils
							.parseCollectionItems(response);
					if (collectionItemList != null
							&& collectionItemList.size() > 0) {
						textView.setVisibility(View.GONE);
						itemGridView.setAdapter(new GridAdapter());
					} else {
						textView.setVisibility(View.VISIBLE);
					}
				} else {
					new ViewToastMsg(context, getResources().getString(
							R.string.connection_error_collectit));
				}
			} else {
				new ViewToastMsg(context, getResources().getString(
						R.string.connection_error));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();
		}

	}

}
