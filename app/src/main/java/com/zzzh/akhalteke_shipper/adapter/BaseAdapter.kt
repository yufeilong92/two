package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.utils.ToolUtils

class BaseAdapter(var context: Context, var infoList: MutableList<StringInfo>) :
    BaseQuickAdapter<StringInfo, BaseViewHolder>(R.layout.item_test, infoList) {

    private var dpspace = 0

    init {
        dpspace = ToolUtils.dpTopx(context, 6f)
    }

    override fun convert(helper: BaseViewHolder?, item: StringInfo?) {
        val position = helper!!.layoutPosition
    }

}