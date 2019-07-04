package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.BankCardInfo

class BankCardAdapter(var context: Context, var infoList: MutableList<BankCardInfo>) :
    BaseQuickAdapter<BankCardInfo, BaseViewHolder>(R.layout.item_bank_card, infoList) {


    override fun convert(helper: BaseViewHolder?, item: BankCardInfo?) {
        val position = helper!!.layoutPosition

        helper!!.apply {
            setText(R.id.item_bank_card_name,BankCardInfo.BANK_CODE[item!!.bank])
            setText(R.id.item_bank_number,"**** **** **** "+item!!.cardNumber)

            addOnClickListener(R.id.item_bank_delete)
            setBackgroundRes(R.id.item_bank_card_bg,BankCardInfo.BANK_BG[item!!.bank]?:R.mipmap.icon_bank_bg)
            setImageResource(R.id.item_bank_card_icon,BankCardInfo.BANK_ICON[item!!.bank]?:R.mipmap.gongshangtubiao)
        }
    }

}