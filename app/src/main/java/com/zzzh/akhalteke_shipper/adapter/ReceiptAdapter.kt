package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils

class ReceiptAdapter(var context: Context, var infoList: ArrayList<StringInfo>) :
    BaseQuickAdapter<StringInfo, BaseViewHolder>(R.layout.item_image, infoList) {


    override fun convert(helper: BaseViewHolder?, item: StringInfo?) {
        val position = helper!!.layoutPosition

        helper?.apply {
            getView<View>(R.id.item_image_delete).visibility = View.GONE
            val imageView = getView<SimpleDraweeView>(R.id.item_image_simaple)
            ImageLoadingUtils.loadNetImage(imageView,item!!.name)
        }

    }

}