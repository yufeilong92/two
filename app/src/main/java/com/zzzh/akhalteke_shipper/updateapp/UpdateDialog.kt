package com.zzzh.akhalteke_shipper.updateapp

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.lipo.utils.DisplayUtil
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.bean.VersionInfo
import kotlinx.android.synthetic.main.dialog_update_app.*
import com.allenliu.versionchecklib.utils.AppUtils
import android.os.*
import com.yanzhenjie.permission.Permission
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.utils.PermissionUtils
import java.io.File

/**
 * 自动更新dialog
 */
class UpdateDialog(var mContext: Context, val info: VersionInfo, val onCancel: () -> Unit) :
    AlertDialog(mContext, R.style.mydialog) {

    //metrics
    private var metrics: DisplayMetrics = context.resources.displayMetrics

    //下载器
    private lateinit var mDownloadManager: DownloadManager
    private val cpObserver by lazy {
        DownloadContentObserver()
    }

    companion object {
        //下载id
        var downloadId: Long = -1L
    }

    //下载的apk名字
    private val apkName by lazy {
        "shipper.apk"
    }

    //handler初始化
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (info.type == "1") {
            setCanceledOnTouchOutside(false)
        }
        mDownloadManager = mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        setSizeMode()
        setContentView(R.layout.dialog_update_app)

        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        dialog_update_app_title.text = "是否升级到${info.version}版本？"
        dialog_update_app_mb.text = "新版本大小：${info.appSize}"
        dialog_update_app_content.text = info.updateLog

        if (info.type == "1") {
            dialog_update_app_ignore.visibility = View.GONE
            dialog_update_app_line.visibility = View.GONE
            dialog_update_app_close.visibility = View.GONE
        }

        dialog_update_app_close.setOnClickListener { dismiss() }
        dialog_update_app_ignore.setOnClickListener { dismiss() }
        dialog_update_app_ok.setOnClickListener {
            dialog_update_app_bar.visibility = View.VISIBLE
            dialog_update_app_ok.visibility = View.GONE
            dialog_update_app_ignore.visibility = View.GONE

            toDownload(info.path)
        }
    }

    /**
     * 设置窗口模式
     */
    private fun setSizeMode() {
        val params = window!!.attributes
        params.width = metrics.widthPixels - DisplayUtil.dip2px(mContext, 72f)
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        window!!.setGravity(Gravity.CENTER)
    }

    /**
     * 开始下载
     * @param url String
     */
    fun toDownload(url: String) {
        PermissionUtils.showPermission(mContext as Activity, "下载需要读写权限", arrayOf(Permission.WRITE_EXTERNAL_STORAGE)) {
        if (downLoadMangerIsEnable(mContext)) {
            val fileCa = File(Environment.getExternalStoragePublicDirectory("zzzh"), apkName)
            if (fileCa.exists()) {
                fileCa.delete()
            }
            val req = DownloadManager.Request(Uri.parse(url))
            req.setDestinationInExternalPublicDir("zzzh", apkName)
            req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            downloadId = mDownloadManager.enqueue(req)
            var reqUri = Uri.fromFile(File(Environment.getExternalStoragePublicDirectory("zzzh"), apkName))
//            mContext.contentResolver.registerContentObserver(
//                reqUri,
//                true,
//                cpObserver
//            )
            val intentSer = Intent()
            intentSer.setClass(mContext, UpdateService::class.java)
            intentSer.putExtra("download_id", downloadId)
            mContext.startService(intentSer)
            handler.post(task)
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

    /**
     * 允许下载权限
     */
    private fun downloadToSet() {
        try {
            //Open the specific App Info page:
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + "com.android.providers.downloads")
            mContext.startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()

            //Open the generic Apps page:
            val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
            mContext.startActivity(intent)
        }
    }

    /**
     * 根据downloadid 查看本次下载
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
     * 更新界面
     */
    private fun updateView() {
        val bytesAndStatus = getBytesAndStatus(downloadId = downloadId)
        val currentSize = bytesAndStatus[0]//当前大小
        val totalSize = bytesAndStatus[1]//总大小
        val status = bytesAndStatus[2]//下载状态

        when (status.toInt()) {
            DownloadManager.STATUS_FAILED -> {
                downloadId = -1
                ToastView.setToasd(mContext, "下载失败")
            }
            DownloadManager.STATUS_PAUSED -> {
            }
            DownloadManager.STATUS_PENDING -> {
            }
            DownloadManager.STATUS_RUNNING -> {
                val processStr = currentSize * 100 / totalSize
                dialog_update_app_bar.progress = processStr.toInt()
            }
            DownloadManager.STATUS_SUCCESSFUL -> {
//                installApkO()
                downloadId = -1
                dismiss()
            }
        }
    }

    /**
     * 设置心跳包
     */
    private val task = object : Runnable {
        override fun run() {
            updateView()
            handler.postDelayed(this, 50)
        }
    }

    inner class DownloadContentObserver : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            updateView()

        }
    }

    /**
     * 兼容8.0安装位置来源的权限
     */
    private fun installApkO() {
        AppUtils.installApk(
            context,
            File(Environment.getExternalStoragePublicDirectory("zzzh"), apkName)
        )
    }

    override fun dismiss() {
        super.dismiss()
        onCancel()
        handler.removeCallbacks(task)
    }

}