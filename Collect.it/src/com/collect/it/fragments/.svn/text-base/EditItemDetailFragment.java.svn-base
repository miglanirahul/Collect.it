package com.collect.it.fragments;

import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ToggleButton;

import com.collect.it.HomeTabFragmentActivity;
import com.collect.it.R;
import com.collect.it.adapter.CollectionHorizontalViewAdapter;
import com.collect.it.adapter.CollectionSelectedHorizontalViewAdapter;
import com.collect.it.adapter.ImageGalleryHorizontalViewAdapter;
import com.collect.it.adapter.TagsHorizontalViewAdapter;
import com.collect.it.adapter.TagsSelectedHorizontalViewAdapter;
import com.collect.it.alerts.DialogProgressCustom;
import com.collect.it.alerts.DoubleOptionAlertWithoutTitle;
import com.collect.it.alerts.SingleOptionAlertWithoutTitle;
import com.collect.it.alerts.ViewToastMsg;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.ViewPagerIds;
import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.database.CollectItDatabase;
import com.collect.it.database.CollectItDatabaseConstants;
import com.collect.it.database.DatabaseManipulation;
import com.collect.it.facebook.FacebookResponse;
import com.collect.it.facebook.FacebookUserDataModel;
import com.collect.it.facebook.FacebookUtil;
import com.collect.it.googleplus.GooglePlusUtils;
import com.collect.it.interfaces.OnSelectedItemProcess;
import com.collect.it.interfaces.OnViewPagerItemSelectionProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.GoogleAnalyticModel;
import com.collect.it.model.ImageGalleryDataModel;
import com.collect.it.model.ItemCollectionDataModel;
import com.collect.it.model.ItemDataModel;
import com.collect.it.model.ItemTagDataModel;
import com.collect.it.server.communication.WebServiceAsyncHttpPostJson;
import com.collect.it.utils.HorizontalListView;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.UtilityMethods;
import com.facebook.Response;

/**
 * This fragment is used to edit an item's detail
 */
