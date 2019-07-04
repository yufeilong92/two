package com.zzzh.akhalteke_shipper.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.view.custom.SlideLockView
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        sli.setmSlippingListenter(object :SlideLockView.Slipping{
            override fun endSlipping() {
                Toast.makeText(this@Main2Activity,"解锁", Toast.LENGTH_SHORT).show()
            }

            override fun startSlipping() {
                Toast.makeText(this@Main2Activity,"复原" +
                        "", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
