package com.zzzh.akhalteke_shipper.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.yanzhenjie.permission.Permission
import com.zzzh.akhalteke_shipper.MainActivity
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.bean.AddressInfo
import com.zzzh.akhalteke_shipper.bean.GoodsDetailInfo
import com.zzzh.akhalteke_shipper.bean.OrderInfo
import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.ui.ShowImageActivity
import com.zzzh.akhalteke_shipper.ui.WebActivity
import com.zzzh.akhalteke_shipper.ui.find.*
import com.zzzh.akhalteke_shipper.ui.login.*
import com.zzzh.akhalteke_shipper.ui.login.CertificationActivity
import com.zzzh.akhalteke_shipper.ui.mine.SetActivity
import com.zzzh.akhalteke_shipper.ui.mine.address.AddressAddActivity
import com.zzzh.akhalteke_shipper.ui.mine.address.AddressManagerActivity
import com.zzzh.akhalteke_shipper.ui.mine.owners.*
import com.zzzh.akhalteke_shipper.ui.mine.wallet.BankManagerActivity
import com.zzzh.akhalteke_shipper.ui.mine.wallet.WithdrawActivity
import com.zzzh.akhalteke_shipper.ui.publish.ShipmentsActivity
import com.zzzh.akhalteke_shipper.ui.publish.TruckChoiceActivity
import com.zzzh.akhalteke_shipper.ui.transport.DriverDetailActivity
import com.zzzh.akhalteke_shipper.ui.transport.DriverNewListActivity
import com.zzzh.akhalteke_shipper.ui.transport.DriverSearchActivity
import com.zzzh.akhalteke_shipper.ui.transport.TransportNewDetailActivity


class RouterTo(var mContext: Context) {

    @SuppressLint("MissingPermission")
    fun callPhone(phoneNum: String) {
        PermissionUtils.showPermission(mContext as Activity, "打电话需要开通打电话权限，请允许", arrayOf(Permission.CALL_PHONE)) {
            //        Intent intent = new Intent(Intent.ACTION_DIAL);
            val intent = Intent(Intent.ACTION_DIAL)
//            val intent = Intent(Intent.ACTION_CALL)
            val data = Uri.parse("tel:$phoneNum")
            intent.data = data
            mContext.startActivity(intent)
            (mContext as Activity).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
        }
    }

    fun callKeFu() {
        callPhone("4001-567-168")
    }

    fun jumpTo(clazz: Class<*>) {
        val intentB = Intent()
        intentB.setClass(mContext, clazz)
        mContext.startActivity(intentB)
        (mContext as Activity).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }

    fun jumpTo(clazz: Class<*>, bundle: Bundle) {
        val intentB = Intent()
        intentB.setClass(mContext, clazz)
        intentB.putExtras(bundle)
        mContext.startActivity(intentB)
        (mContext as Activity).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }

    fun jumpToFoResult(clazz: Class<*>, resultCode: Int) {
        val intentB = Intent()
        intentB.setClass(mContext, clazz)
        (mContext as Activity).startActivityForResult(intentB, resultCode)
        (mContext as Activity).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }

    fun jumpToFoResult(clazz: Class<*>, bundle: Bundle, resultCode: Int) {
        val intentB = Intent()
        intentB.setClass(mContext, clazz)
        intentB.putExtras(bundle)
        (mContext as Activity).startActivityForResult(intentB, resultCode)
        (mContext as Activity).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }

    fun jumpToBU(clazz: Class<*>) {
        val intentB = Intent()
        intentB.setClass(mContext, clazz)
        mContext.startActivity(intentB)
        (mContext as Activity).overridePendingTransition(R.anim.push_buttom_in, R.anim.push_buttom_out)
    }

    fun jumpToBU(clazz: Class<*>, bundle: Bundle) {
        val intentB = Intent()
        intentB.setClass(mContext, clazz)
        intentB.putExtras(bundle)
        mContext.startActivity(intentB)
        (mContext as Activity).overridePendingTransition(R.anim.push_buttom_in, R.anim.push_buttom_out)
    }

    fun jumpToFoResulBU(clazz: Class<*>, resultCode: Int) {
        val intentB = Intent()
        intentB.setClass(mContext, clazz)
        (mContext as Activity).startActivityForResult(intentB, resultCode)
        (mContext as Activity).overridePendingTransition(R.anim.push_buttom_in, R.anim.push_buttom_out)
    }

