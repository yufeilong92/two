package com.zzzh.akhalteke_shipper.ui.mine.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.BankCardAdapter
import com.zzzh.akhalteke_shipper.bean.BankCardInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.BankViewModel
import com.zzzh.akhalteke_shipper.utils.BANK_MANAGER_CODE
import com.zzzh.akhalteke_shipper.view.dialog.PromiseDialog
import kotlinx.android.synthetic.main.activity_bank_manager.*
import kotlinx.android.synthetic.main.gm_title_layout_right.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 银行卡管理
 */
class BankManagerActivity : BaseActivity() {

    private val mViewModel: BankViewModel by lazy {
        ViewModelProviders.of(this).get(BankViewModel::class.java)
    }

    var entryTemp = 0//0管理，1选择

    private var ifLoading = true // 是否需要显示加载
    //银行卡adapter
    private var mAdapter: BankCardAdapter? = null
    private var infoList: MutableList<BankCardInfo> = mutableListOf()//银行卡列表

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_manager)

        entryTemp = intent.getIntExtra("entry_temp",0)

        gm_tv_activity_title.text = "银行卡管理"
        gm_tv_activity_right_title.text = "添加银行卡"
        isShowAddBank(false, false)
        eventBus.register(this)
        initView()
        initViewModel()
        mViewModel.bankCardList()
    }

    /**
     * 显示状态栏添加银行卡
     * @param isShow 状态栏
     * @param isShowDown 底部
     */
    private fun isShowAddBank(isShow: Boolean, isShowDown: Boolean) {
        gm_tv_activity_right_title.visibility = if (isShow) View.VISIBLE else View.GONE
        bank_card_add.visibility = if (isShowDown) View.VISIBLE else View.GONE
    }

    /**
     * 初始化页面
     */
    private fun initView() {
        bank_card_recycle.layoutManager = LinearLayoutManager(mContext)

        bank_card_refresh.apply {
            setOnRefreshListener { refreshlayout ->
                mViewModel.bankCardList()
            }
            setOnLoadMoreListener { refreshlayout ->
            }
            setEnableFooterFollowWhenLoadFinished(true)
            setEnableOverScrollDrag(true)
            setEnableLoadMore(false)
        }

        /**
         * 添加银行卡
         */
        bank_card_add.setOnClickListener { routerTo.jumpTo(BankAddActivity::class.java) }
        gm_tv_activity_right_title.setOnClickListener { routerTo.jumpTo(BankAddActivity::class.java) }
    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    if (ifLoading) {
                        showProgress()
                    }
                }
                1 -> {
                    dismissProgress()
                    bank_card_refresh.finishLoadMore()
                    bank_card_refresh.finishRefresh()
                }
            }
        })

        //银行卡信息
        mViewModel.bankInfos.observe(this, Observer {
            ifLoading = false
            infoList.clear()
            for (info in it!!) {
                infoList.add(info)
            }
            initAdapter()
        })

        //删除成功数据
        mViewModel.successData.observe(this, Observer {
            ifLoading = false
            showToast("删除成功")
            mViewModel.bankCardList()
        })
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (mAdapter == null) {
            if (infoList == null || infoList.isEmpty()) {
                isShowAddBank(true, false)
            } else {
                isShowAddBank(false, true)
            }
            val adapter = BankCardAdapter(mContext!!, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                    if (entryTemp == 1) {//选择银行卡
                        val intentBack = Intent()
                        intentBack.putExtra("bank_info", infoList[position])
                        setResult(BANK_MANAGER_CODE, intentBack)
                        finishBase()
                    }
                }
                mAdapter = this
                setOnItemChildClickListener { adapter, view, position -> clickItem(view.id, infoList[position]) }
                setEmptyView(R.layout.addbank_empty, bank_card_recycle)
            }
//            adapter.bindToRecyclerView(bank_card_recycle)
//            adapter.setEmptyView(R.mipmap.ic_addbank_manager_empty)
            bank_card_recycle.adapter = adapter
        } else {
            if (infoList == null || infoList.isEmpty()) {
                isShowAddBank(true, false)
            } else {
                isShowAddBank(false, true)
            }
            mAdapter!!.notifyDataSetChanged()

        }
    }

    /**
     * item点击事件，删除银行卡
     * @param vId Int view id
     * @param info BankCardInfo 银行卡信息
     */
    private fun clickItem(vId: Int, info: BankCardInfo) {
        when (vId) {
            R.id.item_bank_delete -> {
                PromiseDialog(mContext, "您确定要删除此银行卡？", {
                    ifLoading = true
                    mViewModel.bankCardDelete(info.id)
                }, {}).show()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.BANK_CARD_ADD -> {
                mViewModel.bankCardList()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }

}
