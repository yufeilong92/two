package com.zzzh.akhalteke_shipper.utils

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import java.io.File

/**
 * @Author : YFL  is Creating a porject in akhalteke-Android-driver
 * @Package com.zzzh.akhalteke.utils
 * @Email : yufeilong92@163.com
 * @Time :2019/5/15 11:42
 * @Purpose :图片加载
 */
object GlideUtil {
    /**
     * 加载图片本地
     */
    fun LoadlocalImager(context: Context, img: ImageView, path: String) {
        if (TextUtils.isEmpty(path)){
            img.setImageResource(R.mipmap.icon_place)
            return
        }
        val file= File(path)
        Glide.with(context)
                .load(file)
                .into(img)
    }
    /**
     * 加载图片
     */
    fun LoadImager(context: Context, img: ImageView, path: String) {
        if (TextUtils.isEmpty(path)){
            img.setImageResource(R.mipmap.icon_place)
            return
        }
        Glide.with(context)
                .load(path)
                .into(img)
    }
    /**
     * 加载图片
     */
    fun LoadImagerWithOutHear(context: Context, img: ImageView, path: String) {
        if (TextUtils.isEmpty(path)){
            img.setImageResource(R.mipmap.icon_place)
            return
        }
        val imagerpath= RetrofitFactory.BASE_URL+path
        Glide.with(context)
                .load(imagerpath)
                .into(img)
    }

    /**
     * 加载四个圆角
     */
    fun loadQuadRangleImager(context: Context, img: ImageView, path: String) {
        if (TextUtils.isEmpty(path)){
            img.setImageResource(R.mipmap.icon_place)
            return
        }
        val roundedCorners = RoundedCorners(10);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        val options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context)
                .load(path)
                .apply(options)
                .into(img)
    }
    /**
     * 加载四个圆角
     */
    fun loadQuadRangleImagerWithHttp(context: Context, img: ImageView, path: String) {
        if (TextUtils.isEmpty(path)){
            img.setImageResource(R.mipmap.icon_place)
            return
        }
        val imagerpath= RetrofitFactory.BASE_URL+path
        val roundedCorners = RoundedCorners(10);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        val options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context)
                .load(imagerpath)
                .apply(options)
                .into(img)
    }

    /**
     * 加载圆角
     */
    fun loadCilcleImager(context: Context, img: ImageView, path: String) {
        if (TextUtils.isEmpty(path)){
            img.setImageResource(R.mipmap.icon_place)
            return
        }
        val mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        Glide.with(context)
                .load(path)
                .apply(mRequestOptions)
                .into(img)
    }
}
