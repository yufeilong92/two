package com.zzzh.akhalteke_shipper.retrofit

import com.zzzh.akhalteke_shipper.retrofit.gsonFactory.BaseEntity
import com.zzzh.akhalteke_shipper.utils.Constant
import io.reactivex.Observable
import retrofit2.http.*

interface MineRetrofit {

    @FormUrlEncoded
    @POST("appUser/n/sms/validate")//验证短信验证码
    fun validate(@Field("mobile") mobile: String,
                 @Field("smsCode") smsCode: String
    ): Observable<BaseEntity<String>>


    @GET("login/shipper/getCode")//获取验证码
    fun noticeNewOne(
        @Query("phone") phone: String
    ): Observable<BaseEntity<String>>

}