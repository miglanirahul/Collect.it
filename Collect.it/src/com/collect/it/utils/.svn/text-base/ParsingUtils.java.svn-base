package com.collect.it.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.collect.it.R;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.ItemCollectionDataModel;
import com.collect.it.model.ItemDataModel;
import com.collect.it.model.ItemTagDataModel;
import com.collect.it.model.SearchCollectionDataModel;
import com.collect.it.model.SearchItemDataModel;
import com.collect.it.model.SearchUserDataModel;
import com.collect.it.model.UserDataModel;

/**
 * This class initially used to define methods to parse response from different
 * web service requests for the application
 */
public class ParsingUtils {

	/**
	 * Functionality to parse the item list server response json
	 * 
	 * @param response
	 *            server response that need to parse
	 * 
	 * @return array list of ItemDataModel type
	 */
	public static ArrayList<ItemDataModel> parseItemListJsonResponse(
			String response) {
		ArrayList<ItemDataModel> array = new ArrayList<ItemDataModel>();
		try {
			JSONObject json = new JSONObject(response);

			if (json.has("response")) {
				String responseString = json.getString("response");
				if (responseString.equalsIgnoreCase("true")) {
					if (json.has("totalItems")) {
						String totalItemCount = json.getString("totalItems");

						if (totalItemCount != null
								&& !totalItemCount.equals("")
								&& !totalItemCount.equalsIgnoreCase("null")
								&& (Integer.valueOf(totalItemCount) > 0)) {
							if (json.has("responseData")) {
								JSONArray responseDataJsonArray = json
										.getJSONArray("responseData");

								if (responseDataJsonArray != null
										&& responseDataJsonArray.length() > 0) {
									ItemDataModel dataModel = null;
									for (int i = 0; i < responseDataJsonArray
											.length(); i++) {
										JSONObject responseArrayObject = responseDataJsonArray
												.getJSONObject(i);

										dataModel = new ItemDataModel();

										// save total number of items counter
										dataModel
												.setItemTotalCount(totalItemCount);

										// parse item's information
										if (responseArrayObject.has("Item")) {
											JSONObject itemJson = responseArrayObject
													.getJSONObject("Item");

											if (itemJson.has("id")) {
												dataModel.setItemId(itemJson
														.getString("id"));
											}
											if (itemJson.has("item_name")) {
												dataModel
														.setItemName(itemJson
																.getString("item_name"));
											}
											if (itemJson
													.has("item_description")) {
												dataModel
														.setItemDescription(itemJson
																.getString("item_description"));
											}

											/* parse status */
											if (itemJson.has("status_id")) {
												dataModel
														.setItemStatus(itemJson
																.getString("status_id"));
											}

										}

										// parse user's information who is
										// associated with this item
										if (responseArrayObject.has("User")) {
											JSONObject userJson = responseArrayObject
													.getJSONObject("User");

											if (userJson.has("id")) {
												dataModel.setUserId(userJson
														.getString("id"));
											}
											if (userJson.has("user_name")) {
												dataModel
														.setUserName(userJson
																.getString("user_name"));
											}
											if (userJson.has("email")) {
												dataModel.setUserEmail(userJson
														.getString("email"));
											}
											if (userJson.has("gid")) {
												dataModel.setUserGId(userJson
														.getString("gid"));
											}
											if (userJson.has("fbid")) {
												dataModel.setUserFbId(userJson
														.getString("fbid"));
											}
											if (userJson.has("image_url")) {
												dataModel
														.setUserImage(userJson
																.getString("image_url"));
											}
										}

										// parse item's image data
										if (responseArrayObject
												.has("Itemimage")) {
											JSONArray itemImageJsonArray = responseArrayObject
													.getJSONArray("Itemimage");

											if (itemImageJsonArray != null
													&& itemImageJsonArray
															.length() > 0) {
												for (int j = 0; j < itemImageJsonArray
														.length(); j++) {
													JSONObject imageJson = itemImageJsonArray
															.getJSONObject(j);
													/*
													 * TODO: need to parse for
													 * and store image array
													 * instead single image
													 */
													if (imageJson
															.has("imagewPath")) {
														String imageUrl = imageJson
																.getString("imagewPath");
														dataModel
																.setItemImage(imageUrl);

														if (imageUrl != null
																&& !imageUrl
																		.equalsIgnoreCase("null")) {
															if (imageJson
																	.has("id")) {
																dataModel
																		.setItemImageId(imageJson
																				.getString("id"));
															}
														}

													}

												}
											}

										}

										// parse user detail
										if (responseArrayObject
												.has("UserDetail")) {
											JSONObject userDetailJson = responseArrayObject
													.getJSONObject("UserDetail");
											String fName = "", lName = "", imageUrl = "", aboutMe = "";
											if (userDetailJson
													.has("first_name")) {
												fName = userDetailJson
														.getString("first_name");
											}
											if (userDetailJson.has("last_name")) {
												lName = userDetailJson
														.getString("last_name");
											}
											if (userDetailJson.has("image_url")) {
												imageUrl = userDetailJson
														.getString("image_url");
											}
											if (userDetailJson.has("about")) {
												aboutMe = userDetailJson
														.getString("about");
											}

											if (fName == null
													|| fName.equalsIgnoreCase("null")) {
												fName = "";
											}
											if (lName == null
													|| lName.equalsIgnoreCase("null")) {
												lName = "";
											}
											if (imageUrl == null
													|| imageUrl
															.equalsIgnoreCase("null")) {
												imageUrl = "";
											}
											if (aboutMe == null
													|| aboutMe
															.equalsIgnoreCase("null")) {
												aboutMe = "";
											}

											dataModel.setUserImage(imageUrl);
											dataModel.setUserLastName(lName);
											dataModel.setUserFirstName(fName);
											dataModel.setUserAboutMe(aboutMe);
										}

										// add into main array
										array.add(dataModel);
									}
								}
							}
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	/**
	 * Functionality to parse server response for the detail of an item
	 * 
	 * @param response
	 *            server response that need to parse
	 * 
	 * @return array list of ItemDataModel type
	 */

	public static ArrayList<ItemDataModel> parseItemDetailResponse(
			String response) {
		ArrayList<ItemDataModel> array = new ArrayList<ItemDataModel>();
		try {
			JSONObject json = new JSONObject(response);

			if (json.has("response")) {
				String responseString = json.getString("response");
				if (responseString.equalsIgnoreCase("true")) {
					if (json.has("responseData")) {
						JSONObject responseDataJson = json
								.getJSONObject("responseData");

						ItemDataModel dataModel = new ItemDataModel();
						// parse item's information
						if (responseDataJson.has("Item")) {
							JSONObject itemJson = responseDataJson
									.getJSONObject("Item");

							if (itemJson.has("id")) {
								dataModel.setItemId(itemJson.getString("id"));
							}
							if (itemJson.has("item_name")) {
								dataModel.setItemName(itemJson
										.getString("item_name"));
							}
							if (itemJson.has("item_description")) {
								dataModel.setItemDescription(itemJson
										.getString("item_description"));
							}
							/* parse status */
							if (itemJson.has("status_id")) {
								dataModel.setItemStatus(itemJson
										.getString("status_id"));
							}

						}

						// parse user's information who is
						// associated with this item
						if (responseDataJson.has("User")) {
							JSONObject userJson = responseDataJson
									.getJSONObject("User");

							if (userJson.has("id")) {
								dataModel.setUserId(userJson.getString("id"));
							}
							if (userJson.has("user_name")) {
								dataModel.setUserName(userJson
										.getString("user_name"));
							}
							if (userJson.has("email")) {
								dataModel.setUserEmail(userJson
										.getString("email"));
							}
							if (userJson.has("gid")) {
								dataModel.setUserGId(userJson.getString("gid"));
							}
							if (userJson.has("fbid")) {
								dataModel.setUserFbId(userJson
										.getString("fbid"));
							}
							if (userJson.has("image_url")) {
								dataModel.setUserImage(userJson
										.getString("image_url"));
							}
						}

						// parse item's image data
						if (responseDataJson.has("Itemimage")) {
							JSONArray itemImageJsonArray = responseDataJson
									.getJSONArray("Itemimage");

							if (itemImageJsonArray != null
									&& itemImageJsonArray.length() > 0) {
								for (int j = 0; j < itemImageJsonArray.length(); j++) {
									JSONObject imageJson = itemImageJsonArray
											.getJSONObject(j);
									/*
									 * TODO: need to parse for and store image
									 * array instead single image
									 */
									if (imageJson.has("imagewPath")) {
										String imageUrl = imageJson
												.getString("imagewPath");
										dataModel.setItemImage(imageUrl);

										if (imageUrl != null
												&& !imageUrl
														.equalsIgnoreCase("null")) {
											if (imageJson.has("id")) {
												dataModel
														.setItemImageId(imageJson
																.getString("id"));
											}
										}

									}

								}
							}
						}

						// parse collections information
						if (responseDataJson.has("Collection")) {
							JSONArray collectionJsonArray = responseDataJson
									.getJSONArray("Collection");

							if (collectionJsonArray != null
									&& collectionJsonArray.length() > 0) {
								ItemCollectionDataModel collectioDataModel = null;
								for (int i = 0; i < collectionJsonArray
										.length(); i++) {
									JSONObject collectionArrayJson = collectionJsonArray
											.getJSONObject(i);

									collectioDataModel = new ItemCollectionDataModel();

									if (collectionArrayJson.has("id")) {
										collectioDataModel
												.setCollectionId(collectionArrayJson
														.getString("id"));
									}
									if (collectionArrayJson
											.has("collection_title")) {
										collectioDataModel
												.setCollectionTitle(collectionArrayJson
														.getString("collection_title"));
									}
									collectioDataModel
											.setCollectionImageBackground(R.drawable.tag_collection_shape_red);

									// add collection in to collection array of
									// item list
									dataModel.getItemCollectionList().add(
											collectioDataModel);

								}
							}

						}

						// parse tags information
						if (responseDataJson.has("Tag")) {
							JSONArray tagJsonArray = responseDataJson
									.getJSONArray("Tag");

							if (tagJsonArray != null
									&& tagJsonArray.length() > 0) {
								ItemTagDataModel tagDataModel = null;
								for (int i = 0; i < tagJsonArray.length(); i++) {
									JSONObject tagArrayJson = tagJsonArray
											.getJSONObject(i);

									tagDataModel = new ItemTagDataModel();

									if (tagArrayJson.has("id")) {
										tagDataModel.setTagId(tagArrayJson
												.getString("id"));
									}
									if (tagArrayJson.has("tag_title")) {
										tagDataModel.setTagTitle(tagArrayJson
												.getString("tag_title"));
									}

									if (tagArrayJson.has("parent_tag")) {
										tagDataModel
												.setTagParentTag(tagArrayJson
														.getString("parent_tag"));
									}
									tagDataModel
											.setTagImageBackground(R.drawable.tag_collection_shape_blue);
									// add collection in to collection array of
									// item list
									dataModel.getItemTagList()
											.add(tagDataModel);

								}
							}

						}
						// parse user detail
						if (responseDataJson.has("UserDetail")) {
							JSONObject userDetailJson = responseDataJson
									.getJSONObject("UserDetail");
							String fName = "", lName = "", imageUrl = "", aboutMe = "";
							if (userDetailJson.has("first_name")) {
								fName = userDetailJson.getString("first_name");
							}
							if (userDetailJson.has("last_name")) {
								lName = userDetailJson.getString("last_name");
							}
							if (userDetailJson.has("image_url")) {
								imageUrl = userDetailJson
										.getString("image_url");
							}
							if (userDetailJson.has("about")) {
								aboutMe = userDetailJson.getString("about");
							}

							if (fName == null || fName.equalsIgnoreCase("null")) {
								fName = "";
							}
							if (lName == null || lName.equalsIgnoreCase("null")) {
								lName = "";
							}
							if (imageUrl == null
									|| imageUrl.equalsIgnoreCase("null")) {
								imageUrl = "";
							}
							if (aboutMe == null
									|| aboutMe.equalsIgnoreCase("null")) {
								aboutMe = "";
							}

							dataModel.setUserImage(imageUrl);
							dataModel.setUserLastName(lName);
							dataModel.setUserFirstName(fName);
							dataModel.setUserAboutMe(aboutMe);
						}

						// finally add on to main array
						array.add(dataModel);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	/**
	 * Functionality to parse search results for the server
	 * 
	 * @param Activity
	 *            context
	 * @param response
	 *            server response that need to parse
	 * 
	 * @param true if all data parsed successfully
	 */
	public static synchronized boolean parseSearchResult(Context context,
			String response, int offSet) {
		boolean isPArsedSuccessfully = false;
		try {
			JSONObject responseJson = new JSONObject(response.trim());
			/* parse item search list */
			if (responseJson.has("itemResults")) {
				JSONObject itemResultJson = responseJson
						.getJSONObject("itemResults");
				if (itemResultJson.has("response")) {
					if (itemResultJson.getString("response").equalsIgnoreCase(
							"true")) {

						SearchItemDataModel itemDataModel = new SearchItemDataModel();

						// total items
						if (itemResultJson.has("totalItems")) {
							String count = itemResultJson
									.getString("totalItems");

							if (count == null || count.equals("")
									|| count.equalsIgnoreCase("null")) {
								count = "0";
							}
							itemDataModel.setTotalItemCount(count);
						}

						if (itemResultJson.has("responseData")) {
							JSONArray responseDataJsonArr = itemResultJson
									.getJSONArray("responseData");

							if (responseDataJsonArr != null
									&& responseDataJsonArr.length() > 0) {
								ArrayList<ItemDataModel> itemList = new ArrayList<ItemDataModel>();
								for (int i = 0; i < responseDataJsonArr
										.length(); i++) {

									ItemDataModel dataModel = new ItemDataModel();

									JSONObject responseDataJson = responseDataJsonArr
											.getJSONObject(i);

									// parse item json
									if (responseDataJson.has("Item")) {
										JSONObject itemJson = responseDataJson
												.getJSONObject("Item");

										if (itemJson.has("id")) {
											dataModel.setItemId(itemJson
													.getString("id"));
										}
										if (itemJson.has("item_name")) {
											dataModel.setItemName(itemJson
													.getString("item_name"));
										}
										if (itemJson.has("item_description")) {
											dataModel
													.setItemDescription(itemJson
															.getString("item_description"));
										}
										if (itemJson.has("status_id")) {
											dataModel.setItemStatus(itemJson
													.getString("status_id"));
										}
									}

									// parse user json
									if (responseDataJson.has("User")) {
										JSONObject userJson = responseDataJson
												.getJSONObject("User");

										if (userJson.has("id")) {
											dataModel.setUserId(userJson
													.getString("id"));
										}
										if (userJson.has("user_name")) {
											dataModel.setUserName(userJson
													.getString("user_name"));
										}
										if (userJson.has("login_type")) {
											userJson.getString("login_type");
										}
										if (userJson.has("email")) {
											dataModel.setUserEmail(userJson
													.getString("email"));
										}
									}

									// parse item image json
									if (responseDataJson.has("Itemimage")) {
										JSONArray itemImageJsonArr = responseDataJson
												.getJSONArray("Itemimage");
										if (itemImageJsonArr != null
												&& itemImageJsonArr.length() > 0) {
											for (int j = 0; j < itemImageJsonArr
													.length(); j++) {
												JSONObject imageJSon = itemImageJsonArr
														.getJSONObject(j);

												if (imageJSon.has("imagewPath")) {
													dataModel
															.setItemImage(imageJSon
																	.getString("imagewPath"));
												}
											}
										}
									}

									// parse collection json
									if (responseDataJson.has("Collection")) {
										JSONArray collectionJsonArr = responseDataJson
												.getJSONArray("Collection");
										if (collectionJsonArr != null
												&& collectionJsonArr.length() > 0) {
											ArrayList<ItemCollectionDataModel> collectionArray = new ArrayList<ItemCollectionDataModel>();
											for (int j = 0; j < collectionJsonArr
													.length(); j++) {
												JSONObject collectionJSon = collectionJsonArr
														.getJSONObject(j);
												ItemCollectionDataModel collectionDataModel = new ItemCollectionDataModel();
												if (collectionJSon.has("id")) {
													collectionDataModel
															.setCollectionId(collectionJSon
																	.getString("id"));
												}
												if (collectionJSon
														.has("collection_title")) {
													collectionDataModel
															.setCollectionTitle(collectionJSon
																	.getString("collection_title"));
												}
												if (collectionJSon
														.has("collection_description")) {
													collectionDataModel
															.setCollectionDescription(collectionJSon
																	.getString("collection_description"));
												}
												collectionArray
														.add(collectionDataModel);
											}

											dataModel
													.setItemCollectionList(collectionArray);
										}
									}

									// parse tag json
									if (responseDataJson.has("Tag")) {
										JSONArray tagJsonArr = responseDataJson
												.getJSONArray("Tag");
										if (tagJsonArr != null
												&& tagJsonArr.length() > 0) {
											ArrayList<ItemTagDataModel> tagArray = new ArrayList<ItemTagDataModel>();
											for (int j = 0; j < tagJsonArr
													.length(); j++) {
												ItemTagDataModel tagDtaModel = new ItemTagDataModel();
												JSONObject tagJSon = tagJsonArr
														.getJSONObject(j);

												if (tagJSon.has("id")) {
													tagDtaModel
															.setTagId(tagJSon
																	.getString("id"));
												}
												if (tagJSon.has("tag_title")) {
													tagDtaModel
															.setTagTitle(tagJSon
																	.getString("tag_title"));
												}
												if (tagJSon
														.has("tag_description")) {
													tagDtaModel
															.setTagDescription(tagJSon
																	.getString("tag_description"));
												}
												if (tagJSon.has("parent_tag")) {
													tagDtaModel
															.setTagParentTag(tagJSon
																	.getString("parent_tag"));
												}

												tagArray.add(tagDtaModel);
											}

											dataModel.setItemTagList(tagArray);
										}
									}

									// parse user detail json
									if (responseDataJson.has("UserDetail")) {
										JSONObject userDetailJson = responseDataJson
												.getJSONObject("UserDetail");

										if (userDetailJson.has("first_name")) {
											dataModel
													.setUserFirstName(userDetailJson
															.getString("first_name"));
										}
										if (userDetailJson.has("last_name")) {
											dataModel
													.setUserLastName(userDetailJson
															.getString("last_name"));
										}
										if (userDetailJson.has("about")) {
											dataModel
													.setUserAboutMe(userDetailJson
															.getString("about"));
										}
										if (userDetailJson.has("image_url")) {
											dataModel
													.setUserImage(userDetailJson
															.getString("image_url"));
										}
									}

									itemList.add(dataModel);
									CollectItSharedDataModel.getInstance()
											.getItemSearchList().add(dataModel);
								}

								// itemDataModel.setItemSearchList(itemList);

							}
						}

						/*
						 * CollectItSharedDataModel.getInstance()
						 * .getSearchItemList().clear();
						 */
						CollectItSharedDataModel.getInstance()
								.getSearchItemList().add(itemDataModel);
					} else {
						if (itemResultJson.has("errorText")) {
							if (offSet > 10) {
								new ViewToastMsg(context,
										"No more items found!");
							} else {
								new ViewToastMsg(context,
										itemResultJson.getString("errorText"));
							}

						}
					}
				}
			}

			/************************
			 * parse collection search list
			 * ************************/
			if (responseJson.has("collectionResults")) {
				JSONObject collectionResultJson = responseJson
						.getJSONObject("collectionResults");

				if (collectionResultJson.has("response")) {
					if (collectionResultJson.getString("response")
							.equalsIgnoreCase("true")) {

						SearchCollectionDataModel collectionDataModel = new SearchCollectionDataModel();

						// total number of collections
						if (collectionResultJson.has("totalCollections")) {
							String count = collectionResultJson
									.getString("totalCollections");
							if (count == null || count.equals("")
									|| count.equalsIgnoreCase("null")) {
								count = "0";
							}
							collectionDataModel.setTotalCollectionCount(count);
						}

						if (collectionResultJson.has("responseData")) {
							JSONArray responseDataJsonArr = collectionResultJson
									.getJSONArray("responseData");

							if (responseDataJsonArr != null
									&& responseDataJsonArr.length() > 0) {
								ArrayList<ItemCollectionDataModel> array = new ArrayList<ItemCollectionDataModel>();
								for (int i = 0; i < responseDataJsonArr
										.length(); i++) {
									JSONObject responseDataJson = responseDataJsonArr
											.getJSONObject(i);

									ItemCollectionDataModel dataModel = new ItemCollectionDataModel();
									if (responseDataJson.has("Collection")) {
										JSONObject collectionJson = responseDataJson
												.getJSONObject("Collection");

										if (collectionJson.has("id")) {
											dataModel
													.setCollectionId(collectionJson
															.getString("id"));
										}

										if (collectionJson
												.has("collection_title")) {
											dataModel
													.setCollectionTitle(collectionJson
															.getString("collection_title"));
										}
										if (collectionJson
												.has("collection_description")) {
											dataModel
													.setCollectionDescription(collectionJson
															.getString("collection_description"));
										}
										if (collectionJson.has("user_id")) {
											collectionJson.getString("user_id");
										}

									}

									// image of collection
									try {
										if (responseDataJson
												.has("collectionImages")) {
											JSONObject collectionImageJson = responseDataJson
													.getJSONObject("collectionImages");

											if (collectionImageJson
													.has("imagewPath")) {
												dataModel
														.setCollectionImage(collectionImageJson
																.getString("imagewPath"));
											}

										}
									} catch (Exception e) {
										e.printStackTrace();
									}
									// finally add datamodel to array list
									array.add(dataModel);

									/*
									 * TODO://there is no need of item data for
									 * this collection
									 * 
									 * 
									 * 
									 * if (responseDataJson.has("Item")) {
									 * 
									 * JSONArray itemJsonArr = responseDataJson
									 * .getJSONArray("Item"); if (itemJsonArr !=
									 * null && itemJsonArr.length() > 0) { /*for
									 * (int j = 0; j < itemJsonArr .length();
									 * j++) {
									 * 
									 * JSONObject itemJson = responseDataJson
									 * .getJSONObject("Item");
									 * 
									 * if (itemJson.has("id")) {
									 * itemJson.getString("id"); } if
									 * (itemJson.has("user_id")) {
									 * itemJson.getString("user_id"); } if
									 * (itemJson.has("item_name")) {
									 * itemJson.getString("item_name"); } if
									 * (itemJson.has("item_description")) {
									 * itemJson.getString("item_description"); }
									 * // } // } }
									 */

								}

								collectionDataModel.setCollectionList(array);
							}
						}

						/*
						 * CollectItSharedDataModel.getInstance()
						 * .getSearchCollectionList().clear();
						 */
						CollectItSharedDataModel.getInstance()
								.getSearchCollectionList()
								.add(collectionDataModel);

					}
				}
			}

			/************************
			 * parse user search list
			 * ************************/
			if (responseJson.has("userResults")) {
				JSONObject userResultJson = responseJson
						.getJSONObject("userResults");
				if (userResultJson.has("response")) {
					if (userResultJson.getString("response").equalsIgnoreCase(
							"true")) {

						SearchUserDataModel userDataModel = new SearchUserDataModel();

						// total number of users
						if (userResultJson.has("totalUsers")) {
							String count = userResultJson
									.getString("totalUsers");
							if (count == null || count.equals("")
									|| count.equalsIgnoreCase("null")) {
								count = "0";
							}
							userDataModel.setTotalUserCount(count);
						}

						if (userResultJson.has("responseData")) {
							JSONArray responseDataJsonArr = userResultJson
									.getJSONArray("responseData");

							if (responseDataJsonArr != null
									&& responseDataJsonArr.length() > 0) {
								ArrayList<UserDataModel> array = new ArrayList<UserDataModel>();
								for (int i = 0; i < responseDataJsonArr
										.length(); i++) {
									JSONObject responseDataJson = responseDataJsonArr
											.getJSONObject(i);
									UserDataModel dataModel = new UserDataModel();
									if (responseDataJson.has("User")) {
										JSONObject userJson = responseDataJson
												.getJSONObject("User");

										if (userJson.has("id")) {
											dataModel.setUserId(userJson
													.getString("id"));
										}
										if (userJson.has("user_name")) {
											dataModel.setUserName(userJson
													.getString("user_name"));
										}
										if (userJson.has("email")) {
											dataModel.setEmail(userJson
													.getString("email"));
										}
										if (userJson.has("gid")) {
											dataModel.setgId(userJson
													.getString("gid"));
										}
										if (userJson.has("fbid")) {
											dataModel.setfbId(userJson
													.getString("fbid"));
										}
										if (userJson.has("login_type")) {
											dataModel.setLoginType(userJson
													.getString("login_type"));
										}

									}

									if (responseDataJson.has("UserDetail")) {
										JSONObject userDetailJson = responseDataJson
												.getJSONObject("UserDetail");

										if (userDetailJson.has("first_name")) {
											dataModel.setfName(userDetailJson
													.getString("first_name"));
										}
										if (userDetailJson.has("last_name")) {
											dataModel.setlName(userDetailJson
													.getString("last_name"));
										}
										if (userDetailJson.has("gender")) {
											dataModel.setGender(userDetailJson
													.getString("gender"));
										}
										if (userDetailJson.has("about")) {
											dataModel.setAbout(userDetailJson
													.getString("about"));
										}
										if (userDetailJson.has("image_url")) {
											dataModel
													.setimageUrl(userDetailJson
															.getString("image_url"));
										}

									}

									array.add(dataModel);

								}

								userDataModel.setUserList(array);
							}

						}

						CollectItSharedDataModel.getInstance()
								.getSearchUserList().clear();
						// add into main array
						CollectItSharedDataModel.getInstance()
								.getSearchUserList().add(userDataModel);
					}
				}
			}

			isPArsedSuccessfully = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return isPArsedSuccessfully;
	}

	/**
	 * Functionality to parse item list for the collection
	 * 
	 * @param response
	 *            server response json string
	 * 
	 * @return Array list associated itemDataModel
	 * */

	public static ArrayList<ItemDataModel> parseCollectionItems(String response) {
		ArrayList<ItemDataModel> array = new ArrayList<ItemDataModel>();
		try {
			JSONObject json = new JSONObject(response.trim());
			if (json.has("response")
					&& json.getString("response").equalsIgnoreCase("true")) {
				if (json.has("responseData")) {
					JSONObject responseDataJson = json
							.getJSONObject("responseData");

					ItemDataModel dataModel = null;
					String userId = "", userName = "", email = "", userImage = "", fNAme = "", lName = "", gender = "", about = "";

					// parse user info
					if (responseDataJson.has("User")) {
						JSONObject userJson = responseDataJson
								.getJSONObject("User");
						if (userJson.has("id")) {
							userId = userJson.getString("id");
						}
						if (userJson.has("user_name")) {
							userName = userJson.getString("user_name");
						}
						if (userJson.has("email")) {
							email = userJson.getString("email");
						}

					}

					// parse user image info
					if (responseDataJson.has("UserDetail")) {
						JSONObject userDetailJson = responseDataJson
								.getJSONObject("UserDetail");

						if (userDetailJson.has("image_url")) {
							userImage = userDetailJson.getString("image_url");
						}
						if (userDetailJson.has("first_name")) {
							fNAme = userDetailJson.getString("first_name");
						}
						if (userDetailJson.has("last_name")) {
							lName = userDetailJson.getString("last_name");
						}
						if (userDetailJson.has("gender")) {
							gender = userDetailJson.getString("gender");
						}
						if (userDetailJson.has("about")) {
							about = userDetailJson.getString("about");
						}
					}

					// parse item data
					if (responseDataJson.has("Item")) {
						JSONArray itemJsonArr = responseDataJson
								.getJSONArray("Item");
						if (itemJsonArr != null && itemJsonArr.length() > 0) {
							for (int i = 0; i < itemJsonArr.length(); i++) {
								JSONObject itemJson = itemJsonArr
										.getJSONObject(i);
								dataModel = new ItemDataModel();

								if (itemJson.has("id")) {
									dataModel.setItemId(itemJson
											.getString("id"));
								}
								if (itemJson.has("user_id")) {
									itemJson.getString("user_id");
								}
								if (itemJson.has("item_description")) {
									dataModel.setItemDescription(itemJson
											.getString("item_description"));
								}
								if (itemJson.has("status_id")) {
									dataModel.setItemStatus(itemJson
											.getString("status_id"));
								}

								if (itemJson.has("Itemimage")) {
									JSONArray itemImageArr = itemJson
											.getJSONArray("Itemimage");
									if (itemImageArr != null
											&& itemImageArr.length() > 0) {
										for (int j = 0; j < itemImageArr
												.length(); j++) {
											JSONObject itemImageJson = itemImageArr
													.getJSONObject(j);

											if (itemImageJson.has("imagewPath")) {
												dataModel
														.setItemImage(itemImageJson
																.getString("imagewPath"));
											} else {
												dataModel.setItemImage("");
											}

										}
									}

								}

								// add user detail
								dataModel.setUserId(userId);
								dataModel.setUserName(userName);
								dataModel.setUserAboutMe(about);
								dataModel.setUserFirstName(fNAme);
								dataModel.setUserLastName(lName);
								dataModel.setUserEmail(email);
								dataModel.setUserImage(userImage);

								// finally add datamodel to array list
								array.add(dataModel);
							}
						}

					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
}
