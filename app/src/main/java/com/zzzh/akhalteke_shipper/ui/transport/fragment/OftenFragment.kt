package com.zzzh.akhalteke_shipper.ui.transport.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.backends.pipeline.Fresco
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.MainOftenAdapter
import com.zzzh.akhalteke_shipper.adapter.sendGoods.MainNewOftenAdapter
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.GoodsDetailInfo
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.transport.viewmodel.OftenViewModel
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.PromiseDialog
import kotlinx.android.synthetic.main.fragment_often.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 常用地址
 */
class OftenFragment : BaseFragment() {

    private val mViewModel: OftenViewModel by lazy {
        ViewModelProviders.of(this).get(OftenViewModel::class.java)
    }

    lateinit var mainView: View
    //是否第一次请求数据
    private var isFrist = true
    //是否创建页面
    private var isCreate = false
    //页面是否可见
    private var isVisibleToUser = false

    private var adapter: MainNewOftenAdapter? = null
    /**
     * 删除item 提示dialog
     */
    private var deleteDialog: PromiseDialog? = null

    private val infoList: MutableList<GoodsDetailInfo> = mutableListOf()

    private var page = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_often, null).also { mainView = it }
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
     * 初始化页面，初始化下拉刷新
     */
    private fun initView() {
        foften_recycler.layoutManager = LinearLayoutManager(mContext)

        foften_refresh.apply {
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
                    foften_refresh.finishLoadMore()
                    foften_refresh.finishRefresh()
                }
            }
        })
        //获取列表数据返回监听
        mViewModel.infos.observe(this, Observer {
            if (it!!.pageInfo.page == 0) {
                infoList.clear()
            }
            if (it!!.pageInfo.last) {
                foften_refresh.setEnableLoadMore(false)
            } else {
                foften_refresh.setEnableLoadMore(true)
            }
            page = it!!.pageInfo.page
            it!!.list?.apply {
                for (info in it!!.list) {
                    infoList.add(info)
                }
            }
            initAdapter()
        })
        //删除item
        mViewModel.deleteBack.observe(this, Observer {
            ToastView.setToasd(mContext, "删除成功")
            toGetData(0)
        })
    }

    /**
     * 删除数据
     * @param pageNo Int
     */
    private fun toGetData(pageNo: Int) {
        if (isCreate && isVisibleToUser) {
            mViewModel.getOftenList(pageNo)
        }
    }

    private fun initAdapter() {
        if (adapter == null) {
            adapter = MainNewOftenAdapter(mContext, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                    //跳转到详情页面
                    toCheckCre {
                        routerTo.jumpToTransportDetail(2, infoList[position].goodsOftenId)
                    }
                }
                setEmptyView(R.layout.cell_empty, foften_recycler)
                setOnItemChildClickListener { adapter, view, position ->
                    val info = infoList[position]
                    when (view.id) {
                        R.id.item_often_more -> {
                            //再发一单
                            toCheckCre {
                                routerTo.jumpToPublish(info)
                            }
                        }
                        R.id.tv_item_often_again -> {
                            //再发一单
                            toCheckCre {
                                routerTo.jumpToPublish(info)
                            }
                        }
                        R.id.item_often_delete -> {
                            //删除
                            toCheckCre {
                                deleteDialog = PromiseDialog(mContext, "您确定要删除该信息？", {
                                    mViewModel.goodsDeleteOften(info.goodsOftenId)
                                }, {})
                                deleteDialog!!.show()
                            }
                        }
                        R.id.tv_item_often_delete -> {//删除
                            //删除
                            toCheckCre {
                                deleteDialog = PromiseDialog(mContext, "您确定要删除该信息？", {
                                    mViewModel.goodsDeleteOften(info.goodsOftenId)
                                }, {})
                                deleteDialog!!.show()
                            }
                        }
                    }
                }
            }
            foften_recycler.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.RELOAD_OFTEN -> {
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