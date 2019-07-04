package com.zzzh.akhalteke_shipper.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class AddressBean(
        @PrimaryKey open var id: Int = 0,
        open var area_name: String = "",
        open var pid: Int = 0,
        open var position:Int = 0
) : RealmObject()