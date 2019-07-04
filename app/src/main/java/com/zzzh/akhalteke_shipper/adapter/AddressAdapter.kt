package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.AddressInfo

class AddressAdapter(var context: Context, var infoList: MutableList<AddressInfo>) :
    BaseQuickAdapter<AddressInfo, BaseViewHolder>(R.layout.item_address, infoList) {

    private val redColor = context.resources.getColor(R.color.main_color)
    private val grayColor = context.resources.getColor(R.color.main_text9)

    override fun convert(helper: BaseViewHolder?, item: AddressInfo?) {
        val position = helper!!.layoutPosition

        helper?.apply {
            setText(R.id.item_address_name,item!!.receiverName)
            setText(R.id.item_address_phone,item!!.phone)
            setText(R.id.item_address_content,item!!.areaCodeName+item!!.address)

            addOnClickListener(R.id.item_address_moren)
            addOnClickListener(R.id.item_address_delete)
            addOnClickListener(R.id.item_address_edit)
        }

        when(item!!.ifDefault){
            "2" -> {
                helper!!.setImageResource(R.id.item_address_radio, R.mipmap.icon_radio)
                helper!!.setText(R.id.item_address_text01, "设为默认")
                helper!!.setTextColor(R.id.item_address_text01, grayColor)
            }
            "1" -> {
                helper!!.setImageResource(R.id.item_address_radio, R.mipmap.icon_radio_red)
                helper!!.setText(R.id.item_address_text01, "已设置")
                helper!!.setTextColor(R.id.item_address_text01, redColor)
            }
        }





    }


}