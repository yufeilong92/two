package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.TranRecordInfo

class TranRecordAdapter(var context: Context, var infoList: MutableList<TranRecordInfo>) :
    BaseQuickAdapter<TranRecordInfo, BaseViewHolder>(R.layout.item_tran_record, infoList) {

    override fun convert(helper: BaseViewHolder?, item: TranRecordInfo?) {
        val position = helper!!.layoutPosition
    }

}