package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.InvoiceInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils

class InvoiceAdapter(var context: Context, var infoList: MutableList<InvoiceInfo>) :
    BaseQuickAdapter<InvoiceInfo, BaseViewHolder>(R.layout.item_invoice, infoList) {


    override fun convert(helper: BaseViewHolder?, item: InvoiceInfo?) {
        val position = helper!!.layoutPosition

        helper!!.apply {
            setText(R.id.item_invoice_name, item!!.name)
            setText(R.id.item_invoice_time, "申请时间：" + TimeUntils.getStrTime(item!!.createdTime))
            setText(
                R.id.item_invoice_state, if (item!!.status == "1") {
                    "已审核"
                } else {
                    "待审核"
                }
            )
            setText(R.id.item_invoice_money, "￥" + item!!.cost)
        }
    }

}