package com.zzzh.akhalteke_shipper.bean

import android.os.Parcel
import android.os.Parcelable

class DriverInfo(
    var id:String = "",
    var carOwnerId:String = "",
    var name:String = "",
    var phone:String = "",
    var portrait:String = "",
    var plateNumber:String = "",
    var ifRealCertification:String = "",
    var ifDriver:String = "",
    var ifCar:String = "",
    var carLengthAndType:String = ""
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
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(carOwnerId)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(portrait)
        parcel.writeString(plateNumber)
        parcel.writeString(ifRealCertification)
        parcel.writeString(ifDriver)
        parcel.writeString(ifCar)
        parcel.writeString(carLengthAndType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DriverInfo> {
        override fun createFromParcel(parcel: Parcel): DriverInfo {
            return DriverInfo(parcel)
        }

        override fun newArray(size: Int): Array<DriverInfo?> {
            return arrayOfNulls(size)
        }
    }
}