package com.zzzh.akhalteke_shipper.adapter.sendGoods

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.SendGoodsInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.TimeUtil
import com.zzzh.akhalteke_shipper.utils.ToolUtils
/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.adapter.sendGoods
 * @Package com.zzzh.akhalteke_shipper.adapter.sendGoods
 * @Email : yufeilong92@163.com
 * @Time :2019/5/29 11:34
 * @Purpose :发货历史
 */
class MainNewHistoryAdapter(var context: Context, var infoList: MutableList<SendGoodsInfo>) :
    BaseQuickAdapter<SendGoodsInfo, BaseViewHolder>(R.layout.item_main_new_history, infoList) {

    private var dpspace = 0

    init {
        dpspace = ToolUtils.dpTopx(context, 6f)
    }

    override fun convert(helper: BaseViewHolder?, item: SendGoodsInfo?) {
        val position = helper!!.layoutPosition
        helper!!.apply {
            setText(
                R.id.tv_item_history_infom,
                item!!.carLength + " " + item!!.weightVolume
            )
//            setText(R.id.tv_item_history_release_time, TimeUntils.toTimeSpace(item!!.createdTime))
            addOnClickListener(R.id.tv_item_history_save)

            getView<TextView>(R.id.tv_item_history_city).text =
                ToolUtils.adrSpannStr(mContext, item!!.loadAreaCode, item!!.unloadAreaCode)
            getView<TextView>(R.id.tv_item_history_release_time).text = TimeUtil.getInstance()!!.getMDhmTime(item.createdTime.toLong())
        }

//        val svaeView = helper!!.getView<TextView>(R.id.item_mhistory_save)
    }

}