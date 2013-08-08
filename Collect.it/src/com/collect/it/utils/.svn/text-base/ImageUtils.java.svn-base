package com.collect.it.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.collect.it.model.CollectItSharedDataModel;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.FakeBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.URLConnectionImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class ImageUtils {
	Context context = null;
	static DisplayImageOptions options = null;
	ImageSize minImageSize = null;
	ImageLoader imageLoader = null;

	private static ImageUtils instance;

	Display display;

	public static ImageUtils getInstance(Context context) {
		if (instance == null) {
			instance = new ImageUtils(context);
		}
		return instance;
	}

	private ImageUtils(Context context) {
		this.context = context;

		// Just load image
		options = new DisplayImageOptions.Builder().cacheOnDisc()
				.imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new FakeBitmapDisplayer())
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"/sdcard/Android/data/com.collect.it/cache");
		// Get singletone instance of ImageLoader
		imageLoader = ImageLoader.getInstance();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		// Create configuration for ImageLoader (all options are optional, use
		// only those you really want to customize)
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(outMetrics.widthPixels,
						outMetrics.heightPixels)
				// max width, max height
				.discCacheExtraOptions(outMetrics.widthPixels,
						outMetrics.heightPixels, CompressFormat.JPEG, 75)
				// // Can slow ImageLoader, use it carefully (Better don't use
				// it)
				.threadPoolSize(5).threadPriority(Thread.NORM_PRIORITY - 1)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new WeakMemoryCache())
				// You can pass your own memory cache implementation
				.discCache(new UnlimitedDiscCache(cacheDir))
				// You can pass your own disc cache implementation
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.imageDownloader(
						new URLConnectionImageDownloader(5 * 1000, 20 * 1000))
				// connectTimeout (5 s), readTimeout (20 s)
				.defaultDisplayImageOptions(options).enableLogging().build();
		// Initialize ImageLoader with created configuration. Do it once on
		// Application start.
		imageLoader.init(config);

	}

	public static DisplayImageOptions getOptions() {
		return options;
	}

	/**
	 * Functionality to set bitmap on image view
	 * 
	 * @param image
	 *            url
	 * @param image
	 *            view
	 * @param progress
	 *            bar
	 * @param default image resource
	 * @param true if aspect ratio required on this image else false
	 * @param true if default image displayed if image is not available through
	 *        url else false
	 */

	public void setImageUrlToView(String imageUrl, final ImageView imageView,
			final ProgressBar progressBar, final int defaultImage,
			final boolean isAspectReq, final boolean isDefaultImgReq) {

		// hide image view
		imageView.setVisibility(View.GONE);

		imageLoader.displayImage(imageUrl, imageView, options,
				new ImageLoadingListener() {

					public void onLoadingCancelled() {
						if (progressBar != null) {
							progressBar.setVisibility(View.GONE);
						}
						/*
						 * if (isDefaultImgReq && defaultImage > 0) {
						 * imageView.setImageResource(defaultImage); } else {
						 * imageView.setVisibility(View.GONE); }
						 */
					}

					public void onLoadingComplete(Bitmap bitmap) {
						try {
							if (progressBar != null) {
								progressBar.setVisibility(View.GONE);
							}
							if (bitmap != null) {
								imageView.setVisibility(View.VISIBLE);
								if (isAspectReq) {
									new SetAspectRatio(bitmap, imageView);
									bitmap = null;
								} else {

									/*
									 * if (isRounded) { bitmap =
									 * getRoundedCornerBitmap(bitmap); }
									 */
									imageView.setImageBitmap(bitmap);
									bitmap = null;
								}
							} else if (isDefaultImgReq && defaultImage > 0) {
								imageView.setImageResource(defaultImage);
							} else {
								imageView.setVisibility(View.GONE);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					public void onLoadingFailed(FailReason arg0) {
						try {
							if (progressBar != null) {
								progressBar.setVisibility(View.GONE);
							}
							if (isDefaultImgReq && defaultImage > 0) {
								imageView.setImageResource(defaultImage);
							} else {
								imageView.setVisibility(View.GONE);
							}
						} catch (OutOfMemoryError e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					public void onLoadingStarted() {
						if (progressBar != null) {
							progressBar.setVisibility(View.VISIBLE);
						}
					}

				});
	}

	/**
	 * Functionality to set image with provided bitmap
	 * 
	 * @param bitmap
	 *            image bitmap that need to display on the image view
	 * @param imageView
	 *            image view
	 * @param progressBar
	 *            progress bar that will display when the image is loading
	 * @param defaultImage
	 *            default image resource if image is not available
	 * @param isAspectReq
	 *            true if aspect ratio required on this image else false
	 * @param isDefaultImgReq
	 *            true if default image displayed if image is not available
	 *            through url else false
	 */
	public void setImageBitmapToView(Bitmap bitmap, final ImageView imageView,
			final ProgressBar progressBar, final int defaultImage,
			final boolean isAspectReq, final boolean isDefaultImgReq) {

		// hide image view
		imageView.setVisibility(View.GONE);

		imageLoader.displayImageWithBitmap(bitmap, imageView, options,
				new ImageLoadingListener() {

					public void onLoadingCancelled() {
						if (progressBar != null) {
							progressBar.setVisibility(View.GONE);
						}
						/*
						 * if (isDefaultImgReq && defaultImage > 0) {
						 * imageView.setImageResource(defaultImage); } else {
						 * imageView.setVisibility(View.GONE); }
						 */
					}

					public void onLoadingComplete(Bitmap bitmap) {
						if (progressBar != null) {
							progressBar.setVisibility(View.GONE);
						}
						if (bitmap != null) {
							imageView.setVisibility(View.VISIBLE);
							if (isAspectReq) {
								new SetAspectRatio(bitmap, imageView);
								bitmap = null;
							} else {
								imageView.setImageBitmap(bitmap);
								bitmap = null;
							}
						} else if (isDefaultImgReq && defaultImage > 0) {
							imageView.setImageResource(defaultImage);
						} else {
							imageView.setVisibility(View.GONE);
						}
					}

					public void onLoadingFailed(FailReason arg0) {
						try {
							if (progressBar != null) {
								progressBar.setVisibility(View.GONE);
							}
							if (isDefaultImgReq && defaultImage > 0) {
								imageView.setImageResource(defaultImage);
							} else {
								imageView.setVisibility(View.GONE);
							}
						} catch (OutOfMemoryError e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					public void onLoadingStarted() {
						if (progressBar != null) {
							progressBar.setVisibility(View.VISIBLE);
						}
					}

				});
	}

	private Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(0xFFFFFFFF);
		canvas.drawRoundRect(rectF, 12, 12, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * Functionality to get byte array of image from image path
	 * 
	 * @param image
	 *            path
	 * @param image
	 *            height
	 * @param image
	 *            width
	 * 
	 * @return byte array
	 */
	public static byte[] getByteArray(String path) {

		byte[] b = null;

		try {
			Bitmap bm = decodeScaledBitmapFromSdCard(
					path,
					CollectItSharedDataModel.getInstance().getDisplayMetrics().widthPixels,
					CollectItSharedDataModel.getInstance().getDisplayMetrics().heightPixels);// BitmapFactory.decodeFile(path);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.JPEG, 75, baos); // bm is the
																// bitmap object
			b = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return b;
	}

	/**
	 * Functionality to get byte array of image from image bitmap
	 * 
	 * @param image
	 *            bitmap
	 * 
	 * @return byte array
	 */
	public static byte[] getByteArrayFromBitmap(Bitmap bitmap) {

		byte[] b = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos); // bitmap is
																	// the
																	// bitmap
																	// object
			b = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return b;
	}

	public static Bitmap decodeScaledBitmapFromSdCard(String filePath,
			int reqWidth, int reqHeight) {
		Bitmap bitmap = null;
		try {
			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;

			bitmap = BitmapFactory.decodeFile(filePath, options);

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			bitmap = null;
			bitmap = BitmapFactory.decodeFile(filePath, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * Functionality to get image bitmap for the camera gallery for the latest
	 * image It creates uri for the latest captured image then create bitmap
	 * 
	 * @param context
	 *            fragment activity context
	 * @param width
	 *            required width of image
	 * @param height
	 *            required height of image
	 * @param capturedImageUri
	 *            uri for the image
	 */
	public static Bitmap getBitmapFromDeviceImage(FragmentActivity context,
			int width, int height, Uri capturedImageUri) {
		Bitmap mBitmap = null;
		String orientation = "0";
		try {
			if (capturedImageUri == null) {
				final ContentResolver cr = context.getContentResolver();
				final String[] p1 = new String[] {
						MediaStore.Images.ImageColumns._ID,
						MediaStore.Images.ImageColumns.DATE_TAKEN };

				Cursor c1 = cr.query(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, p1, null,
						null, p1[1] + " DESC");
				if (c1 != null && c1.moveToFirst()) {
					String uristringpic = "content://media/external/images/media/"
							+ c1.getInt(0);
					capturedImageUri = Uri.parse(uristringpic);
					Log.i("TAG", "newuri   " + capturedImageUri);
					c1.close();
				}

			}

			String[] filePathColumn = { MediaStore.Images.Media.DATA,
					MediaStore.Images.Media.ORIENTATION };

			Cursor cursor = context.getContentResolver().query(
					capturedImageUri, filePathColumn, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {

				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);

				int columnIndex1 = cursor.getColumnIndex(filePathColumn[1]);
				orientation = cursor.getString(columnIndex1);

				cursor.close();

				mBitmap = ImageUtils.decodeScaledBitmapFromSdCard(picturePath,
						width, height);
				mBitmap = ImageUtils.rotateBitmap(mBitmap, orientation);
			} else {
				mBitmap = ImageUtils.decodeScaledBitmapFromSdCard(
						capturedImageUri.getPath(), width, height);
				mBitmap = ImageUtils.rotateBitmap(
						mBitmap,
						""
								+ ImageUtils
										.getImageOrientation(capturedImageUri
												.getPath()));
			}

			/** save image on sd card */
			// saveImageToSdCard(capturedImageUri);

		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmap;
	}

	/**
	 * Functionality to save image on to sd card
	 * 
	 * @param imageUri
	 * */
	static void saveImageToSdCard(Uri imageUri) {
		try {
			File dir = new File(Environment.getExternalStorageDirectory()
					+ "/Collect.it");

			if (!dir.exists()) {
				dir.mkdirs();
			}

			File file = new File(dir, "Collect.it_"
					+ System.currentTimeMillis() + ".jpeg");

			FileUtils.copyFile(new File(imageUri.toString()), file);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Bitmap decodeScaledBitmapFromUrl(FragmentActivity context,
			String url, int width, int height) {
		Bitmap bitmap = null;
		try {
			InputStream in = new java.net.URL(url).openStream();
			bitmap = decodeSampledBitmapFromResourceMemOpt(context, in, width,
					height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static Bitmap decodeSampledBitmapFromResourceMemOpt(
			FragmentActivity context, InputStream inputStream, int reqWidth,
			int reqHeight) {

		byte[] byteArr = new byte[0];
		byte[] buffer = new byte[1024];
		int len;
		int count = 0;

		try {
			while ((len = inputStream.read(buffer)) > -1) {
				if (len != 0) {
					if (count + len > byteArr.length) {
						byte[] newbuf = new byte[(count + len) * 2];
						System.arraycopy(byteArr, 0, newbuf, 0, count);
						byteArr = newbuf;
					}

					System.arraycopy(buffer, 0, byteArr, count, len);
					count += len;
				}
			}

			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(byteArr, 0, count, options);

			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;

			int[] pids = { android.os.Process.myPid() };
			ActivityManager mAM = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			android.os.Debug.MemoryInfo myMemInfo = mAM
					.getProcessMemoryInfo(pids)[0];
			return BitmapFactory.decodeByteArray(byteArr, 0, count, options);

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * Functionality to get base 64 of byte array
	 * 
	 * @param byte array
	 * 
	 * @return base 64 string
	 */
	public static String getBase64String(byte[] byteArray) {
		return Base64.encodeToString(byteArray, Base64.DEFAULT);
	}

	/**
	 * Functionality to get image bitmap from the url
	 * 
	 * @param imageUrl
	 *            url path of the image
	 * 
	 * @return bitmap
	 */
	public static Bitmap getBitmapFromURL(String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Functionality to set aspect ratio for the image
	 * */
	public Bitmap resizeBitmap(Bitmap bmp, int newWidth, int newHeight) {
		try {
			if (bmp.getHeight() > newHeight || bmp.getWidth() > newWidth) {
				int originalWidth = bmp.getWidth();
				int originalHeight = bmp.getHeight();
				/*
				 * Log.i(TAG, "originalWidth = " + originalWidth +
				 * "\noriginalHeight = " + originalHeight);
				 */
				float inSampleSize;
				if (originalWidth > originalHeight) {
					inSampleSize = (float) newWidth / originalWidth;
				} else {
					inSampleSize = (float) newHeight / originalHeight;
				}
				newWidth = Math.round(originalWidth * inSampleSize);
				newHeight = Math.round(originalHeight * inSampleSize);
				/*
				 * Log.i(TAG, "newWidth = " + newWidth + "\nnewHeight = " +
				 * newHeight);
				 */
				return Bitmap
						.createScaledBitmap(bmp, newWidth, newHeight, true);
			} else {
				return bmp;

			}
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			return bmp;
		} catch (Exception e) {
			e.printStackTrace();
			return bmp;
		}

	}

	/*
	 * private static final int REQ_WIDTH = 450; private static final int
	 * REQ_HEIGHT = 450;
	 */

	/**
	 * Resize, crop, rotate and Inserts the picture on the layout.
	 * 
	 * @param mImageView
	 *            to insert the bitmap.
	 * @param imageURI
	 *            from wich to obtain the bitmap.
	 * @return
	 * 
	 */
	public static Bitmap setPic(String imageURI, FragmentActivity context,
			int REQ_HEIGHT, int REQ_WIDTH) throws OutOfMemoryError {
		float rotation = 0;
		Bitmap bitmap = null;
		BitmapFactory.Options options = null;
		try {
			// Get the original bitmap dimensions
			options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(imageURI, options);

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, REQ_HEIGHT,
					REQ_WIDTH);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			options.inPurgeable = true; // for effeciency
			options.inInputShareable = true;

			// need rotation?
			bitmap = BitmapFactory.decodeFile(imageURI, options);
			rotation = rotationForImage(context,
					Uri.fromFile(new File(imageURI)));
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (rotation != 0) {
			// rotate
			Matrix matrix = null;
			Bitmap bitdrawable = null;
			try {
				matrix = new Matrix();
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				int newWidth = 700;
				int newHeight = 700;
				float scaleWidth = ((float) newWidth) / width;

				float scaleHeight = ((float) newHeight) / height;
				matrix.postScale(scaleWidth, scaleHeight);
				matrix.preRotate(rotation);
				bitdrawable = Bitmap.createBitmap(
						BitmapFactory.decodeFile(imageURI, options), 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				return bitdrawable;
			} catch (OutOfMemoryError e) {
				bitdrawable.recycle();
				bitdrawable = null;
				System.gc();
			} catch (Exception e) {
				bitdrawable.recycle();
				bitdrawable = null;
				System.gc();
			} finally {
				bitmap = null;
			}
			bitdrawable = Bitmap.createBitmap(
					BitmapFactory.decodeFile(imageURI, options), 0, 0, 200,
					200, matrix, true);
			return bitdrawable;

		} else {
			// use the original
			if (bitmap != null) {
				return bitmap;
			} else {
				return BitmapFactory.decodeFile(imageURI, options);
			}
		}

	}

	public static float rotationForImage(Context context, Uri uri) {
		try {
			if (uri.getScheme().equals("content")) {
				String[] projection = { Images.ImageColumns.ORIENTATION };
				Cursor c = context.getContentResolver().query(uri, projection,
						null, null, null);
				if (c.moveToFirst()) {
					return c.getInt(0);
				}
			} else if (uri.getScheme().equals("file")) {
				ExifInterface exif = new ExifInterface(uri.getPath());
				int rotation = (int) exifOrientationToDegrees(exif
						.getAttributeInt(ExifInterface.TAG_ORIENTATION,
								ExifInterface.ORIENTATION_NORMAL));
				return rotation;
			}
			return 0;

		} catch (IOException e) {
			Log.e("IMAge UTILS", "Error checking exif", e);
			return 0;
		}
	}

	private static float exifOrientationToDegrees(int exifOrientation) {
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
			return 90;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
			return 180;
		} else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
			return 270;
		}
		return 0;
	}

	/**
	 * Functionality to rotate bitmap
	 * 
	 * @param bitmap
	 *            image bitmap that need to change to its original state
	 * @param orientation
	 *            image's bitmap orientation
	 * 
	 * @return rotated bitmap
	 * */
	public static Bitmap rotateBitmap(Bitmap bitmap, String degrees) {
		Bitmap rotatedBitmap = null;
		try {
			Matrix mat = new Matrix();
			mat.postRotate(Float.parseFloat(degrees));
			if (bitmap != null) {
				bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),
						bitmap.getHeight(), false);
			}
			rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
					bitmap.getWidth(), bitmap.getHeight(), mat, true);
			bitmap = null;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			rotatedBitmap = bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			rotatedBitmap = bitmap;
		}
		return rotatedBitmap;
	}

	private Bitmap decodeFile(File f) {

		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 70;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE
					&& o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	/**
	 * Functionality to get orientation of image from uri or image path
	 * 
	 * @param imagePath
	 *            external path of image
	 * 
	 * @return orientation of image
	 */
	public static int getImageOrientation(String imagePath) {
		ExifInterface exif;
		int orientation = 0;
		try {
			exif = new ExifInterface(imagePath);
			orientation = exif
					.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_270:
				orientation = 270;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				orientation = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				orientation = 90;
				break;
			default:
				orientation = 0;
				break;
			}

		} catch (IOException e) {
			e.printStackTrace();
			orientation = 0;
		}
		return orientation;
	}

	/**
	 * Functionality to get resized bitmap from bitmap
	 * 
	 * @param bm
	 *            bitmap
	 * @param newHeight
	 *            required height
	 * @param newWidth
	 *            required width for image
	 * 
	 * @return new resized bitmap
	 * 
	 * */
	public static Bitmap getResizedBitmapFromBitmap(Bitmap bm, int newHeight,
			int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		// float scaleWidth = ((float) newWidth) / width;
		// float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(width, height);

		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);
		return resizedBitmap;
	}

}
