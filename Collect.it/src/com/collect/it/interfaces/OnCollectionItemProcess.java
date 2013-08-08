package com.collect.it.interfaces;

import java.util.ArrayList;

import com.collect.it.model.ItemDataModel;

/**
 * This interface is used with the collection item fetched server data to notify
 * the UI screen for further process
 */
public interface OnCollectionItemProcess {

	/**
	 * This method works as a callback for the UI classes of the application to
	 * notify the class when the data has been fetched and parse and give the
	 * related item listing array to display on the screen and do further
	 * process
	 * 
	 * @param collectionItemList
	 *            the list associated with ItemDataModel class, this list is
	 *            fetched from server and parsed
	 */
	void getCollectionItemList(ArrayList<ItemDataModel> collectionItemList);

	/**
	 * call back method to provide error information
	 * 
	 * @param errorMsg
	 *            represents the error message
	 */
	void onCollectionItemError(String errorMsg);
}
