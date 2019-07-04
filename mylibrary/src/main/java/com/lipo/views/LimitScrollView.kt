package com.lipo.views

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet

class LimitScrollView:NestedScrollView{

    private lateinit var mContext:Context

    constructor(context: Context):super(context){mContext = context}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){mContext = context}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){mContext = context}


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var hMeasureSpec = heightMeasureSpec
        val ds = mContext.resources.displayMetrics
        hMeasureSpec = MeasureSpec.makeMeasureSpec(ds.heightPixels / 4, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, hMeasureSpec)
    }

}