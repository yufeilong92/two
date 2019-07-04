package com.zzzh.akhalteke_shipper.ui.main_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanzhenjie.permission.Permission
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.WebActivity
import com.zzzh.akhalteke_shipper.ui.mine.InformationActivity
import com.zzzh.akhalteke_shipper.ui.mine.NoticeActivity
import com.zzzh.akhalteke_shipper.ui.mine.owners.InvoiceActivity
import com.zzzh.akhalteke_shipper.ui.mine.owners.InvoiceManagerActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.MineViewModel
import com.zzzh.akhalteke_shipper.ui.mine.wallet.BankAddActivity
import com.zzzh.akhalteke_shipper.ui.mine.wallet.WalletActivity
import com.zzzh.akhalteke_shipper.ui.mine.wallet.WalletNewActivity
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.ImageLoadingUtils
import com.zzzh.akhalteke_shipper.utils.PermissionUtils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.VerifyDaoDialog
import kotlinx.android.synthetic.main.cell_main_item.view.*
import kotlinx.android.synthetic.main.fragment_main5.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

/**
 * 我的
 */
class Tab5Fragment : BaseFragment() {

    lateinit var mainView: View
    private var isCreate = false

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(MineViewModel::class.java)
    }

    private var daoTemp = 0//0刚进入页面得时候请求，1点击道认证请求接口
    private var daoEntryTemp = 0//0道认证，1发票

    //列表图标
    private val daoIcons: MutableList<Int> = mutableListOf(
        R.mipmap.icon_dao03,
        R.mipmap.icon_dao_red,
        R.mipmap.icon_dao05,
        R.mipmap.icon_dao02,
        R.mipmap.icon_dao01,
        R.mipmap.icon_dao04
    )

    //道认证审核中dialog
    private lateinit var daoDialog: VerifyDaoDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main5, null).also { mainView = it }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCreate = true
        initView()
        EventBus.getDefault().register(this)
        initClick()
        initViewModel()
        initData()
    }

    override fun onHiddenChanged(hidden: Boolean){
        super.onHiddenChanged(hidden)
        initData()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        fmain5_phone.setOnClickListener { routerTo.callKeFu() }
        fmain5_notice.apply {
            cell_main_item_icon.setImageResource(R.mipmap.icon_main_notice)
            cell_main_item_name.text = "消息"
            setOnClickListener {
                routerTo.jumpTo(NoticeActivity::class.java)
            }
        }

        fmain5_set.apply {
            cell_main_item_icon.setImageResource(R.mipmap.ison_main_set)
            cell_main_item_name.text = "设置"
            setOnClickListener { routerTo.jumpToSet() }
        }

        daoDialog = VerifyDaoDialog(mContext) {}
        fmain5_header.setOnClickListener { routerTo.jumpTo(InformationActivity::class.java) }
        fmain5_view.setOnClickListener { routerTo.jumpTo(InformationActivity::class.java) }
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
                    fmain_money.text = this.balance
                    if (this.daoStatus == "1") {
                        fmain5_icon.setImageResource(daoIcons[ToolUtils.stringToIntM(this.daoRank) - 1])
                    }

                }
            }
        })
    }

    /**
     * 初始化页面点击事件
     */
    private fun initClick() {
//        fmain5_cell01.setOnClickListener {//发票信息
//            val intents = Intent(mContext, InvoiceActivity::class.java)
//            startActivity(intents)
//        }

        fmain5_cell01.setOnClickListener { routerTo.jumpToWeb(WebActivity.CITY_URL) }//城市合伙人
        fmain5_cell01_text.setOnClickListener { routerTo.jumpToWeb(WebActivity.CITY_URL) }//城市合伙人
        fmain5_cell02.setOnClickListener { routerTo.jumpToAddressManager() }//地址管理
        fmain5_cell02_text.setOnClickListener { routerTo.jumpToAddressManager() }//地址管理
        fmain5_cell03.setOnClickListener {
            //发票管理
            daoTemp = 1
            daoEntryTemp = 1
            mViewModel.mineInfo()
        }
        fmain5_cell03_text.setOnClickListener {
            //发票管理
            daoTemp = 1
            daoEntryTemp = 1
            mViewModel.mineInfo()
        }
        fmain5_cell04.setOnClickListener { routerTo.jumpToWeb(WebActivity.ABOUT_URL) }//关于我们
        fmain5_cell04_text.setOnClickListener { routerTo.jumpToWeb(WebActivity.ABOUT_URL) }//关于我们
        fmain_wattle.setOnClickListener { toCheckInfo { routerTo.jumpTo(WalletNewActivity::class.java) } }//钱包

        fmain5_dao.setOnClickListener {
            //道认证
            daoTemp = 1
            daoEntryTemp = 0
            mViewModel.mineInfo()
        }

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
            MessageEvent.MINE_HEADER_SUCCESS->{
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