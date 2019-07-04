package com.lipo.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.FileProvider
import com.lipo.views.ToastView
import java.io.File

object ShareUtils {

    /**
     * 分享文字
     */
    fun shareText(mContext: Context, text: String) {
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        mContext.startActivity(Intent.createChooser(intent, "分享到"))
    }

    /**
     * 分享多张图片
     */
    fun shareImage(mContext: Context, imagePath: String) {
        var imageUri = Uri.fromFile(File(imagePath))
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, imageUri)
        }
        mContext.startActivity(Intent.createChooser(intent, "分享到"))
    }

    /**
     * 分享多张图片
     */
    fun shareMultableImage(mContext: Context, imagePaths: MutableList<String>) {
        val arrayList = ArrayList<Uri>()
        for (path in imagePaths) {
            arrayList.add(Uri.fromFile(File(path)))
        }
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, arrayList)
        }
        mContext.startActivity(Intent.createChooser(intent, "分享到"))
    }

    /**
     * 分享文件
     */
    fun shareFile(mContext: Context, file: File) {
        if(!file.exists()){
            ToastView.setToasd(mContext,"找不到该文件")
            return
        }
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_SEND
            type = "*/*"
            putExtra(Intent.EXTRA_STREAM, getUriFromFile(mContext,file))
        }
        mContext.startActivity(Intent.createChooser(intent, "分享到"))
    }

    private fun getUriFromFile(mContext: Context,file: File):Uri{
        return  FileProvider.getUriForFile(mContext, "com.zzzh.akhalteke_shipper.FileProvider", file)
    }

}