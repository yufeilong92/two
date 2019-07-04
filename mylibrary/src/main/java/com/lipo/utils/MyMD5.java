package com.lipo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MyMD5 {

	public static String getMD5(String str) throws NoSuchAlgorithmException {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

//	public static String getMyCode(String url) {
//
//		MyApplication appliction = MyApplication.getInstance();
//		String ak_key = MyApplication.getInstance().ak_key;
//		String api_key = "8xYsOGQW5r";
//		long timestamp = System.currentTimeMillis() / 1000;
//		String uniquely_code = appliction.getPhoneInfo();
//		String api_secret = "nrC7M8lXecPq2KzeCOy1MFycyc9GdJQj";
//		String locale = "zh-CN";
//		String api_sig = null;
//		try {
//			api_sig = ak_key + uniquely_code + api_key + api_secret;
//			api_sig = getMD5(api_sig);
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
////		String urlMD5 = url + "&uniquely_code=" + uniquely_code + "&api_key=8xYsOGQW5r&timestamp=" + timestamp
////			+ "&api_sig=" + api_sig + "&output=json&limit=&locale=zh-CN&ak=" + api_sig;
////		
////		String urlMD5 = url
////				+ "&ak=" + api_sig;
////		
//		return urlMD5;
//	}
//
//	fun getMD5String(s: String): String? {
//		val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
//		try {
//			val btInput = s.toByteArray()
//			//获得MD5摘要算法的 MessageDigest 对象
//			val mdInst = MessageDigest.getInstance("MD5")
//			//使用指定的字节更新摘要
//			mdInst.update(btInput)
//			//获得密文
//			val md = mdInst.digest()
//			//把密文转换成十六进制的字符串形式
//			val j = md.size
//			val str = CharArray(j * 2)
//			var k = 0
//			for (i in 0 until j) {
//				val byte0 = md[i]
//				str[k++] = hexDigits[byte0.toInt().ushr(4) and 0xf]
//				str[k++] = hexDigits[byte0.toInt() and 0xf]
//			}
//			return String(str)
//		} catch (e: Exception) {
//			e.printStackTrace()
//			return null
//		}
//	}

	public final static String getMD5String(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4',
				'5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			//获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			//使用指定的字节更新摘要
			mdInst.update(btInput);
			//获得密文
			byte[] md = mdInst.digest();
			//把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
