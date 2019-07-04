package com.zzzh.akhalteke_shipper.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.lipo.utils.KeyBoardUtils
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.SelectorImageActivity
import com.zzzh.akhalteke_shipper.ui.login.viewmodel.CertifViewModel
import com.zzzh.akhalteke_shipper.utils.*
import kotlinx.android.synthetic.main.activity_certification.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

/**
 * 实名认证，现废弃
 */
class CertificationActivity : SelectorImageActivity() {

    private val certifViewModel: CertifViewModel by lazy {
        ViewModelProviders.of(this).get(CertifViewModel::class.java)
    }

    private var imageTemp = 0
    private var iDAddress: String = ""
    private var iDAddressReverse: String = ""
    private var portrait: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification)
        eventBus.register(this)
        initView()
        initViewModel()
    }

    private fun initView() {
        certif_before.setOnClickListener {
            imageTemp = 0
            closeKeyboard()
            toShowDialog()
        }
        certif_after.setOnClickListener {
            imageTemp = 1
            closeKeyboard()
            toShowDialog()
        }
        certif_header.setOnClickListener {
            imageTemp = 2
            closeKeyboard()
            toShowDialog()
        }

        certif_submit.setOnClickListener {
            val iDnumber = cer_idno.text.toString().trim()
            val name = cer_name.text.toString().trim()

            isEmpty(name, "请输入真实姓名") ?: return@setOnClickListener

            if (iDnumber.isEmpty() || !IDCardValidate.validate_effective(iDnumber)) {
                showToast("请输入正确格式的身份证号")
                return@setOnClickListener
            }
            isEmpty(iDAddress, "请上传身份证正面照") ?: return@setOnClickListener
            isEmpty(iDAddressReverse, "请上传身份证反面照") ?: return@setOnClickListener
            isEmpty(portrait, "请上传头像") ?: return@setOnClickListener

            certifViewModel.authRealName(
                id = Constant.userInfo.id,
                iDnumber = iDnumber,
                iDAddress = iDAddress,
                iDAddressReverse = iDAddressReverse,
                portrait = portrait,
                name = name
            )
        }
    }

    private fun initViewModel() {
        certifViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })
        certifViewModel.backSuccess.observe(this, Observer { info ->
            Constant.userInfo.ifRealCertification = "1"
            Constant.userInfo.name = info!!.name
            Constant.userInfo.number = info!!.number
            Constant.userInfo.portrait = info!!.portrait
            PreferencesUtils().updateUserInfo()
//            if (Constant.userInfo.ifCompanyCertification == "2") {
//                RouterTo(mContext).jumpToCompanyCertification()
//            } else {
//            RouterTo(mContext).jumpToMain()
//            }

            finish()
        })
    }

    override fun onFileData(imagePath: String, imageStr: String) {
        when (imageTemp) {
            0 -> {
                iDAddress = imageStr
                ImageLoadingUtils.loadLocalImage(certif_before, imagePath)
            }
            1 -> {
                iDAddressReverse = imageStr
                ImageLoadingUtils.loadLocalImage(certif_after, imagePath)
            }
            2 -> {
                portrait = imageStr
                ImageLoadingUtils.loadLocalImage(certif_header, imagePath)
            }
        }
    }

    private fun closeKeyboard() {
        KeyBoardUtils.closeKeybord(cer_idno, mContext)
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
