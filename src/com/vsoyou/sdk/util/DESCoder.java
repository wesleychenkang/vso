package com.vsoyou.sdk.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.vsoyou.sdk.main.Constants;

import android.content.Context;
import android.util.Base64;

public class DESCoder {
	
	//编码
    private static String encoding = "UTF-8"; 
    
	public static String getPublicKey(){
		return new StringBuffer(Constants.PUBLIC_KEY).reverse().toString();
	}

    /**
     * 加密 【先sdkKey再appKey】
     * @param context
     * @param str
     * @return
     */
    public static String encryptoPubAndPri(Context context, String str){
    	String result = str;
    	result = ebotongEncrypto(ConfigUtil.getAppKey(context), ebotongEncrypto(getPublicKey(), str));
    	return result;
    }
    
    /**
     * 解密【先appKey再sdkKey】
     * @param context
     * @param str
     * @return
     */
    public static String decryptoPriAndPub(Context context, String str){
    	String result = str;
    	result = ebotongDecrypto(getPublicKey(), ebotongDecrypto(ConfigUtil.getAppKey(context), str));
    	return result;
    }
  
    /** 
     * 加密字符串
     */  
    public static String ebotongEncrypto(String mKey,String str) {  
        String result = str;  
        if (str != null && str.length() > 0) {  
            try {  
                byte[] encodeByte = symmetricEncrypto(mKey, str.getBytes(encoding));  
                result = Base64.encodeToString(encodeByte, 0); 
  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
  
    /** 
     * 解密字符串
     */  
    public static String ebotongDecrypto(String mKey, String str) {  
        String result = str;  
        if (str != null && str.length() > 0) {  
            try {  
                byte[] encodeByte = Base64.decode(str, 0); 
  
                byte[] decoder = symmetricDecrypto(mKey, encodeByte);  
                result = new String(decoder, encoding);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
    /** 
     * 对称加密字节数组并返回 
     * 
     * @param byteSource 需要加密的数据 
     * @return           经过加密的数据 
     * @throws Exception 
     */  
    public static byte[] symmetricEncrypto(String mKey, byte[] byteSource) throws Exception {  
        try {  
            int mode = Cipher.ENCRYPT_MODE;  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            byte[] keyData = mKey.getBytes();  
            DESKeySpec keySpec = new DESKeySpec(keyData);  
            Key key = keyFactory.generateSecret(keySpec);  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(mode, key);  
  
            byte[] result = cipher.doFinal(byteSource);  
            return result;  
        } catch (Exception e) {  
            throw e;  
        } finally {  
        }  
    }  
    
    /** 
     * 对称解密字节数组并返回 
     * 
     * @param byteSource 需要解密的数据 
     * @return           经过解密的数据 
     * @throws Exception 
     */  
    public static byte[] symmetricDecrypto(String mKey, byte[] byteSource) throws Exception {  
        try {  
            int mode = Cipher.DECRYPT_MODE;  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            byte[] keyData = mKey.getBytes();  
            DESKeySpec keySpec = new DESKeySpec(keyData);  
            Key key = keyFactory.generateSecret(keySpec);  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(mode, key);  
            byte[] result = cipher.doFinal(byteSource);  
            return result;  
        } catch (Exception e) {  
            throw e;  
        } finally {  
  
        }  
    }  
    

}
