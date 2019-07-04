package com.zzzh.akhalteke_shipper.ui.mine.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.MineInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 我的信息
 */
class MineViewModel:BaseViewModel(){

    //我的信息
    val mineData by lazy {
        MutableLiveData<MineInfo>()
    }

    //数据成功的返回
    val successData by lazy {
        MutableLiveData<String>()
    }

    /**
     * 到认证信息
     */
    fun mineInfo() {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().mineInfo(
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mineData.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 意见反馈
     * @param content String
     */
    fun feedback(content:String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().feedback(
            content = content
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data
            }, this::onError, this::onComplise)
    }

}