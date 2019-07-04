package com.zzzh.akhalteke_shipper.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.adapter.AdrCityAdapter
import com.zzzh.akhalteke_shipper.adapter.AdrProAdapter
import com.zzzh.akhalteke_shipper.realm.AddressBean
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import com.zzzh.akhalteke_shipper.ui.login.viewmodel.AddressViewModel
import kotlinx.android.synthetic.main.activity_address.*

/**
 * 地址三级联动
 */
class AddressActivity : BaseActivity() {

    companion object {
        const val adrBackCode = 2101  //地址的返回码
    }

    private val addressViewModel: AddressViewModel by lazy {
        ViewModelProviders.of(this).get(AddressViewModel::class.java)
    }

    var entryTemp = 0       //0普通进入，1进入有全国选项，2进入有全市选项

    private var adrProAdapter: AdrProAdapter? = null //省adapter
    private var cityAdapter: AdrCityAdapter? = null  //市adapter
    private var disAdapter: AdrCityAdapter? = null   //区adapter

    private var adrList: MutableList<AddressBean> = mutableListOf()//省列表
    private var cityList: MutableList<AddressBean> = mutableListOf()//市列表
    private var disList: MutableList<AddressBean> = mutableListOf()//区列表

    private var proId = 0//省id
    private var proName = ""//省名字
    private var cityId = 0//市id
    private var cityName = ""//市名称
    private var areaId = 0//区县id
    private var areaName = ""//区县名称

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        entryTemp = intent.getIntExtra("entry_temp",0)

        initView()
        initViewModel()
    }

    /**
     * 初始化页面
     */
    private fun initView() {
        addressViewModel.toGetData(0, 0)

        address_pro.layoutManager = LinearLayoutManager(mContext)
        address_city.layoutManager = LinearLayoutManager(mContext)
        address_dis.layoutManager = LinearLayoutManager(mContext)
    }

    /**
     * 初始化viewmodel
     */
    private fun initViewModel() {
        addressViewModel.isShowProgress.observe(this, Observer<Int> {
            when (it) {
                0 -> {
                    showProgress()
                }
                1 -> {
                    dismissProgress()
                }
            }
        })

        //获取省份列表并填充数据
        addressViewModel.proLists.observe(this, Observer {
            adrProAdapter = null
            when (entryTemp) {
                0, 2 -> {
                    adrList = it!!
                }
                1 -> {
                    adrList.clear()
                    adrList.add(
                        AddressBean(
                            id = -1,
                            area_name = "全国",
                            pid = -1,
                            position = -1
                        )
                    )
                    for (adr in it!!) {
                        adrList.add(adr)
                    }
                }
            }
            initProAdapter()
        })

        //获取市列表并填充数据
        addressViewModel.cityLists.observe(this, Observer {
            cityAdapter = null
            when (entryTemp) {
                0, 2 -> {
                    cityList = it!!
                }
                1 -> {
                    cityList.clear()
                    cityList.add(
                        AddressBean(
                            id = -1,
                            area_name = "全省",
                            pid = -1,
                            position = -1
                        )
                    )
                    for (adr in it!!) {
                        cityList.add(adr)
                    }
                }
            }

            initCityAdapter()
        })

        //获取区县列表并填充数据
        addressViewModel.disLists.observe(this, Observer {
            disAdapter = null
            when (entryTemp) {
                0 -> {
                    disList = it!!
                }
                1, 2 -> {
                    disList.clear()
                    disList.add(
                        AddressBean(
                            id = -1,
                            area_name = "全市",
                            pid = -1,
                            position = -1
                        )
                    )
                    for (adr in it!!) {
                        disList.add(adr)
                    }
                }
            }
            initDisAdapter()
        })
    }

    /**
     * 初始化省adapter
     */
    private fun initProAdapter() {
        if (adrProAdapter == null) {
            adrProAdapter = AdrProAdapter(mContext, adrList).apply {
                setOnItemClickListener { adapter, view, position ->
                    val item = adrList[position]
                    when (entryTemp) {
                        0, 2 -> {
                            val item = adrList[position]
                            addressViewModel.toGetData(item.id, 1)
                            toSetPosition(position)
                            disList.clear()
                            initDisAdapter()

                            proId = item.id
                            proName = item.area_name
                            cityId = 0
                            cityName = ""
                            areaId = 0
                            areaName = ""
                        }
                        1 -> {
                            if (item.id == -1) {
                                proId = -1
                                proName = "全国"
                                cityId = 0
                                cityName = ""
                                areaId = 0
                                areaName = ""
                                callBackIntent()
                            } else {
                                val item = adrList[position]
                                addressViewModel.toGetData(item.id, 1)
                                toSetPosition(position)
                                disList.clear()
                                initDisAdapter()

                                proId = item.id
                                proName = item.area_name
                                cityId = 0
                                cityName = ""
                                areaId = 0
                                areaName = ""
                            }
                        }
                    }
                }
            }
            address_pro.adapter = adrProAdapter
        } else {
            adrProAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     * 初始化市adapter
     */
    private fun initCityAdapter() {
        if (cityAdapter == null) {
            cityAdapter = AdrCityAdapter(mContext, cityList).apply {
                setOnItemClickListener { adapter, view, position ->
                    val item = cityList[position]
                    when (entryTemp) {
                        0 -> {
                            addressViewModel.toGetData(item.id, 2)
                            toSetPosition(position)

                            cityId = item.id
                            cityName = item.area_name
                            areaId = 0
                            areaName = ""
                        }
                        1, 2 -> {
                            if (item.id == -1) {
                                cityId = -1
                                cityName = "全省"
                                areaId = 0
                                areaName = ""
                                callBackIntent()
                            } else {
                                addressViewModel.toGetData(item.id, 2)
                                toSetPosition(position)

                                cityId = item.id
                                cityName = item.area_name
                                areaId = 0
                                areaName = ""
                            }
                        }
                    }
                }
            }
            address_city.adapter = cityAdapter
        } else {
            cityAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     * 初始化区县adapter
     */
    private fun initDisAdapter() {
        if (disAdapter == null) {
            disAdapter = AdrCityAdapter(mContext, disList).apply {
                setOnItemClickListener { adapter, view, position ->
                    val item = disList[position]

                    when (entryTemp) {
                        0 -> {
                            toSetPosition(position)
                            areaId = item.id
                            areaName = item.area_name

                            callBackIntent()
                        }
                        1, 2 -> {
                            if (item.id == -1) {
                                areaId = -1
                                areaName = "全市"
                                callBackIntent()
                            } else {
                                toSetPosition(position)
                                areaId = item.id
                                areaName = item.area_name

                                callBackIntent()
                            }
                        }
                    }
                }
            }
            address_dis.adapter = disAdapter
        } else {
            disAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     * 返回数据处理
     */
    private fun callBackIntent() {
        val intentBack = Intent()
        intentBack.putExtra("pro_id", proId)
        intentBack.putExtra("pro_name", proName)
        intentBack.putExtra("city_id", cityId)
        intentBack.putExtra("city_name", cityName)
        intentBack.putExtra("area_id", areaId)
        intentBack.putExtra("area_name", areaName)
        setResult(adrBackCode, intentBack)
        finishBase()
    }
}
