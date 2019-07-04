package com.zzzh.akhalteke_shipper.mvp.net

import android.content.Context
import com.zzzh.akhalteke.mvp.view.StringResultInterface
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class UserNet {
    //静态内部类单例模式
    companion object {
        var getInstance = UserNet.instance
    }

    private object UserNet {
        var instance = UserNet()
    }

    /**
     * 请求个人信息
     * @param fromType    坐标类型
     * @param lat 纬度
     * @param lng  经度
     * @param need3  是否需要当天每3或6小时一次的天气预报列表
     * @param  needAlarm 是否需要天气预警
     * @param  needHourData  是否需要每小时数据的累积数组
     * @param  needIndex  是否需要返回指数数据
     * @param  neewMoreDay  是否需要返回7天数据中的后4天
     * @param  context
     */
    fun requestWeather(
            context: Context, fromType: String, lat: String, lng: String,
            need3: Boolean, needAlarm: Boolean, needHourData: Boolean
            , needIndex: Boolean, neewMoreDay: Boolean,
            m: StringResultInterface
    ) {
        if (RetrofitFactory.judgmentNetWork(context)) {
            RetrofitFactory.createMainRetrofit().requestWeather(
                    formType = fromType,
                    lat = lat,
                    lng = lng,
                    need3HourForcast = if (need3) "1" else "0",
                    needAlarm = if (needAlarm) "1" else "0",
                    needHourData = if (needHourData) "1" else "0",
                    needIndex = if (needIndex) "1" else "0",
                    needMoreDay = if (neewMoreDay) "1" else "0"
            )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer {
                        m.Success(it.data)
                    }, Consumer {
                        m.onError(it)
                    }, Action {
                        m.onComplise()
                    })
        } else {
            m.onComplise()
        }
    }

    /**
     * 请求司机位置
     */
    fun reqeustDriverPostion(context: Context, driverId: String, m: StringResultInterface) {
        if (RetrofitFactory.judgmentNetWork(context)) {
            RetrofitFactory.createMainRetrofit().requestDriverPostion(
                    driverid = driverId
            )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer {
                        m.Success(it.data)
                    }, Consumer {
                        m.onError(it)
                    }, Action {
                        m.onComplise()
                    })
        }else{
            m.onComplise()
        }
    }

}