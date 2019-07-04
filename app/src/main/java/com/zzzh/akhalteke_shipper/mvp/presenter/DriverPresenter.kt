package com.zzzh.akhalteke_shipper.mvp.presenter

import android.content.Context
import com.zzzh.akhalteke.mvp.view.RequestResultInterface
import com.zzzh.akhalteke_shipper.mvp.contract.DriverPostionContract

/**
 * @Author : YFL  is Creating a porject in akhalteke-Android-shipper
 * @Package com.zzzh.akhalteke_shipper.mvp.presenter
 * @Email : yufeilong92@163.com
 * @Time :2019/6/25 16:43
 * @Purpose :
 */
class DriverPresenter:DriverPostionContract.Presenter {
    var view: DriverPostionContract.View? = null
    var model: DriverPostionContract.Model? = null
    override fun initMvp(view: DriverPostionContract.View, model: DriverPostionContract.Model) {
        this.view = view
        this.model = model
    }

    override fun requestDriverPostion(context: Context, driverId: String) {
        model!!.requestDriverPostion(context,driverId,object :RequestResultInterface{
            override fun onError(ex: Throwable) {
                view!!.Error(ex)
            }

            override fun onComplise() {
                view!!.Complise()
            }

            override fun <T> Success(t: T) {
                view!!.Success(t)
            }


        })
    }
}