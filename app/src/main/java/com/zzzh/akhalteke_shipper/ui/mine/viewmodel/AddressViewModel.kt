package com.zzzh.akhalteke_shipper.ui.mine.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.AddressInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 我的地址管理
 */
class AddressViewModel : BaseViewModel() {

    val addressList by lazy {
        MutableLiveData<MutableList<AddressInfo>>()
    }

    val successBack by lazy {
        MutableLiveData<String>()
    }

    //地址列表
    fun addressList() {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().addressList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                addressList.value = it.data?: mutableListOf()
            }, this::onError, this::onComplise)
    }

    //添加地址
    fun addressAdd(adrInfo:AddressInfo) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().addressAdd(
            areaCode = adrInfo.areaCode,
            address = adrInfo.address,
            postalCode = adrInfo.postalCode,
            phone = adrInfo.phone,
            receiverName = adrInfo.receiverName
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successBack.value = it.data?:""
            }, this::onError, this::onComplise)
    }

    //更新地址信息
    fun addressUpdate(adrInfo:AddressInfo) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().addressUpdate(
            addressId = adrInfo.id,
            areaCode = adrInfo.areaCode,
            address = adrInfo.address,
            postalCode = adrInfo.postalCode,
            phone = adrInfo.phone,
            receiverName = adrInfo.receiverName
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successBack.value = it.data?:""
            }, this::onError, this::onComplise)
    }

    //删除地址
    fun addressDelete(addressId:String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().addressDelete(
            addressId = addressId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successBack.value = it.data?:""
            }, this::onError, this::onComplise)
    }

    //设为默认地址
    fun addressDefault(addressId:String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().addressDefault(
            addressId = addressId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successBack.value = it.data?:""
            }, this::onError, this::onComplise)
    }

}