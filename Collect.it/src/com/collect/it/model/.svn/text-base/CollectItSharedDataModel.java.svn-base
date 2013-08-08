package com.collect.it.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;

import com.collect.it.constants.CollectItConstants;
import com.collect.it.facebook.FacebookUserDataModel;
import com.google.android.gms.plus.model.people.Person;

public class CollectItSharedDataModel {

	private static CollectItSharedDataModel instance;

	public static CollectItSharedDataModel getInstance() {
		if (instance == null) {
			instance = new CollectItSharedDataModel();
		}

		return instance;
	}

	/**
	 * Login/Signup fragment activity context, because getActivity() gives null
	 * sometimes during the changes of fragments. Thus we have to maintain the
	 * activity object
	 */
	private FragmentActivity currentFragmentActivityContext;

	/**
	 * @return the loginSignupFragmentActivityContext
	 */
	public FragmentActivity getCurrentFragmentActivityContext() {
		return currentFragmentActivityContext;
	}

	/**
	 * @param loginSignupFragmentActivityContext
	 *            the loginSignupFragmentActivityContext to set
	 */
	public void setCurrentFragmentActivityContext(
			FragmentActivity currentFragmentActivityContext) {
		this.currentFragmentActivityContext = currentFragmentActivityContext;
	}

	/**
	 * integer value that will determine the user signup key id. The keys for
	 * collect.it signup, facebook signup and google+ signup is defined in the
	 * constants class
	 */
	private int signupThroughId;

	/**
	 * @return the signupThroughId
	 */
	public int getSignupThroughId() {
		return signupThroughId;
	}

	/**
	 * @param signupThroughId
	 *            the signupThroughId to set
	 */
	public void setSignupThroughId(int signupThroughId) {
		this.signupThroughId = signupThroughId;
	}

	/**
	 * Google analytic librarie's tracker class object to send view/screen name
	 */
	/*
	 * private Tracker gaTracker;
	 *//**
	 * @return the gaTracker
	 */
	/*
	 * public Tracker getGaTracker() { return gaTracker; }
	 *//**
	 * @param gaTracker
	 *            the gaTracker to set
	 */
	/*
	 * public void setGaTracker(Tracker gaTracker) { this.gaTracker = gaTracker;
	 * }
	 */

	/**
	 * device dimension metrics
	 */
	private DisplayMetrics displayMetrics;

	/**
	 * @return the displayMetrics
	 */
	public DisplayMetrics getDisplayMetrics() {
		return displayMetrics;
	}

	/**
	 * @param displayMetrics
	 *            the displayMetrics to set
	 */
	public void setDisplayMetrics(DisplayMetrics displayMetrics) {
		this.displayMetrics = displayMetrics;
	}

	/**
	 * Logged in User detail array list
	 */
	List<UserDataModel> userDetailList = new ArrayList<UserDataModel>();

	/**
	 * @return the userDetailList
	 */
	public List<UserDataModel> getUserDetailList() {
		return userDetailList;
	}

	/**
	 * @param userDetailList
	 *            the userDetailList to set
	 */
	public void setUserDetailList(List<UserDataModel> userDetailList) {
		this.userDetailList = userDetailList;
	}

	/**
	 * Facebook Logged in User detail array list
	 */
	List<FacebookUserDataModel> userDetailListFacebook = new ArrayList<FacebookUserDataModel>();

	/**
	 * @return the userDetailListFacebook
	 */
	public List<FacebookUserDataModel> getUserDetailListFacebook() {
		return userDetailListFacebook;
	}

	/**
	 * @param userDetailListFacebook
	 *            the userDetailListFacebook to set
	 */
	public void setUserDetailListFacebook(
			List<FacebookUserDataModel> userDetailListFacebook) {
		this.userDetailListFacebook = userDetailListFacebook;
	}

	/**
	 * Google plus Logged in User detail array list
	 */
	List<Person> userDetailListGooglePlus = new ArrayList<Person>();

	/**
	 * @return the userDetailListGooglePlus
	 */
	public List<Person> getUserDetailListGooglePlus() {
		return userDetailListGooglePlus;
	}

	/**
	 * @param userDetailListGooglePlus
	 *            the userDetailListGooglePlus to set
	 */
	public void setUserDetailListGooglePlus(
			List<Person> userDetailListGooglePlus) {
		this.userDetailListGooglePlus = userDetailListGooglePlus;
	}

