package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.WalletInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils

class WalletAdapter(var context: Context, var infoList: MutableList<WalletInfo.WalletRecordInfo>) :
    BaseQuickAdapter<WalletInfo.WalletRecordInfo, BaseViewHolder>(R.layout.item_wallet, infoList) {

    private val blackColor = context.resources.getColor(R.color.main_text3)
    private val redColor = context.resources.getColor(R.color.main_color)

    override fun convert(helper: BaseViewHolder?, item: WalletInfo.WalletRecordInfo?) {
        val position = helper!!.layoutPosition

        when (item!!.eventType) {
            "1" -> {
            }
            "2" -> {
            }
            "3" -> {
            }
            "4" -> {
                helper!!.setTextColor(R.id.item_wallet_text01, blackColor)
                helper!!.setTextColor(R.id.item_wallet_money, blackColor)
                helper!!.setTextColor(R.id.item_wallet_text02, blackColor)
                helper!!.setText(R.id.item_wallet_text02, "+")
                helper!!.setText(R.id.item_wallet_name, "充值")
                helper!!.setImageResource(R.id.item_wallet_icon, R.mipmap.ic_wallet_income)
            }
            "5" -> {
                helper!!.setTextColor(R.id.item_wallet_text01, redColor)
                helper!!.setTextColor(R.id.item_wallet_money, redColor)
                helper!!.setTextColor(R.id.item_wallet_text02, redColor)
                helper!!.setText(R.id.item_wallet_text02, "-")
                helper!!.setText(R.id.item_wallet_name, "消费")
                helper!!.setImageResource(R.id.item_wallet_icon, R.mipmap.ic_wallet_recharge)
            }
        }


        helper?.apply {
            setText(R.id.item_wallet_money, item!!.sum)
            setText(R.id.item_wallet_time, TimeUntils.getStrTime(item!!.createdTime))
        }

        helper!!.setVisible(R.id.item_wallet_line, position != 0)
    }

}