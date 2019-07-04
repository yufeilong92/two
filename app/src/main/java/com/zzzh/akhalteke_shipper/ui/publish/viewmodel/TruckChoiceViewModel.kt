package com.zzzh.akhalteke_shipper.ui.publish.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 选择车长车型
 */
class TruckChoiceViewModel : BaseViewModel() {

    val dataBack: MutableLiveData<MutableList<*>> by lazy {
        MutableLiveData<MutableList<*>>()
    }

    /**
     * 获取数据
     */
    fun togetData() {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            Observable.merge(
                RetrofitFactory.createMainRetrofit().carTypeList(),
                RetrofitFactory.createMainRetrofit().carLengthList()
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                isShowProgress.value = 1
                dataBack.value = it.data
            }, this::onError, this::onComplise)
        }

    }


}