package com.zzzh.akhalteke_shipper.utils

import android.app.Activity
import android.content.Context
import android.os.Environment
import com.lipo.utils.ShareUtils
import com.yanzhenjie.permission.Permission
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

object ZzzhUtils {

    fun setWeightAndVolume(weight: String?, volume: String?): String {
        var wandv = ""
        if (!ToolUtils.isEmpty(weight)) {
            wandv = "${weight}吨"
        }
        if (!ToolUtils.isEmpty(volume)) {
            wandv += "${volume}方"
        }
        return wandv
    }

    fun shareDao(mContext: Context) {
        PermissionUtils.showPermission(mContext as Activity, "下载文件需要读写权限", arrayOf(Permission.WRITE_EXTERNAL_STORAGE)) {
            Single.fromCallable {
                val temp = ToolUtils.copyAssetAndWrite(mContext, "法人授权委托书.pdf")
                return@fromCallable temp
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it) {
                        ShareUtils.shareFile(
                            mContext,
                            File(ToolUtils.pathCache(), "法人授权委托书.pdf")
                        )
                    }
                }, {})
        }
    }

    /**
     * 地址名字列表中只显示简写
     */
    fun adrItemShow(area: String, adr: String): String{
        if (ToolUtils.isEmpty(area)) {
            return adr
        } else {
            return area
        }
    }

    /**
     * 地址名字重载
     */
    fun adrNameLoad(area: String, adr: String): String {
        if (area == adr) {
            return adr
        } else {
            return "$area $adr"
        }
    }

    /**
     * 车型车长的重写
     */
    fun carDataLoad(length: String, type: String): String {
        var carStr = ""
        if (!ToolUtils.isEmpty(length)) {
            carStr += "$length "
        }
        if (!ToolUtils.isEmpty(type)) {
            carStr += "$type"
        }
        return carStr
    }

    /**
     * 车辆三项相加
     */
    fun loadCar(legth: String, type: String, wv: String): String {
        val carStr = ZzzhUtils.carDataLoad(legth, type)
        return if (!ToolUtils.isEmpty(carStr)) {
            "$carStr $wv"
        } else {
            wv
        }
    }

}