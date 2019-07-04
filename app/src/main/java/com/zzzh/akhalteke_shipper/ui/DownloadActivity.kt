package com.zzzh.akhalteke_shipper.ui

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.content.Intent
import android.content.ActivityNotFoundException
import android.net.Uri
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import com.yanzhenjie.permission.Permission
import com.zzzh.akhalteke_shipper.utils.PermissionUtils

/**
 * 下载帮助类
 */
open class DownloadActivity : SelectorImageActivity() {

    private lateinit var mDownloadManager: DownloadManager
    private val cpObserver by lazy {
        DownloadContentObserver()
    }

    private var downloadId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    }

    open fun downLoadSuccess(){

    }

    open fun downLoadFailed(){

    }

    open fun downLoadRunning(prec: Int){

    }

    fun toDownload(url: String, fileName: String) {
        PermissionUtils.showPermission(mContext, "下载需要读写权限", arrayOf(Permission.WRITE_EXTERNAL_STORAGE)) {
            if (downLoadMangerIsEnable(mContext)) {
                val req = DownloadManager.Request(Uri.parse(url))
                req.setDestinationInExternalPublicDir("zzzh", fileName)
                req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                downloadId = mDownloadManager.enqueue(req)
                contentResolver.registerContentObserver(
                    mDownloadManager.getUriForDownloadedFile(downloadId),
                    true,
                    cpObserver
                )
            } else {
                downloadToSet()
            }
        }
    }

    /**
     * 是否可以下载
     */
    private fun downLoadMangerIsEnable(context: Context): Boolean {
        val state = context.applicationContext.packageManager
            .getApplicationEnabledSetting("com.android.providers.downloads")

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                    state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED)
        } else {
            !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER)
        }
    }

    private fun downloadToSet() {
        try {
            //Open the specific App Info page:
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + "com.android.providers.downloads")
            startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()

            //Open the generic Apps page:
            val intent = Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
            startActivity(intent)
        }
    }


    private fun getBytesAndStatus(downloadId: Long): Array<Int> {
        val bytesAndStatus = arrayOf(-1, -1, 0)

        val query = DownloadManager.Query().setFilterById(downloadId)
        var c: Cursor? = null
        try {
            c = mDownloadManager.query(query)
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))
            }
        } catch (e: java.lang.Exception) {

        } finally {
            if (c != null) {
                c.close()
            }
        }
        return bytesAndStatus
    }

    private fun updateView() {
        val bytesAndStatus = getBytesAndStatus(downloadId = downloadId)
        val currentSize = bytesAndStatus[0]//当前大小
        val totalSize = bytesAndStatus[1]//总大小
        val status = bytesAndStatus[2]//下载状态

        when (status) {
            DownloadManager.STATUS_FAILED -> {
                downLoadFailed()
            }
            DownloadManager.STATUS_PAUSED -> {
            }
            DownloadManager.STATUS_PENDING -> {
            }
            DownloadManager.STATUS_RUNNING -> {
                downLoadRunning(currentSize * 100 / totalSize)
            }
            DownloadManager.STATUS_SUCCESSFUL -> {
                downLoadSuccess()
            }
        }
    }

    private val handler = Handler(Looper.getMainLooper())

    inner class DownloadContentObserver : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            updateView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(cpObserver)
    }

}