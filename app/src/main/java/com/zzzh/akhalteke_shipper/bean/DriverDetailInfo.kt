package com.zzzh.akhalteke_shipper.bean

class DriverDetailInfo(
    var id:String = "",
    var carOwnerId:String = "",
    var name:String = "",
    var number:String = "",
    var phone:String = "",
    var portrait:String = "",
    var plateNumber:String = "",
    var carLength:String = "",
    var carType:String = "",
    var load:String = "",
    var ifFamiliarCar:String = "",
    var orderTotal:String = "",
    var orderWithShipperTotal:String = "",
    var orderList:MutableList<SourceInfo> = mutableListOf(),
    var orderWithShipperList:MutableList<SourceInfo> = mutableListOf()
)

//{
//    "id":"CZ5064707857000012",
//    "name":"李江涛",
//    "number":"41052719910505545X",
//    "phone":"18300700505",
//    "portrait":"images/akhalteke/test/carowner/portrait/CZ5064707857000012.jpg",
//    "plateNumber":"豫A00001",
//    "carLength":"8.7",
//    "carType":"高栏",
//    "load":"88",
//    "ifFamiliarCar":"1",
//    "orderList":null,
//    "orderWithShipperList":null
//}


//{
//    "carOwnerId":"CAO53649330690001",
//    "name":"于飞龙",
//    "number":"410222199208095552",
//    "phone":"18317837561",
//    "portrait":"images/akhalteke/test/carowner/portrait/2019-03-27-09-24-22CAO53649330690001.jpg",
//    "plateNumber":"豫ASK057",
//    "carLength":null,
//    "carType":"保温",
//    "load":"20",
//    "ifFamiliarCar":"1",
//    "orderList":[
//
//    ],
//    "orderTotal":0,
//    "orderWithShipperList":[
//
//    ],
//    "orderWithShipperTotal":0
//}