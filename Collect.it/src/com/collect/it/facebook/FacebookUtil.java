package com.collect.it.facebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.collect.it.R;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

/**
 * This Class is for login purpose Here the login method is
 * called*************************************************
 * **********************************************Login is done with Async
 * Runner************************************* and result is stored in interface
 * 
 * 
 */
public final class FacebookUtil {

	private static final String TAG = "LoginFacebook";
	private static FacebookUtil instance;

	public static final String ME = "me";
	public static final String MY_FRIENDS = "me/friends";
	public static final String MY_PHOTOS = "me/photos";
	public static final String MY_VIDEOS = "me/videos";
	public static final String SEARCH = "search";
	public static final String MY_FEED = "me/feed";
	public static final HttpMethod HTTP_GET = HttpMethod.GET;
	public static final HttpMethod HTTP_POST = HttpMethod.POST;
	public static final HttpMethod HTTP_DELETE = HttpMethod.DELETE;

	private final List<String> PERMISSIONS = Arrays.asList("basic_info",
			"email");
	private static final List<String> PERMISSIONS_EXTERNAL = Arrays
			.asList("publish_actions");

	private static Context contextActivity;

	/**
	 * This method is used to getInstance
	 * 
	 * @param context
	 *            <P>
	 *            if the instance is present in heap then it is not created
	 *            again. It is reffered from there
	 * 
	 */
	public static FacebookUtil getInstance(Context context) {

		contextActivity = context;
		if (instance == null) {
			Log.i(TAG, "Instance Created");
			instance = new FacebookUtil();
			MessageDigest md = null;
			try {
				PackageInfo info = context.getPackageManager()
						.getPackageInfo(context.getPackageName(),
								PackageManager.GET_SIGNATURES);

				for (Signature signature : info.signatures) {

					try {
						md = MessageDigest.getInstance("SHA");
						md.update(signature.toByteArray());
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			} finally {
				Log.d("HASH_KEY",
						"HASH_KEY = " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		}
		return instance;
	}

	/**
	 * <p>
	 * This method is used to login to facebook with the following parameters
	 * 
	 * @param callback
	 *            {@link OnFacebookListener}
	 * @param login
	 *            {@link LoginButton}
	 */
	public void login(final OnFacebookListener callback, final LoginButton login) {

		login.setReadPermissions(Arrays.asList("basic_info", "email",
				"user_birthday", "user_education_history"));
		login.setUserInfoChangedCallback(new UserInfoChangedCallback() {

			@Override
			public void onUserInfoFetched(GraphUser user) {
				// TODO Auto-generated method stub
				final Session session = Session.getActiveSession();
				if (session != null) {
					SessionState state = session.getState();
					if (state == SessionState.OPENED) {

						if (user != null) {
							FacebookUserDataModel getterSetterClass = new FacebookUserDataModel();
							getterSetterClass.setId(user.getId());
							getterSetterClass.setEmail(user.asMap()
									.get("email").toString());
							getterSetterClass
									.setPicture("http://graph.facebook.com/"
											+ user.getId()
											+ "/picture?type=square");
							getterSetterClass.setBirthday(user.getBirthday());
							getterSetterClass.setFirstname(user.getFirstName());
							getterSetterClass.setLastname(user.getLastName());
							getterSetterClass.setLink(user.getLink());
							getterSetterClass.setName(user.getName());
							getterSetterClass.setUsername(user.getUsername());
							getterSetterClass.setGender(user.asMap()
									.get("gender").toString());

							callback.OnLoginSuccess(getterSetterClass);
						}

					}
				}
			}
		});
		login.setOnErrorListener(new OnErrorListener() {

			@Override
			public void onError(FacebookException error) {
				// TODO Auto-generated method stub
				callback.OnLoginError(error.getMessage());
			}
		});
		login.setSessionStatusCallback(new Session.StatusCallback() {

			@Override
			public void call(final Session session, SessionState state,
					Exception exception) {
				if (session != null) {
					if (state == SessionState.OPENED) {
						Request.executeMeRequestAsync(session,
								new Request.GraphUserCallback() {
									@Override
									public void onCompleted(GraphUser user,
											Response response) {
										if (user != null) {
											FacebookUserDataModel getterSetterClass = new FacebookUserDataModel();
											getterSetterClass.setId(user
													.getId());
											getterSetterClass.setEmail(user
													.asMap().get("email")
													.toString());
											getterSetterClass
													.setPicture("http://graph.facebook.com/"
															+ user.getId()
															+ "/picture?type=square");
											getterSetterClass.setBirthday(user
													.getBirthday());
											getterSetterClass.setFirstname(user
													.getFirstName());
											getterSetterClass.setLastname(user
													.getLastName());
											getterSetterClass.setLink(user
													.getLink());
											getterSetterClass.setName(user
													.getName());
											getterSetterClass.setUsername(user
													.getUsername());
											getterSetterClass.setGender(user
													.asMap().get("gender")
													.toString());
											callback.OnLoginSuccess(getterSetterClass);
										} else {
											Log.i(TAG, "session = " + session);
										}
									}
								});

					} else if (state == SessionState.CLOSED_LOGIN_FAILED) {
						// callback.OnLoginError(state.name());
					} else if (state == SessionState.CLOSED) {
						callback.OnLogoutSuccess();
					}
				}

			}
		});

	}

	/**
	 * This method of login is by without using DefaultLoginButton provided by
	 * facebook
	 * 
	 * @param context
	 *            {@link Context} passed
	 * @param callback
	 *            {@link OnFacebookListener} callback for getting result
	 */
	public void login(final Context context, final OnFacebookListener callback) {
		Session session = Session.getActiveSession();
		// if session is null or closed means user is not login to fb
		if (session == null || session.isClosed() || !session.isOpened()/*
												 * || (session.getAccessToken()
												 * == null)
												 */) {

			final Session.StatusCallback sessionStatusCallback = new Session.StatusCallback() {

				@Override
				public void call(Session session, SessionState state,
						Exception exception) {
					// TODO Auto-generated method stub
					if (exception != null) {
						// Handle fail case here.
						Log.v("", "Facebook login error " + exception);

					}

					// If session is just opened...
					if (state == SessionState.OPENED) {

						List<String> permissions = session.getPermissions();
						if (!isSubsetOf(PERMISSIONS_EXTERNAL, permissions)) {
							Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
									(Activity) context, PERMISSIONS_EXTERNAL);
							session.requestNewPublishPermissions(newPermissionsRequest);
						}

						// Handle success case here.
						getUserDetail(callback);

					} else if (state == SessionState.CLOSED_LOGIN_FAILED) {
						callback.OnLoginError(state.name());
					} else if (state == SessionState.CLOSED) {
						callback.OnLogoutSuccess();
					}

				}

			};

			/*
			 * Session.openActiveSession((Activity) context, true,
			 * sessionStatusCallback);
			 */

			Session.openActiveSessionWithPermission((Activity) context, true,
					sessionStatusCallback, PERMISSIONS);

		} else {
			// if user already login
			getUserDetail(callback);
		}
	}

	/**
	 * To check the permission is already granted by FB
	 */
	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method is used to logout from facebook
	 * 
	 * @param callback
	 *            {@link OnFacebookListener} callback to store result
	 */
	public void logout(OnFacebookListener callback) {
		Session session = Session.getActiveSession();
		// if user is login to fb
		if (session != null && session.isOpened()) {
			Session.getActiveSession().closeAndClearTokenInformation();
			callback.OnLogoutSuccess();
		} else {
			// if user is not login to fb
			callback.OnSessionExpires();
		}
	}

	/**
	 * This method is used to wallpost to facebook
	 * 
	 * @param message
	 *            message to sent as wallpost
	 * @param callback
	 *            {@link OnFacebookListener}
	 * @return
	 */
	public void wallPost(String message, final OnFacebookListener callback) {
		final Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			final Request request = Request.newStatusUpdateRequest(
					Session.getActiveSession(), message,
					new Request.Callback() {
						@Override
						public void onCompleted(Response response) {
							GraphObject object = response.getGraphObject();
							if (object != null) {
								callback.OnWallPostSuccess(response);
							} else {
								FacebookRequestError error = response
										.getError();
								callback.OnFacebookError(
										error.getErrorMessage(),
										error.getErrorType(),
										error.getErrorCode());

							}

						}
					});
			request.executeAsync();
		} else {
			callback.OnSessionExpires();
		}
	}

	/**
	 * <P>
	 * This method is used to postImage to facebook
	 * 
	 * @param bitmap
	 *            bitmap to be posted
	 * @param description
	 *            message to post for the associated image
	 * @param callback
	 *            {@link OnFacebookListener}
	 */
	public void imagePost(Bitmap bitmap, String description,
			final OnFacebookListener callback) {
		final Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {

			if (bitmap == null) {
				bitmap = ((BitmapDrawable) contextActivity.getResources()
						.getDrawable(R.drawable.ic_launcher)).getBitmap();
			}

			Request request = Request.newUploadPhotoRequest(
					Session.getActiveSession(), bitmap, new Request.Callback() {
						@Override
						public void onCompleted(Response response) {
							GraphObject object = response.getGraphObject();
							if (object != null) {
								callback.OnImagePostSuccess(response);
							} else {
								FacebookRequestError error = response
										.getError();
								callback.OnFacebookError(
										error.getErrorMessage(),
										error.getErrorType(),
										error.getErrorCode());

							}
						}

					});
			Bundle params = request.getParameters();
			params.putString("name", description);
			request.setParameters(params);
			request.executeAsync();
		} else {
			callback.OnSessionExpires();
		}
	}

	/**
	 * <p>
	 * This method is used to postimage to the facebook
	 * 
	 * @param file
	 *            {@link File}
	 * @param callback
	 *            {@link OnFacebookListener}
	 * @throws FileNotFoundException
	 */
	public void imagePost(File file, String description,
			final OnFacebookListener callback) {
		final Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			try {
				final Request request = Request.newUploadPhotoRequest(
						Session.getActiveSession(), file,
						new Request.Callback() {

							@Override
							public void onCompleted(Response response) {
								// TODO Auto-generated method stub
								GraphObject object = response.getGraphObject();
								if (object != null) {
									callback.OnImagePostSuccess(response);
								} else {
									FacebookRequestError error = response
											.getError();
									callback.OnFacebookError(
											error.getErrorMessage(),
											error.getErrorType(),
											error.getErrorCode());

								}
							}
						});
				Bundle params = request.getParameters();
				params.putString("name", description);
				request.setParameters(params);
				request.executeAsync();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
		} else {
			callback.OnSessionExpires();
		}
	}

	/**
	 * This method is used to getFriend from facebook
	 * 
	 * @param callback
	 *            {@link OnFacebookListener} callback
	 */
	public void getFriend(final OnFacebookListener callback) {
		final Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			Request request = Request.newMyFriendsRequest(
					Session.getActiveSession(),
					new Request.GraphUserListCallback() {

						@Override
						public void onCompleted(List<GraphUser> users,
								Response response) {
							// TODO Auto-generated method stub
							callback.OnFriendList(users);
						}
					});
			Bundle bundle = new Bundle();
			bundle.putString(
					"fields",
					"id,name, picture, location, birthday, hometown, link, email, gender, first_name, last_name");
			request.setParameters(bundle);
			request.executeAsync();
		} else {
			callback.OnSessionExpires();
		}
	}

	/**
	 * Get the current login user detials
	 * 
	 * @param callback
	 *            {@link OnFacebookListener}
	 */
	public void getUserDetail(final OnFacebookListener callback) {
		final Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			Request request = Request.newMeRequest(session,
					new GraphUserCallback() {

						@Override
						public void onCompleted(GraphUser user,
								Response response) {
							// TODO Auto-generated method stub
							final Session session = Session.getActiveSession();
							if (session != null && session.isOpened()) {
								if (user != null) {
									FacebookUserDataModel getterSetterClass = new FacebookUserDataModel();
									getterSetterClass.setId(user.getId());
									String emailId = "";
									if (user.asMap().containsKey("email")) {
										emailId = user.asMap().get("email")
												.toString();
									}
									getterSetterClass.setEmail(emailId);
									getterSetterClass
											.setPicture("http://graph.facebook.com/"
													+ user.getId()
													+ "/picture?type=square");
									getterSetterClass.setBirthday(user
											.getBirthday());
									getterSetterClass.setFirstname(user
											.getFirstName());
									getterSetterClass.setLastname(user
											.getLastName());
									getterSetterClass.setLink(user.getLink());
									getterSetterClass.setName(user.getName());
									getterSetterClass.setUsername(user
											.getUsername());
									getterSetterClass.setGender(user.asMap()
											.get("gender").toString());
									callback.OnLoginUserDetail(getterSetterClass);
								}
							}

						}
					});
			Bundle bundle = new Bundle();
			bundle.putString(
					"fields",
					"id,name, picture, location, birthday, hometown, link, email, gender, first_name, last_name");
			request.setParameters(bundle);
			request.executeAsync();
		} else {
			callback.OnSessionExpires();
		}
	}

	/**
	 * <p>
	 * This method is used to makeRequest for any kind of operation
	 * 
	 * @param graphPath
	 *            graphPath to used {@link FacebookUtil#}
	 * @param parameters
	 *            parameter to pass in bundle
	 * @param httpMethod
	 *            {@link HttpMethod}
	 * @param callback
	 *            {@link OnFacebookListener}
	 */
	public void makeRequest(String graphPath, Bundle parameters,
			HttpMethod httpMethod, final OnFacebookListener callback) {
		final Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			Request request = new Request(Session.getActiveSession(),
					graphPath, parameters, httpMethod, new Request.Callback() {

						@Override
						public void onCompleted(Response response) {
							// TODO Auto-generated method stub
							callback.OnRequestSuccess(response);
						}
					});
			request.executeAsync();
		} else {
			callback.OnSessionExpires();
		}
	}

}
