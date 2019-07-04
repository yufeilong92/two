package com.zzzh.akhalteke_shipper.ui.main_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.MineTipAdapter
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.WebActivity
import com.zzzh.akhalteke_shipper.ui.mine.InformationActivity
import com.zzzh.akhalteke_shipper.ui.mine.NoticeActivity
import com.zzzh.akhalteke_shipper.ui.mine.owners.InvoiceManagerActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.MineViewModel
import com.zzzh.akhalteke_shipper.ui.mine.wallet.WalletNewActivity
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.VerifyDaoDialog
import com.zzzh.akhalteke_shipper.view.helper.MeRightHelper
import kotlinx.android.synthetic.main.fragment_main5_new01.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author zfb
 * @Date 2019/5/22 10:31
 *
 * 我的页面，重新的版本
 *
 */
class Tab5New01Fragment : BaseFragment() {

    lateinit var mainView: View
    private var isCreate = false

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(MineViewModel::class.java)
    }

    private var daoTemp = 0//0刚进入页面得时候请求，1点击道认证请求接口
    private var daoEntryTemp = 0//0道认证，1发票

    //道认证审核中dialog
    private lateinit var daoDialog: VerifyDaoDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main5_new01, null).also { mainView = it }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCreate = true
        EventBus.getDefault().register(this)

        initView()
        initClick()
        initViewModel()
        initData()

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initData()
    }


    private fun initView() {

        MeRightHelper(fmain5_dao, R.mipmap.ic_me_authentication, "道认证")
        MeRightHelper(fmain5_line, R.mipmap.ic_me_server, "联系客服")
        MeRightHelper(fmain5_notice, R.mipmap.ic_me_msg, "消息")
        MeRightHelper(fmain5_set, R.mipmap.ic_me_settting, "设置")

        daoDialog = VerifyDaoDialog(mContext) {}
    }

    /**
     * 初始化界面点击事件
     */
    private fun initClick() {
        fmain5_wallet.setOnClickListener {
            toCheckInfo { routerTo.jumpTo(WalletNewActivity::class.java) }
        }
        fmain5_header.setOnClickListener {
            routerTo.jumpTo(InformationActivity::class.java)
        }
        fmain5_city.setOnClickListener {
            routerTo.jumpToWeb(WebActivity.CITY_URL)
        }
        fmain5_address.setOnClickListener {
            toCheckCre {
                routerTo.jumpToAddressManager()
            }
        }
        fmain5_invoice.setOnClickListener {
            daoTemp = 1
            daoEntryTemp = 1
            mViewModel.mineInfo()
        }
        fmain5_about_us.setOnClickListener {
                        routerTo.jumpToWeb(WebActivity.ABOUT_URL)
//            routerTo.jumpTo(BaiDuMapActivity::class.java)
        }

        fmain5_dao.setOnClickListener {
            //道认证
            daoTemp = 1
            daoEntryTemp = 0
            mViewModel.mineInfo()
        }
        fmain5_line.setOnClickListener { toCheckCre { routerTo.callKeFu() } }
        fmain5_notice.setOnClickListener {
            routerTo.jumpTo(NoticeActivity::class.java)
        }
        fmain5_set.setOnClickListener {
            routerTo.jumpToSet()
        }
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
        //我的信息数据返回
        mViewModel.mineData.observe(this, Observer {
            it?.apply {
                if (daoTemp == 1) {
                    daoTemp = 0
                    when (this.daoStatus) {
                        "1" -> {
                            if (this.ifInvoiceInfo == "1") {
                                if (daoEntryTemp == 0) {
                                    routerTo.jumpToDaoInfo()
                                } else {
                                    routerTo.jumpTo(InvoiceManagerActivity::class.java)
                                }
                            } else {
                                routerTo.jumpToDaoAuthen()
                            }
                        }
                        "2" -> {
                            routerTo.jumpToDaoAuthen()
                        }
                        "3" -> {
                            ToolUtils.showDialog(daoDialog)
                        }
                        "4" -> {
                            showToast("审核未通过，请重新认证")
                            routerTo.jumpToDaoAuthen()
                        }
                    }
                } else {
//                    fmain_money.text = this.balance
//                    if (this.daoStatus == "1") {
//                        fmain5_icon.setImageResource(daoIcons[ToolUtils.stringToIntM(this.daoRank) - 1])
//                    }
                }
            }
        })
    }

    /**
     * 更新公司名称，名字，头像
     */
    private fun initData() {
        if (isCreate && !isHidden) {
            daoTemp = 0
            mViewModel.mineInfo()
            fmain5_company.text = Constant.userInfo.corporateName
            fmain5_name.text = Constant.userInfo.name
            ImageLoadingUtils.loadNetImage(fmain5_header, Constant.userInfo.portrait)

            val tips: MutableList<Int> = mutableListOf()
            if (Constant.userInfo.ifRealCertification == "1") {
                tips.add(R.mipmap.icon_tip_cer)
            }
            if (Constant.userInfo.ifCompanyCertification == "1") {
                tips.add(R.mipmap.icon_tip_dao)
            }
            fmain5_flow.toSetAdapter(MineTipAdapter(mContext, tips))
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.WITHDRAW_SUCCESS -> {
                daoTemp = 0
                mViewModel.mineInfo()
            }
            MessageEvent.AUTHEN_SUCCESS -> {
                initData()
            }
            MessageEvent.MINE_HEADER_SUCCESS -> {
                ImageLoadingUtils.loadNetImage(fmain5_header, Constant.userInfo.portrait)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        ToolUtils.dismissDialog(daoDialog)
    }

}