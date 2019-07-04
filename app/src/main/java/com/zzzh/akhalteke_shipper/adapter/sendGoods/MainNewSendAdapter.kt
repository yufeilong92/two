package com.zzzh.akhalteke_shipper.adapter.sendGoods

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.SendGoodsInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.adapter.sendGoods
 * @Package com.zzzh.akhalteke_shipper.adapter.sendGoods
 * @Email : yufeilong92@163.com
 * @Time :2019/5/29 11:34
 * @Purpose :发货中
 */
class MainNewSendAdapter(var context: Context, var infoList: MutableList<SendGoodsInfo>) :
    BaseQuickAdapter<SendGoodsInfo, BaseViewHolder>(R.layout.item_new_send, infoList) {


    override fun convert(helper: BaseViewHolder?, item: SendGoodsInfo?) {
        val position = helper!!.layoutPosition

        helper!!.apply {
            setText(
                R.id.tv_item_send_goods_infom,
                item!!.carLength + " " + item!!.weightVolume
            )
            setText(R.id.tv_item_send_goods_time, TimeUntils.getStrTime(item!!.createdTime))

            getView<TextView>(R.id.tv_item_send_goods_address).text =
                ToolUtils.adrSpannStr(mContext, item!!.loadAreaCode, item!!.unloadAreaCode)

            addOnClickListener(R.id.tv_item_designated_driver)
            addOnClickListener(R.id.tv_item_refresh_log)
            addOnClickListener(R.id.tv_item_deete_log)
        }
    }

}