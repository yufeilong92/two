package com.zzzh.akhalteke_shipper.ui.find

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.baidu.mapapi.map.InfoWindow
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.model.LatLng
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.DriverPostionVo
import com.zzzh.akhalteke_shipper.mvp.contract.DriverPostionContract
import com.zzzh.akhalteke_shipper.mvp.model.DriverPostionModel
import com.zzzh.akhalteke_shipper.mvp.presenter.DriverPresenter
import com.zzzh.akhalteke_shipper.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_bai_du_map.*
import kotlinx.android.synthetic.main.gm_title_layout.*


class BaiDuMapActivity : BaseActivity(), DriverPostionContract.View {
    private var mDriverId: String? = null
    private var mPlateId: String? = null

    companion object {
        val DRIVER_ID: String = "driverid"
        val PLATE_ID: String = "cartid"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bai_du_map)
        gm_tv_activity_title.text = "地位"
        if (intent != null) {
            mDriverId = intent.getStringExtra(DRIVER_ID)
            mPlateId = intent.getStringExtra(PLATE_ID)
        }
        initRequestDriverPostion()

    }

    private fun showMap(show: Boolean) {
        bmapview.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun initRequestDriverPostion() {
        val mPresnter = DriverPresenter()
        mPresnter.initMvp(this, DriverPostionModel())
        showProgress()
        mPresnter.requestDriverPostion(mContext, mDriverId!!)
    }

    private fun initDataBaiDu(lat: Double, lng: Double) {
        //缩放
        val builder = MapStatus.Builder()
        builder.zoom(15.0f)
        bmapview.map.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
        //以谁为中心
//        val LOCATION = LatLng(34.721304, 113.747491)
        val LOCATION = LatLng(lat, lng)
        val status = MapStatusUpdateFactory.newLatLng(LOCATION)
        bmapview.map.setMapStatus(status)
        //画图标
        val point = LatLng(lat, lng)
        //构建Marker图标
        //        val bitmap = BitmapDescriptorFactory
        //                .fromResource(R.drawable.ic_baidu_logo)
        //        val option = MarkerOptions()
        //                .position(point)
        //                .icon(bitmap)
        //        bmapview.map.addOverlay(option)
        var buttom = Button(this@BaiDuMapActivity)
        buttom.setText(mPlateId)
        val drawable = resources.getDrawable(R.drawable.ic_baidu_logo)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        buttom.setCompoundDrawables(null, null, null, drawable)
        val infomwindow = InfoWindow(buttom, point, -100)
        bmapview.map.showInfoWindow(infomwindow)
    }

    override fun onPause() {
        super.onPause()
        bmapview.onPause()
    }

    override fun onResume() {
        super.onResume()
        bmapview.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        bmapview.onDestroy()
    }

    override fun Success(t: Any?) {
        val data = t as DriverPostionVo
        if (data.latitude == null||data.latitude=="") return
        showMap(true)
        initDataBaiDu(data.latitude.toDouble(),data.longitude.toDouble())
    }

    override fun Error(ex: Throwable) {
        showToast("网络异常，请稍后重试")
        dismissProgress()
    }

    override fun Complise() {
        dismissProgress()
    }
}
