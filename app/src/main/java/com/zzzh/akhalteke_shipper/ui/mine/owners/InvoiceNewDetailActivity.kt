package com.zzzh.akhalteke_shipper.ui.mine.owners

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.InvoiceInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.InvoiceViewModel
import kotlinx.android.synthetic.main.activity_invoice_new_detail.*
import kotlinx.android.synthetic.main.gm_title_layout.*

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.mine.owners
 * @Package com.zzzh.akhalteke_shipper.ui.mine.owners
 * @Email : yufeilong92@163.com
 * @Time :2019/5/31 11:55
 * @Purpose :已申请详情
 */
class InvoiceNewDetailActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(InvoiceViewModel::class.java)
    }

    var invoiceId = ""//发票id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_new_detail)

        invoiceId = intent.getStringExtra("invoice_id")

        gm_tv_activity_title.text="已申请详情"
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
        nsv_root.visibility = View.VISIBLE
        tv_invoice_detail_hear_content.text = info.name
        tv_invoice_detail_tax_content.text = info.taxNumber
        tv_invoice_detail_address_content.text = info.address
        tv_invoice_detail_phone_content.text = info.phone
        tv_invoice_detail_bank_name_content.text = info.bank
        tv_invoice_detail_bank_account_content.text = info.bankNumber
        tv_invoice_detail_infom_content.text = info.comments
        tv_invoice_detail_money_content.text = "￥" + info.cost

        tv_invoice_detail_addressee.text = info.receiverName
        tv_invoice_detail_address_phone.text = info.receiverPhone
        tv_invoice_detail_consignee.text = info.receiverAddress

        when (info.status) {
            "1" -> {
                progressStep(1)

            }
            "2" -> {
                progressStep(2)

                tv_invoice_detail_dhl.text = info.courierCompany
                tv_invoice_detail_expressnumber.text = info.courierNumber
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
//                invoice_detail_send.visibility = View.GONE

                iv_circle_one.setBackgroundResource(R.drawable.circle_red)
                iv_cicle_one_make_content.setBackgroundResource(R.drawable.circle_qian_yellow)
                iv_circle_one_make.setBackgroundResource(R.color.address_sbg)
                cb_circle_two.isChecked=true
                cb_cicle_two_make_content.isChecked=false
            }
            1 -> {
//                invoice_detail_send.visibility = View.VISIBLE
                iv_circle_one.setBackgroundResource(R.drawable.circle_red)
                iv_cicle_one_make_content.setBackgroundResource(R.drawable.circle_red)
                iv_circle_one_make.setBackgroundResource(R.color.red_phone)
                cb_circle_two.isChecked=true
                cb_cicle_two_make_content.isChecked=true
            }
        }
    }
}
