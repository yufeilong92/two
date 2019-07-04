package com.zzzh.akhalteke_shipper.ui.publish.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 发布货源
 */
class PublishViewModel : BaseViewModel() {

    val backSuccess: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
     * 发布货源信息
     * @param loadAreaCode String  发货地址code
     * @param loadAddress String   发货详细地址
     * @param unloadAreaCode String 卸货地址code
     * @param unloadAddress String  卸货详细地址
     * @param carLengthId1 String   车长1
     * @param carLengthId2 String   车长2
     * @param carLengthId3 String   车长3
     * @param carTypeId1 String     车型1
     * @param carTypeId2 String     车型2
     * @param carTypeId3 String     车型3
     * @param type String           整车/零担
     * @param weight String         重量
     * @param volume String         体积
     * @param name String           货物名称
     * @param loadTime String       装货时间
     * @param loadType String       装货类型
     * @param payType String        支付类型
     * @param cost String           运费
     * @param comments String       备注
     * @param ifDriver String       是否指定司机
     * @param carOwnerId String     司机id
     * @param ifGoodsOften String   是否保存地址
     */
    fun publish(
        loadAreaCode: String,
        loadAddress: String,
        unloadAreaCode: String,
        unloadAddress: String,
        carLengthId1: String,
        carLengthId2: String,
        carLengthId3: String,
        carTypeId1: String,
        carTypeId2: String,
        carTypeId3: String,
//        type: String,
        weight: String,
        volume: String,
        name: String,
        loadTime: String,
        loadType: String,
        payType: String,
        cost: String,
        comments: String,
        ifDriver: String,
        carOwnerId: String,
        ifGoodsOften: String
    ) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().publish(
                loadAreaCode = loadAreaCode,
                loadAddress = loadAddress,
                unloadAreaCode = unloadAreaCode,
                unloadAddress = unloadAddress,
                carLengthId1 = carLengthId1,
                carLengthId2 = carLengthId2,
                carLengthId3 = carLengthId3,
                carTypeId1 = carTypeId1,
                carTypeId2 = carTypeId2,
                carTypeId3 = carTypeId3,
//                type = type,
                weight = weight,
                volume = volume,
                name = name,
                loadTime = loadTime,
                loadType = loadType,
                payType = payType,
                cost = cost,
                comments = comments,
                ifDriver = ifDriver,
                carOwnerId = carOwnerId,
                ifGoodsOften = ifGoodsOften
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    backSuccess.value = it.data
                }, this::onError, this::onComplise)
        }
    }

}