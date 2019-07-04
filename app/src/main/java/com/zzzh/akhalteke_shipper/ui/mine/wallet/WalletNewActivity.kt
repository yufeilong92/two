package com.zzzh.akhalteke_shipper.ui.mine.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.WalletAdapter
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.WalletInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.WalletViewModel
import kotlinx.android.synthetic.main.activity_wallet_new2.*
import kotlinx.android.synthetic.main.gm_title_layout.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.mine.wallet
 * @Package com.zzzh.akhalteke_shipper.ui.mine.wallet
 * @Email : yufeilong92@163.com
 * @Time :2019/5/23 17:16
 * @Purpose : 我的钱包
 */
class WalletNewActivity : BaseActivity() {
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
        setContentView(R.layout.activity_wallet_new2)
        gm_tv_activity_title.text = "我的钱包"
        initView()
        initViewModel()
        mViewModel.walletAccount(0)
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        rlv_money_wallet_recycle.layoutManager = LinearLayoutManager(mContext)
        srl_wallet_refresh.apply {
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
        //充值
        tv_wallect_recharge.setOnClickListener { toCheckInfo { routerTo.jumpTo(TransferInfoActivity::class.java) } }
//        //提现
        tv_wallect_withdraw_deposit.setOnClickListener { routerTo.jumpToWithdraw() }
        //银行卡管理
        tv_wallect_add_card.setOnClickListener { routerTo.jumpToBankManager() }
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
                val typeface = Typeface.createFromAsset(assets, "impact_font.ttf")
                tv_wallect_remaining_sum.typeface = typeface
                tv_wallect_remaining_sum.text = this.usableAmount
                val infos = it.list
                if (page == 0) {
                    infoList.clear()
                }
                if (it.pageInfo.last) {
                    srl_wallet_refresh.setEnableLoadMore(false)
                } else {
                    srl_wallet_refresh.setEnableLoadMore(true)
                }
                page = it.pageInfo.page
                infos.apply {
                    for (info in infos) {
                        infoList.add(info)
                    }
                }
                initAdapter()

                if (infoList.size == 0) {
                    srl_wallet_refresh.setEnableLoadMore(false)
                }
            }
        })
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (mAdapter == null) {
            rlv_money_wallet_recycle.adapter = WalletAdapter(mContext!!, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                }
                mAdapter = this
                setEmptyView(R.layout.cell_empty, rlv_money_wallet_recycle)
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

    override fun onStart() {
        super.onStart()
        eventBus.register(this)
    }

    override fun onStop() {
        super.onStop()
        eventBus.unregister(this)
    }

}
