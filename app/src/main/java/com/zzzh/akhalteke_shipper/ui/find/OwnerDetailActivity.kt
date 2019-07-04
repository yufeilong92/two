package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.SourceTextAdapter
import com.zzzh.akhalteke_shipper.bean.OwnerInfo
import com.zzzh.akhalteke_shipper.bean.SourceInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.GoodsViewModel
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.view.dialog.CallCarOwnerDialog
import kotlinx.android.synthetic.main.activity_owner_detail.*

/**
 * 货主简介
 */
class OwnerDetailActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(GoodsViewModel::class.java)
    }

    private var mAdapter:SourceTextAdapter? = null
    private var infoList:MutableList<SourceInfo> = mutableListOf()

    var shipperId:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_detail)

        shipperId = intent.getStringExtra("shipper_id")

        initView()
        initViewModel()
        mViewModel.getShipperById(shipperId)
    }

    private fun initView(){
        owner_detail_recycle.layoutManager = LinearLayoutManager(mContext)
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
            fillData(it?:return@Observer)
        })
    }

    private fun fillData(info:OwnerInfo){
        showView()
        ImageLoadingUtils.loadNetImage(owner_detail_header,info.portrait)
        owner_detail_name.text = info.name
        owner_detail_time.text = "${TimeUntils.getStrTime(info.registerTime)} 注册"

        if(info.ifRealCertification != "1"){
            owner_detail_icon01.setImageResource(R.mipmap.icon_dui_gray)
            owner_detail_text01.setTextColor(resources.getColor(R.color.main_text6))
        }

        if(info.ifCompanyCertification != "1"){
            owner_detail_icon02.setImageResource(R.mipmap.icon_dui_gray)
            owner_detail_text02.setTextColor(resources.getColor(R.color.main_text6))
        }

        owner_detail_compan_yname.text = info.corporateName
        owner_detail_company_address.text = info.areaAddress
        owner_detail_goodscount.text = info.goodsCount
        owner_detail_ordercount.text = info.orderCount

        owner_detail_number.text = "当天货源（${info.pageListVo.pageInfo.total}）"

        infoList = info.pageListVo.list
        initAdapter()

        owner_detail_bottom.setOnClickListener {
            toCheckInfo {
                val dialog = CallCarOwnerDialog(mContext, info.phone)
                dialog.show()
                dialog.setReplaceText()
            }
        }
    }

    private fun showView(){
        owner_detail_bottom.visibility = View.VISIBLE
        owner_detail_content.visibility = View.VISIBLE
        owner_detail_view01.visibility = View.VISIBLE
    }

    private fun initAdapter(){
        if(mAdapter == null){
            owner_detail_recycle.adapter = SourceTextAdapter(mContext!!,infoList).apply {
                val footerView = LayoutInflater.from(mContext).inflate(R.layout.cell_footer_more,null)
                addFooterView(footerView)
                setOnItemClickListener { adapter, view, position ->
                }
                mAdapter = this
                footerView.setOnClickListener {  routerTo.jumpToOwnerMoreList(shipperId) }
                setEmptyView(R.layout.cell_empty,owner_detail_recycle)
            }
        }else{
            mAdapter!!.notifyDataSetChanged()
        }
    }

}
