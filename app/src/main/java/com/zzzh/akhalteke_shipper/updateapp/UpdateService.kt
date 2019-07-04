package com.zzzh.akhalteke_shipper.updateapp

import android.app.DownloadManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Environment
import android.os.Handler
import android.os.IBinder
import com.allenliu.versionchecklib.utils.AppUtils
import java.io.File

/**
 * 下载service
 */
class UpdateService:Service(){

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    //下载id值
    private var downloadId = 0L
    //apk包名
    private val apkName by lazy {
        "shipper.apk"
    }

    //下载管理器
    private lateinit var mDownloadManager: DownloadManager

    private val handler = Handler()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        downloadId = intent!!.getLongExtra("download_id",0L)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        mDownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        handler.post(task)
    }

    /**
     * 根据下载id查看下载情况
     * @param downloadId Long
     * @return Array<Long>
     */
    private fun getBytesAndStatus(downloadId: Long): Array<Long> {
        val bytesAndStatus = arrayOf(-1L, -1L, 0L)

        val query = DownloadManager.Query().setFilterById(downloadId)
        var c: Cursor? = null
        try {
            c = mDownloadManager.query(query)
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getLong(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                bytesAndStatus[1] = c.getLong(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                bytesAndStatus[2] = c.getLong(c.getColumnIndex(DownloadManager.COLUMN_STATUS))
            }
        } catch (e: java.lang.Exception) {

        } finally {
            if (c != null) {
                c.close()
            }
        }
        return bytesAndStatus
    }

    /**
     * 更新页面
     */
    private fun updateView() {
        val bytesAndStatus = getBytesAndStatus(downloadId = downloadId)
        val currentSize = bytesAndStatus[0]//当前大小
        val totalSize = bytesAndStatus[1]//总大小
        val status = bytesAndStatus[2]//下载状态

        when (status.toInt()) {
            DownloadManager.STATUS_FAILED -> {
                UpdateDialog.downloadId = -1
            }
            DownloadManager.STATUS_PAUSED -> {
            }
            DownloadManager.STATUS_PENDING -> {
            }
            DownloadManager.STATUS_RUNNING -> {
            }
            DownloadManager.STATUS_SUCCESSFUL -> {
                UpdateDialog.downloadId = -1
                handler.removeCallbacks(task)
                installApkO()
                stopSelf()
//                mDownloadManager.remove(downloadId)
            }
        }
    }

    /**
     * 心跳包
     */
    private val task = object : Runnable {
        override fun run() {
            updateView()
            handler.postDelayed(this, 50)
        }
    }

    /**
     * 兼容8.0安装位置来源的权限
     */
    private fun installApkO() {
        AppUtils.installApk(
            this,
            File(Environment.getExternalStoragePublicDirectory("zzzh"), apkName)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(task)
    }



}