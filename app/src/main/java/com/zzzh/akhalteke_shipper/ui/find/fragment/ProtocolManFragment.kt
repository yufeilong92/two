package com.zzzh.akhalteke_shipper.ui.find.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.TranRecordAdapter
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.OrderAgreementInfo
import com.zzzh.akhalteke_shipper.bean.TranRecordInfo
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import kotlinx.android.synthetic.main.fragment_protocol_man.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ProtocolManFragment : BaseFragment() {

    private val mViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(activity!!).get(OrderViewModel::class.java)
    }

    lateinit var mainView: View
    private var isCreate = false//是否创建页面
    private var isVisibleToUser = false//是否可见

    private var status = 0//0运货人，1司机

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_protocol_man, null).also { mainView = it }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        status = arguments!!.getInt("status", 0)

        isCreate = true
        initView()
        EventBus.getDefault().register(this)
        initViewModel()

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
    }

    private fun initView() {

    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, Observer<Int> {
            if (isCreate && isVisibleToUser) {
                when (it) {
                    0 -> {
                        showProgress()
                    }
                    1 -> {
                        dismissProgress()
                    }
                }
            }
        })

        //请求返回的协议数据
        mViewModel.agreementInfo.observe(this, Observer {
            fillData(it ?: return@Observer)
        })
    }

    //填充数据
    private fun fillData(info: OrderAgreementInfo) {
        when (status) {
            0 -> {//运货人
                ImageLoadingUtils.loadNetImage(protocol_man_icon, info.shipperPortrait)
                protocol_man_name.text = info.shipperName
                protocol_man_phone.text = info.shipperPhone
                protocol_man_adr.text = info.shipperAddress
                protocol_man_time.text = TimeUntils.getStrTime(info.launchTime)

                protocol_man_adr_name.text = "公司地址"
                protocol_man_time_name.text = "发起时间"
            }
            1 -> {//司机
                ImageLoadingUtils.loadNetImage(protocol_man_icon, info.driverPortrait)
                protocol_man_name.text = info.ownerName
                protocol_man_phone.text = info.ownerPhone
                protocol_man_adr.text = info.ownerCarNumber
                protocol_man_time.text = info.confirmTime

                protocol_man_adr_name.text = "车牌号     "
                protocol_man_time_name.text = "确认时间"
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}