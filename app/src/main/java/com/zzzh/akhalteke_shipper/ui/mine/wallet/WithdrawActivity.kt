package com.zzzh.akhalteke_shipper.ui.mine.wallet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.BankCardInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.WebActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.WalletViewModel
import com.zzzh.akhalteke_shipper.utils.BANK_MANAGER_CODE
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.activity_withdraw.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 提现
 */
class WithdrawActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(WalletViewModel::class.java)
    }

    private var bankId = ""//银行卡id
    private var usableAmount = ""//可用余额
    private var isAgress = true//是否同意提现协议

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw)
        eventBus.register(this)
        initPro()
        initView()
        initViewModel()
        mViewModel.accountBalance()
    }

    private fun initView() {
        ifBank(false)
        //银行卡管理
        withdraw_tobank.setOnClickListener { routerTo.jumpToBankManager(1) }

        //是否同意提现协议
        withdraw_radio.setOnClickListener {
            if (isAgress) {
                isAgress = false
                withdraw_radio.setImageResource(R.mipmap.icon_radio)
            } else {
                isAgress = true
                withdraw_radio.setImageResource(R.mipmap.icon_radio_red)
            }
        }

        //提现提交
        withdraw_submit.setOnClickListener { submit() }

        //顶部右侧按钮，提现记录
        withdraw_top.setOnClickListener {
            routerTo.jumpTo(WithRecordActivity::class.java)
        }

        //金额编辑框
        withdraw_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ToolUtils.limitEditDit(withdraw_edit, 2)
                val mount = ToolUtils.stringToDouble(usableAmount)
                val editNum = ToolUtils.stringToDouble(withdraw_edit.text.toString().trim())
                if (editNum > mount) {
                    withdraw_edit.setText(mount.toString())
                    withdraw_edit.setSelection(mount.toString().length)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
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
        //账户信息
        mViewModel.balance.observe(this, Observer { info ->
            info?.let {
                usableAmount = it.usableAmount.replace(",", "")
                withdraw_money.text = it.usableAmount.replace(",", "")

                withdraw_all.setOnClickListener {
                    withdraw_edit.setText(usableAmount)
                    withdraw_edit.setSelection(usableAmount.length)
                }
            }
        })
        //成功返回数据
        mViewModel.successData.observe(this, Observer {
            eventBus.post(MessageEvent(MessageEvent.WITHDRAW_SUCCESS))
            routerTo.jumpTo(WithRecordActivity::class.java)
        })
    }

    /**
     * 有没有选择上银行卡
     * @param ifBnak Boolean
     */
    private fun ifBank(ifBnak: Boolean) {
        if (ifBnak) {
            withdraw_bankicon.visibility = View.VISIBLE
            withdraw_bank.visibility = View.VISIBLE
            withdraw_bankno.visibility = View.VISIBLE
            withdraw_bank_text.visibility = View.GONE
        } else {
            withdraw_bankicon.visibility = View.GONE
            withdraw_bank.visibility = View.GONE
            withdraw_bankno.visibility = View.GONE
            withdraw_bank_text.visibility = View.VISIBLE
        }
    }

    /**
     * 初始化《提现条约》，超文本，添加颜色
     */
    private fun initPro() {
        val spanText = SpannableString("我已阅读并同意《提现条约》")
        spanText.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        routerTo.jumpToWeb(WebActivity.CASH_URL)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = resources.getColor(R.color.main_color)
                        ds.isUnderlineText = false
                    }
                }, 7, 13,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        withdraw_pro.movementMethod = LinkMovementMethod.getInstance()
        withdraw_pro.text = spanText
    }

    /**
     * 提交
     */
    private fun submit() {
        val sum = withdraw_edit.text.toString().trim()

        if (ToolUtils.isEmpty(bankId)) {
            showToast("请选择银行卡")
            return
        }

        if (ToolUtils.isEmpty(sum)) {
            showToast("请输入提现金额")
            return
        }
        val replace = sum.replace(",", "")
        val money: Double = replace.toDouble()
        if (money == 0.0) {
            showToast("体现金额不能为零")
            return
        }

        if (!isAgress) {
            showToast("请阅读并同意提现协议")
            return
        }
        toCheckInfo {
            mViewModel.accountCash(sum, bankId)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BANK_MANAGER_CODE && resultCode == BANK_MANAGER_CODE) {
            if (data == null) {
                return
            }

            val bankInfo = data.getParcelableExtra<BankCardInfo>("bank_info")
            bankId = bankInfo.id
            ifBank(true)
            withdraw_bankicon.setImageResource(BankCardInfo.BANK_ICON[bankInfo!!.bank]
                    ?: R.mipmap.gongshangtubiao)
            withdraw_bank.text = BankCardInfo.BANK_CODE[bankInfo!!.bank] ?: "银联"
            withdraw_bankno.text = "尾号${bankInfo.cardNumber}储蓄卡"
        }
        super.onActivityResult(requestCode, resultCode, data)
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
