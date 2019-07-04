package com.zzzh.akhalteke_shipper.ui.mine.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.WalletAdapter
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.WalletInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.WalletViewModel
import kotlinx.android.synthetic.main.activity_wallet.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 钱包管理
 */
class WalletActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(WalletViewModel::class.java)
    }

    private var ifLoading = true // 是否需要显示加载
    private var page = 0

    //钱包转账记录
    private var mAdapter: WalletAdapter? = null
    private var infoList: MutableList<WalletInfo.WalletRecordInfo> = mutableListOf()//转账信息列表

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        eventBus.register(this)
        initView()
        initViewModel()
        mViewModel.walletAccount(0)
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        wallet_recycle.layoutManager = LinearLayoutManager(mContext)
        wallet_refresh.apply {
            setOnRefreshListener { refreshlayout ->
                mViewModel.walletAccount(0)
            }
            setOnLoadMoreListener { refreshlayout ->
                mViewModel.walletAccount(page + 1)
            }
            setEnableFooterFollowWhenLoadFinished(true)
            setEnableOverScrollDrag(true)
            setEnableRefresh(false)
        }

        //返回键
        wallet_top_back.setOnClickListener { finishBase() }
        //充值
        wallet_charge.setOnClickListener { toCheckInfo {routerTo.jumpTo(TransferInfoActivity::class.java) }}
        //转账
        wallet_withdraw.setOnClickListener { routerTo.jumpToWithdraw() }
        //银行卡管理
        wallet_bank.setOnClickListener { routerTo.jumpToBankManager() }
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
                }
            }
        })

        //钱包转账记录
        mViewModel.walletInfo.observe(this, Observer {
            ifLoading = false
            it?.apply {
                wallet_money.text = this.usableAmount
                val infos = it.list
                if (page == 0) {
                    infoList.clear()
                }
                if (it.pageInfo.last) {
                    wallet_refresh.setEnableLoadMore(false)
                } else {
                    wallet_refresh.setEnableLoadMore(true)
                }
                page = it.pageInfo.page
                infos.apply {
                    for (info in infos) {
                        infoList.add(info)
                    }
                }
                initAdapter()

                if (infoList.size == 0) {
                    wallet_refresh.setEnableLoadMore(false)
                }
            }
        })
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (mAdapter == null) {
            wallet_recycle.adapter = WalletAdapter(mContext!!, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                }
                mAdapter = this
                setEmptyView(R.layout.cell_empty, wallet_recycle)
            }
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.WITHDRAW_SUCCESS -> {
                mViewModel.walletAccount(0)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }


}
