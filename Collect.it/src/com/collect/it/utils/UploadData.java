package com.collect.it.utils;

import org.json.JSONObject;

import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.database.CollectItDatabase;
import com.collect.it.database.CollectItDatabaseConstants;
import com.collect.it.database.DatabaseManipulation;
import com.collect.it.server.communication.HttpPostJsonWithoutAsync;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

/**
 * This class is used to upload data on to server
 * 
 * a) upload image data for the added item, available in local database
 */
public class UploadData {
	/**
	 * Declare class variables
	 */
	Context context;

	/**
	 * Constructor definition
	 */
	public UploadData(Context context) {
		this.context = context;

		uploadImageData();
	}

	/**
	 * Functionality to upload image data onto server This method works in
	 * background with the async task class to fetch the local db record and
	 * send on to the server accordingly. After successfully uploading the data
	 * will be deleted from local db
	 */
	synchronized void uploadImageData() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				Cursor cursor = null;
				try {
					cursor = DatabaseManipulation.findValuesFromLocalDatabase(
							CollectItDatabaseConstants.ITEM_IMAGE_TABLE_NAME,
							new String[] { CollectItDatabaseConstants.USER_ID,
									CollectItDatabaseConstants.ITEM_ID,
									CollectItDatabaseConstants.ITEM_IMAGE },
							null, null, null,
							CollectItDatabase.getInstance(context));

					if (cursor != null && cursor.getCount() > 0) {
						cursor.moveToFirst();
						for (int i = 0; i < cursor.getCount(); i++) {
							// hit web server to upload image data on to server
							// db
							String response = new HttpPostJsonWithoutAsync()
									.httpPostWithJson(
											CollectItServerConstants.WEBSERVICE_UPLOAD_IMAGE_FOR_ADDED_ITEM,
											createJsonToUploadItemImage(cursor
													.getString(0).toString(),
													cursor.getString(1)
															.toString(), cursor
															.getString(2)
															.toString()));
							if (response != null) {
								parseImageUploadResponseAndDeleteRecordsFromLocalDb(response);
							}
							cursor.moveToNext();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// close the cursor
					if (cursor != null) {
						cursor.close();
					}
				}
				return null;
			}
		};
	}

	/**
	 * Functionality to create json object to upload item's image
	 * 
	 * @param userId
	 *            user's id
	 * @param itemId
	 *            item id for the image
	 * @param itemPicBase64
	 *            Base64 string of the image
	 * 
	 * @return json object
	 */
	JSONObject createJsonToUploadItemImage(String userId, String itemId,
			String itemPicBase64) {
		JSONObject json = new JSONObject();
		try {
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID, userId);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_ID, itemId);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_PIC,
					itemPicBase64);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Functionality to parse image upload server response
	 * 
	 * This will delete the data if successfully uploaded
	 * 
	 * @param response
	 *            response of uploaded image
	 */
	void parseImageUploadResponseAndDeleteRecordsFromLocalDb(String response) {
		try {
			JSONObject json = new JSONObject(response);

			if (json.has("response")) {
				String responseString = json.getString("response");
				if (responseString.equalsIgnoreCase("true")) {
					if (json.has("responseData")) {
						JSONObject responseDataJson = json
								.getJSONObject("responseData");
						if (responseDataJson.has("itemId")) {
							String itemId = responseDataJson
									.getString("itemId");

							if (itemId != null) {
								// delete image data from the local db
								DatabaseManipulation
										.deleteRows(
												CollectItDatabaseConstants.ITEM_IMAGE_TABLE_NAME,
												CollectItDatabaseConstants.ITEM_ID
														+ "=" + itemId,
												CollectItDatabase
														.getInstance(context));
							}

						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