    fun jumpToFoResultBU(clazz: Class<*>, bundle: Bundle, resultCode: Int) {
        val intentB = Intent()
        intentB.setClass(mContext, clazz)
        intentB.putExtras(bundle)
        (mContext as Activity).startActivityForResult(intentB, resultCode)
        (mContext as Activity).overridePendingTransition(R.anim.push_buttom_in, R.anim.push_buttom_out)
    }

    fun finishBase() {
        (mContext as Activity).finish()
        (mContext as Activity).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
    }

    /**
     * 跳转到首页运单界面
     */
    fun jumpToMainOrder() {
        val intentB = Intent()
        intentB.setClass(mContext, MainActivity::class.java)
        intentB.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intentB.putExtra("entry_temp", 3)
        mContext.startActivity(intentB)
        (mContext as Activity).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }

    /**
     * 跳转验证验证码页面
     */
    fun jumpToLoginCode(phone: String) {
        val bundle = Bundle()
        bundle.putString("phone", phone)
        jumpTo(LoginCodeActivity::class.java, bundle)
    }

    /**
     * 跳转到登录界面
     */
    fun jumpToLogin() {
        val intentB = Intent()
        intentB.setClass(mContext, LoginActivity::class.java)
        intentB.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(intentB)
        (mContext as Activity).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }

    /**
     * 跳转到登录界面，没有跳转动作
     */
    fun jumpToLoginNoTrans() {
        val intentB = Intent()
        intentB.setClass(mContext, LoginActivity::class.java)
        intentB.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(intentB)
    }

    fun jumpToMain() {
        val intentB = Intent()
        intentB.setClass(mContext, MainActivity::class.java)
        intentB.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        mContext.startActivity(intentB)
        (mContext as Activity).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }

    /**
     * 跳转验证验证码页面
     */
    fun jumpToWeb(entryTemp: Int, entryValue: String = "") {
        val bundle = Bundle()
        bundle.putInt("entry_temp", entryTemp)
        bundle.putString("entry_value", entryValue)
        jumpTo(WebActivity::class.java, bundle)
    }

    /**
     * 实名认证
     */
    fun jumpToCertification() {
        val bundle = Bundle()
//        bundle.putString("phone",phone)
        jumpTo(CertificationActivity::class.java, bundle)
    }

    fun jumpToAuthen() {
        val bundle = Bundle()
//        bundle.putString("phone",phone)
        jumpTo(AuthenNewActivity::class.java, bundle)
    }


    /**
     * 公司认证
     */
    fun jumpToCompanyCertification() {
        val bundle = Bundle()
//        bundle.putString("phone",phone)
        jumpTo(CompanyCertificationActivity::class.java, bundle)
    }

    /**
     * 选择地址
     */
    fun jumpToAddress(entryTemp: Int = 0) {
        val bundle = Bundle()
        bundle.putInt("entry_temp", entryTemp)
        jumpToFoResult(AddressActivity::class.java, bundle, AddressActivity.adrBackCode)
    }

    /**
     * 发布
     */
    fun jumpToPublish(detailInfo: GoodsDetailInfo = GoodsDetailInfo()) {
        val bundle = Bundle()
        bundle.putParcelable("detail_info", detailInfo)
        jumpTo(ShipmentsActivity::class.java, bundle)
    }

    /**
     * 车型，车长
     */
    fun jumpToTruckChoice(
        entryTemp: Int = 0,
        tPosition: Int = -1,
        truckTime: ArrayList<StringInfo> = arrayListOf(),
        truckLs: ArrayList<StringInfo> = arrayListOf(),
        truckType: ArrayList<StringInfo> = arrayListOf()
    ) {
        val bundle = Bundle()
        bundle.putInt("entry_temp", entryTemp)
        bundle.putInt("t_position", tPosition)
        bundle.putParcelableArrayList("truck_time", truckTime)
        bundle.putParcelableArrayList("truck_leght", truckLs)
        bundle.putParcelableArrayList("truck_type", truckType)
        jumpToFoResult(TruckChoiceActivity::class.java, bundle, TruckChoiceActivity.DATABACK)
    }

