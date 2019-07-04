package com.zzzh.akhalteke_shipper.ui.mine.owners

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.InvoiceInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.InvoiceViewModel
import kotlinx.android.synthetic.main.activity_invoice_detail.*

/**
 * 发票详情
 */
class InvoiceDetailActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(InvoiceViewModel::class.java)
    }

    var invoiceId = ""//发票id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_detail)

        invoiceId = intent.getStringExtra("invoice_id")

        initViewModel()
        mViewModel.invoiceDetail(invoiceId)
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

        //发票详情
        mViewModel.invoiceDetail.observe(this, Observer {
            fillData(it ?: return@Observer)
        })
    }

    /**
     * 填充发票信息
     * @param info InvoiceInfo
     */
    private fun fillData(info: InvoiceInfo) {
        invoice_Detail_view01.visibility = View.VISIBLE
        invoice_name.text = info.name
        invoice_number.text = info.taxNumber
        invoice_address.text = info.address
        invoice_phone.text = info.phone
        invoice_bank.text = info.bank
        invoice_bankno.text = info.bankNumber
        invoice_remark.text = info.comments
        invoice_money.text = "￥" + info.cost

        invoice_detail_man.text = info.receiverName
        invoice_detail_phone.text = info.receiverPhone
        invoice_detail_address.text = info.receiverAddress

        when (info.status) {
            "1" -> {
                progressStep(1)

            }
            "2" -> {
                progressStep(2)

                invoice_detail_company.text = info.courierCompany
                invoice_detail_no.text = info.courierNumber
            }
        }
    }

    /**
     * 发票申请进度
     * @param temp Int 1审核通过，2审核没有通过
     */
    private fun progressStep(temp: Int) {
        when (temp) {
            2 -> {
                invoice_detail_send.visibility = View.GONE

                invoice_detail_dot02.setBackgroundResource(R.drawable.dot_rouse)
                invoice_detail_line04.setBackgroundResource(R.color.address_sbg)
                invoice_detail_text07.setTextColor(resources.getColor(R.color.main_text6))
                invoice_detail_icon02.setImageResource(R.mipmap.icon_progressing)
            }
            1 -> {
                invoice_detail_send.visibility = View.VISIBLE
                invoice_detail_dot02.setBackgroundResource(R.drawable.dot_red)
                invoice_detail_line04.setBackgroundResource(R.color.main_color)
                invoice_detail_text07.setTextColor(resources.getColor(R.color.main_text3))
                invoice_detail_icon02.setImageResource(R.mipmap.icon_progressed)
            }
        }
    }
}
