package com.zzzh.akhalteke_shipper.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

import java.util.Properties

object StatusBarUtil {

    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var navigationHeight = 0

    private var mMetrics: DisplayMetrics? = null
    val HOME_CURRENT_TAB_POSITION = "HOME_CURRENT_TAB_POSITION"

    private val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"
    private val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private val KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage"

    /**
     * 判断手机是否是小米
     *
     * @return
     */
    val isMIUI: Boolean
        get() {
            val prop = Properties()
            return (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null)
        }

    /**
     * 判断手机是否是魅族
     *
     * @return
     */
    // Invoke Build.hasSmartBar()
    val isFlyme: Boolean
        get() {
            try {
                val method = Build::class.java.getMethod("hasSmartBar")
                return method != null
            } catch (e: Exception) {
                return false
            }

        }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val obj = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = Integer.parseInt(field.get(obj).toString())
            return context.resources.getDimensionPixelSize(x)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * 获取底部导航栏高度
     *
     * @return
     */
    fun getNavigationBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        //获取NavigationBar的高度
        navigationHeight = resources.getDimensionPixelSize(resourceId)
        return navigationHeight
    }

    //获取是否存在NavigationBar
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        val rs = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {

        }

        return hasNavigationBar

    }


    /**
     * @param activity
     * @param useThemestatusBarColor   是否要状态栏的颜色，不设置则为透明色
     * @param withoutUseStatusBarColor 是否不需要使用状态栏为暗色调
     */
    fun setStatusBar(activity: Activity, useThemestatusBarColor: Boolean, withoutUseStatusBarColor: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            val decorView = activity.window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            if (useThemestatusBarColor) {
                activity.window.statusBarColor = Color.BLACK
            } else {
                activity.window.statusBarColor = Color.TRANSPARENT
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            val localLayoutParams = activity.window.attributes
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !withoutUseStatusBarColor) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun reMeasure(activity: Activity) {
        val display = activity.windowManager.defaultDisplay
        mMetrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(mMetrics)
        } else {
            display.getMetrics(mMetrics)
        }

        screenWidth = mMetrics!!.widthPixels
        screenHeight = mMetrics!!.heightPixels
    }

    /**
     * 改变魅族的状态栏字体为黑色，要求FlyMe4以上
     */
    private fun processFlyMe(isLightStatusBar: Boolean, activity: Activity) {
        val lp = activity.window.attributes
        try {
            val instance = Class.forName("android.view.WindowManager\$LayoutParams")
            val value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp)
            val field = instance.getDeclaredField("meizuFlags")
            field.isAccessible = true
            val origin = field.getInt(lp)
            if (isLightStatusBar) {
                field.set(lp, origin or value)
            } else {
                field.set(lp, value.inv() and origin)
            }
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

    }

    /**
     * 改变小米的状态栏字体颜色为黑色, 要求MIUI6以上  lightStatusBar为真时表示黑色字体
     */
    private fun processMIUI(lightStatusBar: Boolean, activity: Activity) {
        val clazz = activity.window.javaClass
        try {
            val darkModeFlag: Int
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            extraFlagField.invoke(activity.window, if (lightStatusBar) darkModeFlag else 0, darkModeFlag)
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

    }

    /**
     * 设置bar字体颜色是黑色
     *
     * @param activity
     */
    fun toStatusBarTextDark(activity: Activity) {
        setStatusTextColor(true, activity)
    }

    /**
     * 设置bar字体颜色是白色
     *
     * @param activity
     */
    fun toStatusBarTextLight(activity: Activity) {
        setStatusTextColor(false, activity)
    }

    /**
     * 设置状态栏文字色值为深色调
     *
     * @param useDart  是否使用深色调
     * @param activity
     */
    fun setStatusTextColor(useDart: Boolean, activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (useDart) {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
            activity.window.decorView.findViewById<View>(android.R.id.content).setPadding(0, 0, 0, navigationHeight)
        } else {
            if (isFlyme) {
                processFlyMe(useDart, activity)
            } else if (isMIUI) {
                processMIUI(useDart, activity)
            }
        }
    }

}
