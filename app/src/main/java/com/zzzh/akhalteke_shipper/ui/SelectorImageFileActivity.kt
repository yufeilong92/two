package com.zzzh.akhalteke_shipper.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.lipo.utils.UriToFile
import com.yanzhenjie.permission.Permission
import com.zzzh.akhalteke_shipper.utils.PermissionUtils
import com.zzzh.akhalteke_shipper.utils.TakePhotoUtils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.utils.UploadImageUitls
import com.zzzh.akhalteke_shipper.view.dialog.SelectImageDialog
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.nereo.multi_image_selector.MultiImageSelector
import me.nereo.multi_image_selector.MultiImageSelectorActivity
import top.zibin.luban.Luban
import java.io.IOException
import java.util.ArrayList

/**
 * 选择图片帮助类
 */
open class SelectorImageFileActivity: BaseActivity() {

    private val REQUEST_IMAGE_BACK = 1002//从相册选择
    private val PHOTO_PIC_CODE = 1001// 拍照

    //图片列表
    val imagePaths: ArrayList<String> = arrayListOf()
    //选择时候弹出的dialog
    lateinit var selectImageDialog: SelectImageDialog
    private var temp = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDialog()
    }

    /**
     * 显示dialog
     */
    fun toShowDialog() {
        ToolUtils.showDialog(selectImageDialog)
    }

    /**
     * 用与继承，获取到数据
     * @param imagePath MutableList<String> 选中的图片地址
     */
    open fun toGetData(imagePath:MutableList<String>){

    }

    /**
     * 初始化dialog
     */
    private fun initDialog() {
        selectImageDialog = object : SelectImageDialog(mContext) {
            override fun onTakePrice() {
                PermissionUtils.showPermission(
                    this@SelectorImageFileActivity, "上传图片需要照相和读写权限，是否同意", arrayOf(
                        Permission.CAMERA,
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    toPicture()
                }
            }

            override fun onFromPhoto() {
                PermissionUtils.showPermission(
                    this@SelectorImageFileActivity, "上传图片需要照相和读写权限，是否同意", arrayOf(
                        Permission.CAMERA,
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    toSelectPhoto()
                }
            }
        }
    }

    /**
     * 选择照片，temp 1选择单张图片，0选择多张图片
     */
    private fun toSelectPhoto() {
        if (temp == 1) {
            MultiImageSelector.create().showCamera(false).single().start(this, REQUEST_IMAGE_BACK)
        } else {
            MultiImageSelector.create().showCamera(false).count(temp).multi().origin(imagePaths)
                .start(this, REQUEST_IMAGE_BACK)
        }
    }

    /**
     * 照相
     */
    private var takePhoneUri: Uri? = null
    private fun toPicture() {
        try {
            takePhoneUri = TakePhotoUtils.takePhoto(mContext, PHOTO_PIC_CODE)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PHOTO_PIC_CODE && resultCode == Activity.RESULT_OK) {
            if (takePhoneUri != null) {
                imagePaths.clear()
                val filePath = UriToFile.getFilePathFromURI(mContext, takePhoneUri!!)
                imagePaths.add(filePath)
                toGetData(imagePaths)
            }
        }

        if (data == null) {
            return
        }

        if (requestCode === REQUEST_IMAGE_BACK) {
            // 获取返回的图片列表(存放的是图片路径)
            imagePaths.clear()
            val paths: MutableList<String> = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT)
            imagePaths.addAll(paths)
            toGetData(imagePaths)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ToolUtils.dismissDialog(selectImageDialog)
    }

}