package com.zzzh.akhalteke_shipper.bean

class UserInfo(
    var ifRealCertification: String = "",
    var ifCompanyCertification: String = "",
    var id: String = "",
    var token: String = "",
    var name: String = "",
    var phone: String = "",
    var portrait: String = "",
    var corporateName: String = "",
    var number: String = ""
)

//ifRealCertification	string	是否实名认证
//ifCompanyCertification	string	是否公司认证
//id	string	货主表主键
//name	string	姓名
//phone	string	手机号
//number	string	身份证号
//portrait	string	头像
//corporateName	string	公司名称