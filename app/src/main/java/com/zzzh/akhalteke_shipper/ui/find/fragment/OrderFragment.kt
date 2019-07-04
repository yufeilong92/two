package com.zzzh.akhalteke_shipper.ui.find.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.NewOrderAdapter
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.OrderInfo
import com.zzzh.akhalteke_shipper.bean.PageInfo
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import com.zzzh.akhalteke_shipper.view.dialog.CallCarOwnerDialog
import com.zzzh.akhalteke_shipper.view.dialog.OrderCancleDialog
import kotlinx.android.synthetic.main.fragment_order.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class OrderFragment : BaseFragment() {

    private val mViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(activity!!).get(OrderViewModel::class.java)
    }

    lateinit var mainView: View
    private var ifLoading = true // 是否需要显示加载
    private var isCreate = false//是否创建页面
    private var isVisibleToUser = false//是否显示

    private val infoList: MutableList<OrderInfo> = mutableListOf()
    private var sendAdapter: NewOrderAdapter? = null

    private var status = 0//0运输中，1已完成，2已取消
    private var page = 0  //请求页数

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order, null).also { mainView = it }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        status = arguments!!.getInt("status", 0)

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
     * 初始化页面
     */
    private fun initView() {
        forder_recycle.layoutManager = LinearLayoutManager(mContext)

        forder_refresh.apply {
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

    /**
     * 初始化viewmodel
     */
    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    if (isCreate && isVisibleToUser && ifLoading) {
                        showProgress()
                    }
                }
                1 -> {

                    dismissProgress()
                    
                    forder_refresh.finishLoadMore()
                    forder_refresh.finishRefresh()
                }
            }
        })
        //列表数据展示
        mViewModel.orderInfos.observe(this, Observer {
            it?.apply {
                if (isCreate && isVisibleToUser && this.status == (this@OrderFragment.status + 1)) {
                    ifLoading = false
                    page = PageInfo.pageLoad(this, infoList, forder_refresh)
                    initAdapter()
                    when (this@OrderFragment.status) {
                        0 -> {
                            mViewModel.label1.value = this.pageInfo.total
                        }
                        1 -> {
                            mViewModel.label2.value = this.pageInfo.total
                        }
                        2 -> {
                            mViewModel.label3.value = this.pageInfo.total
                        }
                    }
                }
            }
        })

        //操作成功之后的返回
        mViewModel.successData.observe(this, Observer {
            ifLoading = true
            toGetData(0)
        })
        //取消成功之后的返回
        mViewModel.cancelBackData.observe(this, Observer {
            ifLoading = true
            showToast("取消成功")
            toGetData(0)
        })


    }

    /**
     * 获取数据
     */
    private fun toGetData(pageNo: Int) {
        if (isCreate && isVisibleToUser) {
            mViewModel.orderList(status + 1, pageNo)
        }
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (sendAdapter == null) {
            sendAdapter = NewOrderAdapter(mContext, infoList, status).apply {
                setOnItemClickListener { adapter, view, position ->
                    toCheckCre {
                        when (status) {
                            0 -> {
                                routerTo.jumpToOrderDetail(infoList[position].orderId)
                            }
                            1 -> {
                                routerTo.jumpToOrderDetail(infoList[position].orderId)
                            }
                            2 -> {
                                routerTo.jumpToOrderDetail(infoList[position].orderId)
                            }
                        }

                    }
                }
                //空页面c
                setEmptyView(R.layout.cell_empty, forder_recycle)
                setOnItemChildClickListener { adapter, view, position ->
                    clickItem(view.id, info = infoList[position])
                }
            }
            forder_recycle.adapter = sendAdapter
        } else {
            sendAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     * item 点击事件
     */
    private fun clickItem(vId: Int, info: OrderInfo) {
        when (vId) {
            R.id.item_order_call -> {//打电话
                ifLoading = true
                toCheckInfo {
                    CallCarOwnerDialog(mContext, info.carOwnerPhone).show()
                }
            }
            R.id.item_order_pay -> {//支付
                if (info.ifPay == "2") {
                    ifLoading = true
                    toCheckInfo {
                        when (info.status) {//订单状态1-运输中，2-已完成，3-已取消
                            "1" -> {
//                                routerTo.jumpToOrderPay(info.orderId, info.totalMoney)
                                routerTo.jumpToOrderPay(
                                    info.loadAreaName,
                                    info.unloadAreaName,
                                    info.createdTime,
                                    info.orderId,
                                    info.totalMoney
                                )

                            }
                            "2" -> {
//                                routerTo.jumpToOrderPay(info.orderId, info.totalMoney)
                                routerTo.jumpToOrderPay(
                                    info.loadAreaName,
                                    info.unloadAreaName,
                                    info.createdTime,
                                    info.orderId,
                                    info.totalMoney
                                )
                            }
                            "3" -> {
                            }
                        }
                    }
                }
            }
            R.id.tv_item_order_pay_status -> {
                if (info.ifPay == "2") {
                    ifLoading = true
                    toCheckInfo {
                        when (info.status) {//订单状态1-运输中，2-已完成，3-已取消
                            "1" -> {
//                                routerTo.jumpToOrderPay(info.orderId, info.totalMoney)
                                routerTo.jumpToOrderPay(
                                    info.loadAreaName,
                                    info.unloadAreaName,
                                    info.createdTime,
                                    info.orderId,
                                    info.totalMoney
                                )

                            }
                            "2" -> {
//                                routerTo.jumpToOrderPay(info.orderId, info.totalMoney)
                                routerTo.jumpToOrderPay(
                                    info.loadAreaName,
                                    info.unloadAreaName,
                                    info.createdTime,
                                    info.orderId,
                                    info.totalMoney
                                )

                            }
                            "3" -> {
                            }
                        }
                    }
                }
            }
            R.id.tv_item_order_cancel -> {//取消订单
//                routerTo.jumpToOrderCancel(info.orderId)
                ifLoading = true
                toCheckInfo {
                    val mDialog = object : OrderCancleDialog(mContext) {
                        override fun cancelSubmit(content: String) {
                            mViewModel.orderCancel(info.orderId, content)
                        }
                    }
                    mDialog.show()
                }
            }
            R.id.item_order_sure -> {//确认收货
                ifLoading = true
                toCheckInfo {
                    mViewModel.orderConfirm(info.orderId)
                }
            }
            R.id.tv_item_order_sure_status -> {//确认收货
                ifLoading = true
                toCheckInfo {
                    mViewModel.orderConfirm(info.orderId)
                }
            }
            R.id.tv_item_order_agree_status -> {//签订协议
                ifLoading = true
                toCheckInfo {
                    when (info.ifAgreement) {
                        "2" -> {
                            routerTo.jumpToProtocolTo(orderInfo = info)
                        }
                        "4" -> {
                            routerTo.jumpToProtocolTo(1, orderInfo = info, orderId = info.orderId)
                        }
                        "1" -> {
                            routerTo.jumpToProtocol(info.orderId)
                        }
                    }
                }
            }
            R.id.item_order_agreement -> {//签订协议
                ifLoading = true
                toCheckInfo {
                    when (info.ifAgreement) {
                        "2" -> {
                            routerTo.jumpToProtocolTo(orderInfo = info)
                        }
                        "4" -> {
                            routerTo.jumpToProtocolTo(1, orderInfo = info, orderId = info.orderId)
                        }
                        "1" -> {
                            routerTo.jumpToProtocol(info.orderId)
                        }
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.DRIVER_APPOINT,
            MessageEvent.GOODS_DELETE,
            MessageEvent.ORDER_CANCEL,
            MessageEvent.ORDER_PAY,
            MessageEvent.PROTOCOLTO_SUCCESS,
            MessageEvent.ORDER_CONFIRM -> {
                ifLoading = true
                toGetData(0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
//        if (forder_refresh != null) {
//            forder_refresh.autoRefresh()
//        }
    }
}