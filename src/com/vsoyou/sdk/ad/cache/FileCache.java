package com.vsoyou.sdk.ad.cache;

import java.io.File;

import android.content.Context;

import com.vsoyou.sdk.ad.cache.util.CommonUtil;
import com.vsoyou.sdk.ad.cache.util.FileHelper;
import com.vsoyou.sdk.util.LogUtil;

/**
 * 获取文件缓存
 * 
 * @author lmy 2013-6-22
 */
public class FileCache {

	public static final String TAG = "FileCache";

	private String dirString; // 缓存目标文件夹

	public FileCache(Context context) {
		dirString = getCacheDir();
		boolean result = FileHelper.createDirectory(dirString);
		LogUtil.i(TAG, "FileCache.createDirectory:" + dirString
				+ ", result = " + result);
	}
	
	/**
	 * 获取缓存文件夹
	 * 
	 * @param cacheType
	 * @return
	 */
	public String getCacheDir() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "vsy/";
		}
		return "";
	}

	/**
	 * 获取缓存文件
	 * @param fileName 缓存文件名
	 * @return
	 */
	public File getFile(String fileName) {
		String filePath = "";
		if (CommonUtil.hasSDCard()) {
			boolean result = FileHelper.createDirectory(dirString);
			if (result) {
				filePath = dirString + fileName.hashCode();
			}
		}
		File f = new File(filePath);
		return f;
	}
	
	/**
	 * 清除缓存
	 */
	public void clear() {
		FileHelper.deleteDirectory(dirString);
	}

}
