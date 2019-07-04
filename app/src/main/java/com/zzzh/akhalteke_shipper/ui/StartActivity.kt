package com.zzzh.akhalteke_shipper.ui

import android.os.Bundle
import android.os.Handler
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.push.PushHelper
import com.zzzh.akhalteke_shipper.ui.login.AuthenActivity
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.PreferencesUtils
import com.zzzh.akhalteke_shipper.utils.RouterTo
import com.zzzh.akhalteke_shipper.utils.ToolUtils

/**
 * 启动页
 * @property handler Handler
 * @property isCreate Boolean
 */
class StartActivity : BaseActivity() {

    private val handler = Handler()

    private var isCreate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        PushHelper.startPush()

        ToolUtils.addressToAddRealm(mContext)
        isCreate = true
        if (PreferencesUtils().isFristApp()) {
            PreferencesUtils().toSetNoFristApp()
            routerTo.jumpTo(SplashActivity::class.java)
        } else {
            handler.postDelayed( {
                if (isCreate) {
                    finish()
                    if (ToolUtils.isEmpty(Constant.token)) {
                        RouterTo(mContext).jumpToLogin()
                    } else {
                        routerTo.jumpToMain()
                    }
                }
            }, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isCreate = false
    }
}
