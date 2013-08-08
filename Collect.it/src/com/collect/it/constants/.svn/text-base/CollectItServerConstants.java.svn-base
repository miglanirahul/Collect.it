package com.collect.it.constants;

/**
 * This class is used to define the web services constants for the app
 */
public class CollectItServerConstants {

	/*********************************************
	 * Web services to communication with collect.it server
	 * ****************************************************/
	/**
	 * Base url for the collect.it web services that appends in the
	 * corresponding web service methods to make full web service url/path.
	 * Netsmartz local server 
	 */
	//public static final String BASE_WEBSERVICE_PATH = "http://collected.netsmartz.us/WebServices/";
	/**
	 * Base url for the collect.it web services that appends in the
	 * corresponding web service methods to make full web service url/path.
	 * Client's live server 
	 */
	public static final String BASE_WEBSERVICE_PATH = "http://54.221.44.101/collectit/WebServices/";
	
	/**
	 * Sign up url for collect.it web service
	 * 
	 * @parameters : WEBSERVICE_KEY_USERNAME, WEBSERVICE_KEY_PASSWORD,
	 *             WEBSERVICE_KEY_SIGNUP_FNAME,WEBSERVICE_KEY_SIGNUP_LNAME,
	 *             WEBSERVICE_KEY_SIGNUP_EMAIL, WEBSERVICE_KEY_SIGNUP_IMAGE,
	 *             WEBSERVICE_KEY_FACEBOOK_ID, WEBSERVICE_KEY_GOOGLEPLUS_ID,
	 *             WEBSERVICE_KEY_REGISTRATION_TYPE,
	 *             WEBSERVICE_KEY_SIGNUP_GENDER, WEBSERVICE_KEY_SIGNUP_ABOUTME
	 */
	public static final String WEBSERVICE_SIGNUP = BASE_WEBSERVICE_PATH
			+ "signup";
	/**
	 * Login url for collect.it web service
	 * 
	 * @parameters : WEBSERVICE_KEY_USERNAME, WEBSERVICE_KEY_PASSWORD
	 */
	public static final String WEBSERVICE_LOGIN = BASE_WEBSERVICE_PATH
			+ "isUser";
	/**
	 * Login through social sites (initially: Facebook/Google+) url for
	 * collect.it web service
	 * 
	 * @parameters : WEBSERVICE_KEY_FACEBOOK_ID, WEBSERVICE_KEY_GOOGLEPLUS_ID,
	 *             WEBSERVICE_KEY_REGISTRATION_TYPE
	 * 
	 */
	public static final String WEBSERVICE_LOGIN_SOCIAL_SITES = BASE_WEBSERVICE_PATH
			+ "isSmUser";

	/**
	 * User details through user id that is found after login into the
	 * application
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID
	 */
	public static final String WEBSERVICE_USED_DETAILS_THROUGH_USERID = BASE_WEBSERVICE_PATH
			+ "getUserDetails";

	/**
	 * Change password for the application login
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID, WEBSERVICE_KEY_CURRENT_PASSWORD,
	 *             WEBSERVICE_KEY_NEW_PASSWORD
	 */
	public static final String WEBSERVICE_CHANGE_PASSWORD = BASE_WEBSERVICE_PATH
			+ "changePassword";

	/**
	 * update logged in user profile
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID, WEBSERVICE_KEY_SIGNUP_FNAME,
	 *             WEBSERVICE_KEY_SIGNUP_LNAME, WEBSERVICE_KEY_SIGNUP_EMAIL,
	 *             WEBSERVICE_KEY_SIGNUP_IMAGE, WEBSERVICE_KEY_FACEBOOK_ID,
	 *             WEBSERVICE_KEY_GOOGLEPLUS_ID,
	 *             WEBSERVICE_KEY_REGISTRATION_TYPE,
	 *             WEBSERVICE_KEY_SIGNUP_GENDER, WEBSERVICE_KEY_SIGNUP_ABOUTME
	 */
	public static final String WEBSERVICE_EDIT_PROFILE = BASE_WEBSERVICE_PATH
			+ "updateProfile";

	/**
	 * set the sharing status for logged in user initially available on Setting
	 * screen
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID, WEBSERVICE_KEY_TWITTER_ID,
	 *             WEBSERVICE_KEY_TWITTER_SHARING_STATUS,
	 *             WEBSERVICE_KEY_GOOGLE_PLUS_SHARING_STATUS,
	 *             WEBSERVICE_KEY_FACEBOOK_SHARING_STATUS,
	 *             WEBSERVICE_KEY_FACEBOOK_ID, WEBSERVICE_KEY_GOOGLEPLUS_ID,
	 */
	public static final String WEBSERVICE_SET_SHARING_STATUS = BASE_WEBSERVICE_PATH
			+ "setSharingStatus";

