package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.SourceTextAdapter
import com.zzzh.akhalteke_shipper.bean.SourceInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.GoodsViewModel
import kotlinx.android.synthetic.main.activity_owner_more_list.*

/**
 * 货源
 */
class OwnerMoreListActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(GoodsViewModel::class.java)
    }

    var shipperId: String = ""//货主id

    private var ifLoading = true // 是否需要显示加载
    private var page = 0

    private var mAdapter: SourceTextAdapter? = null
    private var infoList: MutableList<SourceInfo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_more_list)

        shipperId = intent.getStringExtra("shipper_id")

        initView()
        initViewModel()

        getData(0)
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        oml_recycle.layoutManager = LinearLayoutManager(mContext)

        oml_refresh.apply {
            setOnRefreshListener { refreshlayout ->
                getData(0)
            }
            setOnLoadMoreListener { refreshlayout ->
                getData(page + 1)
            }
            setEnableFooterFollowWhenLoadFinished(true)
            setEnableOverScrollDrag(true)
        }
    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, android.arch.lifecycle.Observer {
            when (it) {
                0 -> {
                    if (ifLoading) {
                        showProgress()
                    }
                }
                1 -> {
                    dismissProgress()
                    oml_refresh.finishLoadMore()
                    oml_refresh.finishRefresh()
                }
            }
        })

        //获取到货源列表
        mViewModel.ownerGoodsList.observe(this, Observer {
            ifLoading = false
            if (it!!.pageInfo.page == 0) {
                infoList.clear()
            }
            if (it!!.pageInfo.last) {
                oml_refresh.setEnableLoadMore(false)
            } else {
                oml_refresh.setEnableLoadMore(true)
            }
            page = it!!.pageInfo.page
            it!!.list?.apply {
                for (info in it!!.list) {
                    infoList.add(info)
                }
            }
            initAdapter()
        })
    }

    /**
     * 获取数据请求接口
     * @param pageNo Int
     */
    private fun getData(pageNo: Int) {
        mViewModel.getShipperGoods(shipperId, pageNo)
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (mAdapter == null) {
            oml_recycle.adapter = SourceTextAdapter(mContext!!, infoList).apply {
                setOnItemClickListener { adapter, view, position ->

                }
                mAdapter = this
                setEmptyView(R.layout.cell_empty, oml_recycle)
            }
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }


}
