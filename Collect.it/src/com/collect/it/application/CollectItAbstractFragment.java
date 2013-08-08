package com.collect.it.application;

import com.collect.it.interfaces.OnWebServiceProcess;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * This will be the base fragment class for all the fragment classes within the
 * application that will have common functionality define below :
 * 
 * initialize variables in onCreateView()
 * 
 * @implements 1) onClick listener for views, 2) onWebServiceProcess for server
 *             hit/response
 * 
 */
public abstract class CollectItAbstractFragment extends
		android.support.v4.app.Fragment implements
// click event listener
		OnClickListener,
		// web service call listener
		OnWebServiceProcess {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// here user can initiate variables and components
		View view = initialization(inflater);

		return view;

	}

	@Override
	public void getServerValues(String response, int id, boolean isOk,
			String exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServerError(int id, String msg) {
		// TODO Auto-generated method stub

	}

	/**
	 * Click events for activity
	 */
	@Override
	public void onClick(View v) {
		
	}

	/**
	 * This function is used to initialize components and variables that is used
	 * in onCreate() in the associated activity
	 * 
	 * @param Layout
	 *            inflater that used to inflate layout for this class to display
	 *            on the screen
	 * 
	 * @return inflated view so that it can be attached for onCreateView()
	 *         return type
	 */
	public abstract View initialization(LayoutInflater inflater);

}
