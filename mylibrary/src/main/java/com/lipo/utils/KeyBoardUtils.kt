package com.lipo.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyBoardUtils {

    /**
     * 打开软键盘
     *
     * @param mEditText
     * @param mContext
     */
    fun openKeybord(mEditText: EditText, mContext: Context) {
        val imm = mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     *
     * @param mContext
     * @param mEditText
     */
    fun closeKeybord(mEditText: EditText, mContext: Context) {
        val imm = mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

}
