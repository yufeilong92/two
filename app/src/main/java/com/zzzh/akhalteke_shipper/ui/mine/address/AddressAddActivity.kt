package com.zzzh.akhalteke_shipper.ui.mine.address

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.AddressInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.live.CarTypeBean
import com.zzzh.akhalteke_shipper.live.CarTypeLiveData
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.login.AddressActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.AddressViewModel
import kotlinx.android.synthetic.main.activity_address_add.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 新增地址
 */
class AddressAddActivity : BaseActivity() {

    private var entryTemp = 0//0添加，1修改
    private lateinit var adrInfo: AddressInfo//地址信息

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(AddressViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_add)
        eventBus.register(this)
        entryTemp = intent.getIntExtra("entry_temp", 0)
        if (entryTemp == 1) {//修改
            adrInfo = intent.getParcelableExtra("address_info")
            fillView()
        } else {//添加
            adrInfo = AddressInfo()
        }
        initView()
        initViewModel()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        address_add_icon03.setOnClickListener { routerTo.jumpToAddress() }
        address_add_area.setOnClickListener { routerTo.jumpToAddress() }
        address_add_submit.setOnClickListener { submit() }
    }

    /**
     * 填充界面
     */
    private fun fillView() {
        address_add_name.setText(adrInfo.receiverName)
        address_add_phone.setText(adrInfo.phone)
        address_add_area.text = adrInfo.areaCodeName
        address_add_adr.setText(adrInfo.address)
        address_add_postal.setText(adrInfo.postalCode)
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

        //操作成功后的返回
        mViewModel.successBack.observe(this, Observer {
            when (entryTemp) {
                0 -> {
                    showToast("添加成功")
                }
                1 -> {
                    showToast("修改成功")
                }
            }
            eventBus.post(MessageEvent(MessageEvent.ADDRESS_LIST_UPDATA))
            finishBase()
        })
    }

    /**
     * 提交数据
     */
    private fun submit() {
        adrInfo.receiverName = address_add_name.text.toString().trim()
        adrInfo.phone = address_add_phone.text.toString().trim()
        adrInfo.address = address_add_adr.text.toString().trim()
        adrInfo.postalCode = address_add_postal.text.toString().trim()

        isEmpty(adrInfo.receiverName, "请输入收货人姓名") ?: return
        isEmpty(adrInfo.phone, "请输入手机号") ?: return

        if (adrInfo.phone.length != 11) {
            showToast("请输入正确的手机号")
            return
        }

        isEmpty(adrInfo.areaCode, "请选择所在区域") ?: return
        isEmpty(adrInfo.address, "请输入详细地址请") ?: return

        when (entryTemp) {
            0 -> {
                mViewModel.addressAdd(adrInfo)
            }
            1 -> {
                mViewModel.addressUpdate(adrInfo)
            }
        }

    }

    /**
     * 省市区返回
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddressActivity.adrBackCode && resultCode == AddressActivity.adrBackCode) {
            data ?: return
            val proId = data!!.getIntExtra("pro_id", 0)
            val cityId = data!!.getIntExtra("city_id", 0)
            val areaId = data!!.getIntExtra("area_id", 0)

            val proName = data!!.getStringExtra("pro_name")
            val cityName = data!!.getStringExtra("city_name")
            val areaName = data!!.getStringExtra("area_name")

            adrInfo.areaCode = areaId.toString()
            adrInfo.areaCodeName = proName + cityName + areaName

            address_add_area.text = adrInfo.areaCodeName
        }
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
