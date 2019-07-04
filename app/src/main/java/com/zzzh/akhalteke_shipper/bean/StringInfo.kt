package com.zzzh.akhalteke_shipper.bean

import android.os.Parcel
import android.os.Parcelable

class StringInfo(
    var id:String = "",
    var name:String = "",
    var value:String = "",
    var temp:Int = 0
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(value)
        parcel.writeInt(temp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StringInfo> {
        override fun createFromParcel(parcel: Parcel): StringInfo {
            return StringInfo(parcel)
        }

        override fun newArray(size: Int): Array<StringInfo?> {
            return arrayOfNulls(size)
        }
    }

}