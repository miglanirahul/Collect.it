package com.collect.it.utils;

import org.json.JSONObject;

import android.content.Context;

import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.interfaces.OnCollectionItemProcess;
import com.collect.it.interfaces.OnWebServiceProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;

/**
 * Functionality to fetch item list for Logged in user
 */
public class GetUserCollectionItemList implements OnWebServiceProcess {
	/**
	 * Declare class variables
	 */

	/** Activity context */
	Context context;

	/** The call back listener for the item fetched and saved accordingly */
	OnCollectionItemProcess onCollectionItemListener;

	/**
	 * starting number from which the item data need to be fetched from server.
	 * * if this is first time fetch then starting index should be 0
	 * */
	private int startIndex = 0;

	/** user's server database id who is associated with the item */
	private String userId;

	/** maximum item data that need to fetch from server */
	private final int ITEM_DATA_LIMIT = 10;
	/** id to identify the task for background thread this screen */
	private final int GET_USER_COLLECTIONS_ITEM_DATA_ID = 101;

	/**
	 * Constructor definition
	 * 
	 * @param context
	 *            Activity context
	 * @param startIndex
	 *            starting counter to fetch item data from. if this is first
	 *            time fetch then starting index should be 0
	 * @param userId
	 *            user's server database id that is associated with item
	 * @param onCollectionItemListener
	 *            callback listener to detect that IO process has been finished
	 * 
	 */
	public GetUserCollectionItemList(Context context, int startIndex,
			String userId, OnCollectionItemProcess onCollectionItemListener) {
		this.context = context;
		this.onCollectionItemListener = onCollectionItemListener;
		this.startIndex = startIndex;

		if (userId == null || userId.equalsIgnoreCase("null")) {
			userId = "";
		}
		this.userId = userId;

		// hit server to fetch record of user collection items
		new WebServiceAsyncHttpPostJson(context,
				CollectItServerConstants.WEBSERVICE_GET_USER_ITEM_DATA,
				GET_USER_COLLECTIONS_ITEM_DATA_ID, this,
				createGetUserItemJson()).execute();

	}

	/**
	 * Functionality to create json object to fetch the user's collections' item
	 * data
	 * 
	 * @return json object for the web service parameters
	 */
	private JSONObject createGetUserItemJson() {
		JSONObject json = new JSONObject();
		try {
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID, userId);
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
	 * Functionality of server response
	 */
	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		if (isOk) {
			if (response != null) {

				if (onCollectionItemListener != null) {
					if(startIndex == 0){
						CollectItSharedDataModel.getInstance().getHomeItemList().clear();
					}
					onCollectionItemListener.getCollectionItemList(ParsingUtils
							.parseItemListJsonResponse(response));
				}
			} else {
				if (onCollectionItemListener != null) {
					onCollectionItemListener.onCollectionItemError(exception);
				}
			}

		} else {
			if (onCollectionItemListener != null) {
				onCollectionItemListener.onCollectionItemError(exception);
			}
		}
	}

	/**
	 * on server error
	 */
	@Override
	public void setServerError(int id, String msg) {
		if (onCollectionItemListener != null) {
			onCollectionItemListener.onCollectionItemError(msg);
		}
	}

}
