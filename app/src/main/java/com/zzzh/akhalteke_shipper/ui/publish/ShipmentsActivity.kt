package com.zzzh.akhalteke_shipper.ui.publish

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import cn.qqtheme.framework.picker.DateTimePicker
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.*
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.login.AddressActivity
import com.zzzh.akhalteke_shipper.ui.publish.viewmodel.PublishViewModel
import com.zzzh.akhalteke_shipper.ui.transport.DriverNewListActivity
import com.zzzh.akhalteke_shipper.updateapp.ext.toast
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.PreferencesUtils
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.LoadTypeDialog
import com.zzzh.akhalteke_shipper.view.dialog.SelectPayWayDialog
import kotlinx.android.synthetic.main.activity_shipments.*
import kotlinx.android.synthetic.main.gm_title_layout.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.publish
 * @Package com.zzzh.akhalteke_shipper.ui.publish
 * @Email : yufeilong92@163.com
 * @Time :2019/5/21 17:25
 * @Purpose :发货
 */
class ShipmentsActivity : BaseActivity() {
    private var temp = 0 //地址类型，0发货地址，1卸货地址
    private var isToSave = false //是否保存本页信息

    private var goodsDetail: GoodsDetailInfo? = null

    private val mViewModel: PublishViewModel by lazy {
        ViewModelProviders.of(this).get(PublishViewModel::class.java)
    }

    private var loadAreaCode = ""//装货地址code
    private var type = "1"
    private var unloadAreaCode = "" //卸货地址code
    private var loadType = ""       //装载方式
    private var payType = ""        //支付方式
    private var loadTime = ""       //装货时间
    private var ifGoodsOften = 1    //是否保存常用，0不保存，1保存
    private var ifDriver = "2"      //是否指定司机，1
    private var carOwnerId = ""     //指定的司机id

    //页面的保存信息
    private lateinit var saveInfo: PublishSaveInfo

    //车长
    private var truckLs = arrayListOf<StringInfo>()
    //车型
    private var truckCs = arrayListOf<StringInfo>()

