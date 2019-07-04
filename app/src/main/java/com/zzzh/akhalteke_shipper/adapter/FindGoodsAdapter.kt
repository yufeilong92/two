package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.SourceInfo
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.TimeUntils

class FindGoodsAdapter(var context: Context, var infoList: MutableList<SourceInfo>) :
    BaseQuickAdapter<SourceInfo, BaseViewHolder>(R.layout.item_findgood_layout, infoList) {

    override fun convert(helper: BaseViewHolder?, item: SourceInfo?) {
        val position = helper!!.layoutPosition
        val headerView = helper!!.getView<SimpleDraweeView>(R.id.sdv_findgood_source_header)
        val tvName = helper!!.getView<TextView>(R.id.tv_item_findgood_name)
        val ivReadName= helper!!.getView<ImageView>(R.id.iv_item_findgood_readname)
        val ivFindGoodDao = helper!!.getView<ImageView>(R.id.iv_item_findgood_dao)
        val tvFindGoodPhone = helper!!.getView<TextView>(R.id.tv_item_findgood_phone)
        val tvTiem = helper!!.getView<TextView>(R.id.tv_item_findgood_time)
        val tvCity = helper!!.getView<TextView>(R.id.tv_item_findgood_city)
        val tvAddress = helper!!.getView<TextView>(R.id.tv_item_findgood_address)
        val tvLoaddCity = helper!!.getView<TextView>(R.id.tv_item_findgood_loadcity)
        val tvloadAddress = helper!!.getView<TextView>(R.id.tv_item_findgood_loadAddress)
        val tvDun = helper!!.getView<TextView>(R.id.tv_item_findgood_dun)
        val tvCar = helper!!.getView<TextView>(R.id.tv_item_findgood_car)
        val tvCarGo = helper!!.getView<TextView>(R.id.tv_item_findgood_cargo)
        ImageLoadingUtils.loadNetImage(headerView, item!!.shipperPortrait)
        ivReadName.visibility=if (item.ifRealCertification=="1") View.VISIBLE else View.GONE
        ivFindGoodDao.visibility=if (item.ifCompanyCertification=="1") View.VISIBLE else View.GONE

        helper?.apply {
            addOnClickListener(R.id.tv_item_findgood_phone)
            tvTiem.text=        TimeUntils.toTimeSpace(item!!.createdTime)
            tvName.text=item.shipperName
            tvCity.text=item.loadAreaCode
            tvLoaddCity.text=item.unloadAreaCode
            tvDun.text=item.weightVolume
            tvCarGo.text=item.goodsName
            tvCar.text=item.carType
            tvAddress.text=item.loadAddress
            tvloadAddress.text=item.unloadAddress
        }
    }



}