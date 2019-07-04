package com.zzzh.akhalteke_shipper.bean

class MineInfo(
    var balance:String = "",
    var daoStatus:String = "",
    var daoRank:String = "",
    var ifInvoiceInfo:String = ""
)

//balance	string	余额
//daoStatus	string	道认证状态,1通过，是2未认证，3审核中，4驳回
//daoRank	string	道认证等级由低到高123456
//ifInvoiceInfo	string	是否填写发票信息1是2否