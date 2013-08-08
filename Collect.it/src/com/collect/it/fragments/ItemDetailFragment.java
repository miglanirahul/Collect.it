package com.collect.it.fragments;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.collect.it.R;
import com.collect.it.adapter.CollectionGalleryEditAdapter;
import com.collect.it.adapter.CollectionSelectedHorizontalViewAdapter;
import com.collect.it.adapter.TagGalleryEditAdapter;
import com.collect.it.adapter.TagsSelectedHorizontalViewAdapter;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.constants.CollectItServerConstants;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.model.ItemCollectionDataModel;
import com.collect.it.model.ItemDataModel;
import com.collect.it.model.ItemTagDataModel;
import com.collect.it.utils.HorizontalListView;

/**
 * This fragment used to display the tags, collections, description and status
 * of an collection item
 */
public class ItemDetailFragment extends CollectItAbstractFragment {
	/**
	 * Declare variables
	 */
	private FragmentActivity context;

	private TextView descriptionTextView, statusTextView;
	private HorizontalListView tagsHorizontalView, collectionsHorizontalView;

	/** adapter for the selected tag list */
	private TagGalleryEditAdapter tagAdapter;
	/** adapter for the selected collection list */
	private CollectionGalleryEditAdapter collectionAdapter;

	@Override
	public View initialization(LayoutInflater inflater) {

		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		View view = inflater.inflate(R.layout.frag_item_detail, null);

		descriptionTextView = (TextView) view
				.findViewById(R.id.item_detail_desc_txt);
		statusTextView = (TextView) view
				.findViewById(R.id.item_detail_status_txt);

		tagsHorizontalView = (HorizontalListView) view
				.findViewById(R.id.item_detail_tag_horizontal);
		collectionsHorizontalView = (HorizontalListView) view
				.findViewById(R.id.item_detail_collection_horizontal);

		return view;
	}

	/**
	 * Functionality to set values
	 * 
	 * @param itemDataArrayList
	 *            array list that contains item details
	 * 
	 */
	void setValues(ArrayList<ItemDataModel> itemDataArrayList) {
		try {
			if (itemDataArrayList != null && itemDataArrayList.size() > 0) {

				String description = itemDataArrayList.get(0)
						.getItemDescription();
				ArrayList<ItemTagDataModel> itemTagList = itemDataArrayList
						.get(0).getItemTagList();
				ArrayList<ItemCollectionDataModel> itemCollectionList = itemDataArrayList
						.get(0).getItemCollectionList();
				if (description == null || description.equalsIgnoreCase("null")) {
					description = "";
				}
				descriptionTextView.setText(description);

				if (itemTagList != null && itemTagList.size() > 0) {
					CollectItSharedDataModel.getInstance()
							.setTagGalleryEditArrayList(itemTagList);

					setTagSelectedAdapter();
				}

				/*
				 * if (itemTagList != null && itemTagList.size() > 0) {
				 * tagsHorizontalView.setAdapter(new TagAdapter(itemTagList)); }
				 */

				/*
				 * if (itemCollectionList != null && itemCollectionList.size() >
				 * 0) { collectionsHorizontalView.setAdapter(new
				 * CollectionAdapter( itemCollectionList)); }
				 */
				if (itemCollectionList != null && itemCollectionList.size() > 0) {
					CollectItSharedDataModel.getInstance()
							.setCollectionEditGalleryArrayList(
									itemCollectionList);

					setCollectionSelectedAdapter();
				}

				// item status
				String statusString = itemDataArrayList.get(0).getItemStatus();
				if (statusString != null && !statusString.equals("")
						&& !statusString.equalsIgnoreCase("null")) {
					try {
						switch (Integer.valueOf(statusString)) {
						case CollectItServerConstants.STATUS_OWN:
							statusString = getResources().getString(
									R.string.own);
							break;
						case CollectItServerConstants.STATUS_SALE:
							statusString = getResources().getString(
									R.string.for_sale);
							break;
						case CollectItServerConstants.STATUS_WANTED:
							statusString = getResources().getString(
									R.string.wanted);
							break;
						default:
							statusString = getResources().getString(
									R.string.own);
							break;
						}

						// set text value
						statusTextView.setText(statusString);

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

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

					if (collectionAdapter != null) {
						collectionAdapter.notifyDataSetChanged();
					} else {
						collectionAdapter = new CollectionGalleryEditAdapter(
								context, null);
						collectionsHorizontalView.setAdapter(collectionAdapter);

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

					if (tagAdapter != null) {
						tagAdapter.notifyDataSetChanged();
					} else {
						tagAdapter = new TagGalleryEditAdapter(context, null);
						tagsHorizontalView.setAdapter(tagAdapter);

					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class ViewHolderTag {
		TextView textView;
	}

	class ViewHolderCollection {
		TextView textView;
	}

	private class TagAdapter extends BaseAdapter {

		private ArrayList<ItemTagDataModel> tagList;

		TagAdapter(ArrayList<ItemTagDataModel> tagList) {
			this.tagList = tagList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tagList.size();
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
		public View getView(int position, View convertView, ViewGroup arg2) {
			View rowView = convertView;
			if (rowView == null) {
				rowView = LayoutInflater.from(context).inflate(
						R.layout.viewpager_tag_collection_item, null);

				// initiate variables
				ViewHolderTag viewHolder = new ViewHolderTag();
				viewHolder.textView = (TextView) rowView
						.findViewById(R.id.viewpager_tag_collection_item_txt);

				rowView.setTag(viewHolder);
			}
			try {
				final ViewHolderTag holder = (ViewHolderTag) rowView.getTag();
				if (CollectItSharedDataModel.getInstance()
						.getTagGalleryArrayList().size() > position) {
					holder.textView
							.setText(tagList.get(position).getTagTitle());
					holder.textView.setBackgroundResource(tagList.get(position)
							.getTagImageBackground());
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
			return rowView;
		}

	}

	private class CollectionAdapter extends BaseAdapter {

		private ArrayList<ItemCollectionDataModel> collectionList;

		CollectionAdapter(ArrayList<ItemCollectionDataModel> collectionList) {
			this.collectionList = collectionList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return collectionList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			View rowView = convertView;
			if (rowView == null) {
				rowView = LayoutInflater.from(context).inflate(
						R.layout.viewpager_tag_collection_item, null);

				// initiate variables
				ViewHolderCollection viewHolder = new ViewHolderCollection();
				viewHolder.textView = (TextView) rowView
						.findViewById(R.id.viewpager_tag_collection_item_txt);

				rowView.setTag(viewHolder);
			}
			try {
				final ViewHolderCollection holder = (ViewHolderCollection) rowView
						.getTag();
				if (CollectItSharedDataModel.getInstance()
						.getTagGalleryArrayList().size() > position) {
					holder.textView.setText(collectionList.get(position)
							.getCollectionTitle());
					holder.textView.setBackgroundResource(collectionList.get(
							position).getCollectionImageBackground());
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
			return rowView;
		}

	}

}
