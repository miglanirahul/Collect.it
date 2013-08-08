package com.collect.it.fragments;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collect.it.R;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.model.ItemDataModel;
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.ParsingUtils;
import com.collect.it.utils.UtilityMethods;

/**
 * This fragment class used to display the detail view of an item
 */
public class ItemDetailMainFragment extends CollectItAbstractFragment {
	/**
	 * Declare variables
	 */
	private FragmentActivity context;

	private ImageView userImageView, itemImageView, touchitBarImageView;
	private TextView userNameTextView;
	private Button detailButton, commentsButton, editItemButton;

	private ArrayList<ItemDataModel> itemDataArrayList;

	@Override
	public View initialization(LayoutInflater inflater) {
		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();
		View view = inflater.inflate(R.layout.frag_item_detail_main, null);

		userImageView = (ImageView) view
				.findViewById(R.id.item_detail_user_img);
		itemImageView = (ImageView) view
				.findViewById(R.id.item_detail_item_img);

		DisplayMetrics displayMatrices = CollectItSharedDataModel.getInstance()
				.getDisplayMetrics();
		itemImageView.setLayoutParams(new LinearLayout.LayoutParams(
				displayMatrices.widthPixels - 20,
				displayMatrices.widthPixels + 20));

		userNameTextView = (TextView) view
				.findViewById(R.id.item_detail_user_name_txt);

		detailButton = (Button) view.findViewById(R.id.item_detail_btn);
		commentsButton = (Button) view
				.findViewById(R.id.item_detail_comment_btn);

		detailButton.setOnClickListener(this);
		commentsButton.setOnClickListener(this);

		editItemButton = (Button) view.findViewById(R.id.item_detail_edit_btn);
		editItemButton.setOnClickListener(this);

		touchitBarImageView = (ImageView) view
				.findViewById(R.id.item_detail_touchit_img);
		touchitBarImageView.setOnClickListener(this);

		return view;
	}

