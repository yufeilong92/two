package com.zzzh.akhalteke_shipper.ui.main_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.PagerFragmentAdapter
import com.zzzh.akhalteke_shipper.bean.FragmentInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.find.fragment.OrderFragment
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_main4.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 首页 运单
 */
class Tab4Fragment : BaseFragment() {

    lateinit var mainView: View
    private var isCreate = false //是否create界面

    private val mViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(activity!!).get(OrderViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main4, null).also { mainView = it }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCreate = true
        initView()
        EventBus.getDefault().register(this)
        initViewPager()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initData()
    }

    private fun initView() {
        fmain4_top.onClickTextButton {
            routerTo.callKeFu()
        }
    }

    private fun initData() {
        if (isCreate && !isHidden) {

        }
    }

    /**
     * 初始化viewpager
     */
    fun initViewPager() {
        var pagers: MutableList<FragmentInfo> = mutableListOf()
        val titleNames: Array<String> = arrayOf("运输中", "已完成", "已取消")

        pagers.add(FragmentInfo(OrderFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 0)
            arguments = bundle
        }, titleNames[0]))

        pagers.add(FragmentInfo(OrderFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 1)
            arguments = bundle
        }, titleNames[1]))

        pagers.add(FragmentInfo(OrderFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 2)
            arguments = bundle
        }, titleNames[2]))

        fmain4_pager.offscreenPageLimit = 2
        fmain4_pager.adapter = PagerFragmentAdapter(fragmentManager!!, pagers)

        fmain4_smart.setViewPager(fmain4_pager)
        fmain4_smart.setDividerColors(R.color.main_line)

        //第一个label添加数量
        mViewModel.label1.observe(this, Observer {
            val textView = fmain4_smart.getTabAt(0).findViewById<TextView>(R.id.tab_two_text)
            if (it == 0) {
                textView.text = "运输中"
            } else {
                textView.text = "运输中（${it!!}）"
            }
        })
        //第二个label添加数量
        mViewModel.label2.observe(this, Observer {
            val textView = fmain4_smart.getTabAt(1).findViewById<TextView>(R.id.tab_two_text)
            if (it == 0) {
                textView.text = "已完成"
            } else {
                textView.text = "已完成（${it!!}）"
            }
        })
        //第三个label添加数量
        mViewModel.label3.observe(this, Observer {
            val textView = fmain4_smart.getTabAt(2).findViewById<TextView>(R.id.tab_two_text)
            if (it == 0) {
                textView.text = "已取消"
            } else {
                textView.text = "已取消（${it!!}）"
            }
        })
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