package com.zzzh.akhalteke_shipper.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClientOption
import com.google.gson.Gson
import com.lipo.views.MyProgreeDialog
import com.lipo.views.ToastView
import com.yanzhenjie.permission.Permission
import com.zzzh.akhalteke.weather.Observer.WeatherObserver
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.GmContentVo
import com.zzzh.akhalteke_shipper.bean.UserInfo
import com.zzzh.akhalteke_shipper.bean.WeatherVo
import com.zzzh.akhalteke_shipper.mvp.contract.WeatherContract
import com.zzzh.akhalteke_shipper.mvp.model.WeatherModel
import com.zzzh.akhalteke_shipper.mvp.presenter.WeatherPresenter
import com.zzzh.akhalteke_shipper.ui.mine.viewmodel.MineViewModel
import com.zzzh.akhalteke_shipper.ui.weather.WeatherActivity
import com.zzzh.akhalteke_shipper.utils.*
import com.zzzh.akhalteke_shipper.view.dialog.CerNoDialog
import com.zzzh.akhalteke_shipper.view.dialog.NOCerDialog
import com.zzzh.akhalteke_shipper.weather.BaseDrawer
import org.greenrobot.eventbus.EventBus
import java.util.*

open class BaseActivity : AppCompatActivity(), WeatherContract.View {

    companion object {
        val CNT_PARAMETE_TITLE: String = "param_title"
    }

    //跳转集中页
    val routerTo: RouterTo by lazy {
        RouterTo(mContext)
    }
    //evenbus初始化
    val eventBus: EventBus by lazy {
        EventBus.getDefault()
    }

    //上下文
    lateinit var mContext: BaseActivity
    //加载中dialog
    lateinit var progressDialog: MyProgreeDialog
    //activity管理器
    val appManager: AppManager = AppManager.appManager
    /**
     * 天气控制者
     */
    private var mWeatherPresenter: WeatherPresenter? = null

    //检测是否实名认证
    private val baseViewModel by lazy {
        ViewModelProviders.of(this).get(CheckViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏
        mContext = this
        progressDialog = MyProgreeDialog(this)
        appManager.addActivity(this)
        initCheckViewModel()
        initRequest()
    }

    fun initRequest() {
        mWeatherPresenter = WeatherPresenter()
        mWeatherPresenter!!.initMvp(this, WeatherModel())
    }

    /**
     * 显示toast
     * @param toastStr String
     */
    open fun showToast(toastStr: String) {
        ToastView.setToasd(mContext, toastStr)
    }

    //检测返回
    private var checkSuccess: () -> Unit = {}

    /**
     * 去检测
     * @param callSuccess () -> Unit
     */
    fun toCheckInfo(callSuccess: () -> Unit) {
        showCerDialog(Constant.userInfo) {
            baseViewModel.infoCheck()
            checkSuccess = callSuccess
        }
    }

    /**
     * 检测是否实名认证
     * @param callSuccess () -> Unit
     */
    fun toCheckCre(callSuccess: () -> Unit) {
        showCerDialog(Constant.userInfo, callSuccess)
    }


    /**
     * 初始化检测view model返回
     */
    private fun initCheckViewModel() {
        baseViewModel.isShowProgress.observe(this, Observer {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
//                    if(ifdismissProgress){
//                        dismissProgress()
//                    }
                }
            }
        })

        baseViewModel.uInfo.observe(this, Observer {
            dismissProgress()
            toCerDialog(it!!)
        })
    }

    /**
     * 显示实名认证dialog
     * @param uInfo UserInfo
     */
    private fun toCerDialog(uInfo: UserInfo) {
        if (Constant.userInfo.ifRealCertification != uInfo.ifRealCertification ||
                Constant.userInfo.ifCompanyCertification != uInfo.ifCompanyCertification
        ) {
            Constant.userInfo.ifRealCertification = uInfo.ifRealCertification
            Constant.userInfo.ifCompanyCertification = uInfo.ifCompanyCertification
            PreferencesUtils().updateUserInfo()
        }

        showCerDialog(uInfo) {
            checkSuccess()
        }
    }

    private fun showCerDialog(uInfo: UserInfo, callBack: () -> Unit) {
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
            else -> {
                NOCerDialog(mContext, 0).show()
            }
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//     fun onMessageEvent(event: MessageEvent) {
//
//    }

    /**
     * 结束界面
     */
    fun finishBase() {
        finish()
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
    }

    /**
     * 空验证
     * @param name String  字段名
     * @param toastStr String toast语句
     * @return String? 返回
     */
    inline fun isEmpty(name: String, toastStr: String): String? {
        if (ToolUtils.isEmpty(name)) {
            showToast(toastStr)
            return null
        }
        return name
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

    override fun onDestroy() {
        super.onDestroy()
        ToolUtils.dismissDialog(progressDialog)
        appManager.finishActivity(this)
    }

    fun onHomeBack(view: View) {
        finishBase()
    }

    fun onFinishWeather() {
        finish()
        overridePendingTransition(R.anim.weather_finish_up, R.anim.weather_finish_down)
    }

    /**
     * @METHOD 设置竖向管理器
     * @param rlv
     */
    open fun setMangager(rlv: RecyclerView) {
        var layout = GridLayoutManager(mContext, 1)
        layout.orientation = GridLayoutManager.VERTICAL
        rlv.layoutManager = layout
    }

    /**
     * 设置横向管理器
     */
    open fun setHorizontalMangager(rlv: RecyclerView) {
        var layout = GridLayoutManager(mContext, 1)
        layout.orientation = GridLayoutManager.HORIZONTAL
        rlv.layoutManager = layout
    }

    /**
     * 设置天气动画
     */
    open fun SwtWeatherType(weather: String): BaseDrawer.Type {
        return WeatherUtil.getInstance()!!.getWeatherType(weather)
    }

    /**
     * 天气观察者
     */
    private var mVo: WeatherObserver? = null

    open fun getWeatherload(o: WeatherObserver) {
        this.mVo = o
        LocationUtils.initLocationOption { latitude, longitude ->
            //提交天气
            mWeatherPresenter!!.requestWeather(
                this@BaseActivity, latitude.toString(), longitude.toString(),
                true, false, true, true, true)
        }
    }

    override fun WeatherSuccess(t: Any?) {
//        FileUtil.saveText(this@BaseActivity, t.toString())
        val gson = Gson()
        val data = gson.fromJson<WeatherVo>(t.toString(), WeatherVo::class.java)
        GmContentVo.setWeatherVo(data)
        BaseApplication.toInstance().addAndRefresh(mVo)
    }

    override fun WeatherComplise() {
    }

    override fun WeatherError(ex: Throwable) {
    }

    /**
     * 设置天监听
     */
    open fun setWeatherOnClick(rlv: RelativeLayout){
        rlv.setOnClickListener {
            if (GmContentVo.getWeatherVo() == null){
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

    fun finishBaseWeather(){
        finish()
        overridePendingTransition(R.anim.weather_up, R.anim.weather_down)
    }


}