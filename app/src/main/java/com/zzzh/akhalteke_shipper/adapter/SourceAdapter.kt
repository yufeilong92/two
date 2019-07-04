package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.SourceInfo
import com.zzzh.akhalteke_shipper.utils.*

class SourceAdapter(var context: Context, var infoList: MutableList<SourceInfo>) :
    BaseQuickAdapter<SourceInfo, BaseViewHolder>(R.layout.item_source, infoList) {

    override fun convert(helper: BaseViewHolder?, item: SourceInfo?) {
        val position = helper!!.layoutPosition
        val headerView = helper!!.getView<SimpleDraweeView>(R.id.item_source_header)
        ImageLoadingUtils.loadNetImage(headerView, item!!.shipperPortrait)
        helper?.apply {
            setText(R.id.item_source_time, TimeUntils.toTimeSpace(item!!.createdTime))
            setText(R.id.item_source_infor, ZzzhUtils.loadCar(item!!.carLength, item!!.carType, item!!.weightVolume))
            setText(R.id.item_source_infor02, "${item!!.goodsName} ${Constant.loadTypes[item!!.loadType] ?: ""}")
            setText(R.id.item_source_manname, item.shipperName)
            addOnClickListener(R.id.item_source_phone)

            getView<TextView>(R.id.item_source_path).text =
                ToolUtils.adrSpannStr(mContext, item!!.loadAreaCode, item!!.unloadAreaCode)

        }
    }



}