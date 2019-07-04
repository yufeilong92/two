package com.zzzh.akhalteke_shipper.ui.mine

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.activity_feedback.*

/**
 * 意见反馈
 * @property mViewModel MineViewModel
 */
class FeedbackActivity : BaseActivity() {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(MineViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        initView()
        initViewModel()

    }

    /**
     * 初始化界面
     */
    private fun initView() {
        //编辑框
        feedback_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val strLenght = feedback_edit.text.toString().trim()
                feedback_text.text = "${strLenght.length}/200"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //提交
        feedback_submit.setOnClickListener {
            val content = feedback_edit.text.toString().trim()
            isEmpty(content, "请输入反馈意见") ?: return@setOnClickListener

            mViewModel.feedback(content)
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

        mViewModel.successData.observe(this, Observer {
            showToast("提交成功")
            finishBase()
        })
    }


}
