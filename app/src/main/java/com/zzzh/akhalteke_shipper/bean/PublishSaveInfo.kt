package com.zzzh.akhalteke_shipper.bean

class PublishSaveInfo(
    var userId: String = "",
    var loadAreaCode: String = "",
    var loadAreaName: String = "",
    var loadAddress: String = "",
    var unloadAreaCode: String = "",
    var unloadAreaName: String = "",
    var unloadAddress: String = "",

    var type: String = "",
    var weight: String = "",
    var volume: String = "",
    var name: String = "",
    var loadTime: String = "",
    var loadType: String = "",
    var payType: String = "",
    var cost: String = "",
    var comments: String = "",
    var ifDriver: String = "",
    var carOwnerId: String = "",
    var ifGoodsOften: Int = 0,
    var truckLs:ArrayList<StringInfo> = arrayListOf<StringInfo>(),
    var truckCs:ArrayList<StringInfo> = arrayListOf<StringInfo>()
)