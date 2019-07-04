package com.zzzh.akhalteke_shipper.ui.mine.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.*
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 钱包管理
 */
class WalletViewModel:BaseViewModel(){

    //成功数据
    val successData by lazy {
        MutableLiveData<String>()
    }

    //钱包信息
    val walletInfo by lazy {
        MutableLiveData<WalletInfo>()
    }

    //银行卡列表
    val bankInfos by lazy {
        MutableLiveData<MutableList<BankInfo>>()
    }

    //我的账户信息
    val balance by lazy {
        MutableLiveData<AccountInfo>()
    }

    //我的消费记录
    val cashInfos by lazy {
        MutableLiveData<PageInfo<CashInfo>>()
    }

    /**
     * 钱包
     * @param page Int
     */
    fun walletAccount(page: Int) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().walletAccount(
            page = page
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                walletInfo.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 充值时展示公司账户信息
     */
    fun getCompanyAccount() {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().getCompanyAccount(
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                bankInfos.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 获取账户余额
     */
    fun accountBalance() {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().accountBalance(
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                balance.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 提现
     * @param sum String 提现金额
     * @param bankId String 银行id
     */
    fun accountCash(sum: String,bankId: String) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().accountCash(
            sum = sum,
            bankId = bankId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 提现记录
     * @param page Int
     */
    fun cashList(page: Int) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().cashList(
            page = page
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cashInfos.value = it.data
            }, this::onError, this::onComplise)
    }


}