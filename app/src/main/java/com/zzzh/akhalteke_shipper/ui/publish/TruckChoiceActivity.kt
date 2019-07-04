package com.zzzh.akhalteke_shipper.ui.publish

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.LabelTextAdapter
import com.zzzh.akhalteke_shipper.bean.CarLengthInfo
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.publish.viewmodel.TruckChoiceViewModel
import com.zzzh.akhalteke_shipper.utils.FullyGridLayoutManager
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import kotlinx.android.synthetic.main.activity_truck_choice.*

/**
 * 选择车长，车型
 */
class TruckChoiceActivity : BaseActivity() {

    companion object {
        const val DATABACK = 2102
    }

    var entryTemp = 0//0、没有时间其他三个都有，1、4项都有，2、没有 用车类型和时间

    val truckChoiceViewModel: TruckChoiceViewModel by lazy {
        ViewModelProviders.of(this).get(TruckChoiceViewModel::class.java)
    }

    //用车类型adapter
    private var labelAdapterT: LabelTextAdapter? = null
    //用车时间adapter
    private var labelAdapterTime: LabelTextAdapter? = null
    //车长adapter
    private var labelAdapterL: LabelTextAdapter? = null
    //车型adapter
    private var labelAdapterC: LabelTextAdapter? = null

    //用车类型list，固定的，初始化就赋值
    private var labelTs: MutableList<StringInfo> = mutableListOf(
        StringInfo(id = "1", name = "整车", temp = 0),
        StringInfo(id = "2", name = "零担", temp = 0)
    )

    //用车时间list，固定的，初始化就赋值
    private var labelTime: MutableList<StringInfo> = mutableListOf(
        StringInfo(id = "1", name = "今天", temp = 0),
        StringInfo(id = "2", name = "明天", temp = 0),
        StringInfo(id = "3", name = "明天以后", temp = 0)
    )

    //车长list
    private var labelLs: MutableList<StringInfo> = mutableListOf()
    //车型list
    private var labelCs: MutableList<StringInfo> = mutableListOf()

    var tposition = -1//用车类型的选择项

