package com.zzzh.akhalteke_shipper.ui.find.fragment

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.FindCarAdapter
import com.zzzh.akhalteke_shipper.bean.FindCarInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.PageInfo
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.FindCarViewModel
import com.zzzh.akhalteke_shipper.view.dialog.CallCarOwnerDialog
import kotlinx.android.synthetic.main.fragment_find_car.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FindCarFragment : BaseFragment() {

    private val findCarViewModel: FindCarViewModel by lazy {
        ViewModelProviders.of(activity!!).get(FindCarViewModel::class.java)
    }

    lateinit var mainView: View
    private var isFrist = true//是否第一次进入
    private var isCreate = false//是否被创建
    private var isVisibleToUser = false//是否即将可见

    private val infoList: MutableList<FindCarInfo> = mutableListOf()
    private var adapter: FindCarAdapter? = null

    private var page = 0//页码，从0开始
    private var status = 0//0熟车，1车源
    private var ifSearch = false//是否搜索数据

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_find_car, null).also { mainView = it }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCreate = true
        status = arguments?.getInt("status", 0) ?: 0
        eventBus.register(this)

        initView()
        initViewModel()
        toGetData(0)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        if (!ifSearch) {
            toGetData(0)
        }
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        ffindcar_recycle.layoutManager = LinearLayoutManager(mContext)

        //下拉刷新
        ffindcar_refresh.apply {
            setOnRefreshListener { refreshlayout ->
                if (!ifSearch) {
                    toGetData(0)
                } else {
                    findCarViewModel.getCarOwnerByPhone(ffindcar_edit.text.toString().trim())
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
        //搜索框 软键盘右下角点击按钮
        ffindcar_edit.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if (isCreate && isVisibleToUser) {
                        val phoneStr = ffindcar_edit.text.toString().trim()
                        if (phoneStr.isEmpty()) {
                            toGetData(0)
                        } else {
                            if (phoneStr.length == 11) {
                                findCarViewModel.getCarOwnerByPhone(phoneStr)
                            } else {
                                showToast("请输入11位手机号码")
                            }
                        }
                    }
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }

        //搜索框监听事件
        ffindcar_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isCreate && isVisibleToUser) {
                    val phoneStr = ffindcar_edit.text.toString().trim()
                    if (phoneStr.isEmpty()) {
                        ffindcar_contentno.visibility = View.GONE
                        ffindcar_refresh.visibility = View.VISIBLE
                        toGetData(0)
                    } else if (phoneStr.length == 11) {
                        findCarViewModel.getCarOwnerByPhone(phoneStr)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        ffindcar_button02.setOnClickListener { routerTo.callPhone(ffindcar_edit.text.toString().trim()) }
        ffindcar_button01.setOnClickListener { findCarViewModel.sendInvitationSms(ffindcar_edit.text.toString().trim()) }
    }

    /**
     * 初始化viewmodel
     */
    private fun initViewModel() {
        //progress消失
        findCarViewModel.isShowProgress.observe(this, Observer<Int> {
            if (isCreate && isVisibleToUser) {
                when (it) {
                    0 -> {
                        if (isFrist) {
                            showProgress()
                        }
                    }
                    1 -> {
                        isFrist = false
                        dismissProgress()
                        ffindcar_refresh.finishLoadMore()
                        ffindcar_refresh.finishRefresh()
                    }
                }
            }
        })
        //车辆列表数据
        findCarViewModel.carListData.observe(this, Observer {
            it?.apply {
                if (isCreate && isVisibleToUser && this.status == (this@FindCarFragment.status + 1)) {
                    ifSearch = false
                    page = PageInfo.pageLoad(this, infoList, ffindcar_refresh)

                    initAdapter()
                }
            }

        })
        //车长车型数据
        findCarViewModel.carLT.observe(this, Observer {
            if (isCreate && isVisibleToUser) {
                isFrist = true
                toGetData(0)
            }
        })

        //列表返回数据
        findCarViewModel.infos.observe(this, Observer {
            if (isCreate && isVisibleToUser) {
                ffindcar_refresh.setEnableLoadMore(false)
                infoList.clear()
                ifSearch = true
                it?.apply {
                    for (info in it) {
                        infoList.add(
                            FindCarInfo(
                                info.carOwnerId,
                                info.name,
                                info.phone,
                                info.portrait,
                                info.plateNumber,
                                info.carLengthAndType ?: ""
                            )
                        )
                    }
                }
                initAdapter()
                if (infoList.size == 0) {
                    ffindcar_contentno.visibility = View.VISIBLE
                    ffindcar_refresh.visibility = View.GONE
                } else {
                    ffindcar_contentno.visibility = View.GONE
                    ffindcar_refresh.visibility = View.VISIBLE
                }
            }
        })
        findCarViewModel.sendSuccssBack.observe(this, Observer {
            showToast("发送成功")
        })
    }

    /**
     * 获取数据
     */
    private fun toGetData(pageNo: Int) {
        if (isCreate && isVisibleToUser) {
            findCarViewModel.carList(ifFamiliar = (status + 1), page = pageNo)
        }
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (adapter == null) {
            adapter = FindCarAdapter(mContext, infoList, status).apply {
                setEmptyView(R.layout.cell_empty, ffindcar_recycle)
                setOnItemChildClickListener { adapter, view, position ->
                    val info = infoList[position]
                    when (view.id) {
                        R.id.item_findcar_call -> {//打电话按钮
                            toCheckInfo {
                                CallCarOwnerDialog(mContext, info.phone).show()
                            }
                        }
                        R.id.item_findcar_add -> {//添加熟车
                            toCheckCre {
                                isFrist = true
                                findCarViewModel.insertFamiliarCar(info.carOwnerId) {
                                    showToast("添加成功")
                                }
                            }
                        }
                    }
                }
                setOnItemClickListener { adapter, view, position ->
                    toCheckCre {
                        val info = infoList[position]
                        routerTo.jumpToDriverDetail(info.carOwnerId)
                    }
                }
            }
            ffindcar_recycle.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    /**
     * event 返回
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.message == MessageEvent.FAMILY_CAR_UPDADE) {
            if (status == 0) {
                toGetData(0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }

}