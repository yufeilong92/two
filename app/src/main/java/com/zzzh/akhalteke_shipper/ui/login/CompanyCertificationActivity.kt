package com.zzzh.akhalteke_shipper.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.ui.SelectorImageActivity
import com.zzzh.akhalteke_shipper.ui.login.viewmodel.ComCerViewModel
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.PreferencesUtils
import com.zzzh.akhalteke_shipper.utils.RouterTo
import kotlinx.android.synthetic.main.activity_company_certification.*

/**
 * 公司认证 ，现废弃
 */
class CompanyCertificationActivity : SelectorImageActivity() {

    val comCerViewModel: ComCerViewModel by lazy {
        ViewModelProviders.of(this).get(ComCerViewModel::class.java)
    }

    private var temp = 0
    private var areaCode = ""

    private var imageTemp = 0
    private var businessLicense = ""
    private var doorPhotos = ""
    private var businessCard = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_certification)

        initView()
        initViewModel()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        //有执照
        company_cer_liscense_layout.setOnClickListener {
            if (temp != 0) {
                temp = 0
                liscense()
            }
        }
        company_cer_liscenseno_layout.setOnClickListener {
            if (temp != 1) {
                temp = 1
                liscenseno()
            }
        }
        company_cer_adr_layout2.setOnClickListener {
            RouterTo(mContext).jumpToAddress()
        }
        company_cer_adr_layout.setOnClickListener {
            RouterTo(mContext).jumpToAddress()
        }
        company_cer_license_image.setOnClickListener {
            imageTemp = 0
            toShowDialog()
        }
        company_cer_headericon.setOnClickListener {
            imageTemp = 1
            toShowDialog()
        }
        company_cer_mpicon.setOnClickListener {
            imageTemp = 2
            toShowDialog()
        }
        company_cer_submit2.setOnClickListener {
            submitData()
        }
        company_cer_submit.setOnClickListener {
            submitData()
        }
    }

    private fun initViewModel() {
        comCerViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })
        comCerViewModel.backSuccess.observe(this, Observer { info ->
            Constant.userInfo.ifCompanyCertification = "1"
            Constant.userInfo.corporateName = info!!.corporateName
            PreferencesUtils().updateUserInfo()
            routerTo.jumpToMain()
        })
    }

    /**
     * 有营业执照
     */
    private fun liscense() {
        ccliscense_icon1.visibility = View.GONE
        ccliscense_icon2.visibility = View.VISIBLE
        ccliscenseno_icon1.visibility = View.VISIBLE
        ccliscenseno_icon2.visibility = View.GONE
        ccliscenseno_view.visibility = View.GONE
        ccliscense_view.visibility = View.VISIBLE

        areaCode = ""
        company_cer_address.text = "选择省市区"
    }

    private fun liscenseno() {

        ccliscense_icon1.visibility = View.VISIBLE
        ccliscense_icon2.visibility = View.GONE
        ccliscenseno_icon1.visibility = View.GONE
        ccliscenseno_icon2.visibility = View.VISIBLE
        ccliscenseno_view.visibility = View.VISIBLE
        ccliscense_view.visibility = View.GONE

        areaCode = ""
        company_cer_address2.text = "选择省市区"
    }


    override fun onFileData(imagePath: String, imageStr: String) {
        when (imageTemp) {
            0 -> {
                businessLicense = imageStr
                ImageLoadingUtils.loadLocalImage(company_cer_license_image, imagePath)
            }
            1 -> {
                doorPhotos = imageStr
                ImageLoadingUtils.loadLocalImage(company_cer_headericon, imagePath)
            }
            2 -> {
                businessCard = imageStr
                ImageLoadingUtils.loadLocalImage(company_cer_mpicon, imagePath)
            }
        }
    }
    var corporateName = ""
    var businessLicenseId = ""
    var detailedAddress = ""
    private fun submitData() {

        when (temp) {
            0 -> {
                businessLicenseId = company_cer_licenseno.text.toString().trim()
                corporateName = company_cer_cname2.text.toString().trim()
                detailedAddress = company_cer_maddr2.text.toString().trim()

                isEmpty(businessLicense, "请上传营业执照照片")?:return
                isEmpty(businessLicenseId, "请输入营业执照号")?:return
                isEmpty(corporateName, "请输入公司名称")?:return
                isEmpty(areaCode, "请选择公司所在地")?:return
                isEmpty(detailedAddress, "请输入公司详细地址")?:return
                doorPhotos = ""
                businessCard = ""
            }
            1 -> {
                corporateName = company_cer_cname.text.toString().trim()
                detailedAddress = company_cer_maddr.text.toString().trim()

                isEmpty(doorPhotos, "请上传门头照片")?:return
                isEmpty(businessCard, "请上传名片照片")?:return
                isEmpty(areaCode, "请选择公司所在地")?:return
                isEmpty(corporateName, "请输入公司名称")?:return
                isEmpty(detailedAddress, "请输入公司详细地址")?:return

                businessLicense = ""
                businessLicenseId = ""
            }
        }

        comCerViewModel.authCompany(
            id = Constant.userInfo.id,
            ifBusinessLicense = (temp + 1).toString(),
            corporateName = corporateName,
            businessLicenseId = businessLicenseId,
            businessLicense = businessLicense,
            doorPhotos = doorPhotos,
            businessCard = businessCard,
            areaCode = areaCode,
            detailedAddress = detailedAddress
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddressActivity.adrBackCode && resultCode == AddressActivity.adrBackCode) {
            data ?: return
            val proId = data!!.getIntExtra("pro_id", 0)
            val cityId = data!!.getIntExtra("city_id", 0)
            val areaId = data!!.getIntExtra("area_id", 0)

            val proName = data!!.getStringExtra("pro_name")
            val cityName = data!!.getStringExtra("city_name")
            val areaName = data!!.getStringExtra("area_name")

            when (temp) {
                0 -> {
                    company_cer_address2.text = proName + cityName + areaName
                }
                1 -> {
                    company_cer_address.text = proName + cityName + areaName
                }
            }
            areaCode = areaId.toString()
        }
    }

}
