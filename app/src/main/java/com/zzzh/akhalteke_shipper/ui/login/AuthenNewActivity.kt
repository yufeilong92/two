package com.zzzh.akhalteke_shipper.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.bumptech.glide.Glide
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.IDImageAuthenInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.SelectorImageFileActivity
import com.zzzh.akhalteke_shipper.ui.login.viewmodel.AuthenViewModel
import com.zzzh.akhalteke_shipper.utils.*
import kotlinx.android.synthetic.main.activity_authen.*
import kotlinx.android.synthetic.main.activity_authen_new.*
import kotlinx.android.synthetic.main.gm_title_layout_right.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.login
 * @Package com.zzzh.akhalteke_shipper.ui.login
 * @Email : yufeilong92@163.com
 * @Time :2019/5/29 11:57
 * @Purpose :实名认证
 */
class AuthenNewActivity : SelectorImageFileActivity() {
    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(AuthenViewModel::class.java)
    }

    private var imageTemp = 0//0正面，1反面
    private var iDAddress = "" //身份证正面地址
    private var iDAddressReverse = ""//身份证反面地址
    private var portrait = ""//头像地址
    private var iDnumber = ""//身份证号
    private var name = ""//名字
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authen_new)
        gm_tv_activity_title.text = "实名认证"
        gm_tv_activity_right_title.text = "联系客服"
        gm_tv_activity_right_title.setOnClickListener {
            routerTo.callKeFu()
        }
        eventBus.register(this)
        initView()
        initViewModel()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        //身份证正面
        iv_authen_id_z.setOnClickListener {
            imageTemp = 0
            toShowDialog()
        }
        //身份证正面
        tv_authen_updata_id_z.setOnClickListener {
            imageTemp = 0
            toShowDialog()
        }
        //身份证反面
        iv_authen_id_f.setOnClickListener {
            imageTemp = 1
            toShowDialog()
        }
        //身份证反面
        tv_authen_updata_id_f.setOnClickListener {
            imageTemp = 1
            toShowDialog()
        }
        //头像上传
        iv_authen_hear.setOnClickListener {
            imageTemp = 2
            toShowDialog()
        }
        //头像上传
        tv_authen_updata_hear.setOnClickListener {
            imageTemp = 2
            toShowDialog()
        }
        //提交认证
        btn_authen_authentication.setOnClickListener {
            submit()
        }

    }

    /**
     * 获取到图片的本地地址
     * @param imagePath MutableList<String>
     */
    override fun toGetData(imagePath: MutableList<String>) {
        super.toGetData(imagePath)
        when (imageTemp) {
            0 -> {//正面
                mViewModel.uploadFaceImage(imagePath[0], mutableMapOf())
            }
            1 -> {//反面
                mViewModel.uploadBackImage(imagePath[0], mutableMapOf())
            }
            2 -> {//头像
                portrait = imagePath[0]
                GlideUtil.LoadlocalImager(mContext, iv_authen_hear, portrait)
            }
        }
    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })
        //身份证正面数据返回
        mViewModel.faceInfo.observe(this, Observer { info ->
            info?.apply { fillLayout1(info) }
        })
        //身份证返回数据返回
        mViewModel.backInfo.observe(this, Observer { info ->
            info?.apply { fillLayout2(info) }
        })
        //提交全部信息后返回 用户信息
        mViewModel.userInfo.observe(this, Observer { info ->
            Constant.userInfo.ifRealCertification = "1"
            Constant.userInfo.name = info!!.name
            Constant.userInfo.number = info!!.number
            Constant.userInfo.portrait = info!!.portrait
            PreferencesUtils().updateUserInfo()
//            RouterTo(mContext).jumpToMain()
            eventBus.post(MessageEvent(MessageEvent.AUTHEN_SUCCESS))
            finish()
        })

        mViewModel.resonseError.value = true
        mViewModel.errorMsg.observe(this, Observer {
            showToast(it!!)
            when (imageTemp) {
                0 -> {
                    iDAddress = ""
                    tv_authen_name.text = ""
                    tv_authen_idnumber.text = ""

                    iDnumber = ""
                    name = ""
                }
                1 -> {
                    iDAddressReverse = ""
                    tv_authen_visa.text = ""
                    tv_authen_validity.text = ""
                }
                2 -> {

                }
            }
        })
    }

    /**
     * 填充正面信息
     * @param info IDImageAuthenInfo
     */
    private fun fillLayout1(info: IDImageAuthenInfo) {
//        authen_layout01.visibility = View.VISIBLE
//        showAHideView(authen_layout01,true)
        iDAddress = info.savePath
        GlideUtil.LoadImagerWithOutHear(mContext, iv_authen_id_z, info.savePath)
//        ImageLoadingUtils.loadNetImage(certif_before, info.savePath)
        tv_authen_name.text = info.name
        tv_authen_idnumber.text = info.idnumber

        iDnumber = info.idnumber
        name = info.name
    }

    /**
     * 填充反面信息
     * @param info IDImageAuthenInfo
     */
    private fun fillLayout2(info: IDImageAuthenInfo) {
//        authen_layout02.visibility = View.VISIBLE
//        showAHideView(authen_layout02,true)
        iDAddressReverse = info.savePath
        GlideUtil.LoadImagerWithOutHear(mContext, iv_authen_id_f, info.savePath)
//        ImageLoadingUtils.loadNetImage(certif_after, info.savePath)
        tv_authen_visa.text = info.issue
        tv_authen_validity.text = info.effectiveTime
    }

    /**
     * 提交数据
     */
    private fun submit() {
        if (ToolUtils.isEmpty(iDAddress)) {
            showToast("请上传身份证正面")
            return
        }

        if (ToolUtils.isEmpty(iDnumber) || ToolUtils.isEmpty(name)) {
            showToast("身份信息获取不全，请重新上传身份证正面")
            return
        }

        if (ToolUtils.isEmpty(iDAddressReverse)) {
            showToast("请上传身份证反面")
            return
        }

        if (ToolUtils.isEmpty(portrait)) {
            showToast("请上传头像")
            return
        }

        mViewModel.realName(
            portrait, mutableMapOf(
                "iDnumber" to iDnumber,
                "iDAddress" to iDAddress,
                "iDAddressReverse" to iDAddressReverse,
                "name" to name
            )
        )
    }

    private fun showAHideView(mView: View, temp: Boolean) {
        if (temp) {
            val mShowAction = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, -0.0f
            )
            mShowAction.repeatMode = Animation.REVERSE
            mShowAction.duration = 500
            mView.startAnimation(mShowAction)
        } else {
            val mHiddenAction = TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f
            )
            mHiddenAction.duration = 500
            mView.startAnimation(mHiddenAction)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
        ToolUtils.dismissDialog(selectImageDialog)
    }
}
