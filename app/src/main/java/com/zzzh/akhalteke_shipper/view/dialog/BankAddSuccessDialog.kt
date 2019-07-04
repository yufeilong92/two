package com.zzzh.akhalteke_shipper.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import com.lipo.utils.DisplayUtil
import com.zzzh.akhalteke_shipper.R
import kotlinx.android.synthetic.main.dialog_bank_add_success.*

class BankAddSuccessDialog(var mContext: Context,val sureClick:()->Unit,val cancelClick:()->Unit) :
    AlertDialog(mContext, R.style.mydialog) {

    private var metrics: DisplayMetrics = context.resources.displayMetrics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSizeMode()
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.dialog_bank_add_success)

        initView()
    }

    private fun initView() {
        dialog_bas_cancel.setOnClickListener {
            cancelClick()
            dismiss()
        }
        dialog_bas_sure.setOnClickListener {
            sureClick()
            dismiss()
        }
    }

    private fun setSizeMode() {
        val params = window!!.attributes
        params.width = metrics.widthPixels - DisplayUtil.dip2px(mContext, 75f)
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        window!!.setGravity(Gravity.CENTER)
    }

}