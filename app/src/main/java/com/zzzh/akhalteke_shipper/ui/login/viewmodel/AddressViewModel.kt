package com.zzzh.akhalteke_shipper.ui.login.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.zzzh.akhalteke_shipper.realm.AddressBean
import com.zzzh.akhalteke_shipper.realm.AddressRealm
import com.zzzh.akhalteke_shipper.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * 地址三级联动
 */
class AddressViewModel : BaseViewModel() {

    val addressRealm = AddressRealm()//地址三级操作

    //省列表数据
    val proLists: MutableLiveData<MutableList<AddressBean>> by lazy {
        MutableLiveData<MutableList<AddressBean>>()
    }
    //市列表数据
    val cityLists: MutableLiveData<MutableList<AddressBean>> by lazy {
        MutableLiveData<MutableList<AddressBean>>()
    }
    //区列表地址
    val disLists: MutableLiveData<MutableList<AddressBean>> by lazy {
        MutableLiveData<MutableList<AddressBean>>()
    }

    /**
     * 获取数据
     * @param pid Int 父类id
     * @param temp Int 0省，1市，2区县
     */
    fun toGetData(pid: Int, temp: Int) {
        addressRealm.selectAddrList(pid).observeOn(AndroidSchedulers.mainThread()).subscribe({ addressBeans ->
            when (temp) {
                0 -> {
                    proLists.value = addressBeans
                }
                1 -> {
                    cityLists.value = addressBeans
                }
                2 -> {
                    disLists.value = addressBeans
                }
            }
        }, {})
    }


}