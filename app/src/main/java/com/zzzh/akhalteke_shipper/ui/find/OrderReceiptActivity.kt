package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.ReceiptAdapter
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.activity_order_receipt.*

/**
 * 回单
 */
class OrderReceiptActivity : BaseActivity() {

    var orderId = ""//订单ID

    private var mAdapter: ReceiptAdapter? = null//适配器
    private var infoList: ArrayList<StringInfo> = arrayListOf()//列表

    private val mViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(this).get(OrderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_receipt)

        orderId = intent.getStringExtra("order_id")

        initViewModel()
        initView()
        mViewModel.orderReceipt(orderId)
    }

    /**
     * 初始化界面
     */
    private fun initView(){
        receipt_recycle.layoutManager = GridLayoutManager(mContext, 3)
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
        //列表数据返回
        mViewModel.receiptList.observe(this, Observer {
            infoList = it!!
            initAdapter()
        })
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (mAdapter == null) {
            receipt_recycle.adapter = ReceiptAdapter(mContext!!,infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                    routerTo.jumpToShowImage(position,infoList)
                }
                mAdapter = this
            }
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

}
