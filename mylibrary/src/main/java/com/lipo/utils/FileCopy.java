package com.lipo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopy {

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		int byteread = 0;
		File oldFile = new File(oldPath);
		if (oldFile.exists()) {
			try {
				InputStream is = new FileInputStream(oldPath);
				OutputStream os = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];

				while ((byteread = is.read(buffer)) != -1) {
					os.write(buffer, 0, byteread);
				}

				os.flush();
				is.close();
				os.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		File oldFile = new File(oldPath);
		if (!oldFile.exists()) {
			return;
		}

		String[] files = oldFile.list();
		if (files == null) {
			return;
		}

		int length = files.length;
		if (length == 0) {
			return;
		}

		File newFile = new File(newPath);
		if (!newFile.exists()) {
			newFile.mkdir();
		}

		if (!oldPath.endsWith(File.separator)) {
			oldPath += File.separator;
		}

		if (!newPath.endsWith(File.separator)) {
			newPath += File.separator;
		}

		for (String file : files) {
			File tempFile = new File(oldPath + file);
			if (tempFile.isFile()) {
				try {
					InputStream is = new FileInputStream(oldPath + file);
					OutputStream os = new FileOutputStream(newPath + file);
					int byteread = 0;
					byte[] buffer = new byte[1024];
					while ((byteread = is.read(buffer)) != -1) {
						os.write(buffer, 0, byteread);
					}

					os.flush();
					is.close();
					os.close();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (tempFile.isDirectory()) {
				copyFolder(oldPath + file, newPath + file);
			}
		}

	}

}
