package com.zzzh.akhalteke_shipper.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.push.PushHelper
import com.zzzh.akhalteke_shipper.retrofit.gsonFactory.ResultException
import com.zzzh.akhalteke_shipper.utils.*
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {

    //是否显示加载中dialog
    val isShowProgress: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    //错误码拦截
    val errorCode: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    //错误信息，配合error code使用
    val errorMsg: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    //错误信息
    val resonseError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    /**
     * 请求中的异常处理
     * @param ex Throwable
     */
    fun onError(ex: Throwable) {
        isShowProgress.value = 1
        try {
            val error = if (ex is SocketTimeoutException) {
                "网络连接超时，请稍后再试..."
            } else if (ex is ConnectException) {
                "网络连接超时，请稍后再试..."
            } else if (ex is UnknownHostException) {
                "网络连接超时，请稍后再试..."
            } else if (ex is HttpException) {
                "网络连接异常，请稍后再试..."
            } else {
                if (ex is ResultException) {
                    when (ex.code) {
                        "3031" -> {
                            if(!Constant.ifToLogin){
                                PushHelper.deleteAlias()
                                PreferencesUtils().clearUserInfo()
                                RouterTo(BaseApplication.toInstance()).jumpToLoginNoTrans()
                            }
                            ""
                        }
                        else -> {
                            errorCode.value = ex.code
                            errorMsg.value = ex.msg
                            if (resonseError.value != null && resonseError.value!!) {
                                ""
                            } else {
                                ex.msg //抛出异常，抓取数据
                            }
                        }
                    }
                } else {
                    "网络连接异常，请稍后再试..."
                }
            }
            if (!ToolUtils.isEmpty(error)) {
                ToastView.setToasd(BaseApplication.toInstance(), error)
            }
            ex.printStackTrace()
        } catch (e1: IOException) {
            e1.printStackTrace()
        } finally {
        }
    }

    /**
     * 请求中的完成
     */
    open fun onComplise() {
        isShowProgress.value = 1
    }


}