package com.collect.it.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.collect.it.R;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.model.SearchRecentDataModel;
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;
import com.collect.it.utils.GetUserCollectionItemList;
import com.collect.it.utils.UtilityMethods;

/** This fragment is used to display the recent search for the users */
public class SearchHistoryFragment extends CollectItAbstractFragment {
	/** Declare class variables */
	private ListView recentSearchListView;

	private RecentSearchAdapter searchAdapter;

	private FragmentActivity context;

	private final int DELETE_SEARCH_TERM_SERVER_KEY = 101;
	private final int TERM_DATA_LIMIT = 10;

	private int offset = 0;

	private ArrayList<SearchRecentDataModel> searchTermList;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_search_history, null);

		recentSearchListView = (ListView) view
				.findViewById(R.id.frag_search_history_listview);

		return view;
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
						getResources().getString(
								R.string.ga_search_item_history_screen));

		// hide setting icon on top bar
		UtilityMethods.setSettingIconVisibility(context, View.GONE);

		// hide back button
		UtilityMethods.setBackButtonVisibility(context, View.INVISIBLE);

		// set title for the screen
		UtilityMethods.setTitleBarTitle(context,
				CollectItConstants.SEARCH_SCREEN_ID);

		hitServer();

	}

	public void hitServer() {
		searchTermList = new ArrayList<SearchRecentDataModel>();
		offset = 0;

		DialogProgressCustom.getInstance().startProgressDialog(context, true);
		new WebServiceAsyncHttpPostJson(context,
				CollectItServerConstants.WEBSERVICE_GET_RECENT_SEARCH_TERMS,
				CollectItConstants.SEARCH_SCREEN_RECENT_ID, this,
				createRecentSearchJson()).execute();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		searchAdapter = null;
		/*
		 * try { SearchHistoryFragment searchHistFrag = (SearchHistoryFragment)
		 * context .getSupportFragmentManager().findFragmentByTag(
		 * FragmentTagNames.SEARCH_RECENT_TERMS_LIST.name());
		 * 
		 * if (searchHistFrag != null) { FragmentTransaction ft =
		 * context.getSupportFragmentManager() .beginTransaction();
		 * ft.detach(searchHistFrag); ft.commit(); } } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
	}

	/** Functionality to create json to find recent search terms */
	JSONObject createRecentSearchJson() {
		JSONObject json = new JSONObject();
		try {
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID,
					CollectItSharedDataModel.getInstance().getUserId());
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_START_INDEX,
					offset);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_DATA_LIMIT,
					TERM_DATA_LIMIT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;

	}

	/**
	 * Functionality to set adapter
	 */
	void setAdapter() {
		/*
		 * if (searchAdapter == null) { searchAdapter = new
		 * RecentSearchAdapter();
		 * recentSearchListView.setAdapter(searchAdapter); } else {
		 * searchAdapter.notifyDataSetChanged(); }
		 */

		recentSearchListView.setAdapter(new RecentSearchAdapter());
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
	}

	/** adapter class to display recent search terms */
	private class RecentSearchAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return searchTermList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			View rowView = view;
			if (rowView == null) {
				rowView = LayoutInflater.from(context).inflate(
						R.layout.search_history_item_row, null);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.crossButton = (Button) rowView
						.findViewById(R.id.search_history_row_cross_btn);
				viewHolder.textView = (TextView) rowView
						.findViewById(R.id.search_history_row_txt);
				viewHolder.linearLayout = (LinearLayout) rowView
						.findViewById(R.id.search_history_row_linear);

				rowView.setTag(viewHolder);
			}

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();

			viewHolder.textView.setText(searchTermList.get(position).getTerm());
			viewHolder.crossButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// hit server to delete this term from the history
					new WebServiceAsyncHttpPostJson(
							context,
							CollectItServerConstants.WEBSERVICE_DELETE_SEARCH_TERM,
							DELETE_SEARCH_TERM_SERVER_KEY,
							SearchHistoryFragment.this,
							createDeleteTermJson(searchTermList.get(position)
									.getTerm())).execute();
					searchTermList.remove(position);
					notifyDataSetChanged();

				}
			});

			viewHolder.linearLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					UtilityMethods.replaceFragment(
							new SearchItemListFragment(), context,
							R.id.search_fragment_frame, true, true,
							FragmentTagNames.SEARCH_ITEM_LIST.name(), null);

					try {
						SearchFragment searchFrag = (SearchFragment) context
								.getSupportFragmentManager().findFragmentByTag(
										FragmentTagNames.SEARCH_TAB.name());
						if (searchFrag != null) {
							searchFrag.hitServer(searchTermList.get(position)
									.getTerm(), 0);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			return rowView;
		}

		class ViewHolder {
			TextView textView;
			Button crossButton;
			LinearLayout linearLayout;
		}

	}

	/** Functionality of server response */
	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		// TODO Auto-generated method stub
		super.getServerValues(response, id, isOk, exception);
		try {
			if (isOk) {
				if (response != null) {
					switch (id) {
					case DELETE_SEARCH_TERM_SERVER_KEY:
						break;
					case CollectItConstants.SEARCH_SCREEN_RECENT_ID:
						parseServerResponse(response);
						break;
					default:
						break;
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
			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();
		}
	}

	/** Functionality to parse server response */
	void parseServerResponse(String response) {
		try {
			JSONObject reponseJSon = new JSONObject(response);
			if (reponseJSon.has("response")
					&& reponseJSon.getString("response").equalsIgnoreCase(
							"true")) {

				if (reponseJSon.has("responseData")) {
					JSONArray responseDataJSonArr = reponseJSon
							.getJSONArray("responseData");
					if (responseDataJSonArr != null
							&& responseDataJSonArr.length() > 0) {
						ArrayList<String> termArray = new ArrayList<String>();
						for (int i = 0; i < responseDataJSonArr.length(); i++) {
							JSONObject responseDataJSon = responseDataJSonArr
									.getJSONObject(i);

							if (responseDataJSon.has("UserSearchterm")) {
								SearchRecentDataModel dataModel = new SearchRecentDataModel();
								JSONObject serchTermJson = responseDataJSon
										.getJSONObject("UserSearchterm");
								if (serchTermJson.has("id")) {
									dataModel.setTermId(serchTermJson
											.getString("id"));
								}
								if (serchTermJson.has("user_id")) {
									dataModel.setUserId(serchTermJson
											.getString("user_id"));
								}
								if (serchTermJson.has("term")) {
									dataModel.setTerm(serchTermJson
											.getString("term"));
								}

								if (!termArray.contains(dataModel.getTerm())
										&& !dataModel.getTerm().equals("")) {
									searchTermList.add(dataModel);
									termArray.add(dataModel.getTerm());
								}

							}

						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			setAdapter();
		}
	}

	/**
	 * Functionality to create json to delete search term
	 * 
	 * @param term
	 *            term for the server parameter
	 * 
	 * @return json object for the server parameters
	 */
	JSONObject createDeleteTermJson(String term) {
		JSONObject json = new JSONObject();
		try {
			json.put(CollectItServerConstants.WEBSERVICE_KEY_SEARCH_TERM, term);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID,
					CollectItSharedDataModel.getInstance().getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Functionality to get more search terms if available
	 */
	private class EndlessScrollListener implements OnScrollListener {

		private int visibleThreshold = 8;
		private int previousTotal = 0;
		private boolean loading = true;

		public EndlessScrollListener() {

		}

		public EndlessScrollListener(int visibleThreshold) {
			this.visibleThreshold = visibleThreshold;
		}

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
				hitServer();
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
