package com.zzzh.akhalteke_shipper.ui.publish

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import cn.qqtheme.framework.picker.DateTimePicker
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.*
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.login.AddressActivity
import com.zzzh.akhalteke_shipper.ui.publish.viewmodel.PublishViewModel
import com.zzzh.akhalteke_shipper.ui.transport.DriverListActivity
import com.zzzh.akhalteke_shipper.ui.transport.DriverNewListActivity
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.PreferencesUtils
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.LoadTypeDialog
import com.zzzh.akhalteke_shipper.view.dialog.SelectPayWayDialog
import kotlinx.android.synthetic.main.activity_publish.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * 发布货源
 */
class PublishActivity : BaseActivity() {

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
        setContentView(R.layout.activity_publish)
        eventBus.register(this)
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
        publish_adr_bg.setOnClickListener {
            temp = 0
            routerTo.jumpToAddress(2)
        }
        publish_toadr_bg.setOnClickListener {
            temp = 1
            routerTo.jumpToAddress(2)
        }
        publish_car_layout.setOnClickListener {
            routerTo.jumpToTruckChoice()
        }

        publish_goodsnum.setOnClickListener { ToolUtils.showDialog(loadDialog) }
        publish_goodspay.setOnClickListener { ToolUtils.showDialog(payDialog) }
        publish_goodstime.setOnClickListener {
            if (dateDialog != null) {
                dateDialog.show()
            }
        }

        //弃用了
        publish_refresh.setOnClickListener {
            if (ifGoodsOften != 1) {
                ifGoodsOften = 1
                carOwnTemp(1)
            }
        }

        //保存常用地址
        publish_save.setOnClickListener {
            if (ifGoodsOften != 0) {
                ifGoodsOften = 0
                carOwnTemp(0)
            } else {
                ifGoodsOften = 1
                carOwnTemp(1)
            }
        }

        //指定司机
        publish_todiver.setOnClickListener {
            todiver()
        }

