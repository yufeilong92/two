package com.zzzh.akhalteke_shipper.ui.mine.owners

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.InvoiceInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.DaoViewModel
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.activity_dao_info.*

/**
 * 道认证信息展示
 */

class DaoInfoActivity : BaseActivity() {

    private val mViewModel by lazy {7
        ViewModelProviders.of(this).get(DaoViewModel::class.java)
    }

    //道认证级别的图片
    private val daoIcons: MutableList<Int> = mutableListOf(
        R.mipmap.icon_dao03,
        R.mipmap.icon_dao_red,
        R.mipmap.icon_dao05,
        R.mipmap.icon_dao02,
        R.mipmap.icon_dao01,
        R.mipmap.icon_dao04
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dao_info)

        initViewModel()
        mViewModel.invoiceInfo()
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
        //道认证信息
        mViewModel.invoiceData.observe(this, Observer {
            fillData(it ?: return@Observer)
        })
    }

    /**
     * 填充道认证信息
     * @param info InvoiceInfo
     */
    private fun fillData(info: InvoiceInfo) {
        dao_info_view.visibility = View.VISIBLE
        invoice_name.text = info.name
        invoice_number.text = info.taxNumber
        invoice_address.text = info.address
        invoice_phone.text = info.phone
        invoice_bank.text = info.bank
        invoice_bankno.text = info.bankNumber
        invoice_remark.text = info.comments
        ImageLoadingUtils.loadNetImage(dao_simple, info.daoAddress)

        dao_info_status.setImageResource(daoIcons[ToolUtils.stringToIntM(info.daoRank) - 1])
    }

}
