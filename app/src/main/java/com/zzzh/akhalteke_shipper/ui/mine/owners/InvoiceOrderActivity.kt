package com.zzzh.akhalteke_shipper.ui.mine.owners

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.InvoiceOrderAdapter
import com.zzzh.akhalteke_shipper.adapter.me.InvoiceNewOrderAdapter
import com.zzzh.akhalteke_shipper.bean.AddressInfo
import com.zzzh.akhalteke_shipper.bean.InvoiceOrderInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.address.AddressManagerActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.AddressViewModel
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.InvoiceViewModel
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.activity_invoice_order.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 请选择要开票的订单
 */
class InvoiceOrderActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(InvoiceViewModel::class.java)
    }
    private val adrViewModel by lazy {
        ViewModelProviders.of(this).get(AddressViewModel::class.java)
    }

    private var ifLoading = true // 是否需要显示加载
    private var page = 0//页码，从0开始
    private var adrInfo = AddressInfo()

    private var mAdapter: InvoiceNewOrderAdapter? = null//发票订单adapter
    private var infoList: MutableList<InvoiceOrderInfo> = mutableListOf()//发票订单列表

    private var tPosition = 0
    private val orderStrs: MutableList<String> = mutableListOf()//订单id集合

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_order)
        eventBus.register(this)
        initView()
        initViewModel()
        mViewModel.invoiceOrderList(0)
        adrViewModel.addressList()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        addressPD(false)
        invoice_order_recycle.layoutManager = LinearLayoutManager(mContext)
        invoice_order_refresh.apply {
            setOnRefreshListener { refreshlayout ->
                mViewModel.invoiceOrderList(0)
            }
            setOnLoadMoreListener { refreshlayout ->
                mViewModel.invoiceOrderList(page + 1)
            }
            setEnableFooterFollowWhenLoadFinished(true)
            setEnableOverScrollDrag(true)
            setEnableRefresh(false)
        }
        io_adr.setOnClickListener { routerTo.jumpToAddressManager(1) }

        invoice_order_submit.setOnClickListener { submitData() }
    }

    /**
     * 结束加载
     */
    fun finishRefresh() {
        invoice_order_refresh.finishLoadMore()
        invoice_order_refresh.finishRefresh()
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

        //发票订单返回数据
        mViewModel.orderList.observe(this, Observer {
            ifLoading = false
            if (it!!.pageInfo.page == 0) {
                infoList.clear()
            }
            if (it!!.pageInfo.last) {
                invoice_order_refresh.setEnableLoadMore(false)
            } else {
                invoice_order_refresh.setEnableLoadMore(true)
            }
            page = it!!.pageInfo.page
            it!!.list?.apply {
                for (info in it!!.list){
                    infoList.add(info)
                }
            }
            initAdapter()
        })

        //发票地址返回信息
        adrViewModel.addressList.observe(this, Observer {
            ifLoading = false
            if (it != null && it.size > 0){
                addressPD(true)
                adrInfo = it!![0]
                fillAdr()
            } else {
                addressPD(false)
            }
        })
    }

    /**
     * 填充地址
     */
    private fun fillAdr() {
        io_adr.visibility = View.VISIBLE
        io_adr_name.text = adrInfo.receiverName
        io_adr_phone.text = adrInfo.phone
        io_adr_address.text = adrInfo.areaCodeName + adrInfo.address
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (mAdapter == null) {
            invoice_order_recycle.adapter = InvoiceNewOrderAdapter(mContext!!, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                    val ioInfo = infoList[position]
                    when (ioInfo.temp) {
                        0 -> {//没有选择，添加
                            ioInfo.temp = 1
                            orderStrs.add(ioInfo.orderId)
                        }
                        1 -> {//选择上，移除
                            ioInfo.temp = 0
                            orderStrs.remove(ioInfo.orderId)
                        }
                    }
                    notifyDataSetChanged()
                }
                mAdapter = this
            }
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     * 判断是否有地址
     * @param isAdr Boolean
     */
    private fun addressPD(isAdr: Boolean) {
        if (isAdr) {
            io_adr_tochoice.visibility = View.GONE
            io_adr_name.visibility = View.VISIBLE
            io_adr_phone.visibility = View.VISIBLE
            io_adr_address.visibility = View.VISIBLE
        } else {
            io_adr_tochoice.visibility = View.VISIBLE
            io_adr_name.visibility = View.GONE
            io_adr_phone.visibility = View.GONE
            io_adr_address.visibility = View.GONE
        }
    }

    /**
     * 提交数据
     */
    private fun submitData() {
        if (orderStrs.size == 0) {
            showToast("请选择订单")
            return
        }

        val oStrBuffer = StringBuffer()
        for (os in orderStrs) {
            oStrBuffer.append(os)
            oStrBuffer.append(",")
        }

        if (!oStrBuffer.isEmpty()) {
            oStrBuffer.deleteCharAt(oStrBuffer.length - 1)
        }

        if (adrInfo == null || ToolUtils.isEmpty(adrInfo.id)) {
            showToast("请选择地址")
            return
        }
        ifLoading = true
        routerTo.jumpToInvoice(
            oStrBuffer.toString(),
            adrInfo.id,
            adrInfo.receiverName,
            adrInfo.phone,
            adrInfo.areaCodeName + adrInfo.address
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AddressManagerActivity.BACK_DATA && resultCode == AddressManagerActivity.BACK_DATA) {
            if (data != null) {
                adrInfo = data.getParcelableExtra("adr_info")
                addressPD(true)
                fillAdr()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.INVOICE_ADD -> {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }

}
