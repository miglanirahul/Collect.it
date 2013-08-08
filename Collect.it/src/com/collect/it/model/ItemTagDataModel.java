package com.collect.it.model;

/**
 * This class used to define the variables for the tags of an item
 */
public class ItemTagDataModel {
	private String
	/** server database id of tag */
	tagId,
	/** name of tag */
	tagTitle,
	/** description of tag */
	tagDescription = "",
	/** parent tag name, basically used for sub tags */
	tagParentTag;
	/** back ground image resource for the tag */
	private int tagImageBackground;

	/**
	 * @return the tagId
	 */
	public String getTagId() {
		return tagId;
	}

	/**
	 * @param tagId
	 *            the tagId to set
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	/**
	 * @return the tagTitle
	 */
	public String getTagTitle() {
		return tagTitle;
	}

	/**
	 * @param tagTitle
	 *            the tagTitle to set
	 */
	public void setTagTitle(String tagTitle) {
		this.tagTitle = tagTitle;
	}

	/**
	 * @return the parentTag
	 */
	public String getTagParentTag() {
		return tagParentTag;
	}

	/**
	 * @param parentTag
	 *            the parentTag to set
	 */
	public void setTagParentTag(String tagParentTag) {
		this.tagParentTag = tagParentTag;
	}

	/**
	 * @return the tagImageBackground
	 */
	public int getTagImageBackground() {
		return tagImageBackground;
	}

	/**
	 * @param tagImageBackground
	 *            the tagImageBackground to set
	 */
	public void setTagImageBackground(int tagImageBackground) {
		this.tagImageBackground = tagImageBackground;
	}

	/**
	 * @return the tagDescription
	 */
	public String getTagDescription() {
		return tagDescription;
	}

	/**
	 * @param tagDescription
	 *            the tagDescription to set
	 */
	public void setTagDescription(String tagDescription) {
		this.tagDescription = tagDescription;
	}

}
