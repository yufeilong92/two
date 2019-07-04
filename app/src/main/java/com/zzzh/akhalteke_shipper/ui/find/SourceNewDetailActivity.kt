package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MainSendInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.GoodsViewModel
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.TimeUntils
import com.zzzh.akhalteke_shipper.utils.ZzzhUtils
import com.zzzh.akhalteke_shipper.view.dialog.CallCarOwnerDialog
import kotlinx.android.synthetic.main.activity_source_new_detail.*
import kotlinx.android.synthetic.main.gm_title_layout.*

/**
 * @Author : YFL  is Creating a porject in com.zzzh.akhalteke_shipper.ui.find
 * @Package com.zzzh.akhalteke_shipper.ui.find
 * @Email : yufeilong92@163.com
 * @Time :2019/5/30 9:23
 * @Purpose :货源详情
 */
class SourceNewDetailActivity : BaseActivity() {

    var goodsId = ""//货源id

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(GoodsViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source_new_detail)

        goodsId = intent.getStringExtra("goods_id")

        gm_tv_activity_title.text="详情"
        initView()
        initViewModel()
        mViewModel.goodsDetails(goodsId)
    }

    private fun initView() {

    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, android.arch.lifecycle.Observer {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })
        //获取货源详情
        mViewModel.senfInfo.observe(this, Observer {
            fillData(it!!)
        })
    }

    /**
     * 填充页面数据
     * @param info MainSendInfo
     */
    private fun fillData(info: MainSendInfo) {
        showView()
//        source_detail_path.text = ToolUtils.adrSpannStr(
//                mContext, ZzzhUtils.adrNameLoad(info.loadAreaCode, info.loadAddress),
//                ZzzhUtils.adrNameLoad(info.unloadAreaCode, info.unloadAddress)
//        )
        tv_source_detail_city.text=info.loadAreaCode
        tv_source_detail_city_address.text=info.loadAddress
        tv_source_detail_uncity.text=info.unloadAreaCode
        tv_source_detail_uncity_unaddress.text= info.unloadAddress


        tv_source_detail_car.text = ZzzhUtils.carDataLoad(info.carLength, info.carType)
        tv_source_detail_goods.text = "${info.goodsName} ${info.weightVolume}"
        tv_source_detail_other.text = info.comments

        ImageLoadingUtils.loadNetImage(sdv_source_detail_header, info.shipperPortrait)
        tv_source_detail_name.text = info.shipperName
        tv_source_detail_create_time.text = TimeUntils.getStrTime(info.registerTime) + "    注册"
        //公司地址
//        source_detail_compan_yname.text = info.corporateName
//        source_detail_company_address.text = info.corporateName

        btn_source_detail__play_phone.setOnClickListener {
            toCheckInfo {
                val dialog = CallCarOwnerDialog(mContext, info.shipperPhone)
                dialog.show()
                dialog.setReplaceText()
            }
        }
        rl_source_detail_driver.setOnClickListener { routerTo.jumpToOwnerDetail(info.shipperId) }

        if (Constant.userInfo.ifRealCertification != "1") {
            ifCer(false)
        } else {
            ifCer(true)
        }

        if (Constant.userInfo.ifCompanyCertification != "1") {
            ifCompany(false)
        } else {
            ifCompany(true)
        }
    }

    /**
     * 显示底部
     */
    private fun showView() {
        ll_source_detail_root.visibility = View.VISIBLE
        btn_source_detail__play_phone.visibility = View.VISIBLE
    }

    /**
     * 是否实名认证
     * @param temp Boolean true实名认证，false没有实名认证
     */
    private fun ifCer(temp: Boolean) {
        iv_source_detail_readname.visibility=if (temp) View.VISIBLE else View.GONE
    }

    /**
     * 是否公司认证
     * @param temp Boolean true已认证,false没有认证
     */
    private fun ifCompany(temp: Boolean) {
        iv_source_detail_readname.visibility=if (temp) View.VISIBLE else View.GONE
    }

}
