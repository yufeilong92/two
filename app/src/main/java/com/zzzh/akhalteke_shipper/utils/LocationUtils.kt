package com.zzzh.akhalteke_shipper.utils

import com.baidu.location.BDLocation
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.LocationClientOption
import com.baidu.location.LocationClientOption.LocationMode
import com.zzzh.akhalteke_shipper.BaseApplication


object LocationUtils {

    /**
     * 初始化定位参数配置
     */
    fun initLocationOption(callBack: (latitude: Double, longitude: Double) -> Unit) {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
//        val locationClient=LocationClient(BaseApplication.toInstance())
        //声明LocationClient类实例并配置定位参数
        val option = LocationClientOption()
        //注册监听函数
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.locationMode = LocationMode.Hight_Accuracy
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        option.setCoorType("bd09ll")
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(0)
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(false)
        option.setWifiCacheTimeOut(5 * 60 * 1000)
        //可选，设置是否需要地址描述
        option.setIsNeedLocationDescribe(true)
        //可选，设置是否需要设备方向结果
        option.setNeedDeviceDirect(false)
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.isLocationNotify = false
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(true)
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true)
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true)
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false)
        //可选，默认false，设置是否开启Gps定位
        option.isOpenGps = true
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        option.setIsNeedAltitude(false)
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        option.setOpenAutoNotifyMode()
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        option.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT)
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        BaseApplication.toInstance().mLocationClien!!.locOption = option
        BaseApplication.toInstance().mLocationClien!!.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(location: BDLocation?) {
                //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
                //以下只列举部分获取经纬度相关（常用）的结果信息
                //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

                //获取纬度信息
                val latitude = location!!.latitude
                //获取经度信息
                val longitude = location!!.longitude
                //获取定位精度，默认值为0.0f
                val radius = location!!.radius
                //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
                val coorType = location!!.coorType
                //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
                val errorCode = location!!.locType

                if (latitude != 0.0 && longitude != 0.0) {
                    Constant.latitude = latitude
                    Constant.longitude = longitude

                    callBack(latitude, longitude)
                    BaseApplication.toInstance().mLocationClien!!.stop()
                } else {
                    LocationUtils.initLocationOption(callBack)
                }
            }
        })
        BaseApplication.toInstance().mLocationClien!!.start()
    }
}
