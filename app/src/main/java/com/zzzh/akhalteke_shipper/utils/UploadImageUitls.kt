package com.zzzh.akhalteke_shipper.utils

import android.content.Context
import com.lipo.utils.MyMD5
import com.zzzh.akhalteke_shipper.BaseApplication
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import top.zibin.luban.Luban
import java.io.File

object UploadImageUitls {

    fun lubanPicture(
        fileParams: MutableMap<String, String>,
        params: MutableMap<String, String>
    ): Observable<MutableList<MultipartBody.Part>> {
        val imagePaths = ArrayList<String>()
        for (key in fileParams.keys){
            imagePaths.add(fileParams[key]?:"")
        }
        return Flowable.just(imagePaths)
            .observeOn(Schedulers.io())
            .map { list ->

                Luban.with(BaseApplication.toInstance())
                    .ignoreBy(100)
                    .setTargetDir(ToolUtils.pathCache().toString())
                    .load(list).get()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .flatMap {
                val fileMap = mutableMapOf<String,File>()
                var temp = 0
                for (key in fileParams.keys){
                    if(it.size>temp){
                        fileMap.put(key,it[temp])
                    }
                    temp++
                }
                return@flatMap uploadImage(fileMap, params)
            }
    }

    fun uploadImage(files: MutableMap<String, File>, params: MutableMap<String, String>): Observable<MutableList<MultipartBody.Part>> {
        return Single.fromCallable {
            val builder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
            val sortString = StringBuffer()
            for (str in params.toSortedMap().keys) {
                var valueName = params[str]
                sortString.append("&$str=$valueName")
                builder.addFormDataPart(str, valueName)
            }
            if (!ToolUtils.isEmpty(Constant.token)) {
                sortString.append("&token=${Constant.token}")
            }
            builder.addFormDataPart("sign", MyMD5.getMD5String(sortString.substring(1).toString()).toUpperCase())
            for (fileKey in files.keys){
                builder.addFormDataPart(fileKey, files[fileKey]?.name?:"", RequestBody.create(MediaType.parse("image/png"), files[fileKey]))//imgfile 后台接收图片流的参数名
            }


            return@fromCallable builder.build().parts()
        }.toObservable()
    }

}