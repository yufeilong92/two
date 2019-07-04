package com.zzzh.akhalteke_shipper.ui.transport.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.DriverDetailInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 司机信息
 */
class DriverOwnerViewModel : BaseViewModel() {

    val mInfo by lazy {
        MutableLiveData<DriverDetailInfo>()
    }

    val successData by lazy {
        MutableLiveData<String>()
    }

    /**
     * 基本信息
     */
    fun getFamiliarCarInfo(carOwnerId: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().getFamiliarCarInfo(carOwnerId = carOwnerId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mInfo.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 移除熟车
     */
    fun deleteFamiliarCar(carOwnerId: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().deleteFamiliarCar(carOwnerId = carOwnerId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 获取车辆详情
     * @param carOwnerId String 车主id
     */
    fun getCarInfo(carOwnerId: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().getCarInfo(carOwnerId = carOwnerId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mInfo.value = it.data
            }, this::onError, this::onComplise)
    }



}