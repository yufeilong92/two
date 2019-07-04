package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.EditText
import cn.qqtheme.framework.picker.DateTimePicker
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.OrderAgreementInfo
import com.zzzh.akhalteke_shipper.bean.OrderInfo
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.WebActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import com.zzzh.akhalteke_shipper.ui.login.AddressActivity
import com.zzzh.akhalteke_shipper.ui.publish.TruckChoiceActivity
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.activity_protocol_to.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

/**
 * 发起货物运输协议
 */
class ProtocolToActivity : BaseActivity() {

    private val mViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(this).get(OrderViewModel::class.java)
    }

    var entryTemp = 0//0普通进入，1再次发起协议
    var orderId = ""//订单id

    private lateinit var orderInfo: OrderInfo //订单信息
    private var proInfo: OrderAgreementInfo = OrderAgreementInfo() //协议，用于提交数据

    private var timeTemp = 0    //时间种类，0发货时间，1卸货时间
    private lateinit var dateDialog: DateTimePicker  //时间选择器

    private var adrTemp = 0   //地址种类，0发货地址，1卸货地址
    private var agreemTemp = 0//0同意，1不同意

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protocol_to)
        eventBus.register(this)

        entryTemp = intent.getIntExtra("entry_temp",0)
        orderId = intent.getStringExtra("order_id")

        initDateTime()
        initView()
        initEdit()
        initSpanText()
        initViewModel()

        orderInfo = intent.getParcelableExtra("order_info")
        fillData()
