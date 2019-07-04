package com.lipo.views

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter
import com.lipo.mylibrary.R
import com.lipo.utils.DisplayUtil

import java.util.ArrayList;

/**
 * 不规则item(text)
 */
class FlowLayout : ViewGroup {

    private var leftM = 0
    private var bottomM = 0

    //记录每个View的位置
    private val mChildPos = ArrayList<ChildPos>()
    private var textSize: Float = 0.toFloat()
    private var textColor: Int = 0
    private var textColorSelector: Int = 0
    private var shapeRadius: Float = 0.toFloat()
    private var shapeLineColor: Int = 0
    private var shapeBackgroundColor: Int = 0
    private var shapeBackgroundColorSelector: Int = 0
    private var shapeLineWidth: Float = 0.toFloat()
    private var deleteBtnColor: Int = 0
    /**
     * 是否是可删除模式
     */
    private var isDeleteMode: Boolean = false
    /**
     * 记录所有选中着的词
     */
    private val mAllSelectedWords = mutableListOf<String>()

    private inner class ChildPos(
        internal var left: Int,
        internal var top: Int,
        internal var right: Int,
        internal var bottom: Int
    )

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        initAttributes(context, attrs)
    }

    /**
     * 最终调用这个构造方法
     *
     * @param context  上下文
     * @param attrs    xml属性集合
     * @param defStyle Theme中定义的style
     */
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    /**
     * 流式布局属性设置
     *
     * @param context
     * @param attrs
     */
    @SuppressLint("ResourceAsColor")
    private fun initAttributes(context: Context, attrs: AttributeSet?) {
        @SuppressLint("Recycle")
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.KingoitFlowLayout)
        textSize = typedArray.getDimension(R.styleable.KingoitFlowLayout_flowLayoutTextSize, 16f)
        textColor = typedArray.getColor(R.styleable.KingoitFlowLayout_flowLayoutTextColor, Color.parseColor("#FF4081"))
        textColorSelector = typedArray.getResourceId(R.styleable.KingoitFlowLayout_flowLayoutTextColorSelector, 0)
        shapeRadius = typedArray.getDimension(R.styleable.KingoitFlowLayout_flowLayoutRadius, 40f)
        shapeLineColor =
            typedArray.getColor(R.styleable.KingoitFlowLayout_flowLayoutLineColor, Color.parseColor("#ADADAD"))
        shapeBackgroundColor =
            typedArray.getColor(R.styleable.KingoitFlowLayout_flowLayoutBackgroundColor, Color.parseColor("#c5cae9"))
        shapeBackgroundColorSelector =
            typedArray.getResourceId(R.styleable.KingoitFlowLayout_flowLayoutBackgroundColorSelector, 0)
        shapeLineWidth = typedArray.getDimension(R.styleable.KingoitFlowLayout_flowLayoutLineWidth, 4f)
        deleteBtnColor = typedArray.getColor(R.styleable.KingoitFlowLayout_flowLayoutDeleteBtnColor, Color.GRAY)

        leftM = DisplayUtil.dip2px(context,0f)
        bottomM = DisplayUtil.dip2px(context,0f)

    }

    /**
     * 测量宽度和高度
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //获取流式布局的宽度和模式
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        //获取流式布局的高度和模式
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        //使用wrap_content的流式布局的最终宽度和高度
        var width = 0
        var height = 0
        //记录每一行的宽度和高度
        var lineWidth = 0
        var lineHeight = 0
        //得到内部元素的个数
        val count = childCount
        mChildPos.clear()
        for (i in 0 until count) {
            //获取对应索引的view
            val child = getChildAt(i)
            //测量子view的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val lp = if (child.layoutParams is ViewGroup.MarginLayoutParams) {
                (child.layoutParams) as ViewGroup.MarginLayoutParams
            } else {
                MarginLayoutParams(child.layoutParams).apply {
                    leftMargin = leftM
                    rightMargin = leftM
                    bottomMargin = bottomM
                }
            }
            //子view占据的宽度
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            //子view占据的高度
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            //换行
            if (lineWidth + childWidth > widthSize - paddingLeft - paddingRight) {
                //取最大的行宽为流式布局宽度
                width = Math.max(width, lineWidth)
                //叠加行高得到流式布局高度
                height += lineHeight
                //重置行宽度为第一个View的宽度
                lineWidth = childWidth
                //重置行高度为第一个View的高度
                lineHeight = childHeight
                //记录位置
                mChildPos.add(
                    ChildPos(
                        paddingLeft + lp.leftMargin,
                        paddingTop + height + lp.topMargin,
                        paddingLeft + childWidth - lp.rightMargin,
                        paddingTop + height + childHeight - lp.bottomMargin
                    )
                )
            } else {  //不换行
                //记录位置
                mChildPos.add(
                    ChildPos(
                        paddingLeft + lineWidth + lp.leftMargin,
                        paddingTop + height + lp.topMargin,
                        paddingLeft + lineWidth + childWidth - lp.rightMargin,
                        paddingTop + height + childHeight - lp.bottomMargin
                    )
                )
                //叠加子View宽度得到新行宽度
                lineWidth += childWidth
                //取当前行子View最大高度作为行高度
                lineHeight = Math.max(lineHeight, childHeight)
            }
            //最后一个控件
            if (i == count - 1) {
                width = Math.max(lineWidth, width)
                height += lineHeight
            }
        }
        // 得到最终的宽高
        // 宽度：如果是AT_MOST模式，则使用我们计算得到的宽度值，否则遵循测量值
        // 高度：只要布局中内容的高度大于测量高度，就使用内容高度（无视测量模式）；否则才使用测量高度
        val flowLayoutWidth =
            if (widthMode == View.MeasureSpec.AT_MOST) width + paddingLeft + paddingRight else widthSize
        val flowLayoutHeight =
            if (heightMode == View.MeasureSpec.AT_MOST) height + paddingTop + paddingBottom else heightSize
        //真实高度
        realHeight = height + paddingTop + paddingBottom
        //测量高度
        measureHeight = heightSize
        if (heightMode == View.MeasureSpec.EXACTLY) {
            realHeight = Math.max(measureHeight, realHeight)
        }
        scrollable = realHeight > measureHeight
        // 设置最终的宽高
        setMeasuredDimension(flowLayoutWidth, flowLayoutHeight)
    }

    /**
     * 让ViewGroup能够支持margin属性
     */
    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(context, attrs)
    }

    /**
     * 设置每个View的位置
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            val pos = mChildPos[i]
            //设置View的左边、上边、右边底边位置
            child.layout(pos.left, pos.top, pos.right, pos.bottom)
        }
    }

    fun addItemView(inflater: LayoutInflater, tvName: String) {
        //加载 ItemView并设置名称，并设置名称
//        val view = inflater.inflate(R.layout.kingoit_flow_layout, this, false)
//        val delete = view.findViewById(R.id.delete)
//        if (isDeleteMode) {
//            delete.setVisibility(View.VISIBLE)
//        } else {
//            delete.setVisibility(View.GONE)
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            delete.setImageTintList(ColorStateList.valueOf(deleteBtnColor))
//        }
//        val textView = view.findViewById(R.id.value)
//        textView.setTextSize(textSize / context.resources.displayMetrics.scaledDensity)
//        if (textColorSelector != 0) {
//            val csl = resources.getColorStateList(textColorSelector)
//            textView.setTextColor(csl)
//        } else {
//            textView.setTextColor(textColor)
//        }
//        textView.setPadding(20, 4, 20, 4)
//        textView.setText(tvName)
//        //动态设置shape
//        val drawable = GradientDrawable()
//        drawable.cornerRadius = shapeRadius
//        drawable.setStroke(shapeLineWidth.toInt(), shapeLineColor)
//        if (shapeBackgroundColorSelector != 0) {
//            val csl = resources.getColorStateList(shapeBackgroundColorSelector)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                drawable.color = csl
//            }
//        } else {
//            drawable.setColor(shapeBackgroundColor)
//        }
//        textView.setBackgroundDrawable(drawable)

        //把 ItemView加入流式布局
//        this.addView(view)
    }

    fun isDeleteMode(): Boolean {
        return isDeleteMode
    }

    fun setDeleteMode(deleteMode: Boolean) {
        isDeleteMode = deleteMode
    }

    //---20180815---修复不可滑动bug----start----
    private var scrollable: Boolean = false // 是否可以滚动
    private var measureHeight: Int = 0 // 测量得到的高度
    private var realHeight: Int = 0 // 整个流式布局控件的实际高度
    private var scrolledHeight = 0 // 已经滚动过的高度
    private var startY: Int = 0 // 本次滑动开始的Y坐标位置
    private var offsetY: Int = 0 // 本次滑动的偏移量
    private var pointerDown: Boolean = false // 在ACTION_MOVE中，视第一次触发为手指按下，从第二次触发开始计入正式滑动

    /**
     * 滚动事件的处理，当布局可以滚动（内容高度大于测量高度）时，对手势操作进行处理
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 只有当布局可以滚动的时候（内容高度大于测量高度的时候），且处于拦截模式，才会对手势操作进行处理
        if (scrollable && isInterceptedTouch) {
            val currY = event.y.toInt()
            when (event.action) {
                // 因为ACTION_DOWN手势可能是为了点击布局中的某个子元素，因此在onInterceptTouchEvent()方法中没有拦截这个手势
                // 因此，在这个事件中不能获取到startY，也因此才将startY的获取移动到第一次滚动的时候进行
                MotionEvent.ACTION_DOWN -> {
                }
                // 当第一次触发ACTION_MOVE事件时，视为手指按下；以后的ACTION_MOVE事件才视为滚动事件
                MotionEvent.ACTION_MOVE ->
                    // 用pointerDown标志位只是手指是否已经按下
                    if (!pointerDown) {
                        startY = currY
                        pointerDown = true
                    } else {
                        offsetY = startY - currY // 下滑大于0
                        // 布局中的内容跟随手指的滚动而滚动
                        // 用scrolledHeight记录以前的滚动事件中滚动过的高度（因为不一定每一次滚动都是从布局的最顶端开始的）
                        this.scrollTo(0, scrolledHeight + offsetY)
                    }
                // 手指抬起时，更新scrolledHeight的值；
                // 如果滚动过界（滚动到高于布局最顶端或低于布局最低端的时候），设置滚动回到布局的边界处
                MotionEvent.ACTION_UP -> {
                    scrolledHeight += offsetY
                    if (scrolledHeight + offsetY < 0) {
                        this.scrollTo(0, 0)
                        scrolledHeight = 0
                    } else if (scrolledHeight + offsetY + measureHeight > realHeight) {
                        this.scrollTo(0, realHeight - measureHeight)
                        scrolledHeight = realHeight - measureHeight
                    }
                    // 手指抬起后别忘了重置这个标志位
                    pointerDown = false
                }
                else -> {
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 事件拦截，当手指按下或抬起的时候不进行拦截（因为可能这个操作只是点击了布局中的某个子元素）；
     * 当手指移动的时候，才将事件拦截；
     * 因增加最小滑动距离防止点击时误触滑动
     */
    private var isInterceptedTouch: Boolean = false
    private var startYY = 0
    private var pointerDownY: Boolean = false
    private val minDistance = 10

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var intercepted = false
        val currY = ev.y.toInt()
        var offsetY = 0
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                pointerDownY = true
                intercepted = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (pointerDownY) {
                    startYY = currY
                } else {
                    offsetY = currY - startYY
                }
                pointerDownY = false
                intercepted = Math.abs(offsetY) > minDistance
            }
            MotionEvent.ACTION_UP ->
                // 手指抬起后别忘了重置这个标志位
                intercepted = false
            else -> {
            }
        }
        isInterceptedTouch = intercepted
        return intercepted
    }

    //---20180815---修复不可滑动bug----end----

    /**
     * 流式布局显示
     * Toast.makeText(FlowLayoutActivity.this, keywords, Toast.LENGTH_SHORT).show();
     *
     * @param list
     */
    fun showTag(list: MutableList<String>, listener: ItemClickListener) {
        removeAllViews()
        for (i in list.indices) {
            val keywords = list[i]
            addItemView(LayoutInflater.from(context), keywords)
            getChildAt(i).setOnClickListener {
                if (isDeleteMode()) {
                    list.remove(keywords)
                    showTag(list, listener)
                } else {
                    val child = getChildAt(i)
                    child.isSelected = !child.isSelected
                    if (child.isSelected) {
                        mAllSelectedWords.add(list[i])
                    } else {
                        mAllSelectedWords.remove(list[i])
                    }
                    listener.onClick(keywords, mAllSelectedWords)
                }
            }
        }
    }

    fun toSetAdapter(adapter: BaseAdapter) {
        removeAllViews()
        for (i in 0 until adapter.count) {
            this.addView(adapter.getView(i, null, null))
        }
    }

    interface ItemClickListener {
        /**
         * item 点击事件
         *
         * @param currentSelectedkeywords
         * @param allSelectedKeywords
         */
        fun onClick(currentSelectedkeywords: String, allSelectedKeywords: MutableList<String>)
    }
}