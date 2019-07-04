package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.InvoiceOrderInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils

class InvoiceOrderAdapter(var context: Context, var infoList: MutableList<InvoiceOrderInfo>) :
    BaseQuickAdapter<InvoiceOrderInfo, BaseViewHolder>(R.layout.item_invoice_order, infoList) {


    override fun convert(helper: BaseViewHolder?, item: InvoiceOrderInfo?) {
        val position = helper!!.layoutPosition

        helper?.apply {
            setText(R.id.item_io_path, item!!.loadAreaName)
            setText(R.id.item_io_type, item!!.carLengthType + " " + item!!.weightVolume)
            setText(R.id.item_io_no, item!!.orderId)
            setText(R.id.item_io_time, TimeUntils.getStrTime(item!!.createdTime))
            setText(R.id.item_io_money, "￥${item!!.totalAmount}")

            if (item!!.temp == 1) {
                setImageResource(R.id.item_io_radio, R.mipmap.icon_radio_red)
            } else {
                setImageResource(R.id.item_io_radio, R.mipmap.icon_radio)
            }

            getView<TextView>(R.id.item_io_path).text =
                ToolUtils.adrSpannStr(mContext, item!!.loadAreaName, item!!.unloadAreaName)
        }
    }

}