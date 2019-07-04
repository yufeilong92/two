package com.zzzh.akhalteke_shipper.bean

import android.os.Parcel
import android.os.Parcelable

class GoodsDetailInfo(
    var goodsOftenId:String = "",
    var shipperPortrait:String = "",
    var shipperId:String = "",
    var shipperName:String = "",
    var shipperPhone:String = "",
    var corporateName:String = "",
    var carLengthVO:String = "",
    var carTypeVO:String = "",
    var weightVolumeVO:String = "",
    var loadAreaCodeVO:String = "",
    var unloadAreaCodeVO:String = "",
    var createdTime:String = "",
    var loadAreaCode:String = "",
    var loadAddress:String = "",
    var unloadAreaCode:String = "",
    var unloadAddress:String = "",
    var carLengthId1:String = "",
    var carLengthId2:String = "",
    var carLengthId3:String = "",
    var carTypeId1:String = "",
    var carTypeId2:String = "",
    var carTypeId3:String = "",
    var type:String = "",
    var weight:String = "",
    var volume:String = "",
    var name:String = "",
    var loadTime:String = "",
    var loadType:String = "",
    var payType:String = "",
    var cost:String = "",
    var comments:String = ""
):Parcelable {
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
        parcel.writeString(goodsOftenId)
        parcel.writeString(shipperPortrait)
        parcel.writeString(shipperId)
        parcel.writeString(shipperName)
        parcel.writeString(shipperPhone)
        parcel.writeString(corporateName)
        parcel.writeString(carLengthVO)
        parcel.writeString(carTypeVO)
        parcel.writeString(weightVolumeVO)
        parcel.writeString(loadAreaCodeVO)
        parcel.writeString(unloadAreaCodeVO)
        parcel.writeString(createdTime)
        parcel.writeString(loadAreaCode)
        parcel.writeString(loadAddress)
        parcel.writeString(unloadAreaCode)
        parcel.writeString(unloadAddress)
        parcel.writeString(carLengthId1)
        parcel.writeString(carLengthId2)
        parcel.writeString(carLengthId3)
        parcel.writeString(carTypeId1)
        parcel.writeString(carTypeId2)
        parcel.writeString(carTypeId3)
        parcel.writeString(type)
        parcel.writeString(weight)
        parcel.writeString(volume)
        parcel.writeString(name)
        parcel.writeString(loadTime)
        parcel.writeString(loadType)
        parcel.writeString(payType)
        parcel.writeString(cost)
        parcel.writeString(comments)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GoodsDetailInfo> {
        override fun createFromParcel(parcel: Parcel): GoodsDetailInfo {
            return GoodsDetailInfo(parcel)
        }

        override fun newArray(size: Int): Array<GoodsDetailInfo?> {
            return arrayOfNulls(size)
        }
    }
}