package com.zzzh.akhalteke_shipper.retrofit.gsonFactory

import com.google.gson.annotations.SerializedName

class BaseNoEntity{

    @SerializedName("code")
    var code:String = ""

    @SerializedName("msg")
    var msg:String = ""

}