package com.zzzh.akhalteke_shipper.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.lipo.utils.KeyBoardUtils
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.push.PushHelper
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.login.viewmodel.LoginViewModel
import com.zzzh.akhalteke_shipper.ui.login.viewmodel.MapViewModel
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.RouterTo
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.helper.GetCodeHelper
import kotlinx.android.synthetic.main.activity_login_code.*
import kotlinx.android.synthetic.main.cell_getcode.view.*

/**
 * 填写手机验证码
 */
class LoginCodeActivity : BaseActivity() {

    private lateinit var getCodeHelper: GetCodeHelper//6格数字填写的帮助类

    private var phone: String = ""//手机号码

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }
    private val mapViewModel by lazy {
        ViewModelProviders.of(this).get(MapViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_code)
        phone = intent.getStringExtra("phone")
        initView()
        initViewModel()
    }

    /**
     * 初始化页面
     */
    private fun initView() {
        login_getcode_phone.text = phone
//        getCodeHelper = object : GetCodeHelper(mContext, login_getcode_layout) {
//            override fun submit(code: String) {
//                loginViewModel.checkCode(phone, code)
//            }
//        }

        //返回按键
        login_getcode_back.setOnClickListener { finishBase() }

        //验证码输入框
        login_getcode_layout.apply {
            val mViews = arrayOf(
                login_getcode_code1,
                login_getcode_code2,
                login_getcode_code3,
                login_getcode_code4,
                login_getcode_code5,
                login_getcode_code6
            )

            //输入监听
            login_getcode_edit.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val editStr = login_getcode_edit.text.toString().trim()
                    val length = editStr.length
                    for (i in 0..5) {
                        mViews[i].text = ""
                    }
                    for (i in 0 until length) {
                        mViews[i].text = editStr.substring(i, i + 1)
                    }

                    if (length == 6) {
//                        submit(editStr)
                        loginViewModel.checkCode(phone, editStr)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            login_getcode_edit.isLongClickable = false
            login_getcode_edit.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
            login_getcode_edit.setOnClickListener {
                val imm =
                    login_getcode_edit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                login_getcode_edit.isFocusableInTouchMode = true
                login_getcode_edit.requestFocus()
                imm.showSoftInput(login_getcode_edit, InputMethodManager.SHOW_FORCED)
                login_getcode_edit.isFocusableInTouchMode = false
            }

//            setOnClickListener {  KeyBoardUtils.openKeybord(login_getcode_edit,mContext) }
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

        //验证成功后的数码返回监听
        loginViewModel.userInfo.observe(this, Observer {
            PushHelper.toStartPush()
            mapViewModel.pointAdd(Constant.latitude.toString(), Constant.longitude.toString())
            finish()
            routerTo.jumpToMain()
//            if (it!!.ifRealCertification != "1") {
//                routerTo.jumpToCertification()
//            } else if (it!!.ifCompanyCertification != "1") {
//                routerTo.jumpToCompanyCertification()
//            } else {
//                routerTo.jumpToMain()
//            }
        })
    }


}
