package com.zzzh.akhalteke_shipper.ui.mine.owners

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.InvoiceInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.InvoiceViewModel
import kotlinx.android.synthetic.main.activity_invoice.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 发票信息
 */
class InvoiceActivity : BaseActivity() {

    var orderIds = ""//订单ids
    var addressId = ""//地址id
    var receiverName = ""//接收人名字
    var receiverPhone = ""//接收人手机号
    var receiverAddress = ""//具体地址

    private var info = InvoiceInfo()

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(InvoiceViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        orderIds = intent.getStringExtra("order_ids")
        addressId = intent.getStringExtra("adr_id")
        receiverName = intent.getStringExtra("adr_name")
        receiverPhone = intent.getStringExtra("adr_phone")
        receiverAddress = intent.getStringExtra("adr_content")

        eventBus.register(this)
        initView()
        initViewModel()

        mViewModel.invoiceInfo()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        invoice_modify.setOnClickListener { routerTo.jumpToCertification(1) }
        invoice_submit.setOnClickListener { submitData() }
        invoice_link.setOnClickListener { routerTo.callKeFu() }
    }

    /**
     * 提交数据
     */
    private fun submitData() {
        info.orderIds = orderIds
        info.addressId = addressId
        info.receiverName = receiverName
        info.receiverPhone = receiverPhone
        info.receiverAddress = receiverAddress
        toCheckInfo {
            mViewModel.invoiceBill(info)
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

        //发票信息
        mViewModel.invoiceData.observe(this, Observer {
            info = it!!
            fillData(info)
        })

        //提交返回
        mViewModel.successData.observe(this, Observer {
            showToast("提交成功")
            eventBus.post(MessageEvent(MessageEvent.INVOICE_ADD))
            finishBase()
        })
    }

    /**
     * 填充数据
     * @param info InvoiceInfo
     */
    private fun fillData(info: InvoiceInfo) {
        invoice_view01.visibility = View.VISIBLE
        invoice_name.text = info.name
        invoice_number.text = info.taxNumber
        invoice_address.text = info.address
        invoice_phone.text = info.phone
        invoice_bank.text = info.bank
        invoice_bankno.text = info.bankNumber
        invoice_remark.text = info.comments
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.DAO_ADD_SUCCESS -> {
                mViewModel.invoiceInfo()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }


}
