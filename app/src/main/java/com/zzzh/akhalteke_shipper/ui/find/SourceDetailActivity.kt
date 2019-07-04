package com.zzzh.akhalteke_shipper.ui.find

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MainSendInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.GoodsViewModel
import com.zzzh.akhalteke_shipper.utils.*
import com.zzzh.akhalteke_shipper.view.dialog.CallCarOwnerDialog
import kotlinx.android.synthetic.main.activity_source_detail.*

/**
 * 货源详情
 */
class SourceDetailActivity : BaseActivity() {

    var goodsId = ""//货源id

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(GoodsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source_detail)

        goodsId = intent.getStringExtra("goods_id")

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
        source_detail_path.text = ToolUtils.adrSpannStr(
            mContext, ZzzhUtils.adrNameLoad(info.loadAreaCode, info.loadAddress),
            ZzzhUtils.adrNameLoad(info.unloadAreaCode, info.unloadAddress)
        )

        source_detail_car.text = ZzzhUtils.carDataLoad(info.carLength, info.carType)
        source_detail_goods.text = "${info.goodsName} ${info.weightVolume}"
        source_detail_text05.text = info.comments

        ImageLoadingUtils.loadNetImage(source_detail_header, info.shipperPortrait)
        source_detail_name.text = info.shipperName
        source_detail_time.text = TimeUntils.getStrTime(info.registerTime) + "    注册"
        source_detail_compan_yname.text = info.corporateName
//        source_detail_company_address.text = info.corporateName

        source_detail_bottom.setOnClickListener {
            toCheckInfo {
                CallCarOwnerDialog(mContext, info.shipperPhone).show()
            }
        }
        source_detail_owner.setOnClickListener { routerTo.jumpToOwnerDetail(info.shipperId) }

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
        source_detail_view01.visibility = View.VISIBLE
        source_detail_bottom.visibility = View.VISIBLE
    }

    /**
     * 是否实名认证
     * @param temp Boolean true实名认证，false没有实名认证
     */
    private fun ifCer(temp: Boolean) {
        if (temp) {
            source_detail_id_radio.setImageResource(R.mipmap.icon_dui_red)
            source_detail_id_name.setTextColor(ContextCompat.getColor(mContext, R.color.main_color))
        } else {
            source_detail_id_radio.setImageResource(R.mipmap.icon_dui_gray)
            source_detail_id_name.setTextColor(ContextCompat.getColor(mContext, R.color.main_text6))
        }
    }

    /**
     * 是否公司认证
     * @param temp Boolean true已认证,false没有认证
     */
    private fun ifCompany(temp: Boolean) {
        if (temp) {
            source_detail_company_radio.setImageResource(R.mipmap.icon_dui_red)
            source_detail_company_text.setTextColor(ContextCompat.getColor(mContext, R.color.main_color))
        } else {
            source_detail_company_radio.setImageResource(R.mipmap.icon_dui_gray)
            source_detail_company_text.setTextColor(ContextCompat.getColor(mContext, R.color.main_text6))
        }
    }

}
