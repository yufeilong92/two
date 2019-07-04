package com.zzzh.akhalteke_shipper.view.page

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import com.lipo.utils.DisplayUtil
import com.zzzh.akhalteke_shipper.utils.ToolUtils

/**
 * @Author zfb
 * @Date 2019/5/22 11:35
 *
 * 不处理点击事件的scroll类
 *
 */
class NoClickScrollView(val mContext: Context, val attrs: AttributeSet) : NestedScrollView(mContext, attrs) {

    private val topH = DisplayUtil.dip2px(mContext, 240f)
    private var tY = topH
    private var mY = 0f
    private var mScrollY = 0
    private var stopY = 0
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                return if (ev.y > tY) {
                    super.dispatchTouchEvent(ev)
                } else {
                    false
                }
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
                if (stopY != scrollY) {
                    mScrollY = scrollY - mScrollY
                    tY -= mScrollY
                    mScrollY = scrollY
                    ToolUtils.log("scrollY：$scrollY,mScrollY:$mScrollY,tY:$tY")
                }
                stopY = scrollY
            }
            MotionEvent.ACTION_CANCEL -> {
            }
        }

        return super.dispatchTouchEvent(ev)
    }

}
