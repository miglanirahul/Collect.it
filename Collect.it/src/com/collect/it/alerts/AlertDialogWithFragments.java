package com.collect.it.alerts;

import com.collect.it.interfaces.OnAlertDialogFragmentListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/*
 * This class is used for creating Custom Alert Dialog using Fragments. You can create custom Alert Dialog and use it on fragments. 
 */
public class AlertDialogWithFragments extends DialogFragment {

	private AlertDialog alertDialog;
	private String message, title;
	private static AlertDialogWithFragments alertObj;
	private int iconResId, button1, button2, fragmentID;
	private AlertType alertType;
	private OnAlertDialogFragmentListener alertDialogListener;

	/*
	 * Use enum to select type of Dialog when using getAlertDialog Method
	 */

	public static enum AlertType {
		SINGLEOPTION, DOUBLEOPTION, CUSTOM
	};

	/***
	 * Static method to get instance of Dialog Fragment class and to set
	 * arguments as passed in parameters.Pass the arguments as per required in
	 * the dialog else you can pass default values for example null for string
	 * parameter and 0 for integer.
	 * 
	 * @param title
	 *            for the dialog
	 * @param msg
	 *            message to show on dialog
	 * @param button1
	 *            name of first button as declared in String.xml
	 * @param button2
	 *            name of second button as declared in String.xml
	 * @param iconResId
	 *            drawable id to show icon on alert dialog
	 * @param alertType
	 *            type of alert ( Selected from
	 *            AlertDialogWithFragments.AlertType
	 * @return
	 */
	/*
	 * public static AlertDialogWithFragments getAlertDialog() { if (alertObj ==
	 * null) { alertObj = new AlertDialogWithFragments(); } return alertObj; }
	 */

	public void setDialogArguments(String title, String msg, int button1,
			int button2, int iconResId, AlertType alertType,
			OnAlertDialogFragmentListener listener, int fragmentID) {

		try {
			Bundle bundle = new Bundle();
			bundle.putString("message", msg);
			bundle.putString("title", title);
			bundle.putInt("button1", button1);
			bundle.putInt("button2", button2);
			bundle.putInt("iconresId", iconResId);
			bundle.putSerializable("alertType", alertType);
			bundle.putInt("fragmentID", fragmentID);
			this.setArguments(bundle);
			alertDialogListener = listener;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.DialogFragment#onDismiss(android.content.
	 * DialogInterface)
	 */
	@Override
	public void onDismiss(DialogInterface dialog) {
		// TODO Auto-generated method stub
		super.onDismiss(dialog);

	}

	/**
	 * This method is used to create dialog with arguments passed in the
	 * setDialogArgument method.
	 */

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			this.message = getArguments().getString("message");
			this.title = getArguments().getString("title");
			this.iconResId = getArguments().getInt("iconresId");
			this.button1 = getArguments().getInt("button1");
			this.button2 = getArguments().getInt("button2");
			this.alertType = (AlertType) getArguments().getSerializable(
					"alertType");
			this.fragmentID = getArguments().getInt("fragmentID");
			if (alertType == AlertType.SINGLEOPTION) {
				alertDialog = new AlertDialog.Builder(getActivity())
						.setIcon(iconResId)
						.setTitle(title)
						.setMessage(message)
						.setPositiveButton(getString(button1),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {

										if (alertDialogListener != null) {
											alertDialogListener
													.doPositiveClick();
										} else {
											alertDialogListener
													.interfaceAttachError(
															fragmentID,
															"Error in interface attach");
										}
										// dismiss();
									}
								}).create();
				return alertDialog;
			} else if (alertType == AlertType.DOUBLEOPTION) {
				return alertDialog = new AlertDialog.Builder(getActivity())
						.setIcon(iconResId)
						.setTitle(title)
						.setMessage(message)
						.setPositiveButton(getString(button1),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										if (alertDialogListener != null) {
											alertDialogListener
													.doPositiveClick();
										}
									}
								})
						.setNegativeButton(getString(button2),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										if (alertDialogListener != null) {
											alertDialogListener
													.doNegativeClick();
										}
									}
								}).create();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			alertDialog.show();
		}
		return null;
	}

}
