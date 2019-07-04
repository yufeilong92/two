package com.zzzh.akhalteke_shipper.ui.mine.address

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.AddressAdapter
import com.zzzh.akhalteke_shipper.bean.AddressInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.AddressViewModel
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.PromiseDialog
import kotlinx.android.synthetic.main.activity_address_manager.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 我的地址管理
 */
class AddressManagerActivity : BaseActivity() {

    var entryTemp = 0//0、从我的页面进入，1、选择地址

    private var ifLoading = true // 是否需要显示加载
    private var deleteId = ""//删除item的id
    private var mAdapter: AddressAdapter? = null//地址列表adapter
    private var infoList: MutableList<AddressInfo> = mutableListOf()//地址列表

    //删除地址dialog
    private lateinit var deleteDialog: PromiseDialog

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(AddressViewModel::class.java)
    }

    companion object {
        const val BACK_DATA = 2017
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_manager)

        entryTemp = intent.getIntExtra("entry_temp",0)

        eventBus.register(this)
        initView()
        initViewModel()
        mViewModel.addressList()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        address_manager_recycle.layoutManager = LinearLayoutManager(mContext)
        address_manager_refresh.apply {
            setOnRefreshListener { refreshlayout ->
                mViewModel.addressList()
            }
            setOnLoadMoreListener { refreshlayout ->
            }
            setEnableFooterFollowWhenLoadFinished(true)
            setEnableOverScrollDrag(true)
            setEnableLoadMore(false)
        }

        //底部新增按钮
        adr_manager_bottom.setOnClickListener { routerTo.jumpToAddressAdd(0) }

        //删除地址dialog
        deleteDialog = PromiseDialog(mContext, "确定要删除该地址吗？", {
            ifLoading = true
            mViewModel.addressDelete(deleteId)
        }, {})
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
                    address_manager_refresh.finishRefresh()
                }
            }
        })

        //地址列表数据
        mViewModel.addressList.observe(this, Observer { infolist ->
            ifLoading = false
            infolist?.let {
                infoList.clear()
                for (data in it) {
                    infoList.add(data)
                }
                initAdapter()
            }
        })
        //删除之后返回
        mViewModel.successBack.observe(this, Observer {
            ifLoading = false
            deleteId = ""
            mViewModel.addressList()
        })
    }

    private fun initAdapter() {
        if (mAdapter == null) {
            address_manager_recycle.adapter = AddressAdapter(mContext!!, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                    when (entryTemp) {//选择地址返回
                        1 -> {
                            val intentBack = Intent()
                            intentBack.putExtra("adr_info", infoList[position])
                            setResult(AddressManagerActivity.BACK_DATA, intentBack)
                            finishBase()
                        }
                    }
                }
                setOnItemChildClickListener { adapter, view, position -> clickItem(view.id, infoList[position]) }
                mAdapter = this
                setEmptyView(R.layout.cell_empty, address_manager_recycle)
            }
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     * item点击事件
     * @param vId Int
     * @param info AddressInfo
     */
    private fun clickItem(vId: Int, info: AddressInfo) {
        when (vId) {
            R.id.item_address_moren -> {//设置默认地址
                if (info.ifDefault == "2") {
                    ifLoading = true
                    mViewModel.addressDefault(info.id)
                }
            }
            R.id.item_address_delete -> {//删除地址
                ToolUtils.showDialog(deleteDialog)
                deleteId = info.id
            }
            R.id.item_address_edit -> {//重新编辑地址
                routerTo.jumpToAddressAdd(1, info)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.ADDRESS_LIST_UPDATA -> {
                mViewModel.addressList()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
        ToolUtils.dismissDialog(deleteDialog)
    }

}
