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
import kotlinx.android.synthetic.main.activity_protocol_to_new.*
import kotlinx.android.synthetic.main.gm_title_layout.*
import java.lang.Exception
import java.util.*
/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.find
 * @Package com.zzzh.akhalteke_shipper.ui.find
 * @Email : yufeilong92@163.com
 * @Time :2019/5/23 17:16
 * @Purpose :发起货物运输协议
 */
class ProtocolNewToActivity : BaseActivity() {
    private val mViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(this).get(OrderViewModel::class.java)
    }

    private var entryTemp = 0//0普通进入，1再次发起协议
    private var orderId = ""//订单id
    private lateinit var orderInfo: OrderInfo //订单信息

    private var proInfo: OrderAgreementInfo = OrderAgreementInfo() //协议，用于提交数据

    private var timeTemp = 0    //时间种类，0发货时间，1卸货时间
    private lateinit var dateDialog: DateTimePicker  //时间选择器

    private var adrTemp = 0   //地址种类，0发货地址，1卸货地址
    private var agreemTemp = 0//0同意，1不同意

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protocol_to_new)
        gm_tv_activity_title.text = "发起货物运输协议"

        entryTemp = intent.getIntExtra("entry_temp",0)
        orderId = intent.getStringExtra("order_id")
        orderInfo = intent.getParcelableExtra("order_info")

        initDateTime()
        initView()
        initEdit()
        initSpanText()
        initViewModel()
        //提交按钮
        btn_protocol_submit.setOnClickListener {
            submite()
        }
        fillData()
    }

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
                    tv_protocol_loading_time.text = dateStr
                    tv_protocol_unloading_un.text = "${timeStr}前"
                }
                1 -> {
                    proInfo.unloadTime = TimeUntils.getTime(dateTimeStr).toString()
                    tv_protocol_unloading_time.text = dateStr
                    tv_protocolt_unlaoding_untime.text = "${timeStr}前"
                }
            }

            if (!ToolUtils.isEmpty(proInfo.loadTime) && !ToolUtils.isEmpty(proInfo.unloadTime)) {
                tv_protocol_fatalism.text = TimeUntils.toSpaceTime(proInfo.loadTime, proInfo.unloadTime)
            }
        })

        val bdateStr = "${(calendar.get(Calendar.MONTH) + 1)}-${calendar.get(Calendar.DATE)}"
        val btimeStr = "00:00"
        val bdateTimeStr = "${calendar.get(Calendar.YEAR)}-$bdateStr $btimeStr"


        proInfo.loadTime = TimeUntils.getTime(bdateTimeStr).toString()
        tv_protocol_loading_time.text = bdateStr
        tv_protocol_unloading_un.text = "${btimeStr}前"

        calendar.add(Calendar.DAY_OF_MONTH, 2)

        val ndateStr = "${(calendar.get(Calendar.MONTH) + 1)}-${calendar.get(Calendar.DATE)}"
        val ntimeStr = "00:00"
        val ndateTimeStr = "${calendar.get(Calendar.YEAR)}-$ndateStr $ntimeStr"

        proInfo.unloadTime = TimeUntils.getTime(ndateTimeStr).toString()
        tv_protocol_unloading_time.text = ndateStr
        tv_protocolt_unlaoding_untime.text = "${ntimeStr}前"
        tv_protocol_fatalism.text = "2天"
    }

    private fun initView() {
        //装货地址
        rl_protocol_loading.setOnClickListener {
            timeTemp = 0
            if (dateDialog != null) {
                dateDialog.show()
            }
        }
        //卸货地址
        rl_protocol_unloading.setOnClickListener {
            timeTemp = 1
            if (dateDialog != null) {
                dateDialog.show()
            }
        }
        //装货地址选择
        rl_protocol_city.setOnClickListener {
            adrTemp = 0
            routerTo.jumpToAddress(2)
        }
        //卸货地址选择
        rl_protocol_uncity.setOnClickListener {
            adrTemp = 1
            routerTo.jumpToAddress(2)
        }

    }

    private fun initEdit() {
        //通过平台支付的运费，计算总费用，设置限定2位小数
        et_protocol_cost.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                agreementCharge()
                ToolUtils.limitEditDit(et_protocol_cost, 2)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        //最晚卸货时间，不能超过30天
        et_protocol_day.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val editStr = et_protocol_day.text.toString().trim()
                val editNum = ToolUtils.stringToDouble(editStr)
                if (editNum > 30) {
                    et_protocol_day.setText("30")
                    et_protocol_day.setSelection(2)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //重量，不能超过1000，最多一位小数
        et_protocol_max_ton.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editNumVerfiy(et_protocol_max_ton, 1000)
                ToolUtils.limitEditDit(et_protocol_max_ton, 1)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        //体积，不能超过1000，最多一位小数
        et_protocol_man_f.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editNumVerfiy(et_protocol_man_f, 1000)
                ToolUtils.limitEditDit(et_protocol_man_f, 1)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

//        //运费，最多2位小数
//        protocol_to_cost.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                ToolUtils.limitEditDit(protocol_to_cost, 2)
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//        })
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
        tv_protocol_read_agreement.movementMethod = LinkMovementMethod.getInstance()
        tv_protocol_read_agreement.text = spanText
    }
    /**
     * 第一次进入时，根据订单填充数据
     */
    private fun fillData() {
        et_protocol_cost.setText(orderInfo.cost.replace(",", ""))
        tv_protocol_city_type.text = orderInfo.carLengthType
        et_protocol_plate.setText(orderInfo.carOwnerPlate) 

        if (orderInfo.loadAreaName == orderInfo.loadAddress) {
            tv_protocol_city.text = ""
        } else {
            tv_protocol_city.text = orderInfo.loadAreaName
        }
        et_protocol_loading_detail.setText(orderInfo.loadAddress)

        if (orderInfo.unloadAreaName == orderInfo.unloadAddress) {
            tv_protocol_uncity.text = ""
        } else {
            tv_protocol_uncity.text = orderInfo.unloadAreaName
        }
        et_protocol_unloading.setText(orderInfo.unloadAddress)

        et_protocol_goodname.setText(orderInfo.name)
        if (!ToolUtils.isEmpty(orderInfo.weight)) {
            et_protocol_max_ton.setText(orderInfo.weight)
        }
        if (!ToolUtils.isEmpty(orderInfo.volume)) {
            et_protocol_man_f.setText(orderInfo.volume)
        }
    }
  
    /**
     * 由根据平台支付费用，计算费用
     */
    private fun agreementCharge() {
        var money = et_protocol_cost.text.toString().trim()
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
    private fun submite() {
        proInfo.companyAmount = et_protocol_cost.text.toString().trim()
        proInfo.totalAmount = tv_protocol_pay.text.toString().trim()
        proInfo.payTime = et_protocol_day.text.toString().trim()
        proInfo.loadAddress =
            tv_protocol_city.text.toString().trim() + et_protocol_loading_detail.text.toString().trim()
        proInfo.unloadAddress =
            tv_protocol_uncity.text.toString().trim() + et_protocol_unloading.text.toString().trim()
        proInfo.carTypeLength = tv_protocol_city_type.text.toString().trim()

        proInfo.weight = et_protocol_max_ton.text.toString().trim()
        proInfo.volume = et_protocol_man_f.text.toString().trim()
        proInfo.goodsName = et_protocol_goodname.text.toString().trim()
        proInfo.appointment = et_protocol_assumpsit.text.toString().trim()

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
            tv_protocol_pay.text = it ?: ""
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
        et_protocol_day.setText(aInfo.totalAmount)
        et_protocol_cost.setText(aInfo.companyAmount)
        tv_protocol_loading_time.text = TimeUntils.getStrTime(aInfo.loadTime)
        tv_protocol_unloading_time.text = TimeUntils.getStrTime(aInfo.unloadTime)

        if (!ToolUtils.isEmpty(aInfo.loadTime) && !ToolUtils.isEmpty(aInfo.unloadTime)) {
            tv_protocol_fatalism.text = TimeUntils.toSpaceTime(aInfo.loadTime, aInfo.unloadTime)
        }
        tv_protocol_city.text = aInfo.loadAddress
        tv_protocol_uncity.text = aInfo.unloadAddress
        et_protocol_goodname.setText(aInfo.goodsName)
        tv_protocol_city_type.text = aInfo.carTypeLength

        et_protocol_plate.setText( aInfo.ownerCarNumber)

        if (!ToolUtils.isEmpty(orderInfo.weight)) {
            et_protocol_max_ton.setText(orderInfo.weight)
        }
        if (!ToolUtils.isEmpty(orderInfo.volume)) {
            et_protocol_man_f.setText(orderInfo.volume)
        }

        et_protocol_assumpsit.setText(aInfo.appointment)
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
                    tv_protocol_city.text = cityName + areaName
                    proInfo.loadAddress = areaId.toString()
                }
                1 -> {
                    tv_protocol_uncity.text = cityName + areaName
//                    unloadAreaCode = areaId.toString()
                }
            }

            when (adrTemp) {
                0 -> {
                    when {
                        proId == -1 -> {
                            tv_protocol_city.text = "请选择发货地区"
                        }
                        cityId == -1 -> {
                            tv_protocol_city.text = proName
                        }
                        areaId == -1 -> {
                            tv_protocol_city.text = proName + cityName
                        }
                        else -> {
                            tv_protocol_city.text = proName + cityName + areaName
                        }
                    }
                }
                1 -> {
                    when {
                        proId == -1 -> {
                            tv_protocol_uncity.text = "请选择卸货地区"
                        }
                        cityId == -1 -> {
                            tv_protocol_uncity.text = proName
                        }
                        areaId == -1 -> {
                            tv_protocol_uncity.text = proName + cityName
                        }
                        else -> {
                            tv_protocol_uncity.text = proName + cityName + areaName
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

            tv_protocol_city_type.text = strBuffer

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (dateDialog != null && dateDialog.isShowing) {
            dateDialog.dismiss()
        }

    }
}
