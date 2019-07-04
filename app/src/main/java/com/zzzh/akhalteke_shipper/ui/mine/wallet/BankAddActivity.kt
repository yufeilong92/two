package com.zzzh.akhalteke_shipper.ui.mine.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.BankCardInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.BankViewModel
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.view.dialog.*
import kotlinx.android.synthetic.main.activity_bank_add.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 添加银行卡
 * @property radioTemp Int
 * @property mViewModel BankViewModel
 */
class BankAddActivity : BaseActivity() {

    private var radioTemp = 0//是否同意协议，暂时取消了

    private val mViewModel: BankViewModel by lazy {
        ViewModelProviders.of(this).get(BankViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventBus.register(this)
        setContentView(R.layout.activity_bank_add)
        initView()
        initViewModel()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        bank_add_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val textStr = bank_add_number.text.toString().trim()
                if (textStr.isEmpty()) {
                    bank_add_nb.visibility = View.VISIBLE
                } else {
                    bank_add_nb.visibility = View.GONE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //是否同意协议，暂时取消了
        bank_add_radio.setOnClickListener {
            if(radioTemp == 0){
                radioTemp = 1
                bank_add_radio.setImageResource(R.mipmap.icon_radio)
            }else{
                radioTemp = 0
                bank_add_radio.setImageResource(R.mipmap.icon_radio_red)
            }
        }

        //持卡人说明 提示
        bank_add_nb_tishi.setOnClickListener { PromiseSingleDialog(mContext,"","",{}).apply {
            toSetTitleAndContent("持卡人说明","为保证账户资金安全，只能绑定认证用户本人","我知道了")
            show()
        } }

        //提交按钮
        bank_add_submit.setOnClickListener { submit() }

        //页面数据填充
        Constant.userInfo?.apply {
            bank_add_name.text = this.name
            bank_add_idnumber.text = this.number
            bank_add_nb_name.text = this.name
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

        //错误码响应
        mViewModel.resonseError.postValue(true)
        mViewModel.successData.observe(this, Observer {
//            showToast("添加成功")
            eventBus.post(MessageEvent(MessageEvent.BANK_CARD_ADD))
            BankAddSuccessDialog(mContext,{
                finishBase()
            },{
                finishBase()
            }).show()
        })
        mViewModel.errorMsg.observe(this, Observer {
            BankAddFailureDialog(mContext,it!!,{
                bank_add_number.setText("")
                bank_add_phone.setText("")
            },{
                bank_add_number.setText("")
                bank_add_phone.setText("")
            }).show()
        })
    }

    /**
     * 提交数据
     */
    private fun submit(){
        val bankInfo = BankCardInfo(
            name = Constant.userInfo?.name,
            idCardNumber = Constant.userInfo?.number
        )
        bankInfo.cardNumber = bank_add_number.text.toString().trim()
        bankInfo.phone = bank_add_phone.text.toString().trim()

        isEmpty(bankInfo.cardNumber,"请输入银行卡号")?:return
        isEmpty(bankInfo.phone,"请输入预留手机号")?:return

        if(radioTemp == 1){
            showToast("请阅读并同意")
            return
        }
        mViewModel.addBank(bankInfo)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        eventBus.unregister(this)
    }

}
