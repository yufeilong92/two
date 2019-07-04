package com.zzzh.akhalteke_shipper.ui.find.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.MainSendInfo
import com.zzzh.akhalteke_shipper.bean.OwnerInfo
import com.zzzh.akhalteke_shipper.bean.PageInfo
import com.zzzh.akhalteke_shipper.bean.SourceInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class GoodsViewModel : BaseViewModel() {

    val goodsList by lazy {
        MutableLiveData<PageInfo<SourceInfo>>()
    }

    val senfInfo: MutableLiveData<MainSendInfo> by lazy {
        MutableLiveData<MainSendInfo>()
    }

    val ownerInfo by lazy {
        MutableLiveData<OwnerInfo>()
    }


    val ownerGoodsList by lazy {
        MutableLiveData<PageInfo<SourceInfo>>()
    }

    /**
     * 获取货源列表（发货中）
     */
    fun goodsNowList(
        loadAreaCode: String,
        unloadAreaCode: String,
        sortType: String = "1",
        type: String,
        loadTime: String,
        carLength: String,
        carType: String,
        page: Int
    ) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return

        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().goodsNowList(
            loadAreaCode = loadAreaCode,
            unloadAreaCode = unloadAreaCode,
            sortType = sortType,
            type = type,
            loadTime = loadTime,
            carLength = carLength,
            carType = carType,
            page = page
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                goodsList.value = it.data
            }, this::onError, this::onComplise)

    }


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
     * 货主简介
     */
    fun getShipperById(shipperId: String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().getShipperById(
                shipperId = shipperId
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    ownerInfo.value = it.data
                }, this::onError, this::onComplise)
        }
    }


    fun getShipperGoods(shipperId: String,page: Int) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().getShipperGoods(
                shipperId = shipperId,
                page = page
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    ownerGoodsList.value = it.data
                }, this::onError, this::onComplise)
        }
    }

}