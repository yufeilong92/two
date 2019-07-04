package com.zzzh.akhalteke_shipper.adapter.sendGoods

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.GoodsDetailInfo
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.TimeUtil
import com.zzzh.akhalteke_shipper.utils.ToolUtils

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.adapter.sendGoods
 * @Package com.zzzh.akhalteke_shipper.adapter.sendGoods
 * @Email : yufeilong92@163.com
 * @Time :2019/5/29 11:34
 * @Purpose :常发货
 */
class MainNewOftenAdapter(var context: Context, var infoList: MutableList<GoodsDetailInfo>) :
        BaseQuickAdapter<GoodsDetailInfo, BaseViewHolder>(R.layout.item_new_often, infoList) {

    private var dpspace = 0

    init {
        dpspace = ToolUtils.dpTopx(context, 6f)
    }

    override fun convert(helper: BaseViewHolder?, item: GoodsDetailInfo?) {
        val position = helper!!.layoutPosition

        helper!!.apply {
            setText(
                    R.id.tv_item_often_infom,
                    item!!.carLengthVO + " " + item!!.weightVolumeVO
            )
            addOnClickListener(R.id.tv_item_often_again)
            addOnClickListener(R.id.tv_item_often_delete)
            getView<TextView>(R.id.tv_item_often_city).text =
                    ToolUtils.adrSpannStr(mContext, item!!.loadAreaCodeVO, item!!.unloadAreaCodeVO)
            getView<TextView>(R.id.tv_item_often_release_time).text = TimeUtil.getInstance()!!.getMDhmTime(item.loadTime.toLong())
        }
    }

}