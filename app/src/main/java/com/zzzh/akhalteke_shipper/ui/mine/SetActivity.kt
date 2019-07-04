package com.zzzh.akhalteke_shipper.ui.mine

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Environment
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.push.PushHelper
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.updateapp.UpdateDialog
import com.zzzh.akhalteke_shipper.updateapp.UpdateViewModel
import com.zzzh.akhalteke_shipper.utils.*
import com.zzzh.akhalteke_shipper.view.dialog.PromiseDialog
import kotlinx.android.synthetic.main.activity_set.*

/**
 * 设置
 */
class SetActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)
        initView()
        tv_seting_setting.text="当前版本${ToolUtils.getVersionName(mContext)}"
    }

    private fun initView() {
//        set_version_code.text = "当前版本${ToolUtils.getVersionCode(mContext)?.versionName?:"1.0.0"}"
        //返回
        set_feedback.setOnClickListener {
            routerTo.jumpTo(FeedbackActivity::class.java)
        }
        //清空缓存
        set_clear.setOnClickListener {
            var number = ImageLoadingUtils.clearCaches()
//            if (number > 800) {
//                showToast("清除${Arith.div(number.toDouble(), 1024.0, 2)}K空间")
//            } else {
//                showToast("清除${number}K空间")
//            }
            val filePath01 = Environment.getExternalStoragePublicDirectory("zzzh")
            if (filePath01.exists()) {
                val listFile = filePath01.listFiles()
                if (listFile!!.isNotEmpty()) {
                    for (lfile in listFile!!) {
                        lfile.delete()
                    }
                }
            }
            showToast("已清空")
        }
        //退出登录
        set_submit.setOnClickListener {
            val dialog = PromiseDialog(mContext, "确认退出账号?", {
                PushHelper.deleteAlias()
                PreferencesUtils().clearUserInfo()
                routerTo.jumpToLogin()
            }, {
            })
            dialog.show()

        }
        //检查更新
        set_version.setOnClickListener {
            update()
        }
    }

    private val updateViewModel by lazy {
        ViewModelProviders.of(this).get(UpdateViewModel::class.java)
    }

    /**
     * 检测更新
     */
    private fun update() {
        updateViewModel.isShowProgress.observe(this, Observer {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })
        updateViewModel.vInfo.observe(this, Observer { versionInfo ->
            val vCode = ToolUtils.getVersionCode(mContext)!!.versionCode
            if (ToolUtils.stringToIntM(versionInfo!!.versionCode) > vCode) {
                if (!Constant.ifToLogin) {
                    val dialog = UpdateDialog(mContext, versionInfo) {
                        if (versionInfo.type == "1") {
                            mContext.finish()
                        }
                    }
                    dialog!!.show()
                }
            } else {
                showToast("已经是最新版本")
            }
        })
        updateViewModel.updateHttp(false)
    }


}
