package com.zzzh.akhalteke_shipper.bean

import android.os.Parcel
import android.os.Parcelable

class AddressInfo(
    var id:String = "",
    var receiverName:String = "",
    var phone:String = "",
    var areaCodeName:String = "",
    var areaCode:String = "",
    var address:String = "",
    var postalCode:String = "",
    var ifDefault:String = ""
):Parcelable{
    constructor(parcel: Parcel) : this(
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
        parcel.writeString(id)
        parcel.writeString(receiverName)
        parcel.writeString(phone)
        parcel.writeString(areaCodeName)
        parcel.writeString(areaCode)
        parcel.writeString(address)
        parcel.writeString(postalCode)
        parcel.writeString(ifDefault)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddressInfo> {
        override fun createFromParcel(parcel: Parcel): AddressInfo {
            return AddressInfo(parcel)
        }

        override fun newArray(size: Int): Array<AddressInfo?> {
            return arrayOfNulls(size)
        }
    }

}