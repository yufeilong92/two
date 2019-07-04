package com.zzzh.akhalteke_shipper.ui.mine.owners

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.InvoiceInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.DaoViewModel
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.VerifyDaoDialog
import kotlinx.android.synthetic.main.activity_certification2.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 道认证
 */
class CertificationActivity : BaseActivity() {

    private var entryTemp = 0 //0道认证，1修改信息
    private var daoStr = "" //道认证图片地址
    private var businessLicense = ""//营业执照地址
    private var businessName = "" //公司名称
    private var businessNumber = ""//营业执照号
    private var businessAddress = ""//公司地址

    //道认证类
    private var invoiceInfo: InvoiceInfo = InvoiceInfo()

    //认证dialog
    private lateinit var daoDialog: VerifyDaoDialog

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(DaoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification2)
        eventBus.register(this)
        entryTemp = intent.getIntExtra("entry_temp", 0)

        if (entryTemp == 1) {
            mViewModel.invoiceInfo()
            fillView()
        } else {
            daoStr = intent.getStringExtra("dao_str")
            businessLicense = intent.getStringExtra("bussiness_license")
            businessName = intent.getStringExtra("bussiness_name")
            businessAddress = intent.getStringExtra("bussiness_adr")
            businessNumber = intent.getStringExtra("bussiness_number")

            fillView0()
        }
        initView()
        initViewModel()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        cer_dao_submit.setOnClickListener {
            submit()
        }

        daoDialog = VerifyDaoDialog(mContext) {
            finishBase()
        }
    }

    /**
     * 修改进入填充数据
     */
    private fun fillView() {
        cer_dao_name.setText(invoiceInfo.name)
        cer_dao_tax.setText(invoiceInfo.taxNumber)
        cer_dao_adr.setText(invoiceInfo.address)
        cer_dao_phone.setText(invoiceInfo.phone)
        cer_dao_bank.setText(invoiceInfo.bank)
        cer_dao_bankno.setText(invoiceInfo.bankNumber)
        cer_dao_remark.setText(invoiceInfo.comments)
    }

    /**
     * 首次进入填充数据
     */
    private fun fillView0(){
        cer_dao_name.setText(businessName)
        cer_dao_tax.setText(businessNumber)
        cer_dao_adr.setText(businessAddress)
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

        //提交之后返回
        mViewModel.successData.observe(this, Observer {
            eventBus.post(MessageEvent(MessageEvent.DAO_ADD_SUCCESS))
            when (entryTemp) {
                0 -> {
                    showToast("提交成功")
                    ToolUtils.showDialog(daoDialog)
//                    finish()
//                    routerTo.jumpToDaoInfo()
                }
                1 -> {
                    showToast("修改成功")
                    finishBase()
                }
            }
        })

        //修改时，道认证信息返回
        mViewModel.invoiceData.observe(this, Observer {
            invoiceInfo = it ?: return@Observer
            fillView()
        })
    }

    /**
     * 提交数据
     */
    private fun submit() {
        invoiceInfo.name = cer_dao_name.text.toString().trim()
        invoiceInfo.taxNumber = cer_dao_tax.text.toString().trim()
        invoiceInfo.address = cer_dao_adr.text.toString().trim()
        invoiceInfo.phone = cer_dao_phone.text.toString().trim()
        invoiceInfo.bank = cer_dao_bank.text.toString().trim()
        invoiceInfo.bankNumber = cer_dao_bankno.text.toString().trim()
        invoiceInfo.comments = cer_dao_remark.text.toString().trim()

        isEmpty(invoiceInfo.name, "请输入发票抬头信息") ?: return
        isEmpty(invoiceInfo.taxNumber, "请输入纳税人识别码") ?: return
        isEmpty(invoiceInfo.address, "请输入注册地址") ?: return
        isEmpty(invoiceInfo.phone, "请输入注册点电话") ?: return

        if (invoiceInfo.phone.length != 11) {
            showToast("请输入11位手机号")
            return
        }

        isEmpty(invoiceInfo.bank, "请输入开户银行") ?: return
        isEmpty(invoiceInfo.bankNumber, "请输入银行卡号") ?: return
        toCheckInfo {
            when (entryTemp) {
                0 -> {
                    invoiceInfo.dao = daoStr
                    mViewModel.daoAuth(daoStr, mutableMapOf(
                        "name" to invoiceInfo.name,
                        "taxNumber" to invoiceInfo.taxNumber,
                        "address" to invoiceInfo.address,
                        "phone" to invoiceInfo.phone,
                        "bank" to invoiceInfo.bank,
                        "bankNumber" to invoiceInfo.bankNumber,
                        "comments" to invoiceInfo.comments,
                        "businessLicense" to businessLicense,
                        "businessLicenseNumber" to businessNumber
                    ))
                }
                1 -> {
                    mViewModel.invoiceUpdate(invoiceInfo)
                }
            }
        }
    }

//    taxNumber	必填	string	税号
//    address	必填	string	单位地址
//    phone	必填	string	电话号码
//    bank	必填	string	开户银行
//    bankNumber	必填	string	银行账户
//    comments	非必填	string	商品明细
//    businessLicense	必填	string	营业执照地址
//    businessLicenseNumber	必填	string	营业执照号

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
        ToolUtils.dismissDialog(daoDialog)
    }

}
