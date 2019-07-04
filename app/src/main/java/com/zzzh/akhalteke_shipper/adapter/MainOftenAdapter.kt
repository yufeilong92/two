package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.GoodsDetailInfo
import com.zzzh.akhalteke_shipper.utils.ToolUtils

class MainOftenAdapter(var context: Context, var infoList: MutableList<GoodsDetailInfo>) :
    BaseQuickAdapter<GoodsDetailInfo, BaseViewHolder>(R.layout.item_often, infoList) {

    private var dpspace = 0

    init {
        dpspace = ToolUtils.dpTopx(context, 6f)
    }

    override fun convert(helper: BaseViewHolder?, item: GoodsDetailInfo?) {
        val position = helper!!.layoutPosition

        helper!!.apply {
            setText(
                R.id.item_often_infor,
                item!!.carLengthVO + " " + item!!.weightVolumeVO
            )
            addOnClickListener(R.id.item_often_more)
            addOnClickListener(R.id.item_often_delete)

            getView<TextView>(R.id.item_often_path).text =
                ToolUtils.adrSpannStr(mContext, item!!.loadAreaCodeVO, item!!.unloadAreaCodeVO)
        }
    }

}