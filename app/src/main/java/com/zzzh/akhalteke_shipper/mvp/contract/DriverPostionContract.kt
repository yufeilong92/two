package com.zzzh.akhalteke_shipper.mvp.contract

import android.content.Context
import com.zzzh.akhalteke.mvp.view.RequestResultInterface

/**
 * @Author : YFL  is Creating a porject in akhalteke-Android-shipper
 * @Package com.zzzh.akhalteke_shipper.mvp.contract
 * @Email : yufeilong92@163.com
 * @Time :2019/6/25 16:30
 * @Purpose :获取司机位置
 */
interface DriverPostionContract {
    interface View {
        fun Success(t: Any?)
        fun Error(ex: Throwable)
        fun Complise()
    }

    interface Model {
        fun requestDriverPostion(context: Context,driverId:String, request: RequestResultInterface)
    }

    interface Presenter {
        fun initMvp(view: View, model: Model)
        fun requestDriverPostion(context: Context,driverId:String)
    }
}