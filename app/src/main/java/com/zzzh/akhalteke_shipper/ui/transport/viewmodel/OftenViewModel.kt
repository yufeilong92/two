package com.zzzh.akhalteke_shipper.ui.transport.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.GoodsDetailInfo
import com.zzzh.akhalteke_shipper.bean.PageInfo
import com.zzzh.akhalteke_shipper.bean.SendGoodsInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 常用发货源
 */
class OftenViewModel: BaseViewModel(){

    val infos: MutableLiveData<PageInfo<GoodsDetailInfo>> by lazy {
        MutableLiveData<PageInfo<GoodsDetailInfo>>()
    }

    val deleteBack:MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    /**
     * 获取常发货源列表
     * @param page Int 页码
     */
    fun getOftenList(page: Int) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().getOftenList(
                page = page
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    infos.value = it.data
                }, this::onError, this::onComplise)
        }
    }

    /**
     * 删除
     */
    fun goodsDeleteOften(goodsId:String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().goodsDeleteOften(
                goodsId = goodsId
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    deleteBack.value = it.data?:""
                }, this::onError, this::onComplise)
        }
    }

}