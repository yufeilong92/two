package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.SendGoodsInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils

class MainSendAdapter(var context: Context, var infoList: MutableList<SendGoodsInfo>) :
    BaseQuickAdapter<SendGoodsInfo, BaseViewHolder>(R.layout.item_send, infoList) {


    override fun convert(helper: BaseViewHolder?, item: SendGoodsInfo?) {
        val position = helper!!.layoutPosition

        helper!!.apply {
            setText(
                R.id.item_send_infor,
                item!!.carLength + " " + item!!.weightVolume
            )
            setText(R.id.item_send_time, TimeUntils.toTimeSpace(item!!.createdTime))

            getView<TextView>(R.id.item_send_path).text =
                ToolUtils.adrSpannStr(mContext, item!!.loadAreaCode, item!!.unloadAreaCode)

            addOnClickListener(R.id.item_send_todiver)
            addOnClickListener(R.id.item_send_refresh)
            addOnClickListener(R.id.item_send_delete)
        }
    }

}