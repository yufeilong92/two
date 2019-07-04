package com.zzzh.akhalteke_shipper.updateapp

import android.app.Dialog
import android.arch.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.VersionInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.retrofit.gsonFactory.ResultException
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 更新app
 */
class UpdateViewModel:BaseViewModel(){

    val vInfo by lazy {
        MutableLiveData<VersionInfo>()
    }

    fun updateHttp( isProcess: Boolean = false) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        if (isProcess) {
            isShowProgress.value = 0
        }
        RetrofitFactory.createMainRetrofit().shipperVersion(
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, { ex ->
                if (isProcess) {
                    isShowProgress.value = 1
                }

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
                            if (ex.code == "-1038") {
                                val responseStr = ex.data
                                vInfo.value = Gson().fromJson<VersionInfo>(responseStr, VersionInfo::class.java)
                            }
                            ""
                        } else {
                            "网络连接异常，请稍后再试..."
                        }
                    }
                    if (!ToolUtils.isEmpty(error)) {
//                        ToastView.setToasd(BaseApplication.toInstance(), error)
                    }
                    ex.printStackTrace()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                } finally {

                }

            }, this::onComplise)
    }

}