package com.zzzh.akhalteke_shipper.ui.find.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.SourceTextAdapter
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.SourceInfo
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.transport.viewmodel.DriverOwnerViewModel
import kotlinx.android.synthetic.main.fragment_sending.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TransRecordFragment : BaseFragment() {

    private val mViewModel by lazy {
        ViewModelProviders.of(activity!!).get(DriverOwnerViewModel::class.java)
    }

    lateinit var mainView: View
    private var isCreate = false//是否创建
    private var isVisibleToUser = false//是否可见

    private val infoList: MutableList<SourceInfo> = mutableListOf()
    private var sendAdapter: SourceTextAdapter? = null

    private var page = 0
    private var status = 0//0交易记录，1与我交易

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sending, null).also { mainView = it }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        isCreate = true
        status = arguments!!.getInt("status")
        initView()
        EventBus.getDefault().register(this)
        initViewModel()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
    }

    private fun initView() {
        fsend_recycle.layoutManager = LinearLayoutManager(mContext)
        fsend_refresh.setEnableLoadMore(false)
        fsend_refresh.setEnableRefresh(false)
    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, Observer<Int> {
            if (isCreate && isVisibleToUser) {
                when (it) {
                    0 -> {
                        showProgress()
                    }
                    1 -> {
                        dismissProgress()
                    }
                }
            }
        })
        //获取页面所有数据
        mViewModel.mInfo.observe(this, Observer {
            it?.apply {
                val list01 = it.orderList
                val list02 = it.orderWithShipperList
                when (status) {
                    0 -> {
                        infoList.clear()
                        for (ls in list01){
                            infoList.add(ls)
                        }
                        initAdapter()
                    }
                    1 -> {
                        infoList.clear()
                        for (ls in list02){
                            infoList.add(ls)
                        }
                        initAdapter()
                    }
                }
            }
        })
    }


    private fun initAdapter() {
        if (sendAdapter == null) {
            sendAdapter = SourceTextAdapter(mContext, infoList).apply {
                setOnItemClickListener { adapter, view, position ->

                }
                setEmptyView(R.layout.cell_empty, fsend_recycle)
                setOnItemChildClickListener { adapter, view, position ->

                }
            }
            fsend_recycle.adapter = sendAdapter
        } else {
            sendAdapter!!.notifyDataSetChanged()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}