        //
        publish_publish.setOnClickListener {
            submitData()
        }
    }

    /**
     *
     */
    private fun initFillData() {
        if (saveInfo != null && !ToolUtils.isEmpty(saveInfo.userId)) {
            publish_goodsname.setText(saveInfo.name)
            publish_adr_medit.setText(saveInfo.loadAddress)
            publish_adr.text = saveInfo.loadAreaName
            loadAreaCode = saveInfo.loadAreaCode

            publish_toadr_medit.setText(saveInfo.unloadAddress)
            publish_toadr.text = saveInfo.unloadAreaName
            unloadAreaCode = saveInfo.unloadAreaCode

            truckCs = saveInfo.truckCs
            truckLs = saveInfo.truckLs
            fillTruck()

            publish_area1.setText(saveInfo.weight)
            publish_area2.setText(saveInfo.volume)

            publish_goodsfee.setText(saveInfo.cost)
//            publish_goodstime.text = TimeUntils.getStrTime(saveInfo.loadTime)
//            loadTime = saveInfo.loadTime

            publish_goodsnum.text = Constant.loadTypes[saveInfo.loadType]
            loadType = saveInfo.loadType

            publish_goodspay.text = saveInfo.payType
            publish_goodsremark.setText(saveInfo.comments)

            ifGoodsOften = saveInfo.ifGoodsOften
            carOwnTemp(ifGoodsOften)
        }
    }

    /**
     * 初始化dialog
     */
    private fun initDialog() {
        val goodsNumText = publish_goodsnum

        //装卸方式
        loadDialog = object : LoadTypeDialog(mContext) {
            override fun choiceItem(id: Int, name: String) {
                loadType = id.toString()
                goodsNumText.text = name
            }
        }

        //支付方式
        val goodsPayView = publish_goodspay
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
            publish_goodstime.text = timeStr
            loadTime = TimeUntils.getTime(timeStr).toString()
        })

        //重量编辑框，设置不超过1000，最多一位小数
        publish_area1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editNumVerfiy(publish_area1, 1000)
                ToolUtils.limitEditDit(publish_area1, 1)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //体积编辑框，设置不超过1000，最多一位小数
        publish_area2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editNumVerfiy(publish_area2, 1000)
                ToolUtils.limitEditDit(publish_area2, 1)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //运费编辑框，设置最多两位小数
        publish_goodsfee.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ToolUtils.limitEditDit(publish_goodsfee, 2)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
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
     * 保存常用选择项
     * @param temp Int 0保存，1不保存
     */
    private fun carOwnTemp(temp: Int) {
        when (temp) {
            1 -> {
                publish_refresh_icon.setImageResource(R.mipmap.icon_ring_red)
                publish_save_icon.setImageResource(R.mipmap.icon_ring_gray)
                publish_save_text.setTextColor(ContextCompat.getColor(mContext, R.color.main_text3))
            }
            0 -> {
                publish_save_icon.setImageResource(R.mipmap.icon_ring_red)
                publish_refresh_icon.setImageResource(R.mipmap.icon_ring_gray)
                publish_save_text.setTextColor(ContextCompat.getColor(mContext, R.color.main_color))
            }
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
     * 提交数据
     */
    private fun submitData() {
        val loadAddress = publish_adr_medit.text.toString().trim()
        val unloadAddress = publish_toadr_medit.text.toString().trim()
        val weight = publish_area1.text.toString().trim()
        val volume = publish_area2.text.toString().trim()
        val name = publish_goodsname.text.toString().trim()
        val cost = publish_goodsfee.text.toString().trim()
        val comments = publish_goodsremark.text.toString().trim()

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

    /**
     * 提交数据的判断
     * @return Boolean
     */
    private fun judgeData(): Boolean {
        val loadAddress = publish_adr_medit.text.toString().trim()
        val unloadAddress = publish_toadr_medit.text.toString().trim()
        val weight = publish_area1.text.toString().trim()
        val volume = publish_area2.text.toString().trim()
        val name = publish_goodsname.text.toString().trim()
        val cost = publish_goodsfee.text.toString().trim()
        val comments = publish_goodsremark.text.toString().trim()

        isEmpty(name, "请输入货物名称") ?: return false
        isEmpty(loadAreaCode, "请输入发货地") ?: return false
        isEmpty(unloadAreaCode, "请输入卸货地") ?: return false
//        isEmpty(cost, "请输入运费") ?: return false

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

//        isEmpty(loadTime, "请选择装货时间") ?: return
//        isEmpty(loadType, "请选择装货方式") ?: return

        return true
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
                            publish_adr.text = "请选择发货地区"
                            loadAreaCode = ""
                        }
                        cityId == -1 -> {
                            publish_adr.text = proName
                            loadAreaCode = proId.toString()
                        }
                        areaId == -1 -> {
                            publish_adr.text = proName + cityName
                            loadAreaCode = cityId.toString()
                        }
                        else -> {
                            publish_adr.text = proName + cityName + areaName
                            loadAreaCode = areaId.toString()
                        }
                    }
                }
                1 -> {
                    when {
                        proId == -1 -> {
                            publish_toadr.text = "请选择卸货地区"
                            unloadAreaCode = ""
                        }
                        cityId == -1 -> {
                            publish_toadr.text = proName
                            unloadAreaCode = proId.toString()
                        }
                        areaId == -1 -> {
                            publish_toadr.text = proName + cityName
                            unloadAreaCode = cityId.toString()
                        }
                        else -> {
                            publish_toadr.text = proName + cityName + areaName
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
            publish_carinfo.text = ""
        }

        for (ls in truckCs) {
            strBuffer.append(ls.name + "/")
        }
        if (!strBuffer.isEmpty()) {
            strBuffer.deleteCharAt(strBuffer.length - 1)
            publish_carinfo.text = strBuffer
        }
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

        publish_adr.text = goodsDetail!!.loadAreaCodeVO
        publish_toadr.text = goodsDetail!!.unloadAreaCodeVO
        publish_adr_medit.setText(goodsDetail!!.loadAddress)
        publish_toadr_medit.setText(goodsDetail!!.unloadAddress)

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

        publish_area1.setText(goodsDetail!!.weight)
        publish_area2.setText(goodsDetail!!.volume)
        publish_goodsname.setText(goodsDetail!!.name)
//        publish_goodstime.text = TimeUntils.getStrTime(goodsDetail!!.loadTime)
        publish_goodsnum.text = Constant.loadTypes[goodsDetail!!.loadType]
        publish_goodspay.text = goodsDetail!!.payType
        publish_goodsfee.setText(goodsDetail!!.cost.replace(",", ""))
        publish_goodsremark.setText(goodsDetail!!.comments)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
        }
    }

    /**
     * 保存数据
     */
    private fun toSaveData() {
        val sInfo = PublishSaveInfo()
        sInfo.userId = Constant.userInfo.id
        sInfo.name = publish_goodsname.text.toString().trim()
        sInfo.loadAddress = publish_adr_medit.text.toString().trim()
        sInfo.loadAreaName = publish_adr.text.toString().trim()
        sInfo.loadAreaCode = loadAreaCode
        sInfo.unloadAddress = publish_toadr_medit.text.toString().trim()
        sInfo.unloadAreaName = publish_toadr.text.toString().trim()
        sInfo.unloadAreaCode = unloadAreaCode
        sInfo.truckCs = truckCs
        sInfo.truckLs = truckLs
        sInfo.weight = publish_area1.text.toString().trim()
        sInfo.volume = publish_area2.text.toString().trim()
        sInfo.cost = publish_goodsfee.text.toString().trim()
        sInfo.loadTime = loadTime
        sInfo.loadType = loadType
        sInfo.payType = payType
        sInfo.comments = publish_goodsremark.text.toString().trim()
        sInfo.ifGoodsOften = ifGoodsOften
        PreferencesUtils().toSavePublish(sInfo)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isToSave) {
            toSaveData()
        }
        eventBus.unregister(this)
        ToolUtils.dismissDialog(loadDialog)
        ToolUtils.dismissDialog(payDialog)
        if (dateDialog != null && dateDialog.isShowing) {
            dateDialog.dismiss()
        }
    }

}
