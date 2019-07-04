package com.shangxing.views.page

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.utils.Constant


class MyBarView:View{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        var tda: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.MyBarStyle)

        val barBg = tda.getColor(R.styleable.MyBarStyle_barBg,resources.getColor(R.color.gm_color))

        setBackgroundColor(barBg)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(Constant.screenWidth,Constant.statusHeight)
    }

}