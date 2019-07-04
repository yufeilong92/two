package com.zzzh.akhalteke_shipper.retrofit.gsonFactory

import com.google.gson.annotations.SerializedName

/**
 * Created by apple on 2018/7/8.
 */
class BaseEntity<E>(){

    @SerializedName("code")
    var code:String = ""

    @SerializedName("message")
    var message:String = ""

    @SerializedName("data")
    var data:E? = null

}
