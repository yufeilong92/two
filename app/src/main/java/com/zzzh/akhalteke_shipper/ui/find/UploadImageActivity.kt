package com.zzzh.akhalteke_shipper.ui.find

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.PhotoAdapter
import com.zzzh.akhalteke_shipper.bean.ImageInfo
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.ui.SelectorImageActivity
import kotlinx.android.synthetic.main.activity_upload_image.*

/**
 * 上传回单，暂时废弃
 */
class UploadImageActivity : SelectorImageActivity() {

    private var mAdapter: PhotoAdapter? = null
    private var infoList: MutableList<ImageInfo> = mutableListOf()
    private var imageList: ArrayList<StringInfo> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)
        initView()
    }

    private fun initView() {
        upload_image_recycle.layoutManager = GridLayoutManager(mContext, 3)
        infoList.add(ImageInfo(dataType = ImageInfo.ADD_TYPE))
        initAdapter()
    }

    override fun onFileData(imagePath: String, imageStr: String) {
        super.onFileData(imagePath, imageStr)
        infoList.add(
            (infoList.size - 1),
            ImageInfo(imagePath = imagePath, imageStr = imageStr, dataType = ImageInfo.IMAGE_TYPE)
        )
        initAdapter()
        imageList.add(StringInfo(name = imagePath))
    }

    private fun initAdapter() {
        if (mAdapter == null) {
            mAdapter = object : PhotoAdapter(mContext!!, infoList) {
                override fun addImage() {
                    toShowDialog()
                }

                override fun deleteImage(position: Int) {
                    infoList.removeAt(position)
                    notifyDataSetChanged()
                    imageList.removeAt(position)
                }

                override fun showImage(position: Int) {
                    routerTo.jumpToShowImage(position,imageList)
                }
            }
            upload_image_recycle.adapter = mAdapter
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }


}
