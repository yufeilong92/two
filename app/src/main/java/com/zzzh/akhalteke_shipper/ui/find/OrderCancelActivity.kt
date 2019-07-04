package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.ImageView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.activity_order_cancel.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 取消订单
 * @author zfb
 */
class OrderCancelActivity : BaseActivity() {

    var orderId = ""

    private lateinit var orderRadios: Array<ImageView>

    private var tposition: Int = 0

    private val strList = arrayOf("司机时间有变，协商取消", "因厂家原因取消订单", "因天气或其他不可抵抗力原因取消订单", "其他")

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(OrderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_cancel)

        orderId = intent.getStringExtra("order_id")

        eventBus.register(this)
        initView()
        initViewModel()
    }

    private fun initView() {
        orderRadios = arrayOf(order_cancel_radio1, order_cancel_radio2, order_cancel_radio3, order_cancel_radio4)
        initShowRadio(0)

        for (i in 0..3) {
            orderRadios[i].setOnClickListener { initShowRadio(i) }
        }

        order_cancel_submit.setOnClickListener { mViewModel.orderCancel(orderId, strList[tposition]) }
        order_cancel_top.onClickTextButton { routerTo.callKeFu() }
    }

    /**
     * 选择按钮的设置，0选上，1没选中
     * @param position Int
     */
    private fun initShowRadio(position: Int) {
        if (position != tposition) {
            orderRadios[position].setImageResource(R.mipmap.icon_radio_red)
            orderRadios[tposition].setImageResource(R.mipmap.icon_radio)
            tposition = position
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
        //取消成功
        mViewModel.successData.observe(this, Observer {
            showToast("取消成功")
            eventBus.post(MessageEvent(MessageEvent.ORDER_CANCEL))
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
