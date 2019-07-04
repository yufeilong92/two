package com.lipo.views

import android.content.Context
import android.graphics.*
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.lipo.mylibrary.R

/**
 * @Author : ZFB  is Creating a porject in myDemo
 * @Package com.lipo.mydemo.view
 * @Email : 879736112@qq.com
 * @Time :2019/5/28 11:17
 * @Purpose :带有圆角的布局
 */
class RoundConstraintLayout(val mContext: Context, val attSet: AttributeSet) : ConstraintLayout(mContext, attSet) {

    private var paint: Paint? = null
    private var roundWidth = 5f
    private var roundHeight = 5f
    private var paint2: Paint? = null

    init {
        val tda = mContext.obtainStyledAttributes(attSet, R.styleable.RoundConsStyle)
        val corners_radius = tda.getDimension(R.styleable.RoundConsStyle_corners_radius, 0f)

        roundWidth = corners_radius
        roundHeight = corners_radius

        paint = Paint()
        paint!!.color = Color.WHITE
        paint!!.isAntiAlias = true
        paint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)

        paint2 = Paint()
        paint2!!.xfermode = null
    }

    override fun draw(canvas: Canvas) {
        val minWH = Math.min(width/2f,height/2f)
        if(roundWidth>minWH){
            roundWidth = minWH
            roundHeight = minWH
        }

        val bitmap = Bitmap.createBitmap(
            width, height,
            Bitmap.Config.ARGB_8888
        )
        val canvas2 = Canvas(bitmap)
        super.draw(canvas2)
        drawLiftUp(canvas2)
        drawRightUp(canvas2)
        drawLiftDown(canvas2)
        drawRightDown(canvas2)
        canvas.drawBitmap(bitmap, 0f, 0f, paint2)
        bitmap.recycle()
    }

    private fun drawLiftUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, roundHeight)
        path.lineTo(0f, 0f)
        path.lineTo(roundWidth, 0f)
        path.arcTo(RectF(0f, 0f, (roundWidth * 2), (roundHeight * 2)), -90f, -90f)
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawLiftDown(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, (height - roundHeight))
        path.lineTo(0f, height.toFloat())
        path.lineTo(roundWidth, height.toFloat())
        path.arcTo(
            RectF(
                0f, (height - roundHeight * 2),
                (0 + roundWidth * 2), height.toFloat()
            ), 90f, 90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawRightDown(canvas: Canvas){
        val path = Path()
        path.moveTo((width - roundWidth), height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), (height - roundHeight))
        path.arcTo(
            RectF((width - roundWidth * 2), (height - roundHeight * 2), width.toFloat(), height.toFloat()),
            0f,
            90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

    private fun drawRightUp(canvas: Canvas) {
        val path = Path()
        path.moveTo(width.toFloat(), roundHeight)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo((width - roundWidth), 0f)
        path.arcTo(
            RectF(
                (width - roundWidth * 2), 0f, width.toFloat(),
                (0 + roundHeight * 2)
            ), -90f, 90f
        )
        path.close()
        canvas.drawPath(path, paint!!)
    }

}