package com.zzzh.akhalteke_shipper.ui.find.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.DriverInfo
import com.zzzh.akhalteke_shipper.bean.FindCarInfo
import com.zzzh.akhalteke_shipper.bean.PageInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FindCarViewModel : BaseViewModel() {

    /**
     * 车长id
     */
    val carLT: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val carListData: MutableLiveData<PageInfo<FindCarInfo>> by lazy {
        MutableLiveData<PageInfo<FindCarInfo>>()
    }

    val infos:MutableLiveData<MutableList<DriverInfo>> by lazy {
        MutableLiveData<MutableList<DriverInfo>>()
    }


    val sendSuccssBack:MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    /**
     * 列表，找熟车，找车
     */
    fun carList(
        ifFamiliar: Int,
        page: Int
    ) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return

        var screen = "2"
        var cL = ""
        var cT = ""
        if (!ToolUtils.isEmpty(carLT.value)) {
            val cLTs = carLT.value!!.split("|")
            cL = cLTs[0]
            cT = cLTs[1]
            if (!ToolUtils.isEmpty(cL) || !ToolUtils.isEmpty(cT)) {
                screen = "1"
            }
        }

        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().carList(
            ifFamiliar = ifFamiliar.toString(),
            screen = screen,
            carLengthList = cL,
            carTypeList = cT,
            page = page
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.data?.status = ifFamiliar
                carListData.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 添加熟车
     */
    fun insertFamiliarCar(carOwnerId: String, callBack: () -> Unit) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return

        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().insertFamiliarCar(
            carOwnerId = carOwnerId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callBack()
            }, this::onError, this::onComplise)

    }

    /**
     * 根据电话查询车主
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
     * 联系货主
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