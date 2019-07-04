package com.zzzh.akhalteke_shipper.ui.transport

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.PagerFragmentAdapter
import com.zzzh.akhalteke_shipper.bean.DriverDetailInfo
import com.zzzh.akhalteke_shipper.bean.FragmentInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.fragment.TransRecordFragment
import com.zzzh.akhalteke_shipper.ui.transport.viewmodel.DriverOwnerViewModel
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.CallCarOwnerDialog
import kotlinx.android.synthetic.main.activity_driver_detail.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 司机简介
 */
class DriverDetailActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(DriverOwnerViewModel::class.java)
    }

    var driverId = ""//司机id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_detail)
        eventBus.register(this)

        driverId = intent.getStringExtra("driver_id")

        initView()
        initViewPager()
        initViewModel()

        driverId?.let {
            mViewModel.getCarInfo(it)
        }

    }

    private fun initView(){

    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })
        //获取页面数据监听
        mViewModel.mInfo.observe(this, Observer {
            fillView(it?:return@Observer)
        })

        //移除成功后监听
        mViewModel.successData.observe(this, Observer {
            showToast("移除成功")
            eventBus.post(MessageEvent(MessageEvent.FAMILY_CAR_UPDADE))
            finish()
        })
    }

    /**
     * 页面填充数据
     * @param info DriverDetailInfo
     */
    private fun fillView(info:DriverDetailInfo){
        showView()
        ImageLoadingUtils.loadNetImage(driver_detail_header,info.portrait?:"")
        driver_detail_name.text = info.name?:""
        driver_detail_phone.text = ToolUtils.phoneEncry(info.phone)
        driver_detail_content.text = "${info.plateNumber} ${info.carLength}米 ${info.carType} 载重${info.load}吨"

        if(info.ifFamiliarCar == "1"){
            driver_detail_label.visibility = View.VISIBLE
            driver_detail_cancel.visibility = View.VISIBLE
            driver_detail_cancel.setOnClickListener { mViewModel.deleteFamiliarCar(driverId?:"") }
        }else{
            driver_detail_label.visibility = View.GONE
            driver_detail_cancel.visibility = View.GONE
        }

        driver_detail_smart.getTabAt(0).findViewById<TextView>(R.id.tab_two_text).text = "交易记录（${info.orderTotal}）"
        driver_detail_smart.getTabAt(1).findViewById<TextView>(R.id.tab_two_text).text = "与我交易（${info.orderWithShipperTotal}）"

        driver_detail_call.setOnClickListener {
            toCheckInfo {
                CallCarOwnerDialog(mContext,info.phone).show()
            }
        }
    }

    /**
     * 初始化viewpager
     */
    fun initViewPager() {
        var pagers: MutableList<FragmentInfo> = mutableListOf()
        val titleNames: Array<String> = arrayOf("交易记录", "与我交易")

        pagers.add(FragmentInfo(TransRecordFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 0)
            arguments = bundle
        }, titleNames[0]))

        pagers.add(FragmentInfo(TransRecordFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 1)
            arguments = bundle
        }, titleNames[1]))

        driver_detail_pager.offscreenPageLimit = 1
        driver_detail_pager.adapter = PagerFragmentAdapter(supportFragmentManager, pagers)

        driver_detail_smart.setViewPager(driver_detail_pager)
        driver_detail_smart.setDividerColors(R.color.main_line)
    }

    /**
     * 显示页面信息
     */
    private fun showView(){
        driver_detail_car.visibility = View.VISIBLE
        driver_detail_view.visibility = View.VISIBLE
        driver_detail_call.visibility = View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }

}
