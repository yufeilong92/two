package com.zzzh.akhalteke_shipper.ui.login.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *  地图类，上传地址坐标
 */
class MapViewModel : BaseViewModel() {

    val successBack by lazy {
        MutableLiveData<String>()
    }

    /**
     * 把地理位置坐标上传
     * @param latitude String  纬度
     * @param longitude String  经度
     */
    fun pointAdd(latitude: String, longitude: String) {
        Log.i("提交定位信息","$latitude ====$longitude")
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            RetrofitFactory.createMainRetrofit().pointAdd(
                latitude = latitude,
                longitude = longitude
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    successBack.value = it.data
                }, this::onError, this::onComplise)
        }
    }

}