package com.zzzh.akhalteke_shipper.retrofit

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lipo.utils.NetWork
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.retrofit.gsonFactory.GsonDConverterFactory
import com.zzzh.akhalteke_shipper.utils.StringNullAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by apple on 2018/7/8.
 */
object RetrofitFactory {

    //    val BASE_URL: String = "http://zzzh.natapp1.cc/"
//    val BASE_URL: String = "http://192.168.100.12:8080/" //本地
//    val BASE_URL: String = "http://2y50t76103.zicp.vip:41782/" //本地
//    val BASE_URL: String = "http://39.105.197.48:8080/" //测试
    val BASE_URL: String = "http://www.zzzh56.com:8082/"   //外网
//    val BASE_URL: String = "http://39.106.71.119:8080/"   //外网（IP地址）


    private val TIMEOUT: Long = 10
    private var mainRetrofit: MainRetrofit? = null
    private var mineRetrofit: MineRetrofit? = null

    /**
     * 设置http连接参数，超时时间，log日志，header拦截
     */
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(InterceptorUtil.LogInterceptor())
        .addInterceptor(InterceptorUtil.HeaderInterceptor())
        .build()

    /**
     * 解析json格式
     */
    private fun buildGson(): Gson {
        return GsonBuilder().serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .registerTypeAdapter(String::class.java, StringNullAdapter())
            .create()
    }

    /**
     * 初始化retrofit
     */
    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonDConverterFactory(buildGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    /**
     * 创建retrofit
     */
    fun createMainRetrofit(): MainRetrofit {
        if (mainRetrofit == null) {
            mainRetrofit = createRetrofit().create(MainRetrofit::class.java)
        }
        return mainRetrofit!!
    }

    /**
     * 判断当前网络是否可用
     */
    fun judgmentNetWork(context: Context): Boolean {
        if (NetWork.isNetworkConnected(context)) {
            return true
        }
        ToastView.setToasd(context, "当前网络不可用")
        return false
    }

}