    //装卸方式dialog
    private lateinit var loadDialog: LoadTypeDialog
    //支付方式dialog
    private lateinit var payDialog: SelectPayWayDialog
    //日期dialog
    private lateinit var dateDialog: DateTimePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipments)
        gm_tv_activity_title.text="发布货源"
        goodsDetail = intent.getParcelableExtra("detail_info")
        saveInfo = PreferencesUtils().toGetSavePublish()
        initView()
        initDialog()
        initViewModel()
        initViewData()
    }
    /**
     * 初始化页面
     */
    private fun initView() {
        tv_shipment_city.setOnClickListener {
            temp = 0
            routerTo.jumpToAddress(2)
        }
        tv_shipment_uncity.setOnClickListener {
            temp = 1
            routerTo.jumpToAddress(2)
        }
        ll_shipment_cartype.setOnClickListener {
            routerTo.jumpToTruckChoice()
        }

        ll_shipment_goodsnum.setOnClickListener { ToolUtils.showDialog(loadDialog) }
        ll_shipment_pay_type.setOnClickListener { ToolUtils.showDialog(payDialog) }
        ll_shipment_time.setOnClickListener {
            if (dateDialog != null) {
                dateDialog.show()
            }
        }


        cb_shipment_sel.setOnCheckedChangeListener { buttonView, isChecked -> 
            if (isChecked){
                ifGoodsOften = 0 
            }else{
                ifGoodsOften = 1
            }
        }
        
        //指定司机
        tv_shipment_appoint.setOnClickListener {
            todiver()
        }

        //
        tv_shipment_publish.setOnClickListener {
            submitData()
        }
    }
    /**
     * 指定司机
     */
    private fun todiver() {
        if (!judgeData()) {
            return
        }
        routerTo.jumpToDriverList(1)
    }

    /**
     * 提交数据的判断
     * @return Boolean
     */
    private fun judgeData(): Boolean {
        val loadAddress = tv_shipment_city.text.toString().trim()
        val unloadAddress = tv_shipment_uncity.text.toString().trim()
        val weight = et_shipment_quality.text.toString().trim()
        val volume = et_shipment_volume.text.toString().trim()
        val name = et_shipment_good_name.text.toString().trim()
        val cost = et_shipment_cunt.text.toString().trim()
        val comments = et_shipment_mark.text.toString().trim()

        if (!TextUtils.isEmpty(cost)){
            if (cost.toDouble()<100.0f){
                toast("货物运费最低100")
                return false
            }
        }
        isEmpty(name, "请输入货物名称") ?: return false
        isEmpty(loadAddress, "请输入发货地") ?: return false
        isEmpty(unloadAddress, "请输入卸货地") ?: return false

//        isEmpty(loadAddress, "请输入发货详细地址") ?: false
//        isEmpty(unloadAddress, "请输入卸货详细地址") ?: false
//
//        if (type == "0") {
//            showToast("请选择用车类型")
//            return
//        }
//
//        if (truckLs.size == 0) {
//            showToast("请选择车长")
//            return
//        }
//        if (truckCs.size == 0) {
//            showToast("请选择车型")
//            return
//        }

        if (ToolUtils.isEmpty(weight) && ToolUtils.isEmpty(volume)) {
            showToast("货物质量/体积请至少填一项")
            return false
        }
//        isEmpty(cost, "请输入运费") ?: return false

//        isEmpty(loadTime, "请选择装货时间") ?: return
//        isEmpty(loadType, "请选择装货方式") ?: return

        return true
    }
    /**
     * 提交数据
     */
    private fun submitData() {
        val loadAddress = et_shipment_address.text.toString().trim()
        val unloadAddress = et_shipment_unaddress.text.toString().trim()
        val weight = et_shipment_quality.text.toString().trim()
        val volume = et_shipment_volume.text.toString().trim()
        val name = et_shipment_good_name.text.toString().trim()
        val cost = et_shipment_cunt.text.toString().trim()
        val comments = et_shipment_mark.text.toString().trim()

        if (!judgeData()) {
            return
        }

        var carLengthId1 = ""
        var carLengthId2 = ""
        var carLengthId3 = ""
        for (i in 0 until truckLs.size) {
            when (i) {
                0 -> {
                    carLengthId1 = truckLs[i].id
                }
                1 -> {
                    carLengthId2 = truckLs[i].id
                }
                2 -> {
                    carLengthId3 = truckLs[i].id
                }
            }
        }

        var carTypeId1 = ""
        var carTypeId2 = ""
        var carTypeId3 = ""
        for (i in 0 until truckCs.size) {
            when (i) {
                0 -> {
                    carTypeId1 = truckCs[i].id
                }
                1 -> {
                    carTypeId2 = truckCs[i].id
                }
                2 -> {
                    carTypeId3 = truckCs[i].id
                }
            }
        }
        toCheckInfo {
            mViewModel.publish(
                loadAreaCode = loadAreaCode,
                loadAddress = loadAddress,
                unloadAreaCode = unloadAreaCode,
                unloadAddress = unloadAddress,
                carLengthId1 = carLengthId1,
                carLengthId2 = carLengthId2,
                carLengthId3 = carLengthId3,
                carTypeId1 = carTypeId1,
                carTypeId2 = carTypeId2,
                carTypeId3 = carTypeId3,
//                type = "1",
                weight = weight,
                volume = volume,
                name = name,
                loadTime = loadTime,
                loadType = loadType,
                payType = payType,
                cost = cost,
                comments = comments,
                ifDriver = ifDriver,
                carOwnerId = carOwnerId,
                ifGoodsOften = (ifGoodsOften + 1).toString()
            )
        }
    }
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

            when (temp) {
                0 -> {
                    when {
                        proId == -1 -> {
                            tv_shipment_city.text = "请选择发货地区"
                            loadAreaCode = ""
                        }
                        cityId == -1 -> {
                            tv_shipment_city.text = proName
                            loadAreaCode = proId.toString()
                        }
                        areaId == -1 -> {
                            tv_shipment_city.text = proName + cityName
                            loadAreaCode = cityId.toString()
                        }
                        else -> {
                            tv_shipment_city.text = proName + cityName + areaName
                            loadAreaCode = areaId.toString()
                        }
                    }
                }
                1 -> {
                    when {
                        proId == -1 -> {
                            tv_shipment_uncity.text = "请选择卸货地区"
                            unloadAreaCode = ""
                        }
                        cityId == -1 -> {
                            tv_shipment_uncity.text = proName
                            unloadAreaCode = proId.toString()
                        }
                        areaId == -1 -> {
                            tv_shipment_uncity.text = proName + cityName
                            unloadAreaCode = cityId.toString()
                        }
                        else -> {
                            tv_shipment_uncity.text = proName + cityName + areaName
                            unloadAreaCode = areaId.toString()
                        }
                    }
                }
            }
        } else if (requestCode == TruckChoiceActivity.DATABACK && resultCode == TruckChoiceActivity.DATABACK) {
            data ?: return
            type = data.getStringExtra("car_type")
            truckLs = data.getParcelableArrayListExtra("car_lengths")
            truckCs = data.getParcelableArrayListExtra("car_types")

            fillTruck()

        } else if (requestCode == DriverNewListActivity.DATABACK && resultCode == DriverNewListActivity.DATABACK) {
            data ?: return
            val driverInfo = data.getParcelableExtra<DriverInfo>("driver_info")
            if (driverInfo != null && !ToolUtils.isEmpty(driverInfo.carOwnerId)) {
                ifDriver = "1"
                carOwnerId = driverInfo.carOwnerId
                submitData()
            } else {
                showToast("未指定司机")
            }
        }
    }
    /**
     * 填充车长车型
     */
    private fun fillTruck() {
        val strBuffer = StringBuffer()

        for (ls in truckLs) {
            strBuffer.append(ls.name + "/")
        }

        if (!strBuffer.isEmpty()) {
            strBuffer.deleteCharAt(strBuffer.length - 1)
            strBuffer.append("    ")
        } else {
            tv_shipment_car_type.text = ""
        }

        for (ls in truckCs) {
            strBuffer.append(ls.name + "/")
        }
        if (!strBuffer.isEmpty()) {
            strBuffer.deleteCharAt(strBuffer.length - 1)
            tv_shipment_car_type.text = strBuffer
        }
    }
    /**
     * 初始化dialog
     */
    private fun initDialog() {
        val goodsNumText = tv_shipment_goodsnum

        //装卸方式
        loadDialog = object : LoadTypeDialog(mContext) {
            override fun choiceItem(id: Int, name: String) {
                loadType = id.toString()
                goodsNumText.text = name
            }
        }

        //支付方式
        val goodsPayView = tv_shipment_pay_type
        payDialog = object : SelectPayWayDialog(mContext) {
            override fun callBackData(name: String) {
                payType = name
                goodsPayView.text = name
            }
        }

        //选择日期
        val calendar = Calendar.getInstance()
        dateDialog = DateTimePicker(mContext, DateTimePicker.HOUR_24)
        dateDialog.setDateRangeStart(1990, 1, 1)
        dateDialog.setDateRangeEnd(2100, 1, 1)
        dateDialog.setSelectedItem(
            calendar.get(Calendar.YEAR),
            (calendar.get(Calendar.MONTH) + 1),
            calendar.get(Calendar.DATE), 0, 0
        )
        dateDialog.setOnDateTimePickListener(DateTimePicker.OnYearMonthDayTimePickListener { year, month, day, hour, minute ->
            val timeStr = "$year-$month-$day $hour:$minute"
            tv_shipment_time.text = timeStr
            loadTime = TimeUntils.getTime(timeStr).toString()
        })

        //重量编辑框，设置不超过1000，最多一位小数
        et_shipment_quality.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editNumVerfiy(et_shipment_quality, 1000)
                ToolUtils.limitEditDit(et_shipment_quality, 1)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //体积编辑框，设置不超过1000，最多一位小数
        et_shipment_volume.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editNumVerfiy(et_shipment_volume, 1000)
                ToolUtils.limitEditDit(et_shipment_volume, 1)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //运费编辑框，设置最多两位小数
        et_shipment_cunt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ToolUtils.limitEditDit(et_shipment_cunt, 2)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    /**
     * 输入的数字不能大于number值
     */
    private fun editNumVerfiy(editText: EditText, number: Int) {
        val editStr = editText.text.toString().trim()
        val editNum = ToolUtils.stringToDouble(editStr)
        if (editNum > number) {
            editText.setText(number.toString())
            editText.setSelection(number.toString().length)
        }
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

        //提交发布货源成功返回监听
        mViewModel.backSuccess.observe(this, Observer {
            showToast("提交成功")
            PreferencesUtils().clearSPublish()
            isToSave = true
            if (ifDriver == "1" && !ToolUtils.isEmpty(carOwnerId)) {
//                routerTo.jumpToMainOrder()
                eventBus.post(MessageEvent(MessageEvent.DRIVER_APPOINT))
            } else {
                eventBus.post(MessageEvent(MessageEvent.PUBLISH_SUCCESS))
            }
            finishBase()
        })
    }
    /**
     * 初始化页面数据，用于页面数据的保存草稿之后的填充
     */
    private fun initViewData() {
        if (goodsDetail == null || ToolUtils.isEmpty(goodsDetail!!.goodsOftenId)) {
            initFillData()
            return
        }
        loadAreaCode = goodsDetail!!.loadAreaCode
        unloadAreaCode = goodsDetail!!.unloadAreaCode
        type = goodsDetail!!.type
        loadType = goodsDetail!!.loadType
        payType = goodsDetail!!.payType
//        loadTime = goodsDetail!!.loadTime

        tv_shipment_city.text = goodsDetail!!.loadAreaCodeVO
        tv_shipment_uncity.text = goodsDetail!!.unloadAreaCodeVO
        et_shipment_address.setText(goodsDetail!!.loadAddress)
        et_shipment_unaddress.setText(goodsDetail!!.unloadAddress)

        truckLs.add(StringInfo(id = goodsDetail!!.carLengthId1))
        if (!ToolUtils.isEmpty(goodsDetail!!.carLengthId2)) {
            truckLs.add(StringInfo(id = goodsDetail!!.carLengthId2))
        }
        if (!ToolUtils.isEmpty(goodsDetail!!.carLengthId3)) {
            truckLs.add(StringInfo(id = goodsDetail!!.carLengthId3))
        }

        truckCs.add(StringInfo(id = goodsDetail!!.carTypeId1))
        if (!ToolUtils.isEmpty(goodsDetail!!.carTypeId2)) {
            truckCs.add(StringInfo(id = goodsDetail!!.carTypeId2))
        }
        if (!ToolUtils.isEmpty(goodsDetail!!.carTypeId3)) {
            truckCs.add(StringInfo(id = goodsDetail!!.carTypeId3))
        }

        et_shipment_quality.setText(goodsDetail!!.weight)
        et_shipment_volume.setText(goodsDetail!!.volume)
        et_shipment_good_name.setText(goodsDetail!!.name)
//        tv_shipment_time.text = TimeUntils.getStrTime(goodsDetail!!.loadTime)
        tv_shipment_goodsnum.text = Constant.loadTypes[goodsDetail!!.loadType]
        tv_shipment_pay_type.text = goodsDetail!!.payType
        et_shipment_cunt.setText(goodsDetail!!.cost.replace(",", ""))
        et_shipment_mark.setText(goodsDetail!!.comments)
    }

    /**
     *
     */
    private fun initFillData() {
        if (saveInfo != null && !ToolUtils.isEmpty(saveInfo.userId)) {
            et_shipment_good_name.setText(saveInfo.name)
            et_shipment_address.setText(saveInfo.loadAddress)
            tv_shipment_city.text = saveInfo.loadAreaName
            loadAreaCode = saveInfo.loadAreaCode

            et_shipment_unaddress.setText(saveInfo.unloadAddress)
            tv_shipment_uncity.text = saveInfo.unloadAreaName
            unloadAreaCode = saveInfo.unloadAreaCode

            truckCs = saveInfo.truckCs
            truckLs = saveInfo.truckLs
            fillTruck()

            et_shipment_quality.setText(saveInfo.weight)
            et_shipment_volume.setText(saveInfo.volume)

            et_shipment_cunt.setText(saveInfo.cost)
//            publish_goodstime.text = TimeUntils.getStrTime(saveInfo.loadTime)
//            loadTime = saveInfo.loadTime

            tv_shipment_goodsnum.text = Constant.loadTypes[saveInfo.loadType]
            loadType = saveInfo.loadType

            tv_shipment_pay_type.text = saveInfo.payType
            et_shipment_mark.setText(saveInfo.comments)

            ifGoodsOften = saveInfo.ifGoodsOften
            cb_shipment_sel.isChecked= ifGoodsOften==1
        }
    }

    override fun onStart() {
        super.onStart()
        eventBus.register(this)
    }

    override fun onStop() {
        super.onStop()
        eventBus.unregister(this)
    }
    override fun onDestroy() {
        super.onDestroy()
        if (!isToSave) {
            toSaveData()
        }

        ToolUtils.dismissDialog(loadDialog)
        ToolUtils.dismissDialog(payDialog)
        if (dateDialog != null && dateDialog.isShowing) {
            dateDialog.dismiss()
        }
    }

    /**
     * 保存数据
     */
    private fun toSaveData() {
        val sInfo = PublishSaveInfo()
        sInfo.userId = Constant.userInfo.id
        sInfo.name = et_shipment_good_name.text.toString().trim()
        sInfo.loadAddress = et_shipment_address.text.toString().trim()
        sInfo.loadAreaName = tv_shipment_city.text.toString().trim()
        sInfo.loadAreaCode = loadAreaCode
        sInfo.unloadAddress = et_shipment_unaddress.text.toString().trim()
        sInfo.unloadAreaName = tv_shipment_uncity.text.toString().trim()
        sInfo.unloadAreaCode = unloadAreaCode
        sInfo.truckCs = truckCs
        sInfo.truckLs = truckLs
        sInfo.weight = et_shipment_quality.text.toString().trim()
        sInfo.volume = et_shipment_volume.text.toString().trim()
        sInfo.cost = et_shipment_cunt.text.toString().trim()
        sInfo.loadTime = loadTime
        sInfo.loadType = loadType
        sInfo.payType = payType
        sInfo.comments = et_shipment_mark.text.toString().trim()
        sInfo.ifGoodsOften = ifGoodsOften
        PreferencesUtils().toSavePublish(sInfo)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
        }
    }

}