    /**
     * 指定的司机列表 1选指定司机，0传入字段进行指定司机
     */
    fun jumpToDriverList(entryTemp: Int, goodsId: String = "") {
        val bundle = Bundle()
        bundle.putInt("entry_temp", entryTemp)
        bundle.putString("goods_id", goodsId)
        jumpToFoResult(DriverNewListActivity::class.java, bundle, DriverNewListActivity.DATABACK)
    }

    /**
     * 货源详情
     * entryTemp 0发货中，1发货历史，2常用地址
     */
    fun jumpToTransportDetail(entryTemp: Int, goodsId: String) {
        val bundle = Bundle()
        bundle.putInt("entry_temp", entryTemp)
        bundle.putString("goods_id", goodsId)
        jumpTo(TransportNewDetailActivity::class.java, bundle)
    }

    /**
     * 搜索司机
     */
    fun jumpToDriverSearch() {
        val bundle = Bundle()
//        bundle.putString("phone",phone)
        jumpTo(DriverSearchActivity::class.java, bundle)
    }

    /**
     * 选择类型排序
     */
    fun jumpToSortLabel(tPosition: String) {
        val bundle = Bundle()
        bundle.putString("t_position", tPosition)
        jumpToFoResult(SortLabelActivity::class.java, bundle, SortLabelActivity.DATA_BACK)
    }

    /**
     * 签订协议
     */
    fun jumpToProtocolTo(entryTemp: Int = 0, orderInfo: OrderInfo = OrderInfo(), orderId: String = "") {
//        if(ToolUtils.isEmpty(orderInfo.totalMoney)){
//            orderInfo.totalMoney = ""
//        }
//        if(ToolUtils.isEmpty(orderInfo.carOwnerPlate)){
//            orderInfo.carOwnerPlate = ""
//        }
        val bundle = Bundle()
        bundle.putInt("entry_temp", entryTemp)
        bundle.putParcelable("order_info", orderInfo)
        bundle.putString("order_id", orderId)
        jumpTo(ProtocolNewToActivity::class.java, bundle)
    }

    /**
     * 签订协议
     */
    fun jumpToProtocol(orderId: String) {
        val bundle = Bundle()
        bundle.putString("order_id", orderId)
        jumpTo(ProtocolNewActivity::class.java, bundle)
    }

    /**
     * 设置页面
     */
    fun jumpToSet() {
        val bundle = Bundle()
//        bundle.putString("phone",phone)
        jumpTo(SetActivity::class.java, bundle)
    }

    /**
     * 批量上传照片
     */
    fun jumpToUploadImage() {
        val bundle = Bundle()
//        bundle.putString("phone",phone)
        jumpTo(UploadImageActivity::class.java, bundle)
    }

    /**
     * 选择类型排序
     */
    fun jumpToShowImage(tPosition: Int, imageList: ArrayList<StringInfo>) {
        val bundle = Bundle()
        bundle.putInt("data_position", tPosition)
        bundle.putParcelableArrayList("image_list", imageList)
        jumpToFoResult(ShowImageActivity::class.java, bundle, SortLabelActivity.DATA_BACK)
    }


    /**
     * 货主详情
     */
    fun jumpToOwnerDetail(ownerId: String) {
        val bundle = Bundle()
        bundle.putString("shipper_id", ownerId)
        jumpTo(OwnerNewDetailActivity::class.java, bundle)
    }

    /**
     * 货主详情
     */
    fun jumpToOwnerMoreList(ownerId: String) {
        val bundle = Bundle()
        bundle.putString("shipper_id", ownerId)
        jumpTo(OwnerMoreListActivity::class.java, bundle)
    }

    /**
     * 司机详情
     */
    fun jumpToDriverDetail(driverId: String) {
        val bundle = Bundle()
        bundle.putString("driver_id", driverId)
        jumpTo(DriverDetailActivity::class.java, bundle)
    }

    /**
     * 地址管理
     */
    fun jumpToAddressManager(entryTemp: Int = 0) {
        val bundle = Bundle()
        bundle.putInt("entry_temp", entryTemp)
        jumpToFoResult(AddressManagerActivity::class.java, bundle, AddressManagerActivity.BACK_DATA)
    }

