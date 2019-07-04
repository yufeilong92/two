package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.activity_order_pay.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class OrderPayActivity : BaseActivity() {


    var orderId = ""//订单id
    var orderMoney = ""//订单金额

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(OrderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_pay)

        orderId = intent.getStringExtra("order_id")
        orderMoney = intent.getStringExtra("order_money")

        eventBus.register(this)
        initViewModel()
        initView()
    }

    private fun initView(){
        order_pay_total.text = "订单总价：¥$orderMoney"
        order_pay_money.text = "¥$orderMoney"

        order_pay_submit.setOnClickListener {
            toCheckInfo {mViewModel.orderPayment(orderId)}
        }
    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, android.arch.lifecycle.Observer {
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
