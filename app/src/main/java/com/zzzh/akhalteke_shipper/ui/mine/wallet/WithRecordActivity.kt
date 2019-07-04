package com.zzzh.akhalteke_shipper.ui.mine.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.WithdrawRecordAdapter
import com.zzzh.akhalteke_shipper.bean.CashInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.WalletViewModel
import kotlinx.android.synthetic.main.activity_with_record.*

/**
 * 提现记录
 */
class WithRecordActivity : BaseActivity() {

    private var ifLoading = true // 是否需要显示加载
    private var page = 0

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(WalletViewModel::class.java)
    }

    //提现记录adapter
    private var mAdapter: WithdrawRecordAdapter? = null
    //提现记录列表
    private var infoList: MutableList<CashInfo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_record)

        initView()
        initViewModel()
        toGetData(0)
    }

    /**
     * 初始化页面
     */
    private fun initView() {
        withdraw_record_refresh.apply {
            setOnRefreshListener { refreshlayout ->
                toGetData(0)
            }
            setOnLoadMoreListener { refreshlayout ->
                toGetData(page + 1)
            }
            setEnableFooterFollowWhenLoadFinished(true)
            setEnableOverScrollDrag(true)
        }
    }

    private fun initViewModel() {
        withdraw_record_recycle.layoutManager = LinearLayoutManager(mContext)
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
        //提现记录
        mViewModel.cashInfos.observe(this, Observer {
            ifLoading = false
            if (it!!.pageInfo.page == 0) {
                infoList.clear()
            }
            if (it!!.pageInfo.last) {
                withdraw_record_refresh.setEnableLoadMore(false)
            } else {
                withdraw_record_refresh.setEnableLoadMore(true)
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

    private fun toGetData(pageNo: Int) {
        mViewModel.cashList(page = pageNo)
    }

    /**
     * 结束刷新
     */
    fun finishRefresh() {
        withdraw_record_refresh.finishLoadMore()
        withdraw_record_refresh.finishRefresh()
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (mAdapter == null) {
            withdraw_record_recycle.adapter = WithdrawRecordAdapter(mContext!!, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                }
                mAdapter = this
                setEmptyView(R.layout.cell_empty,withdraw_record_recycle)
            }
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

}
