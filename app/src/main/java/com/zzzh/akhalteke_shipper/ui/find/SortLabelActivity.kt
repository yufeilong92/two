package com.zzzh.akhalteke_shipper.ui.find

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.TextLineAdapter
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.activity_sort_label.*

class SortLabelActivity : BaseActivity() {

    companion object {
        const val DATA_BACK = 2104
    }

    private val sortList = arrayListOf<StringInfo>(
        StringInfo(id = "0", name = "默认排序"),
        StringInfo(id = "1", name = "距离排序")
    )

    private var mAdapter: TextLineAdapter? = null

    private var tPosition = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_label)

        tPosition = intent.getStringExtra("t_position")

        initView()
    }

    private fun initView() {
        sort_label_recycle.layoutManager = LinearLayoutManager(mContext)
        initAdapter()
        if (!ToolUtils.isEmpty(tPosition)) {
            mAdapter!!.toSetPosition(ToolUtils.stringToIntM(tPosition))
        }
    }

    private fun initAdapter() {
        if (mAdapter == null) {
            mAdapter = TextLineAdapter(mContext, sortList).apply {
                setOnItemClickListener { adapter, view, position ->
                    dataBack(sortList[position])
                }
            }
            sort_label_recycle.adapter = mAdapter
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    private fun dataBack(strInfo: StringInfo) {
        val intentBack = Intent()
        intentBack.putExtra("data_string", strInfo)
        setResult(DATA_BACK, intentBack)
        finishBase()
    }

}
