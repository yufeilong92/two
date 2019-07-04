package com.zzzh.akhalteke_shipper.ui.find.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.OrderAgreementInfo
import com.zzzh.akhalteke_shipper.bean.OrderInfo
import com.zzzh.akhalteke_shipper.bean.PageInfo
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OrderViewModel : BaseViewModel() {

    val orderInfos by lazy {
        MutableLiveData<PageInfo<OrderInfo>>()
    }

    val orderInfo by lazy {
        MutableLiveData<OrderInfo>()
    }

    val successData by lazy {
        MutableLiveData<String>()
    }

    val cancelBackData by lazy {
        MutableLiveData<String>()
    }

    val agreementInfo by lazy {
        MutableLiveData<OrderAgreementInfo>()
    }

    val label1 by lazy {
        MutableLiveData<Int>()
    }

    val label2 by lazy {
        MutableLiveData<Int>()
    }

    val label3 by lazy {
        MutableLiveData<Int>()
    }

    val receiptList by lazy {
        MutableLiveData<ArrayList<StringInfo>>()
    }

    val totalMoeny by lazy {
        MutableLiveData<String>()
    }

    val successPayData by lazy {
        MutableLiveData<String>()
    }

    /**
     * 订单列表
     */
    fun orderList(status: Int, page: Int) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().orderList(
            status = status,
            page = page
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.data?.status = status
                orderInfos.value = it.data
            }, this::onError, this::onComplise)

    }

    /**
     * 订单详情
     */
    fun orderInfo(orderId: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().orderInfo(
            orderId = orderId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                orderInfo.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 支付
     */
    fun orderPayment(orderId: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().orderPayment(
            orderId = orderId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successPayData.value = it.data ?: ""
            }, this::onError, this::onComplise)
    }

    /**
     * 确认收货
     */
    fun orderConfirm(orderId: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().orderConfirm(
            orderId = orderId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data ?: ""
            }, this::onError, this::onComplise)
    }

    /**
     * 取消
     */
    fun orderCancel(orderId: String,reason:String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().orderCancel(
            orderId = orderId,
            reason = reason
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cancelBackData.value = it.data ?: ""
            }, this::onError, this::onComplise)
    }

    /**
     * 回单
     */
    fun orderReceipt(orderId: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().orderReceipt(
            orderId = orderId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val imageData = it.data
                val imgList:ArrayList<StringInfo> =  arrayListOf()
                if(!ToolUtils.isEmpty(imageData)){
                    val imgs = imageData!!.split(",")
                    for (img in imgs){
                        imgList.add(StringInfo(name = img))
                    }
                }
                receiptList.value = imgList
            }, this::onError, this::onComplise)
    }


    /**
     * 发起协议
     */
    fun agreementInit(oaInfo: OrderAgreementInfo) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().agreementInit(
            orderId = oaInfo.orderId,
            weightVolume = oaInfo.weightVolume,
            carTypeLength = oaInfo.carTypeLength,
            totalAmount = oaInfo.totalAmount,
            companyAmount = oaInfo.companyAmount,
            payTime = oaInfo.payTime,
            goodsName = oaInfo.goodsName,
            loadTime = oaInfo.loadTime,
            unloadTime = oaInfo.unloadTime,
            loadAddress = oaInfo.loadAddress,
            unloadAddress = oaInfo.unloadAddress,
            appointment = oaInfo.appointment
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 查看协议
     */
    fun agreementInfo(orderId: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().agreementInfo(
            orderId = orderId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                agreementInfo.value = it.data
            }, this::onError, this::onComplise)
    }

    fun agreementCharge(money: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        RetrofitFactory.createMainRetrofit().agreementCharge(
            money = money
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                totalMoeny.value = it.data ?: "0"
            }, this::onError, this::onComplise)
    }

    //    orderId	必填	string	订单id
//    weightVolume	必填	string	重量体积
//    carTypeLength	必填	string	车型车长
//    sumAmount	必填	string	总运费
//    companyAmount	必填	string	通过平台支付运费
//    payTime	必填	string	付款时间（卸货后支付的最大天数）
//    goodsName	非必填	string	货物名称
//    loadTime	必填	string	装货时间（13位时间戳）
//    unloadTime	必填	string	到货时间（13位时间戳）
//    loadAddress	必填	string	装货地址（地区
//    unloadAddress	必填	string	卸货地址（地区
//    appointment	非必填	string	其他约定

}