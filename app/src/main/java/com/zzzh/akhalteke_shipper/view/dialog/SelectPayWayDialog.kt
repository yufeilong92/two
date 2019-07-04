package com.zzzh.akhalteke_shipper.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import android.widget.Toolbar
import com.lipo.utils.KeyBoardUtils
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.PayWayAdapter
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.dialog_pay_way.*

abstract class SelectPayWayDialog : Dialog {

    private lateinit var mContext: Context
    private var isCreaded = false
    private var metrics: DisplayMetrics? = null

    private var adapter: PayWayAdapter? = null

    constructor(context: Context) : super(context, R.style.mydialog) {
        init(context)
    }


    protected constructor(context: Context, theme: Int) : super(context, theme) {
        init(context)
    }

    private fun init(context: Context) {
        mContext = context
        metrics = context.resources.displayMetrics
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setSizeMode()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_pay_way)

        isCreaded = true
        initView()

    }

    private fun initView() {
        initAdapter()
        dialog_payway!!.layoutParams = FrameLayout.LayoutParams(
            metrics!!.widthPixels, FrameLayout.LayoutParams.WRAP_CONTENT
        )

        dialog_payway_sure.setOnClickListener {
            val editName = dialog_payway_edit.text.toString().trim()
//            if(ToolUtils.isEmpty(editName)){
//                ToastView.setToasd(mContext,"请选择支付方式")
//                return@setOnClickListener
//            }
            callBackData(editName)
            dismiss()
        }
    }

    private fun initAdapter() {
        if (adapter == null) {
            adapter = object : PayWayAdapter(
                mContext, mutableListOf(
                    StringInfo(name = "到付"),
                    StringInfo(name = "现付+到付"),
                    StringInfo(name = "三段付"),
                    StringInfo(name = "需回单"),
                    StringInfo(name = "全部现金")
                )
            ) {
                override fun clickItem(item: StringInfo) {
                    dialog_payway_flow.toSetAdapter(adapter!!)
                    dialog_payway_edit.setText(item.name)
                    dialog_payway_edit.setSelection(item.name.length)
                }
            }
            dialog_payway_flow.toSetAdapter(adapter!!)
            dialog_payway_edit.setText("到付")
            dialog_payway_edit.setSelection(2)
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun dismiss() {
        KeyBoardUtils.closeKeybord(dialog_payway_edit!!, mContext)
        super.dismiss()
    }
    abstract fun callBackData(name:String)
    private fun setSizeMode() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val params = window!!.attributes
        params.width = metrics!!.widthPixels
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        window!!.setGravity(Gravity.BOTTOM)
    }
}