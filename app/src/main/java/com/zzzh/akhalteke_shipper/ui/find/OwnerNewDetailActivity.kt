package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.SourceTextAdapter
import com.zzzh.akhalteke_shipper.bean.OwnerInfo
import com.zzzh.akhalteke_shipper.bean.SourceInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.GoodsViewModel
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.view.dialog.CallCarOwnerDialog
import kotlinx.android.synthetic.main.activity_owner_new_detail.*
import kotlinx.android.synthetic.main.gm_title_layout_transparent.*

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.find
 * @Package com.zzzh.akhalteke_shipper.ui.find
 * @Email : yufeilong92@163.com
 * @Time :2019/5/30 10:30
 * @Purpose : 货主简介
 */
class OwnerNewDetailActivity : BaseActivity() {
    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(GoodsViewModel::class.java)
    }

    private var mAdapter: SourceTextAdapter? = null
    private var infoList: MutableList<SourceInfo> = mutableListOf()

    var shipperId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_new_detail)

        shipperId = intent.getStringExtra("shipper_id")

        gm_tv_activity_title.text = "货主简介"
        initView()
        initViewModel()
        mViewModel.getShipperById(shipperId)
    }

    private fun initView() {
        rlv_owner_detail_recycle.layoutManager = LinearLayoutManager(mContext)
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

        mViewModel.ownerInfo.observe(this, Observer {
            fillData(it ?: return@Observer)
        })
    }

    private fun fillData(info: OwnerInfo) {
        showView()
        ImageLoadingUtils.loadNetImage(sdv_owner_detail_header, info.portrait)
        tv_owner_detail_name.text = info.name
        tv_owner_detail_create_time.text = "${TimeUntils.getStrTime(info.registerTime)} 注册"
        iv_owner_detail_realname.visibility = if (info.ifRealCertification != "1") View.GONE else View.VISIBLE
        iv_owner_detail_dao.visibility=if (info.ifCompanyCertification != "1") View.GONE else View.VISIBLE
        isShowTextView(TextUtils.isEmpty(info.corporateName),tv_owner_detail_company)
        tv_owner_detail_company.text = info.corporateName
        isShowTextView(TextUtils.isEmpty(info.areaAddress),tv_owner_detail_address)
        tv_owner_detail_address.text = info.areaAddress
        tv_owner_detail_send_number.text = info.goodsCount
        tv_owner_detail_deal_number.text = info.orderCount

        tv_owner_detail_title3.text = "${info.goodsList!!.size}"

        infoList = info.goodsList!!
        initAdapter()

        tv_owner_detail_it.setOnClickListener {
            toCheckInfo {
                val dialog = CallCarOwnerDialog(mContext, info.phone)
                dialog.show()
                dialog.setReplaceText()
            }
        }
    }

    private fun isShowTextView(isShow:Boolean,tv:TextView){
        tv.visibility=if (isShow)View.GONE else View.VISIBLE
    }
    private fun showView() {
        tv_owner_detail_it.visibility = View.VISIBLE
        rl_owner_detail_rlv.visibility = View.VISIBLE
        rl_owner_detail_all.visibility = View.VISIBLE
    }

    private fun initAdapter() {
        if (mAdapter == null) {
            rlv_owner_detail_recycle.adapter = SourceTextAdapter(mContext!!, infoList).apply {
//                val footerView = LayoutInflater.from(mContext).inflate(R.layout.cell_footer_more, null)
//                addFooterView(footerView)
                setOnItemClickListener { adapter, view, position ->
                }
                mAdapter = this
//                footerView.setOnClickListener { routerTo.jumpToOwnerMoreList(shipperId) }
                setEmptyView(R.layout.cell_empty, rlv_owner_detail_recycle)
            }
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

}
