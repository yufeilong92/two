package com.zzzh.akhalteke_shipper.ui.main_fragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.PagerFragmentAdapter
import com.zzzh.akhalteke_shipper.bean.FragmentInfo
import com.zzzh.akhalteke_shipper.bean.MessageEvent
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.find.fragment.FindCarFragment
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.FindCarViewModel
import com.zzzh.akhalteke_shipper.ui.login.AddressActivity
import com.zzzh.akhalteke_shipper.ui.publish.TruckChoiceActivity
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.fragment_main2.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 找车
 */
class Tab2Fragment : BaseFragment() {

    lateinit var mainView: View
    private var isCreate = false

    var type = ""
    var truckLs: ArrayList<StringInfo> = arrayListOf()//车长
    var truckCs: ArrayList<StringInfo> = arrayListOf()//车型

    private val findCarViewModel: FindCarViewModel by lazy {
        ViewModelProviders.of(activity!!).get(FindCarViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main2, null).also { mainView = it }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCreate = true
        initView()
        EventBus.getDefault().register(this)
        initViewPager()
    }

    /**
     * fragment 隐藏 和 显示
     * @param hidden Boolean
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initData()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        fmain2_toaddr.setOnClickListener {
        }
        //车型车长选择
        fmain2_totype.setOnClickListener {
            val intentBack = Intent()
            intentBack.setClass(mContext, TruckChoiceActivity::class.java)
            intentBack.putExtra("entry_temp", 2)
            intentBack.putExtra("truck_time", arrayListOf<String>())
            intentBack.putExtra("truck_leght", truckLs)
            intentBack.putExtra("truck_type", truckCs)
            startActivityForResult(intentBack, TruckChoiceActivity.DATABACK)
            activity!!.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
        }
    }

    /**
     * 初始化viewpager
     */
    fun initViewPager() {
        var pagers: MutableList<FragmentInfo> = mutableListOf()
        val titleNames: Array<String> = arrayOf("熟车", "车源")

        pagers.add(FragmentInfo(FindCarFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 0)
            arguments = bundle
        }, titleNames[0]))

        pagers.add(FragmentInfo(FindCarFragment().apply {
            val bundle = Bundle()
            bundle.putInt("status", 1)
            arguments = bundle
        }, titleNames[1]))

        fmain2_viewpager.offscreenPageLimit = 1
        fmain2_viewpager.adapter = PagerFragmentAdapter(fragmentManager!!, pagers)
        fmain2_viewpager.currentItem = 1
        fmain2_viewpager.offscreenPageLimit=2
        fmain2_smarttab.setViewPager(fmain2_viewpager)
        fmain2_smarttab.setDividerColors(R.color.main_line)
    }

    private fun initData() {
        if (isCreate && !isHidden) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AddressActivity.adrBackCode && resultCode == AddressActivity.adrBackCode) {
            data ?: return
            val proId = data!!.getIntExtra("pro_id", 0)
            val cityId = data!!.getIntExtra("city_id", 0)
            val areaId = data!!.getIntExtra("area_id", 0)

            val proName = data!!.getStringExtra("pro_name")
            val cityName = data!!.getStringExtra("city_name")
            val areaName = data!!.getStringExtra("area_name")

            when {
                proId == -1 -> {
                    fmain2_toaddr_text.text = "出发地"
                }
                cityId == -1 -> {
                    fmain2_toaddr_text.text = proName
                }
                areaId == -1 -> {
                    fmain2_toaddr_text.text = proName + cityName
                }
                else -> {
                    fmain2_toaddr_text.text = proName + cityName + areaName
                }
            }

        } else if (requestCode == TruckChoiceActivity.DATABACK && resultCode == TruckChoiceActivity.DATABACK) {
            data ?: return
            //返回车型车长
            type = data.getStringExtra("car_type")
            truckLs = data.getParcelableArrayListExtra("car_lengths")
            truckCs = data.getParcelableArrayListExtra("car_types")

            val strBuffer = StringBuffer()

//            when (type) {
//                "1" -> {
//                    strBuffer.append("整车  ")
//                }
//                "2" -> {
//                    strBuffer.append("零担  ")
//                }
//            }

            val clenght = StringBuffer()
            for (ls in truckLs) {
                strBuffer.append(ls.name + "/")
                clenght.append(ls.id + ",")
            }
            if (!strBuffer.isEmpty()) {
                strBuffer.deleteCharAt(strBuffer.length - 1)
            }
            if (!clenght.isEmpty()) {
                clenght.deleteCharAt(clenght.length - 1)
            }
            strBuffer.append("    ")

            val ctype = StringBuffer()
            for (ls in truckCs) {
                strBuffer.append(ls.name + "/")
                ctype.append(ls.id + ",")
            }
            if (!strBuffer.isEmpty()) {
                strBuffer.deleteCharAt(strBuffer.length - 1)
            }
            if (!ctype.isEmpty()) {
                ctype.deleteCharAt(ctype.length - 1)
            }

            val clt = "$clenght|$ctype"
            if (findCarViewModel.carLT.value != clt) {
                findCarViewModel.carLT.postValue("$clenght|$ctype")
            }

            if (strBuffer.isEmpty() || ToolUtils.isEmpty(strBuffer.toString().trim())) {
                fmain2_totype_text.text = "请选择车型车长"
            } else {
                fmain2_totype_text.text = strBuffer
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
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