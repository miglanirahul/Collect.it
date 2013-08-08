package com.collect.it.model;

import android.graphics.Bitmap;

/**
 * This model class represents the data for the image gallery on add item screen
 */
public class ImageGalleryDataModel {
	/**
	 * background drawable resource if value is 0 then we'll consider the
	 * backgroundBitymap value
	 */
	private int backgroundResource;
	/** bitmap that will fetched either from camera or from device's gallery */
	private Bitmap backgroundBitmap;

	/**
	 * @return the backgroundResource
	 */
	public int getBackgroundResource() {
		return backgroundResource;
	}

	/**
	 * @param backgroundResource
	 *            the backgroundResource to set
	 */
	public void setBackgroundResource(int backgroundResource) {
		this.backgroundResource = backgroundResource;
	}

	/**
	 * @return the backgroundBitmap
	 */
	public Bitmap getBackgroundBitmap() {
		return backgroundBitmap;
	}

	/**
	 * @param backgroundBitmap
	 *            the backgroundBitmap to set
	 */
	public void setBackgroundBitmap(Bitmap backgroundBitmap) {
		this.backgroundBitmap = backgroundBitmap;
	}

}
