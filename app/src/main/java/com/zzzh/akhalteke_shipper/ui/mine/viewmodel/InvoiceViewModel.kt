package com.zzzh.akhalteke_shipper.ui.mine.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.InvoiceInfo
import com.zzzh.akhalteke_shipper.bean.InvoiceOrderInfo
import com.zzzh.akhalteke_shipper.bean.PageInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 发票管理
 */
class InvoiceViewModel:BaseViewModel(){

    //成功的数据返回
    val successData by lazy {
        MutableLiveData<String>()
    }

    //订单列表
    val orderList by lazy {
        MutableLiveData<PageInfo<InvoiceOrderInfo>>()
    }

    //发票列表
    val invoiceList by lazy {
        MutableLiveData<PageInfo<InvoiceInfo>>()
    }

    //发票详情
    val invoiceDetail by lazy {
        MutableLiveData<InvoiceInfo>()
    }

    //发票信息
    val invoiceData by lazy {
        MutableLiveData<InvoiceInfo>()
    }
    /**
     * 开发票
     */
    fun invoiceBill(info: InvoiceInfo) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().invoiceBill(
            receiverName = info.receiverName,
            receiverPhone = info.receiverPhone,
            receiverAddress = info.receiverAddress,
            name = info.name,
            taxNumber = info.taxNumber,
            address = info.address,
            phone = info.phone,
            bank = info.bank,
            bankNumber = info.bankNumber,
            comments = info.comments,
            orderIds = info.orderIds,
            addressId = info.addressId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 查看可开票订单列表
     * @param page Int
     */
    fun invoiceOrderList(page: Int) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().invoiceOrderList(
            page = page
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                orderList.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 开票历史
     * @param page Int
     */
    fun invoiceList(page: Int) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().invoiceList(
            page = page
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                invoiceList.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 发票记录详情
     * @param invoiceRecordId String
     */
    fun invoiceDetail(invoiceRecordId: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().invoiceDetail(
            invoiceRecordId = invoiceRecordId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                invoiceDetail.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 发票资质信息
     */
    fun invoiceInfo() {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().invoiceInfo(
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                invoiceData.value = it.data
            }, this::onError, this::onComplise)
    }

}