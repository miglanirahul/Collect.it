package com.collect.it.fragments;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.collect.it.R;
import com.collect.it.application.CollectItAbstractFragment;
import com.collect.it.model.CollectItSharedDataModel;

/**
 * This fragment class is used to display the comment list for the particular
 * item
 */
public class CommentFragment extends CollectItAbstractFragment {
	/**
	 * Declare class variables
	 */
	private FragmentActivity context;

	private ListView commentListView;

	@Override
	public View initialization(LayoutInflater inflater) {

		context = CollectItSharedDataModel.getInstance()
				.getCurrentFragmentActivityContext();

		View view = inflater.inflate(R.layout.frag_comment, null);

		commentListView = (ListView) view.findViewById(R.id.comment_list);
		commentListView.setAdapter(new CommentListAdapter());

		return view;
	}

	/**
	 * adapter class to display comments on the screen
	 */
	class CommentListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
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
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.frag_comment_list_row, null);
			return view;
		}
	}
}
