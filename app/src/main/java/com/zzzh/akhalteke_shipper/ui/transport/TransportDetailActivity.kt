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
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.utils.ZzzhUtils
import kotlinx.android.synthetic.main.activity_transport_detail.*
import kotlinx.android.synthetic.main.cell_detail_route.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 货源详情
 */
class TransportDetailActivity : BaseActivity() {

    val mViewModel: TranDetailViewModel by lazy {
        ViewModelProviders.of(this).get(TranDetailViewModel::class.java)
    }
    private val goodsViewModel: SendGoodsViewModel by lazy {
        ViewModelProviders.of(this).get(SendGoodsViewModel::class.java)
    }
    private var entryTemp = 0//0发货中，发货历史，1常用地址
    private var goodsId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport_detail)
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
                tdetail_bottom.visibility = View.VISIBLE
            }
            1, 2 -> {
                tdetail_bottom.visibility = View.GONE
            }
        }

        //指定司机
        item_send_todiver.setOnClickListener { routerTo.jumpToDriverList(0, goodsId) }
        //刷新，
        item_send_refresh.setOnClickListener { showToast("刷新成功") }
        //删除这条
        item_send_delete.setOnClickListener { goodsViewModel.goodsDelete(goodsId) }
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
            transport_detail_content.apply {
                cell_droute_path.text = ToolUtils.adrSpannStr(
                    mContext,
                    ZzzhUtils.adrNameLoad(info.loadAreaCode, info.loadAddress),
                    ZzzhUtils.adrNameLoad(info.unloadAreaCode, info.unloadAddress)
                )

                cell_droute_car.text = info.carType + " " + info.carLength
                cell_droute_goods.text = info.goodsName + " " + info.weightVolume
                cell_droute_other.text = info.comments
            }
        })
        //发货中，发货历史
        mViewModel.goodsInfo.observe(this, Observer { info ->
            info ?: return@Observer
            showView()
            transport_detail_content.apply {
                cell_droute_path.text = ToolUtils.adrSpannStr(
                    mContext,
                    ZzzhUtils.adrNameLoad(info.loadAreaCodeVO, info.loadAddress),
                    ZzzhUtils.adrNameLoad(info.unloadAreaCodeVO, info.unloadAddress)
                )
                cell_droute_goods.text = ZzzhUtils.setWeightAndVolume(info.weight, info.volume)
                cell_droute_other.text = info.comments
            }
        })

        //删除成功
        goodsViewModel.deleteBack.observe(this, Observer {
            ToastView.setToasd(mContext, "删除成功")
            EventBus.getDefault().post(MessageEvent(MessageEvent.GOODS_DELETE))
            finish()
        })
    }

    private fun showView() {
        tdetail_bottom.visibility = View.VISIBLE
        transport_detail_content.visibility = View.VISIBLE
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
