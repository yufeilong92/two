package com.zzzh.akhalteke_shipper.view.helper

import android.view.View
import android.widget.TextView
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import kotlinx.android.synthetic.main.cell_main_send_tab.view.*

class FilterTabHelper(
    val mContext: BaseActivity,
    val mView: View
) {

    private lateinit var fromText:TextView
    private lateinit var toText:TextView
    private lateinit var pxText:TextView
    private lateinit var sxText:TextView

    init {
        mView.apply {
            fromText = cell_sendtab_text
            toText = cell_sendtab_to_text
            pxText = cell_sendtab_px_text
            sxText = cell_sendtab_sx_text

            cell_sendtab_from.setOnClickListener {  }
            cell_sendtab_to.setOnClickListener {  }
            cell_sendtab_px.setOnClickListener {  }
            cell_sendtab_sx.setOnClickListener {  }
        }
    }

}