package com.zzzh.akhalteke_shipper.ui.login.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.UserInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 公司认证
 */
class ComCerViewModel : BaseViewModel() {

    val backSuccess: MutableLiveData<UserInfo> by lazy {
        MutableLiveData<UserInfo>()
    }

    /**
     * 公司认证
     */
    fun authCompany(
        id: String,
        ifBusinessLicense: String,
        corporateName: String,
        businessLicenseId: String,
        businessLicense: String,
        doorPhotos: String,
        businessCard: String,
        areaCode: String,
        detailedAddress: String
    ) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().authCompany(
                id = id,
                ifBusinessLicense = ifBusinessLicense,
                corporateName = corporateName,
                businessLicenseId = businessLicenseId,
                businessLicense = businessLicense,
                doorPhotos = doorPhotos,
                businessCard = businessCard,
                areaCode = areaCode,
                detailedAddress = detailedAddress
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    backSuccess.value = it.data
                }, this::onError, this::onComplise)
        }
    }

}