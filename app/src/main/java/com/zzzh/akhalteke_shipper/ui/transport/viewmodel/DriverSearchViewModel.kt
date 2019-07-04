package com.zzzh.akhalteke_shipper.ui.transport.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.DriverInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 找司机，搜索
 */
class DriverSearchViewModel:BaseViewModel(){

    val info:MutableLiveData<MutableList<DriverInfo>> by lazy {
        MutableLiveData<MutableList<DriverInfo>>()
    }

    /**
     * 手机号查询司机
     * @param phone String 手机号，完整的
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
                    info.value = it.data
                }, this::onError, this::onComplise)
        }
    }

}