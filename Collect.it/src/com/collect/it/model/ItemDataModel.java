package com.collect.it.model;

import java.util.ArrayList;

/**
 * This class is used to define the variables for the item list and details data
 */
public class ItemDataModel {
	private String
	/** server database id of item */
	itemId,
	/** name of item */
	itemName,
	/** description of item */
	itemDescription,
	/** image of item */
	itemImage,
	/** image id of added item image */
	itemImageId,
	/** user id who has added the item */
	userId,
	/** user image against the added item */
	userImage,
	/** user email against the item */
	userEmail,
	/** user's facebook id against item */
	userFbId,
	/** user's google+ id against the item */
	userGId,
	/** user name who has added the item */
	userName,
	/** total number of items that are available */
	itemTotalCount = "0",
	/** user's first name */
	userFirstName = "",
	/** user's last name */
	userLastName = "",
	/** user's about me */
	userAboutMe = "",
	/** item status */
	itemStatus = "";

	/**
	 * array list that contains the tag lists that are associated with this item
	 */
	ArrayList<ItemTagDataModel> itemTagList = new ArrayList<ItemTagDataModel>();
	/**
	 * array list that contains the collection lists that are associated with
	 * this item
	 */
	ArrayList<ItemCollectionDataModel> itemCollectionList = new ArrayList<ItemCollectionDataModel>();

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName
	 *            the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemDescription
	 */
	public String getItemDescription() {
		return itemDescription;
	}

	/**
	 * @param itemDescription
	 *            the itemDescription to set
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	/**
	 * @return the itemImage
	 */
	public String getItemImage() {
		return itemImage;
	}

	/**
	 * @param itemImage
	 *            the itemImage to set
	 */
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

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
	 * @return the userImage
	 */
	public String getUserImage() {
		return userImage;
	}

	/**
	 * @param userImage
	 *            the userImage to set
	 */
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail
	 *            the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the userFbId
	 */
	public String getUserFbId() {
		return userFbId;
	}

	/**
	 * @param userFbId
	 *            the userFbId to set
	 */
	public void setUserFbId(String userFbId) {
		this.userFbId = userFbId;
	}

	/**
	 * @return the userGId
	 */
	public String getUserGId() {
		return userGId;
	}

	/**
	 * @param userGId
	 *            the userGId to set
	 */
	public void setUserGId(String userGId) {
		this.userGId = userGId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the itemTotalCount
	 */
	public String getItemTotalCount() {
		return itemTotalCount;
	}

	/**
	 * @param itemTotalCount
	 *            the itemTotalCount to set
	 */
	public void setItemTotalCount(String itemTotalCount) {
		this.itemTotalCount = itemTotalCount;
	}

	/**
	 * @return the itemTagList
	 */
	public ArrayList<ItemTagDataModel> getItemTagList() {
		return itemTagList;
	}

	/**
	 * @param itemTagList
	 *            the itemTagList to set
	 */
	public void setItemTagList(ArrayList<ItemTagDataModel> itemTagList) {
		this.itemTagList = itemTagList;
	}

	/**
	 * @return the itemCollectionList
	 */
	public ArrayList<ItemCollectionDataModel> getItemCollectionList() {
		return itemCollectionList;
	}

	/**
	 * @param itemCollectionList
	 *            the itemCollectionList to set
	 */
	public void setItemCollectionList(
			ArrayList<ItemCollectionDataModel> itemCollectionList) {
		this.itemCollectionList = itemCollectionList;
	}

	/**
	 * @return the userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}

	/**
	 * @param userFirstName
	 *            the userFirstName to set
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	/**
	 * @return the userLastName
	 */
	public String getUserLastName() {
		return userLastName;
	}

	/**
	 * @param userLastName
	 *            the userLastName to set
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	/**
	 * @return the itemImageId
	 */
	public String getItemImageId() {
		return itemImageId;
	}

	/**
	 * @param itemImageId
	 *            the itemImageId to set
	 */
	public void setItemImageId(String itemImageId) {
		this.itemImageId = itemImageId;
	}

	/**
	 * @return the userAboutMe
	 */
	public String getUserAboutMe() {
		return userAboutMe;
	}

	/**
	 * @param userAboutMe
	 *            the userAboutMe to set
	 */
	public void setUserAboutMe(String userAboutMe) {
		this.userAboutMe = userAboutMe;
	}

	/**
	 * @return the itemStatus
	 */
	public String getItemStatus() {
		return itemStatus;
	}

	/**
	 * @param itemStatus
	 *            the itemStatus to set
	 */
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

}
