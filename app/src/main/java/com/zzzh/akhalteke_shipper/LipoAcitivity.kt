package com.zzzh.akhalteke_shipper

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yanzhenjie.permission.Permission
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.GoodsViewModel
import com.zzzh.akhalteke_shipper.utils.PermissionUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LipoAcitivity: BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventBus.register(this)
    }

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(GoodsViewModel::class.java)
    }
    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, android.arch.lifecycle.Observer {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })
    }


    /**
     * 测试权限需求
     */
    fun testPer() {
        PermissionUtils.showPermission(mContext, "", arrayOf(Permission.WRITE_EXTERNAL_STORAGE)) {

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.MESSAGEINFO -> {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }

}