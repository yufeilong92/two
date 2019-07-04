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
import com.zzzh.akhalteke_shipper.adapter.MainSendAdapter
import com.zzzh.akhalteke_shipper.adapter.sendGoods.MainNewSendAdapter
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.SendGoodsInfo
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.transport.viewmodel.SendGoodsViewModel
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.PromiseDialog
import kotlinx.android.synthetic.main.fragment_sending.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 发布中...
 */
class SendingFragment : BaseFragment() {

    private val mViewModel: SendGoodsViewModel by lazy {
        ViewModelProviders.of(this).get(SendGoodsViewModel::class.java)
    }

    private var ifLoading = true // 是否需要显示加载
    lateinit var mainView: View
    //是否创建页面
    private var isCreate = false
    //页面是否可见
    private var isVisibleToUser = false

    private val infoList: MutableList<SendGoodsInfo> = mutableListOf()
    private var sendAdapter: MainNewSendAdapter? = null

    private var deleteDialog: PromiseDialog? = null

    private var page = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sending, null).also { mainView = it }
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
        page = 0
        toGetData(0)
    }

    /**
     * 初始化界面，初始化下拉刷新
     */
    private fun initView() {
        fsend_recycle.layoutManager = LinearLayoutManager(mContext)

        fsend_refresh.apply {
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
                    if(ifLoading){
                        showProgress()
                    }
                }
                1 -> {
                    dismissProgress()
                    fsend_refresh.finishLoadMore()
                    fsend_refresh.finishRefresh()
                }
            }
        })
        //列表数据获取监听
        mViewModel.infos.observe(this, Observer {
            ifLoading = false
            if (it!!.pageInfo.page == 0) {
                infoList.clear()
            }
            if (it!!.pageInfo.last) {
                fsend_refresh.setEnableLoadMore(false)
            } else {
                fsend_refresh.setEnableLoadMore(true)
            }
            page = it!!.pageInfo.page
            it!!.list?.apply {
                for (info in it!!.list) {
                    infoList.add(info)
                }
            }
            initAdapter()
        })
        //删除成功返回监听
        mViewModel.deleteBack.observe(this, Observer {
            ifLoading = false
            ToastView.setToasd(mContext, "删除成功")
            toGetData(0)
        })
    }

    /**
     * 获取数据
     * @param pageNo Int
     */
    private fun toGetData(pageNo: Int) {
        if (isCreate && isVisibleToUser) {
            mViewModel.getOwnerGoods(pageNo, "1")
        }
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (sendAdapter == null) {
            val footerView = LayoutInflater.from(mContext).inflate(R.layout.cell_main_send_text, null)
            sendAdapter = MainNewSendAdapter(mContext, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                    //跳转到详情页面
                    ifLoading = true
                    toCheckCre {
                        routerTo.jumpToTransportDetail(0, infoList[position].goodsId)
                    }
                }
                addFooterView(footerView)
                setEmptyView(R.layout.cell_empty, fsend_recycle)
                setOnItemChildClickListener { adapter, view, position ->
                    val info = infoList[position]
                    when (view.id) {
                        R.id.item_send_todiver -> {//指定司机
                            //去指定司机
                            toCheckCre {
                                routerTo.jumpToDriverList(0, info.goodsId)
                            }
                        }
                        R.id.tv_item_designated_driver -> {//指定司机
                            //去指定司机
                            toCheckCre {
                                routerTo.jumpToDriverList(0, info.goodsId)
                            }
                        }
                        R.id.item_send_refresh -> {//刷新
                            //刷新，暂时没用
                            ToastView.setToasd(mContext, "刷新成功")
                        }
                        R.id.tv_item_refresh_log -> {//刷新
                            //刷新，暂时没用
                            ToastView.setToasd(mContext, "刷新成功")
                        }
                        R.id.item_send_delete -> {//删除
                            //删除该条
                            toCheckCre {
                                deleteDialog = PromiseDialog(mContext, "您确定要删除该信息？", {
                                    mViewModel.goodsDelete(info.goodsId)
                                }, {})
                                deleteDialog!!.show()
                            }
                        }
                        R.id.tv_item_deete_log -> {//删除
                            //删除该条
                            toCheckCre {
                                deleteDialog = PromiseDialog(mContext, "您确定要删除该信息？", {
                                    mViewModel.goodsDelete(info.goodsId)
                                }, {})
                                deleteDialog!!.show()
                            }
                        }
                    }
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
            MessageEvent.GOODS_DELETE,
            MessageEvent.PUBLISH_SUCCESS,
            MessageEvent.DRIVER_APPOINT-> {
                toGetData(0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ToolUtils.dismissDialog(deleteDialog)
        EventBus.getDefault().unregister(this)
    }

}