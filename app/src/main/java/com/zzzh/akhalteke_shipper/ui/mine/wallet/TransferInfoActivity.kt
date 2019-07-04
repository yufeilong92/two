package com.zzzh.akhalteke_shipper.ui.mine.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.TransferAdapter
import com.zzzh.akhalteke_shipper.bean.BankInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.WalletViewModel
import kotlinx.android.synthetic.main.activity_transfer_info.*

/**
 * 转账信息
 */
class TransferInfoActivity : BaseActivity() {

    private var ifLoading = true // 是否需要显示加载
    private var mAdapter: TransferAdapter? = null//转账adapter
    private var infoList: MutableList<BankInfo> = mutableListOf()//转账列表

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(WalletViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_info)

        initView()
        initViewModel()
        mViewModel.getCompanyAccount()
    }

    /**
     * 初始化页面
     */
    private fun initView() {
        transfer_info_recycle.layoutManager = LinearLayoutManager(mContext)

        transfer_info_refresh.apply {
            setOnRefreshListener { refreshlayout ->
                mViewModel.getCompanyAccount()
            }
            setOnLoadMoreListener { refreshlayout ->
            }
            setEnableFooterFollowWhenLoadFinished(true)
            setEnableOverScrollDrag(true)
            setEnableLoadMore(false)
        }
    }

    /**
     * 结束刷新
     */
    fun finishRefresh() {
        transfer_info_refresh.finishLoadMore()
        transfer_info_refresh.finishRefresh()
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

        //转账信息返回监听
        mViewModel.bankInfos.observe(this, Observer {
            ifLoading = false
            infoList.clear()
            it?.apply {
                for(info in it){
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
            transfer_info_recycle.adapter = TransferAdapter(mContext!!, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                }
                mAdapter = this
            }
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }
}
