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
import kotlinx.android.synthetic.main.activity_invoice_new.*
import kotlinx.android.synthetic.main.gm_title_layout_transparent_right.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.mine.owners
 * @Package com.zzzh.akhalteke_shipper.ui.mine.owners
 * @Email : yufeilong92@163.com
 * @Time :2019/6/3 17:15
 * @Purpose : 发票信息
 */
class InvoiceNewActivity : BaseActivity() {

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
        setContentView(R.layout.activity_invoice_new)

        orderIds = intent.getStringExtra("order_ids")
        addressId = intent.getStringExtra("adr_id")
        receiverName = intent.getStringExtra("adr_name")
        receiverPhone = intent.getStringExtra("adr_phone")
        receiverAddress = intent.getStringExtra("adr_content")

        gm_tv_activity_title.text="发票信息"
        tv_gm_activity_right_title.text="联系客服"
        eventBus.register(this)
        initView()
        initViewModel()

        mViewModel.invoiceInfo()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        btn_invoice_alter.setOnClickListener { routerTo.jumpToCertification(1) }
        btn_invoice_sumbit.setOnClickListener { submitData() }
        tv_gm_activity_right_title.setOnClickListener { routerTo.callKeFu() }
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
        nsv_root.visibility = View.VISIBLE
        tv_invoice_hear_content.text = info.name
        tv_invoice_tax_content.text = info.taxNumber
        tv_invoice_address_content.text = info.address
        tv_invoice_phone_content.text = info.phone
        tv_invoice_bank_name_content.text = info.bank
        tv_invoice_bank_account_content.text = info.bankNumber
        tv_invoice_infom_content.text = info.comments
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
