package com.zzzh.akhalteke_shipper.retrofit.gsonFactory

import com.google.gson.annotations.SerializedName

class BaseErrorEntity{
    @SerializedName("code")
    var code:String = ""

    @SerializedName("msg")
    var msg:String = ""

    @SerializedName("data")
    var data:String = ""
}