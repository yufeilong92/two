package com.zzzh.akhalteke_shipper.ui.login.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.UserInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import com.zzzh.akhalteke_shipper.utils.PreferencesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 登录
 */
class LoginViewModel : BaseViewModel() {

    //注册码返回
    val codeInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    //用户信息
    val userInfo: MutableLiveData<UserInfo> by lazy {
        MutableLiveData<UserInfo>()
    }

    /**
     * 获取手机注册码
     * @param phone String 手机号
     */
    fun getCode(phone: String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().toGetCode(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    codeInfo.value = it.data
                }, this::onError, this::onComplise)
        }
    }

    /**
     * 提交数据，验证登录
     * @param phone String 手机号
     * @param code String  手机验证码
     */
    fun checkCode(phone: String,code:String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().checkCode(
                code= code,
                phone = phone
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data ->
                    val info = data?.data
                    PreferencesUtils().shareUserInfo(info)
                    userInfo.value = info
                }, this::onError, this::onComplise)
        }
    }

}