package com.vsoyou.sdk.util;

import java.io.File;
import java.io.IOException;

import android.content.Context;

import com.vsoyou.sdk.ad.cache.util.CommonUtil;
import com.vsoyou.sdk.ad.cache.util.FileHelper;

public class NImsiUtil {
	
private static final String LOCAL_NIMSI = "local_nImsi";
	
	public static String getNImsiValue(Context context){
		LogUtil.i("nImsi", "getNImsiValue start");
		String nImsi = "";
		//从本地SharedPreferences中拿数据
		LocalStorage storage = LocalStorage.getInstance(context);
		nImsi = storage.getString(LOCAL_NIMSI, "");
		LogUtil.i("nImsi", "nImsi-->" + nImsi);
		if("".equals(nImsi)){
			//从SD卡上读取nImsi
			if(CommonUtil.hasSDCard()){
				LogUtil.i("nImsi", "have sd card");
				String filePath = CommonUtil.getRootFilePath() + "vs/value.key";
				if(FileHelper.fileIsExist(filePath)){//文件存在
					File file = new File(filePath);
					String str = FileHelper.readFileData(file);
					if(StringUtil.isEmpty(str)){
						nImsi = "" + System.currentTimeMillis();
						FileHelper.writeFile(filePath, nImsi);
					}else{
						nImsi = str;
					}
					storage.putString(LOCAL_NIMSI, nImsi);
				}else{ //文件不存在
					String dirpath = CommonUtil.getRootFilePath() + "vs";
					if(FileHelper.createDirectory(dirpath)){
						File keyFile = new File(filePath);
						keyFile.delete();
						try {
							keyFile.createNewFile();
							nImsi = "" + System.currentTimeMillis();
							FileHelper.writeFile(filePath, nImsi);
						} catch (IOException e) {
							nImsi = "" + System.currentTimeMillis();
							storage.putString(LOCAL_NIMSI, nImsi);
							e.printStackTrace();
						}
						
					}else{
						nImsi = "" + System.currentTimeMillis();
						storage.putString(LOCAL_NIMSI, nImsi);
					}
				}
			}else{
				LogUtil.i("nImsi", "no sd card");
				nImsi = "" + System.currentTimeMillis();
				storage.putString(LOCAL_NIMSI, nImsi);
			}
		}
		return nImsi;
	} 

}
