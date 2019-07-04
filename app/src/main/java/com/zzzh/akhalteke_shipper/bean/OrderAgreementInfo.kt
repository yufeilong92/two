package com.zzzh.akhalteke_shipper.bean

class OrderAgreementInfo(
    var id:String = "",
    var orderId:String = "",
    var weightVolume:String = "",
    var weight:String = "",
    var volume:String = "",
    var carTypeLength:String = "",
    var sumAmount:String = "",
    var companyAmount:String = "",
    var payTime:String = "",
    var goodsName:String = "",
    var loadTime:String = "",
    var unloadTime:String = "",
    var loadAddress:String = "",
    var unloadAddress:String = "",
    var appointment:String = "",
    var launchTime:String = "",
    var confirmTime:String = "",
    var shipperName:String = "",
    var shipperPhone:String = "",
    var shipperAddress:String = "",
    var ownerName:String = "",
    var ownerPhone:String = "",
    var ownerCarNumber:String = "",
    var status:String = "",
    var totalAmount:String = "",
    var driverPortrait:String = "",
    var shipperPortrait:String = ""
)
//id	string	协议id
//orderId	string	订单id
//weightVolume	string	重量体积
//carTypeLength	string	车长车型
//sumAmount	string	总运费
//companyAmount	string	通过平台支付运费
//payTime	string	付款时间
//goodsName	string	货物名称
//loadTime	string	装货时间
//unloadTime	string	卸货时间
//loadAddress	string	装货地址
//unloadAddress	string	卸货地址
//appointment	string	其他约定
//launchTime	string	发起时间
//confirmTime	string	确认时间
//shipperName	string	发货人姓名
//shipperPhone	string	发货人手机
//shipperAddress	string	发货人地址
//ownerName	string	承运司机名字
//ownerPhone	string	承运司机手机
//ownerCarNumber	string	承运司机车牌号
//status	string	状态
//driverPortrait	string	承运司机头像
//shipperPortrait	string	货主头像