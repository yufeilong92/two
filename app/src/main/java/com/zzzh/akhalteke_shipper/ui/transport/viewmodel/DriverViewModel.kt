package com.zzzh.akhalteke_shipper.ui.transport.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.DriverInfo
import com.zzzh.akhalteke_shipper.bean.PageInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 司机信息
 */
class DriverViewModel : BaseViewModel() {

    val driverInfos: MutableLiveData<PageInfo<DriverInfo>> by lazy {
        MutableLiveData<PageInfo<DriverInfo>>()
    }

    val appointBack:MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    val infos:MutableLiveData<MutableList<DriverInfo>> by lazy {
        MutableLiveData<MutableList<DriverInfo>>()
    }

    val sendSuccssBack:MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    /**
     * 获取指定司机列表(修改)
     */
    fun getFamiliarCarList(
        page: Int,
        cL:String,
        cT:String
    ) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return

        isShowProgress.value = 0
        var screen = "2"

        if(!ToolUtils.isEmpty(cL)||!ToolUtils.isEmpty(cT)){
            screen = "1"
        }
        RetrofitFactory.createMainRetrofit().getFamiliarCarList(
            ifFamiliar = "1",
            screen = screen,
            carLengthList = cL,
            carTypeList = cT,
            page = page
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                driverInfos.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 获取指定司机列表
     */
    fun getFamiliarCarList(page: Int) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().getFamiliarCarList(
                page = page
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    driverInfos.value = it.data
                }, this::onError, this::onComplise)
        }
    }

    /**
     * 指定司机
     */
    fun appointDriver(goodsId:String,carOwnerId:String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().appointDriver(
                goodsId = goodsId,
                carOwnerId = carOwnerId
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    appointBack.value = it.data
                }, this::onError, this::onComplise)
        }
    }

    /**
     * 根据手机号获取车主
     */
    fun getCarOwnerByPhone(phone:String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().getCarOwnerByPhone(
                phone = phone
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    infos.value = it.data
                }, this::onError, this::onComplise)
        }
    }

    /**
     * 根据手机号联系他
     */
    fun sendInvitationSms(phone:String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().sendInvitationSms(
                phone = phone
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    sendSuccssBack.value = it.data
                }, this::onError, this::onComplise)
        }
    }


}