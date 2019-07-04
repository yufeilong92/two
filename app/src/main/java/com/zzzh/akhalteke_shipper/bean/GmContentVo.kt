package com.zzzh.akhalteke_shipper.bean

/**
 * @Author : YFL  is Creating a porject in akhalteke-Android-shipper
 * @Package com.zzzh.akhalteke_shipper.bean
 * @Email : yufeilong92@163.com
 * @Time :2019/5/30 16:53
 * @Purpose :通用累
 */
object GmContentVo {

    var mWeatherVo: WeatherVo? = null

    fun setWeatherVo(vo: WeatherVo) {
        this.mWeatherVo = vo
    }

    fun getWeatherVo(): WeatherVo? {
        if (mWeatherVo == null)
            return null
        return mWeatherVo
    }
}