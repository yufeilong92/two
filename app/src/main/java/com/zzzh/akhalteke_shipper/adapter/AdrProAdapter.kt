package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.realm.AddressBean

class AdrProAdapter(var context: Context, var infoList: MutableList<AddressBean>) :
    BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.item_address_pro, infoList) {

    public var selectPosition:Int = -1
    private var sbg = context.resources.getColor(R.color.address_sbg)
    private var tranColor = context.resources.getColor(R.color.transparent)

    private var blackTextC = context.resources.getColor(R.color.main_text3)
    private var mainColor = context.resources.getColor(R.color.main_color_text)

    override fun convert(helper: BaseViewHolder?, item: AddressBean?) {
        val position = helper!!.layoutPosition
        val mView = helper!!.getView<View>(R.id.item_adrpro)
        if(selectPosition == position){
//            mView.setBackgroundColor(sbg)
//            helper!!.setVisible(R.id.item_adrpro_label,true)
            helper!!.setTextColor(R.id.item_adrpro_text,mainColor)
        }else{
            mView.setBackgroundColor(tranColor)
//            helper!!.setVisible(R.id.item_adrpro_label,false)
            helper!!.setTextColor(R.id.item_adrpro_text,blackTextC)
        }

        helper!!.setText(R.id.item_adrpro_text,item!!.area_name)
    }

    fun toSetPosition(selectPosition:Int){
        this.selectPosition = selectPosition
        notifyDataSetChanged()
    }

}