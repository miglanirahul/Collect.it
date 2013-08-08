package com.collect.it.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.collect.it.R;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.facebook.FacebookResponse;
import com.collect.it.facebook.FacebookUserDataModel;
import com.collect.it.facebook.FacebookUtil;
import com.collect.it.googleplus.GooglePlusUtils;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.ItemCollectionDataModel;
import com.collect.it.model.ItemDataModel;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.UtilityMethods;
import com.facebook.Response;

/**
 * This fragment class used to define common title bar that appears in
 * application at the top of every screen
 */
public class TitleBalFragment extends CollectItAbstractFragment {
	/**
	 * Declare class variables
	 */
	private Button backButton, shareButton;
	private ImageView settingImageView, titleImageView;
	private TextView titleTextView;

	String messageToPost = "Collect.it";
	Bitmap bitmap = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.collect.it.application.CollectItAbstractFragment#onClick(android.
	 * view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		// back button
		case R.id.frag_topbar_back_btn:

			UtilityMethods.closeCurrentScreen(CollectItSharedDataModel
					.getInstance().getCurrentFragmentActivityContext());
			break;
		/* Setting icon */
		case R.id.frag_topbar_setting_img:
			UtilityMethods.replaceFragment(new SettingsFragment(),
					CollectItSharedDataModel.getInstance()
							.getCurrentFragmentActivityContext(),
					R.id.tab_content, true, true, FragmentTagNames.SETTING
							.name(), null);
			break;
		case R.id.frag_topbar_share_img:
			shareButtonFunc();
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

