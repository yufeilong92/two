package com.zzzh.akhalteke_shipper.ui.transport.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.MainHistoryAdapter
import com.zzzh.akhalteke_shipper.adapter.sendGoods.MainNewHistoryAdapter
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.SendGoodsInfo
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.transport.viewmodel.SendGoodsViewModel
import kotlinx.android.synthetic.main.fragment_main_history.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 发货，历史记录
 */
class HistoryFragment : BaseFragment() {

    private val mViewModel: SendGoodsViewModel by lazy {
        ViewModelProviders.of(this).get(SendGoodsViewModel::class.java)
    }


    lateinit var mainView: View
    //是否第一次请求数据
    private var isFrist = true
    //是否创建页面
    private var isCreate = false
    //页面是否可见
    private var isVisibleToUser = false
    //页面数据list
    private val infoList: MutableList<SendGoodsInfo> = mutableListOf()
    private var adapter: MainNewHistoryAdapter? = null

    private var page = 0//页码，从0开始

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_history, null).also { mainView = it }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCreate = true
        initView()
        EventBus.getDefault().register(this)
        initViewModel()

        toGetData(0)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        toGetData(0)
    }

    /**
     * 初始化界面，初始化下拉刷新
     */
    private fun initView() {
        fmhistory_recycler.layoutManager = LinearLayoutManager(mContext)

        fmhistory_refresh.apply {
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
        mViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    if (isFrist) {
                        showProgress()
                    }
                }
                1 -> {
                    isFrist = false
                    dismissProgress()
                    fmhistory_refresh.finishLoadMore()
                    fmhistory_refresh.finishRefresh()
                }
            }
        })
        //获取列表数据返回监听
        mViewModel.infos.observe(this, Observer {

            if (it!!.pageInfo.page == 0) {
                infoList.clear()
            }
            if (it!!.pageInfo.last) {
                fmhistory_refresh.setEnableLoadMore(false)
            } else {
                fmhistory_refresh.setEnableLoadMore(true)
            }
            page = it!!.pageInfo.page
            it!!.list?.apply {
                for (info in it!!.list) {
                    infoList.add(info)
                }
            }
            initAdapter()
        })
        //添加常用地址
        mViewModel.insertOftenBack.observe(this, Observer {
            ToastView.setToasd(mContext, "添加成功")
            EventBus.getDefault().post(MessageEvent(MessageEvent.RELOAD_OFTEN))
        })
    }

    /**
     * 获取页面数据
     * @param pageNo Int 页码
     */
    private fun toGetData(pageNo: Int) {
        if (isCreate && isVisibleToUser) {
            mViewModel.getOwnerGoods(pageNo, "2")
        }
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (adapter == null) {
            adapter = MainNewHistoryAdapter(mContext, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                    //点击item跳转到详情页面
                    toCheckCre {
                        routerTo.jumpToTransportDetail(1, infoList[position].goodsId)
                    }
                }
                setEmptyView(R.layout.cell_empty, fmhistory_recycler)
                setOnItemChildClickListener { adapter, view, position ->
                    when (view.id) {
                        R.id.item_mhistory_save -> {
                            //保存常用地址
                            toCheckCre {
                                isFrist = true
                                mViewModel.goodsInsertOften(infoList[position].goodsId)
                            }
                        }
                        R.id.tv_item_history_save -> {
                            //保存常用地址
                            toCheckCre {
                                isFrist = true
                                mViewModel.goodsInsertOften(infoList[position].goodsId)
                            }
                        }
                    }
                }
            }
            fmhistory_recycler.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.message == MessageEvent.MESSAGEINFO) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}