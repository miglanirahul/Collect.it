package com.collect.it.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SetAspectRatio {

	public SetAspectRatio(Bitmap img, ImageView myView) {
		try {
			int height, width, w;
			int layoutHeight = myView.getHeight();// myView.getDrawable().getIntrinsicHeight();
			int layoutWidth = myView.getWidth();// getDrawable().getIntrinsicWidth();

			if (layoutHeight < 1) {
				layoutHeight = myView.getDrawable().getIntrinsicHeight();
			}
			if (layoutWidth < 1) {
				layoutWidth = myView.getDrawable().getIntrinsicWidth();
			}

			if (img != null) {
				height = img.getHeight() > layoutHeight ? layoutHeight : img
						.getHeight();
				int ratio = ((img.getHeight() - height) * 100 / img.getHeight());
				int wi = (img.getWidth() * ratio) / 100;
				width = img.getWidth() - wi;
				w = width;
				int width1 = width > layoutWidth ? layoutWidth : width;
				if (width > 0) {
					ratio = ((width - width1) * 100 / width);
					int hi = (height * ratio) / 100;
					height = height - hi;
					w = width1;
				}

				ViewGroup.LayoutParams params = myView.getLayoutParams();
				params.height = height;
				params.width = w;
				myView.setLayoutParams(params);

				myView.setImageBitmap(img);
				myView.setMaxHeight(img.getHeight());
				img = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static float convertPixelsToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;

	}

	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}
}
