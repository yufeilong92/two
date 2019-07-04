package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.OrderInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.custom.SlideLockView
import kotlinx.android.synthetic.main.activity_order_new_detail.*
import kotlinx.android.synthetic.main.gm_title_layout_right.*
import kotlinx.android.synthetic.main.gm_transport_address_layout.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.find
 * @Package com.zzzh.akhalteke_shipper.ui.find
 * @Email : yufeilong92@163.com
 * @Time :2019/5/24 17:35
 * @Purpose :新的运单详情
 */

class OrderNewComplateDetailActivity : BaseActivity() {

    var orderId = ""
    private val mViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(this).get(OrderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_new_complate_detail)

        orderId = intent.getStringExtra("order_id")

        initCreateView()
        initViewModel()
        mViewModel.orderInfo(orderId)
    }

    override fun onRestart() {
        super.onRestart()
        mViewModel.orderInfo(orderId)
    }

    fun initCreateView() {
//        gm_tv_activity_right_title.setOnClickListener { routerTo.callKeFu() }
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
        rl_order_detail_root.visibility = View.VISIBLE
//        tv_order_detail_city.text = ZzzhUtils.adrNameLoad(info.loadAreaName, info.loadAddress)
//        tv_order_detail_city.text = ZzzhUtils.adrNameLoad(info.unloadAreaName, info.unloadAddress)
        tv_order_detail_city.text=info.loadAreaName
        tv_order_detail_city_address.text=info.loadAddress
        tv_order_detail_uncity.text=info.unloadAreaName
        tv_order_detail_uncity_unaddress.text=info.unloadAddress

        if (ToolUtils.isEmpty(info.loadTime)) {
//            isShowTime(false)
            isShowLoadTime(false)
            isShowUnLoadTime(false)
        } else {
//            isShowTime(true)
            if (ToolUtils.isEmpty(info.loadTime) || info.loadTime == "0") {
                tv_order_detail_time.text = "暂无"
                isShowLoadTime(false)
            } else {
                tv_order_detail_time.text = TimeUntils.getStrTime(info.loadTime) + " 前"
                isShowLoadTime(true)
            }

            if (ToolUtils.isEmpty(info.unloadTime) || info.unloadTime == "0") {
                tv_order_detail_untime.text = "暂无"
                isShowUnLoadTime(false)
            } else {
                tv_order_detail_untime.text = TimeUntils.getStrTime(info.unloadTime) + " 前"
                isShowUnLoadTime(true)
            }
        }
        val mLists = mutableListOf<String>()

        if (!ToolUtils.isEmpty(info.name)) {
            mLists.add(info.name)
        }
        if (!ToolUtils.isEmpty(info.carLengthType)) {
            mLists.add(info.carLengthType)
        }
        if (!ToolUtils.isEmpty(info.weightVolume)) {
            mLists.add(info.weightVolume)
        }
        addDetailTag(mLists)

        ImageLoadingUtils.loadNetImage(sdv_order_detail, info.carOwnerPortrait)
        tv_order_detail_name.text = info.carOwnerName
        tv_order_detail_plate.text = info.carOwnerPlate

        iv_order_detail_phone.setOnClickListener { routerTo.callPhone(info.carOwnerPhone) }
        //支付状态
        if (info.ifPay == "1") {
            tv_order_detail_pay_status.text = "已支付"
        } else {
            tv_order_detail_pay_status.text = "未支付"
            tv_order_detail_pay_status.setOnClickListener {//支付点击
                when (info.status){//2完成 3取消
                    "3"->{

                    }
                    "2"->{
                        //                routerTo.jumpToOrderPay(info.orderId, info.totalMoney)
                        routerTo.jumpToOrderPay(info.loadAreaName,info.unloadAreaName,info.createdTime,info.orderId, info.totalMoney)

                    }
                }

            }
        }


        if (ToolUtils.isEmpty(info.totalMoney)) {
            tv_order_detail_money.text = "暂无"
            //隐藏金钱
            isShowMoney(false)
            //隐藏支付状态
            isShowPayStatus(false)
        } else {
            tv_order_detail_money.text = "￥${info.totalMoney}"
            //显示金钱
            isShowMoney(true)
            //显示支付状态
            isShowPayStatus(true)
        }

        if (info.receipt == "1") {//回单
            rl_order_detail_receipt.visibility = View.VISIBLE
            rl_order_detail_receipt.setOnClickListener { routerTo.jumpToOrderReceipt(info.orderId) }
        } else {
            rl_order_detail_receipt.visibility = View.GONE
        }

        tv_order_detail_ordertime.text = TimeUntils.getStrTime(info.createdTime)
        tv_order_detail_copy.setOnClickListener { ToolUtils.copyData(mContext, info.orderId) }

        tv_order_detail_topro.setOnClickListener { routerTo.jumpToProtocolTo(orderInfo = info) }


        //订单状态1-运输中，2-已完成，3-已取消
        when (info.status) {
            "1" -> {//运输中
                ll_order_detail_bottom.visibility = View.VISIBLE
                tv_order_detail_pay.visibility = View.GONE

                when (info.ifAgreement) {//协议状态1待确认，2未发起，3同意，4拒绝
                    "1" -> {
                        isShowProtocol(true)
                        tv_order_detail_topro.setOnClickListener { routerTo.jumpToProtocol(info.orderId) }
                        tv_order_detail_topro.visibility = View.VISIBLE
                        tv_order_detail_sure.visibility = View.GONE
                        tv_order_detail_pay.visibility = View.GONE
                        tv_order_detail_topro.text = "待司机确认"
                        isShowSure(false)
                    }
                    "2" -> {
                        isShowProtocol(false)
                        tv_order_detail_topro.setOnClickListener { routerTo.jumpToProtocolTo(orderInfo = info) }
                        tv_order_detail_topro.visibility = View.VISIBLE
                        tv_order_detail_sure.visibility = View.GONE
                        tv_order_detail_pay.visibility = View.GONE
                        tv_order_detail_topro.text = "签订协议"
                        isShowSure(false)
                    }
                    "3" -> {
                        isShowProtocol(true)
                        tv_order_detail_topro.visibility = View.GONE
                        tv_order_detail_sure.visibility = View.VISIBLE
                        if (info.ifPay == "1") {
                            tv_order_detail_pay.text = "已支付"
                        } else {
                            tv_order_detail_pay.text = "支付运单"
                        }
                        tv_order_detail_pay.visibility = View.VISIBLE
                        isShowSure(true)
                    }
                    "4" -> {
                        isShowProtocol(true)
                        tv_order_detail_topro.setOnClickListener {
                            routerTo.jumpToProtocolTo(
                                1,
                                orderInfo = info,
                                orderId = info.orderId
                            )
                        }
                        tv_order_detail_topro.visibility = View.VISIBLE
                        tv_order_detail_sure.visibility = View.GONE
                        tv_order_detail_pay.visibility = View.GONE
                        tv_order_detail_topro.text = "再次发起协议"
                        isShowSure(false)
                    }
                }
                tv_order_detail_cancel.visibility = View.VISIBLE
                gm_tv_activity_title.text = "运输中"
            }
            "2" -> {//已完成
                isShowProtocol(true)
                if (info.ifPay == "1") {
                    ll_order_detail_bottom.visibility = View.GONE
                } else {
                    ll_order_detail_bottom.visibility = View.VISIBLE
                    tv_order_detail_pay.visibility = View.VISIBLE
                    tv_order_detail_sure.visibility = View.GONE
                    tv_order_detail_topro.visibility = View.GONE
                    tv_order_detail_cancel.visibility = View.GONE
                    tv_order_detail_pay.setOnClickListener { routerTo.jumpToOrderCancel(info.orderId) }
                }
                gm_tv_activity_title.text = "已完成"
            }
            "3" -> {//已取消
                isShowProtocol(false)
                ll_order_detail_bottom.visibility = View.GONE
                gm_tv_activity_title.text = "已取消"
            }
        }
        rl_order_detail_freight.setOnClickListener { routerTo.jumpToProtocol(info.orderId) }
        tv_order_detail_cancel.setOnClickListener { routerTo.jumpToOrderCancel(info.orderId) }
        tv_order_detail_pay.setOnClickListener {
            if (info.ifPay == "2") {
//                routerTo.jumpToOrderPay(info.orderId, info.totalMoney)
                routerTo.jumpToOrderPay(info.loadAreaName,info.unloadAreaName,info.createdTime,info.orderId, info.totalMoney)

            }
        }
        tv_order_detail_sure.setOnClickListener { toCheckInfo { mViewModel.orderConfirm(info.orderId) } }
        //滑动确认收货
        sli.setmSlippingListenter(object :SlideLockView.Slipping{
            override fun startSlipping() {
            }
            override fun endSlipping() {
                toCheckInfo { mViewModel.orderConfirm(info.orderId) }
            }
        })
        tv_order_detail_orderid.text = info.orderId
    }

    /**
     * 是否显示金钱
     */
    private fun isShowMoney(isShow: Boolean) {
        tv_order_detail_money.visibility = if (isShow) View.VISIBLE else View.GONE
    }
    /**
     * 是否显示支付状态
     */
    private fun isShowPayStatus(ishow: Boolean) {
        tv_order_detail_pay_status.visibility = if (ishow) View.VISIBLE else View.GONE
    }

    /**
     * 是否显示确认收货
     */
    private fun isShowSure(isShow: Boolean) {
        ll_order_detail_complate.visibility = if (isShow) View.VISIBLE else View.GONE
        ll_order_detail_bottom.visibility = if (isShow) View.GONE else View.VISIBLE
    }

    /**
     * 是否显示发货卸货时间
     * @param ishow Boolean true 显示，false不显示
     */
    private fun isShowTime(ishow: Boolean) {
        if (ishow) {
//            order_detail_from_label.visibility = View.VISIBLE
            ll_order_detail_loadd.visibility = View.VISIBLE
//            order_detail_to_label.visibility = View.VISIBLE
            ll_order_detail_unload.visibility = View.VISIBLE
        } else {
//            order_detail_from_label.visibility = View.GONE
            ll_order_detail_loadd.visibility = View.GONE
//            order_detail_to_label.visibility = View.GONE
            ll_order_detail_unload.visibility = View.GONE
        }
    }

    /**
     * 显示装货时间
     */
    private fun isShowLoadTime(isShow: Boolean){
        ll_order_detail_loadd.visibility =if (isShow) View.VISIBLE else View.GONE
    }
    /**
     * 显示卸货时间
     */
    private fun isShowUnLoadTime(isShow: Boolean){
        ll_order_detail_unload.visibility =if (isShow) View.VISIBLE else View.GONE
    }

    fun addDetailTag(datas: MutableList<String>?) {
        flw_order_detail_tag.removeAllViews()
        if (datas == null || datas.isEmpty()) {
            flw_order_detail_tag.visibility = View.GONE
            return
        }
        for (i in datas!!.indices) {
            val key = datas!![i]
            val itemvo =
                LayoutInflater.from(mContext).inflate(R.layout.item_order_detail_tag, flw_order_detail_tag, false)
            val tv = itemvo.findViewById(R.id.tv_order_detail_tag) as TextView
            val oneInt = ToolUtils.getImageDrawer(mContext)
            tv.background = oneInt
            tv.text = key
            flw_order_detail_tag.addView(itemvo)
        }
    }

    /**
     * 是否显示协议
     * @param isShow Boolean  true显示，false不显示
     */
    private fun isShowProtocol(isShow: Boolean) {
        if (isShow) {
            rl_order_detail_freight.visibility = View.VISIBLE
        } else {
            rl_order_detail_freight.visibility = View.GONE
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

    override fun onStart() {
        super.onStart()
        eventBus.register(this)
    }

    override fun onStop() {
        super.onStop()
        eventBus.unregister(this)
    }

}
