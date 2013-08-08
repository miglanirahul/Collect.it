package com.collect.it.model;

import java.util.ArrayList;

/**
 * This class is used to define the variables for the collection list items
 */
public class SearchCollectionDataModel {
	/** total number of collections */
	private String totalCollectionCount = "0";

	private ArrayList<ItemCollectionDataModel> collectionList = new ArrayList<ItemCollectionDataModel>();

	/**
	 * @return the totalCollectionCount
	 */
	public String getTotalCollectionCount() {
		return totalCollectionCount;
	}

	/**
	 * @param totalCollectionCount
	 *            the totalCollectionCount to set
	 */
	public void setTotalCollectionCount(String totalCollectionCount) {
		this.totalCollectionCount = totalCollectionCount;
	}

	/**
	 * @return the collectionList
	 */
	public ArrayList<ItemCollectionDataModel> getCollectionList() {
		return collectionList;
	}

	/**
	 * @param collectionList
	 *            the collectionList to set
	 */
	public void setCollectionList(
			ArrayList<ItemCollectionDataModel> collectionList) {
		this.collectionList = collectionList;
	}

}
