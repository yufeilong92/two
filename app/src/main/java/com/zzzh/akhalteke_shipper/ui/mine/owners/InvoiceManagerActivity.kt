package com.zzzh.akhalteke_shipper.ui.mine.owners

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.InvoiceAdapter
import com.zzzh.akhalteke_shipper.adapter.me.InvoiceNewAdapter
import com.zzzh.akhalteke_shipper.bean.InvoiceInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.InvoiceViewModel
import kotlinx.android.synthetic.main.activity_invoice_manager.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 发票管理
 */
class InvoiceManagerActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(InvoiceViewModel::class.java)
    }

    private var ifLoading = true // 是否需要显示加载
    private var mAdapter: InvoiceNewAdapter? = null//发票adapter
    private var infoList: MutableList<InvoiceInfo> = mutableListOf()//发票列表

    private var page = 0//页码 从0开始

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_manager)
        eventBus.register(this)
        initView()
        initViewModel()
        mViewModel.invoiceList(0)
    }

    /**
     * 初始化页面信息
     */
    private fun initView() {
        invoice_manager_recycle.layoutManager = LinearLayoutManager(mContext)

        invoice_manager_refresh.apply {
            setOnRefreshListener { refreshlayout ->
                mViewModel.invoiceList(0)
            }
            setOnLoadMoreListener { refreshlayout ->
                mViewModel.invoiceList(page + 1)
            }
            setEnableFooterFollowWhenLoadFinished(true)
            setEnableOverScrollDrag(true)
        }

        invoice_manager_tip.setOnClickListener { routerTo.jumpTo(InvoiceOrderActivity::class.java) }
    }

    /**
     * 结束加载
     */
    fun finishRefresh() {
        invoice_manager_refresh.finishLoadMore()
        invoice_manager_refresh.finishRefresh()
    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, android.arch.lifecycle.Observer {
            when (it) {
                0 -> {
                    if(ifLoading){
                        showProgress()
                    }
                }
                1 -> {
                    dismissProgress()
                    finishRefresh()
                }
            }
        })

        //发票列表
        mViewModel.invoiceList.observe(this, Observer {
            ifLoading = false
            if (it!!.pageInfo.page == 0) {
                infoList.clear()
            }
            if (it!!.pageInfo.last) {
                invoice_manager_refresh.setEnableLoadMore(false)
            } else {
                invoice_manager_refresh.setEnableLoadMore(true)
            }
            page = it!!.pageInfo.page
            it!!.list?.apply {
                for (info in it!!.list) {
                    infoList.add(info)
                }
            }
            initAdapter()
        })
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (mAdapter == null) {
            invoice_manager_recycle.adapter = InvoiceNewAdapter(mContext!!, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                    routerTo.jumpToInvoiceDetail(infoList[position].invoiceRecordId)
                }
                mAdapter = this
                setEmptyView(R.layout.cell_empty, invoice_manager_recycle)
            }
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.INVOICE_ADD -> {
                mViewModel.invoiceList(0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }

}
