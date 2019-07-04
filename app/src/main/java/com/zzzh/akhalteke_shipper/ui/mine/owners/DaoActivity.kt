package com.zzzh.akhalteke_shipper.ui.mine.owners

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.lipo.utils.ShareUtils
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.ui.DownloadActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.DaoViewModel
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.utils.ZzzhUtils
import com.zzzh.akhalteke_shipper.view.dialog.VerifyDaoDialog
import kotlinx.android.synthetic.main.activity_dao.*
import java.io.File

/**
 * 道认证，暂时废弃
 */
class DaoActivity : DownloadActivity() {

    private var daoStr = ""

    private lateinit var daoDialog: VerifyDaoDialog

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(DaoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dao)

        initView()
        initViewModel()
    }


    private fun initView() {
        dao_image.setOnClickListener {
            toShowDialog()
        }

        dao_submit.setOnClickListener {
            isEmpty(daoStr, "请上传法人授权书") ?: return@setOnClickListener

            routerTo.jumpToCertification(0, daoStr)
//            mViewModel.daoAuth(daoStr)

        }

        daoDialog = VerifyDaoDialog(mContext) {
            finishBase()
        }

        dao_layout02.setOnClickListener {
            ZzzhUtils.shareDao(mContext)
        }
    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })

        mViewModel.successData.observe(this, Observer {
            showToast("上传成功")
            ToolUtils.showDialog(daoDialog)
        })
    }

    override fun onFileData(imagePath: String, imageStr: String) {
        super.onFileData(imagePath, imageStr)
        ImageLoadingUtils.loadLocalImage(dao_image, imagePath)
        dao_icon04.visibility = View.GONE
        daoStr = imageStr
    }

    override fun onDestroy() {
        super.onDestroy()
        ToolUtils.dismissDialog(daoDialog)
    }

}
