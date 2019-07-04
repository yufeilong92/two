package com.zzzh.akhalteke_shipper.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import com.lipo.utils.DisplayUtil
import com.zzzh.akhalteke_shipper.R
import kotlinx.android.synthetic.main.dialog_verify_dao.*

class VerifyDaoDialog(var mContext: Context, val cancelClick: () -> Unit) :
    AlertDialog(mContext, R.style.mydialog) {

    private var metrics: DisplayMetrics = context.resources.displayMetrics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSizeMode()
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.dialog_verify_dao)

        initView()
    }

    private fun initView() {
        dialog_verify_dao_close.setOnClickListener {
            cancelClick()
            dismiss()
        }
    }

    private fun setSizeMode() {
        val params = window!!.attributes
        params.width = metrics.widthPixels - DisplayUtil.dip2px(mContext, 72f)
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        window!!.setGravity(Gravity.CENTER)
    }

}