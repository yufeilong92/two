package com.zzzh.akhalteke_shipper.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import com.lipo.utils.DisplayUtil
import com.zzzh.akhalteke_shipper.R
import kotlinx.android.synthetic.main.dialog_promise_single.*

class PromiseSingleDialog(
    var mContext: Context,
    var titleName: String = "提示",
    var content: String = "",
    val sureClick: () -> Unit) :
    AlertDialog(mContext, R.style.mydialog) {

    private var metrics: DisplayMetrics = context.resources.displayMetrics
    private var sureStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSizeMode()
        setContentView(R.layout.dialog_promise_single)

        initView()
    }

    private fun initView() {
        dialog_promise_single_title.text = titleName
        dialog_promise_single_content.text = content
        dialog_promise_single_sure.setOnClickListener {
            sureClick()
            dismiss()
        }
    }

    private fun toSetTitle(title:String){
        if(dialog_promise_single_title!=null){
            dialog_promise_single_title.text = title
        }
    }

    private fun toSetContent(content:String){
        if(dialog_promise_single_content!=null){
            dialog_promise_single_content.text = content
        }
    }

     fun toSetTitleAndContent(title:String,content:String,sureStr:String){
        if(dialog_promise_single_title!=null){
            dialog_promise_single_title.text = title
        }else{
            titleName = title
        }
        if(dialog_promise_single_content!=null){
            dialog_promise_single_content.text = content
        }else{
            this.content = content
        }
        if(dialog_promise_single_sure!=null){
            dialog_promise_single_sure.text = sureStr
        }else{
            this.sureStr = sureStr
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