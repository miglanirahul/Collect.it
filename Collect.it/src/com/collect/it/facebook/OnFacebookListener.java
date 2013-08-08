package com.collect.it.facebook;

import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphUser;

/**
 * 
 * This interface attached to various method in class
 *         in this package and result is stored in following methods and used in
 *         activity
 * 
 */
public interface OnFacebookListener {

	public void OnLoginSuccess(FacebookUserDataModel loginDetail);

	public void OnLoginError(String error);

	public void OnLogoutSuccess();

	public void OnLoginUserDetail(FacebookUserDataModel user);

	public void OnWallPostSuccess(Response response);

	public void OnImagePostSuccess(Response response);

	public void OnFriendList(List<GraphUser> friends);

	public void OnRequestSuccess(Response response);

	public void OnSessionExpires();

	public void OnFacebookError(String errorMessage, String errorType,
			int errorCode);

}