    var truckTime = arrayListOf<StringInfo>()//用车时间的选中项
    var truckLs = arrayListOf<StringInfo>()//车长的选中项
    var truckCs = arrayListOf<StringInfo>()//车型的选中项

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truck_choice)

        entryTemp = intent.getIntExtra("entry_temp",0)
        tposition = intent.getIntExtra("t_position",-1)
        truckTime = intent.getParcelableArrayListExtra("truck_time")
        truckLs = intent.getParcelableArrayListExtra("truck_leght")
        truckCs = intent.getParcelableArrayListExtra("truck_type")

        initData()
        initView()
        initViewModel()
        truckChoiceViewModel.togetData()
    }

    /**
     * 初始化数据，时间，车长，车型，用车类型
     */
    private fun initData() {
        if (truckTime == null) {
            truckTime = arrayListOf<StringInfo>()
        }
        if (truckLs == null) {
            truckLs = arrayListOf<StringInfo>()
        }
        if (truckCs == null) {
            truckCs = arrayListOf<StringInfo>()
        }
        if (tposition > 1 || tposition < 0) {
            tposition = -1
        }
        if (tposition == 1) {
            labelTs[0].temp = 0
            labelTs[1].temp = 1
        }
        for (tt in truckTime) {
            val tl = (ToolUtils.stringToIntM(tt.id) - 1)
            if (tl in 0..2) {
                labelTime[tl].temp = 1
            }
        }
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        truck_choice_all.layoutManager = FullyGridLayoutManager(mContext, 4)
        truck_choice_long.layoutManager = FullyGridLayoutManager(mContext, 4)
        truck_choice_cag.layoutManager = FullyGridLayoutManager(mContext, 4)
        truck_choice_time.layoutManager = FullyGridLayoutManager(mContext, 4)

        initTAdapter()
        when (entryTemp) {//0、没有时间其他三个都有，1、4项都有，2、没有 用车类型和时间
            0 -> {
                truck_choice_time_layout.visibility = View.GONE
            }
            1 -> {
                truck_choice_time_layout.visibility = View.VISIBLE
                initTimeAdapter()
            }
            2 -> {
                truck_choice_time_layout.visibility = View.GONE
            }
        }

        //确定按钮
        truck_choice_button.setOnClickListener {
            toCallBackIntent()
        }
        //取消按钮，清空所有选项
        truck_choice_cancel.setOnClickListener {
            truckLs.clear()
            truckCs.clear()
            tposition = -1
            for (cs in labelCs) {
                if (cs.temp == 1) {
                    cs.temp = 0
                }
            }
            for (ls in labelLs) {
                if (ls.temp == 1) {
                    ls.temp = 0
                }
            }
            labelTs[0].temp = 0
            labelTs[1].temp = 0
            initTAdapter()
            initLAdapter()
            initCAdapter()
            when (entryTemp) {
                1 -> {
                    truckTime.clear()
                    for (ls in labelTime) {
                        if (ls.temp == 1) {
                            ls.temp = 0
                        }
                    }
                    initTimeAdapter()
                }
            }
        }
    }

    private fun initViewModel() {
        truckChoiceViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })
        //获取车长，车型
        truckChoiceViewModel.dataBack.observe(this, Observer {
            truck_choice_view01.visibility = View.VISIBLE
            for (data in it!!) {
                when (data) {
                    is StringInfo -> {
                        if (truckCs.size > 0) {
                            for (ts in truckCs) {
                                if (data.id == ts.id) {
                                    data.temp = 1
                                }
                            }
                        }
                        labelCs.add(data)
                        initCAdapter()
                    }
                    is CarLengthInfo -> {
                        val strLS = StringInfo(id = data.id, name = data.length)
                        if (truckLs.size > 0) {
                            for (ts in truckLs) {
                                if (data.id == ts.id) {
                                    strLS.temp = 1
                                }
                            }
                        }
                        labelLs.add(strLS)
                        initLAdapter()
                    }
                }
            }
        })
    }


    /**
     * 所用车得类型
     */
    private fun initTAdapter() {
        if (labelAdapterT == null) {
            labelAdapterT = LabelTextAdapter(mContext, labelTs).apply {
                setOnItemClickListener { adapter, view, position ->
                    var strInfo = labelTs[position]
                    if (position != tposition) {
                        strInfo.temp = 1
                        if (tposition in 0..1) {
                            labelTs[tposition].temp = 0
                        }
                        notifyDataSetChanged()
                        tposition = position
                    }
                }
            }
            truck_choice_all.adapter = labelAdapterT
        } else {
            labelAdapterT!!.notifyDataSetChanged()
        }
    }

    /**
     * 时间
     */
    private fun initTimeAdapter() {
        if (labelAdapterTime == null) {
            labelAdapterTime = LabelTextAdapter(mContext, labelTime).apply {
                setOnItemClickListener { adapter, view, position ->
                    var strInfo = labelTime[position]

                    for (tL in truckTime) {
                        if (strInfo.id == tL.id) {
                            strInfo.temp = 0
                            notifyDataSetChanged()
                            truckTime.remove(tL)
                            return@setOnItemClickListener
                        }
                    }

                    if (truckTime.size < 3) {
                        strInfo.temp = 1
                        truckTime.add(strInfo)
                        notifyDataSetChanged()
                    } else {
                        showToast("最多选择三个")
                    }

                }
            }
            truck_choice_time.adapter = labelAdapterTime
        } else {
            labelAdapterTime!!.notifyDataSetChanged()
        }
    }

    /**
     * 车长
     */
    private fun initLAdapter() {
        if (labelAdapterL == null) {
            labelAdapterL = LabelTextAdapter(mContext, labelLs).apply {
                setOnItemClickListener { adapter, view, position ->
                    var strInfo = labelLs[position]

                    for (tL in truckLs) {
                        if (strInfo.id == tL.id) {
                            strInfo.temp = 0
                            notifyDataSetChanged()
                            truckLs.remove(tL)
                            return@setOnItemClickListener
                        }
                    }

                    if (truckLs.size < 3) {
                        strInfo.temp = 1
                        truckLs.add(strInfo)
                        notifyDataSetChanged()
                    } else {
                        showToast("最多选择三个")
                    }

                }
            }
            truck_choice_long.adapter = labelAdapterL
        } else {
            labelAdapterL!!.notifyDataSetChanged()
        }
    }

    /**
     * 车得类型
     */
    private fun initCAdapter() {
        if (labelAdapterC == null) {
            labelAdapterC = LabelTextAdapter(mContext, labelCs).apply {
                setOnItemClickListener { adapter, view, position ->
                    var strInfo = labelCs[position]
                    for (tL in truckCs) {
                        if (strInfo.id == tL.id) {
                            strInfo.temp = 0
                            notifyDataSetChanged()
                            truckCs.remove(tL)
                            return@setOnItemClickListener
                        }
                    }
                    if (truckCs.size < 3) {
                        strInfo.temp = 1
                        truckCs.add(strInfo)
                        notifyDataSetChanged()
                    } else {
                        showToast("最多选择三个")
                    }
                }
            }
            truck_choice_cag.adapter = labelAdapterC
        } else {
            labelAdapterC!!.notifyDataSetChanged()
        }
    }

    /**
     * 返回数据
     */
    fun toCallBackIntent() {
//        when(entryTemp){
//            1->{
//                if(tposition == -1){
//                    showToast("请选择用车类型")
//                    return
//                }
//
//                if (truckLs.size == 0) {
//                    showToast("请选择车长")
//                    return
//                }
//                if (truckCs.size == 0) {
//                    showToast("请选择车型")
//                    return
//                }
//            }
//        }
        val intentBack = Intent()
        intentBack.putExtra("car_type", (tposition + 1).toString())
        intentBack.putExtra("car_lengths", truckLs)
        when (entryTemp) {
            1 -> {
                intentBack.putExtra("car_times", truckTime)
            }
        }
        intentBack.putExtra("car_types", truckCs)
        setResult(DATABACK, intentBack)
        finishBase()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
