package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.PagerFragmentAdapter
import com.zzzh.akhalteke_shipper.bean.FragmentInfo
import com.zzzh.akhalteke_shipper.bean.OrderAgreementInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.fragment.ProtocolManFragment
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.CallCarOwnerDialog
import kotlinx.android.synthetic.main.activity_protocol.*

/**
 * 货物运输协议详情
 */
class ProtocolActivity : BaseActivity() {

    private lateinit var orderId: String//订单id

    private val mViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(this).get(OrderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protocol)

        orderId = intent.getStringExtra("order_id")

        initViewPager()
        initViewModel()
        mViewModel.agreementInfo(orderId = orderId)
        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        protocol_web.loadUrl("${RetrofitFactory.BASE_URL}agreement/goods.html")
    }

    /**
     * 初始化viewpager
     */
    fun initViewPager() {
        var pagers: MutableList<FragmentInfo> = mutableListOf()
        val titleNames: Array<String> = arrayOf("发货人", "承运司机")

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


        protocol_pager.adapter = PagerFragmentAdapter(supportFragmentManager, pagers)

        protocol_smart.setViewPager(protocol_pager)
        protocol_smart.setDividerColors(R.color.main_line)

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
        protocol_no.text = "运单编号：${info.orderId}"
        protocol_view01.visibility = View.VISIBLE
        protocol_address.text = ToolUtils.adrSpannStr(mContext,info.loadAddress,info.unloadAddress)
        protocol_wv.text = info.weightVolume
        protocol_tl.text = info.carTypeLength
        protocol_cost.text = info.companyAmount
        protocol_pay_time.text = info.payTime
        protocol_goodsname.text = info.goodsName
        protocol_fromtime.text = TimeUntils.getStrTime(info.loadTime)
        protocol_totime.text = TimeUntils.getStrTime(info.unloadTime)
        protocol_fromadr.text = info.loadAddress
        protocol_toadr.text = info.unloadAddress
        protocol_appointment.text = info.appointment

        protocol_top.onClickTextButton {
            routerTo.callPhone(info.ownerPhone)
        }

        when (info.status) {//	状态1同意2待确认3拒绝
            "1" -> {
                protocol_status_icon.setImageResource(R.mipmap.icon_protocol_success)
            }
            "2" -> {
                protocol_status_icon.setImageResource(R.mipmap.icon_protocol_sure)
            }
            "3" -> {
                protocol_status_icon.setImageResource(R.mipmap.icon_protocol_cancel)
            }
        }

        /**
         * 联系司机
         */
        protocol_top.onClickTextButton {
            toCheckInfo {
                CallCarOwnerDialog(mContext,info.ownerPhone).show()
            }
        }
    }

}
