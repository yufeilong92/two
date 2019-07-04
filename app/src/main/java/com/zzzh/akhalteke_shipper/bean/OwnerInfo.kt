package com.zzzh.akhalteke_shipper.bean

class OwnerInfo(
        var name:String = "",
        var registerTime:String = "",
        var phone:String = "",
        var portrait:String = "",
        var corporateName:String = "",
        var areaAddress:String = "",
        var ifRealCertification:String = "",
        var ifCompanyCertification:String = "",
        var goodsCount:String = "",
        var orderCount:String = "",
        var pageListVo:PageInfo<SourceInfo> = PageInfo(),
        var goodsList:MutableList<SourceInfo>? = null
)
//{
//    "name":"王海倩",
//    "registerTime":"1552384554000",
//    "phone":"18838985581",
//    "portrait":"images/akhalteke/test/shipper/portrait/2019-03-12-17-57-16SHP52384554230001.jpg",
//    "corporateName":"zzzh",
//    "areaAddress":null,
//    "ifRealCertification":"1",
//    "ifCompanyCertification":"2",
//    "goodsCount":"25",
//    "orderCount":"1",
//    "pageListVo":{
//    "pageInfo":{
//        "total":2,
//        "page":0,
//        "size":2,
//        "totalPage":1,
//        "first":true,
//        "last":true
//    },
//    "list":[
//    {
//        "goodsId":"GDS53858968580008",
//        "loadAreaName":"北京市 东城区",
//        "unloadAreaName":"北京市 丰台区",
//        "carLength":"1.8米",
//        "carType":"平板",
//        "weightVolume":"100吨200m³",
//        "loadTime":"0"
//    },
//    {
//        "goodsId":"GDS53858958830007",
//        "loadAreaName":"北京市 东城区",
//        "unloadAreaName":"北京市 丰台区",
//        "carLength":"1.8米",
//        "carType":"平板",
//        "weightVolume":"100吨200m³",
//        "loadTime":"0"
//    }
//    ]
//}
//}