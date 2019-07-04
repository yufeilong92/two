package com.zzzh.akhalteke_shipper.utils

import android.content.Context
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.bean.PublishSaveInfo
import com.zzzh.akhalteke_shipper.bean.UserInfo
import org.json.JSONException
import org.json.JSONObject


class PreferencesUtils {

    val preferences = BaseApplication.toInstance().getSharedPreferences("lipo_zzzh_shipper", Context.MODE_PRIVATE)


    /**
     * 注册登录时保存userInfo
     */
    fun shareUserInfo(userInfo: UserInfo?) {
        userInfo?.apply {
            if (ToolUtils.isEmpty(userInfo.name)) {
                userInfo.name = "马道货主"
            }
            if (ToolUtils.isEmpty(userInfo.corporateName)) {
                userInfo.corporateName = ""
            }
            Constant.userInfo = userInfo
            Constant.token = userInfo.token
            val editor = preferences.edit()
            editor.putString("lipo_shipperuser", JSON.toJSONString(userInfo))
            editor.apply()
        }
    }

    /**
     * 更新user信息
     */
    fun updateUserInfo() {

        if (ToolUtils.isEmpty(Constant.userInfo.name)) {
            Constant.userInfo.name = "马道货主"
        }
        if (ToolUtils.isEmpty(Constant.userInfo.corporateName)) {
            Constant.userInfo.corporateName = ""
        }

        val editor = preferences.edit()
        editor.putString("lipo_shipperuser", JSON.toJSONString(Constant.userInfo))
        editor.apply()
    }


    /**
     * 初始化的时候获取userInfo信息
     */
    fun toGetUserInfo() {
        val userString = preferences.getString("lipo_shipperuser", "")
        if (userString == null || userString.isEmpty()) {
            null
        } else {
            Gson().fromJson<UserInfo>(userString, UserInfo::class.java).also {
                Constant.userInfo = it
                Constant.token = Constant.userInfo.token
            }
        }
    }

    /**
     * 保存发布货源数据
     */
    fun toSavePublish(info: PublishSaveInfo) {
        if (info != null && !ToolUtils.isEmpty(info.userId)) {
            val editor = preferences.edit()
            editor.putString("lipo_pbsave", JSON.toJSONString(info))
            editor.apply()
        }
    }

    /**
     * 获取保存的发布货源的数据
     */
    fun toGetSavePublish(): PublishSaveInfo {
        val pbStr = preferences.getString("lipo_pbsave", "")
        return if (pbStr == null || pbStr.isEmpty()) {
            PublishSaveInfo()
        } else {
            val info = Gson().fromJson<PublishSaveInfo>(pbStr, PublishSaveInfo::class.java)
            if(info!=null&& !ToolUtils.isEmpty(info.userId)){
                if(info.userId!=Constant.userInfo.id){
                    val editor = preferences.edit()
                    editor.putString("lipo_pbsave", "")
                    editor.apply()
                    PublishSaveInfo()
                }else{
                    info
                }
            }else{
                PublishSaveInfo()
            }
        }
    }

    /**
     * 清楚保存的发布货源的数据
     */
    fun clearSPublish(){
        val editor = preferences.edit()
        editor.putString("lipo_pbsave", "{}").commit()
    }

    /**
     * 清楚token 和 user 信息
     */
    fun clearUserInfo() {
        Constant.userInfo = UserInfo()
        Constant.token = ""
        val editor = preferences.edit()
        editor.putString("lipo_shipperuser", "{}")
        editor.apply()
    }

    /**
     * 设置提示地址信息已经加入数据库
     */
    fun toShareAddressFristRealm() {
        val editor = preferences.edit()
        editor.putBoolean("isFristAddressTo", false)
        editor.apply()
    }

    /**
     * 判断地址信息是否加入数据库
     */
    fun toGetAddressFristRealm(): Boolean {
        return preferences.getBoolean("isFristAddressTo", true).apply {
            if (this) {
                toShareAddressFristRealm()
            }
        }
    }

    /**
     * 获取是否第一打开app，用于启动页判断
     */
    fun isFristApp(): Boolean {
        return preferences.getBoolean("zzs_isFristApp", true)
    }

    /**
     * 设置不是第一次打开app，不显示启动页
     */
    fun toSetNoFristApp() {
        Constant.isFristApp = false
        val editor = preferences.edit()
        editor.putBoolean("zzs_isFristApp", false)
        editor.apply()
    }
}