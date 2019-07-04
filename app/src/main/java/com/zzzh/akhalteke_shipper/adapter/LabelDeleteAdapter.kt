package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.StringInfo

abstract class LabelDeleteAdapter(val myContext: Context, val listStr: MutableList<StringInfo>) : BaseAdapter() {

    val layoutInflater: LayoutInflater = LayoutInflater.from(myContext)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var mView = convertView
        var info = listStr[position]
        if (mView == null) {
            mView = layoutInflater.inflate(R.layout.item_label_delete, null)
        }
        val item_labeltext = mView!!.findViewById<TextView>(R.id.ild_name)
        item_labeltext.text = info.name


        mView!!.findViewById<View>(R.id.ild_delete).setOnClickListener {
            clickItem(info)
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