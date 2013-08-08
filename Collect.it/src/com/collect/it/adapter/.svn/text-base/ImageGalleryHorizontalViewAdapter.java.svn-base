package com.collect.it.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.collect.it.R;
import com.collect.it.adapter.CollectionHorizontalViewAdapter.ViewHolder;
import com.collect.it.constants.CollectItEnums.ViewPagerIds;
import com.collect.it.interfaces.OnViewPagerItemSelectionProcess;
import com.collect.it.model.CollectItSharedDataModel;
import com.collect.it.utils.ImageUtils;

/**
 * The pager adapter class that is used to display data as a gallery view 1)
 * AddItem screen : image gallery 2) AddItem screen : tags gallery 3) AddItem
 * screen : collection gallery
 * 
 * ref:
 * http://stackoverflow.com/questions/13664155/dynamically-add-and-remove-view
 * -to-viewpager
 * 
 * http://androidtrainningcenter.blogspot.co.il/2012/10/viewpager-example-in-
 * android.html
 */
public class ImageGalleryHorizontalViewAdapter extends BaseAdapter {
	/**
	 * Declare class variables
	 */
	private Activity context;

	private OnViewPagerItemSelectionProcess onImageGalleryItemClickListener;

	/**
	 * Constructor definition
	 * 
	 * @param context
	 *            Activity context
	 * @param onImageGalleryItemClickListener
	 *            callback for the associated view pager screen/class
	 */
	public ImageGalleryHorizontalViewAdapter(Activity context,
			OnViewPagerItemSelectionProcess onImageGalleryItemClickListener) {
		this.context = context;
		this.onImageGalleryItemClickListener = onImageGalleryItemClickListener;
	}

	class ViewHolder {
		ImageView imageView;
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
	 * container.addView(v); } catch (Exception e) { e.printStackTrace();
	 * 
	 * } return v; }
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
		if (CollectItSharedDataModel.getInstance().getImageGalleryArrayList() != null) {
			return CollectItSharedDataModel.getInstance()
					.getImageGalleryArrayList().size();
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
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			rowView = LayoutInflater.from(context).inflate(
					R.layout.viewpager_item_image, null);

			// initiate variables
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) rowView
					.findViewById(R.id.viewpager_image_gallery_img);

			rowView.setTag(viewHolder);

			
			
		}
		try {
			final ViewHolder holder = (ViewHolder) rowView.getTag();

			if (CollectItSharedDataModel.getInstance()
					.getImageGalleryArrayList() != null
					&& CollectItSharedDataModel.getInstance()
							.getImageGalleryArrayList().size() > position) {
				if (CollectItSharedDataModel.getInstance()
						.getImageGalleryArrayList().get(position)
						.getBackgroundResource() > 0) {
					holder.imageView.setImageResource(CollectItSharedDataModel
							.getInstance().getImageGalleryArrayList()
							.get(position).getBackgroundResource());
				} else if (CollectItSharedDataModel.getInstance()
						.getImageGalleryArrayList().get(position)
						.getBackgroundBitmap() != null) {
					ImageUtils.getInstance(context).setImageBitmapToView(
							CollectItSharedDataModel.getInstance()
									.getImageGalleryArrayList().get(position)
									.getBackgroundBitmap(), holder.imageView,
							null, R.drawable.frame_locked, false, true);
				}

			}
			
			rowView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (onImageGalleryItemClickListener != null) {
						onImageGalleryItemClickListener.onItemSelected(v,
								position, ViewPagerIds.IMAGE_GALLERY_ID.name());
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowView;
	}

}
