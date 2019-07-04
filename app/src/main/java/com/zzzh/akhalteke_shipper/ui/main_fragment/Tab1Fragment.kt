package com.zzzh.akhalteke_shipper.ui.main_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.yanzhenjie.permission.Permission
import com.zzzh.akhalteke.weather.Observer.WeatherObserver
import com.zzzh.akhalteke_shipper.MainActivity
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.FindGoodsAdapter
import com.zzzh.akhalteke_shipper.adapter.LabelDeleteAdapter
import com.zzzh.akhalteke_shipper.bean.*
import com.zzzh.akhalteke_shipper.ui.BaseFragment
import com.zzzh.akhalteke_shipper.ui.find.SortLabelActivity
import com.zzzh.akhalteke_shipper.ui.find.viewmodel.GoodsViewModel
import com.zzzh.akhalteke_shipper.ui.login.AddressActivity
import com.zzzh.akhalteke_shipper.ui.publish.TruckChoiceActivity
import com.zzzh.akhalteke_shipper.utils.PermissionUtils
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import com.zzzh.akhalteke_shipper.view.dialog.CallCarOwnerDialog
import kotlinx.android.synthetic.main.cell_main_send_tab.view.*
import kotlinx.android.synthetic.main.fragment_main1.*
import kotlinx.android.synthetic.main.gm_weather_title_root.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 找货
 */
class Tab1Fragment : BaseFragment(), WeatherObserver {

    private val mViewModel by lazy {
        ViewModelProviders.of(this).get(GoodsViewModel::class.java)
    }

    private lateinit var mainView: View
    private var ifLoading = true // 是否需要显示加载
    private var isCreate = false

    private lateinit var fromText: TextView
    private lateinit var toText: TextView
    private lateinit var pxText: TextView
    private lateinit var sxText: TextView

    private var adrTemp = 0//0出发地，1目的地

    private var loadAreaCode = ""//发货地址码
    private var unloadAreaCode = ""//卸货地址码
    private var type = ""//车类型，暂时没用了
    private var truckTime = arrayListOf<StringInfo>()//装车时间
    private var truckLs = arrayListOf<StringInfo>()//车型
    private var truckCs = arrayListOf<StringInfo>()//车长
    private var sortStr = StringInfo()//排序，暂时没用到

    private var mAdapter: FindGoodsAdapter? = null //货源列表adapter
    private var infoList: MutableList<SourceInfo> = mutableListOf()//货源数据