		// hide back button
		/*
		 * if(activity instanceof LoginHomeActivity){
		 * 
		 * }else{
		 * 
		 * }
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onDetach()
	 */
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_topbar, null);

		backButton = (Button) view.findViewById(R.id.frag_topbar_back_btn);
		backButton.setOnClickListener(this);

		shareButton = (Button) view.findViewById(R.id.frag_topbar_share_img);
		shareButton.setOnClickListener(this);

		settingImageView = (ImageView) view
				.findViewById(R.id.frag_topbar_setting_img);
		settingImageView.setOnClickListener(this);

		titleTextView = (TextView) view
				.findViewById(R.id.frag_topbar_title_txt);
		titleImageView = (ImageView) view
				.findViewById(R.id.frag_topbar_title_img);

		return view;
	}

	/**
	 * Functionality to hide/show back button
	 * 
	 * @param visibility
	 *            pass View.VISIBLE to display back button or pass
	 *            View.INVISIBLE to hide back button
	 */
	public void setVisibilityBackButton(int visibility) {
		if (backButton != null) {
			backButton.setVisibility(visibility);
		}
	}

	/**
	 * Functionality to hide/show setting image
	 * 
	 * @param visibility
	 *            pass View.VISIBLE to display setting icon or pass
	 *            View.INVISIBLE to hide setting icon
	 */
	public void setVisibilitySettingImage(int visibility) {
		if (settingImageView != null) {
			settingImageView.setVisibility(visibility);
		}
	}

	/**
	 * Functionality to set title for the respective screen
	 * 
	 * @param isTitleTextView
	 *            true if need to display title text then title image will be
	 *            hide
	 * @param title
	 *            text to show on title text, this works if isTitleTextView will
	 *            be true
	 */
	public void setTitle(boolean isTitleTextView, String title) {
		try {
			if (titleImageView != null && titleTextView != null) {

				if (isTitleTextView) {
					if (title != null && !title.equals("")
							&& !title.equalsIgnoreCase("null")) {
						titleTextView.setText(title);
						titleTextView.setVisibility(View.VISIBLE);
						titleImageView.setVisibility(View.GONE);
					} else {
						titleTextView.setVisibility(View.GONE);
						titleImageView.setVisibility(View.VISIBLE);
					}
				} else {
					titleTextView.setVisibility(View.GONE);
					titleImageView.setVisibility(View.VISIBLE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to set title for the respective screen
	 * 
	 * @param isTitleTextView
	 *            true if need to display title text then title image will be
	 *            hide
	 * @param title
	 *            text to show on title text, this works if isTitleTextView will
	 *            be true
	 */
	public void setShareIconVisibility(boolean isdisplay) {
		try {
			if (shareButton != null) {

				if (isdisplay) {
					shareButton.setVisibility(View.VISIBLE);
				} else {
					shareButton.setVisibility(View.GONE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to share item on social networks
	 */
	void shareButtonFunc() {
		final FragmentActivity context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();
		String[] options = new String[] { "Facebook", "Google+" };
		final Boolean[] selected = new Boolean[] { false, false };
		new AlertDialog.Builder(context)
				.setTitle(
						context.getResources().getString(
								R.string.image_pick_msg))
				.setNegativeButton(
						context.getResources().getString(R.string.cancel),
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						})
				.setPositiveButton(
						context.getResources().getString(R.string.share),
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									if (selected[0] || selected[1]) {
										ArrayList<ItemDataModel> itemlist = CollectItSharedDataModel
												.getInstance()
												.getItemDetailList();
										if (itemlist != null
												&& itemlist.size() > 0) {
											/* create message to post on social */

											bitmap = BitmapFactory
													.decodeResource(
															getResources(),
															R.drawable.app_icon_gold);
											try {
												ArrayList<ItemCollectionDataModel> selectedCollectionList = itemlist
														.get(0)
														.getItemCollectionList();
												if (selectedCollectionList != null
														&& selectedCollectionList
																.size() > 0) {
													String collections = "";
													for (int i = 0; i < selectedCollectionList
															.size(); i++) {
														if (i == 0) {
															collections = selectedCollectionList
																	.get(i)
																	.getCollectionTitle();
														} else {
															collections = collections
																	+ ", "
																	+ selectedCollectionList
																			.get(i)
																			.getCollectionTitle();
														}
													}
													messageToPost = UtilityMethods
															.getMessage(
																	context,
																	collections);

												}
											} catch (Exception e) {
												e.printStackTrace();
											}

											try {
												/**
												 * Find the added image for the
												 * item
												 */

												if ((itemlist.get(0)
														.getItemImage() != null)) {
													bitmap = ImageUtils
															.getBitmapFromURL(itemlist
																	.get(0)
																	.getItemImage());
												}

												if (bitmap == null) {
													bitmap = BitmapFactory
															.decodeResource(
																	getResources(),
																	R.drawable.app_icon_gold);
												}

											} catch (Exception e) {
												e.printStackTrace();
											}

											if (selected[0]) {
												/* wall post on facebook */
												FacebookUtil.getInstance(
														context).login(context,
														new FacebookResponse() {

															/*
															 * (non-Javadoc)
															 * 
															 * @see com.collect
															 * .it.facebook.
															 * FacebookResponse
															 * #
															 * OnLoginUserDetail
															 * ( com.collect. it
															 * .facebook.
															 * FacebookUserDataModel
															 * )
															 */
															@Override
															public void OnLoginUserDetail(
																	FacebookUserDataModel user) {
																// TODO
																// Auto-generated
																// method
																// stub
																super.OnLoginUserDetail(user);
																FacebookUtil
																		.getInstance(
																				context)
																		.imagePost(
																				bitmap,
																				messageToPost,
																				new FacebookResponse() {
																					@Override
																					public void OnImagePostSuccess(
																							Response response) {
																						// super.OnImagePostSuccess(response);
																						if (context != null) {
																							context.runOnUiThread(new Runnable() {

																								@Override
																								public void run() {
																									new SingleOptionAlertWithoutTitle(
																											context,
																											getResources()
																													.getString(
																															R.string.facebook_wall_post_msg),
																											getResources()
																													.getString(
																															R.string.ok),
																											0);
																								}
																							});
																						}

																					}

																					@Override
																					public void OnSessionExpires() {

																						super.OnSessionExpires();
																						
																						  new
																						  ViewToastMsg
																						  (
																						  context
																						  ,
																						  " Facebook login error"
																						  )
																						  ;
																						 
																					}

																				});

															}

															@Override
															public void OnSessionExpires() {
																// TODO
																// Auto-generated
																// method
																// stub
																super.OnSessionExpires();
															}

														});

											}
											if (selected[1]) {
												/* post on Google+ */
												GooglePlusUtils
														.getInstance(context)
														.shareImageTextGplus(
																context,
																messageToPost,
																CollectItConstants.GOOGLE_PLUS_FILENAME
																		+ "_"
																		+ System.currentTimeMillis(),
																bitmap);

											}
										}
									} else {
										if (context != null) {
											context.runOnUiThread(new Runnable() {
												public void run() {
													new ViewToastMsg(
															context,
															context.getResources()
																	.getString(
																			R.string.choose_option_msg));
												}
											});
										}

									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						})
				.setMultiChoiceItems(options, null,
						new OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								if (which == 0) {
									selected[0] = true;
								}

								if (which == 1) {
									selected[1] = true;
								}
							}
						}).create().show();

	}
}