	/**
	 * Login user's id
	 */
	private String userId;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Shared preferences for the application
	 */
	private SharedPreferences preferences;

	/**
	 * @return the preferences
	 */
	public SharedPreferences getPreferences(Context context) {
		preferences = context
				.getSharedPreferences(
						CollectItConstants.SHARED_PREFERENCES_KEY,
						Context.MODE_PRIVATE);
		return preferences;
	}

	private ArrayList<ItemTagDataModel>
	/** Array list that will contain tag name and tag gallery items background */
	tagGalleryArrayList = new ArrayList<ItemTagDataModel>(),
	/** ArrayList that contains the selected Tags for the item */
	selectedTagsList = new ArrayList<ItemTagDataModel>();

	private ArrayList<ItemCollectionDataModel>
	/**
	 * Array list that will contain collection name and collection gallery items
	 * background
	 */
	collectionGalleryArrayList = new ArrayList<ItemCollectionDataModel>(),
	/** ArrayList that contains the selected collections for the item */
	selectedCollectionList = new ArrayList<ItemCollectionDataModel>();

	/**
	 * @return the tagGalleryArrayList
	 */
	public ArrayList<ItemTagDataModel> getTagGalleryArrayList() {
		return tagGalleryArrayList;
	}

	/**
	 * @param tagGalleryArrayList
	 *            the tagGalleryArrayList to set
	 */
	public void setTagGalleryArrayList(
			ArrayList<ItemTagDataModel> tagGalleryArrayList) {
		this.tagGalleryArrayList = tagGalleryArrayList;
	}

	/**
	 * @return the collectionGalleryArrayList
	 */
	public ArrayList<ItemCollectionDataModel> getCollectionGalleryArrayList() {
		return collectionGalleryArrayList;
	}

	/**
	 * @param collectionGalleryArrayList
	 *            the collectionGalleryArrayList to set
	 */
	public void setCollectionGalleryArrayList(
			ArrayList<ItemCollectionDataModel> collectionGalleryArrayList) {
		this.collectionGalleryArrayList = collectionGalleryArrayList;
	}

	/**
	 * Array list for the image gallery of add item screen
	 */
	private ArrayList<ImageGalleryDataModel> imageGalleryArrayList = new ArrayList<ImageGalleryDataModel>();

	/**
	 * @return the imageGalleryArrayList
	 */
	public ArrayList<ImageGalleryDataModel> getImageGalleryArrayList() {
		return imageGalleryArrayList;
	}

	/**
	 * @param imageGalleryArrayList
	 *            the imageGalleryArrayList to set
	 */
	public void setImageGalleryArrayList(
			ArrayList<ImageGalleryDataModel> imageGalleryArrayList) {
		this.imageGalleryArrayList = imageGalleryArrayList;
	}

	/**
	 * @return the selectedTagsList
	 */
	public ArrayList<ItemTagDataModel> getSelectedTagsList() {
		return selectedTagsList;
	}

	/**
	 * @param selectedTagsList
	 *            the selectedTagsList to set
	 */
	public void setSelectedTagsList(ArrayList<ItemTagDataModel> selectedTagsList) {
		this.selectedTagsList = selectedTagsList;
	}

	/**
	 * @return the selectedCollectionList
	 */
	public ArrayList<ItemCollectionDataModel> getSelectedCollectionList() {
		return selectedCollectionList;
	}

	/**
	 * @param selectedCollectionList
	 *            the selectedCollectionList to set
	 */
	public void setSelectedCollectionList(
			ArrayList<ItemCollectionDataModel> selectedCollectionList) {
		this.selectedCollectionList = selectedCollectionList;
	}

	/** array list for the edit detail screen data */
	private ArrayList<ItemDataModel> editItemDetailList = new ArrayList<ItemDataModel>();

	/**
	 * @return the editItemDetailList
	 */
	public ArrayList<ItemDataModel> getEditItemDetailList() {
		return editItemDetailList;
	}

	/**
	 * @param editItemDetailList
	 *            the editItemDetailList to set
	 */
	public void setEditItemDetailList(
			ArrayList<ItemDataModel> editItemDetailList) {
		this.editItemDetailList = editItemDetailList;
	}

