package com.zzzh.akhalteke_shipper.bean

import android.os.Parcel
import android.os.Parcelable

//cost	string	开票金额
//name	string	公司名称
//taxNumber	string	税号
//address	string	单位地址
//phone	string	电话号码
//bank	string	开户银行
//bankNumber	string	银行账户
//receiverName	string	收件人姓名
//receiverPhone	string	收件人手机
//receiverAddress	string	收件地址
//comments	string	商品明细
//status	string	发票状态，1-已审核2-待审核
//courierNumber	string	快递单号
//courierCompany	string	快递公司

class InvoiceInfo(
    var cost: String = "",
    var dao: String = "",
    var name: String = "",
    var taxNumber: String = "",
    var address: String = "",
    var phone: String = "",
    var bank: String = "",
    var bankNumber: String = "",
    var comments: String = "",
    var status: String = "",
    var daoRank: String = "",
    var daoAddress: String = "",

    var receiverName: String = "",
    var invoiceRecordId: String = "",
    var receiverPhone: String = "",
    var receiverAddress: String = "",
    var courierNumber: String = "",
    var courierCompany: String = "",
    var orderIds: String = "",
    var createdTime: String = "",
    var addressId: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cost)
        parcel.writeString(dao)
        parcel.writeString(name)
        parcel.writeString(taxNumber)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(bank)
        parcel.writeString(bankNumber)
        parcel.writeString(comments)
        parcel.writeString(status)
        parcel.writeString(daoRank)
        parcel.writeString(daoAddress)
        parcel.writeString(receiverName)
        parcel.writeString(invoiceRecordId)
        parcel.writeString(receiverPhone)
        parcel.writeString(receiverAddress)
        parcel.writeString(courierNumber)
        parcel.writeString(courierCompany)
        parcel.writeString(orderIds)
        parcel.writeString(createdTime)
        parcel.writeString(addressId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InvoiceInfo> {
        override fun createFromParcel(parcel: Parcel): InvoiceInfo {
            return InvoiceInfo(parcel)
        }

        override fun newArray(size: Int): Array<InvoiceInfo?> {
            return arrayOfNulls(size)
        }
    }
}
//name	string	公司名称
//taxNumber	string	税号
//address	string	单位地址
//phone	string	电话号码
//bank	string	开户银行
//bankNumber	string	银行账户
//comments	备注	*
//daoAddress	string	道认证法人授权书图片地址

//    shipperId	必填	string	货主id
//    name	必填	string	发票抬头
//    taxNumber	必填	string	税号
//    address	必填	string	单位地址
//    phone	必填	string	电话号码
//    bank	必填	string	开户银行
//    bankNumber	必填	string	银行账户
//    comments	必填	string	商品明细
//    orderIds	必填	string	开票的订单id，多个使用逗号相连接
//    addressId	必填	string	地址id

//receiverName	必填	string	收件人姓名
//    receiverPhone	必填	string	收件人手机
//    receiverAddress	必填	string	收件地址