package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.OrderAgreementInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import com.zzzh.akhalteke_shipper.utils.GlideUtil
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.CallCarOwnerDialog
import kotlinx.android.synthetic.main.activity_protocol_new.*
import kotlinx.android.synthetic.main.gm_title_layout_transparent_right.*

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.find
 * @Package com.zzzh.akhalteke_shipper.ui.find
 * @Email : yufeilong92@163.com
 * @Time :2019/5/28 17:26
 * @Purpose :货物新运输详情
 */
class ProtocolNewActivity : BaseActivity() {
    private lateinit var orderId: String//订单id

    private val mViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(this).get(OrderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protocol_new)
        gm_tv_activity_title.text = "货物运输协议"
        tv_gm_activity_right_title.text = "联系司机"
        orderId = intent.getStringExtra("order_id")

        initViewModel()
        mViewModel.agreementInfo(orderId = orderId)
        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        web_protocol_agreement.loadUrl("${RetrofitFactory.BASE_URL}agreement/goods.html")
    }

    /**
     * 初始化viewpager
     */
    fun initViewPager() {
//        var pagers: MutableList<FragmentInfo> = mutableListOf()
//        val titleNames: Array<String> = arrayOf("发货人", "承运司机")

/*
        pagers.add(FragmentInfo(ProtocolManFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 0)
            arguments = bundle
        }, titleNames[0]))

        pagers.add(FragmentInfo(ProtocolManFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 1)
            arguments = bundle
        }, titleNames[1]))

*/

//        protocol_pager.adapter = PagerFragmentAdapter(supportFragmentManager, pagers)

//        protocol_smart.setViewPager(protocol_pager)
//        protocol_smart.setDividerColors(R.color.main_line)

    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, android.arch.lifecycle.Observer {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })

        //货物运输协议信息
        mViewModel.agreementInfo.observe(this, Observer {
            fillData(it ?: return@Observer)
        })
    }

    /**
     * 填充数据
     * @param info OrderAgreementInfo
     */
    private fun fillData(info: OrderAgreementInfo) {
        nsv_protocol_root.visibility = View.VISIBLE
        tv_protocol_order_number.text = info.orderId
        tv_protocol_title_content.text = ToolUtils.adrSpannStr(mContext, info.loadAddress, info.unloadAddress)
        tv_protocol_weight.text = info.weightVolume
        tv_protocol_car_type.text = info.carTypeLength
        tv_protocol_count.text = info.companyAmount
        tv_protocol_pay_time.text = info.payTime
        tv_protocol_goods_name.text = info.goodsName
        tv_protocol_load_time.text = TimeUntils.getStrTime(info.loadTime)
        tv_protocol_dao_time.text = TimeUntils.getStrTime(info.unloadTime)
        tv_protocol_dao_address.text = info.loadAddress
        tv_protocol_unload_address.text = info.unloadAddress
        tv_protocol_other.text = info.appointment
        tv_gm_activity_right_title.setOnClickListener {
            toCheckInfo {
                CallCarOwnerDialog(mContext, info.ownerPhone).show()
            }
        }
        tv_protocol_time.text=info.launchTime
        setTvBg(true,false)
        showPlateAddess(true)
        showSendImager(true,info.shipperPortrait)
        setInfom(info.shipperName,info.shipperPhone,info.shipperAddress)
        //发货人
        tv_protocol_consigner.setOnClickListener {
            setTvBg(true,false)
            showPlateAddess(true)
            showSendImager(true,info.shipperPortrait)
            setInfom(info.shipperName,info.shipperPhone,info.shipperAddress)
        }
        //司机
        tv_protocol_driver.setOnClickListener {
            setTvBg(false,true)
            showPlateAddess(false)
            showSendImager(true,info.driverPortrait)
            setInfom(info.ownerName,info.ownerPhone,info.ownerCarNumber)
        }

    }

    /**
     * 显示公司地址或者车牌号
     */
    private  fun showPlateAddess(isShow:Boolean){
        rl_protocol_address.visibility=if (isShow) View.VISIBLE else View.GONE
        rl_protocol_plate.visibility=if(isShow) View.GONE else View.VISIBLE
    }
    private  fun showSendImager(isShow: Boolean,path:String){
        iv_protocol_send_hear.visibility=if (isShow) View.VISIBLE else View.GONE
        iv_protocol_driver_hear.visibility=if (isShow) View.GONE else View.VISIBLE
        if (isShow){
            GlideUtil.loadQuadRangleImagerWithHttp(mContext,iv_protocol_send_hear,path)
        }else{
            GlideUtil.loadQuadRangleImagerWithHttp(mContext,iv_protocol_driver_hear,path)
        }
    }
    private fun setInfom(name:String,phone:String,addressOrplate:String){
        tv_protocol_name.text=name
        tv_protocol_phone.text=phone
        tv_protocol_address.text=addressOrplate
        tv_protocol_plate.text=addressOrplate
    }
    private  fun setTvBg(send:Boolean, driver:Boolean){
        tv_protocol_consigner.setBackgroundResource(if (send) R.drawable.bg_protocol_agreement_black else R.drawable.bg_protocol_agreement_tran)
        tv_protocol_consigner.setTextColor(resources.getColor(if (send) R.color.white else R.color.main_text3))
        tv_protocol_driver.setBackgroundResource(if (driver) R.drawable.bg_protocol_agreement_black else R.drawable.bg_protocol_agreement_tran)
        tv_protocol_driver.setTextColor(resources.getColor(if (driver) R.color.white else R.color.main_text3))
    }

}
