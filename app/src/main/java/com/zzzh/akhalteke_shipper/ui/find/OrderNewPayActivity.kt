package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.activity_order_new_pay.*
import kotlinx.android.synthetic.main.gm_title_layout_transparent.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.find
 * @Package com.zzzh.akhalteke_shipper.ui.find
 * @Email : yufeilong92@163.com
 * @Time :2019/5/31 18:34
 * @Purpose :支付界面
 */
class OrderNewPayActivity : BaseActivity() {

    var startAddress = ""//开始时间
    var endAddress = ""//结束时间
    var orderId = ""//订单id
    var orderMoney = ""//订单金额
    var createtime=""//订单创建时间

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(OrderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startAddress = intent.getStringExtra("start_address")
        endAddress = intent.getStringExtra("end_address")
        orderId = intent.getStringExtra("order_id")
        orderMoney = intent.getStringExtra("order_money")
        createtime = intent.getStringExtra("create_time")

        setContentView(R.layout.activity_order_new_pay)
        gm_tv_activity_title.text="订单支付"
        eventBus.register(this)
        initViewModel()
        initView()
    }

    private fun initView(){
        tv_order_pay_number.text=orderId
        tv_order_pay_city.text=  ToolUtils.adrSpannStr(mContext, startAddress, endAddress)
        tv_order_pay_time.text= TimeUntils.getStrTime(createtime)
        tv_order_pay_count.text = "¥$orderMoney"
        tv_order_detail_pay_count.text = "¥$orderMoney"

        tv_order_pay_payment.setOnClickListener {
            toCheckInfo {
                mViewModel.orderPayment(orderId)
            }
        }

    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, Observer {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })

        //支付成功返回数据
        mViewModel.successData.observe(this, Observer {
//            showToast("支付成功")
//            eventBus.post(MessageEvent(MessageEvent.ORDER_PAY))
//            finishBase()
        })
        mViewModel.successPayData.observe(this, Observer {
            showToast("支付成功")
            eventBus.post(MessageEvent(MessageEvent.ORDER_PAY))
            finishBase()
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }

}
