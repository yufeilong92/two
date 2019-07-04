package com.zzzh.akhalteke_shipper.adapter.me

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.InvoiceOrderInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils

class InvoiceNewOrderAdapter(var context: Context, var infoList: MutableList<InvoiceOrderInfo>) :
    BaseQuickAdapter<InvoiceOrderInfo, BaseViewHolder>(R.layout.item_invoice_new_order, infoList) {


    override fun convert(helper: BaseViewHolder?, item: InvoiceOrderInfo?) {
        val position = helper!!.layoutPosition

        helper?.apply {
//            setText(R.id.tv_item_invoice_order_title, item!!.loadAreaName)
            setText(R.id.tv_item_invocie_order_content, item!!.carLengthType + " " + item!!.weightVolume)
            setText(R.id.tv_item_invoice_order_order, item!!.orderId)
            setText(R.id.tv_item_invoice_order_time, TimeUntils.getStrTime(item!!.createdTime))
            setText(R.id.tv_item_invocie_order_count, "￥${item!!.totalAmount}")

            if (item!!.temp == 1) {
                setImageResource(R.id.iv_item_invocie_order, R.mipmap.icon_radio_red)
            } else {
                setImageResource(R.id.iv_item_invocie_order, R.mipmap.icon_radio)
            }

            getView<TextView>(R.id.tv_item_invoice_order_title).text =
                ToolUtils.adrSpannStr(mContext, item!!.loadAreaName, item!!.unloadAreaName)
        }
    }

}