package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.FindCarInfo
import com.zzzh.akhalteke_shipper.utils.GlideUtil

class FindCarAdapter(var context: Context, var infoList: MutableList<FindCarInfo>, val status: Int = 0) :
    BaseQuickAdapter<FindCarInfo, BaseViewHolder>(R.layout.item_find_car, infoList) {

    override fun convert(helper: BaseViewHolder?, item: FindCarInfo?) {
        val position = helper!!.layoutPosition

        val headerView = helper!!.getView<ImageView>(R.id.item_findcar_header)
//        ImageLoadingUtils.loadNetImage(headerView, item!!.portrait ?: "")
        GlideUtil.loadQuadRangleImagerWithHttp(context,headerView,item!!.portrait )

        helper!!.apply {
            setText(R.id.item_findcar_name, item!!.name ?: "")
            setText(R.id.item_findcar_no, item!!.plateNumber ?: "")
            setText(R.id.item_findcar_type, item!!.carLengthAndType ?: "")

            addOnClickListener(R.id.item_findcar_call)
            addOnClickListener(R.id.item_findcar_add)
        }

        val addCarView = helper!!.getView<View>(R.id.item_findcar_add)
        if (status == 0) {
            addCarView.visibility = View.GONE
        } else {
            addCarView.visibility = View.VISIBLE
        }

    }

}