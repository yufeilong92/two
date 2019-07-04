package com.zzzh.akhalteke_shipper.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.retrofit.RetrofitFactory
import com.zzzh.akhalteke_shipper.utils.WebHelper
import kotlinx.android.synthetic.main.activity_web.*


/**
 * 网页
 */
class WebActivity : BaseActivity() {

    private var entryTemp = 0//进入方式
    private var entryValue = ""//进入参数
    private var titleName = ""//标题名称
    private var mUrl: String = ""//打开的网页地址

    //webview 辅助类
    private var helper: WebHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        entryTemp = intent.getIntExtra("entry_temp", 0)
        entryValue = intent.getStringExtra("entry_value")

        addData()
        initView()

    }

    /**
     * 初始化界面
     */
    private fun initView() {
        web_title.toSetTitleName(titleName)
        web_title.onClickBackButton {
            toBackGo()
        }

        helper = WebHelper(mContext)
        initWeb(web_view)
        web_view.loadUrl(mUrl)

        toWebChromeClient(web_view)
    }

    /**
     * 初始化webview
     * @param webView WebView
     */
    fun initWeb(webView: WebView) {
        val webSettings = webView.settings.apply {
            javaScriptEnabled = true // 支持js
            useWideViewPort = false // 将图片调整到适合webview的大小
            loadWithOverviewMode = true // 缩放至屏幕的大小
            loadsImagesAutomatically = true //  支持自动加载图片
            builtInZoomControls = true  // 设置支持缩放
            setSupportZoom(true) // 支持缩放

            domStorageEnabled = true
            allowContentAccess = true
            setAppCacheEnabled(true)
            databaseEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webSettings.setAppCachePath(mContext.cacheDir.absolutePath)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view!!.loadUrl(url)
                return true
            }
        }
    }

    fun toWebChromeClient(webView: WebView) {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                // 设置title
                super.onReceivedTitle(view, title)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                // 加载进度
                super.onProgressChanged(view, newProgress)

                when (newProgress) {
                    0 -> {
                        showProgress()
                    }
                    100 -> {
                        dismissProgress()
                    }
                }
            }
        }
    }

    val httpUrl = RetrofitFactory.BASE_URL
    private fun addData() {
        when (entryTemp) {
            ABOUT_URL -> {
                mUrl = "${httpUrl}about.html"
                titleName = "关于我们"
            }
            CASH_URL -> {
                mUrl = "${httpUrl}agreement/cash.html"
                titleName = "提现协议"
            }
            GOODS_URL -> {
                mUrl = "${httpUrl}agreement/goods.html"
                titleName = "货物运输协议"
            }
            DRIVER_URL -> {
                mUrl = "${httpUrl}agreement/driver.html"
                titleName = "司机协议"
            }
            SHIPPER_URL -> {
                mUrl = "${httpUrl}agreement/shipper.html"
                titleName = "货主协议"
            }
            USER_SERVICE_URL -> {
                mUrl = "${httpUrl}agreement/userService.html"
                titleName = "用户服务协议"
            }
            CITY_URL -> {
                mUrl = "${httpUrl}city.html"
                titleName = "城市合伙人"
            }
        }
    }

    companion object {
        const val ABOUT_URL = 0
        const val CASH_URL = 1
        const val GOODS_URL = 2
        const val DRIVER_URL = 3
        const val SHIPPER_URL = 4
        const val USER_SERVICE_URL = 5
        const val CITY_URL = 6
    }

//    提现协议：agreement/cash.html
//    货源协议：agreement/goods.html
//    司机协议：agreement/driver.html
//    货主协议：agreement/shipper.html
//    用户服务协议：agreement/userService.html


    private fun toBackGo(){
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            finishBase()
        }
    }

    override fun onBackPressed(){
        toBackGo()
    }
}
