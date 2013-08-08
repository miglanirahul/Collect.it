package com.collect.it.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.collect.it.R;
import com.collect.it.adapter.CollectionHorizontalViewAdapter.ViewHolder;
import com.collect.it.adapter.TagsSelectedHorizontalViewAdapter.ViewHolderEditText;
import com.collect.it.constants.CollectItEnums.ViewPagerIds;
import com.collect.it.fragments.AddItemFragment;
import com.collect.it.interfaces.OnSelectedItemProcess;
import com.collect.it.interfaces.OnViewPagerItemSelectionProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.ItemCollectionDataModel;

/**
 * This adapter class used to display the gallery for the selected collection
 * that user wants to associate with an item
 */
public class CollectionSelectedHorizontalViewAdapter extends BaseAdapter {

	/**
	 * Declare class variables
	 */

	private Activity context;

	private OnSelectedItemProcess onSelectedItemListener;

	/**
	 * Constructor definition
	 * 
	 * @param context
	 *            Activity context
	 * @param onImageGalleryItemClickListener
	 *            callback for the associated view pager screen/class
	 */
	public CollectionSelectedHorizontalViewAdapter(Activity context,
			OnSelectedItemProcess onSelectedItemListener) {
		this.context = context;
		this.onSelectedItemListener = onSelectedItemListener;
	}

	class ViewHolder {
		TextView textView;
		ImageView crossImageView;
	}

	class ViewHolderEditText {
		EditText editText;
	}

	// -----------------------------------------------------------------------------
	// Used by ViewPager. Called when ViewPager needs a page to display; it is
	// our job
	// to add the page to the container, which is normally the ViewPager itself.
	// Since
	// all our pages are persistent, we simply retrieve it from our "views"
	// ArrayList.
	/*
	 * @Override public Object instantiateItem(ViewGroup container, final int
	 * position) {
	 * 
	 * }
	 * 
	 * //
	 * ------------------------------------------------------------------------
	 * ----- // Used by ViewPager. Called when ViewPager no longer needs a page
	 * to // display; it // is our job to remove the page from the container,
	 * which is normally the // ViewPager itself. Since all our pages are
	 * persistent, we do nothing to // the // contents of our "views" ArrayList.
	 * 
	 * @Override public void destroyItem(View arg0, int arg1, Object arg2) {
	 * ((ViewPager) arg0).removeView((View) arg2); }
	 */

	// -----------------------------------------------------------------------------
	// Used by ViewPager; can be used by app as well.
	// Returns the total number of pages that the ViewPage can display. This
	// must
	// never be 0.
	@Override
	public int getCount() {
		if (CollectItSharedDataModel.getInstance().getSelectedCollectionList() != null) {
			return CollectItSharedDataModel.getInstance()
					.getSelectedCollectionList().size() /*+ 1*/;
		} else {
			return 0;
		}

	}

	// -----------------------------------------------------------------------------
	// Used by ViewPager.
	/*
	 * @Override public boolean isViewFromObject(View view, Object object) {
	 * return view == ((View) object); }
	 * 
	 * @Override public float getPageWidth(int position) { return (0.3f); }
	 */

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		/*if (position == 0) {
			if (rowView == null
					|| rowView.getId() != R.layout.viewpager_edittext_item) {
				rowView = LayoutInflater.from(context).inflate(
						R.layout.viewpager_edittext_item, null);

				// initiate variables
				ViewHolderEditText viewHolderEditText = new ViewHolderEditText();
				viewHolderEditText.editText = (EditText) rowView
						.findViewById(R.id.viewpager_item_edittext);

				rowView.setTag(viewHolderEditText);
			}
			try {
				final ViewHolderEditText holderEditText = (ViewHolderEditText) rowView
						.getTag();

				// set hint for the edit text
				holderEditText.editText
						.setHint(R.string.choose_collection_hint);
				if (CollectItSharedDataModel.getInstance()
						.getSelectedTagsList().size() > position - 1) {
					holderEditText.editText.getText().clear();
				}

				holderEditText.editText
						.setOnEditorActionListener(new OnEditorActionListener() {

							@Override
							public boolean onEditorAction(TextView v,
									int actionId, KeyEvent event) {
								if (actionId == EditorInfo.IME_ACTION_DONE
										|| actionId == EditorInfo.IME_ACTION_NEXT) {
									String collectionString = holderEditText.editText
											.getText().toString().trim();
									if (collectionString != null
											&& !collectionString.equals("")
											&& !collectionString
													.equalsIgnoreCase("null")) {
										ArrayList<ItemCollectionDataModel> collectionArrayList = CollectItSharedDataModel
												.getInstance()
												.getSelectedCollectionList();
										if (collectionArrayList != null) {
											if (collectionArrayList.size() < AddItemFragment.COLLECTION_MAX) {
												ItemCollectionDataModel collectionModel = new ItemCollectionDataModel();
												collectionModel
														.setCollectionTitle(collectionString);
												collectionModel
														.setCollectionImageBackground(R.drawable.red_tag_big);
												CollectItSharedDataModel
														.getInstance()
														.getSelectedCollectionList()
														.add(collectionModel);

												holderEditText.editText
														.getText().clear();
												notifyDataSetChanged();
											}
										}
									}
									return true;
								}
								return false;
							}
						});

			} catch (Exception e) {
				e.printStackTrace();

			}

		} else {*/
			if (rowView == null
					|| rowView.getId() != R.layout.item_tag_collection_selected_item_row) {
				rowView = LayoutInflater.from(context).inflate(
						R.layout.item_tag_collection_selected_item_row, null);

				// initiate variables
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.textView = (TextView) rowView
						.findViewById(R.id.item_tag_collection_selected_txt);
				viewHolder.crossImageView = (ImageView) rowView
						.findViewById(R.id.item_tag_collection_selected_cros_img);

				rowView.setTag(viewHolder);
			}
			try {

				final ViewHolder holder = (ViewHolder) rowView.getTag();
				if (CollectItSharedDataModel.getInstance()
						.getSelectedCollectionList().size() > position) {
					holder.textView.setText(CollectItSharedDataModel
							.getInstance().getSelectedCollectionList()
							.get(position).getCollectionTitle());
					holder.textView
							.setBackgroundResource(CollectItSharedDataModel
									.getInstance().getSelectedCollectionList()
									.get(position)
									.getCollectionImageBackground());
					holder.crossImageView.setImageResource(R.drawable.icon_cross_red);
					holder.crossImageView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if(onSelectedItemListener != null){
								onSelectedItemListener.onSelectedCollectionRemoved(position, CollectItSharedDataModel
										.getInstance().getSelectedCollectionList());
							}
						}
					});
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		//}
		return rowView;
	}

}
