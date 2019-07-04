package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.SelectRecyclerVo
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.utils.ToolUtils

/**
 * @Author : YFL  is Creating a porject in akhalteke-Android-shipper
 * @Package com.zzzh.akhalteke_shipper.adapter
 * @Email : yufeilong92@163.com
 * @Time :2019/5/27 16:43
 * @Purpose :订单取消原因
 */
class CancleOrderCaseAdapter(var context: Context, var infoList: MutableList<SelectRecyclerVo>) :
    BaseQuickAdapter<SelectRecyclerVo, BaseViewHolder>(R.layout.item_cancle_order_case, infoList) {
    override fun convert(helper: BaseViewHolder?, item: SelectRecyclerVo?) {
        val position = helper!!.layoutPosition
        val cb = helper.getView<CheckBox>(R.id.cb_order_detail_case)
        cb.isChecked = item!!.isCheck
        cb.text = item.name
        cb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!cb.isPressed)return@setOnCheckedChangeListener
            if (chbItemListener != null) {
                chbItemListener!!.setCheckBokItemListener(position,isChecked, item.name)
            }
        }
    }

    interface CheckBoklistener {
        fun setCheckBokItemListener(postion:Int,chb: Boolean, case: String)
    }
    var chbItemListener: CheckBoklistener? = null
    fun setOnCheckBokItemListener(ch: CheckBoklistener) {
        this.chbItemListener = ch
    }
    /**
     * 刷新数据
     */
    fun refreshData(infoList: MutableList<SelectRecyclerVo>) {
        this.infoList = infoList
        notifyDataSetChanged()
    }

}