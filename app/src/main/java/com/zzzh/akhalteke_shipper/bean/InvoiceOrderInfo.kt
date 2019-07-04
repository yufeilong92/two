package com.zzzh.akhalteke_shipper.bean

class InvoiceOrderInfo(
    var orderId:String = "",
    var loadAreaName:String = "",
    var unloadAreaName:String = "",
    var carLengthType:String = "",
    var weightVolume:String = "",
    var totalAmount:String = "",
    var createdTime:String = "",
    var temp:Int = 0
)

//orderId	string	订单主键ID
//loadAreaName	string	装货地区
//unloadAreaName	string	卸货地区
//carLengthType	string	车长车型
//weightVolume	string	重量体积
//totalAmount	string	开票金额
//createdTime	string	创建时间