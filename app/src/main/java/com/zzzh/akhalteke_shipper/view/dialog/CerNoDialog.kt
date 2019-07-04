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
import kotlinx.android.synthetic.main.dialog_cerno.*

class CerNoDialog(var mContext: Context, val type: Int = 0) :
    AlertDialog(mContext, R.style.mydialog) {

    private var metrics: DisplayMetrics = context.resources.displayMetrics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSizeMode()
        setContentView(R.layout.dialog_cerno)

        initView()
    }

    private fun initView() {

        when (type) {
            0 -> {
                dialog_cerno_text01.text = "实名认证未通过，请重新认证"
            }
            1 -> {
                dialog_cerno_text01.text = "公司认证未通过，请重新认证"
            }
        }

        dialog_cerno_delete.setOnClickListener { dismiss() }
        dialog_cerno_sure.setOnClickListener {
            dismiss()
            when (type) {
                0 -> {
                    RouterTo(mContext).jumpToAuthen()
                }
                1 -> {
                    RouterTo(mContext).jumpToCompanyCertification()
                }
            }
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