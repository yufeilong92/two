package com.zzzh.akhalteke_shipper.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.WindowManager
import com.zzzh.akhalteke_shipper.R
import kotlinx.android.synthetic.main.dialog_load_type.*

abstract class LoadTypeDialog(var mContext: Context) : AlertDialog(mContext, R.style.mydialog) {

    private var metrics: DisplayMetrics = context.resources.displayMetrics
    private val itemInfos = arrayListOf(
        "一装一卸", "一装两卸", "一装多卸", "两装一卸", "两装两卸", "多装多卸"
    )

    private var tposition = 0

    init {
        window!!.setWindowAnimations(R.style.popup_animation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSizeMode()
        setContentView(R.layout.dialog_load_type)

        initView()
    }

    private fun initView() {
        dialog_load_loop.setNotLoop()
        dialog_load_loop.setInitPosition(0)
        dialog_load_loop.setItems(itemInfos)
        dialog_load_loop.setListener {
            tposition = it
        }
        dialog_load_cancel.setOnClickListener { dismiss() }
        dialog_load_sure.setOnClickListener {
            choiceItem(tposition + 1, itemInfos[tposition])
            dismiss()
        }
    }

    abstract fun choiceItem(id: Int, name: String)

    private fun setSizeMode() {
        val params = window!!.attributes
        params.width = metrics.widthPixels
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        window!!.setGravity(Gravity.BOTTOM)
    }
}