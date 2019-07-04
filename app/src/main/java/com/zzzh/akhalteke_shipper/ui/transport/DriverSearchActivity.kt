package com.zzzh.akhalteke_shipper.ui.transport

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.transport.viewmodel.DriverSearchViewModel
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.activity_driver_search.*

/**
 * 搜索司机，暂时废弃
 */
class DriverSearchActivity : BaseActivity() {

    val mViewModel: DriverSearchViewModel by lazy {
        ViewModelProviders.of(this).get(DriverSearchViewModel::class.java)
    }
    private var editStr = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_search)

        initView()
        initViewModel()
    }

    private fun initView() {
        driver_search_edit.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    submitData()
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }

        item_driver_button02.setOnClickListener {
            routerTo.callPhone(editStr)
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
        mViewModel.info.observe(this, Observer {
//            if(it == null||ToolUtils.isEmpty(it!!.id)){
//                isShowData(false)
//            }else{
//                isShowData(true)
//            }
        })
    }

    private fun isShowData(isData: Boolean) {
        if (isData) {
            driver_search_content.visibility = View.VISIBLE
            driver_search_contentno.visibility = View.GONE
        } else {
            driver_search_content.visibility = View.GONE
            driver_search_contentno.visibility = View.VISIBLE
        }
    }

    private fun submitData(){
         editStr = driver_search_edit.text.toString().trim()
        if(ToolUtils.isEmpty(editStr)||editStr.length!=11){
            showToast("请输入正确的电话号码")
            return
        }
        mViewModel.getCarOwnerByPhone(editStr)
    }

}
