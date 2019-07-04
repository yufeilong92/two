package com.zzzh.akhalteke_shipper.adapter.me

import android.content.Context
import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.InvoiceInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils

class InvoiceNewAdapter(var context: Context, var infoList: MutableList<InvoiceInfo>) :
    BaseQuickAdapter<InvoiceInfo, BaseViewHolder>(R.layout.item_new_invoice, infoList) {


    override fun convert(helper: BaseViewHolder?, item: InvoiceInfo?) {
        val position = helper!!.layoutPosition

        val mCheckBok = helper.getView<CheckBox>(R.id.cb_item_invoice_status)
        helper!!.apply {
            setText(R.id.tv_item_invoice_title, item!!.name)
            setText(R.id.tv_item_invoice_time, "申请时间：" + TimeUntils.getStrTime(item!!.createdTime))
//            setText(
//                R.id.tv_item_invoice_status, if (item!!.status == "1") {
//                    "已审核"
//                } else {
//                    "待审核"
//                }
//            )
            mCheckBok.text=if (item!!.status == "1")   "已审核" else   "待审核"
            mCheckBok.isChecked=item!!.status == "1"
            setText(R.id.tv_item_invoice_money, "￥" + item!!.cost)
        }
    }

}