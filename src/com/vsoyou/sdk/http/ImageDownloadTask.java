package com.vsoyou.sdk.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.vsoyou.sdk.ad.cache.FileCache;
import com.vsoyou.sdk.ad.cache.util.CommonUtil;
import com.vsoyou.sdk.util.LogUtil;

/**
 * 异步下载图片
 */
public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
	private static final String TAG = "ImageDownloaderTask";

	private FileCache fileCache;
	private int errorCode;
	private String errorMessage;
	private HttpCallback<Bitmap> callBack;

	public ImageDownloadTask(Context context, HttpCallback<Bitmap> callBack) {
		fileCache = new FileCache(context);
		this.callBack = callBack;
	}

	@Override
	protected Bitmap doInBackground(String... strings) {
		if (strings.length == 0) {
			errorCode = -1;
			errorMessage = "参数错误！";
			return null;
		}
		String url = strings[0];
		return getBitmap(url);
	}

	@Override
	protected void onPostExecute(Bitmap response) {
		if (response != null) {
			callBack.onSuccess(response);
		} else {
			callBack.onFailure(errorCode, errorMessage);
		}

	}

	private Bitmap getBitmap(String url) {
		LogUtil.i(TAG, "getBitmap.url-->" + url);
		File f = null;
		Bitmap b = null;
		boolean sdExists = CommonUtil.hasSDCard();
		if (sdExists) {
			f = fileCache.getFile(url);
			// 先从文件缓存中查找是否有
			if (f != null && f.exists()) {
				b = decodeFile(f);
			}
			if (b != null) {
				return b;
			}
		}
		// 最后从指定的url中下载图片
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			if (!sdExists) {
				return BitmapFactory.decodeStream(is);
			}
			OutputStream os = new FileOutputStream(f);
			CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (Exception ex) {
			LogUtil.e(
					TAG,
					"getBitmap catch Exception...\nmessage = "
							+ ex.getMessage());
			errorCode = -1;
			errorMessage = "网络错误，网络不给力";
			return null;
		}
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
			Log.e("", "CopyStream catch Exception...");
		}
	}

	// decode这个图片并且按比例缩放以减少内存消耗，虚拟机对每张图片的缓存大小也是有限制的
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 300;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

}