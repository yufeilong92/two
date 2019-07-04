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
import kotlinx.android.synthetic.main.activity_dao_new_info.*
import kotlinx.android.synthetic.main.gm_title_layout_transparent.*

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.mine.owners
 * @Package com.zzzh.akhalteke_shipper.ui.mine.owners
 * @Email : yufeilong92@163.com
 * @Time :2019/5/30 14:07
 * @Purpose :道认证信息
 */
class DaoNewInfoActivity : BaseActivity() {

    private val mViewModel by lazy {
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
        setContentView(R.layout.activity_dao_new_info)
        initView()
        initViewModel()
        mViewModel.invoiceInfo()
    }

    private fun initView() {
        gm_tv_activity_title.text = "道认证"
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
        nsv_dao_root.visibility = View.VISIBLE
        tv_dao_infom_rise_content.text = info.name
        tv_dao_infom_coding_content.text = info.taxNumber
        tv_dao_infom_address_content.text = info.address
        tv_dao_infom_phone_content.text = info.phone
        tv_dao_infom_bank_content.text = info.bank
        tv_dao_infom_bank_name_content.text = info.bankNumber
        tv_dao_infom_infom_content.text = info.comments
        ImageLoadingUtils.loadNetImage(iv_dao_infom_book, info.daoAddress)
//        GlideUtil.LoadImagerWithOutHear(mContext, iv_dao_infom_book, info.daoAddress)
        iv_dao_infom_logo.setImageResource(daoIcons[ToolUtils.stringToIntM(info.daoRank) - 1])
    }

}
