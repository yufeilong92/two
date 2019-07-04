package com.lipo.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo.State

class JudgeNetwork(context: Context) {

    private var k_isWifi: Boolean = false
    private var k_isMobile: Boolean = false// 是否为WiFi，是否为移动
    private val connectivityManager_KBaseActivity: ConnectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var kState: State? = null

    val isInternet: Boolean
        get() {
            isWifi
            is3G
            return !(!k_isWifi && !k_isMobile)

        }

    private val isWifi: Boolean
        get() {
            kState = connectivityManager_KBaseActivity.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI).state
            k_isWifi = kState == State.CONNECTED
            return k_isWifi
        }

    private val is3G: Boolean
        get() {
            kState = connectivityManager_KBaseActivity.getNetworkInfo(
                    ConnectivityManager.TYPE_MOBILE).state
            k_isMobile = kState == State.CONNECTED
            return k_isMobile
        }

    /**
     * 打开网络设置
     * @param context
     */
    fun openNetset(context: Context) {
        //判断手机系统的版  即API大于10 就是3.0或以上版
        var intent: Intent? = null
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
        } else {
            intent = Intent()
            val component = ComponentName("com.android.settings", "com.android.settings.WirelessSettings")
            intent.component = component
            intent.action = "android.intent.action.VIEW"
        }
        context.startActivity(intent)

    }
}