    /**
     * 地址编辑
     * @entryTemp 0 添加，1 修改
     */
    fun jumpToAddressAdd(entryTemp: Int, adrInfo: AddressInfo = AddressInfo()) {
        val bundle = Bundle()
        bundle.putInt("entry_temp", entryTemp)
        bundle.putParcelable("address_info", adrInfo)
        jumpTo(AddressAddActivity::class.java, bundle)
    }

    /**
     * 订单详情
     */
    fun jumpToOrderDetail(orderId: String) {
        val bundle = Bundle()
        bundle.putString("order_id", orderId)
        jumpTo(OrderNewDetailActivity::class.java, bundle)
    }

    fun jumpToOrderCancel(orderId: String) {
        val bundle = Bundle()
        bundle.putString("order_id", orderId)
        jumpTo(OrderCancelActivity::class.java, bundle)
    }

    fun jumpToOrderPay(orderId: String, orderMoeny: String) {
        val bundle = Bundle()
        bundle.putString("order_id", orderId)
        bundle.putString("order_money", orderMoeny)
        jumpTo(OrderPayActivity::class.java, bundle)
    }

    fun jumpToOrderPay(start: String, end: String, time: String, orderId: String, orderMoeny: String) {
        val bundle = Bundle()
        bundle.putString("order_id", orderId)
        bundle.putString("order_money", orderMoeny)
        bundle.putString("create_time", time)
        bundle.putString("start_address", start)
        bundle.putString("end_address", end)
        jumpTo(OrderNewPayActivity::class.java, bundle)
    }

    fun jumpToOrderReceipt(orderId: String) {
        val bundle = Bundle()
        bundle.putString("order_id", orderId)
        jumpTo(OrderReceiptActivity::class.java, bundle)
    }

    fun jumpToDao() {
        val bundle = Bundle()
        jumpTo(DaoActivity::class.java, bundle)
    }

    fun jumpToDaoAuthen() {
        val bundle = Bundle()
        jumpTo(DaoNewAuthenActivity::class.java, bundle)
    }

    fun jumpToInvoice(orderIds: String, adrId: String, adrName: String, adrPhone: String, adrContent: String) {
        val bundle = Bundle()
        bundle.putString("order_ids", orderIds)
        bundle.putString("adr_id", adrId)
        bundle.putString("adr_name", adrName)
        bundle.putString("adr_phone", adrPhone)
        bundle.putString("adr_content", adrContent)
        jumpTo(InvoiceNewActivity::class.java, bundle)
    }

    fun jumpToInvoiceDetail(invoiceId: String) {
        val bundle = Bundle()
        bundle.putString("invoice_id", invoiceId)
        jumpTo(InvoiceNewDetailActivity::class.java, bundle)
    }

    /**
     * 跳转到资源详情
     * @param goodsId String
     */
    fun jumpToSourceDetail(goodsId: String) {
        val bundle = Bundle()
        bundle.putString("goods_id", goodsId)
        jumpTo(SourceNewDetailActivity::class.java, bundle)
    }



    /**
     * 到认证
     * @entryTemp 0 添加，1 修改
     */
    fun jumpToCertification(
        entryTemp: Int, dao: String = "",//
        businessLicense: String = "",//营业执照地址
        businessName: String = "",//页发票抬头
        businessNumber: String = "",//营业执照号
        businessAddress: String = ""//单位地址
    ) {
        val bundle = Bundle()
        bundle.putInt("entry_temp", entryTemp)
        bundle.putString("dao_str", dao)
        bundle.putString("bussiness_license", businessLicense)
        bundle.putString("bussiness_name", businessName)
        bundle.putString("bussiness_adr", businessAddress)
        bundle.putString("bussiness_number", businessNumber)
        jumpTo(com.zzzh.akhalteke_shipper.ui.mine.owners.CertificationActivity::class.java, bundle)
    }

    fun jumpToDaoInfo() {
        val bundle = Bundle()
        jumpTo(DaoNewInfoActivity::class.java, bundle)
    }


    /**
     * 转账
     */
    fun jumpToWithdraw() {
        val bundle = Bundle()
        jumpTo(WithdrawActivity::class.java, bundle)
    }

    /**
     * 银行卡管理
     */
    fun jumpToBankManager(entryTemp: Int = 0) {
        val bundle = Bundle()
        bundle.putInt("entry_temp", entryTemp)
        jumpTo(BankManagerActivity::class.java, bundle)
    }


}