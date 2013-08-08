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
import com.collect.it.fragments.AddItemFragment;
import com.collect.it.interfaces.OnSelectedItemProcess;
import com.collect.it.interfaces.OnViewPagerItemSelectionProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.ItemTagDataModel;

/**
 * This adapter class used to display the gallery for the selected tags that
 * user wants to associate with an item
 */
public class TagsSelectedHorizontalViewAdapter extends BaseAdapter {

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
	public TagsSelectedHorizontalViewAdapter(Activity context,
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

		/*
		 * if (position == 0) { if (rowView == null || rowView.getId() !=
		 * R.layout.viewpager_edittext_item) { rowView =
		 * LayoutInflater.from(context).inflate(
		 * R.layout.viewpager_edittext_item, null);
		 * 
		 * // initiate variables ViewHolderEditText viewHolderEditText = new
		 * ViewHolderEditText(); viewHolderEditText.editText = (EditText)
		 * rowView .findViewById(R.id.viewpager_item_edittext);
		 * 
		 * rowView.setTag(viewHolderEditText); } try { final ViewHolderEditText
		 * holderEditText = (ViewHolderEditText) rowView .getTag(); // set hint
		 * for the edit text
		 * holderEditText.editText.setHint(R.string.tag_it_hint); if
		 * (CollectItSharedDataModel.getInstance() .getSelectedTagsList().size()
		 * > position - 1) { holderEditText.editText.getText().clear();
		 * 
		 * }
		 * 
		 * holderEditText.editText .setOnEditorActionListener(new
		 * OnEditorActionListener() {
		 * 
		 * @Override public boolean onEditorAction(TextView v, int actionId,
		 * KeyEvent event) { if (actionId == EditorInfo.IME_ACTION_DONE ||
		 * actionId == EditorInfo.IME_ACTION_NEXT) { String tagString =
		 * holderEditText.editText .getText().toString().trim(); if (tagString
		 * != null && !tagString.equals("") && !tagString
		 * .equalsIgnoreCase("null")) { ArrayList<ItemTagDataModel> tagArrayList
		 * = CollectItSharedDataModel .getInstance() .getSelectedTagsList(); if
		 * (tagArrayList != null) { if (tagArrayList.size() <
		 * AddItemFragment.TAG_MAX) { ItemTagDataModel collectionModel = new
		 * ItemTagDataModel(); collectionModel .setTagTitle(tagString);
		 * collectionModel .setTagImageBackground(R.drawable.golden_tag);
		 * CollectItSharedDataModel .getInstance() .getSelectedTagsList()
		 * .add(collectionModel);
		 * 
		 * holderEditText.editText .getText().clear();
		 * 
		 * notifyDataSetChanged(); } } } return true; } return false; } });
		 * 
		 * } catch (Exception e) { e.printStackTrace();
		 * 
		 * }
		 * 
		 * } else {
		 */
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
			if (CollectItSharedDataModel.getInstance().getSelectedTagsList()
					.size() > position) {
				holder.textView.setText(CollectItSharedDataModel.getInstance()
						.getSelectedTagsList().get(position).getTagTitle());
				holder.textView.setBackgroundResource(CollectItSharedDataModel
						.getInstance().getSelectedTagsList().get(position)
						.getTagImageBackground());

				/*try {
					holder.textView.setTextColor(context.getResources().getColor(
							R.color.blue));
				} catch (Exception e) {
					e.printStackTrace();
				}*/

				holder.crossImageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (onSelectedItemListener != null) {
							onSelectedItemListener.onSelectedTagRemoved(
									position, CollectItSharedDataModel
											.getInstance()
											.getSelectedTagsList());
						}
					}
				});
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		// }

		return rowView;
	}

	@Override
	public int getCount() {
		if (CollectItSharedDataModel.getInstance().getSelectedTagsList() != null) {
			return CollectItSharedDataModel.getInstance().getSelectedTagsList()
					.size()/* + 1 */;
		} else {
			return 0;
		}
	}

}
