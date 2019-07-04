package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.BankInfo
import com.zzzh.akhalteke_shipper.utils.ToolUtils

class TransferAdapter(var context: Context, var infoList: MutableList<BankInfo>) :
    BaseQuickAdapter<BankInfo, BaseViewHolder>(R.layout.item_transfer, infoList) {

    override fun convert(helper: BaseViewHolder?, item: BankInfo?) {
        val position = helper!!.layoutPosition

        helper?.apply {
            setText(R.id.item_transfer_number, item!!.account)
//            setText(R.id.item_transfer_name, item!!.bank)
            setText(R.id.item_transfer_bank,item.bank)
            setText(R.id.item_transfer_text06, "备注：${item!!.comment}")
            setText(R.id.item_transfer_bankno, item!!.bankNumber)

            getView<View>(R.id.item_transfer_copy).setOnClickListener {
                ToolUtils.copyData(mContext,item!!.account)
            }
        }
    }

}