package com.zzzh.akhalteke_shipper.ui.mine

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.BankInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.WalletViewModel
import kotlinx.android.synthetic.main.activity_transfer.*

/**
 * 对公转账 ，暂时弃用
 * @property mViewModel WalletViewModel
 */
class TransferActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(WalletViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        initViewModel()

        mViewModel.getCompanyAccount()
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
    }


    private fun fillData(info:BankInfo){
        transfer_bankno.text = info.bankNumber
        transfer_bankname.text = info.bank
        transfer_name.text = info.account
        transfer_remark.text = info.comment
    }

}
