package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.utils.ToolUtils

class LabelTextAdapter(val context: Context, val infoList: MutableList<StringInfo>) :
    BaseQuickAdapter<StringInfo, BaseViewHolder>(R.layout.item_label_text, infoList) {

    override fun convert(helper: BaseViewHolder?, item: StringInfo?) {
        val position = helper!!.layoutPosition
        val item_label_name = helper!!.getView<TextView>(R.id.item_label_name)
        if(item!!.temp == 1){
            item_label_name.setBackgroundResource(R.drawable.bg_select_qualification_s)
            item_label_name.setTextColor(context.resources.getColor(R.color.white))
        }else{
            item_label_name.setBackgroundResource(R.drawable.bg_select_qualification_n)
            item_label_name.setTextColor(context.resources.getColor(R.color.main_text6))
        }
        item_label_name.text = item.name
    }

}