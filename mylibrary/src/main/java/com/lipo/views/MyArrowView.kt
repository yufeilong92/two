package com.lipo.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.lipo.mylibrary.R

/**
 * @Author : ZFB  is Creating a porject in myDemo
 * @Package com.lipo.mydemo.view
 * @Email : 879736112@qq.com
 * @Time :2019/5/28 9:57
 * @Purpose :向右的箭头
 */
class MyArrowView(val mContext: Context, val attr: AttributeSet) : View(mContext, attr) {

    val mPaint = Paint()

    init {
        val tda = mContext.obtainStyledAttributes(attr, R.styleable.ArrowStyle)
        val arrowColor =
            tda.getColor(R.styleable.ArrowStyle_arrow_color, ContextCompat.getColor(mContext, R.color.white))

        mPaint.apply {
            isAntiAlias = true
            color = arrowColor
            style = Paint.Style.STROKE
            isDither = true
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val sWidth = (width / 7f)
        mPaint.strokeWidth = sWidth + 1f

        val path = Path()
        path.moveTo(sWidth, sWidth)
        path.lineTo((width - sWidth), height / 2f)
        path.lineTo(sWidth, height.toFloat() - sWidth)
        canvas!!.drawPath(path, mPaint)
    }

}