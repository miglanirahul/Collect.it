package com.collect.it.adapter;

import com.collect.it.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This adapter class is used to display the grid list of collection items of
 * the user
 */
public class ItemGridListAdapter extends BaseAdapter {
	/**
	 * Declare class variables
	 */
	Activity context;

	/**
	 * Constructor definition
	 * 
	 * @param context
	 *            Activity context
	 */
	public ItemGridListAdapter(Activity context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
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
	public View getView(int arg0, View view, ViewGroup arg2) {
		View rowView = view;
		if (rowView != null) {
			rowView = LayoutInflater.from(context).inflate(
					R.layout.collection_item_data, null);

			ViewHolder viewHolder = new ViewHolder();

			viewHolder.itemDescriptionTextView = (TextView) rowView
					.findViewById(R.id.collection_item_data_item_desc_txt);
			viewHolder.userName = (TextView) rowView
					.findViewById(R.id.collection_item_data_user_name_txt);
			viewHolder.touchCountTextView = (TextView) rowView
					.findViewById(R.id.collection_item_data_touches_count_txt);

			viewHolder.itemImageView = (ImageView) rowView
					.findViewById(R.id.collection_item_data_item_img);
			viewHolder.touchImageView = (ImageView) rowView
					.findViewById(R.id.collection_item_data_touches_img);

			rowView.setTag(viewHolder);
		}

		ViewHolder viewholder = (ViewHolder) rowView.getTag();

		// set values
		
		return view;
	}

	/**
	 * class to hold components for the grid view items
	 */
	private class ViewHolder {
		ImageView itemImageView, touchImageView;
		FrameLayout imageTouchFrameLayout;
		TextView userName, itemDescriptionTextView, touchCountTextView;
	}

}
