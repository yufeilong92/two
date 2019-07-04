package com.zzzh.akhalteke_shipper

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDexApplication
import cn.jpush.android.api.JPushInterface
import com.baidu.location.LocationClient
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.facebook.drawee.backends.pipeline.Fresco
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.bugly.crashreport.CrashReport
import com.zhy.http.okhttp.OkHttpUtils
import com.zzzh.akhalteke.weather.Observer.WeatherManger
import com.zzzh.akhalteke.weather.Observer.WeatherObserver
import com.zzzh.akhalteke_shipper.utils.PreferencesUtils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import io.realm.Realm
import io.realm.RealmConfiguration

class BaseApplication : MultiDexApplication() {

    private var count = 0
    private var isStop = false
    private var isExitOrHome = false
    private val weatherManger = WeatherManger()
     var mLocationClien:LocationClient?=null
    override fun onCreate() {
        super.onCreate()
        instance = this

        if (applicationInfo.packageName == getCurProcessName(this)) {
            Fresco.initialize(this)
            initData()
            initRealm()
            activityLifecycle()
            initBaidu()
            //腾讯bugly
            CrashReport.initCrashReport(applicationContext, "bef529d05d", false)

            initJPush()
        }

    }
    fun initBaidu(){
        mLocationClien = LocationClient(this)
        SDKInitializer.initialize(this)
        SDKInitializer.setCoordType(CoordType.BD09LL)
    }


    /**
     * 初始化realm 数据库
     */
    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("myrealm.realm").build()
        Realm.setDefaultConfiguration(config)
    }

    private fun activityLifecycle() {
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {
                isExitOrHome = false
                if (count === 0 && isStop) {
                    ifAppFront = true
                    isStop = false
                }
                count++
            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                count--
                if (count === 0) {
                    ifAppFront = false
                    isStop = true
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }

    /**
     * 获取进程名
     * @param context Context
     * @return String?
     */
    private fun getCurProcessName(context: Context): String? {
        val pid = android.os.Process.myPid()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in activityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return ""
    }

    /**
     * 初始化user信息，屏幕宽高
     */
    private fun initData() {
        PreferencesUtils().toGetUserInfo()
        ToolUtils.obtainScreenWH(this)

        OkHttpUtils.getInstance()
            .init(this)
            .debug(true, "okHttp")
            .timeout((20 * 1000).toLong())
    }

    companion object {
        //设置application 单例模式
        private lateinit var instance: BaseApplication

        var ifAppFront = false

        fun toInstance(): BaseApplication {
            return instance
        }

        //初始化下拉刷新参数
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.main_bg, R.color.main_text9)
                return@setDefaultRefreshHeaderCreator ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate)
            }

            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                return@setDefaultRefreshFooterCreator ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate)
            }
        }
    }


    /**
     * 添加天气观察者并通知被观察
     */
    fun addAndRefresh(o: WeatherObserver?) {
        weatherManger.registerOberver(o)
        weatherManger.notifyObserver()
    }

    fun initJPush(){
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
    }

    //删除订阅者
    fun removerAllObserver() {
        weatherManger.removerAll()
    }
    /**
     * 添加天气观察者
     */
    fun addObserver(o: WeatherObserver?) {
        weatherManger.registerOberver(o)
    }

    /**
     * 通知观察者
     */
    fun WeatherNotifyObjserver() {
        weatherManger.notifyObserver()
    }
}