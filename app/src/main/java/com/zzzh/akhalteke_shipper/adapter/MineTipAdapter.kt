package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.StringInfo

/**
 * @Author zfb
 * @Date 2019/5/22 10:28
 *
 * 我的页面 货主的标签
 *
 */
class MineTipAdapter(val myContext: Context, val listStr: MutableList<Int>) : BaseAdapter() {

    val layoutInflater: LayoutInflater = LayoutInflater.from(myContext)

    private var mPosition = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var mView = convertView
        var info = listStr[position]
        if (mView == null) {
            mView = layoutInflater.inflate(R.layout.item_label_icon, null)
        }
        val iconImage = mView!!.findViewById<ImageView>(R.id.item_label_icon_image)
        iconImage.setImageResource(info)
        return mView!!
    }

    override fun getItem(position: Int): Any {
        return listStr[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listStr.size
    }

}