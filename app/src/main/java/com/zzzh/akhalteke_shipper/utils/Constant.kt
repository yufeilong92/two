package com.zzzh.akhalteke_shipper.utils

import com.zzzh.akhalteke_shipper.bean.UserInfo


/**
 * Created by apple on 2018/7/16.
 */
object Constant {

    const val PAGE_SIZE = 12

    var statusHeight: Int = 0    //bar高度
    var screenWidth: Int = 0     //屏幕宽
    var screenHeight: Int = 0    //屏幕高

    var isFristApp: Boolean = true   //是否第一次打开app
    var token: String = ""//token 解析//token
    var userInfo: UserInfo = UserInfo()
    var isFristBehave = true//是不是第一次打开首页活动
    var ifToLogin = false

    var latitude :Double= 0.0
    var longitude:Double = 0.0

    val loadTypes: MutableMap<String, String> = mutableMapOf(
        Pair("1","一装一卸"),
        Pair("2","一装两卸"),
        Pair("3","一装多卸"),
        Pair("4","两装一卸"),
        Pair("5","两装两卸"),
        Pair("6","多装多卸")
    )

    val timeTypes: MutableMap<String, String> = mutableMapOf(
        Pair("1","今天"),
        Pair("2","明天"),
        Pair("3","后天")
    )

    val goodsTypes: MutableMap<String, String> = mutableMapOf(
        Pair("1","整车"),
        Pair("2","零担")
    )

}