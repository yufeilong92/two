package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.DriverInfo
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.RouterTo

class DriverAdapter(val context: Context, val infoList: MutableList<DriverInfo>, temp: Int) :
    BaseQuickAdapter<DriverInfo, BaseViewHolder>(R.layout.item_driver, infoList) {

    override fun convert(helper: BaseViewHolder?, item: DriverInfo?) {
        val position = helper!!.layoutPosition
        val ddriver_header = helper!!.getView<SimpleDraweeView>(R.id.ddriver_header)
        val item_driver_button2 = helper!!.getView<TextView>(R.id.item_driver_button2)

        helper!!.apply {
            setText(R.id.ddriver_name, item!!.name)
            setText(R.id.ddriver_no, item!!.plateNumber)
            setVisible(R.id.ddriver_icon, item!!.ifDriver == "1")

            addOnClickListener(R.id.item_driver_button1)
        }
        ImageLoadingUtils.loadNetImage(ddriver_header, item!!.portrait)

        item_driver_button2.setOnClickListener {
            RouterTo(mContext).callPhone(item!!.phone)
        }
    }

}