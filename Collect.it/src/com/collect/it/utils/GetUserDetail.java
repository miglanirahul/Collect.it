package com.collect.it.utils;

import org.json.JSONObject;

import android.app.Activity;

import com.collect.it.R;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.interfaces.OnUserDetailProcess;
import com.collect.it.interfaces.OnWebServiceProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.UserDataModel;
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;

/**
 * This class is used to fetch the user details from server
 */
public class GetUserDetail implements OnWebServiceProcess {
	/**
	 * Declare class variables
	 */
	private Activity context;
	private OnUserDetailProcess userDetailListener;
	private int taskId;

	/**
	 * Constructor definition
	 * 
	 * @param context
	 *            Activity context
	 * @param userDetailListener
	 *            listener to provide response for future process
	 * @param taskId
	 *            id that will identify the requested task
	 */

	public GetUserDetail(Activity context,
			OnUserDetailProcess userDetailListener, int taskId) {
		this.context = context;
		this.userDetailListener = userDetailListener;
		this.taskId = taskId;

		new WebServiceAsyncHttpPostJson(
				context,
				CollectItServerConstants.WEBSERVICE_USED_DETAILS_THROUGH_USERID,
				0, this, createParameterJson()).execute();
	}

	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		try {
			if (isOk) {
				if (response != null) {
					parseServerResponse(response);
				} else {
					new ViewToastMsg(context, context.getResources().getString(
							R.string.connection_error_collectit));
				}
			} else if (exception.equalsIgnoreCase(context.getResources()
					.getString(R.string.connection_error_internet))) {
				new ViewToastMsg(context, exception);
			} else {
				new ViewToastMsg(context, context.getResources().getString(
						R.string.connection_error));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// nothing to do
		}
	}

	@Override
	public void setServerError(int id, String msg) {

	}

