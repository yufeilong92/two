package com.lipo.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.TextView
import com.lipo.mylibrary.R

/**
 * @Author : ZFB  is Creating a porject
 * @Email : 879736112@qq.com
 * @Time :2019/5/28 14:39
 * @Purpose :带圆角的textView
 */
class RoundTextView(val mContext: Context, val attSet: AttributeSet) : TextView(mContext, attSet) {

    private val mPaint = Paint()
    private val mPaint2 = Paint()
    private var roundRadius = 0f

    init {
        val tda = mContext.obtainStyledAttributes(attSet, R.styleable.RoundConsStyle)
        roundRadius = tda.getDimension(R.styleable.RoundConsStyle_corners_radius, 0f)

        mPaint.apply {
            color = Color.WHITE
            isAntiAlias = true
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        }

        mPaint2.apply {
            xfermode = null
        }

    }

    override fun draw(canvas: Canvas?) {
        val minWH = Math.min(width / 2f, height / 2f)
        if (roundRadius > minWH) {
            roundRadius = minWH
        }
        roundRadius = 60f
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas2 = Canvas(bmp)
        super.draw(canvas2)
        drawLeftUp(canvas2)
        drawLeftDown(canvas2)
        drawRightUp(canvas2)
        drawRightDown(canvas2)

        canvas!!.drawBitmap(bmp, 0f, 0f, mPaint2)
        bmp.recycle()

    }


    private fun drawLeftUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, roundRadius)
        path.lineTo(0f, 0f)
        path.lineTo(roundRadius, 0f)
        path.arcTo(
            RectF(0f, 0f, roundRadius * 2, roundRadius * 2),
            -90f, -90f
        )
        path.close()
        canvas.drawPath(path, mPaint)
    }

    private fun drawLeftDown(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, (height - roundRadius))
        path.lineTo(0f, height.toFloat())
        path.lineTo(roundRadius, height.toFloat() )
        path.arcTo(
            RectF(0f, (height - roundRadius * 2), roundRadius * 2, height.toFloat()),
            90f, 90f
        )
        path.close()
        canvas.drawPath(path, mPaint)
    }

    private fun drawRightUp(canvas: Canvas) {
        val path = Path()
        path.moveTo((width - roundRadius), 0f)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width.toFloat(), roundRadius)
        path.arcTo(
            RectF((width - roundRadius * 2), 0f, width.toFloat(), roundRadius * 2),
            0f, -90f
        )
        path.close()
        canvas.drawPath(path, mPaint)
    }

    private fun drawRightDown(canvas: Canvas) {
        val path = Path()
        path.moveTo((width - roundRadius), height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), (height - roundRadius))
        path.arcTo(
            RectF((width - roundRadius * 2), (height - roundRadius * 2), width.toFloat(), height.toFloat()),
            0f, 90f
        )
        path.close()
        canvas.drawPath(path, mPaint)
    }


}