package com.zzzh.akhalteke_shipper.bean

class CashInfo(
    var sum:String = "",
    var status:String = "",
    var bank:String = "",
    var bankNumber:String = "",
    var createdTime:String = ""
)

//sum	string	金额
//status	string	状态1-成功，2-审核中，3失败
//bank	string	提现银行
//bankNumber	string	提现卡号
//createdTime	string	发起时间