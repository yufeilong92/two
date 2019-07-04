package com.zzzh.akhalteke_shipper.ui.transport

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.transport.viewmodel.SendGoodsViewModel
import com.zzzh.akhalteke_shipper.ui.transport.viewmodel.TranDetailViewModel
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.utils.ZzzhUtils
import kotlinx.android.synthetic.main.activity_transport_new_detail.*
import kotlinx.android.synthetic.main.gm_title_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.transport
 * @Package com.zzzh.akhalteke_shipper.ui.transport
 * @Email : yufeilong92@163.com
 * @Time :2019/5/29 9:52
 * @Purpose :发货详情
 */
class TransportNewDetailActivity : BaseActivity() {
    val mViewModel: TranDetailViewModel by lazy {
        ViewModelProviders.of(this).get(TranDetailViewModel::class.java)
    }
    private val goodsViewModel: SendGoodsViewModel by lazy {
        ViewModelProviders.of(this).get(SendGoodsViewModel::class.java)
    }
    private var entryTemp = 0//0发货中，1发货历史，2常用地址
    private var goodsId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport_new_detail)
        gm_tv_activity_title.text = "详情"
        EventBus.getDefault().register(this)
        entryTemp = intent.getIntExtra("entry_temp", 0)
        goodsId = intent.getStringExtra("goods_id")

        initView()
        initViewModel()
        when (entryTemp) {
            0, 1 -> {
                mViewModel.goodsDetails(goodsId)
            }
            2 -> {
                mViewModel.oftenGoodsDetail(goodsId)
            }
        }
    }

    /**
     * 初始化页面
     */
    private fun initView() {
        when (entryTemp) {
            0 -> {
                ll_transport_detail.visibility = View.VISIBLE
            }
            1, 2 -> {
                ll_transport_detail.visibility = View.GONE
            }
        }

        //指定司机
        iv_designated_driver_log.setOnClickListener { routerTo.jumpToDriverList(0, goodsId) }
        //刷新，
        iv_refresh_log.setOnClickListener { showToast("刷新成功") }
        //删除这条
        iv_delete_log.setOnClickListener { goodsViewModel.goodsDelete(goodsId) }
    }


    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, Observer {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })

        //常用地址
        mViewModel.senfInfo.observe(this, Observer { info ->
            info ?: return@Observer
            showView()
            tv_transport_detail_address.text = ToolUtils.adrSpannStr(
                mContext,
                ZzzhUtils.adrNameLoad(info.loadAreaCode, info.loadAddress),
                ZzzhUtils.adrNameLoad(info.unloadAreaCode, info.unloadAddress)
            )

            tv_transport_detail_car.text = info.carType + " " + info.carLength
            tv_transport_detail_goods.text = info.goodsName + " " + info.weightVolume
            tv_transport_detail_other.text = info.comments
        })
        //发货中，发货历史
        mViewModel.goodsInfo.observe(this, Observer { info ->
            info ?: return@Observer
            showView()
            tv_transport_detail_address.text = ToolUtils.adrSpannStr(
                mContext,
                ZzzhUtils.adrNameLoad(info.loadAreaCodeVO, info.loadAddress),
                ZzzhUtils.adrNameLoad(info.unloadAreaCodeVO, info.unloadAddress)
            )
            tv_transport_detail_goods.text = ZzzhUtils.setWeightAndVolume(info.weight, info.volume)
            tv_transport_detail_other.text = info.comments
        })

        //删除成功
        goodsViewModel.deleteBack.observe(this, Observer {
            ToastView.setToasd(mContext, "删除成功")
            EventBus.getDefault().post(MessageEvent(MessageEvent.GOODS_DELETE))
            finish()
        })
    }

    private fun showView() {
//        ll_transport_detail.visibility = View.VISIBLE
        ll_transport_detail_root.visibility = View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.DRIVER_APPOINT -> {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}
