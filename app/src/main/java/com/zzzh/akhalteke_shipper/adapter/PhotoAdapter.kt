package com.zzzh.akhalteke_shipper.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.ImageInfo
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils

abstract class PhotoAdapter(val mContext: Context, private val inforList: MutableList<ImageInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val inflater = LayoutInflater.from(mContext)!!

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return when (p1) {
            ImageInfo.IMAGE_TYPE -> {
                ImageViewHolder(inflater.inflate(R.layout.item_image, null))
            }
            ImageInfo.ADD_TYPE -> {
                AddViewHolder(inflater.inflate(R.layout.item_image_add, null))
            }
            else -> {
                ImageViewHolder(inflater.inflate(R.layout.item_image, null))
            }
        }
    }

    override fun getItemCount(): Int {
        return inforList.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when (p0) {
            is ImageViewHolder -> {
                val holder01 = p0 as ImageViewHolder
                holder01.deleteView.setOnClickListener { deleteImage(p1) }
                holder01.imageView.setOnClickListener { showImage(p1) }
                holder01.showImageView(inforList[p1].imagePath)
            }
            is AddViewHolder -> {
                val holder02 = p0 as AddViewHolder
                holder02.addView.setOnClickListener { addImage() }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == (inforList.size - 1)) {
            return ImageInfo.ADD_TYPE
        } else {
            return ImageInfo.IMAGE_TYPE
        }
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<SimpleDraweeView>(R.id.item_image_simaple)
        val deleteView = itemView.findViewById<ImageView>(R.id.item_image_delete)
        init {
        }

        fun showImageView(url:String){
            if(url.startsWith("http")){
                ImageLoadingUtils.loadNetImage(imageView,url)
            }else{
                ImageLoadingUtils.loadLocalImage(imageView,url)
            }
        }
    }

    class AddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addView = itemView.findViewById<View>(R.id.iiadd_icon)
        init {

        }
    }

    abstract fun addImage()
    abstract fun deleteImage(position: Int)
    abstract fun showImage(position: Int)


}