    private var page = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main1, null).also { mainView = it }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCreate = true
        initView()
        EventBus.getDefault().register(this)
        initViewModel()
        tv_gm_weather_title.text = "找货"
        setWeatherOnClick(rl_gm_weather_root)


    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initData()
        if (GmContentVo.mWeatherVo == null || GmContentVo.mWeatherVo!!.cityInfo == null) return
        bindViewData(GmContentVo.mWeatherVo!!)
    }

    /**
     * 初始化页面
     */
    private fun initView() {
        //筛选器
        ftab1_filter.apply {
            fromText = cell_sendtab_text
            toText = cell_sendtab_to_text
            pxText = cell_sendtab_px_text
            sxText = cell_sendtab_sx_text

            //发货地址
            cell_sendtab_from.setOnClickListener {
                adrTemp = 0
                val intentAdr = Intent()
                intentAdr.setClass(mContext, AddressActivity::class.java)
                intentAdr.putExtra("entry_temp", 1)
                startActivityForResult(intentAdr, AddressActivity.adrBackCode)
                mContext.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
            }
            //收货地址
            cell_sendtab_to.setOnClickListener {
                adrTemp = 1
                val intentAdr = Intent()
                intentAdr.setClass(mContext, AddressActivity::class.java)
                intentAdr.putExtra("entry_temp", 1)
                startActivityForResult(intentAdr, AddressActivity.adrBackCode)
                mContext.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
            }
            //排序
            cell_sendtab_px.setOnClickListener {
                //                routerTo.jumpToSortLabel(sortStr.id)
            }
            //车型车长
            cell_sendtab_sx.setOnClickListener {
                val intentBack = Intent()
                intentBack.setClass(mContext, TruckChoiceActivity::class.java)
                intentBack.putExtra("entry_temp", 1)
                intentBack.putExtra("t_position", (ToolUtils.stringToIntM(type) - 1))
                intentBack.putExtra("truck_time", truckTime)
                intentBack.putExtra("truck_leght", truckLs)
                intentBack.putExtra("truck_type", truckCs)
                startActivityForResult(intentBack, TruckChoiceActivity.DATABACK)
                activity!!.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
            }
        }
        //下来刷新组件
        ftab1_recycle.layoutManager = LinearLayoutManager(mContext)
        ftab1_refresh.apply {
            setOnRefreshListener { refreshlayout ->
                submitData(0)
            }
            setOnLoadMoreListener { refreshlayout ->
                submitData(page + 1)
            }
            setEnableFooterFollowWhenLoadFinished(true)
            setEnableOverScrollDrag(true)
        }
        //清楚列表
        ftab1_clear.setOnClickListener {
            isShowFilter(false)
            type = ""
            truckTime.clear()
            truckLs.clear()
            truckCs.clear()
            ifLoading = true
            initData()
        }
    }

    /**
     * 请求数据
     */
    private fun initData() {
        if (isCreate && !isHidden) {
            submitData(0)
        }
    }

    override fun onStart() {
        super.onStart()
        PermissionUtils.showPermission(mContext, "APP需要定位权限，请允许", arrayOf(Permission.ACCESS_COARSE_LOCATION)) {
            if (GmContentVo.getWeatherVo() == null) {
                if (activity is MainActivity) {
                    val main: MainActivity = activity as MainActivity
                    main.getWeatherload(this)
                }
            }
        }
    }

    private fun initViewModel() {
        mViewModel.isShowProgress.observe(this, android.arch.lifecycle.Observer {
            when (it) {
                0 -> {
                    if (ifLoading) {
                        showProgress()
                    }
                }
                1 -> {
                    dismissProgress()
                    ftab1_refresh.finishLoadMore()
                    ftab1_refresh.finishRefresh()
                }
            }
        })
        //获取到数据列表
        mViewModel.goodsList.observe(this, Observer {
            ifLoading = false
            if (it!!.pageInfo.page == 0) {
                infoList.clear()
            }
            if (it!!.pageInfo.last) {
                ftab1_refresh.setEnableLoadMore(false)
            } else {
                ftab1_refresh.setEnableLoadMore(true)
            }
            page = it!!.pageInfo.page
            it!!.list?.apply {
                for (info in it!!.list) {
                    infoList.add(info)
                }
            }
            initAdapter()
        })
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        if (mAdapter == null) {
            ftab1_recycle.adapter = FindGoodsAdapter(mContext!!, infoList).apply {
                setOnItemClickListener { adapter, view, position ->
                    toCheckCre {
                        routerTo.jumpToSourceDetail(infoList[position].goodsId)
                    }

                }
                mAdapter = this
                setEmptyView(R.layout.cell_empty, ftab1_recycle)
                setOnItemChildClickListener { adapter, view, position ->
                    when (view.id) {
                        R.id.tv_item_findgood_phone -> {
                            toCheckInfo {
                                val dialog = CallCarOwnerDialog(mContext, infoList[position].shipperPhone)
                                dialog.show()
                                dialog.setReplaceText()
                            }
                        }
                        else -> {
                            childItemClick(infoList[position], view)
                        }
                    }
                }
            }
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     * 点击列表item
     * @param info SourceInfo
     * @param mView View
     */
    private fun childItemClick(info: SourceInfo, mView: View) {
        when (mView.id) {
            R.id.item_source_phone -> {
                toCheckInfo {
                    val dialog = CallCarOwnerDialog(mContext, info.shipperPhone)
                    dialog.show()
                    dialog.setReplaceText()
                }
            }
        }
    }

    /**
     * 筛选，请求数据
     * @param pageNo Int
     */
    private fun submitData(pageNo: Int) {
        val loadTime = StringBuffer()
        truckTime?.apply {
            for (timeStr in truckTime) {
                loadTime.append(timeStr.id)
                loadTime.append(",")
            }
            if (!loadTime.isEmpty()) {
                loadTime.deleteCharAt(loadTime.length - 1)
            }
        }
        val carLength = StringBuffer()
        truckLs?.apply {
            for (str in truckLs) {
                carLength.append(str.id)
                carLength.append(",")
            }
            if (!carLength.isEmpty()) {
                carLength.deleteCharAt(carLength.length - 1)
            }
        }
        val carType = StringBuffer()
        truckCs?.apply {
            for (str in truckCs) {
                carType.append(str.id)
                carType.append(",")
            }
            if (!carType.isEmpty()) {
                carType.deleteCharAt(carType.length - 1)
            }
        }

        if (type != "1" && type != "2") {
            type = ""
        }

        mViewModel.goodsNowList(
                loadAreaCode = loadAreaCode,
                unloadAreaCode = unloadAreaCode,
                type = type,
                loadTime = loadTime.toString(),
                carLength = carLength.toString(),
                carType = carType.toString(),
                page = pageNo
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddressActivity.adrBackCode && resultCode == AddressActivity.adrBackCode) {
            data ?: return
            val proId = data!!.getIntExtra("pro_id", 0)
            val cityId = data!!.getIntExtra("city_id", 0)
            val areaId = data!!.getIntExtra("area_id", 0)

            val proName = data!!.getStringExtra("pro_name")
            val cityName = data!!.getStringExtra("city_name")
            val areaName = data!!.getStringExtra("area_name")

            when (adrTemp) {
                0 -> {
                    when {
                        proId == -1 -> {
                            ftab1_filter.cell_sendtab_text.text = "出发地"
                            loadAreaCode = ""
                        }
                        cityId == -1 -> {
                            ftab1_filter.cell_sendtab_text.text = proName
                            loadAreaCode = proId.toString()
                        }
                        areaId == -1 -> {
                            ftab1_filter.cell_sendtab_text.text = cityName
                            loadAreaCode = cityId.toString()
                        }
                        else -> {
                            ftab1_filter.cell_sendtab_text.text = cityName
                            loadAreaCode = areaId.toString()
                        }
                    }
                }
                1 -> {
                    when {
                        proId == -1 -> {
                            toText.text = "目的地"
                            unloadAreaCode = ""
                        }
                        cityId == -1 -> {
                            toText.text = proName
                            unloadAreaCode = proId.toString()
                        }
                        areaId == -1 -> {
                            toText.text = cityName
                            unloadAreaCode = cityId.toString()
                        }
                        else -> {
                            toText.text = cityName
                            unloadAreaCode = areaId.toString()
                        }
                    }
                }
            }
            ifLoading = true
            initData()
        } else if (requestCode == TruckChoiceActivity.DATABACK && resultCode == TruckChoiceActivity.DATABACK) {
            data ?: return
            type = data.getStringExtra("car_type")
            truckTime = data.getParcelableArrayListExtra("car_times")
            truckLs = data.getParcelableArrayListExtra("car_lengths")
            truckCs = data.getParcelableArrayListExtra("car_types")

//            val strBuffer = StringBuffer()
//
//            when (type) {
//                "1" -> {
//                    strBuffer.append("整车")
//                }
//                "2" -> {
//                    strBuffer.append("零担")
//                }
//            }
//            for (ls in truckLs) {
//                strBuffer.append(ls.name + "/")
//            }
//            if(!strBuffer.isEmpty()){
//                strBuffer.deleteCharAt(strBuffer.length - 1)
//                strBuffer.append("    ")
//            }
//            for (ls in truckCs) {
//                strBuffer.append(ls.name + "/")
//            }
//            if(!strBuffer.isEmpty()){
//                strBuffer.deleteCharAt(strBuffer.length - 1)
//            }
//            if(strBuffer.isEmpty()|| strBuffer.toString().isEmpty()){
//                sxText.text = "筛选"
//            }else{
//                sxText.text = strBuffer
//            }
            val strInfos: MutableList<StringInfo> = mutableListOf()
            when (type) {
                "1" -> {
                    strInfos.add(StringInfo(id = "1", name = "整车"))
                }
                "2" -> {
                    strInfos.add(StringInfo(id = "1", name = "零担"))
                }
            }
            truckTime?.apply {
                if (this.size > 0) {
                    val sBuffer = StringBuffer()
                    for (sInfo in this) {
                        sBuffer.append(sInfo.name)
                        sBuffer.append("/")
                    }
                    sBuffer.deleteCharAt(sBuffer.length - 1)
                    strInfos.add(StringInfo(id = "2", name = sBuffer.toString()))
                }
            }

            truckLs?.apply {
                if (this.size > 0) {
                    val sBuffer = StringBuffer()
                    for (sInfo in this) {
                        sBuffer.append(sInfo.name)
                        sBuffer.append("/")
                    }
                    sBuffer.deleteCharAt(sBuffer.length - 1)
                    strInfos.add(StringInfo(id = "3", name = sBuffer.toString()))
                }
            }

            truckCs?.apply {
                if (this.size > 0) {
                    val sBuffer = StringBuffer()
                    for (sInfo in this) {
                        sBuffer.append(sInfo.name)
                        sBuffer.append("/")
                    }
                    sBuffer.deleteCharAt(sBuffer.length - 1)
                    strInfos.add(StringInfo(id = "4", name = sBuffer.toString()))
                }
            }

            if (strInfos.size == 0) {
                isShowFilter(false)
            } else {
                isShowFilter(true)
                val adapter = object : LabelDeleteAdapter(
                        mContext, strInfos
                ) {
                    override fun clickItem(item: StringInfo) {
                        strInfos.remove(item)
                        when (item.id) {
                            "1" -> {
                                type = ""
                            }
                            "2" -> {
                                truckTime.clear()
                            }
                            "3" -> {
                                truckLs.clear()
                            }
                            "4" -> {
                                truckCs.clear()
                            }
                        }
                        ftab1_flow.toSetAdapter(this)
                        if (strInfos.size == 0) {
                            isShowFilter(false)
                        }
                        ifLoading = true
                        initData()
                    }
                }
                ftab1_flow.toSetAdapter(adapter)
            }
            ifLoading = true
            initData()
        } else if (SortLabelActivity.DATA_BACK == requestCode && resultCode == SortLabelActivity.DATA_BACK) {
            data ?: return
            sortStr = data.getParcelableExtra("data_string")
            pxText.text = sortStr.name
            ifLoading = true
            initData()
        }
    }

    /**
     * 是否显示车长车型，小item
     * @param isShow Boolean  true显示，false不显示
     */
    private fun isShowFilter(isShow: Boolean) {
        if (isShow) {
            ftab1_clear.visibility = View.VISIBLE
            ftab1_flow.visibility = View.VISIBLE
//            ftab1_space.visibility = View.GONE

        } else {
            ftab1_clear.visibility = View.GONE
            ftab1_flow.visibility = View.GONE
//            ftab1_space.visibility = View.VISIBLE
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

    override fun upDataWeather() {
        val vo = GmContentVo.getWeatherVo() ?: return
        if (vo.cityInfo == null) return
        GmContentVo.setWeatherVo(vo)
        bindViewData(vo)
    }

    fun bindViewData(data: WeatherVo) {
        ll_weather_root.visibility = View.VISIBLE
        val info = data.cityInfo
        val vo = data.now
        val f1 = data.f1
        tv_gm_waether_max_t.text = f1.day_air_temperature
        tv_gm_waether_min_t.text = f1.night_air_temperature
        if (info.c3 == info.c5) {
            tv_gm_weather_city.visibility = View.GONE
        } else {
            tv_gm_weather_city.visibility = View.VISIBLE
        }
        tv_gm_weather_city.text = info.c5
        tv_gm_weather_couny.text = info.c3
        tv_gm_weather_wea.text = vo.weather

    }

}