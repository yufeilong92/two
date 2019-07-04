package com.zzzh.akhalteke_shipper.bean

class WalletInfo(
    var usableAmount: String = "",
    var pageInfo: PageDtoInfo = PageDtoInfo(),
    var list: MutableList<WalletRecordInfo> = mutableListOf()
) {
    class WalletRecordInfo(
        var eventType: String = "",
        var sum: String = "",
        var createdTime: String = ""
    )
}

//eventType	string	变更事件类型冻结-1解冻-2抵消-3充值-4提现-5
//sum	string	变更金额
//createdTime	string	创建时间