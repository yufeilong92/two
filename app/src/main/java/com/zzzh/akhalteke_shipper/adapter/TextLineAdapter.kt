package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.StringInfo

class TextLineAdapter(var context: Context, var infoList: MutableList<StringInfo>) :
    BaseQuickAdapter<StringInfo, BaseViewHolder>(R.layout.item_text_label, infoList) {

    private var selectPosition:Int = -1

    private var sbg = context.resources.getColor(R.color.address_sbg)
    private var tranColor = context.resources.getColor(R.color.transparent)

    private var blackTextC = context.resources.getColor(R.color.main_text3)
    private var mainColor = context.resources.getColor(R.color.main_color_text)

    override fun convert(helper: BaseViewHolder?, item: StringInfo?) {
        val position = helper!!.layoutPosition
        val mView = helper!!.getView<TextView>(R.id.item_text_label_name)
        if(selectPosition == position){
            mView.setBackgroundColor(sbg)
            helper!!.setTextColor(R.id.item_text_label_name,mainColor)
        }else{
            mView.setBackgroundColor(tranColor)
            helper!!.setTextColor(R.id.item_text_label_name,blackTextC)
        }
    }

    fun toSetPosition(selectPosition:Int){
        this.selectPosition = selectPosition
        notifyDataSetChanged()
    }
}