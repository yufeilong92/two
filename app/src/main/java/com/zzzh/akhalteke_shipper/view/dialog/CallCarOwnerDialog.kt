package com.zzzh.akhalteke_shipper.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import com.lipo.utils.DisplayUtil
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.utils.RouterTo
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.dialog_call_carowner.*

class CallCarOwnerDialog(val mContext: Context, val phone: String) : AlertDialog(mContext, R.style.mydialog) {

    private var metrics: DisplayMetrics = context.resources.displayMetrics

//    init {
//        window!!.setWindowAnimations(R.style.popup_animation)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSizeMode()
        setContentView(R.layout.dialog_call_carowner)
        initView()
    }

    fun setReplaceText() {
        var content = dialog_call_text01.text.trim().toString()
        content = content.replace("司机", "货主")
        dialog_call_text01.text = content
    }

    private fun initView() {
        call_phone_content.text = ToolUtils.phoneEncry(phone)
        call_phone.setOnClickListener {
            RouterTo(mContext).callPhone(phone)
        }
    }

    private fun setSizeMode() {
        val params = window!!.attributes
        params.width = metrics.widthPixels - DisplayUtil.dip2px(mContext, 70f)
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        window!!.setGravity(Gravity.CENTER)
    }

}