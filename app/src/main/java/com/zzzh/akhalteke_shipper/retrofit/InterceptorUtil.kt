package com.zzzh.akhalteke_shipper.retrofit

import android.text.TextUtils
import android.util.Base64
import com.lipo.utils.MyMD5
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.logging.HttpLoggingInterceptor
import java.net.URLDecoder
import java.net.URLEncoder
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


object InterceptorUtil {

    val APPKEYV = "9d5f6afa47254f149809256ee94aa98b"

    /**
     * 日志拦截打印
     */
    fun LogInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            ToolUtils.log(message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /**
     * 头部拦截处理
     */
    fun HeaderInterceptor(): Interceptor {
        return Interceptor { chain ->

            val original = chain.request()
            val requestBuilder = original.newBuilder()

            when (original.method()) {
                "GET" -> requestBuilder.url(toGetHttpUrl(original.url()))
                "PUT" -> requestBuilder.put(toPOSTHttpUrl((original.body() as FormBody)))
                "POST" ->
                    if (original.body() is FormBody) {
                        requestBuilder.post(toPOSTHttpUrl((original.body() as FormBody)))
                    }
                "DELETE" -> requestBuilder.url(toGetHttpUrl(original.url()))
            }

            requestBuilder.addHeader("Charset", "UTF-8")
            requestBuilder.addHeader("Content-Type", "application/json")
            requestBuilder.addHeader("Accept-Encoding", "gzip,deflate")
            requestBuilder.addHeader("Cache-Control", "no-store")
            requestBuilder.addHeader("Pragma", "no-cache")

            return@Interceptor chain.proceed(requestBuilder.build())
        }
    }

    private fun toGetHttpUrl(originalHttpUrl: HttpUrl): HttpUrl {
        val urlKey = originalHttpUrl.newBuilder()
            .build()

        return urlKey.newBuilder()
            .addQueryParameter("sign", handParams(urlKey.url().toString()))
            .build()
    }

    private fun toPOSTHttpUrl(body: FormBody): FormBody {
        var bodyString = ""
        val fBuilder = FormBody.Builder()
        val currentTime = System.currentTimeMillis() / 1000
        if (body.size() > 0) {
            for (i in 0 until body.size()) {
                if (i != 0) {
                    bodyString += "&"
                }
                val ename = body.encodedName(i)

                var bValue = body.value(i)
                if (ename == "name") {
                    bValue = bValue.replace(" ", "+")
                }
                fBuilder.add(ename, bValue)
                bodyString += "$ename=$bValue"
            }
        }
        if (!ToolUtils.isEmpty(Constant.token)) {
            fBuilder.add("sign", handParams(bodyString))
        }
        return fBuilder.build()
    }

    private fun handParams(url: String): String {
        var str = ""
        val urlParams = url.split("?")
        if (urlParams.size > 1) {
            str = urlParams[1]
        } else {
            str = url
        }

        str = toSortData(str)

        if (!ToolUtils.isEmpty(Constant.token)) {
            str += "&token=" + Constant.token
        }

        return MyMD5.getMD5String(str).toUpperCase()
    }

    fun hmacSha1(base: String, key: String): String {
        try {
            if (TextUtils.isEmpty(base) || TextUtils.isEmpty(key)) {
                return ""
            }
            val type = "HmacSHA1"
            val secret = SecretKeySpec(key.toByteArray(), type)
            var mac: Mac? = null
            mac = Mac.getInstance(type)
            mac!!.init(secret)
            val digest = mac.doFinal(base.toByteArray())
            return Base64.encodeToString(digest, Base64.DEFAULT).trim()
        } catch (e: NoSuchAlgorithmException) {
            return ""
        } catch (e: InvalidKeyException) {
            return ""
        }
    }

    private fun toSortData(strUrl: String): String {
        val sortMap: MutableMap<String, String> = mutableMapOf()

        val allPair = strUrl.split("&")

        for (pair in allPair) {
//            if (pair.contains("name=")) {
//                sortMap["name"] = pair.replace("name=", "")
//            } else {
            val nameValues = pair.split(Regex("="), 2)
//                if (nameValues.size == 3) {
//                    if (nameValues[2] == "\n") {
//                        sortMap[nameValues[0]] = (nameValues[1] + "=")
//                    }
//                }else if(nameValues.size == 4){
//                    if(nameValues[2] == "\n"&&nameValues[3] == "\n"){
//
//                    }
//                } else
            if (nameValues.size == 2) {
                sortMap[nameValues[0]] = nameValues[1]
            } else {
                continue
            }
//            }
        }

        var sortString = ""
        for (str in sortMap.toSortedMap().keys){
            var valueName = sortMap[str]

            sortString += "&$str=$valueName"
        }
        if(ToolUtils.isEmpty(sortString)){
            return ""
        }else{
            return sortString.substring(1)
        }
    }

}