package com.zzzh.akhalteke_shipper

import android.app.Dialog
import android.app.DownloadManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.yanzhenjie.permission.Permission
import com.zzzh.akhalteke.weather.Observer.WeatherObserver
import com.zzzh.akhalteke_shipper.bean.GmContentVo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.VersionInfo
import com.zzzh.akhalteke_shipper.bean.WeatherVo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.main_fragment.Tab1Fragment
import com.zzzh.akhalteke_shipper.updateapp.UpdateDialog
import com.zzzh.akhalteke_shipper.updateapp.UpdateUitls
import com.zzzh.akhalteke_shipper.updateapp.UpdateViewModel
import com.zzzh.akhalteke_shipper.utils.LocationUtils
import com.zzzh.akhalteke_shipper.utils.PermissionUtils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.cell_tab_bottom.*
import kotlinx.android.synthetic.main.gm_weather_title_root.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * 首页
 */
class MainActivity : BaseActivity(){
    //下面tab的位置
    private var temp: Int = 2

    private var fragments: Array<Fragment?> = arrayOf()
    private var images: Array<ImageView> = arrayOf()
    private var texts: Array<TextView> = arrayOf()

    private var isDestroy = true
    private var entryTemp = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        eventBus.register(this)
        entryTemp = intent.getIntExtra("entry_temp", 2)
        temp = entryTemp
        isDestroy = false
        initView()
        update()
    }

    override fun onStart() {
        super.onStart()

    }

    /**
     * 初始化页面
     */
    fun initView() {
        fragments = arrayOf(
                supportFragmentManager.findFragmentById(R.id.main_fragment1),
                supportFragmentManager.findFragmentById(R.id.main_fragment2),
                supportFragmentManager.findFragmentById(R.id.main_fragment3),
                supportFragmentManager.findFragmentById(R.id.main_fragment4),
                supportFragmentManager.findFragmentById(R.id.main_fragment5)
        )

        images = arrayOf(image1, image2, image3, image4, image5)
        texts = arrayOf(text1, text2, text3, text4, text5)
        val transaction = supportFragmentManager.beginTransaction()
        for (i in 0..4) {
            transaction.hide(fragments!![i]!!)
        }
        transaction.show(fragments!![temp]!!)
        transaction.commit()

        images[temp].isSelected = true
        texts[temp].isSelected = true
    }

    /**
     * tab切换
     * @param view View
     */
    fun LayoutOnclickMethod(view: View) {
        when (view) {
            layout1 ->{
                changeLayout(0)
            }
            layout2 -> changeLayout(1)
            layout3 -> changeLayout(2)
            layout4 -> changeLayout(3)
            layout5 -> changeLayout(4)
        }
    }

    /**
     * 按钮切换后，底部按钮和页面的变化
     * @param position Int
     */
    fun changeLayout(position: Int) {
        if (position != temp) {
            supportFragmentManager.beginTransaction().hide(fragments[temp]!!).show(fragments[position]!!).commitAllowingStateLoss()
            images[temp].isSelected = false
            images[position].isSelected = true
            texts[temp].isSelected = false
            texts[position].isSelected = true
            temp = position
        }
    }

    /**
     * 返回键，按两次返回
     */
    private var current = 0L

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val nowTime = System.currentTimeMillis()
            if (nowTime - current < 2000) {
                finish()
            } else {
                current = nowTime
                showToast("再按一次退出")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    /**
     * 自动更新
     */
    private val updateViewModel by lazy {
        ViewModelProviders.of(this).get(UpdateViewModel::class.java)
    }
    private var dialog: UpdateDialog? = null
    private fun update() {
        updateViewModel.isShowProgress.observe(this, Observer {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })

        updateViewModel.vInfo.observe(this, Observer { versionInfo ->
            val vCode = ToolUtils.getVersionCode(mContext)!!.versionCode

            if (ToolUtils.stringToIntM(versionInfo!!.versionCode) > vCode) {
                dialog = UpdateDialog(mContext, versionInfo) {
                    if (versionInfo.type == "1") {
                        mContext.finish()
                    }
                }
                if (!isDestroy) {
                    dialog!!.show()
                }
            }
        })
        updateViewModel.updateHttp(false)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.message) {
            MessageEvent.DRIVER_APPOINT -> {
                changeLayout(3)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isDestroy = true
        eventBus.unregister(this)
        if (UpdateDialog.downloadId > 0) {
            (mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).remove(UpdateDialog.downloadId)
            UpdateDialog.downloadId = -1
        }
        ToolUtils.dismissDialog(dialog)
    }


}
