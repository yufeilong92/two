package com.zzzh.akhalteke_shipper.ui.login.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.IDImageAuthenInfo
import com.zzzh.akhalteke_shipper.bean.UserInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.UploadImageUitls
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.lang.Exception

/**
 *  实名认证
 */
class AuthenViewModel : BaseViewModel() {

    //身份证正面
    val faceInfo by lazy {
        MutableLiveData<IDImageAuthenInfo>()
    }
    //身份证背面
    val backInfo by lazy {
        MutableLiveData<IDImageAuthenInfo>()
    }
    //返回的用户信息
    val userInfo by lazy {
        MutableLiveData<UserInfo>()
    }

    /**
     * 上传身份证正面
     * @param imagePath String 图片地址
     * @param params MutableMap<String, String>配置参数
     */
    fun uploadFaceImage(
        imagePath: String,
        params: MutableMap<String, String>
    ) {

        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            return
        }
        params.put("userId", Constant.userInfo.id)
        isShowProgress.value = 0
        UploadImageUitls.lubanPicture( mutableMapOf("file" to imagePath), params)
            .flatMap { part ->
                return@flatMap RetrofitFactory.createMainRetrofit().uploadFaceImage(part).subscribeOn(Schedulers.io())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                faceInfo.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 上传身份证背面
     * @param imagePath String 图片地址
     * @param params MutableMap<String, String> 配置参数
     */
    fun uploadBackImage(
        imagePath: String,
        params: MutableMap<String, String>
    ) {

        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            return
        }
        params.put("userId", Constant.userInfo.id)
        isShowProgress.value = 0
        UploadImageUitls.lubanPicture(mutableMapOf("file" to imagePath), params)
            .flatMap { part ->
                return@flatMap RetrofitFactory.createMainRetrofit().uploadBackImage(part).subscribeOn(Schedulers.io())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                backInfo.value = it.data
            }, this::onError, this::onComplise)
    }

    //实名认证
    fun realName(
        imagePath: String,
        params: MutableMap<String, String>
    ) {
        try {
            val file = File(imagePath)
            if (!file.exists()) {
                return
            }

            if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
                return
            }
            params.put("id", Constant.userInfo.id)
            isShowProgress.value = 0
            UploadImageUitls.lubanPicture(mutableMapOf(
                "portrait" to file.absolutePath
            ), params)
                .flatMap { part ->
                    return@flatMap RetrofitFactory.createMainRetrofit().realName(part).subscribeOn(Schedulers.io())
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userInfo.value = it.data
                }, this::onError, this::onComplise)

        } catch (e: Exception) {

        }
    }


}