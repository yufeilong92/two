package com.zzzh.akhalteke_shipper.push

import cn.jpush.android.api.JPushInterface
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.ToolUtils

/**
 * @Author : ZFB  is Creating a porject in akhalteke-Android-shipper
 * @Package com.zzzh.akhalteke_shipper.push
 * @Email : 879736112@qq.com
 * @Time :2019/6/4 10:49
 * @Purpose :推送的帮助类
 */
object PushHelper{

    /**
     * 极光推送服务会恢复正常工作
     */
     fun startPush() {
        JPushInterface.resumePush(BaseApplication.toInstance())
    }

    /**
     * 极光推送服务会被停止掉
     */
     fun stopPush() {
        JPushInterface.stopPush(BaseApplication.toInstance())
    }

     fun setAlias() {
        if (Constant.userInfo != null && !ToolUtils.isEmpty(Constant.userInfo.id)) {
            JPushInterface.setAlias(BaseApplication.toInstance(), 10832, Constant.userInfo.id)
        }
    }

     fun deleteAlias(){
        if (Constant.userInfo != null && !ToolUtils.isEmpty(Constant.userInfo.id)) {
            JPushInterface.deleteAlias(BaseApplication.toInstance(), 10832)
        }
    }

    fun toStartPush(){
        startPush()
        setAlias()
    }

    fun toStopPush(){
        stopPush()
        deleteAlias()
    }

}