	/**
	 * Forgot password web service method to send the user name/user email id to
	 * get forgot password initially on to user's email
	 * 
	 * @parameters : WEBSERVICE_KEY_USERNAME, WEBSERVICE_KEY_SIGNUP_EMAIL
	 */
	public static final String WEBSERVICE_FORGOT_PASSWORD = BASE_WEBSERVICE_PATH
			+ "forgetPassword";

	/**
	 * Get the tag list that have been created by logged in user
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID, WEBSERVICE_KEY_LIMIT
	 */
	public static final String WEBSERVICE_GET_RECENT_TAGS = BASE_WEBSERVICE_PATH
			+ "getRecentTags";

	/**
	 * Get the collection list that have been created by logged in user
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID, WEBSERVICE_KEY_LIMIT
	 */
	public static final String WEBSERVICE_GET_RECENT_COLLECTIONS = BASE_WEBSERVICE_PATH
			+ "getRecentCollections";

	/**
	 * Web service method to add the items to user's collection
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID, WEBSERVICE_KEY_ITEM_NAME,
	 *             WEBSERVICE_KEY_ITEM_DESCRIPTION,
	 *             WEBSERVICE_KEY_ITEM_TAG_ARRAY, WEBSERVICE_KEY_ITEM_TAG_ID,
	 *             WEBSERVICE_KEY_ITEM_TAG_TITLE,
	 *             WEBSERVICE_KEY_ITEM_TAG_PARENT,
	 *             WEBSERVICE_KEY_ITEM_COLLECTION_ARRAY,
	 *             WEBSERVICE_KEY_ITEM_COLLECTION_ID,
	 *             WEBSERVICE_KEY_ITEM_COLLECTION_TITLE
	 */
	public static final String WEBSERVICE_ADD_ITEM = BASE_WEBSERVICE_PATH
			+ "addItem";

	/**
	 * Web service method to edit an item's detail for a particular user
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID, WEBSERVICE_KEY_ITEM_NAME,
	 *             WEBSERVICE_KEY_ITEM_DESCRIPTION,
	 *             WEBSERVICE_KEY_ITEM_TAG_ARRAY, WEBSERVICE_KEY_ITEM_TAG_ID,
	 *             WEBSERVICE_KEY_ITEM_TAG_TITLE,
	 *             WEBSERVICE_KEY_ITEM_TAG_PARENT,
	 *             WEBSERVICE_KEY_ITEM_COLLECTION_ARRAY,
	 *             WEBSERVICE_KEY_ITEM_COLLECTION_ID,
	 *             WEBSERVICE_KEY_ITEM_COLLECTION_TITLE, WEBSERVICE_KEY_ITEM_ID,
	 *             WEBSERVICE_KEY_ITEM_IMAGE_ID
	 */
	public static final String WEBSERVICE_EDIT_ITEM_DETAIL = BASE_WEBSERVICE_PATH
			+ "editItemDetails";

	/**
	 * Web service method to upload an image for the added item to user's
	 * collection
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID, WEBSERVICE_KEY_ITEM_ID,
	 *             WEBSERVICE_KEY_ITEM_PIC
	 */
	public static final String WEBSERVICE_UPLOAD_IMAGE_FOR_ADDED_ITEM = BASE_WEBSERVICE_PATH
			+ "addItemImage";

	/**
	 * Web service method to get user's collections' items from the server
	 * database
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID, WEBSERVICE_KEY_ITEM_START_INDEX,
	 *             WEBSERVICE_KEY_ITEM_DATA_LIMIT
	 */
	public static final String WEBSERVICE_GET_USER_ITEM_DATA = BASE_WEBSERVICE_PATH
			+ "getItems";

	/**
	 * Web service method to get an item detail
	 * 
	 * @parameters : WEBSERVICE_KEY_ITEM_ID
	 */
	public static final String WEBSERVICE_GET_ITEM_DETAIL_BY_ITEM_ID = BASE_WEBSERVICE_PATH
			+ "getItemDetails";

	/**
	 * Web service method to get search items, users and collections on the
	 * basis of user id and search term
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID, WEBSERVICE_KEY_SEARCH_TERM
	 */
	public static final String WEBSERVICE_GET_SEARCH = BASE_WEBSERVICE_PATH
			+ "searchText";

	/**
	 * Web service method to get recent search terms of user
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID
	 */
	public static final String WEBSERVICE_GET_RECENT_SEARCH_TERMS = BASE_WEBSERVICE_PATH
			+ "getUserRcentSearch";

	/**
	 * Web service method to delete an item
	 * 
	 * @parameters : WEBSERVICE_KEY_ITEM_ID
	 */
	public static final String WEBSERVICE_DELETE_ITEM = BASE_WEBSERVICE_PATH
			+ "deleteItem";

