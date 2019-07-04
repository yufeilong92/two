package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.BankCardInfo
import com.zzzh.akhalteke_shipper.bean.CashInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils

class WithdrawRecordAdapter(var context: Context, var infoList: MutableList<CashInfo>) :
    BaseQuickAdapter<CashInfo, BaseViewHolder>(R.layout.item_withdraw_record, infoList) {

    val colorSuc = context.resources.getColor(R.color.withdraw_success)
    val colorfail = context.resources.getColor(R.color.main_color)
    val coloring = context.resources.getColor(R.color.withdraw_ing)

    override fun convert(helper: BaseViewHolder?, item: CashInfo?) {
        val position = helper!!.layoutPosition
        helper?.apply {
            setImageResource(R.id.iwr_icon, BankCardInfo.BANK_ICON[item!!.bank] ?: R.mipmap.gongshangtubiao)
            setText(R.id.iwr_name, BankCardInfo.BANK_CODE[item!!.bank] ?: item!!.bank)
            setText(R.id.iwr_money, "￥${item!!.sum}")
            setText(R.id.iwr_bankno, "（尾号 ${item!!.bankNumber}）")
            setText(R.id.iwr_time, TimeUntils.getStrTime(item!!.createdTime))

            val statusView = getView<TextView>(R.id.iwr_status)

            when (item!!.status) {//状态1-成功，2-审核中，3失败
                "1" -> {
                    statusView.setBackgroundResource(R.drawable.bg_withdraw_success)
                    statusView.setTextColor(colorSuc)
                    statusView.text = "提现成功"
                }
                "2" -> {
                    statusView.setBackgroundResource(R.drawable.bg_withdraw_ing)
                    statusView.setTextColor(coloring)
                    statusView.text = "正在审核"
                }
                "3" -> {
                    statusView.setBackgroundResource(R.drawable.bg_withdraw_fail)
                    statusView.setTextColor(colorfail)
                    statusView.text = "提现失败"
                }
            }
        }

    }

}