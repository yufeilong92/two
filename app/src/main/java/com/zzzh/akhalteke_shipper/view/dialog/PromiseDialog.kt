package com.zzzh.akhalteke_shipper.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import com.lipo.utils.DisplayUtil
import com.zzzh.akhalteke_shipper.R
import kotlinx.android.synthetic.main.dialog_promise.*

class PromiseDialog(var mContext: Context, val titleName: String = "", val sureClick: () -> Unit,val cancelClick:()->Unit) :
    AlertDialog(mContext, R.style.mydialog) {

    private var metrics: DisplayMetrics = context.resources.displayMetrics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSizeMode()
        setContentView(R.layout.dialog_promise)

        initView()
    }

    private fun initView() {
        dialog_promise_title.text = titleName
        dialog_promise_cancle.setOnClickListener {
            cancelClick()
            dismiss()
        }
        dialog_promise_sure.setOnClickListener {
            sureClick()
            dismiss()
        }
    }

    private fun toSetTitle(title:String){
        if(dialog_promise_title!=null){
            dialog_promise_title.text = title
        }
    }

    private fun setSizeMode() {
        val params = window!!.attributes
        params.width = metrics.widthPixels - DisplayUtil.dip2px(mContext, 100f)
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        window!!.setGravity(Gravity.CENTER)
    }

}