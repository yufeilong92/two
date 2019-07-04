package com.zzzh.akhalteke_shipper.ui.mine.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.BankCardInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BankViewModel : BaseViewModel() {

    val successData by lazy {
        MutableLiveData<String>()
    }

    val bacnCode by lazy {
        MutableLiveData<String>()
    }

    val bankInfos by lazy {
        MutableLiveData<MutableList<BankCardInfo>>()
    }


    /**
     * 添加银行卡
     */
    fun bankCardAdd(bankInfo: BankCardInfo) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().bankCardAdd(
            name = bankInfo.name,
            idCardNumber = bankInfo.idCardNumber,
            cardNumber = bankInfo.cardNumber,
            phone = bankInfo.phone,
            bank = bankInfo.bank
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 银行删除
     */
    fun bankCardDelete(cardId: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().bankCardDelete(
            cardId = cardId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 检查银行卡类型
     */
    fun checkCardType(cardNumber: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().checkCardType(
            cardNumber = cardNumber
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                bacnCode.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 银行卡列表
     */
    fun bankCardList() {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().bankCardList(
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                bankInfos.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 添加银行卡
     */
    fun addBank(bankInfo: BankCardInfo){
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0

        RetrofitFactory.createMainRetrofit().checkCardType(
            cardNumber = bankInfo.cardNumber
        ).flatMap {data ->
            RetrofitFactory.createMainRetrofit().bankCardAdd(
                name = bankInfo.name,
                idCardNumber = bankInfo.idCardNumber,
                cardNumber = bankInfo.cardNumber,
                phone = bankInfo.phone,
                bank = data.data?:""
            )
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data
            }, this::onError, this::onComplise)
    }

}