	/**
	 * Functionality to create json to fetch user details
	 */
	JSONObject createParameterJson() {
		JSONObject json = new JSONObject();
		try {
			String userId = CollectItSharedDataModel.getInstance().getUserId();
			if (userId != null && !userId.equalsIgnoreCase("null")) {
				json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID,
						userId);
			} else {
				json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID, "");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Functionality to parse server response
	 * 
	 * @param response
	 *            server response
	 */
	void parseServerResponse(String response) {
		try {
			// clear old records if available
			CollectItSharedDataModel.getInstance().getUserDetailList().clear();

			JSONObject json = new JSONObject(response);
			String responseString = "";
			if (json.has("response")) {
				responseString = json.getString("response");

				if (responseString.equalsIgnoreCase("true")) {

					if (json.has("responseData")) {
						JSONObject responseDataJson = json
								.getJSONObject("responseData");

						UserDataModel userData = new UserDataModel();

						if (responseDataJson.has("User")) {
							JSONObject userJson = responseDataJson
									.getJSONObject("User");

							// this will be the user Id as discussed with Munish
							// Sir
							if (userJson.has("id")) {
								// userData.setId(userJson.getString("id"));
								userData.setUserId(userJson.getString("id"));
							}
							if (userJson.has("user_name")) {
								userData.setUserName(userJson
										.getString("user_name"));
							}
							if (userJson.has("email")) {
								userData.setEmail(userJson.getString("email"));
							}
							if (userJson.has("gid")) {
								userData.setgId(userJson.getString("gid"));
							}
							if (userJson.has("fbid")) {
								userData.setfbId(userJson.getString("fbid"));
							}
							if (userJson.has("login_type")) {
								userData.setLoginType(userJson
										.getString("login_type"));
							}
							if (userJson.has("created")) {
								userData.setCreatedDate(userJson
										.getString("created"));
							}
							if (userJson.has("modified")) {
								userData.setModifiedDate(userJson
										.getString("modified"));
							}
							if (userJson.has("password")) {
								userData.setPassword(userJson
										.getString("password"));
							}
							if (userJson.has("smuser")) {
								userData.setSmUser(userJson.getString("smuser"));
							}

						}

						if (responseDataJson.has("UserDetail")) {
							JSONObject userDetailJson = responseDataJson
									.getJSONObject("UserDetail");

							/*
							 * if (userData.getId() == null ||
							 * userData.getId().equals("")) {
							 * 
							 * if (userDetailJson.has("id")) {
							 * userData.setId(userDetailJson .getString("id"));
							 * }
							 * 
							 * }
							 */
							// this is a foreign key as per discussed with
							// Munish Sir. It'll be same as 'id' key in the
							// responseData json packet as defined above
							if (userData.getUserId() == null
									|| userData.getUserId().equals("")
									|| userData.getUserId().equalsIgnoreCase(
											"null")) {
								if (userDetailJson.has("user_id")) {

									userData.setUserId(userDetailJson
											.getString("user_id"));

								}
							}
							if (userDetailJson.has("first_name")) {
								userData.setfName(userDetailJson
										.getString("first_name"));
							}
							if (userDetailJson.has("last_name")) {
								userData.setlName(userDetailJson
										.getString("last_name"));
							}
							if (userDetailJson.has("gender")) {
								userData.setGender(userDetailJson
										.getString("gender"));
							}
							if (userDetailJson.has("about")) {
								userData.setAbout(userDetailJson
										.getString("about"));
							}

							if (userData.getCreatedDate() == null
									|| userData.getCreatedDate().equals("")) {
								if (userDetailJson.has("created")) {
									userData.setCreatedDate(userDetailJson
											.getString("created"));
								}
							}
							if (userData.getModifiedDate() == null
									|| userData.getModifiedDate().equals("")) {
								if (userDetailJson.has("modified")) {
									userData.setModifiedDate(userDetailJson
											.getString("modified"));
								}
							}
							if (userDetailJson.has("user_type")) {
								userData.setUserType(userDetailJson
										.getString("user_type"));
							}
							if (userDetailJson.has("image_url")) {
								userData.setimageUrl(userDetailJson
										.getString("image_url"));
							}
						}

						// parse user sharing status for setting screen
						if (responseDataJson.has("UserSharingstatus")) {
							JSONObject userSharingJson = responseDataJson
									.getJSONObject("UserSharingstatus");

							if (userSharingJson.has("fb_sharing_status")) {
								String fbShareStatus = userSharingJson
										.getString("fb_sharing_status");
								if (fbShareStatus != null
										&& !fbShareStatus
												.equalsIgnoreCase("null")) {
									userData.setFacebookSharingStatus(fbShareStatus);
								} else {
									userData.setFacebookSharingStatus("0");
								}

							}

							if (userSharingJson.has("google_sharing_status")) {

								String gShareStatus = userSharingJson
										.getString("google_sharing_status");
								if (gShareStatus != null
										&& !gShareStatus
												.equalsIgnoreCase("null")) {
									userData.setGooglePlusSharingStatus(gShareStatus);
								} else {
									userData.setGooglePlusSharingStatus("0");
								}
							}

							if (userSharingJson.has("twitter_sharing_status")) {

								String tShareStatus = userSharingJson
										.getString("twitter_sharing_status");
								if (tShareStatus != null
										&& !tShareStatus
												.equalsIgnoreCase("null")) {
									userData.setTwitterSharingStatus(tShareStatus);
								} else {
									userData.setTwitterSharingStatus("0");
								}
							}

						}

						CollectItSharedDataModel.getInstance()
								.getUserDetailList().add(userData);

						if (userDetailListener != null) {
							userDetailListener
									.onUserDetailStoredSuccessfully(taskId);
						}

					} else {
						if (json.has("msg")) {
							String messageString = json.getString("msg");
							new ViewToastMsg(context, messageString);
						}
					}

				} else {
					if (json.has("errorText")) {
						String errorString = json.getString("errorText");
						new ViewToastMsg(context, errorString);
						if (userDetailListener != null) {
							userDetailListener.onUserDetailError(errorString);
						}
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (userDetailListener != null) {
				userDetailListener.onUserDetailError(e.getMessage().toString());
			}
		}

	}

}
