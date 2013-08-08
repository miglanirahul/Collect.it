package com.collect.it.model;

import java.util.ArrayList;

/**
 * This class is used to define the variables for the user list items
 */
public class SearchUserDataModel {
	/** total number of users */
	private String totalUserCount = "0";

	private ArrayList<UserDataModel> userList = new ArrayList<UserDataModel>();

	/**
	 * @return the totalUserCount
	 */
	public String getTotalUserCount() {
		return totalUserCount;
	}

	/**
	 * @param totalUserCount
	 *            the totalUserCount to set
	 */
	public void setTotalUserCount(String totalUserCount) {
		this.totalUserCount = totalUserCount;
	}

	/**
	 * @return the userList
	 */
	public ArrayList<UserDataModel> getUserList() {
		return userList;
	}

	/**
	 * @param userList
	 *            the userList to set
	 */
	public void setUserList(ArrayList<UserDataModel> userList) {
		this.userList = userList;
	}

}
