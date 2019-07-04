package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.SendGoodsInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils

class MainHistoryAdapter(var context: Context, var infoList: MutableList<SendGoodsInfo>) :
    BaseQuickAdapter<SendGoodsInfo, BaseViewHolder>(R.layout.item_main_history, infoList) {

    private var dpspace = 0

    init {
        dpspace = ToolUtils.dpTopx(context, 6f)
    }

    override fun convert(helper: BaseViewHolder?, item: SendGoodsInfo?) {
        val position = helper!!.layoutPosition
        helper!!.apply {
            setText(
                R.id.item_mhistory_infor,
                item!!.carLength + " " + item!!.weightVolume
            )
            setText(R.id.item_mhistory_time, TimeUntils.toTimeSpace(item!!.createdTime))
            addOnClickListener(R.id.item_mhistory_save)

            getView<TextView>(R.id.item_mhistory_path).text =
                ToolUtils.adrSpannStr(mContext, item!!.loadAreaCode, item!!.unloadAreaCode)
        }

//        val svaeView = helper!!.getView<TextView>(R.id.item_mhistory_save)
    }

}