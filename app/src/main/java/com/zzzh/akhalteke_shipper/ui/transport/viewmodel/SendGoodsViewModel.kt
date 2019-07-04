package com.zzzh.akhalteke_shipper.ui.transport.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.PageInfo
import com.zzzh.akhalteke_shipper.bean.SendGoodsInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 发货
 */
class SendGoodsViewModel:BaseViewModel(){

    val infos: MutableLiveData<PageInfo<SendGoodsInfo>> by lazy {
        MutableLiveData<PageInfo<SendGoodsInfo>>()
    }

    val insertOftenBack:MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    val deleteBack:MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    /**
     * 获取货主自身发布货源
     * @param page Int 页码
     * @param isRelease String  1-发布中，2-发布历史
     */
    fun getOwnerGoods(page: Int,isRelease:String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().getOwnerGoods(
                page = page,
                isRelease = isRelease
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    infos.value = it.data
                }, this::onError, this::onComplise)
        }
    }

    /**
     * 存为常用
     */
    fun goodsInsertOften(goodsId:String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().goodsInsertOften(
                goodsId = goodsId
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    insertOftenBack.value = it.data?:""
                }, this::onError, this::onComplise)
        }
    }

    /**
     * 删除
     */
    fun goodsDelete(goodsId:String) {
        if (RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            isShowProgress.value = 0
            RetrofitFactory.createMainRetrofit().goodsDelete(
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