	/**
	 * Web service method to delete a search term
	 * 
	 * @parameters : WEBSERVICE_KEY_USER_ID, WEBSERVICE_KEY_SEARCH_TERM
	 */
	public static final String WEBSERVICE_DELETE_SEARCH_TERM = BASE_WEBSERVICE_PATH
			+ "deleteTerm";
	
	
	/**
	 * Web service method to delete a search term
	 * 
	 * @parameters : WEBSERVICE_KEY_ITEM_COLLECTION_ID
	 */
	public static final String WEBSERVICE_GET_COLLECTION_ITEM = BASE_WEBSERVICE_PATH
			+ "getCollectionItems";

	/*****************************************
	 * Web service registration type keys for sign up
	 *******************************************/
	public static final int REGISTRATION_TYPE_GOOGLEPLUS = 1;
	public static final int REGISTRATION_TYPE_FACEBOOK = 2;
	public static final int REGISTRATION_TYPE_COLLECTIT = 3;

	/*****************************************
	 * Item screen status keys
	 *******************************************/
	public static final int STATUS_OWN = 1;
	public static final int STATUS_SALE = 2;
	public static final int STATUS_WANTED = 3;

	/*******************************************
	 * Web service parameters' keys
	 * ***************************************/
	public static final String WEBSERVICE_KEY_USERNAME = "userName";
	public static final String WEBSERVICE_KEY_PASSWORD = "userPassword";
	public static final String WEBSERVICE_KEY_SIGNUP_FNAME = "userFirstName";
	public static final String WEBSERVICE_KEY_SIGNUP_LNAME = "userLastName";
	public static final String WEBSERVICE_KEY_SIGNUP_EMAIL = "userEmail";
	public static final String WEBSERVICE_KEY_SIGNUP_IMAGE = "userProfilePic";
	public static final String WEBSERVICE_KEY_FACEBOOK_ID = "userFbID";
	public static final String WEBSERVICE_KEY_GOOGLEPLUS_ID = "userGid";
	public static final String WEBSERVICE_KEY_REGISTRATION_TYPE = "userRegType";
	public static final String WEBSERVICE_KEY_SIGNUP_GENDER = "userGender";
	public static final String WEBSERVICE_KEY_SIGNUP_ABOUTME = "userAboutMe";
	public static final String WEBSERVICE_KEY_USER_ID = "userId";
	public static final String WEBSERVICE_KEY_CURRENT_PASSWORD = "userCurrentPassword";
	public static final String WEBSERVICE_KEY_NEW_PASSWORD = "userNewPassword";
	public static final String WEBSERVICE_KEY_FACEBOOK_SHARING_STATUS = "userFbStatus";
	public static final String WEBSERVICE_KEY_GOOGLE_PLUS_SHARING_STATUS = "userGStatus";
	public static final String WEBSERVICE_KEY_TWITTER_SHARING_STATUS = "userTStatus";
	public static final String WEBSERVICE_KEY_TWITTER_ID = "userTId";
	public static final String WEBSERVICE_KEY_LIMIT = "limit";
	public static final String WEBSERVICE_KEY_ITEM_NAME = "itemName";
	public static final String WEBSERVICE_KEY_ITEM_DESCRIPTION = "itemDesc";
	public static final String WEBSERVICE_KEY_ITEM_TAG_ARRAY = "itemTagsArray";
	public static final String WEBSERVICE_KEY_ITEM_TAG_ID = "tagId";
	public static final String WEBSERVICE_KEY_ITEM_TAG_TITLE = "tagTitle";
	public static final String WEBSERVICE_KEY_ITEM_TAG_PARENT = "tagParent";
	public static final String WEBSERVICE_KEY_ITEM_COLLECTION_ARRAY = "itemCollectionArray";
	public static final String WEBSERVICE_KEY_ITEM_COLLECTION_ID = "collectionId";
	public static final String WEBSERVICE_KEY_ITEM_COLLECTION_TITLE = "collectionTitle";
	public static final String WEBSERVICE_KEY_ITEM_ID = "itemId";
	public static final String WEBSERVICE_KEY_ITEM_PIC = "itemPic";
	public static final String WEBSERVICE_KEY_ITEM_START_INDEX = "start";
	public static final String WEBSERVICE_KEY_ITEM_DATA_LIMIT = "limit";
	public static final String WEBSERVICE_KEY_ITEM_IMAGE_ID = "itemImageId";
	public static final String WEBSERVICE_KEY_ITEM_STATUS_ID = "statusId";
	public static final String WEBSERVICE_KEY_SEARCH_TERM = "term";
	public static final String WEBSERVICE_KEY_SEARCH_TERM_ID = "termId";
}
