package com.zzzh.akhalteke_shipper.mvp.model

import android.content.Context
import com.zzzh.akhalteke.mvp.view.RequestResultInterface
import com.zzzh.akhalteke.mvp.view.StringResultInterface
import com.zzzh.akhalteke_shipper.mvp.contract.WeatherContract
import com.zzzh.akhalteke_shipper.mvp.net.UserNet

/**
 * @Author : YFL  is Creating a porject in akhalteke-Android-driver
 * @Package com.zzzh.akhalteke.mvp.model
 * @Email : yufeilong92@163.com
 * @Time :2019/5/10 11:04
 * @Purpose :天气接口
 */
class WeatherModel : WeatherContract.Model {
    override fun requestWeather(context: Context, fromType: String, lat: String, lng: String, need3: Boolean, needAlarm: Boolean, needHourData: Boolean, needIndex: Boolean, neewMoreDay: Boolean, request: RequestResultInterface) {
        val net = UserNet.getInstance
        net.requestWeather(context, fromType, lat, lng, need3, needAlarm,
                needHourData, needIndex, neewMoreDay, object : StringResultInterface {
            override fun onError(ex: Throwable) {
                request.onError(ex)
            }

            override fun onComplise() {
                request.onComplise()
            }

            override fun <T> Success(t: T) {
                request.Success(t)
            }
        })
    }

}