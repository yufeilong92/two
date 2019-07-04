package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.OrderInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import com.zzzh.akhalteke_shipper.utils.*
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class OrderDetailActivity : BaseActivity() {

    var orderId = ""

    private val mViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(this).get(OrderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        orderId = intent.getStringExtra("order_id")

        eventBus.register(this)

        initView()
        initViewModel()
        mViewModel.orderInfo(orderId)
    }

    private fun initView() {
//        order_detail_more.setOnClickListener {
//        }
        order_detail_top.onClickTextButton { routerTo.callKeFu() }
    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })
        //运单详情
        mViewModel.orderInfo.observe(this, Observer { info ->
            info?.let {
                fillData(info)
            }
        })
        //点击操作之后返回的刷新
        mViewModel.successData.observe(this, Observer {
            mViewModel.orderInfo(orderId)
            eventBus.post(MessageEvent(MessageEvent.ORDER_CONFIRM))
        })
    }

    /**
     * 填充页面数据
     * @param info OrderInfo
     */
    private fun fillData(info: OrderInfo) {
        order_detail_view01.visibility = View.VISIBLE

        order_detail_from.text = ZzzhUtils.adrNameLoad(info.loadAreaName, info.loadAddress)
        order_detail_to.text = ZzzhUtils.adrNameLoad(info.unloadAreaName, info.unloadAddress)

        if (ToolUtils.isEmpty(info.loadTime)) {
            isShowTime(false)
        } else {
            isShowTime(true)

            if (ToolUtils.isEmpty(info.loadTime) || info.loadTime == "0") {
                order_detail_from_time.text = "暂无"
            } else {
                order_detail_from_time.text = TimeUntils.getStrTime(info.loadTime) + " 前"
            }

            if (ToolUtils.isEmpty(info.unloadTime) || info.unloadTime == "0") {
                order_detail_to_time.text = "暂无"
            } else {
                order_detail_to_time.text = TimeUntils.getStrTime(info.unloadTime) + " 前"
            }
        }

        if (ToolUtils.isEmpty(info.name)) {
            order_detail_type01.visibility = View.GONE
        } else {
            order_detail_type01.visibility = View.VISIBLE
            order_detail_type01.text = info.name
        }

        if (ToolUtils.isEmpty(info.carLengthType)) {
            order_detail_type02.visibility = View.GONE
        } else {
            order_detail_type02.visibility = View.VISIBLE
            order_detail_type02.text = info.carLengthType
        }

        if (ToolUtils.isEmpty(info.weightVolume)) {
            order_detail_type03.visibility = View.GONE
        } else {
            order_detail_type03.visibility = View.VISIBLE
            order_detail_type03.text = info.weightVolume
        }

        ImageLoadingUtils.loadNetImage(order_detail_header, info.carOwnerPortrait)
        order_detail_name.text = info.carOwnerName
        order_detail_no.text = info.carOwnerPlate

        order_detail_call.setOnClickListener { routerTo.callPhone(info.carOwnerPhone) }

        if (ToolUtils.isEmpty(info.totalMoney)) {
            order_detail_cost.text = "暂无"
        } else {
            order_detail_cost.text = "￥${info.totalMoney}"
        }

        if (info.receipt == "1") {
            order_detail_back.visibility = View.VISIBLE
            order_detail_back.setOnClickListener { routerTo.jumpToOrderReceipt(info.orderId) }
        } else {
            order_detail_back.visibility = View.GONE
        }

        order_detail_ordertime.text = TimeUntils.getStrTime(info.createdTime)
        order_detail_copy.setOnClickListener { ToolUtils.copyData(mContext, info.orderId) }

        order_detail_topro.setOnClickListener { routerTo.jumpToProtocolTo(orderInfo = info) }


        //订单状态1-运输中，2-已完成，3-已取消
        when (info.status) {
            "1" -> {//运输中
                order_detail_bottom.visibility = View.VISIBLE
                order_detail_pay.visibility = View.GONE

                when (info.ifAgreement) {//协议状态1待确认，2未发起，3同意，4拒绝
                    "1" -> {
                        isShowProtocol(true)
                        order_detail_topro.setOnClickListener { routerTo.jumpToProtocol(info.orderId) }
                        order_detail_topro.visibility = View.VISIBLE
                        order_detail_sure.visibility = View.GONE
                        order_detail_pay.visibility = View.GONE
                        order_detail_topro.text = "待司机确认"
                    }
                    "2" -> {
                        isShowProtocol(false)
                        order_detail_topro.setOnClickListener { routerTo.jumpToProtocolTo(orderInfo = info) }
                        order_detail_topro.visibility = View.VISIBLE
                        order_detail_sure.visibility = View.GONE
                        order_detail_pay.visibility = View.GONE
                        order_detail_topro.text = "签订协议"
                    }
                    "3" -> {
                        isShowProtocol(true)
                        order_detail_topro.visibility = View.GONE
                        order_detail_sure.visibility = View.VISIBLE
                        if (info.ifPay == "1") {
                            order_detail_pay.text = "已支付"
                        } else {
                            order_detail_pay.text = "支付运单"
                        }
                        order_detail_pay.visibility = View.VISIBLE
                    }
                    "4" -> {
                        isShowProtocol(true)
                        order_detail_topro.setOnClickListener {
                            routerTo.jumpToProtocolTo(
                                1,
                                orderInfo = info,
                                orderId = info.orderId
                            )
                        }
                        order_detail_topro.visibility = View.VISIBLE
                        order_detail_sure.visibility = View.GONE
                        order_detail_pay.visibility = View.GONE
                        order_detail_topro.text = "再次发起协议"
                    }
                }
                order_detail_cancel.visibility = View.VISIBLE
                order_detail_top.toSetTitleName("运输中")
            }
            "2" -> {//已完成
                isShowProtocol(true)
                if (info.ifPay == "1") {
                    order_detail_bottom.visibility = View.GONE
                } else {
                    order_detail_bottom.visibility = View.VISIBLE
                    order_detail_pay.visibility = View.VISIBLE
                    order_detail_sure.visibility = View.GONE
                    order_detail_topro.visibility = View.GONE
                    order_detail_cancel.visibility = View.GONE
                    order_detail_pay.setOnClickListener { routerTo.jumpToOrderCancel(info.orderId) }
                }
                order_detail_top.toSetTitleName("已完成")
            }
            "3" -> {//已取消
                isShowProtocol(false)
                order_detail_bottom.visibility = View.GONE
                order_detail_top.toSetTitleName("已取消")
            }
        }
        order_detail_pro.setOnClickListener { routerTo.jumpToProtocol(info.orderId) }
        order_detail_cancel.setOnClickListener { routerTo.jumpToOrderCancel(info.orderId) }
        order_detail_pay.setOnClickListener {
            if (info.ifPay == "2") {
//                routerTo.jumpToOrderPay(info.orderId, info.totalMoney)
                routerTo.jumpToOrderPay(info.loadAreaName,info.unloadAreaName,info.createdTime,info.orderId, info.totalMoney)
            }
        }
        order_detail_sure.setOnClickListener { toCheckInfo { mViewModel.orderConfirm(info.orderId) } }

        order_detail_orderno.text = info.orderId
    }

    /**
     * 是否显示发货卸货时间
     * @param ishow Boolean true 显示，false不显示
     */
    private fun isShowTime(ishow: Boolean) {
        if (ishow) {
            order_detail_from_label.visibility = View.VISIBLE
            order_detail_from_time.visibility = View.VISIBLE
            order_detail_to_label.visibility = View.VISIBLE
            order_detail_to_time.visibility = View.VISIBLE
        } else {
            order_detail_from_label.visibility = View.GONE
            order_detail_from_time.visibility = View.GONE
            order_detail_to_label.visibility = View.GONE
            order_detail_to_time.visibility = View.GONE
        }
    }


    private fun isShowFee(isShow: Boolean) {
        if (isShow) {
            order_detail_fee.visibility = View.VISIBLE
        } else {
            order_detail_fee.visibility = View.GONE
        }
    }

    /**
     * 是否显示协议
     * @param isShow Boolean  true显示，false不显示
     */
    private fun isShowProtocol(isShow: Boolean) {
        if (isShow) {
            order_detail_pro.visibility = View.VISIBLE
        } else {
            order_detail_pro.visibility = View.GONE
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.ORDER_CANCEL, MessageEvent.ORDER_PAY, MessageEvent.PROTOCOLTO_SUCCESS -> {
                mViewModel.orderInfo(orderId)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }

}
