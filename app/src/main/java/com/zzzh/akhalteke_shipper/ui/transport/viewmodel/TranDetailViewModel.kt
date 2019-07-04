package com.zzzh.akhalteke_shipper.ui.transport.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.GoodsDetailInfo
import com.zzzh.akhalteke_shipper.bean.MainSendInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 货源详情
 */
class TranDetailViewModel : BaseViewModel() {

    val senfInfo: MutableLiveData<MainSendInfo> by lazy {
        MutableLiveData<MainSendInfo>()
    }

    val goodsInfo: MutableLiveData<GoodsDetailInfo> by lazy {
        MutableLiveData<GoodsDetailInfo>()
    }

    /**
     * 查看货源详情（发货中）
     * @param goodsId String
     */
    fun goodsDetails(goodsId: String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().goodsDetails(
                goodsId = goodsId
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    senfInfo.value = it.data
                }, this::onError, this::onComplise)
        }
    }

    /**
     * 常用地址
     */
    fun oftenGoodsDetail(goodsId: String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().oftenGoodsDetail(
                goodsId = goodsId
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    goodsInfo.value = it.data
                }, this::onError, this::onComplise)
        }
    }

}