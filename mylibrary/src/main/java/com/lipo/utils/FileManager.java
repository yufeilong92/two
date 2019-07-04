package com.lipo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

	
	
	/**
	 * 整体的缓存地址
	 * 
	 * @return
	 */
	public static String getCasheAddr() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = Environment
					.getExternalStoragePublicDirectory("sinoDream");
			return file.getPath();
		} else {
			return null;
		}
	}

	/**
	 * 下载的地址
	 * 
	 * @return
	 */
	public static String getDownloadAddr() {
		String casheAddr = getCasheAddr();
		if (casheAddr != null) {
			String downloadAddr = casheAddr + File.separator + "download";
			File file = new File(downloadAddr);
			if (!file.exists()) {
				file.mkdirs();
			}
			return file.getPath();
		} else {
			return null;
		}
	}

	/**
	 * 录音的地址
	 * 
	 * @return
	 */
	public static String getRecordAddr() {
		String downloadAddr = getDownloadAddr();
		if (downloadAddr != null) {
			String recordAddr = downloadAddr + File.separator + "record";
			File file = new File(recordAddr);
			if (!file.exists()) {
				file.mkdirs();
			}
			return file.getPath();
		} else {
			return null;
		}
	}

	/**
	 * 录音的地址
	 * 
	 * @return
	 */
	public static String getImageAddr() {
		String downloadAddr = getDownloadAddr();
		if (downloadAddr != null) {
			String recordAddr = downloadAddr + File.separator + "image";
			File file = new File(recordAddr);
			if (!file.exists()) {
				file.mkdirs();
			}
			return file.getPath();
		} else {
			return null;
		}
	}

	public static File getImageAddrFile() {
		String downloadAddr = getDownloadAddr();
		if (downloadAddr != null) {
			String recordAddr = downloadAddr + File.separator + "image";
			File file = new File(recordAddr);
			if (!file.exists()) {
				file.mkdirs();
			}
			return file;
		} else {
			return null;
		}
	}

	/**
	 * 闹钟DIY被录制的地址
	 * 
	 * @return
	 */
	public static String getAlarmRecordAddr() {
		String oldAddr = getRecordAddr();
		if (oldAddr != null) {
			String addr = oldAddr + File.separator + "alarmDIYRecord";
			File file = new File(addr);
			if (!file.exists()) {
				file.mkdirs();
			}
			return file.getPath();
		} else {
			return null;
		}
	}

	/**
	 * 闹钟DIY被保存的地址
	 * 
	 * @return
	 */
	public static String getAlarmSaveAddr() {
		String oldAddr = getRecordAddr();
		if (oldAddr != null) {
			String addr = oldAddr + File.separator + "alarmDIYSave";
			File file = new File(addr);
			if (!file.exists()) {
				file.mkdirs();
			}
			return file.getPath();
		} else {
			return null;
		}
	}

	/**
	 * 天气DIY被录制的地址
	 * 
	 * @return
	 */
	public static String getWeatherRecordAddr() {
		String oldAddr = getRecordAddr();
		if (oldAddr != null) {
			String addr = oldAddr + File.separator + "weatherDIYRecord";
			File file = new File(addr);
			if (!file.exists()) {
				file.mkdirs();
			}
			return file.getPath();
		} else {
			return null;
		}
	}

	/**
	 * 天气DIY被保存的地址
	 * 
	 * @return
	 */
	public static String getWeatherSaveAddr() {
		String oldAddr = getRecordAddr();
		if (oldAddr != null) {
			String addr = oldAddr + File.separator + "weatherDIYSave";
			File file = new File(addr);
			if (!file.exists()) {
				file.mkdirs();
			}
			return file.getPath();
		} else {
			return null;
		}
	}

	/**
	 * 删除文件夹及下面的所有文件 文件夹下没有子目录
	 * 
	 * @param path
	 */
	public static void deleteAll(String path) {
		File file = new File(path);
		File[] listFile = file.listFiles();
		if (listFile.length > 0) {
			for (File lfile : listFile) {
				lfile.delete();
			}
		}
		file.delete();
	}

	/**
	 * 删除文件夹下面的所有文件(不包括该文件夹) 文件夹下没有子目录
	 * 
	 * @param path
	 */
	public static void deleteAllFlie(String path) {
		File file = new File(path);
		File[] listFile = file.listFiles();
		if (listFile.length > 0) {
			for (File lfile : listFile) {
				lfile.delete();
			}
		}
	}

	/**
	 * 文件写入
	 * 
	 * @param path
	 * @param content
	 * @throws IOException
	 */
	public static void writeFile(String path, String content)
			throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		fw.write(content);
		fw.flush();
		fw.close();
	}

	/**
	 * 读取文件
	 * 
	 * @param path
	 *            (文件地址)
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory()) {
			throw new FileNotFoundException();
		}
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuffer sb = new StringBuffer();
		String temp = null;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();

		return sb.toString();
	}

	/**
	 * 获取到数据缓存的地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getDataCache(Context context) {
		File file = context.getExternalCacheDir();
		File file2 = new File(file, "data");
		if (!file2.exists()) {
			file2.mkdir();
		}
		return file.getPath();
	}

	public static File getCacheImage(Context context) {
		File file = new File(getDataCache(context), "image");
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	/**
	 * 图片上传
	 * 
	 * @param bitmap对象
	 * @param temp
	 *            判断是什么类型的图片，1是png类型的，0是jpeg类型的
	 * @return
	 */
	public static String getBase64Data(Bitmap bitmap, int temp) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (temp == 1) {
			bitmap.compress(CompressFormat.PNG, 100, bos);
		} else {
			bitmap.compress(CompressFormat.JPEG, 100, bos);
		}

		try {
			bos.flush();
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] bytes = bos.toByteArray();

		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	public static void cacheBitmap(Context context, Bitmap bitmap, String name) {
		File file2 = new File(getCacheImage(context), name);
		try {
			file2.createNewFile();
			FileOutputStream fos = new FileOutputStream(file2);
			bitmap.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}
}
