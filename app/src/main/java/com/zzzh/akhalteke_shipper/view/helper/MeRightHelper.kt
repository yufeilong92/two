package com.zzzh.akhalteke_shipper.view.helper

import android.view.View
import kotlinx.android.synthetic.main.cell_me_right.view.*

/**
 * @Author zfb
 * @Date 2019/5/22 11:06
 *
 * 我的里面向右跳转的辅助页面  对应界面cell_me_right
 *
 */

class MeRightHelper(val mView: View, val iconId: Int, val name: String) {

    init {
        mView.apply {
            cell_me_right_icon.setImageResource(iconId)
            cell_me_right_name.text = name
        }
    }

}