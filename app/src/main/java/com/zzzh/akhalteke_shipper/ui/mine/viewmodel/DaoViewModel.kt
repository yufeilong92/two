package com.zzzh.akhalteke_shipper.ui.mine.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.BussinessInfo
import com.zzzh.akhalteke_shipper.bean.InvoiceInfo
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.UploadImageUitls
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DaoViewModel : BaseViewModel() {

    val successData by lazy {
        MutableLiveData<String>()
    }

    val invoiceData by lazy {
        MutableLiveData<InvoiceInfo>()
    }

    val bussInfo by lazy {
        MutableLiveData<BussinessInfo>()
    }

    /**
     * //识别营业执照
     * @param imagePath String
     * @param params MutableMap<String, String>
     */
    fun authBussiness(
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
                return@flatMap RetrofitFactory.createMainRetrofit().authBussiness(part).subscribeOn(Schedulers.io())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                bussInfo.value = it.data
            }, this::onError, this::onComplise)
    }


    /**
     * 道认证提交
     */
    fun daoAuth(
        imagePath: String,
        params: MutableMap<String, String>
    ) {

        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) {
            return
        }
        params.put("shipperId", Constant.userInfo.id)
        isShowProgress.value = 0
        UploadImageUitls.lubanPicture(mutableMapOf("dao" to imagePath), params)
            .flatMap { part ->
                return@flatMap RetrofitFactory.createMainRetrofit().daoAuth(part).subscribeOn(Schedulers.io())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 道认证提交
     */
    fun daoAuth(info: InvoiceInfo) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().daoAuth(
            dao = info.dao,
            name = info.name,
            taxNumber = info.taxNumber,
            address = info.address,
            phone = info.phone,
            bank = info.bank,
            bankNumber = info.bankNumber,
            comments = info.comments
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data ?: ""
            }, this::onError, this::onComplise)
    }

    /**
     * 发票资质信息
     */
    fun invoiceInfo() {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().invoiceInfo(
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                invoiceData.value = it.data
            }, this::onError, this::onComplise)
    }

    /**
     * 通过道认证后填写发票信息
     * @param info InvoiceInfo
     */
    fun invoiceInsert(info: InvoiceInfo) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().invoiceInsert(
            name = info.name,
            taxNumber = info.taxNumber,
            address = info.address,
            phone = info.phone,
            bank = info.bank,
            bankNumber = info.bankNumber,
            comments = info.comments
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data ?: ""
            }, this::onError, this::onComplise)
    }


    /**
     * 更新发票信息
     * @param info InvoiceInfo
     */
    fun invoiceUpdate(info: InvoiceInfo) {
        if (!RetrofitFactory.judgmentNetWork(BaseApplication.toInstance())) return
        isShowProgress.value = 0
        RetrofitFactory.createMainRetrofit().invoiceUpdate(
            name = info.name,
            taxNumber = info.taxNumber,
            address = info.address,
            phone = info.phone,
            bank = info.bank,
            bankNumber = info.bankNumber,
            comments = info.comments
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                successData.value = it.data ?: ""
            }, this::onError, this::onComplise)
    }

//    shipperId	必填	string	货主id
//    name	必填	string	页发票抬头
//    taxNumber	必填	string	税号
//    address	必填	string	单位地址
//    phone	必填	string	电话号码
//    bank	必填	string	开户银行
//    bankNumber	必填	string	银行账户
//    comments	非必填	string	商品明细
}