package com.zzzh.akhalteke_shipper.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.CancleOrderCaseAdapter
import com.zzzh.akhalteke_shipper.bean.SelectRecyclerVo
import kotlinx.android.synthetic.main.item_order_detal_case.*

/**
 * @Author : YFL  is Creating a porject in akhalteke-Android-shipper
 * @Package com.zzzh.akhalteke_shipper.view.dialog
 * @Email : yufeilong92@163.com
 * @Time :2019/5/27 15:41
 * @Purpose :订单取消原因
 */
abstract class OrderCancleDialog(context: Context?) : AlertDialog(context, R.style.mydialog) {
    private var metrics: DisplayMetrics = context!!.resources.displayMetrics

    init {
        window!!.setWindowAnimations(R.style.popup_animation)
    }

    private var cancelContent: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSizeMode()
        setContentView(R.layout.item_order_detal_case)
        setAdapter()
        initView()
    }

    private fun initView() {
        tv_dialog_order_detail_submit.setOnClickListener {
            if(cancelContent == ""){
                ToastView.setToasd(context,"请选择取消运单原因")
                return@setOnClickListener
            }
            dismiss()
            cancelSubmit(cancelContent)
        }
    }

    fun setAdapter() {
        val cancleDatas = context.resources.getStringArray(R.array.cancle_order_case)
        var mDatas = mutableListOf<SelectRecyclerVo>()
        for (postion in 0 until cancleDatas.size) {
            val mSelect = SelectRecyclerVo()
            mSelect.id = postion
            mSelect.isCheck = false
            mSelect.name = cancleDatas[postion]
            mDatas.add(mSelect)
        }
        val layoutmanger = GridLayoutManager(context, 1)
        layoutmanger.orientation = GridLayoutManager.VERTICAL
        val adapter = CancleOrderCaseAdapter(context, mDatas)
        rlv_dialog_order_detail_case.layoutManager = LinearLayoutManager(context)
        rlv_dialog_order_detail_case.adapter = adapter
        adapter.setOnCheckBokItemListener(object : CancleOrderCaseAdapter.CheckBoklistener {
            override fun setCheckBokItemListener(postion: Int, chb: Boolean, case: String) {
                mClearSelect(mDatas)
                mDatas[postion].isCheck = chb
                adapter.refreshData(mDatas)
                cancelContent = cancleDatas[postion]
            }
        })
    }

    private fun mClearSelect(mDatas: MutableList<SelectRecyclerVo>) {
        if (mDatas == null || mDatas.isEmpty()) return
        for (item in mDatas) {
            item.isCheck = false
        }
    }

    abstract fun cancelSubmit(content: String)

    private fun setSizeMode() {
        val params = window!!.attributes
        params.width = metrics.widthPixels
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        window.setBackgroundDrawableResource(R.color.transparent)
        window!!.setGravity(Gravity.BOTTOM)
    }

}