package com.collect.it.interfaces;

/**
 * This interface is used where user detail is fetched from Collect.it server
 */
public interface OnUserDetailProcess {

	/**
	 * this method will be called if the user detail has been successfully saved
	 * locally into the application
	 * 
	 * @param taskId
	 *            this id used to identify associated task
	 */
	void onUserDetailStoredSuccessfully(int taskId);

	/**
	 * if there is an error during fetching user details
	 * 
	 * @param errorMsg
	 *            error message
	 */
	void onUserDetailError(String errorMsg);
}
