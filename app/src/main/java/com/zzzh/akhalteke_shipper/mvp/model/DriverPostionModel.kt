package com.zzzh.akhalteke_shipper.mvp.model

import android.content.Context
import com.zzzh.akhalteke.mvp.view.RequestResultInterface
import com.zzzh.akhalteke.mvp.view.StringResultInterface
import com.zzzh.akhalteke_shipper.mvp.contract.DriverPostionContract
import com.zzzh.akhalteke_shipper.mvp.net.UserNet

/**
 * @Author : YFL  is Creating a porject in akhalteke-Android-shipper
 * @Package com.zzzh.akhalteke_shipper.mvp.model
 * @Email : yufeilong92@163.com
 * @Time :2019/6/25 16:33
 * @Purpose :司机位置
 */
class DriverPostionModel : DriverPostionContract.Model {
    override fun requestDriverPostion(context: Context, driverId: String, request: RequestResultInterface) {
        val net = UserNet.getInstance
        net.reqeustDriverPostion(context, driverId, object : StringResultInterface {
            override fun <T> Success(t: T) {
                request.Success(t)
            }

            override fun onError(ex: Throwable) {
                request.onError(ex)
            }

            override fun onComplise() {
                request.onComplise()
            }


        })

    }
}