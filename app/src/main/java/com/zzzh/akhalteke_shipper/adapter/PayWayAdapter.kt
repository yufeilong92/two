package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.StringInfo

abstract class PayWayAdapter(val myContext: Context, val listStr: MutableList<StringInfo>) : BaseAdapter() {

    val layoutInflater: LayoutInflater = LayoutInflater.from(myContext)

    private var mPosition = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var mView = convertView
        var info = listStr[position]
        if (mView == null) {
            mView = layoutInflater.inflate(R.layout.item_label_text2, null)
        }
        val item_labeltext = mView!!.findViewById<TextView>(R.id.item_labeltext)
        item_labeltext.text = info.name

        if(mPosition == position){
            item_labeltext.setBackgroundResource(R.drawable.bg_line_red)
            item_labeltext.setTextColor(myContext.resources.getColor(R.color.main_color))
        }else{
            item_labeltext.setBackgroundResource(R.drawable.bg_line_black)
            item_labeltext.setTextColor(myContext.resources.getColor(R.color.main_text6))
        }

        item_labeltext.setOnClickListener {
            if(position!=mPosition){
                mPosition = position
                clickItem(info)
            }
        }

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

    abstract fun clickItem(item: StringInfo)

}