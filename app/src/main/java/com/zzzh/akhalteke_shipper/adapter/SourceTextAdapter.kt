package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.SourceInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.utils.ZzzhUtils

class SourceTextAdapter(var context: Context, var infoList: MutableList<SourceInfo>) :
    BaseQuickAdapter<SourceInfo, BaseViewHolder>(R.layout.item_tran_record, infoList) {


    override fun convert(helper: BaseViewHolder?, item: SourceInfo?) {
        val position = helper!!.layoutPosition
        helper?.apply {
            getView<TextView>(R.id.item_tran_record_from).text =
                ToolUtils.adrSpannStr(mContext, item!!.loadAreaName, item!!.unloadAreaName)
            if (ToolUtils.isEmpty(item!!.weightVolume)) {
                setText(R.id.item_tran_record_type, ZzzhUtils.setWeightAndVolume(item!!.weight,item!!.volume))
            } else {
                setText(R.id.item_tran_record_type, ZzzhUtils.loadCar(item!!.carLength, item!!.carType, item!!.weightVolume))
            }
            if (ToolUtils.isEmpty(item!!.loadTime)) {
                setText(R.id.item_tran_record_time, TimeUntils.getStrTime(item!!.createdTime))
            } else {
                setText(R.id.item_tran_record_time, TimeUntils.getStrTime(item!!.loadTime))
            }
        }
    }

}