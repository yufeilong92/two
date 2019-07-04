package com.zzzh.akhalteke_shipper.bean

import android.os.Parcel
import android.os.Parcelable
import com.zzzh.akhalteke_shipper.R

class BankCardInfo(
    var id: String = "",
    var cardNumber: String = "",
    var bank: String = "",
    var name: String = "",
    var idCardNumber: String = "",
    var phone: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(cardNumber)
        parcel.writeString(bank)
        parcel.writeString(name)
        parcel.writeString(idCardNumber)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BankCardInfo> {
        override fun createFromParcel(parcel: Parcel): BankCardInfo {
            return BankCardInfo(parcel)
        }

        override fun newArray(size: Int): Array<BankCardInfo?> {
            return arrayOfNulls(size)
        }


        val BANK_CODE = mutableMapOf(
            "0105" to "建设银行",
            "0104" to "中国银行",
            "0103" to "农业银行",
            "0102" to "工商银行",
            "0100" to "邮政银行",
            "0301" to "交通银行",
            "0302" to "中信银行",
            "0303" to "光大银行",
            "0304" to "华夏银行",
            "0305" to "民生银行",
            "0306" to "广发银行",
            "0307" to "平安银行",
            "0308" to "招商银行",
            "0309" to "兴业银行"
        )

        val BANK_BG = mutableMapOf(
            "0105" to R.mipmap.jiansheyinhang,
            "0104" to R.mipmap.zhongguoyinhang,
            "0103" to R.mipmap.nongyeyinhang,
            "0102" to R.mipmap.gongshangyinhang,
            "0100" to R.mipmap.youzhengyinhan,
            "0301" to R.mipmap.jiaotongyinhang,
            "0302" to R.mipmap.zhongxinyinhang,
            "0303" to R.mipmap.guangdayinhang,
            "0304" to R.mipmap.huaxiayinhang,
            "0305" to R.mipmap.minshengyinhang,
            "0306" to R.mipmap.guangfayinhang,
            "0307" to R.mipmap.pinganyinhang,
            "0308" to R.mipmap.zhaoshangyinhang,
            "0309" to R.mipmap.xingyeyinhang
        )

        val BANK_ICON = mutableMapOf(
            "0105" to R.mipmap.jianshetubiao,
            "0104" to R.mipmap.zhongguotubiao,
            "0103" to R.mipmap.nongyeyinhangtuboao,
            "0102" to R.mipmap.gongshangtubiao,
            "0100" to R.mipmap.youzhengtubiao,
            "0301" to R.mipmap.jiaotongyinhangtubio,
            "0302" to R.mipmap.zhongxinyinhangtubio,
            "0303" to R.mipmap.guangdatubiao,
            "0304" to R.mipmap.huaxiayinhangtubio,
            "0305" to R.mipmap.mingshengyinhangtubiao,
            "0306" to R.mipmap.guangfayinhangtubio,
            "0307" to R.mipmap.pinganyinhangtubiao,
            "0308" to R.mipmap.zhaoshangtubiao,
            "0309" to R.mipmap.xingyetubiao
        )
    }
}