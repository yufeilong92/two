package com.zzzh.akhalteke_shipper.ui.transport

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.DriverAdapter
import com.zzzh.akhalteke_shipper.adapter.DriverNewAdapter
import com.zzzh.akhalteke_shipper.bean.DriverInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.publish.TruckChoiceActivity
import com.zzzh.akhalteke_shipper.ui.transport.viewmodel.DriverViewModel
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.activity_driver_list.*
import kotlinx.android.synthetic.main.activity_driver_new_list.*
import kotlinx.android.synthetic.main.gm_title_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.transport
 * @Package com.zzzh.akhalteke_shipper.ui.transport
 * @Email : yufeilong92@163.com
 * @Time :2019/5/28 14:35
 * @Purpose :指定司机
 */
class DriverNewListActivity : BaseActivity() {

    companion object {
        const val DATABACK = 2103
    }

    private var ifLoading = true // 是否需要显示加载
    private var entryTemp = 0 //0从发货中的列表也进入，1从发布货源进入
    private var goodsId = "" //货源id
    private val mViewModel: DriverViewModel by lazy {
        ViewModelProviders.of(this).get(DriverViewModel::class.java)
    }
    //页码，从0开始
    private var page = 0
    //是否搜索
    private var ifSearch = false
    private var mAdapter: DriverNewAdapter? = null
    private var infoList: MutableList<DriverInfo> = mutableListOf()

