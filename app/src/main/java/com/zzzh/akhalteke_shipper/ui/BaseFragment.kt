package com.zzzh.akhalteke_shipper.ui

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.RelativeLayout
import com.lipo.views.MyProgreeDialog
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.GmContentVo
import com.zzzh.akhalteke_shipper.bean.UserInfo
import com.zzzh.akhalteke_shipper.ui.weather.WeatherActivity
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.PreferencesUtils
import com.zzzh.akhalteke_shipper.utils.RouterTo
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.CerNoDialog
import com.zzzh.akhalteke_shipper.view.dialog.NOCerDialog
import org.greenrobot.eventbus.EventBus

import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseFragment : Fragment() {

    //跳转集中页
    val routerTo: RouterTo by lazy {
        RouterTo(mContext)
    }
    //evenbus初始化
    val eventBus: EventBus by lazy {
        EventBus.getDefault()
    }
    //上下文
    lateinit var mContext: Activity
    //加载中dialog
    lateinit var progressDialog: MyProgreeDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity!!
        progressDialog = MyProgreeDialog(mContext)
        initCheckViewModel()
    }

    /**
     * 结束界面
     */
    fun finishBase() {
        mContext.finish()
        mContext.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
    }
    /**
     * 展示加载中dialog
     */
    fun showProgress() {
        ToolUtils.showDialog(progressDialog)
    }
    /**
     * 加载中dialog消失
     */
    fun dismissProgress() {
        ToolUtils.dismissDialog(progressDialog)
    }
    /**
     * 显示toast
     * @param toastStr String
     */
    fun showToast(toastStr: String) {
        ToastView.setToasd(mContext, toastStr)
    }

    private val baseViewModel by lazy {
        ViewModelProviders.of(this).get(CheckViewModel::class.java)
    }

    /**
     * 初始化检测view model返回
     */
    private fun initCheckViewModel() {
        baseViewModel.isShowProgress.observe(this, android.arch.lifecycle.Observer {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })

        baseViewModel.uInfo.observe(this, android.arch.lifecycle.Observer {
            toCerDialog(it!!)
        })
    }
    //检测返回
    private var checkSuccess: () -> Unit = {}
    /**
     * 去检测
     * @param callSuccess () -> Unit
     */
    fun toCheckInfo(callSuccess: () -> Unit) {
        showCerDialog(Constant.userInfo){
            baseViewModel.infoCheck()
            checkSuccess = callSuccess
        }
    }
    /**
     * 检测是否实名认证
     * @param callSuccess () -> Unit
     */
    fun toCheckCre(callSuccess: () -> Unit){
        showCerDialog(Constant.userInfo,callSuccess)
    }

    /**
     * 显示实名认证dialog
     * @param uInfo UserInfo
     */
    fun toCerDialog(uInfo: UserInfo) {
        if (Constant.userInfo.ifRealCertification != uInfo.ifRealCertification ||
            Constant.userInfo.ifCompanyCertification != uInfo.ifCompanyCertification
        ) {
            Constant.userInfo.ifRealCertification = uInfo.ifRealCertification
            Constant.userInfo.ifCompanyCertification = uInfo.ifCompanyCertification
            PreferencesUtils().updateUserInfo()
        }

        showCerDialog(uInfo){
            checkSuccess()
        }
    }

    private fun showCerDialog(uInfo: UserInfo,callBack:()->Unit) {
        when (uInfo.ifRealCertification) {
            "1" -> {
                callBack()
//                when (uInfo.ifCompanyCertification) {
//                    "1" -> {
//                        callBack()
//                    }
//                    "2" -> {
//                        NOCerDialog(mContext, 1).show()
//                    }
//                    "3" -> {
//                        CerNoDialog(mContext, 1).show()
//                    }
//                    else ->{
//                        NOCerDialog(mContext, 1).show()
//                    }
//                }
            }
            "2" -> {
                NOCerDialog(mContext, 0).show()
            }
            "3" -> {
                CerNoDialog(mContext, 0).show()
            }
            else ->{
                NOCerDialog(mContext, 0).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ToolUtils.dismissDialog(progressDialog)
    }
    /**
     * 设置天监听
     */
    open fun setWeatherOnClick(rlv: RelativeLayout) {
        rlv.setOnClickListener {
            if (GmContentVo.getWeatherVo() == null) {
                showToast("暂无天气信息")
                return@setOnClickListener
            }
            val vo = GmContentVo.getWeatherVo()
            val bunlde = Bundle()
            bunlde.putParcelable(WeatherActivity.TYPE, vo)
            jumpWeatherTo(WeatherActivity::class.java, bunlde, "马道天气")
        }
    }

    fun jumpWeatherTo(clazz: Class<*>, bundle: Bundle, title: String) {
        val intentB = Intent()
        intentB.setClass(mContext, clazz)
        intentB.putExtras(bundle)
        intentB.putExtra(BaseActivity.CNT_PARAMETE_TITLE, title)
        mContext.startActivity(intentB)
        mContext.overridePendingTransition(R.anim.weather_up, R.anim.weather_down)
    }
}