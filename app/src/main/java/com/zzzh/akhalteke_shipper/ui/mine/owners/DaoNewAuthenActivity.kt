package com.zzzh.akhalteke_shipper.ui.mine.owners

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.BussinessInfo
import com.zzzh.akhalteke_shipper.ui.SelectorImageFileActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.DaoViewModel
import com.zzzh.akhalteke_shipper.utils.GlideUtil
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.utils.ZzzhUtils
import kotlinx.android.synthetic.main.activity_dao_new_authen.*
import kotlinx.android.synthetic.main.gm_title_layout.gm_tv_activity_title
import kotlinx.android.synthetic.main.gm_title_layout_right.*

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.mine.owners
 * @Package com.zzzh.akhalteke_shipper.ui.mine.owners
 * @Email : yufeilong92@163.com
 * @Time :2019/5/29 16:28
 * @Purpose :道认证
 */
class DaoNewAuthenActivity : SelectorImageFileActivity() {
    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(DaoViewModel::class.java)
    }

    private var imageTemp = 0//0营业执照，1法人授权书
    private var daoStr = "" //法人授权书地址
    private var businessLicense = ""//营业执照地址
    private var businessName = ""//公司名称
    private var businessAddress = ""//公司地址
    private var businessNumber = ""//营业执照号
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dao_new_authen)
        gm_tv_activity_title.text="道认证"
        gm_tv_activity_right_title.text="联系客服"
        gm_tv_activity_right_title.setOnClickListener {
            toCheckInfo {
                routerTo.callKeFu()
            }
        }
        initViewModel()
        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        iv_dao_auther_zhizhao.setOnClickListener {
            //营业执照图片选择
            imageTemp = 0
            toShowDialog()
        }
        rl_dao_authen_updata_corporation.setOnClickListener {
            //法人授权书图片选择
            imageTemp = 1
            toShowDialog()
        }

        btn_dao_auther_submit.setOnClickListener {
            //提交
            submit()
        }
        //模块下载
        rl_dao_authen_down_corporation.setOnClickListener {
            ZzzhUtils.shareDao(mContext)
        }
    }

    /**
     * 营业执照图片上传后返回营业执照信息
     * @param info BussinessInfo
     */
    private fun showDao(info: BussinessInfo) {
//        dao_authen_layout02.visibility = View.VISIBLE
        tv_dao_authen_zhidao.text = info.number
        tv_dao_authen_company.text = info.name
        tv_dao_authen_address.text = info.address
//        ImageLoadingUtils.loadNetImage(dao_authen_license_image, info.imagePath)
        GlideUtil.LoadImagerWithOutHear(mContext, iv_dao_auther_zhizhao, info.imagePath)
        businessLicense = info.imagePath
        businessName = info.name
        businessAddress = info.address
        businessNumber = info.number
    }



    override fun toGetData(imagePath: MutableList<String>) {
        super.toGetData(imagePath)
        when (imageTemp) {//营业执照图片
            0 -> {
                mViewModel.authBussiness(imagePath[0], mutableMapOf())
            }
            1 -> {//法人授权书图片
                daoStr = imagePath[0]
//                ImageLoadingUtils.loadLocalImage(dao_image, daoStr)
                GlideUtil.LoadImager(mContext, iv_dao_authen_updata_corporation,daoStr)
//                dao_icon04.visibility = View.GONE
            }
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
        //上传营业执照后返回
        mViewModel.bussInfo.observe(this, Observer {
            showDao(it!!)
        })
        mViewModel.resonseError.value = true
        mViewModel.errorMsg.observe(this, Observer {
            showToast(it!!)
            tv_dao_authen_zhidao.text = ""
            tv_dao_authen_company.text =""
            tv_dao_authen_address.text =""

            businessLicense = ""
            businessName = ""
            businessAddress = ""
            businessNumber = ""
        })
    }

    /**
     * 提交数据
     */
    private fun submit() {
        if (ToolUtils.isEmpty(businessLicense)) {
            showToast("请上传营业执照")
            return
        }

        if (ToolUtils.isEmpty(businessName) || ToolUtils.isEmpty(businessAddress)) {
            showToast("营业执照信息不对，请重新上传")
            return
        }


        if (ToolUtils.isEmpty(daoStr)) {
            showToast("请上传法人授权书")
            return
        }

        routerTo.jumpToCertification(0, daoStr, businessLicense, businessName, businessNumber, businessAddress)
        finish()
    }

}
