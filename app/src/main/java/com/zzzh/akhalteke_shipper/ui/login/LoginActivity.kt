package com.zzzh.akhalteke_shipper.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.lipo.utils.KeyBoardUtils
import com.yanzhenjie.permission.Permission
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.WebActivity
import com.zzzh.akhalteke_shipper.ui.login.viewmodel.LoginViewModel
import com.zzzh.akhalteke_shipper.utils.*
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录，填写手机号
 */
class LoginActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    private var temp = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        Constant.ifToLogin = true
        initViewModel()

    }

    override fun onResume() {
        super.onResume()
        initLoaction()
    }

    private fun initView() {
        //获取手机验证码
        login_getcode.setOnClickListener {
            val phone = login_phone.text.toString().trim()
            isEmpty(phone, "请输入手机号码") ?: return@setOnClickListener

            if (phone.length != 11) {
                showToast("请输入11位电话号码")
                return@setOnClickListener
            }

            if (temp == 1) {
                showToast("请已阅读并同意《马道隐私协议》")
                return@setOnClickListener
            }

            KeyBoardUtils.closeKeybord(login_phone, mContext)

            loginViewModel.getCode(phone = phone)
        }

        //是否同意协议

        login_radio.setOnClickListener {
            when (temp) {
                0 -> {
                    temp = 1
                    login_radio.setImageResource(R.drawable.ic_logo_btn_n)
                }
                1 -> {
                    temp = 0
                    login_radio.setImageResource(R.drawable.ic_logo_btn_s)
                }
            }
        }

        login_pro.setOnClickListener {
            routerTo.jumpToWeb(WebActivity.SHIPPER_URL)
        }
    }

    private fun initViewModel() {
        loginViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })
        //错误码处理，暂时不用
        loginViewModel.errorCode.observe(this, Observer {
            when (it) {
                "2051" -> {
                    RouterTo(mContext).jumpToLoginCode(login_phone.text.toString().trim())
                }
            }
        })

        //监听发送短信验证码返回的数据
        loginViewModel.codeInfo.observe(this, Observer { codeInfo ->
            RouterTo(mContext).jumpToLoginCode(login_phone.text.toString().trim())
        })
    }

    /**
     * 手机定位的权限
     */
    private fun initLoaction() {
        PermissionUtils.showPermission(mContext, "APP需要定位权限，请允许", arrayOf(Permission.ACCESS_COARSE_LOCATION)) {
            LocationUtils.initLocationOption { latitude, longitude ->
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.ifToLogin = false
    }


}
