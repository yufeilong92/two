package com.zzzh.akhalteke_shipper.bean

import android.os.Parcel
import android.os.Parcelable

class OrderInfo(
    var orderId: String = "",
    var carOwnerId: String = "",
    var carOwnerName: String = "",
    var carOwnerPlate: String = "",
    var carOwnerPortrait: String = "",
    var carOwnerPhone: String = "",
    var goodsId: String = "",
    var loadAreaName: String = "",
    var loadAddress: String = "",
    var unloadAreaName: String = "",
    var unloadAddress: String = "",
    var carLengthType: String = "",
    var weight: String = "",
    var volume: String = "",
    var weightVolume: String = "",
    var name: String = "",
    var loadTime: String = "",
    var unloadTime: String = "",
    var cost: String = "",
    var createdTime: String = "",
    var ifAgreement: String = "",//协议状态1待确认，2未发起，3拒绝，4同意
    var ifPay: String = "",
    var status: String = "",//订单状态1-运输中，2-已完成，3-已取消
    var receipt: String = "",//	是否有回单，1是2否
    var totalMoney: String = ""//通过平台总费用（包含服务费）
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
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(orderId)
        parcel.writeString(carOwnerId)
        parcel.writeString(carOwnerName)
        parcel.writeString(carOwnerPlate)
        parcel.writeString(carOwnerPortrait)
        parcel.writeString(carOwnerPhone)
        parcel.writeString(goodsId)
        parcel.writeString(loadAreaName)
        parcel.writeString(loadAddress)
        parcel.writeString(unloadAreaName)
        parcel.writeString(unloadAddress)
        parcel.writeString(carLengthType)
        parcel.writeString(weight)
        parcel.writeString(volume)
        parcel.writeString(weightVolume)
        parcel.writeString(name)
        parcel.writeString(loadTime)
        parcel.writeString(unloadTime)
        parcel.writeString(cost)
        parcel.writeString(createdTime)
        parcel.writeString(ifAgreement)
        parcel.writeString(ifPay)
        parcel.writeString(status)
        parcel.writeString(receipt)
        parcel.writeString(totalMoney)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderInfo> {
        override fun createFromParcel(parcel: Parcel): OrderInfo {
            return OrderInfo(parcel)
        }

        override fun newArray(size: Int): Array<OrderInfo?> {
            return arrayOfNulls(size)
        }
    }
}
//orderId	string	订单表主键
//carOwnerId	string	车主id
//carOwnerName	string	车主名字
//carOwnerPlate	string	车主车牌
//carOwnerPortrait	string	车主头像
//carOwnerPhone	string	车主手机
//goodsId	string	货源id
//loadAreaName	string	装货地区名称
//loadAddress	string	装货地详细地址
//unloadAreaName	string	卸货地区名称
//unloadAddress	string	卸货地详细地址
//carLengthType	string	车长车型
//weight	string	车主载重
//name	string	货物名称
//loadTime	string	装货时间
//cost	string	运费
//createdTime	string	创建时间
//ifAgreement	string	是否签订协议
//ifPay	string	是否支付