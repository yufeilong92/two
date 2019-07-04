package com.zzzh.akhalteke_shipper.bean

data class SourceInfo(
    var goodsId:String = "",
    var shipperId:String = "",
    var shipperPortrait:String = "",
    var shipperName:String = "",
    var shipperPhone:String = "",
    var loadAreaCode:String = "",
    var loadAreaName:String = "",
    var unloadAreaCode:String = "",
    var unloadAreaName:String = "",
    val loadAddress:String?="",//装货地址详情
    val unloadAddress:String?="",//卸货地址详情
    var carLength:String = "",
    var carType:String = "",
    var goodsType:String = "",
    var weightVolume:String = "",
    var weight:String = "",
    var volume:String = "",
    var goodsName:String = "",
    var loadType:String = "",
    var payType:String = "",
    var comments:String = "",
    var loadTime:String = "",
    var ifRealCertification:String = "",
    var ifCompanyCertification:String = "",
    var createdTime:String = ""

)
//goodsId	string	货源ID
//shipperId	string	货主ID
//shipperPortrait	string	货主头像
//shipperName	string	货主姓名
//shipperPhone	string	货主电话
//loadAreaCode	string	装货地地区编码
//unloadAreaCode	string	卸货地地区编码
//carLength	string	车长
//carType	string	车型
//ifRealCertification	string	1 是， 2 否实名认证
//ifCompanyCertification	string	1 是， 2 否车辆认证
//goodsType	string	用车类型，整车-1、零担-2
//weightVolume	string	货源重量和体积
//goodsName	string	货物名称
//loadType	string	装卸方式一装一卸-1、一装两卸-2、一装多卸-3、两装一卸-4、两装两卸-5、多装多卸-6
//payType	string	支付方式
//comments	string	备注
//createdTime	string	发布时间