	private ArrayList<ItemCollectionDataModel>
	/**
	 * Array list that will contain collection name and collection gallery items
	 * background
	 */
	collectionEditGalleryArrayList = new ArrayList<ItemCollectionDataModel>();
	private ArrayList<ItemTagDataModel>
	/** Array list that will contain tag name and tag gallery items background */
	tagGalleryEditArrayList = new ArrayList<ItemTagDataModel>();

	/**
	 * @return the collectionEditGalleryArrayList
	 */
	public ArrayList<ItemCollectionDataModel> getCollectionEditGalleryArrayList() {
		return collectionEditGalleryArrayList;
	}

	/**
	 * @param collectionEditGalleryArrayList
	 *            the collectionEditGalleryArrayList to set
	 */
	public void setCollectionEditGalleryArrayList(
			ArrayList<ItemCollectionDataModel> collectionEditGalleryArrayList) {
		this.collectionEditGalleryArrayList = collectionEditGalleryArrayList;
	}

	/**
	 * @return the tagGalleryEditArrayList
	 */
	public ArrayList<ItemTagDataModel> getTagGalleryEditArrayList() {
		return tagGalleryEditArrayList;
	}

	/**
	 * @param tagGalleryEditArrayList
	 *            the tagGalleryEditArrayList to set
	 */
	public void setTagGalleryEditArrayList(
			ArrayList<ItemTagDataModel> tagGalleryEditArrayList) {
		this.tagGalleryEditArrayList = tagGalleryEditArrayList;
	}

	/** search item list */
	ArrayList<SearchItemDataModel> searchItemList = new ArrayList<SearchItemDataModel>();

	/** search collection list */
	ArrayList<SearchCollectionDataModel> searchCollectionList = new ArrayList<SearchCollectionDataModel>();

	/** search user list */
	ArrayList<SearchUserDataModel> searchUserList = new ArrayList<SearchUserDataModel>();

	/**
	 * @return the searchItemList
	 */
	public ArrayList<SearchItemDataModel> getSearchItemList() {
		return searchItemList;
	}

	/**
	 * @param searchItemList
	 *            the searchItemList to set
	 */
	public void setSearchItemList(ArrayList<SearchItemDataModel> searchItemList) {
		this.searchItemList = searchItemList;
	}

	/**
	 * @return the searchCollectionList
	 */
	public ArrayList<SearchCollectionDataModel> getSearchCollectionList() {
		return searchCollectionList;
	}

	/**
	 * @param searchCollectionList
	 *            the searchCollectionList to set
	 */
	public void setSearchCollectionList(
			ArrayList<SearchCollectionDataModel> searchCollectionList) {
		this.searchCollectionList = searchCollectionList;
	}

	/**
	 * @return the searchUserList
	 */
	public ArrayList<SearchUserDataModel> getSearchUserList() {
		return searchUserList;
	}

	/**
	 * @param searchUserList
	 *            the searchUserList to set
	 */
	public void setSearchUserList(ArrayList<SearchUserDataModel> searchUserList) {
		this.searchUserList = searchUserList;
	}


	/** array list for item detail screen */
	private ArrayList<ItemDataModel> itemDetailList = new ArrayList<ItemDataModel>();

	/**
	 * @return the itemDetailList
	 */
	public ArrayList<ItemDataModel> getItemDetailList() {
		return itemDetailList;
	}

	/**
	 * @param itemDetailList
	 *            the itemDetailList to set
	 */
	public void setItemDetailList(ArrayList<ItemDataModel> itemDetailList) {
		this.itemDetailList = itemDetailList;
	}
	
	/**array list for the home screen item lis*/
	private ArrayList<ItemDataModel> homeItemList = new ArrayList<ItemDataModel>();;

	/**
	 * @return the homeItemList
	 */
	public ArrayList<ItemDataModel> getHomeItemList() {
		return homeItemList;
	}

	/**
	 * @param homeItemList the homeItemList to set
	 */
	public void setHomeItemList(ArrayList<ItemDataModel> homeItemList) {
		this.homeItemList = homeItemList;
	}
	
	/**search screen item list*/
	private ArrayList<ItemDataModel> itemSearchList = new ArrayList<ItemDataModel>();

	/**
	 * @return the itemSearchList
	 */
	public ArrayList<ItemDataModel> getItemSearchList() {
		return itemSearchList;
	}

	/**
	 * @param itemSearchList the itemSearchList to set
	 */
	public void setItemSearchList(ArrayList<ItemDataModel> itemSearchList) {
		this.itemSearchList = itemSearchList;
	}
	
	

}
