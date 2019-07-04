package com.zzzh.akhalteke_shipper.ui.mine.wallet

import android.os.Bundle
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_withdrawing.*

/**
 * 提现记录过程
 */
class WithdrawingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdrawing)
    }


    private fun step01(){
        withdrawing_dot01.setBackgroundResource(R.drawable.dot_withdrawing_s)
        withdrawing_line01.setBackgroundResource(R.color.withdraw_red)
        withdrawing_text03.setTextColor(resources.getColor(R.color.withdraw_red))

        withdrawing_dot02.setBackgroundResource(R.drawable.dot_withdrawing_n)
        withdrawing_line02.setBackgroundResource(R.color.main_line)
        withdrawing_text04.setTextColor(resources.getColor(R.color.main_text9))

        withdrawing_dot03.setBackgroundResource(R.drawable.dot_withdrawing_n)
        withdrawing_text04.setTextColor(resources.getColor(R.color.main_text9))
    }

    private fun step02(){
        withdrawing_dot01.setBackgroundResource(R.drawable.dot_withdrawing_s)
        withdrawing_line01.setBackgroundResource(R.color.withdraw_red)
        withdrawing_text03.setTextColor(resources.getColor(R.color.withdraw_red))

        withdrawing_dot02.setBackgroundResource(R.drawable.dot_withdrawing_s)
        withdrawing_line02.setBackgroundResource(R.color.withdraw_red)
        withdrawing_text04.setTextColor(resources.getColor(R.color.withdraw_red))

        withdrawing_dot03.setBackgroundResource(R.drawable.dot_withdrawing_n)
        withdrawing_text04.setTextColor(resources.getColor(R.color.main_text9))
    }

    private fun step03(){
        withdrawing_dot01.setBackgroundResource(R.drawable.dot_withdrawing_s)
        withdrawing_line01.setBackgroundResource(R.color.withdraw_red)
        withdrawing_text03.setTextColor(resources.getColor(R.color.withdraw_red))

        withdrawing_dot02.setBackgroundResource(R.drawable.dot_withdrawing_s)
        withdrawing_line02.setBackgroundResource(R.color.withdraw_red)
        withdrawing_text04.setTextColor(resources.getColor(R.color.withdraw_red))

        withdrawing_dot03.setBackgroundResource(R.drawable.dot_withdrawing_s)
        withdrawing_text04.setTextColor(resources.getColor(R.color.withdraw_red))
    }
}
