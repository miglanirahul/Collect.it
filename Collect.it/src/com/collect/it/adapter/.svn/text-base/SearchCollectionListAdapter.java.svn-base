package com.collect.it.adapter;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collect.it.R;
import com.collect.it.constants.CollectItConstants;
import com.collect.it.constants.CollectItEnums.FragmentTagNames;
import com.collect.it.fragments.CollectionItemListFragment;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.ItemCollectionDataModel;
import com.collect.it.utils.ImageUtils;
import com.collect.it.utils.UtilityMethods;

/**
 * adapter class that will display the collection list for the search screen
 */
public class SearchCollectionListAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (CollectItSharedDataModel.getInstance().getSearchCollectionList() != null
				&& CollectItSharedDataModel.getInstance()
						.getSearchCollectionList().size() > 0
				&& CollectItSharedDataModel.getInstance()
						.getSearchCollectionList().get(0).getCollectionList() != null
				&& CollectItSharedDataModel.getInstance()
						.getSearchCollectionList().get(0).getCollectionList()
						.size() > 0) {
			return CollectItSharedDataModel.getInstance()
					.getSearchCollectionList().get(0).getCollectionList()
					.size();
		} else {
			return 0;
		}

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
	public View getView(final int position, View view, ViewGroup arg2) {
		View rowView = view;
		try {
			if (rowView == null) {
				rowView = LayoutInflater.from(
						CollectItSharedDataModel.getInstance()
								.getCurrentFragmentActivityContext()).inflate(
						R.layout.frag_search_item_collection, null);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.collectionImageView = (ImageView) rowView
						.findViewById(R.id.search_item_collection_item_data_item_img);
				viewHolder.imageProgressBar = (ProgressBar) rowView
						.findViewById(R.id.search_item_collection_item_data_itemimage_progressBar);
				viewHolder.collectionNameTextView = (TextView) rowView
						.findViewById(R.id.search_item_collection_item_data_item_desc_txt);
				viewHolder.mainLinear = (LinearLayout) rowView
						.findViewById(R.id.search_item_collection_main_linear);

				rowView.setTag(viewHolder);
			}

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();

			final ArrayList<ItemCollectionDataModel> collectionList = CollectItSharedDataModel
					.getInstance().getSearchCollectionList().get(0)
					.getCollectionList();

			String collectionNameString = collectionList.get(position)
					.getCollectionTitle();
			if (collectionNameString == null
					|| collectionNameString.equalsIgnoreCase("null")) {
				collectionNameString = "";
			}
			viewHolder.collectionNameTextView.setText(collectionNameString);

			// image
			String collectionImageString = collectionList.get(position)
					.getCollectionImage();
			if (collectionImageString != null
					&& !collectionImageString.equalsIgnoreCase("null")
					&& !collectionImageString.equals("")) {
				ImageUtils.getInstance(
						CollectItSharedDataModel.getInstance()
								.getCurrentFragmentActivityContext())
						.setImageUrlToView(collectionImageString,
								viewHolder.collectionImageView,
								viewHolder.imageProgressBar,
								R.drawable.app_icon_gold, false, true);
			}

			viewHolder.mainLinear.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						Bundle bundle = new Bundle();
						bundle.putString(
								CollectItConstants.BUNDLE_COLLECTION_ID_KEY,
								collectionList.get(position).getCollectionId());

						UtilityMethods.replaceFragment(
								new CollectionItemListFragment(),
								CollectItSharedDataModel.getInstance()
										.getCurrentFragmentActivityContext(),
								R.id.tab_content, true, true,
								FragmentTagNames.COLLECTION_ITEM_LIST.name(),
								bundle);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowView;
	}

	class ViewHolder {
		ImageView collectionImageView;
		TextView collectionNameTextView;
		ProgressBar imageProgressBar;
		LinearLayout mainLinear;
	}
}