	/** click event functionality */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.item_detail_comment_btn:
			/*
			 * setButtonImage(false, true); replaceFragment(false);
			 */
			new SingleOptionAlertWithoutTitle(context, getResources()
					.getString(R.string.coming_soon), getResources().getString(
					R.string.ok), 0);
			break;
		case R.id.item_detail_btn:
			setButtonImage(true, false);
			replaceFragment(true);
			break;
		case R.id.item_detail_edit_btn:
			editItemFunc();
			break;
		case R.id.item_detail_touchit_img:
			new SingleOptionAlertWithoutTitle(context, getResources()
					.getString(R.string.coming_soon), getResources().getString(
					R.string.ok), 0);
			break;
		default:
			break;
		}
	}

	/**
	 * Functionality to set button background image
	 * 
	 * @param isDetailButton
	 *            true if detail button need to display as clicked
	 * @param isCommentButton
	 *            true if comment button need to display as clicked
	 */
	void setButtonImage(boolean isDetailButton, boolean isCommentButton) {
		if (isDetailButton) {
			detailButton.setBackgroundResource(R.drawable.tab_active_btn);
		} else {
			detailButton.setBackgroundResource(R.drawable.tab_inactive_btn);
		}

		if (isCommentButton) {
			commentsButton.setBackgroundResource(R.drawable.tab_active_btn);
		} else {
			commentsButton.setBackgroundResource(R.drawable.tab_inactive_btn);
		}
	}

	/**
	 * Functionality to replace fragment for details/comments view
	 * 
	 * @param isDetailFragment
	 *            true if detail fragment need to display so that associated tag
	 *            can be assigned
	 */
	void replaceFragment(boolean isDetailFragment) {
		if (isDetailFragment) {
			UtilityMethods.replaceFragment(new ItemDetailFragment(), context,
					R.id.item_detail_frament_framelayout, true, false,
					FragmentTagNames.ITEM_DETAIL.name(), null);

			// refresh the values
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					setValues(false);
				}
			}, 1000);

		} else {
			UtilityMethods.replaceFragment(new CommentFragment(), context,
					R.id.item_detail_frament_framelayout, true, false,
					FragmentTagNames.ITEM_DETAIL_COMMENT.name(), null);
		}

	}

	/**
	 * Functionality to add fragment for details/comments view
	 */
	void addFragment() {
		UtilityMethods.replaceFragment(new ItemDetailFragment(), context,
				R.id.item_detail_frament_framelayout, true, false,
				FragmentTagNames.ITEM_DETAIL.name(), null);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
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
								.getString(R.string.ga_item_detail_screen));

		// hide setting icon on top bar
		UtilityMethods.setSettingIconVisibility(context, View.GONE);

		// show back button
		UtilityMethods.setBackButtonVisibility(context, View.VISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.ITEM_DETAIL_SCREEN_ID);

		// add detail fragment initially
		setButtonImage(true, false);
		addFragment();

		Bundle bundle = getArguments();

		if (bundle != null) {
			if (bundle.containsKey(CollectItConstants.BUNDLE_ITEM_ID_KEY)) {
				DialogProgressCustom.getInstance().startProgressDialog(context,
						true);
				new WebServiceAsyncHttpPostJson(
						context,
						CollectItServerConstants.WEBSERVICE_GET_ITEM_DETAIL_BY_ITEM_ID,
						CollectItConstants.ITEM_DETAIL_SCREEN_ID,
						this,
						createItemDeatilJson(bundle
								.getString(CollectItConstants.BUNDLE_ITEM_ID_KEY)))
						.execute();
			}
		} else {
			new SingleOptionAlertWithoutTitle(context, getResources()
					.getString(R.string.item_detail_error_msg), getResources()
					.getString(R.string.ok),
					SingleOptionAlertWithoutTitle.CLOSE_CURRENT_FRAGMENT);
		}

	}

	/**
	 * Functionality to create json object for the item detail web service
	 * parameters
	 * 
	 * @param itemId
	 *            item id the detail is required for
	 */
	private JSONObject createItemDeatilJson(String itemId) {
		JSONObject json = new JSONObject();
		try {
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_ID, itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Functionality of server response
	 */
	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		// TODO Auto-generated method stub
		super.getServerValues(response, id, isOk, exception);
		try {
			if (isOk) {
				if (response != null) {
					itemDataArrayList = ParsingUtils
							.parseItemDetailResponse(response);

					if (itemDataArrayList != null
							&& itemDataArrayList.size() > 0) {
						CollectItSharedDataModel.getInstance()
								.setItemDetailList(itemDataArrayList);
						setValues(true);
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
			// close the dialog if opened
			DialogProgressCustom.getInstance().stopProgressDialog();
		}
	}

	/**
	 * Functionality to set values
	 * 
	 * @param isAllData
	 *            true if need to display the values for the details screen
	 *            components and main details screen component values. false
	 *            will display only detail fragment screen values
	 */
	void setValues(boolean isAllData) {
		try {

			if (isAllData) {
				userNameTextView
						.setText(itemDataArrayList.get(0).getUserName());

				String userImageUrl = itemDataArrayList.get(0).getUserImage();
				if (userImageUrl != null && !userImageUrl.equals("")
						&& !userImageUrl.equalsIgnoreCase("null")) {
					ImageUtils.getInstance(context).setImageUrlToView(
							userImageUrl, userImageView, null,
							R.drawable.signup_image_placeholder, false, true);
				} else {
					userImageView
							.setImageResource(R.drawable.signup_image_placeholder);
				}

				String itemImageUrl = itemDataArrayList.get(0).getItemImage();
				if (itemImageUrl != null && !itemImageUrl.equals("")
						&& !itemImageUrl.equalsIgnoreCase("null")) {
					ImageUtils.getInstance(context).setImageUrlToView(
							itemImageUrl, itemImageView, null,
							R.drawable.app_icon_gold, false, true);
				} else {
					itemImageView.setImageResource(R.drawable.app_icon_gold);
				}
			}

			try {
				ItemDetailFragment detailFrag = (ItemDetailFragment) context
						.getSupportFragmentManager().findFragmentByTag(
								FragmentTagNames.ITEM_DETAIL.name());

				if (detailFrag != null) {
					detailFrag.setValues(itemDataArrayList);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			// visibility of edit button
			if (itemDataArrayList
					.get(0)
					.getUserId()
					.equalsIgnoreCase(
							CollectItSharedDataModel.getInstance().getUserId())) {
				editItemButton.setVisibility(View.VISIBLE);
			} else {
				editItemButton.setVisibility(View.INVISIBLE);

				try {
					TitleBalFragment topFragment = (TitleBalFragment) context
							.getSupportFragmentManager().findFragmentById(
									R.id.main_frag_title_frag);
					if (topFragment != null) {
						topFragment.setShareIconVisibility(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality for the edit item button
	 */
	void editItemFunc() {
		if (itemDataArrayList != null && itemDataArrayList.size() > 0) {

			CollectItSharedDataModel.getInstance().setEditItemDetailList(
					itemDataArrayList);
			UtilityMethods.replaceFragment(new EditItemDetailFragment(),
					context, R.id.tab_content, true, true,
					FragmentTagNames.EDIT_ITEM_DETAIL.name(), null);

		}
	}
}