//        when (entryTemp) {
//            0 -> {
//
//            }
//            1 -> {
//                mViewModel.agreementInfo(orderId = orderId)
//            }
//        }
    }

    /**
     * 初始化发货时间和卸货时间
     */
    private fun initDateTime() {
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
            val dateTimeStr = "$year-$month-$day $hour:$minute"
            val dateStr = "$month-$day"
            val timeStr = "$hour:$minute"

            //填充发货时间和卸货时间
            when (timeTemp) {
                0 -> {
                    proInfo.loadTime = TimeUntils.getTime(dateTimeStr).toString()
                    protocol_to_time01.text = dateStr
                    protocol_to_time02.text = "${timeStr}前"
                }
                1 -> {
                    proInfo.unloadTime = TimeUntils.getTime(dateTimeStr).toString()
                    protocol_to_time03.text = dateStr
                    protocol_to_time04.text = "${timeStr}前"
                }
            }

            if (!ToolUtils.isEmpty(proInfo.loadTime) && !ToolUtils.isEmpty(proInfo.unloadTime)) {
                protocol_to_timespace.text = TimeUntils.toSpaceTime(proInfo.loadTime, proInfo.unloadTime)
            }
        })

        val bdateStr = "${(calendar.get(Calendar.MONTH) + 1)}-${calendar.get(Calendar.DATE)}"
        val btimeStr = "00:00"
        val bdateTimeStr = "${calendar.get(Calendar.YEAR)}-$bdateStr $btimeStr"


        proInfo.loadTime = TimeUntils.getTime(bdateTimeStr).toString()
        protocol_to_time01.text = bdateStr
        protocol_to_time02.text = "${btimeStr}前"

        calendar.add(Calendar.DAY_OF_MONTH, 2)

        val ndateStr = "${(calendar.get(Calendar.MONTH) + 1)}-${calendar.get(Calendar.DATE)}"
        val ntimeStr = "00:00"
        val ndateTimeStr = "${calendar.get(Calendar.YEAR)}-$ndateStr $ntimeStr"

        proInfo.unloadTime = TimeUntils.getTime(ndateTimeStr).toString()
        protocol_to_time03.text = ndateStr
        protocol_to_time04.text = "${ntimeStr}前"

        protocol_to_timespace.text = "2天"
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        //发货时间点击事件
        protocol_to_fromtime.setOnClickListener {
            timeTemp = 0
            if (dateDialog != null) {
                dateDialog.show()
            }
        }
        //卸货时间点击事件
        protocol_to_totime.setOnClickListener {
            timeTemp = 1
            if (dateDialog != null) {
                dateDialog.show()
            }
        }
        //发货地址选择
        protocol_to_adrfrom.setOnClickListener {
            adrTemp = 0
            routerTo.jumpToAddress(2)
        }
        //卸货地址选择
        protocol_to_adrto.setOnClickListener {
            adrTemp = 1
            routerTo.jumpToAddress(2)
        }
        protocol_to_type.setOnClickListener {
            //            routerTo.jumpToTruckChoice()
        }

        //是否保存位常用地址按钮
        protocol_to_radio.apply {
            setOnClickListener {
                if (agreemTemp == 0) {
                    agreemTemp = 1
                    setImageResource(R.mipmap.icon_radio)
                } else {
                    agreemTemp = 0
                    setImageResource(R.mipmap.icon_radio_red)
                }
            }
        }

        //提交按钮
        protocol_to_submit.setOnClickListener { submitData() }

    }

    //初始化输入框
    private fun initEdit() {
        //通过平台支付的运费，计算总费用，设置限定2位小数
        protocol_to_company_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                agreementCharge()
                ToolUtils.limitEditDit(protocol_to_company_amount, 2)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        //最晚卸货时间，不能超过30天
        protocol_to_costdate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val editStr = protocol_to_costdate.text.toString().trim()
                val editNum = ToolUtils.stringToDouble(editStr)
                if (editNum > 30) {
                    protocol_to_costdate.setText("30")
                    protocol_to_costdate.setSelection(2)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //重量，不能超过1000，最多一位小数
        protocol_to_weight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editNumVerfiy(protocol_to_weight, 1000)
                ToolUtils.limitEditDit(protocol_to_weight, 1)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        //体积，不能超过1000，最多一位小数
        protocol_to_volume.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editNumVerfiy(protocol_to_volume, 1000)
                ToolUtils.limitEditDit(protocol_to_volume, 1)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //运费，最多2位小数
        protocol_to_cost.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ToolUtils.limitEditDit(protocol_to_cost, 2)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    /**
     * 第一次进入时，根据订单填充数据
     */
    private fun fillData() {
        protocol_to_company_amount.setText(orderInfo.cost.replace(",", ""))
        protocol_to_type.text = orderInfo.carLengthType
        protocol_to_text15.text = orderInfo.carOwnerPlate

        if (orderInfo.loadAreaName == orderInfo.loadAddress) {
            protocol_to_adrfrom.text = ""
        } else {
            protocol_to_adrfrom.text = orderInfo.loadAreaName
        }
        protocol_to_adrfromcontent.setText(orderInfo.loadAddress)

        if (orderInfo.unloadAreaName == orderInfo.unloadAddress) {
            protocol_to_adrto.text = ""
        } else {
            protocol_to_adrto.text = orderInfo.unloadAreaName
        }
        protocol_to_adrto_detail.setText(orderInfo.unloadAddress)

        protocol_to_goodsname.setText(orderInfo.name)
        if (!ToolUtils.isEmpty(orderInfo.weight)) {
            protocol_to_weight.setText(orderInfo.weight)
        }
        if (!ToolUtils.isEmpty(orderInfo.volume)) {
            protocol_to_volume.setText(orderInfo.volume)
        }
    }

    /**
     * 设置《用户服务协议》和《货物运输协议》，超文本格式
     */
    private fun initSpanText() {
        val spanText = SpannableString("我已阅读并同意《用户服务协议》和《货物运输协议》")
        spanText.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    routerTo.jumpToWeb(WebActivity.USER_SERVICE_URL)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = resources.getColor(R.color.main_color)
                    ds.isUnderlineText = false
                }
            }, 7, 15,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        spanText.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    routerTo.jumpToWeb(WebActivity.GOODS_URL)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = resources.getColor(R.color.main_color)
                    ds.isUnderlineText = false
                }
            }, 16, 24,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        protocol_to_pro.movementMethod = LinkMovementMethod.getInstance()
        protocol_to_pro.text = spanText
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

        //协议提交成功后的返回
        mViewModel.successData.observe(this, android.arch.lifecycle.Observer {
            showToast("提交成功")
            finishBase()
            eventBus.post(MessageEvent(MessageEvent.PROTOCOLTO_SUCCESS))
        })
        //计算支付平台金额
        mViewModel.totalMoeny.observe(this, android.arch.lifecycle.Observer {
            protocol_to_total.text = it ?: ""
        })
        //订单协议，重新
        mViewModel.agreementInfo.observe(this, android.arch.lifecycle.Observer {
            fillData(it!!)
        })
    }

    /**
     * 再次发起协议时，根据协议填充数据，暂时废弃了
     * @param aInfo OrderAgreementInfo
     */
    private fun fillData(aInfo: OrderAgreementInfo) {
        proInfo = aInfo
//        protocol_to_cost.setText(aInfo.sumAmount)
        protocol_to_costdate.setText(aInfo.totalAmount)
        protocol_to_company_amount.setText(aInfo.companyAmount)
        protocol_to_time02.text = TimeUntils.getStrTime(aInfo.loadTime)
        protocol_to_time04.text = TimeUntils.getStrTime(aInfo.unloadTime)

        if (!ToolUtils.isEmpty(aInfo.loadTime) && !ToolUtils.isEmpty(aInfo.unloadTime)) {
            protocol_to_timespace.text = TimeUntils.toSpaceTime(aInfo.loadTime, aInfo.unloadTime)
        }
        protocol_to_adrfrom.text = aInfo.loadAddress
        protocol_to_adrto.text = aInfo.unloadAddress
        protocol_to_goodsname.setText(aInfo.goodsName)
        protocol_to_type.text = aInfo.carTypeLength

        protocol_to_text15.text = aInfo.ownerCarNumber

        if (!ToolUtils.isEmpty(orderInfo.weight)) {
            protocol_to_weight.setText(orderInfo.weight)
        }
        if (!ToolUtils.isEmpty(orderInfo.volume)) {
            protocol_to_volume.setText(orderInfo.volume)
        }

        protocol_to_appointment.setText(aInfo.appointment)
    }

    /**
     * 由根据平台支付费用，计算费用
     */
    private fun agreementCharge() {
        var money = protocol_to_company_amount.text.toString().trim()
        if (money == "") {
            money = "0"
        }

        try {
            val moneyNum = java.lang.Double.valueOf(money.replace(",", "")).toDouble()
            mViewModel.agreementCharge(money = money)
        } catch (e: Exception) {
            showToast("请输入正确的金额")
        }
    }

    /**
     * 提交数据
     */
    private fun submitData() {
//        proInfo.sumAmount = protocol_to_cost.text.toString().trim().replace(",", "")
        proInfo.companyAmount = protocol_to_company_amount.text.toString().trim()
        proInfo.totalAmount = protocol_to_total.text.toString().trim()
        proInfo.payTime = protocol_to_costdate.text.toString().trim()
        proInfo.loadAddress =
            protocol_to_adrfrom.text.toString().trim() + protocol_to_adrfromcontent.text.toString().trim()
        proInfo.unloadAddress =
            protocol_to_adrto.text.toString().trim() + protocol_to_adrto_detail.text.toString().trim()
        proInfo.carTypeLength = protocol_to_type.text.toString().trim()

        proInfo.weight = protocol_to_weight.text.toString().trim()
        proInfo.volume = protocol_to_volume.text.toString().trim()
        proInfo.goodsName = protocol_to_goodsname.text.toString().trim()
        proInfo.appointment = protocol_to_appointment.text.toString().trim()

//        isEmpty(proInfo.sumAmount, "请输入总运费") ?: return
        isEmpty(proInfo.companyAmount, "请输入通过平台支付运费") ?: return
        isEmpty(proInfo.payTime, "请输入卸货后支付的最大天数") ?: return
        isEmpty(proInfo.totalAmount, "没有计算出应付金额，请重新填写通过平台支付运费") ?: return

        if (ToolUtils.stringToDouble(proInfo.companyAmount) < 100) {
            showToast("输入的通过平台支付运费要大于100")
            return
        }
//
//        if (ToolUtils.stringToDouble(proInfo.companyAmount) > ToolUtils.stringToDouble(orderInfo.cost.replace(",", ""))) {
//            showToast("输入的通过平台支付运费不能大于总运费")
//            return
//        }

        isEmpty(proInfo.loadTime, "请选择装货时间") ?: return
        isEmpty(proInfo.unloadTime, "请输入到货时间") ?: return

        isEmpty(proInfo.loadAddress, "请输入装货地址") ?: return
        isEmpty(proInfo.unloadAddress, "请输入卸货地址") ?: return
        isEmpty(proInfo.carTypeLength, "请选择车型车长") ?: return

        if (agreemTemp == 1) {
            showToast("请阅读并同意《用户服务协议》和《货物运输协议》")
            return
        }
        when (entryTemp) {
            0 -> {
                proInfo.orderId = orderInfo.orderId
            }
            1 -> {
                proInfo.orderId = orderId
            }
        }

        proInfo.weightVolume = proInfo.weight + "吨" + proInfo.volume + "m³"

        toCheckInfo {
            mViewModel.agreementInit(proInfo)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddressActivity.adrBackCode && resultCode == AddressActivity.adrBackCode) {
            data ?: return
            //地址返回数据填充
            val proId = data!!.getIntExtra("pro_id", 0)
            val cityId = data!!.getIntExtra("city_id", 0)
            val areaId = data!!.getIntExtra("area_id", 0)

            val proName = data!!.getStringExtra("pro_name")
            val cityName = data!!.getStringExtra("city_name")
            val areaName = data!!.getStringExtra("area_name")

            when (adrTemp) {
                0 -> {
                    protocol_to_adrfrom.text = cityName + areaName
                    proInfo.loadAddress = areaId.toString()
                }
                1 -> {
                    protocol_to_adrto.text = cityName + areaName
//                    unloadAreaCode = areaId.toString()
                }
            }

            when (adrTemp) {
                0 -> {
                    when {
                        proId == -1 -> {
                            protocol_to_adrfrom.text = "请选择发货地区"
                        }
                        cityId == -1 -> {
                            protocol_to_adrfrom.text = proName
                        }
                        areaId == -1 -> {
                            protocol_to_adrfrom.text = proName + cityName
                        }
                        else -> {
                            protocol_to_adrfrom.text = proName + cityName + areaName
                        }
                    }
                }
                1 -> {
                    when {
                        proId == -1 -> {
                            protocol_to_adrto.text = "请选择卸货地区"
                        }
                        cityId == -1 -> {
                            protocol_to_adrto.text = proName
                        }
                        areaId == -1 -> {
                            protocol_to_adrto.text = proName + cityName
                        }
                        else -> {
                            protocol_to_adrto.text = proName + cityName + areaName
                        }
                    }
                }
            }

        } else if (requestCode == TruckChoiceActivity.DATABACK && resultCode == TruckChoiceActivity.DATABACK) {
            data ?: return
            //车长车型，数据返回填充
            val type = data.getStringExtra("car_type")
            val truckLs: ArrayList<StringInfo> = data.getParcelableArrayListExtra("car_lengths")
            val truckCs: ArrayList<StringInfo> = data.getParcelableArrayListExtra("car_types")

            val strBuffer = StringBuffer()

//            when (type) {
//                "1" -> {
//                    strBuffer.append("整车  ")
//                }
//                "2" -> {
//                    strBuffer.append("零担  ")
//                }
//            }

            val clenght = StringBuffer()
            for (ls in truckLs) {
                strBuffer.append(ls.name + "/")
                clenght.append(ls.id + ",")
            }
            strBuffer.deleteCharAt(strBuffer.length - 1)
            clenght.deleteCharAt(clenght.length - 1)
            strBuffer.append("    ")

            val ctype = StringBuffer()
            for (ls in truckCs) {
                strBuffer.append(ls.name + "/")
                ctype.append(ls.id + ",")
            }
            strBuffer.deleteCharAt(strBuffer.length - 1)
            ctype.deleteCharAt(ctype.length - 1)

            val clt = "$clenght|$ctype"
//            if(findCarViewModel.carLT.value!=clt){
//                findCarViewModel.carLT.postValue("$clenght|$ctype")
//            }

            protocol_to_type.text = strBuffer

        }
    }

    /**
     * 设置输入框不能大于
     * @param editText EditText
     * @param number Int 不能大于的数字
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
//            MessageEvent.MESSAGEINFO -> {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
        if (dateDialog != null && dateDialog.isShowing) {
            dateDialog.dismiss()
        }

    }

}
