package com.zzzh.akhalteke_shipper.retrofit.gsonFactory

import java.io.IOException

class ResultException(
        var msg: String = "",
        var code: String = "",
        var data: String = "") : IOException() {

}