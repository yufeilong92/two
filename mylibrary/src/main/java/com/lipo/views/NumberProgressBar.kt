package com.lipo.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.lipo.mylibrary.R


/**
 * Created by daimajia on 14-4-30.
 */
class NumberProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val default_text_color = Color.rgb(66, 145, 241)
    private val default_reached_color = Color.rgb(66, 145, 241)
    private val default_unreached_color = Color.rgb(204, 204, 204)
    private val default_progress_text_offset: Float
    private val default_text_size: Float
    private val default_reached_bar_height: Float
    private val default_unreached_bar_height: Float
    var max = 100
        set(maxProgress) {
            if (maxProgress > 0) {
                field = maxProgress
                invalidate()
            }
        }
    /**
     * Current progress, can not exceed the max progress.
     */
    var progress = 0
        set(progress) {
            if (progress <= max && progress >= 0) {
                field = progress
                invalidate()
            }
        }
    /**
     * The progress area bar color.
     */
    private var mReachedBarColor: Int = 0
    /**
     * The bar unreached area color.
     */
    private var mUnreachedBarColor: Int = 0
    /**
     * The progress text color.
     */
    /**
     * Get progress text color.
     *
     * @return progress text color.
     */
    var textColor: Int = 0
        private set
    /**
     * The progress text size.
     */
    private var mTextSize: Float = 0.toFloat()
    /**
     * The height of the reached area.
     */
    var reachedBarHeight: Float = 0.toFloat()
    /**
     * The height of the unreached area.
     */
    var unreachedBarHeight: Float = 0.toFloat()
    /**
     * The suffix of the number.
     */
    var suffix: String? = "%"
        set(suffix) = if (suffix == null) {
            field = ""
        } else {
            field = suffix
        }
    /**
     * The prefix.
     */
    var prefix: String? = ""
        set(prefix) = if (prefix == null)
            field = ""
        else {
            field = prefix
        }
    /**
     * The width of the text that to be drawn.
     */
    private var mDrawTextWidth: Float = 0.toFloat()

    /**
     * The drawn text start.
     */
    private var mDrawTextStart: Float = 0.toFloat()

    /**
     * The drawn text end.
     */
    private var mDrawTextEnd: Float = 0.toFloat()

    /**
     * The text that to be drawn in onDraw().
     */
    private var mCurrentDrawText: String? = null

    /**
     * The Paint of the reached area.
     */
    private var mReachedBarPaint: Paint? = null
    /**
     * The Paint of the unreached area.
     */
    private var mUnreachedBarPaint: Paint? = null
    /**
     * The Paint of the progress text.
     */
    private var mTextPaint: Paint? = null

    /**
     * Unreached bar area to draw rect.
     */
    private val mUnreachedRectF = RectF(0f, 0f, 0f, 0f)
    /**
     * Reached bar area rect.
     */
    private val mReachedRectF = RectF(0f, 0f, 0f, 0f)

    /**
     * The progress text offset.
     */
    private val mOffset: Float

    /**
     * Determine if need to draw unreached area.
     */
    private var mDrawUnreachedBar = true

    private var mDrawReachedBar = true

    var progressTextVisibility = true
        private set

    /**
     * Listener
     */
    private var mListener: OnProgressBarListener? = null
    private val mCicrlePaint: Paint? = null

    /**
     * Get progress text size.
     *
     * @return progress text size.
     */
    var progressTextSize: Float
        get() = mTextSize
        set(textSize) {
            this.mTextSize = textSize
            mTextPaint!!.textSize = mTextSize
            invalidate()
        }

    var unreachedBarColor: Int
        get() = mUnreachedBarColor
        set(barColor) {
            this.mUnreachedBarColor = barColor
            mUnreachedBarPaint!!.color = mUnreachedBarColor
            invalidate()
        }

    var reachedBarColor: Int
        get() = mReachedBarColor
        set(progressColor) {
            this.mReachedBarColor = progressColor
            mReachedBarPaint!!.color = mReachedBarColor
            invalidate()
        }

    init {

        default_reached_bar_height = dp2px(1.5f)
        default_unreached_bar_height = dp2px(1.0f)
        default_text_size = sp2px(10f)
        default_progress_text_offset = dp2px(3.0f)

        //load styled attributes.
        val attributes =
            context.theme.obtainStyledAttributes(attrs, R.styleable.UpdateAppNumberProgressBar, defStyleAttr, 0)

        mReachedBarColor =
            attributes.getColor(R.styleable.UpdateAppNumberProgressBar_progress_reached_color, default_reached_color)
        mUnreachedBarColor = attributes.getColor(
            R.styleable.UpdateAppNumberProgressBar_progress_unreached_color,
            default_unreached_color
        )
        textColor = attributes.getColor(R.styleable.UpdateAppNumberProgressBar_progress_text_color, default_text_color)
        mTextSize =
            attributes.getDimension(R.styleable.UpdateAppNumberProgressBar_progress_text_size, default_text_size)

        reachedBarHeight = attributes.getDimension(
            R.styleable.UpdateAppNumberProgressBar_progress_reached_bar_height,
            default_reached_bar_height
        )
        unreachedBarHeight = attributes.getDimension(
            R.styleable.UpdateAppNumberProgressBar_progress_unreached_bar_height,
            default_unreached_bar_height
        )
        mOffset = attributes.getDimension(
            R.styleable.UpdateAppNumberProgressBar_progress_text_offset,
            default_progress_text_offset
        )

        val textVisible =
            attributes.getInt(R.styleable.UpdateAppNumberProgressBar_progress_text_visibility, PROGRESS_TEXT_VISIBLE)
        if (textVisible != PROGRESS_TEXT_VISIBLE) {
            progressTextVisibility = false
        }

        progress = attributes.getInt(R.styleable.UpdateAppNumberProgressBar_progress_current, 0)
        max = attributes.getInt(R.styleable.UpdateAppNumberProgressBar_progress_max, 100)

        attributes.recycle()
        initializePainters()
    }

    override fun getSuggestedMinimumWidth(): Int {
        return mTextSize.toInt()
    }

    override fun getSuggestedMinimumHeight(): Int {
        return Math.max(mTextSize.toInt(), Math.max(reachedBarHeight.toInt(), unreachedBarHeight.toInt()))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false))
    }

    private fun measure(measureSpec: Int, isWidth: Boolean): Int {
        var result: Int
        val mode = View.MeasureSpec.getMode(measureSpec)
        val size = View.MeasureSpec.getSize(measureSpec)
        val padding = if (isWidth) paddingLeft + paddingRight else paddingTop + paddingBottom
        if (mode == View.MeasureSpec.EXACTLY) {
            result = size
        } else {
            result = if (isWidth) suggestedMinimumWidth else suggestedMinimumHeight
            result += padding
            if (mode == View.MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size)
                } else {
                    result = Math.min(result, size)
                }
            }
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        if (progressTextVisibility) {
            calculateDrawRectF()
        } else {
            calculateDrawRectFWithoutProgressText()
        }

        if (mDrawReachedBar) {
            canvas.drawRect(mReachedRectF, mReachedBarPaint!!)
        }

        if (mDrawUnreachedBar) {
            canvas.drawRect(mUnreachedRectF, mUnreachedBarPaint!!)
        }

        if (progressTextVisibility) {
            //            float start = (mUnreachedRectF.left + mReachedRectF.right) / 2.0f;
            //            float radius = (mUnreachedRectF.left - mReachedRectF.right) / 2.0f;
            //            canvas.drawCircle(start, getHeight() / 2.0f, radius, mCicrlePaint);
            canvas.drawText(mCurrentDrawText!!, mDrawTextStart, mDrawTextEnd, mTextPaint!!)
            //            Log.d("NumberProgressBar", mCurrentDrawText);
            //            Log.d("NumberProgressBar", "mDrawTextStart:" + mDrawTextStart);
            //            Log.d("NumberProgressBar", "mDrawTextEnd:" + mDrawTextEnd);
            //            Log.d("NumberProgressBar", "start:" + start);
        }


    }

    private fun initializePainters() {
        mReachedBarPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mReachedBarPaint!!.color = mReachedBarColor

        mUnreachedBarPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mUnreachedBarPaint!!.color = mUnreachedBarColor

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint!!.color = textColor
        mTextPaint!!.textSize = mTextSize

        //        mCicrlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //        mCicrlePaint.setColor(Color.parseColor("#66ff6666"));
    }

    private fun calculateDrawRectFWithoutProgressText() {
        mReachedRectF.left = paddingLeft.toFloat()
        mReachedRectF.top = height / 2.0f - reachedBarHeight / 2.0f
        mReachedRectF.right = (width - paddingLeft - paddingRight) / (max * 1.0f) * progress + paddingLeft
        mReachedRectF.bottom = height / 2.0f + reachedBarHeight / 2.0f

        mUnreachedRectF.left = mReachedRectF.right
        mUnreachedRectF.right = (width - paddingRight).toFloat()
        mUnreachedRectF.top = height / 2.0f + -unreachedBarHeight / 2.0f
        mUnreachedRectF.bottom = height / 2.0f + unreachedBarHeight / 2.0f
    }

    private fun calculateDrawRectF() {

        mCurrentDrawText = String.format("%d", progress * 100 / max)
        mCurrentDrawText = prefix + mCurrentDrawText + suffix
        mDrawTextWidth = mTextPaint!!.measureText(mCurrentDrawText)

        if (progress == 0) {
            mDrawReachedBar = false
            mDrawTextStart = paddingLeft.toFloat()
        } else {
            mDrawReachedBar = true
            mReachedRectF.left = paddingLeft.toFloat()
            mReachedRectF.top = height / 2.0f - reachedBarHeight / 2.0f
            mReachedRectF.right = (width - paddingLeft - paddingRight) / (max * 1.0f) * progress - mOffset + paddingLeft
            mReachedRectF.bottom = height / 2.0f + reachedBarHeight / 2.0f
            mDrawTextStart = mReachedRectF.right + mOffset
        }

        mDrawTextEnd = (height / 2.0f - (mTextPaint!!.descent() + mTextPaint!!.ascent()) / 2.0f).toInt().toFloat()

        if (mDrawTextStart + mDrawTextWidth >= width - paddingRight) {
            mDrawTextStart = width.toFloat() - paddingRight.toFloat() - mDrawTextWidth
            mReachedRectF.right = mDrawTextStart - mOffset
        }

        val unreachedBarStart = mDrawTextStart + mDrawTextWidth + mOffset
        if (unreachedBarStart >= width - paddingRight) {
            mDrawUnreachedBar = false
        } else {
            mDrawUnreachedBar = true
            mUnreachedRectF.left = unreachedBarStart
            mUnreachedRectF.right = (width - paddingRight).toFloat()
            mUnreachedRectF.top = height / 2.0f + -unreachedBarHeight / 2.0f
            mUnreachedRectF.bottom = height / 2.0f + unreachedBarHeight / 2.0f
        }
    }

    fun setProgressTextColor(textColor: Int) {
        this.textColor = textColor
        mTextPaint!!.color = this.textColor
        invalidate()
    }

    fun incrementProgressBy(by: Int) {
        if (by > 0) {
            progress = progress + by
        }

        if (mListener != null) {
            mListener!!.onProgressChange(progress, max)
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState())
        bundle.putInt(INSTANCE_TEXT_COLOR, textColor)
        bundle.putFloat(INSTANCE_TEXT_SIZE, progressTextSize)
        bundle.putFloat(INSTANCE_REACHED_BAR_HEIGHT, reachedBarHeight)
        bundle.putFloat(INSTANCE_UNREACHED_BAR_HEIGHT, unreachedBarHeight)
        bundle.putInt(INSTANCE_REACHED_BAR_COLOR, reachedBarColor)
        bundle.putInt(INSTANCE_UNREACHED_BAR_COLOR, unreachedBarColor)
        bundle.putInt(INSTANCE_MAX, max)
        bundle.putInt(INSTANCE_PROGRESS, progress)
        bundle.putString(INSTANCE_SUFFIX, suffix)
        bundle.putString(INSTANCE_PREFIX, prefix)
        bundle.putBoolean(INSTANCE_TEXT_VISIBILITY, progressTextVisibility)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            textColor = state.getInt(INSTANCE_TEXT_COLOR)
            mTextSize = state.getFloat(INSTANCE_TEXT_SIZE)
            reachedBarHeight = state.getFloat(INSTANCE_REACHED_BAR_HEIGHT)
            unreachedBarHeight = state.getFloat(INSTANCE_UNREACHED_BAR_HEIGHT)
            mReachedBarColor = state.getInt(INSTANCE_REACHED_BAR_COLOR)
            mUnreachedBarColor = state.getInt(INSTANCE_UNREACHED_BAR_COLOR)
            initializePainters()
            max = state.getInt(INSTANCE_MAX)
            progress = state.getInt(INSTANCE_PROGRESS)
            prefix = state.getString(INSTANCE_PREFIX)
            suffix = state.getString(INSTANCE_SUFFIX)
            setProgressTextVisibility(if (state.getBoolean(INSTANCE_TEXT_VISIBILITY)) ProgressTextVisibility.VISIBLE else ProgressTextVisibility.INVISIBLE)
            super.onRestoreInstanceState(state.getParcelable(INSTANCE_STATE))
            return
        }
        super.onRestoreInstanceState(state)
    }

    fun dp2px(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    fun sp2px(sp: Float): Float {
        val scale = resources.displayMetrics.scaledDensity
        return sp * scale
    }

    fun setProgressTextVisibility(visibility: ProgressTextVisibility) {
        progressTextVisibility = visibility == ProgressTextVisibility.VISIBLE
        invalidate()
    }

    fun setOnProgressBarListener(listener: OnProgressBarListener) {
        mListener = listener
    }

    enum class ProgressTextVisibility {
        VISIBLE, INVISIBLE
    }

    interface OnProgressBarListener {

        fun onProgressChange(current: Int, max: Int)
    }

    companion object {

        /**
         * For save and restore instance of progressbar.
         */
        private val INSTANCE_STATE = "saved_instance"
        private val INSTANCE_TEXT_COLOR = "text_color"
        private val INSTANCE_TEXT_SIZE = "text_size"
        private val INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height"
        private val INSTANCE_REACHED_BAR_COLOR = "reached_bar_color"
        private val INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height"
        private val INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color"
        private val INSTANCE_MAX = "max"
        private val INSTANCE_PROGRESS = "progress"
        private val INSTANCE_SUFFIX = "suffix"
        private val INSTANCE_PREFIX = "prefix"
        private val INSTANCE_TEXT_VISIBILITY = "text_visibility"
        private val PROGRESS_TEXT_VISIBLE = 0
    }

}
