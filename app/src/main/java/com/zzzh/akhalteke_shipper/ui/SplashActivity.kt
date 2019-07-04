package com.zzzh.akhalteke_shipper.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * 第一次启动时候的，介绍页
 */
class SplashActivity : BaseActivity() {

    private val dotViews: MutableList<View> = mutableListOf()
    private val imageViewList: MutableList<View> = mutableListOf()
    private lateinit var whiteDot: Drawable
    private lateinit var mainColorDot: Drawable
    private var dotHeight: Int = 0
    private var dotSpace: Int = 0
    private val resIds = arrayOf(R.mipmap.welcome001, R.mipmap.welcome002)
    private var tempPosition: Int = 0

    private val number = 2
    private var ifJumpTo = true

    private val mHandle = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initView()

    }

    /**
     * 初始化页面
     */
    private fun initView() {
        whiteDot = resources.getDrawable(R.drawable.dot_rouse)
        mainColorDot = resources.getDrawable(R.drawable.dot_red)
        dotHeight = ToolUtils.dpTopx(mContext, 8f)
        dotSpace = ToolUtils.dpTopx(mContext, 5f)

        for (i in 0 until number) {
            var imageView: ImageView = ImageView(mContext)
            imageView.layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageResource(resIds[i])

            imageViewList.add(imageView)

            addDotView(i)
        }

//        var toView = LayoutInflater.from(mContext).inflate(R.layout.cell_splash_into, null)
//        val buttonView = toView.findViewById<View>(R.id.cell_splah_into_button)
//        buttonView.setOnClickListener {
//            routerTo.jumpToLogin()
//            finish()
//        }
//        imageViewList.add(toView)
//        addDotView(number-1)

        splash_pager.adapter = MyPageAdapter()
        splash_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position !== tempPosition) {
                    dotViews[position].background = mainColorDot
                    dotViews[tempPosition].background = whiteDot
                    tempPosition = position
                }

                if(position == 1){
                    mHandle.postDelayed({
                        if(ifJumpTo){
                            ifJumpTo = false
                            routerTo.jumpToLogin()
                            finish()
                        }
                    }, 3000)
                }else{
                    ifJumpTo = false
                }
            }
        })

        splash_pager.setOnTouchListener { v, event ->
            return@setOnTouchListener mGestureDetector.onTouchEvent(event)
        }

    }

    private val FLING_MIN_DISTANCE = 20// 移动最小距离
    private val FLING_MIN_VELOCITY = 200// 移动最大速度

    private val mGestureDetector: GestureDetector by lazy {
        GestureDetector(mContext, object : GestureDetector.OnGestureListener {
            override fun onShowPress(e: MotionEvent?) {
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return false
            }

            override fun onDown(e: MotionEvent?): Boolean {
                return false
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                if (splash_pager.currentItem == 1) {
                    if (e1!!.x - e2!!.x > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY && ifJumpTo) {
                        mHandle.postDelayed({
                            if(ifJumpTo){
                                ifJumpTo = false
                                routerTo.jumpToLogin()
                                finish()
                            }
                        }, 300)
                        return true
                    }
                }
                return false
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                return false
            }

            override fun onLongPress(e: MotionEvent?) {
            }
        })
    }

    /**
     * 添加下面的点
     * @param i Int
     */
    private fun addDotView(i: Int) {
        val dotView: View = View(mContext)
        dotView.apply {
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(dotHeight, dotHeight)
            params.leftMargin = dotSpace
            params.rightMargin = dotSpace
            layoutParams = params
            background = if (i == 0) {
                mainColorDot
            } else {
                whiteDot
            }
        }
        dotViews.add(dotView)
        splash_pager_indicator.addView(dotView)
    }

    private inner class MyPageAdapter : PagerAdapter() {

        override fun getCount(): Int {
            return number
        }
        // 需要实现以下四个方法

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imgView = imageViewList[position]
            container.addView(imgView)
            return imgView
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            // 销毁对应位置上的Object
            // super.destroyItem(container, position, object);
            container.removeView(obj as View)
        }
    }
}
