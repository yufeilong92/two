package com.zzzh.akhalteke_shipper.ui.mine

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.SelectorImageFileActivity
import com.zzzh.akhalteke_shipper.ui.login.AuthenActivity
import com.zzzh.akhalteke_shipper.ui.login.AuthenNewActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.InformationViewModel
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.PreferencesUtils
import kotlinx.android.synthetic.main.activity_information.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 我的信息
 */
class InformationActivity : SelectorImageFileActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(InformationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        eventBus.register(this)
        initView()
        initViewModel()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        ImageLoadingUtils.loadNetImage(information_header, Constant.userInfo.portrait)
        information_idno.text = Constant.userInfo.number ?: ""
        information_phone.text = Constant.userInfo.phone ?: ""
        information_realname.text = Constant.userInfo.name ?: ""
        information_company.text = Constant.userInfo.corporateName ?: ""

        //是否实名认证
        if (Constant.userInfo.ifRealCertification == "1") {
            information_next01.visibility = View.GONE
        } else {
            information_next01.visibility = View.VISIBLE
            information_realname.text = "去认证"
            information_idview.setOnClickListener {
                routerTo.jumpTo(AuthenNewActivity::class.java)
            }
        }
        information_header.setOnClickListener {
            toShowDialog()
        }
    }

    override fun toGetData(imagePath: MutableList<String>) {
        //上传头像接口调用
        mViewModel.uploadPortrait(imagePath = imagePath[0])
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

        //上传头像后返回头像地址
        mViewModel.imageUrl.observe(this, Observer {
            Constant.userInfo.portrait = it!!
            PreferencesUtils().updateUserInfo()
            ImageLoadingUtils.loadNetImage(information_header,it!!)
            ToastView.setToasd(mContext,"更新成功")
            eventBus.post(MessageEvent(MessageEvent.MINE_HEADER_SUCCESS))
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.AUTHEN_SUCCESS -> {
                initView()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }

}
