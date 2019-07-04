package com.zzzh.akhalteke_shipper.ui.main_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.lipo.utils.DisplayUtil
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.PagerFragmentAdapter
import com.zzzh.akhalteke_shipper.bean.FragmentInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.transport.fragment.HistoryFragment
import com.zzzh.akhalteke_shipper.ui.transport.fragment.OftenFragment
import com.zzzh.akhalteke_shipper.ui.transport.fragment.SendingFragment
import com.zzzh.akhalteke_shipper.utils.Constant
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.fragment_main3.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 首页 发货
 * @property mainView View
 * @property isCreate Boolean
 */
class Tab3Fragment : BaseFragment() {

    lateinit var mainView: View
    private var isCreate = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main3, null).also { mainView = it }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCreate = true
        initView()
        EventBus.getDefault().register(this)
        initViewPager()
        moveTip()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initData()
    }

    private fun initView() {
        fmain1_publish.setOnClickListener {
            routerTo.jumpToPublish()
        }
    }

    /**
     * 初始化viewpager
     */
    fun initViewPager() {
        var pagers: MutableList<FragmentInfo> = mutableListOf()
        val titleNames: Array<String> = arrayOf("发货中", "发货历史", "常发货源")

        pagers.add(FragmentInfo(SendingFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 0)
            arguments = bundle
        }, titleNames[0]))

        pagers.add(FragmentInfo(HistoryFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 1)
            arguments = bundle
        }, titleNames[1]))

        pagers.add(FragmentInfo(OftenFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 2)
            arguments = bundle
        }, titleNames[2]))

        fmain1_viewpager.offscreenPageLimit = 2
        fmain1_viewpager.adapter = PagerFragmentAdapter(fragmentManager!!, pagers)

        fmain1_smarttab.setViewPager(fmain1_viewpager)
        fmain1_smarttab.setDividerColors(R.color.main_line)

    }

    private fun initData() {
        if (isCreate && !isHidden) {

        }
    }

    /**
     * 发布货源的图标可以上下移动
     */
    private fun moveTip() {
        var downY = 0f
        var isMove = false
        val bottomP = DisplayUtil.dip2px(mContext, 60f)
        val topP = Constant.screenHeight - DisplayUtil.dip2px(mContext, 260f)
        fmain1_publish.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downY = event.rawY
                    isMove = false
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_MOVE -> {
                    var yDistance = event.rawY - downY
                    if (Math.abs(yDistance) > 10) {
                        isMove = true
                        fmain1_publish.translationY = fmain1_publish.translationY + yDistance
                        if (fmain1_publish.translationY > bottomP) {
                            fmain1_publish.translationY = bottomP.toFloat()
                        }

                        if (fmain1_publish.translationY + topP < 0) {
                            fmain1_publish.translationY = -topP.toFloat()
                        }
                    }
                    downY = event.rawY
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    if (!isMove) {
                        toCheckCre {
                            routerTo.jumpToPublish()
                        }
                    }
                    isMove = false
                    return@setOnTouchListener false
                }
                else -> {
                    return@setOnTouchListener false
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.message == MessageEvent.MESSAGEINFO) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}