    //车长选中项
    var truckLs: ArrayList<StringInfo> = arrayListOf()
    //车型选中项
    var truckCs: ArrayList<StringInfo> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_new_list)
        EventBus.getDefault().register(this)
        entryTemp = intent.getIntExtra("entry_temp", 0)
        when (entryTemp) {
            0 -> {
                goodsId = intent.getStringExtra("goods_id")
            }
        }
        initView()
        initViewModel()
        toGetData(0)
    }

    /**
     * 初始化页面
     */
    private fun initView() {
        gm_tv_activity_title.text = "指定司机"
        //设置编辑框弹出键盘右下角市搜索按钮
        et_driver_list_phone.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val phoneStr = et_driver_list_phone.text.toString().trim()
                    if (phoneStr.isEmpty()) {
                        ifLoading = true
                        toGetData(0)
                    } else {
                        if (phoneStr.length == 11) {
                            ifLoading = true
                            mViewModel.getCarOwnerByPhone(phoneStr)
                        } else {
                            showToast("请输入11位手机号码")
                        }
                    }
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }

        //设置编辑框，输入11号码开始搜索，0时清除数据
        et_driver_list_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val phoneStr = et_driver_list_phone.text.toString().trim()
                if (phoneStr.isEmpty()) {
                    ifLoading = true
                    toGetData(0)
                } else if (phoneStr.length == 11) {
                    ifLoading = true
                    mViewModel.getCarOwnerByPhone(phoneStr)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //初始化下拉刷新
        srl_driver_list_refresh.apply {
            setOnRefreshListener { refreshlayout ->
                if (!ifSearch) {
                    toGetData(0)
                } else {
                    mViewModel.getCarOwnerByPhone(et_driver_list_phone.text.toString().trim())
                }
            }
            setOnLoadMoreListener { refreshlayout ->
                if (!ifSearch) {
                    toGetData(page + 1)
                }
            }
            setEnableFooterFollowWhenLoadFinished(true)
            setEnableOverScrollDrag(true)
        }

        rlv_driver_list_recycle.layoutManager = LinearLayoutManager(mContext)
        iv_driver_list_search.setOnClickListener {
            routerTo.jumpToDriverSearch()
        }

        //打电话
        tv_contact_driver.setOnClickListener { routerTo.callPhone(et_driver_list_phone.text.toString().trim()) }
        //发短信
        tv_driver_invite.setOnClickListener { mViewModel.sendInvitationSms(et_driver_list_phone.text.toString().trim()) }

        //选中车长车型
//        fmain2_totype.setOnClickListener {
//            routerTo.jumpToTruckChoice(entryTemp = 2, truckLs = truckLs, truckType = truckCs)
//        }
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
                    srl_driver_list_refresh.finishLoadMore()
                    srl_driver_list_refresh.finishRefresh()
                }
            }
        })
        //指定司机列表数据返回监听
        mViewModel.driverInfos.observe(this, Observer {
            ifLoading = false
            srl_driver_list_refresh.visibility = View.VISIBLE
            if (it!!.pageInfo.page == 0) {
                infoList.clear()
            }
            if (it!!.pageInfo.last) {
                srl_driver_list_refresh.setEnableLoadMore(false)
            } else {
                srl_driver_list_refresh.setEnableLoadMore(true)
            }
            page = it!!.pageInfo.page
            it!!.list?.apply {
                for (info in it!!.list) {
                    infoList.add(info)
                }
            }
            initAdapter()
        })

        //搜索的时候列表数据返回
        mViewModel.infos.observe(this, Observer {
            ifLoading = false
            infoList.clear()
            it?.apply {
                for (info in it) {
                    infoList.add(info)
                }
            }
            initAdapter()
            if (infoList.size == 0) {
                rl_driver_intver.visibility = View.VISIBLE
                srl_driver_list_refresh.visibility = View.GONE
            } else {
                rl_driver_intver.visibility = View.GONE
                srl_driver_list_refresh.visibility = View.VISIBLE
            }
            srl_driver_list_refresh.setEnableLoadMore(false)
        })

        //指定司机
        mViewModel.appointBack.observe(this, Observer {
            ifLoading = false
            EventBus.getDefault().post(MessageEvent(MessageEvent.DRIVER_APPOINT))
            finishBase()
        })

        //发送短信成功
        mViewModel.sendSuccssBack.observe(this, Observer {
            ifLoading = false
            showToast("发送成功")
        })

    }

    /**
     * 请求数据
     * @param pageNo Int
     */
    private fun toGetData(pageNo: Int) {
//        mViewModel.getFamiliarCarList(pageNo)
        mViewModel.getFamiliarCarList(pageNo, clenght.toString(), ctype.toString())
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (mAdapter == null) {
            rlv_driver_list_recycle.adapter = DriverNewAdapter(mContext!!, infoList, 1).apply {
                setOnItemClickListener { adapter, view, position ->

                }
                setOnItemChildClickListener { adapter, view, position ->
                    when (view.id) {
                        R.id.tv_item_carrier_driver -> {
                            when (entryTemp) {
                                0 -> {
                                    ifLoading = true
                                    toCheckInfo {
                                        mViewModel.appointDriver(goodsId, infoList[position].carOwnerId)
                                    }
                                }
                                1 -> {
                                    toBackData(infoList[position])
                                }
                            }
                        }
                    }
                }
                setEmptyView(R.layout.cell_empty, rlv_driver_list_recycle)
                mAdapter = this
            }
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     * 返回数据
     * @param driverInfo DriverInfo  司机详情
     */
    private fun toBackData(driverInfo: DriverInfo) {
        val intentBack = Intent()
        intentBack.putExtra("driver_info", driverInfo)
        setResult(DriverNewListActivity.DATABACK, intentBack)
        finishBase()
    }

    private val clenght = StringBuffer()
    private val ctype = StringBuffer()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TruckChoiceActivity.DATABACK && resultCode == TruckChoiceActivity.DATABACK) {
            data ?: return
            //选择车长车型
            truckLs = data.getParcelableArrayListExtra("car_lengths")
            truckCs = data.getParcelableArrayListExtra("car_types")

            val strBuffer = StringBuffer()
            clenght.setLength(0)
            ctype.setLength(0)

            for (ls in truckLs) {
                strBuffer.append(ls.name + "/")
                clenght.append(ls.id + ",")
            }
            if (!strBuffer.isEmpty()) {
                strBuffer.deleteCharAt(strBuffer.length - 1)
            }
            if (!clenght.isEmpty()) {
                clenght.deleteCharAt(clenght.length - 1)
            }
            strBuffer.append("    ")

            for (ls in truckCs) {
                strBuffer.append(ls.name + "/")
                ctype.append(ls.id + ",")
            }
            if (!strBuffer.isEmpty()) {
                strBuffer.deleteCharAt(strBuffer.length - 1)
            }
            if (!ctype.isEmpty()) {
                ctype.deleteCharAt(ctype.length - 1)
            }

            if (strBuffer.isEmpty() || ToolUtils.isEmpty(strBuffer.toString().trim())) {
                fmain2_totype_text.text = "请选择车型车长"
            } else {
                fmain2_totype_text.text = strBuffer
            }
            ifLoading = true
            toGetData(0)
        }
        super.onActivityResult(requestCode, resultCode, data)
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
