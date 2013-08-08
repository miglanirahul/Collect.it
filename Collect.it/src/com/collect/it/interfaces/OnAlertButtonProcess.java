package com.collect.it.interfaces;

/**
 * This interface is used when need to callback on alert button selected
 */
public interface OnAlertButtonProcess {

	/**
	 * on the click of left button
	 * 
	 * @param process
	 *            id that will identify the further processing
	 */
	void onLeftButton(int processId);

	/**
	 * on the click of right button
	 * 
	 * @param process
	 *            id that will identify the further processing
	 * */
	void onRightButton(int processId);
}
