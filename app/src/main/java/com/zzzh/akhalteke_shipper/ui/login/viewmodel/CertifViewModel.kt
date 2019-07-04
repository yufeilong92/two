package com.zzzh.akhalteke_shipper.ui.login.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.UserInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 实名认证，现废弃
 * @property backSuccess MutableLiveData<UserInfo>
 */
class CertifViewModel:BaseViewModel(){

    //返回数据
    val backSuccess: MutableLiveData<UserInfo> by lazy {
        MutableLiveData<UserInfo>()
    }

    fun authRealName(id: String,iDnumber: String,iDAddress: String,iDAddressReverse: String,portrait: String,name: String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().authRealName(
                id = id,
                iDnumber = iDnumber,
                iDAddress = iDAddress,
                iDAddressReverse = iDAddressReverse,
                portrait = portrait,
                name = name
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    backSuccess.value = it.data
                }, this::onError, this::onComplise)
        }
    }

//    fun test(){
//        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
//            isShowProgress.value = 0
//            RetrofitFactory.createMainRetrofit().test(
//            )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    backSuccess.value = it.data
//                }, this::onError, this::onComplise)
//        }
//
//    }

}