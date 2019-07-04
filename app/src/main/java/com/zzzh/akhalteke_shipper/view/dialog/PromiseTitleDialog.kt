package com.zzzh.akhalteke_shipper.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import com.lipo.utils.DisplayUtil
import com.zzzh.akhalteke_shipper.R
import kotlinx.android.synthetic.main.dialog_promise_title.*

class PromiseTitleDialog(
    var mContext: Context,
    var titleName: String = "",
    var content: String = "",
    val sureClick: () -> Unit,
    val cancelClick: () -> Unit
) :
    AlertDialog(mContext, R.style.mydialog) {

    private var metrics: DisplayMetrics = context.resources.displayMetrics
    private var sureStr = ""
    private var cancelStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSizeMode()
        setContentView(R.layout.dialog_promise_title)

        initView()
    }

    private fun initView() {
        if (titleName != "") {
            dialog_promise_ttitle.text = titleName
        }
        if (content != "") {
            dialog_promise_tcontent.text = content
        }
        if (cancelStr != "") {
            dialog_promise_tcancle.text = cancelStr
        }
        if (sureStr != "") {
            dialog_promise_tsure.text = sureStr
        }
        dialog_promise_tcancle.setOnClickListener {
            cancelClick()
            dismiss()
        }
        dialog_promise_tsure.setOnClickListener {
            sureClick()
            dismiss()
        }
    }

    private fun toSetTitle(title: String) {
        if (dialog_promise_ttitle != null) {
            dialog_promise_ttitle.text = title
        }
    }

    private fun toSetContent(content: String) {
        if (dialog_promise_tcontent != null) {
            dialog_promise_tcontent.text = content
        }
    }

    private fun toSetData(
        title: String = "温馨提示",
        content: String,
        sureButton: String = "确定",
        cancelButton: String = "取消"
    ) {
        if (dialog_promise_ttitle != null) {
            dialog_promise_ttitle.text = title
        } else {
            titleName = title
        }
        if (dialog_promise_tcontent != null) {
            dialog_promise_tcontent.text = content
        } else {
            this.content = content
        }
        if (dialog_promise_tcancle != null) {
            dialog_promise_tcancle.text = cancelButton
        } else {
            cancelStr = cancelButton
        }
        if (dialog_promise_tsure != null) {
            dialog_promise_tsure.text = sureButton
        } else {
            sureStr = sureButton
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