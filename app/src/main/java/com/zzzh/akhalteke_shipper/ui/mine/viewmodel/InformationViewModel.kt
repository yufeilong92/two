package com.zzzh.akhalteke_shipper.ui.mine.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.UploadImageUitls
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 个人信息，上传头像
 * @Author zfb
 * @Date 2019/5/7 14:12
 */
class InformationViewModel : BaseViewModel() {

    val imageUrl by lazy {
        MutableLiveData<String>()
    }

    /**
     * 上传身份证正面
     * @param imagePath String 图片地址
     * @param params MutableMap<String, String>配置参数
     */
    fun uploadPortrait(
        imagePath: String
    ) {

        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            return
        }
        val params = mutableMapOf<String, String>()
        params.put("userId", Constant.userInfo.id)
        isShowProgress.value = 0
        UploadImageUitls.lubanPicture(mutableMapOf("file" to imagePath), params)
            .flatMap { part ->
                return@flatMap RetrofitFactory.createMainRetrofit().uploadPortrait(part).subscribeOn(Schedulers.io())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                imageUrl.value = it.data
            }, this::onError, this::onComplise)
    }


}