public class EditItemDetailFragment extends CollectItAbstractFragment implements
		OnViewPagerItemSelectionProcess, OnTouchListener, OnSelectedItemProcess {
	/**
	 * Declare class variables
	 */
	private FragmentActivity context;

	private EditText tagEditText, descriptionEditText, collectionEditText;
	private ToggleButton collectItToggleButton, facebookjToggleButton,
			googlePlusToggleButton;
	private HorizontalListView imageGalleryHorizontalListView,
			tagHorizontalListView, collectionHorizontalListView,
			tagSelectedHorizontalListView,
			collectionSelectedHorizontalListView;
	private RadioButton radioButtonOwn, radioButtonSale, radioButtonWanted;
	private Button addTagButton, addCollectionButton;

	public static final int
	/** maximum size of tag gallery */
	TAG_MAX = 50,
	/** maximum size of collection gallery */
	COLLECTION_MAX = 50;
	/** key to detect server response to fetch the tags */
	private final int TAG_SERVER_KEY = 111,
	/** key to detect server response to fetch the collections */
	COLLECTION_SERVER_KEY = 112,
	/** count of tags that need to fetch from server */
	MAX_TAG_SERVER_COUNT = 10,
	/** count of collections that need to fetch from server */
	MAX_COLLECTION_SERVER_COUNT = 10,
	/** key to detect server response to delete an item */
	DELETE_ITEM_SERVER_KEY = 113;

	private String
	/** server database's Item id that is added by the user */
	itemId,
	/**
	 * item image's base 64 string that need to upload on to server for the
	 * correspondence added item by user
	 */
	itemImageBase64String;

	/** adapter for the tag list */
	private TagsHorizontalViewAdapter tagAdapter;
	/** adapter for the collection list */
	private CollectionHorizontalViewAdapter collectionAdapter;
	/** adapter for the selected tag list */
	private TagsSelectedHorizontalViewAdapter tagSelectedAdapter;
	/** adapter for the selected collection list */
	private CollectionSelectedHorizontalViewAdapter collectionSelectedAdapter;
	/** adapter for the image gallery */
	private ImageGalleryHorizontalViewAdapter imageGalleryAdapter;

	@Override
	public View initialization(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.frag_edit_item_detail, null);

		try {
			context = CollectItSharedDataModel.getInstance()
					.getCurrentFragmentActivityContext();

			// add data onto image gallery
			CollectItSharedDataModel.getInstance().getImageGalleryArrayList()
					.clear();
			for (int i = 0; i < 11; i++) {
				ImageGalleryDataModel dataModel = new ImageGalleryDataModel();
				if (i == 0) {
					dataModel.setBackgroundResource(R.drawable.add_media);
				} else {
					dataModel.setBackgroundResource(R.drawable.frame_locked);
				}

				dataModel.setBackgroundBitmap(null);
				CollectItSharedDataModel.getInstance()
						.getImageGalleryArrayList().add(dataModel);
			}

			// initiate components
			imageGalleryHorizontalListView = (HorizontalListView) view
					.findViewById(R.id.edit_item_image_viewpager);
			tagHorizontalListView = (HorizontalListView) view
					.findViewById(R.id.edit_item_tag_viewpager);
			collectionHorizontalListView = (HorizontalListView) view
					.findViewById(R.id.edit_item_collection_viewpager);

			tagSelectedHorizontalListView = (HorizontalListView) view
					.findViewById(R.id.edit_item_tag_selected_viewpager);
			collectionSelectedHorizontalListView = (HorizontalListView) view
					.findViewById(R.id.edit_item_collection_selected_viewpager);

			// set adapter classes for the correspondence view pagers
			setImageGalleryPagerAdapter();

			tagEditText = (EditText) view
					.findViewById(R.id.edit_item_tag_edittext);
			descriptionEditText = (EditText) view
					.findViewById(R.id.edit_item_description_edittext);
			collectionEditText = (EditText) view
					.findViewById(R.id.edit_item_collection_edittext);

			// set editor action listener
			tagEditText
					.setOnEditorActionListener(new AddItemEditorActionListener());
			collectionEditText
					.setOnEditorActionListener(new AddItemEditorActionListener());

			tagEditText.setHint(Html.fromHtml("<small>"
					+ getString(R.string.tag_it_hint) + "</small>"));
			collectionEditText.setHint(Html.fromHtml("<small>"
					+ getString(R.string.choose_collection_hint) + "</small>"));

			collectItToggleButton = (ToggleButton) view
					.findViewById(R.id.edit_item_collectit_toggle);
			facebookjToggleButton = (ToggleButton) view
					.findViewById(R.id.edit_item_facebook_toggle);
			googlePlusToggleButton = (ToggleButton) view
					.findViewById(R.id.edit_item_googleplus_toggle);

			facebookjToggleButton.setOnClickListener(this);
			googlePlusToggleButton.setOnClickListener(this);

			view.findViewById(R.id.edit_item_save_btn).setOnClickListener(this);
			view.findViewById(R.id.edit_item_delete_btn).setOnClickListener(
					this);

			radioButtonOwn = (RadioButton) view
					.findViewById(R.id.edit_item_own_radio);
			radioButtonSale = (RadioButton) view
					.findViewById(R.id.edit_item_forsale_radio);
			radioButtonWanted = (RadioButton) view
					.findViewById(R.id.edit_item_wanted_radio);

			addTagButton = (Button) view
					.findViewById(R.id.edit_item_tag_add_btn);
			addCollectionButton = (Button) view
					.findViewById(R.id.edit_item_collection_add_btn);

			addTagButton.setOnClickListener(this);
			addCollectionButton.setOnClickListener(this);

			// hit web service to fetch tags list and collection list
			fetchTagsCollection();

			setValues();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		CollectItSharedDataModel.getInstance().getImageGalleryArrayList()
				.clear();
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
		try {
			context = CollectItSharedDataModel.getInstance()
					.getCurrentFragmentActivityContext();

			// Google analytics
			// Send a screen view when the Activity is displayed to the user.
			GoogleAnalyticModel
					.getInstance()
					.getTracker(context)
					.sendView(
							getResources().getString(
									R.string.ga_edit_item_screen));

			// hide setting icon on top bar
			UtilityMethods.setSettingIconVisibility(context, View.GONE);

			// show back button
			UtilityMethods.setBackButtonVisibility(context, View.VISIBLE);

			// set title for the screen
			UtilityMethods.setTitleBarTitle(context,
					CollectItConstants.EDIT_ITEM_SCREEN_ID);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * View pager item click listener
	 * 
	 * @param view
	 *            clicked view/page
	 * @param position
	 *            clicked item's position
	 * @param associatedTaskId
	 *            this will identify the adapter/view pager
	 */
	@Override
	public void onItemSelected(View view, int position, String associatedTaskId) {
		try {
			if (associatedTaskId != null) {
				ViewPagerIds taskId = ViewPagerIds.valueOf(associatedTaskId);

				switch (taskId) {
				/* collections gallery */
				case COLEECTION_GALLERY_ID:
					ArrayList<ItemCollectionDataModel> collectionArrayList = CollectItSharedDataModel
							.getInstance().getCollectionGalleryArrayList();

					if (collectionArrayList != null
							&& collectionArrayList.size() > position) {
						CollectItSharedDataModel.getInstance()
								.getSelectedCollectionList()
								.add(collectionArrayList.get(position));

						// remove this item from main array and update view
						// pager adapter for list
						CollectItSharedDataModel.getInstance()
								.getCollectionGalleryArrayList()
								.remove(position);
						setCollectionPagerAdapter();
						setCollectionSelectedAdapter();
					}

					break;
				/* tag gallery */
				case TAG_GALLERY_ID:
					ArrayList<ItemTagDataModel> tagArrayList = CollectItSharedDataModel
							.getInstance().getTagGalleryArrayList();

					if (tagArrayList != null && tagArrayList.size() > position) {
						CollectItSharedDataModel.getInstance()
								.getSelectedTagsList()
								.add(tagArrayList.get(position));
						// remove this item from main array and update view
						// pager adapter for list
						CollectItSharedDataModel.getInstance()
								.getTagGalleryArrayList().remove(position);
						setTagPagerAdapter();
						setTagSelectedAdapter();
					}

					break;
				/* Image gallery */
				case IMAGE_GALLERY_ID:
					if (position == 0) {
						new DoubleOptionAlertWithoutTitle(context,
								getResources().getString(
										R.string.image_pick_msg),
								getResources().getString(R.string.camera),
								getResources().getString(R.string.gallery),
								DoubleOptionAlertWithoutTitle.IMAGE_PICK, null);
					}
					break;
				default:
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Click event functionality for the view/components
	 */
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
		/* save button */
		case R.id.edit_item_save_btn:
			saveButtonFunc();
			break;
		case R.id.edit_item_delete_btn:
			deleteItemFunc();
			break;
		case R.id.edit_item_facebook_toggle:
			if (facebookjToggleButton.isChecked()) {
				FacebookUtil.getInstance(context).login(context,
						new FacebookResponse() {

							/*
							 * (non-Javadoc)
							 * 
							 * @see com.collect .it.facebook. FacebookResponse #
							 * OnLoginUserDetail ( com.collect. it .facebook.
							 * FacebookUserDataModel )
							 */
							@Override
							public void OnLoginUserDetail(
									FacebookUserDataModel user) {
								// TODO
								// Auto-generated
								// method
								// stub
								super.OnLoginUserDetail(user);

								/*
								 * new ViewToastMsg(context,
								 * " Facebook login success");
								 */

							}

							@Override
							public void OnSessionExpires() {
								// TODO Auto-generated method stub
								super.OnSessionExpires();
								facebookjToggleButton.setChecked(false);
							}

						});
			}
			break;
		case R.id.edit_item_tag_add_btn:
			addTagFunc();
			break;
		case R.id.edit_item_collection_add_btn:
			addCollectionFunc();
			break;
		default:
			break;
		}
	}

	/**
	 * Functionality of save button
	 */
	void saveButtonFunc() {
		String descriptionString = descriptionEditText.getText().toString()
				.trim();

		if (descriptionString != null && !descriptionString.equals("")
				&& !descriptionString.equalsIgnoreCase("null")) {

			ArrayList<ItemTagDataModel> selectedTagsList = CollectItSharedDataModel
					.getInstance().getSelectedTagsList();
			ArrayList<ItemCollectionDataModel> selectedCollectionList = CollectItSharedDataModel
					.getInstance().getSelectedCollectionList();
			Bitmap imageBitmap = CollectItSharedDataModel.getInstance()
					.getImageGalleryArrayList().get(0).getBackgroundBitmap();

			if (selectedTagsList != null && selectedTagsList.size() > 0) {
				if (selectedCollectionList != null
						&& selectedCollectionList.size() > 0) {
					if (imageBitmap != null) {
						DialogProgressCustom.getInstance().startProgressDialog(
								context, true);
						new WebServiceAsyncHttpPostJson(
								context,
								CollectItServerConstants.WEBSERVICE_EDIT_ITEM_DETAIL,
								CollectItConstants.EDIT_ITEM_SCREEN_ID, this,
								createJsonEditItem()).execute();
					} else {
						new SingleOptionAlertWithoutTitle(
								context,
								getResources().getString(R.string.choose_image),
								getResources().getString(R.string.ok), 0);
					}
				} else {
					new SingleOptionAlertWithoutTitle(context, getResources()
							.getString(R.string.enter_collection),
							getResources().getString(R.string.ok), 0);
				}
			} else {
				new SingleOptionAlertWithoutTitle(context, getResources()
						.getString(R.string.enter_tag), getResources()
						.getString(R.string.ok), 0);
			}
		} else {
			new SingleOptionAlertWithoutTitle(context, getResources()
					.getString(R.string.enter_description), getResources()
					.getString(R.string.ok), 0);
		}
	}

	/**
	 * Functionality to listen soft keyboard buttons on the edittext
	 */
	class AddItemEditorActionListener implements OnEditorActionListener {

		@Override
		public boolean onEditorAction(TextView view, int actionId,
				KeyEvent event) {

			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_NEXT) {
				switch (view.getId()) {
				/**
				 * Collections edit text
				 * 
				 * on the done/next button the entered text will be added on to
				 * gallery and the edit box will be available with blank string
				 * */
				case R.id.edit_item_collection_edittext:
					// add collections for an item
					if (collectionEditText.getText().toString().trim()
							.equals("")) {
						// nothing to do
					} else {
						addCollectionFunc();
					}
					// hide keyboard
					UtilityMethods.hideKeyboard(context);
					break;
				/**
				 * tag edit text
				 * 
				 * on the done/next button the entered text will be added on to
				 * gallery and the edit box will be available with blank string
				 */
				case R.id.edit_item_tag_edittext:
					// add tag for an item
					if (tagEditText.getText().toString().trim().equals("")) {
						// nothing to do
					} else {
						addTagFunc();
					}
					// hide keyboard
					UtilityMethods.hideKeyboard(context);

					break;
				default:
					break;
				}
				return true;
			}

			return false;
		}

	}

	/**
	 * Functionality to clear values initially for: 1) clear
	 * selectedCollectionList, 2) clear selectedTagsList, 3) clear
	 * TagGalleryArrayList, 4) clear CollectionGalleryArrayList, 5) clear tag
	 * edit text value, 6) clear collection edit text value
	 */
	void clearClassData() {
		try {

			if (CollectItSharedDataModel.getInstance()
					.getSelectedCollectionList() != null
					&& CollectItSharedDataModel.getInstance()
							.getSelectedCollectionList().size() > 0) {
				CollectItSharedDataModel.getInstance()
						.getSelectedCollectionList().clear();
			}
			if (CollectItSharedDataModel.getInstance().getSelectedTagsList() != null
					&& CollectItSharedDataModel.getInstance()
							.getSelectedTagsList().size() > 0) {
				CollectItSharedDataModel.getInstance().getSelectedTagsList()
						.clear();
			}
			if (CollectItSharedDataModel.getInstance().getTagGalleryArrayList() != null
					&& CollectItSharedDataModel.getInstance()
							.getTagGalleryArrayList().size() > 0) {
				CollectItSharedDataModel.getInstance().getTagGalleryArrayList()
						.clear();
			}
			if (CollectItSharedDataModel.getInstance()
					.getCollectionGalleryArrayList() != null
					&& CollectItSharedDataModel.getInstance()
							.getCollectionGalleryArrayList().size() > 0) {
				CollectItSharedDataModel.getInstance()
						.getCollectionGalleryArrayList().clear();
			}
			tagEditText.getText().clear();
			collectionEditText.getText().clear();
			descriptionEditText.getText().clear();

			collectionEditText.setVisibility(View.VISIBLE);
			tagEditText.setVisibility(View.VISIBLE);
			tagSelectedHorizontalListView.setVisibility(View.GONE);
			collectionSelectedHorizontalListView.setVisibility(View.GONE);

			// clear image gallery
			CollectItSharedDataModel.getInstance().getImageGalleryArrayList()
					.get(0).setBackgroundBitmap(null);
			CollectItSharedDataModel.getInstance().getImageGalleryArrayList()
					.get(0).setBackgroundResource(R.drawable.add_media);
			setImageGalleryPagerAdapter();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to create json object to fetch the recent tags from server
	 * 
	 * @param integer
	 *            value to detect tags or collection
	 * 
	 * @return json object for the server parameters
	 */

	private JSONObject createJsonForRecentTagsCollections(int taskId) {
		JSONObject json = new JSONObject();
		try {
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID,
					CollectItSharedDataModel.getInstance().getUserId());

			switch (taskId) {
			case TAG_SERVER_KEY:
				json.put(CollectItServerConstants.WEBSERVICE_KEY_LIMIT,
						MAX_TAG_SERVER_COUNT);
				break;
			case COLLECTION_SERVER_KEY:
				json.put(CollectItServerConstants.WEBSERVICE_KEY_LIMIT,
						MAX_COLLECTION_SERVER_COUNT);
				break;
			default:
				json.put(CollectItServerConstants.WEBSERVICE_KEY_LIMIT,
						MAX_TAG_SERVER_COUNT);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Functionality to create json object for add item
	 * 
	 * @return json object for add item web service parameters
	 */
	JSONObject createJsonEditItem() {
		JSONObject json = new JSONObject();
		try {
			// add user id
			ArrayList<ItemDataModel> editList = CollectItSharedDataModel
					.getInstance().getEditItemDetailList();
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID, editList
					.get(0).getUserId());

			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_ID, editList
					.get(0).getItemId());

			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_IMAGE_ID,
					editList.get(0).getItemImageId());

			// add description for the item
			String descString = descriptionEditText.getText().toString().trim();
			if (descString == null || descString.equalsIgnoreCase("null")) {
				descString = "";
			}
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_DESCRIPTION,
					descString);

			// add item name
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_NAME,
					descString);

			// add tags for the item
			JSONArray jsonArray = new JSONArray();
			ArrayList<ItemTagDataModel> selectedTagsList = CollectItSharedDataModel
					.getInstance().getSelectedTagsList();
			if (selectedTagsList != null && selectedTagsList.size() > 0) {
				for (int i = 0; i < selectedTagsList.size(); i++) {
					JSONObject arrayObject = new JSONObject();
					arrayObject
							.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_TAG_ID,
									selectedTagsList.get(i).getTagId());
					arrayObject
							.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_TAG_TITLE,
									selectedTagsList.get(i).getTagTitle());
					arrayObject
							.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_TAG_PARENT,
									selectedTagsList.get(i).getTagParentTag());
					jsonArray.put(arrayObject);
				}
			}

			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_TAG_ARRAY,
					jsonArray);

			JSONArray jsonArrayCollection = new JSONArray();
			ArrayList<ItemCollectionDataModel> selectedCollectionList = CollectItSharedDataModel
					.getInstance().getSelectedCollectionList();
			if (selectedCollectionList != null
					&& selectedCollectionList.size() > 0) {
				for (int i = 0; i < selectedCollectionList.size(); i++) {
					JSONObject arrayObjectCollection = new JSONObject();
					arrayObjectCollection
							.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_COLLECTION_ID,
									selectedCollectionList.get(i)
											.getCollectionId());
					arrayObjectCollection
							.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_COLLECTION_TITLE,
									selectedCollectionList.get(i)
											.getCollectionTitle());
					jsonArrayCollection.put(arrayObjectCollection);
				}
			}
			json.put(
					CollectItServerConstants.WEBSERVICE_KEY_ITEM_COLLECTION_ARRAY,
					jsonArrayCollection);

			// add status
			int status = CollectItServerConstants.STATUS_OWN;
			if (radioButtonOwn.isChecked()) {
				status = CollectItServerConstants.STATUS_OWN;
			} else if (radioButtonSale.isChecked()) {
				status = CollectItServerConstants.STATUS_SALE;
			} else if (radioButtonWanted.isChecked()) {
				status = CollectItServerConstants.STATUS_WANTED;
			}
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_STATUS_ID,
					status);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Functionality to create json object to upload item's image
	 * 
	 * @param itemId
	 *            item id for the image
	 * @param itemPicBase64
	 *            Base64 string of the image
	 * 
	 * @return json object
	 */
	JSONObject createJsonToUploadItemImage(String itemId, String itemPicBase64) {
		JSONObject json = new JSONObject();
		try {
			json.put(CollectItServerConstants.WEBSERVICE_KEY_USER_ID,
					CollectItSharedDataModel.getInstance().getUserId());
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_ID, itemId);
			json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_PIC,
					itemPicBase64);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Functionality to hit web service to fetch collections and tags for teg
	 * user
	 */
	void fetchTagsCollection() {

		// clear old data
		clearClassData();

		DialogProgressCustom.getInstance().startProgressDialog(context, true);
		// hit web service to fetch recent tags for the user
		new WebServiceAsyncHttpPostJson(context,
				CollectItServerConstants.WEBSERVICE_GET_RECENT_TAGS,
				TAG_SERVER_KEY, this,
				createJsonForRecentTagsCollections(TAG_SERVER_KEY)).execute();

		// hit server to fetch recent collections for the user
		new WebServiceAsyncHttpPostJson(context,
				CollectItServerConstants.WEBSERVICE_GET_RECENT_COLLECTIONS,
				COLLECTION_SERVER_KEY, this,
				createJsonForRecentTagsCollections(COLLECTION_SERVER_KEY))
				.execute();
	}

	/**
	 * Functionality to set adapter for the tag list
	 */
	void setTagPagerAdapter() {
		try {
			context.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (tagAdapter != null) {
						tagAdapter.notifyDataSetChanged();
					} else {
						tagAdapter = new TagsHorizontalViewAdapter(context,
								EditItemDetailFragment.this);
						tagHorizontalListView.setAdapter(tagAdapter);

					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to set adapter for the collection list
	 */
	void setCollectionPagerAdapter() {
		try {
			context.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (collectionAdapter != null) {
						collectionAdapter.notifyDataSetChanged();
					} else {
						collectionAdapter = new CollectionHorizontalViewAdapter(
								context, EditItemDetailFragment.this);
						collectionHorizontalListView
								.setAdapter(collectionAdapter);

					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to set adapter for the selected tag list that is choosed by
	 * used for an item
	 */
	void setTagSelectedAdapter() {
		try {
			context.runOnUiThread(new Runnable() {

				@Override
				public void run() {

					// set visibility for the components
					// tagEditText.setVisibility(View.GONE);
					tagEditText.getText().clear();
					tagSelectedHorizontalListView.setVisibility(View.VISIBLE);

					if (tagSelectedAdapter != null) {
						tagSelectedAdapter.notifyDataSetChanged();
					} else {
						tagSelectedAdapter = new TagsSelectedHorizontalViewAdapter(
								context, EditItemDetailFragment.this);
						tagSelectedHorizontalListView
								.setAdapter(tagSelectedAdapter);

					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to set adapter for the collection list
	 */
	void setCollectionSelectedAdapter() {
		try {
			context.runOnUiThread(new Runnable() {

				@Override
				public void run() {

					// set visibility for the components
					// collectionEditText.setVisibility(View.GONE);
					collectionEditText.getText().clear();
					collectionSelectedHorizontalListView
							.setVisibility(View.VISIBLE);

					if (collectionSelectedAdapter != null) {
						collectionSelectedAdapter.notifyDataSetChanged();
					} else {
						collectionSelectedAdapter = new CollectionSelectedHorizontalViewAdapter(
								context, EditItemDetailFragment.this);
						collectionSelectedHorizontalListView
								.setAdapter(collectionSelectedAdapter);

					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to set adapter for the image gallery
	 */
	void setImageGalleryPagerAdapter() {
		try {
			context.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (imageGalleryAdapter != null) {
						imageGalleryAdapter.notifyDataSetChanged();
					} else {
						imageGalleryAdapter = new ImageGalleryHorizontalViewAdapter(
								context, EditItemDetailFragment.this);
						imageGalleryHorizontalListView
								.setAdapter(imageGalleryAdapter);

					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Functionality to update item image into image gallery
	 */
	public void updateItemImage(Bitmap bitmap) {
		if (bitmap != null) {
			CollectItSharedDataModel.getInstance().getImageGalleryArrayList()
					.get(0).setBackgroundResource(0);
			CollectItSharedDataModel.getInstance().getImageGalleryArrayList()
					.get(0).setBackgroundBitmap(bitmap);

			imageGalleryAdapter = null;

			setImageGalleryPagerAdapter();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.collect.it.application.CollectItAbstractFragment#getServerValues(
	 * java.lang.String, int, boolean, java.lang.String)
	 */
	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		// TODO Auto-generated method stub
		super.getServerValues(response, id, isOk, exception);
		try {
			if (isOk) {
				if (response != null) {
					parseServerResponse(response, id);
				} else {
					new ViewToastMsg(context, getResources().getString(
							R.string.connection_error_collectit));
				}
			} else if (exception.equalsIgnoreCase(getResources().getString(
					R.string.connection_error_internet))) {
				switch (id) {
				case CollectItConstants.UPLOAD_IMAGE_ID:
					// save image data on to local db to send later
					saveImageDataToLoacalDb();
					break;
				default:
					new ViewToastMsg(context, exception);
					break;
				}

			} else {
				switch (id) {
				case CollectItConstants.UPLOAD_IMAGE_ID:
					// save image data on to local db to send later
					saveImageDataToLoacalDb();
					break;
				default:
					new ViewToastMsg(context, getResources().getString(
							R.string.connection_error));
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// finally close the progress dialog
			// stop progress dialog
			DialogProgressCustom.getInstance().stopProgressDialog();
		}

	}

	/**
	 * Functionality to parse server response
	 * 
	 * @param response
	 *            server response string
	 * @param id
	 *            task id that detects the requested web service hit task
	 */
	private void parseServerResponse(String response, int id) {
		try {
			JSONObject json = new JSONObject(response);
			String responseString = "";
			if (json.has("response")) {
				responseString = json.getString("response");

				if (responseString.equalsIgnoreCase("true")) {

					switch (id) {
					case TAG_SERVER_KEY:

						parseTagResponse(response);

						// finally set adapter or notify adapter
						setTagPagerAdapter();

						break;
					case COLLECTION_SERVER_KEY:
						parseCollectionResponse(response);

						// finally set adapter or notify adapter
						setCollectionPagerAdapter();

						break;
					case CollectItConstants.UPLOAD_IMAGE_ID:
						Log.d("UPLOAD_IMAGE_ID", "" + json.toString());

						break;
					case DELETE_ITEM_SERVER_KEY:
						parseDeleteItemServerResponse(response);
						break;
					default:
						/**
						 * Find the added image for the item
						 */
						Bitmap bitmap = BitmapFactory.decodeResource(
								getResources(), R.drawable.app_icon_gold);
						if (CollectItSharedDataModel.getInstance()
								.getImageGalleryArrayList() != null
								&& CollectItSharedDataModel.getInstance()
										.getImageGalleryArrayList().size() > 0) {
							bitmap = CollectItSharedDataModel.getInstance()
									.getImageGalleryArrayList().get(0)
									.getBackgroundBitmap();

						}

						if (json.has("responseData")) {
							JSONObject responseDataJSon = json
									.getJSONObject("responseData");

							if (responseDataJSon.has("itemId")) {
								itemId = responseDataJSon.getString("itemId");
								itemImageBase64String = ImageUtils
										.getBase64String(ImageUtils
												.getByteArrayFromBitmap(bitmap));
								/** upload image onto server */
								new WebServiceAsyncHttpPostJson(
										context,
										CollectItServerConstants.WEBSERVICE_UPLOAD_IMAGE_FOR_ADDED_ITEM,
										CollectItConstants.UPLOAD_IMAGE_ID,
										EditItemDetailFragment.this,
										createJsonToUploadItemImage(itemId,
												itemImageBase64String))
										.execute();

							}
						}

						/* create message to post on social sites */
						String messageToPost = "Collect.it";
						try {
							ArrayList<ItemCollectionDataModel> selectedCollectionList = CollectItSharedDataModel
									.getInstance().getSelectedCollectionList();
							if (selectedCollectionList != null
									&& selectedCollectionList.size() > 0) {
								String collections = "";
								for (int i = 0; i < selectedCollectionList
										.size(); i++) {
									if (i == 0) {
										collections = selectedCollectionList
												.get(i).getCollectionTitle();
									} else {
										collections = collections
												+ ", "
												+ selectedCollectionList.get(i)
														.getCollectionTitle();
									}

								}
								messageToPost = UtilityMethods.getMessage(
										context, collections);

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						/* wall post on facebook */
						if (facebookjToggleButton.isChecked()) {

							FacebookUtil.getInstance(context).imagePost(bitmap,
									messageToPost, new FacebookResponse() {
										@Override
										public void OnImagePostSuccess(
												Response response) {

											// super.OnImagePostSuccess(response);
											context.runOnUiThread(new Runnable() {

												@Override
												public void run() {
													try {
														new SingleOptionAlertWithoutTitle(
																context,
																getResources()
																		.getString(
																				R.string.facebook_wall_post_msg),
																getResources()
																		.getString(
																				R.string.ok),
																0);
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
											});

										}

										@Override
										public void OnSessionExpires() {

											super.OnSessionExpires();
											new ViewToastMsg(context,
													" Facebook login error");
										}

									});

						}

						/* post on Google+ */
						if (googlePlusToggleButton.isChecked()) {
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

						if (json.has("msg")) {
							String messageString = json.getString("msg");
							CollectItSharedDataModel.getInstance()
									.getHomeItemList().clear();
							new SingleOptionAlertWithoutTitle(
									context,
									messageString,
									getResources().getString(R.string.ok),
									SingleOptionAlertWithoutTitle.CLOSE_CURRENT_FRAGMENT);
						}
						break;

					}

				} else {
					if (json.has("errorText")) {
						String errorString = json.getString("errorText");
						switch (id) {
						/* Do not show error message for tag/collection list */
						case TAG_SERVER_KEY:
						case COLLECTION_SERVER_KEY:
							// nothing to do
							break;
						case CollectItConstants.UPLOAD_IMAGE_ID:
							// save image data to upload later on to server
							saveImageDataToLoacalDb();
							break;
						default:
							new SingleOptionAlertWithoutTitle(context,
									errorString, getResources().getString(
											R.string.ok), 0);
							break;
						}

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			new ViewToastMsg(context, getResources().getString(
					R.string.connection_error_collectit));
		}
	}

	/**
	 * Functionality to parse the tag response
	 * 
	 * @param response
	 *            server response string
	 */
	void parseTagResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);

			if (json.has("responseData")) {
				JSONArray responseDataJson = json.getJSONArray("responseData");
				if (responseDataJson != null && responseDataJson.length() > 0) {
					// clear old data for the tags
					CollectItSharedDataModel.getInstance()
							.getTagGalleryArrayList().clear();
					for (int i = 0; i < responseDataJson.length(); i++) {

						JSONObject jsonIndex = responseDataJson
								.getJSONObject(i);

						ItemTagDataModel dataModel = new ItemTagDataModel();

						if (jsonIndex.has("Tag")) {
							JSONObject tagJSon = jsonIndex.getJSONObject("Tag");

							if (tagJSon.has("id")) {
								dataModel.setTagId(tagJSon.getString("id"));
							}

							if (tagJSon.has("tag_title")) {
								dataModel.setTagTitle(tagJSon
										.getString("tag_title"));
							}

							if (tagJSon.has("tag_description")) {
								dataModel.setTagDescription(tagJSon
										.getString("tag_description"));
							}
							if (tagJSon.has("parent_tag")) {
								dataModel.setTagParentTag(tagJSon
										.getString("parent_tag"));
							}

							dataModel
									.setTagImageBackground(R.drawable.tag_collection_shape_blue);

						}

						// finally add onto main list
						CollectItSharedDataModel.getInstance()
								.getTagGalleryArrayList().add(dataModel);

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// nothing to do
		}
	}

	/**
	 * Functionality to parse the collection response
	 * 
	 * @param response
	 *            server response string
	 */
	void parseCollectionResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);

			if (json.has("responseData")) {
				JSONArray responseDataJson = json.getJSONArray("responseData");
				if (responseDataJson != null && responseDataJson.length() > 0) {
					// clear old data for the tags
					CollectItSharedDataModel.getInstance()
							.getCollectionGalleryArrayList().clear();

					for (int i = 0; i < responseDataJson.length(); i++) {

						JSONObject jsonIndex = responseDataJson
								.getJSONObject(i);

						ItemCollectionDataModel dataModel = new ItemCollectionDataModel();

						if (jsonIndex.has("Collection")) {
							JSONObject collectionJson = jsonIndex
									.getJSONObject("Collection");

							if (collectionJson.has("id")) {
								dataModel.setCollectionId(collectionJson
										.getString("id"));
							}

							if (collectionJson.has("collection_title")) {
								dataModel.setCollectionTitle(collectionJson
										.getString("collection_title"));
							}

							if (collectionJson.has("collection_description")) {
								dataModel
										.setCollectionDescription(collectionJson
												.getString("collection_description"));
							}

							dataModel
									.setCollectionImageBackground(R.drawable.tag_collection_shape_red);

						}

						// finally add onto main list
						CollectItSharedDataModel.getInstance()
								.getCollectionGalleryArrayList().add(dataModel);

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * touch event functionality for the view pagers
	 * 
	 * this will allow scrolling of view pager items under the scroll view
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.edit_item_image_viewpager:
		case R.id.edit_item_tag_viewpager:
		case R.id.edit_item_collection_viewpager:
			v.getParent().requestDisallowInterceptTouchEvent(true);
			break;
		default:
			break;
		}
		return false;
	}

	/**
	 * Functionality to save image data on to local db of application to upload
	 * later
	 */

	void saveImageDataToLoacalDb() {

		DatabaseManipulation.insertData(
		// table name
				CollectItDatabaseConstants.ITEM_IMAGE_TABLE_NAME,
				// column names
				new String[] { CollectItDatabaseConstants.USER_ID,
						CollectItDatabaseConstants.ITEM_ID,
						CollectItDatabaseConstants.ITEM_IMAGE },
				// values respect to columns
				new String[] {
						CollectItSharedDataModel.getInstance().getUserId(),
						itemId, itemImageBase64String },
				// SQLiteDatabase object
				CollectItDatabase.getInstance(context));
	}

	/**
	 * Functionality to set values against components
	 */
	synchronized void setValues() {
		ArrayList<ItemDataModel> editList = CollectItSharedDataModel
				.getInstance().getEditItemDetailList();
		if (editList != null && editList.size() > 0) {
			String descriptionString = editList.get(0).getItemDescription();
			// set description value for item
			if (descriptionString == null
					|| descriptionString.equalsIgnoreCase("null")) {
				descriptionString = "";
			}
			descriptionEditText.setText(descriptionString);

			ArrayList<ItemCollectionDataModel> collectionList = editList.get(0)
					.getItemCollectionList();
			if (collectionList != null && collectionList.size() > 0) {
				CollectItSharedDataModel.getInstance()
						.setSelectedCollectionList(collectionList);

				setCollectionSelectedAdapter();
			}

			ArrayList<ItemTagDataModel> tagList = editList.get(0)
					.getItemTagList();
			if (tagList != null && tagList.size() > 0) {
				CollectItSharedDataModel.getInstance().setSelectedTagsList(
						tagList);

				setTagSelectedAdapter();
			}

			// set item image on image gallery.
			// we have to use async task because we can not perform IO on main
			// thread in android
			final String itemImage = editList.get(0).getItemImage();

			if (itemImage != null && !itemImage.equals("")
					&& !itemImage.equalsIgnoreCase("null")) {
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						try {
							DisplayMetrics displayMatrix = CollectItSharedDataModel
									.getInstance().getDisplayMetrics();
							URL url = new URL(itemImage);
							Bitmap bitmap = ImageUtils
									.decodeScaledBitmapFromUrl(context,
											url.toString(),
											displayMatrix.widthPixels,
											displayMatrix.widthPixels);// BitmapFactory.decodeStream(url
							// .openConnection().getInputStream());//
							// ImageUtils.getBitmapFromURL(itemImage);
							if (bitmap != null) {
								CollectItSharedDataModel.getInstance()
										.getImageGalleryArrayList().get(0)
										.setBackgroundBitmap(bitmap);
								CollectItSharedDataModel.getInstance()
										.getImageGalleryArrayList().get(0)
										.setBackgroundResource(0);
								bitmap = null;

							}
						} catch (OutOfMemoryError e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						// super.onPostExecute(result);
						setImageGalleryPagerAdapter();
					}
				}.execute();

			}

		}

		// item status
		// item status
		String statusString = editList.get(0).getItemStatus();
		if (statusString != null && !statusString.equals("")
				&& !statusString.equalsIgnoreCase("null")) {
			try {
				switch (Integer.valueOf(statusString)) {
				case CollectItServerConstants.STATUS_OWN:
					radioButtonOwn.setChecked(true);
					break;
				case CollectItServerConstants.STATUS_SALE:
					radioButtonSale.setChecked(true);
					break;
				case CollectItServerConstants.STATUS_WANTED:
					radioButtonWanted.setChecked(true);
					break;
				default:
					radioButtonOwn.setChecked(true);
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** Functionality to removed selected tags */
	@Override
	public void onSelectedTagRemoved(int position,
			ArrayList<ItemTagDataModel> tagList) {
		CollectItSharedDataModel
				.getInstance()
				.getTagGalleryArrayList()
				.add(CollectItSharedDataModel.getInstance()
						.getSelectedTagsList().get(position));
		CollectItSharedDataModel.getInstance().getSelectedTagsList()
				.remove(position);

		setTagPagerAdapter();
		setTagSelectedAdapter();

	}

	/** Functionality to removed selected collections */
	@Override
	public void onSelectedCollectionRemoved(int position,
			ArrayList<ItemCollectionDataModel> collectionList) {
		CollectItSharedDataModel
				.getInstance()
				.getCollectionGalleryArrayList()
				.add(CollectItSharedDataModel.getInstance()
						.getSelectedCollectionList().get(position));
		CollectItSharedDataModel.getInstance().getSelectedCollectionList()
				.remove(position);

		setCollectionPagerAdapter();
		setCollectionSelectedAdapter();

	}

	/**
	 * Functionality to add collections for an item
	 */
	void addCollectionFunc() {
		String collectionString = collectionEditText.getText().toString()
				.trim();
		if (collectionString != null && !collectionString.equals("")
				&& !collectionString.equalsIgnoreCase("null")) {
			ArrayList<ItemCollectionDataModel> collectionArrayList = CollectItSharedDataModel
					.getInstance().getSelectedCollectionList();
			if (collectionArrayList != null) {
				if (collectionArrayList.size() < COLLECTION_MAX) {
					ItemCollectionDataModel collectionModel = new ItemCollectionDataModel();
					collectionModel.setCollectionTitle(collectionString);
					collectionModel
							.setCollectionImageBackground(R.drawable.tag_collection_shape_red);
					CollectItSharedDataModel.getInstance()
							.getSelectedCollectionList().add(collectionModel);
					// update view pager list adapter
					setCollectionSelectedAdapter();
					descriptionEditText.setFocusable(false);
					// clear the edit text values
					// collectionEditText.getText().clear();
				}
			}
		} else {
			new ViewToastMsg(context, getResources().getString(
					R.string.add_collection_alert_msg));
		}
	}

	/**
	 * Functionality to add tag for an item
	 */
	void addTagFunc() {
		String tagString = tagEditText.getText().toString().trim();
		if (tagString != null && !tagString.equals("")
				&& !tagString.equalsIgnoreCase("null")) {
			ArrayList<ItemTagDataModel> tagArrayList = CollectItSharedDataModel
					.getInstance().getSelectedTagsList();
			if (tagArrayList != null) {
				if (tagArrayList.size() < TAG_MAX) {
					ItemTagDataModel collectionModel = new ItemTagDataModel();
					collectionModel.setTagTitle(tagString);
					collectionModel
							.setTagImageBackground(R.drawable.tag_collection_shape_blue);
					CollectItSharedDataModel.getInstance()
							.getSelectedTagsList().add(collectionModel);
					// update view pager list adapter
					setTagSelectedAdapter();
					descriptionEditText.setFocusable(false);
					// clear the edit text values
					// tagEditText.getText().clear();
				}
			}
		} else {
			new ViewToastMsg(context, getResources().getString(
					R.string.add_tag_alert_msg));
		}
	}

	/**
	 * Functionality to delete an item
	 */
	void deleteItemFunc() {
		/*
		 * new DoubleOptionAlertWithoutTitle(context, getResources().getString(
		 * R.string.delete_item_msg), getResources().getString(
		 * R.string.yes_btn), getResources().getString(R.string.no_btn),
		 * DoubleOptionAlertWithoutTitle.DELETE_ITEM);
		 */
		DialogProgressCustom.getInstance().startProgressDialog(context, true);
		new WebServiceAsyncHttpPostJson(context,
				CollectItServerConstants.WEBSERVICE_DELETE_ITEM,
				DELETE_ITEM_SERVER_KEY, this, createDeleteItemJson()).execute();
	}

	/**
	 * Functionality to create json object to delete an item web service
	 * parameter
	 */
	JSONObject createDeleteItemJson() {
		JSONObject json = new JSONObject();
		try {

			ArrayList<ItemDataModel> editList = CollectItSharedDataModel
					.getInstance().getEditItemDetailList();
			if (editList != null && editList.size() > 0) {
				json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_ID,
						editList.get(0).getItemId());
			} else {
				json.put(CollectItServerConstants.WEBSERVICE_KEY_ITEM_ID, "");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Functionality to parse delete item server response
	 * 
	 * @param servere
	 *            response
	 */
	void parseDeleteItemServerResponse(String response) {
		try {
			JSONObject json = new JSONObject(response);
			String responseString = "";
			if (json.has("response")) {
				responseString = json.getString("response");

				if (responseString.equalsIgnoreCase("true")) {
					if (json.has("msg")) {
						String messageString = json.getString("msg");
						CollectItSharedDataModel.getInstance()
								.getHomeItemList().clear();
						new SingleOptionAlertWithoutTitle(
								context,
								messageString,
								getResources().getString(R.string.ok),
								SingleOptionAlertWithoutTitle.DELETE_ITEM_FRAGMENT);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
