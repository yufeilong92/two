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
/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.adapter
 * @Package com.zzzh.akhalteke_shipper.adapter
 * @Email : yufeilong92@163.com
 * @Time :2019/5/28 15:37
 * @Purpose : 指定司机适配器
 */
class DriverNewAdapter(val context: Context, val infoList: MutableList<DriverInfo>, temp: Int) :
    BaseQuickAdapter<DriverInfo, BaseViewHolder>(R.layout.item_new_driver, infoList) {

    override fun convert(helper: BaseViewHolder?, item: DriverInfo?) {
        val position = helper!!.layoutPosition
        val ddriver_header = helper!!.getView<SimpleDraweeView>(R.id.sdv_ddriver_header)
        val item_driver_button2 = helper!!.getView<TextView>(R.id.tv_item_designated_driver)

        helper!!.apply {
            setText(R.id.tv_item_driver_name, item!!.name)
            setText(R.id.tv_item_driver_plate, item!!.plateNumber)
            //老司机
//            setVisible(R.id.iv_item_driver_olddriver, item!!.ifDriver == "1")
            //五星豪车
//            setVisible(R.id.iv_item_driver_fivecar, item!!.ifCar == "1")
             //实名认证
//            setVisible(R.id.iv_item_driver_readname, item!!.ifRealCertification == "1")
            addOnClickListener(R.id.tv_item_carrier_driver)
        }
        ImageLoadingUtils.loadNetImage(ddriver_header, item!!.portrait)

        item_driver_button2.setOnClickListener {
            RouterTo(mContext).callPhone(item!!.phone)
        }
    }


}