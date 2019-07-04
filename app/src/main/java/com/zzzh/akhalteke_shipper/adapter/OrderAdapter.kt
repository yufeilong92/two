package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.OrderInfo
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.utils.ZzzhUtils

class OrderAdapter(var context: Context, var infoList: MutableList<OrderInfo>, val status: Int) :
    BaseQuickAdapter<OrderInfo, BaseViewHolder>(R.layout.item_order, infoList) {

    override fun convert(helper: BaseViewHolder?, item: OrderInfo?) {
        val position = helper!!.layoutPosition

        helper?.apply {
            setText(R.id.item_order_time, TimeUntils.getStrTime(item!!.createdTime))
            setText(
                R.id.item_order_type,
                if(ToolUtils.isEmpty(item!!.carLengthType)){
                    ZzzhUtils.setWeightAndVolume(item!!.weight, item!!.volume)
                }else{
                    item!!.carLengthType +" "+ ZzzhUtils.setWeightAndVolume(item!!.weight, item!!.volume)
                }
            )
            setText(R.id.item_order_name, item!!.carOwnerName)
            setText(R.id.item_order_no, item!!.carOwnerPlate)

            getView<TextView>(R.id.item_order_from).text =
                ToolUtils.adrSpannStr(mContext,ZzzhUtils.adrItemShow(item!!.loadAreaName, item!!.loadAddress),
                    ZzzhUtils.adrItemShow(item!!.unloadAreaName,item!!.unloadAddress))

            addOnClickListener(R.id.item_order_call)
            addOnClickListener(R.id.item_order_pay)
            addOnClickListener(R.id.item_order_sure)
            addOnClickListener(R.id.item_order_agreement)


            val headerImage = getView<SimpleDraweeView>(R.id.item_order_header)
            ImageLoadingUtils.loadNetImage(headerImage, item!!.carOwnerPortrait)

            val payButton = getView<TextView>(R.id.item_order_pay)
            val sureButton = getView<TextView>(R.id.item_order_sure)
            val agreeButton = getView<TextView>(R.id.item_order_agreement)

            val bottomView = getView<View>(R.id.item_order_bottom)
            when (item!!.status) {
                "1" -> {
                    when (item!!.ifAgreement) {//协议状态1待确认，2未发起，3拒绝，4同意
                        "1" -> {
                            setText(R.id.item_order_status, "待司机确认协议")
                            agreeButton.visibility = View.VISIBLE
                            sureButton.visibility = View.GONE
                            payButton.visibility = View.GONE
                            agreeButton.text = "待司机确认"
                        }
                        "2" -> {
                            setText(R.id.item_order_status, "待签协议")
                            agreeButton.visibility = View.VISIBLE
                            sureButton.visibility = View.GONE
                            payButton.visibility = View.GONE
                            agreeButton.text = "签订协议"
                        }
                        "3" -> {
                            setText(R.id.item_order_status, "待确认收货")
                            sureButton.visibility = View.VISIBLE
                            agreeButton.visibility = View.GONE

                            if(item!!.ifPay == "1"){
                                payButton.text = "已支付"
                            }else{
                                payButton.text = "待支付"
                            }

                            payButton.visibility = View.VISIBLE
                        }
                        "4" -> {
                            setText(R.id.item_order_status, "已拒绝协议")
                            agreeButton.visibility = View.VISIBLE
                            sureButton.visibility = View.GONE
                            agreeButton.text = "再次发起协议"
                            payButton.visibility = View.GONE
                        }
                    }
                    bottomView.visibility = View.VISIBLE
                }
                "2" -> {
                    setText(R.id.item_order_status, "已完成")
                    if (item!!.ifPay == "1") {
                        payButton.text = "待支付"
                        bottomView.visibility = View.GONE
                    } else {
                        bottomView.visibility = View.VISIBLE
                    }
                    sureButton.visibility = View.GONE
                    agreeButton.visibility = View.GONE
                    payButton.visibility = View.VISIBLE
                }
                "3" -> {
                    setText(R.id.item_order_status, "已取消")
                    bottomView.visibility = View.GONE
                }
            }
        }
    }

}