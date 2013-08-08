package com.collect.it.fragments;

import org.json.JSONObject;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;
import com.collect.it.utils.ParsingUtils;
import com.collect.it.utils.UtilityMethods;

/**
 * This fragment used to display search functionality for the application
 */
public class SearchFragment extends CollectItAbstractFragment {
	/**
	 * Declare class variables
	 */
	private FragmentActivity context;

	private EditText searchEditText;

	private final int ITEM_DATA_LIMIT = 20;

	private int offSet = 0;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_search, null);

		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		searchEditText = (EditText) view
				.findViewById(R.id.search_items_edittext);

		view.findViewById(R.id.search_btn).setOnClickListener(this);

		return view;
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

		// Google analytics
		// Send a screen view when the Activity is displayed to the user.
		GoogleAnalyticModel.getInstance().getTracker(context)
				.sendView(getResources().getString(R.string.ga_search_screen));

		// hide setting icon on top bar
		UtilityMethods.setSettingIconVisibility(context, View.GONE);

		// hide back button
		UtilityMethods.setBackButtonVisibility(context, View.INVISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.SEARCH_SCREEN_ID);

		// add search item list fragment
		replaceFragment();

	}

	/** Click event functionality */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		// search text button
		case R.id.search_btn:
			String searchString = searchEditText.getText().toString().trim();
			if (searchString != null && !searchString.equals("")) {
				setItemSearchValues();
				clearCounts();
				hitServer(searchString, 0);
			} else {
				new ViewToastMsg(context, getResources().getString(
						R.string.enter_search_text));
			}

			break;
		case R.id.search_items_btn:
			// setButtonBackground(true, false, false);
			// nothing to do for phase-I
			break;
		case R.id.search_collections_btn:
			// setButtonBackground(false, true, false);
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

	/**
	 * Functionality to hit server for to get search results
	 */
	public void hitServer(String term, int startIndex) {

		offSet = startIndex;

		if (term == null || term.equals("") || term.equalsIgnoreCase("null")) {
			term = searchEditText.getText().toString().trim();
		}
		if (startIndex == 0) {
			CollectItSharedDataModel.getInstance().getSearchItemList().clear();
			CollectItSharedDataModel.getInstance().getSearchCollectionList()
					.clear();
			CollectItSharedDataModel.getInstance().getSearchUserList().clear();
			CollectItSharedDataModel.getInstance().getItemSearchList().clear();
		}

		DialogProgressCustom.getInstance().startProgressDialog(context, true);
		new WebServiceAsyncHttpPostJson(context,
				CollectItServerConstants.WEBSERVICE_GET_SEARCH,
				CollectItConstants.SEARCH_SCREEN_ID, this, createSearchJson(
						term, startIndex)).execute();
		/*
		 * CollectItSharedDataModel.getInstance().getSearchItemList().clear();
		 * CollectItSharedDataModel.getInstance().getSearchCollectionList()
		 * .clear();
		 * CollectItSharedDataModel.getInstance().getSearchUserList().clear();
		 */

		String searchText = searchEditText.getText().toString().trim();
		if (term != null && !term.equalsIgnoreCase("null") && !term.equals("")) {
			searchEditText.setText(term);
		}

	}

	/**
	 * Functionality to create json for the search terms
	 * 
	 * @return json object that will use as parameters for server hit
	 */
	private JSONObject createSearchJson(String term, int startIndex) {

		if (term == null) {
			term = searchEditText.getText().toString().trim();
		}

		JSONObject json = new JSONObject();
		try {
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID,
					CollectItSharedDataModel.getInstance().getUserId());
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SEARCH_TERM, term);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_START_INDEX,
					startIndex);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_DATA_LIMIT,
					ITEM_DATA_LIMIT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Functionality of server response data
	 */
	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		// TODO Auto-generated method stub
		super.getServerValues(response, id, isOk, exception);
		try {
			if (isOk) {
				if (response != null) {
					if (ParsingUtils.parseSearchResult(context, response,
							offSet)) {
						if ((CollectItSharedDataModel.getInstance()
								.getSearchUserList() != null && CollectItSharedDataModel
								.getInstance().getSearchUserList().size() > 0)
								|| (CollectItSharedDataModel.getInstance()
										.getSearchCollectionList() != null && CollectItSharedDataModel
										.getInstance()
										.getSearchCollectionList().size() > 0)
								|| (CollectItSharedDataModel.getInstance()
										.getSearchItemList() != null && CollectItSharedDataModel
										.getInstance().getSearchItemList()
										.size() > 0)) {
							try {

								SearchItemListFragment searchItemFrag = (SearchItemListFragment) context
										.getSupportFragmentManager()
										.findFragmentByTag(
												FragmentTagNames.SEARCH_ITEM_LIST
														.name());
								if (searchItemFrag == null
										|| searchItemFrag.isDetached()) {
									UtilityMethods.replaceFragment(
											new SearchItemListFragment(),
											context,
											R.id.search_fragment_frame, true,
											true,
											FragmentTagNames.SEARCH_ITEM_LIST
													.name(), null);
								}
							} catch (Exception e) {
								e.printStackTrace();
								UtilityMethods.replaceFragment(
										new SearchItemListFragment(), context,
										R.id.search_fragment_frame, true, true,
										FragmentTagNames.SEARCH_ITEM_LIST
												.name(), null);
							} finally {
								setItemSearchValues();
							}
						} else {
							/*
							 * new ViewToastMsg(context,
							 * getResources().getString(
							 * R.string.no_result_founds));
							 */
							try {
								SearchHistoryFragment searchHistoryFrag = (SearchHistoryFragment) context
										.getSupportFragmentManager()
										.findFragmentByTag(
												FragmentTagNames.SEARCH_RECENT_TERMS_LIST
														.name());
								if (searchHistoryFrag != null) {
									searchHistoryFrag.hitServer();
								} else {
									// setItemSearchValues();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else {
						new ViewToastMsg(context, getResources().getString(
								R.string.connection_error_collectit));
					}
				} else {
					new ViewToastMsg(context, getResources().getString(
							R.string.connection_error_collectit));
				}
			} else {
				new ViewToastMsg(context, getResources().getString(
						R.string.connection_error));
			}
		} catch (StackOverflowError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();
		}
	}

	/**
	 * Functionality to set values for the screen
	 */
	void replaceFragment() {
		// find the search list item fragment
		SearchItemListFragment itemListFrag = (SearchItemListFragment) context
				.getSupportFragmentManager().findFragmentByTag(
						FragmentTagNames.SEARCH_ITEM_LIST.name());
		if (itemListFrag != null) {
			try {
				FragmentTransaction ft = context.getSupportFragmentManager()
						.beginTransaction();
				ft.remove(itemListFrag);
				ft.commit();
				context.getSupportFragmentManager().popBackStack();

			} catch (Exception e) {
				e.printStackTrace();
			}

			UtilityMethods.replaceFragment(new SearchItemListFragment(),
					context, R.id.search_fragment_frame, true, true,
					FragmentTagNames.SEARCH_ITEM_LIST.name(), null);
		} else {
			/*SearchHistoryFragment searchHistoryFrag = (SearchHistoryFragment) context
					.getSupportFragmentManager().findFragmentByTag(
							FragmentTagNames.SEARCH_RECENT_TERMS_LIST.name());
			
			if (searchHistoryFrag != null) {
				try {
					FragmentTransaction ft = context.getSupportFragmentManager()
							.beginTransaction();
					ft.remove(searchHistoryFrag);
					ft.commit();
					context.getSupportFragmentManager().popBackStack();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}*/
			UtilityMethods.replaceFragment(new SearchHistoryFragment(),
					context, R.id.search_fragment_frame, true, false,
					FragmentTagNames.SEARCH_RECENT_TERMS_LIST.name(), null);
		}
	}

	/**
	 * Functionality to notify adapter of search item list
	 */
	void setItemSearchValues() {

		try {
			SearchItemListFragment searchItemFrag = (SearchItemListFragment) context
					.getSupportFragmentManager().findFragmentByTag(
							FragmentTagNames.SEARCH_ITEM_LIST.name());
			if (searchItemFrag != null) {
				searchItemFrag.setItemListAdapter();
			} else {
				try {
					SearchHistoryFragment searchHistoryFrag = (SearchHistoryFragment) context
							.getSupportFragmentManager().findFragmentByTag(
									FragmentTagNames.SEARCH_RECENT_TERMS_LIST
											.name());
					if (searchHistoryFrag != null) {
						if (!searchHistoryFrag.isVisible()
								|| searchHistoryFrag.isHidden()) {
							try {
								FragmentTransaction ft = context
										.getSupportFragmentManager()
										.beginTransaction();
								ft.remove(searchHistoryFrag);
								ft.commit();
								context.getSupportFragmentManager()
										.popBackStack();
								UtilityMethods
										.replaceFragment(
												new SearchHistoryFragment(),
												context,
												R.id.search_fragment_frame,
												true,
												false,
												FragmentTagNames.SEARCH_RECENT_TERMS_LIST
														.name(), null);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						searchHistoryFrag.hitServer();
					} else {
						// setItemSearchValues();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Functionality to clear counts */
	void clearCounts() {
		try {
			SearchItemListFragment itemListFrag = (SearchItemListFragment) context
					.getSupportFragmentManager().findFragmentByTag(
							FragmentTagNames.SEARCH_ITEM_LIST.name());
			if (itemListFrag != null) {
				itemListFrag.clearCounts();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
