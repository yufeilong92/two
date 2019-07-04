package com.zzzh.akhalteke_shipper.view.page


import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.zzzh.akhalteke_shipper.R
import kotlinx.android.synthetic.main.cell_title.view.*

class MyTitleView: FrameLayout {

    constructor(mContext: Context?) : super(mContext)
    constructor(mContext: Context?, attrs: AttributeSet?) : super(mContext, attrs){

        var tda: TypedArray = mContext!!.obtainStyledAttributes(attrs, R.styleable.MyTitleStyle)

        val rightIcon = tda.getResourceId(R.styleable.MyTitleStyle_rightIcon,0)
        val titleName = tda.getString(R.styleable.MyTitleStyle_titleName)
        val rightText = tda.getString(R.styleable.MyTitleStyle_rightText)
        val isBack = tda.getBoolean(R.styleable.MyTitleStyle_isBack,true)
        val bgStyle = tda.getInteger(R.styleable.MyTitleStyle_bgStyle,0)

        val view = LayoutInflater.from(mContext).inflate(R.layout.cell_title, null)
        addView(view)

        if(isBack){
            cell_title_back.visibility = View.VISIBLE
        }else{
            cell_title_back.visibility = View.GONE
        }

        if(titleName!=null){
            cell_title_title.text = titleName
        }

        if(rightText!=null){
            cell_title_text_button.visibility = View.VISIBLE
            cell_title_text_button.text = rightText
        }else{
            cell_title_text_button.visibility = View.GONE
        }

        if(rightIcon!=0){
            cell_title_image_button.visibility = View.VISIBLE
            cell_title_image_button.setImageResource(rightIcon)
        }else{
            cell_title_image_button.visibility = View.GONE
        }

//        if(bgStyle == 0){
//            cell_title_view.setBackgroundColor(resources.getColor(R.color.main_color))
//            cell_title_text_button.setTextColor(resources.getColor(R.color.white))
//            cell_title_title.setTextColor(resources.getColor(R.color.white))
//            cell_title_back.setImageResource(R.mipmap.back_black_icon)
//        }else if(bgStyle == 1){
//            cell_title_view.setBackgroundColor(resources.getColor(R.color.transparent))
//            cell_title_text_button.setTextColor(resources.getColor(R.color.main_text3))
//            cell_title_title.setTextColor(resources.getColor(R.color.main_text3))
////            cell_title_back.setImageResource(R.mipmap.back)
//        }

        cell_title_back.setOnClickListener {
            (mContext as Activity).apply {
                finish()
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out)
            }
        }
    }

    fun toSetTitleName(titleName:String){
        cell_title_title.text = titleName
    }

    fun onClickTextButton(onclick:()->Unit){
        cell_title_text_button.setOnClickListener {
            onclick()
        }
    }

    fun onClickImageButton(onclick:()->Unit){
        cell_title_image_button.setOnClickListener {
            onclick()
        }
    }

    /**
     * 重写返回按钮
     */
    fun onClickBackButton(onclick: () -> Unit){
        cell_title_back.setOnClickListener {
            onclick()
        }
    }

    fun modifyTextButton(textStr:String){
        cell_title_text_button.text = textStr
    }

    fun showingBack(isToBack:Boolean){
        cell_title_back.visibility = if(isToBack){View.VISIBLE}else{View.GONE}
    }

}