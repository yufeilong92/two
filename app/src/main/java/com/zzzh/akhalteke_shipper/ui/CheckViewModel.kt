package com.zzzh.akhalteke_shipper.ui

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.UserInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 检测是否实名认证
 */
class CheckViewModel : BaseViewModel() {

    val uInfo by lazy {
        MutableLiveData<UserInfo>()
    }

    /**
     * 用户检测
     */
    fun infoCheck() {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())){
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().infoCheck()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    uInfo.value = it.data
                }, this::onError, this::onComplise)
        }
    }

}