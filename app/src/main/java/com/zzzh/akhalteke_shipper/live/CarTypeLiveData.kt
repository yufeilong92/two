package com.zzzh.akhalteke_shipper.live

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class CarTypeLiveData : LiveData<CarTypeBean>() {

//    private val stockManager: StockManager = StockManager(symbol)

    companion object {
        private val ctLivedata: CarTypeLiveData by lazy {
            CarTypeLiveData()
        }

        @Synchronized
        fun getIstance(): CarTypeLiveData {
            return ctLivedata
        }

    }

    public override fun setValue(value: CarTypeBean?) {
        super.setValue(value)
    }

}

class CarTypeBean(
    var name:String = ""
)