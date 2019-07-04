package com.zzzh.akhalteke_shipper.view.helper

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.lipo.utils.KeyBoardUtils
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import kotlinx.android.synthetic.main.cell_getcode.view.*

abstract class GetCodeHelper(
    val mContext: BaseActivity,
    val mView: View
) {

    init {
        mView.apply {
            val  mViews = arrayOf(
                login_getcode_code1,
                login_getcode_code2,
                login_getcode_code3,
                login_getcode_code4,
                login_getcode_code5,
                login_getcode_code6
            )

            login_getcode_edit.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val editStr = login_getcode_edit.text.toString().trim()
                    val length = editStr.length
                    for (i in 0..5){
                        mViews[i].text = ""
                    }
                    for (i in 0 until length) {
                        mViews[i].text = editStr.substring(i, i + 1)
                    }

                    if (length == 6) {
                        submit(editStr)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            setOnClickListener {  KeyBoardUtils.openKeybord(login_getcode_edit,mContext) }
        }

    }

    fun closeKeyBord(){
        mView.apply {
        KeyBoardUtils.closeKeybord(login_getcode_edit,mContext)
        }
    }

    fun getTextCode(): String {
        mView.apply {
            return login_getcode_edit.text.toString().trim()
        }
    }

    fun clearCode(){

    }

    abstract fun submit(code:String)

}