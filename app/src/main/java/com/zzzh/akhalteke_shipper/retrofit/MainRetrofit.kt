package com.zzzh.akhalteke_shipper.retrofit

import com.lipo.utils.SystemUtil
import com.zzzh.akhalteke_shipper.bean.*
import com.zzzh.akhalteke_shipper.retrofit.gsonFactory.BaseEntity
import com.zzzh.akhalteke_shipper.utils.Constant
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * Created by apple on 2018/7/15.
 */
interface MainRetrofit {

    @GET("androidShipperVersion.json")//获取版本信息
    fun shipperVersion(
    ): Observable<BaseEntity<String>>

    @Multipart
    @POST("appUser/n/attach/uploadFile")//上传
    fun uploadImage(
            @Part partList: MutableList<MultipartBody.Part>
    ): Observable<BaseEntity<String>>

    @Multipart
    @POST("shipper/portrait/update")//上传头像
    fun uploadPortrait(
            @Part partList: MutableList<MultipartBody.Part>
    ): Observable<BaseEntity<String>>

    @Multipart
    @POST("auth/shipper/idCard/face")//上传正面图片
    fun uploadFaceImage(
            @Part partList: MutableList<MultipartBody.Part>
    ): Observable<BaseEntity<IDImageAuthenInfo>>


    @Multipart
    @POST("auth/shipper/idCard/back")//上传反面图片
    fun uploadBackImage(
            @Part partList: MutableList<MultipartBody.Part>
    ): Observable<BaseEntity<IDImageAuthenInfo>>

    @Multipart
    @POST("auth/shipper/realName")//实人认证
    fun realName(
            @Part partList: MutableList<MultipartBody.Part>
    ): Observable<BaseEntity<UserInfo>>

    @Multipart
    @POST("auth/shipper/business")//识别营业执照
    fun authBussiness(
            @Part partList: MutableList<MultipartBody.Part>
    ): Observable<BaseEntity<BussinessInfo>>

    @FormUrlEncoded
    @POST("login/shipper/getCode")//获取验证码
    fun toGetCode(
            @Field("phone") phone: String
    ): Observable<BaseEntity<String>>

//    @FormUrlEncoded
//    @POST("test")//获取验证码
//    fun test(
//        @Field("id") id: String = "12",
//        @Field("iname") iname: String = "验证码",
//        @Field("iage") iage: String = "23"
//    ): Observable<BaseEntity<String>>

    /**
     * 登陆
     */
    @FormUrlEncoded
    @POST("login/shipper/checkCode")//对上传的验证码进行校验
    fun checkCode(
            @Field("phone") phone: String,
            @Field("code") code: String,
            @Field("phoneType") phoneType: String = "1",
            @Field("brand") brand: String = SystemUtil.getDeviceBrand(),
            @Field("model") model: String = SystemUtil.getSystemModel()
    ): Observable<BaseEntity<UserInfo>>

    @FormUrlEncoded
    @POST("auth/shipper/realName")//实名认证
    fun authRealName(
            @Field("id") id: String,
            @Field("iDnumber") iDnumber: String,
            @Field("iDAddress") iDAddress: String,
            @Field("iDAddressReverse") iDAddressReverse: String,
            @Field("portrait") portrait: String,
            @Field("name") name: String
    ): Observable<BaseEntity<UserInfo>>

    @FormUrlEncoded
    @POST("auth/shipper/com")//分为有营业执照和无营业执照两种
    fun authCompany(
            @Field("id") id: String,
            @Field("ifBusinessLicense") ifBusinessLicense: String,
            @Field("corporateName") corporateName: String,
            @Field("businessLicenseId") businessLicenseId: String,
            @Field("businessLicense") businessLicense: String,
            @Field("doorPhotos") doorPhotos: String,
            @Field("businessCard") businessCard: String,
            @Field("areaCode") areaCode: String,
            @Field("detailedAddress") detailedAddress: String
    ): Observable<BaseEntity<UserInfo>>

    @FormUrlEncoded
    @POST("shipper/carType/list")//获取车型列表
    fun carTypeList(
            @Field("id") id: String = Constant.userInfo.id
    ): Observable<BaseEntity<MutableList<StringInfo>>>

    @FormUrlEncoded
    @POST("shipper/carLength/list")//获取车长列表
    fun carLengthList(
            @Field("id") id: String = Constant.userInfo.id
    ): Observable<BaseEntity<MutableList<CarLengthInfo>>>

    @FormUrlEncoded
    @POST("goods/shipper/getFamiliarCarList")//获取熟车列表
    fun getFamiliarCarList(
            @Field("id") id: String = Constant.userInfo.id,
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("page") page: Int
    ): Observable<BaseEntity<PageInfo<DriverInfo>>>

    @FormUrlEncoded
    @POST("shipper/carList")//货主找车可以根据车长车型筛选
    fun getFamiliarCarList(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("ifFamiliar") ifFamiliar: String = "1",//查找是否熟车列表，1-是，2-否
            @Field("screen") screen: String,//筛选标识，1-有筛选条件，2-为没有筛选条件
            @Field("carLengthList") carLengthList: String,//车长主键使用逗号相连，筛选标识为1时（不选为全部）不能为空
            @Field("carTypeList") carTypeList: String,//车型主键用逗号相连，筛选标识为1时（不选为全部）不能为空
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("page") page: Int
    ): Observable<BaseEntity<PageInfo<DriverInfo>>>

    @FormUrlEncoded
    @POST("goods/init")//发布货源信息
    fun publish(
            @Field("id") id: String = Constant.userInfo.id,
            @Field("loadAreaCode") loadAreaCode: String,
            @Field("loadAddress") loadAddress: String,
            @Field("unloadAreaCode") unloadAreaCode: String,
            @Field("unloadAddress") unloadAddress: String,
            @Field("carLengthId1") carLengthId1: String,
            @Field("carLengthId2") carLengthId2: String,
            @Field("carLengthId3") carLengthId3: String,
            @Field("carTypeId1") carTypeId1: String,
            @Field("carTypeId2") carTypeId2: String,
            @Field("carTypeId3") carTypeId3: String,
//            @Field("type") type: String,
            @Field("weight") weight: String,
            @Field("volume") volume: String,
            @Field("name") name: String,
            @Field("loadTime") loadTime: String,
            @Field("loadType") loadType: String,
            @Field("payType") payType: String,
            @Field("cost") cost: String,
            @Field("comments") comments: String,
            @Field("ifDriver") ifDriver: String,
            @Field("carOwnerId") carOwnerId: String,
            @Field("ifGoodsOften") ifGoodsOften: String
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("goods/shipper/getOwnerGoods")//获取货主自身发布货源
    fun getOwnerGoods(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("isRelease") isRelease: String,//1-发布中，2-发布历史
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("page") page: Int
    ): Observable<BaseEntity<PageInfo<SendGoodsInfo>>>


    @FormUrlEncoded
    @POST("shipper/sendInvitationSms")//发送邀请短息
    fun sendInvitationSms(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("phone") phone: String
    ): Observable<BaseEntity<String>>


    @FormUrlEncoded
    @POST("goods/getOftenList")//获取常发货源列表
    fun getOftenList(
            @Field("id") id: String = Constant.userInfo.id,
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("page") page: Int
    ): Observable<BaseEntity<PageInfo<GoodsDetailInfo>>>

    @FormUrlEncoded
    @POST("goods/shipper/get")//查看货源详情（发货中）
    fun goodsDetails(
            @Field("id") id: String = Constant.userInfo.id,
            @Field("goodsId") goodsId: String
    ): Observable<BaseEntity<MainSendInfo>>

    @FormUrlEncoded
    @POST("goods/delete")//删除货源信息（发布中）
    fun goodsDelete(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("goodsId") goodsId: String
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("goods/getOftenGoods")//获取常发货源（详细）
    fun oftenGoodsDetail(
            @Field("id") id: String = Constant.userInfo.id,
            @Field("goodsId") goodsId: String
    ): Observable<BaseEntity<GoodsDetailInfo>>

    @FormUrlEncoded
    @POST("goods/deleteOften")//删除常发货源
    fun goodsDeleteOften(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("goodsId") goodsId: String
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("goods/insertOften")//货源添加至常用货源
    fun goodsInsertOften(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("goodsId") goodsId: String
    ): Observable<BaseEntity<String>>


    @FormUrlEncoded
    @POST("goods/shipper/appointDriver")//指定司机
    fun appointDriver(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("goodsId") goodsId: String,
            @Field("carOwnerId") carOwnerId: String
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/getCarOwnerByPhone")//手机号查询司机
    fun getCarOwnerByPhone(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("phone") phone: String
    ): Observable<BaseEntity<MutableList<DriverInfo>>>


    @FormUrlEncoded
    @POST("goods/shipper/getCarInfo")//获取车辆详情
    fun getCarInfo(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("carOwnerId") carOwnerId: String
    ): Observable<BaseEntity<DriverDetailInfo>>


    /**
     * 找车
     */
    @FormUrlEncoded
    @POST("shipper/carList")//货主找车可以根据车长车型筛选
    fun carList(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("ifFamiliar") ifFamiliar: String,//查找是否熟车列表，1-是，2-否
            @Field("screen") screen: String,//筛选标识，1-有筛选条件，2-为没有筛选条件
            @Field("carLengthList") carLengthList: String,//车长主键使用逗号相连，筛选标识为1时（不选为全部）不能为空
            @Field("carTypeList") carTypeList: String,//车型主键用逗号相连，筛选标识为1时（不选为全部）不能为空
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("page") page: Int
    ): Observable<BaseEntity<PageInfo<FindCarInfo>>>

    @FormUrlEncoded
    @POST("goods/insertFamiliarCar")//添加熟车
    fun insertFamiliarCar(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("carOwnerId") carOwnerId: String,//车主表id
            @Field("comments") comments: String = ""//备注
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("goods/shipper/deleteFamiliarCar")//通过车主id货主id移除熟车
    fun deleteFamiliarCar(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("carOwnerId") carOwnerId: String//车主表id
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("goods/shipper/getFamiliarCarInfo")//获取熟车详情
    fun getFamiliarCarInfo(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("carOwnerId") carOwnerId: String//车主表id
    ): Observable<BaseEntity<DriverDetailInfo>>


    @FormUrlEncoded
    @POST("shipper/getShipperById")//货主简介
    fun getShipperById(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("shipperId") shipperId: String//车主表id
    ): Observable<BaseEntity<OwnerInfo>>

    @FormUrlEncoded
    @POST("shipper/getShipperGoods")//查看货主货源列表
    fun getShipperGoods(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("shipperId") shipperId: String,//车主表id
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("page") page: Int
    ): Observable<BaseEntity<PageInfo<SourceInfo>>>


    /**
     * 地址信息
     */
    @FormUrlEncoded
    @POST("shipper/address/list")//地址列表
    fun addressList(
            @Field("id") id: String = Constant.userInfo.id
    ): Observable<BaseEntity<MutableList<AddressInfo>>>

    @FormUrlEncoded
    @POST("shipper/address/add")//添加地址
    fun addressAdd(
            @Field("id") id: String = Constant.userInfo.id,
            @Field("areaCode") areaCode: String,
            @Field("address") address: String,
            @Field("postalCode") postalCode: String,
            @Field("phone") phone: String,
            @Field("receiverName") receiverName: String
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/address/update")//更新地址信息
    fun addressUpdate(
            @Field("id") id: String = Constant.userInfo.id,
            @Field("addressId") addressId: String,
            @Field("areaCode") areaCode: String,
            @Field("address") address: String,
            @Field("postalCode") postalCode: String,
            @Field("phone") phone: String,
            @Field("receiverName") receiverName: String
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/address/delete")//删除地址
    fun addressDelete(
            @Field("id") id: String = Constant.userInfo.id,
            @Field("addressId") addressId: String
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/address/addDefault")//设为默认地址
    fun addressDefault(
            @Field("id") id: String = Constant.userInfo.id,
            @Field("addressId") addressId: String
    ): Observable<BaseEntity<String>>

    /**
     * 订单信息
     */
    @FormUrlEncoded
    @POST("shipper/order/list")//订单列表
    fun orderList(
            @Field("id") id: String = Constant.userInfo.id,
            @Field("status") status: Int,//订单状态1-运输中，2-已完成，3-已取消，为空默认未1
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("page") page: Int
    ): Observable<BaseEntity<PageInfo<OrderInfo>>>


    @FormUrlEncoded
    @POST("shipper/order/info")//订单详情
    fun orderInfo(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("orderId") orderId: String//订单id
    ): Observable<BaseEntity<OrderInfo>>

    @FormUrlEncoded
    @POST("shipper/order/confirm")//确认收货
    fun orderConfirm(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("orderId") orderId: String//订单id
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/order/payment")//订单支付
    fun orderPayment(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("orderId") orderId: String//订单id
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/order/cancel")//取消订单
    fun orderCancel(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("orderId") orderId: String,//订单id
            @Field("reason") reason: String//取消原因
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/order/receipt")//查看回单
    fun orderReceipt(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("orderId") orderId: String//订单id
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/agreement/init")//发起协议
    fun agreementInit(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("orderId") orderId: String,
            @Field("weightVolume") weightVolume: String,
            @Field("carTypeLength") carTypeLength: String,
            @Field("totalAmount") totalAmount: String,
            @Field("companyAmount") companyAmount: String,
            @Field("payTime") payTime: String,
            @Field("goodsName") goodsName: String = "",
            @Field("loadTime") loadTime: String,
            @Field("unloadTime") unloadTime: String,
            @Field("loadAddress") loadAddress: String,
            @Field("unloadAddress") unloadAddress: String,
            @Field("appointment") appointment: String = ""
    ): Observable<BaseEntity<String>>

//    shipperId	必填	string	货主ID
//    orderId	必填	string	订单id
//    weightVolume	必填	string	重量体积
//    carTypeLength	必填	string	车型车长
//    sumAmount	必填	string	总运费
//    companyAmount	必填	string	通过平台支付运费
//    payTime	必填	string	付款时间（卸货后支付的最大天数）
//    goodsName	非必填	string	货物名称
//    loadTime	必填	string	装货时间（13位时间戳）
//    unloadTime	必填	string	到货时间（13位时间戳）
//    loadAddress	必填	string	装货地址（地区
//    unloadAddress	必填	string	卸货地址（地区
//    appointment	非必填	string	其他约定

    @FormUrlEncoded
    @POST("shipper/agreement/info")//货主查看订单签署的协议
    fun agreementInfo(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("orderId") orderId: String//订单id
    ): Observable<BaseEntity<OrderAgreementInfo>>

    @FormUrlEncoded
    @POST("shipper/agreement/charge")//计算服务费
    fun agreementCharge(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("money") money: String//订单id
    ): Observable<BaseEntity<String>>


    /**
     * 银行卡管理
     */
    @FormUrlEncoded
    @POST("shipper/bankCard/add")//添加银行卡
    fun bankCardAdd(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("name") name: String,//
            @Field("idCardNumber") idCardNumber: String,//
            @Field("cardNumber") cardNumber: String,//
            @Field("phone") phone: String,//
            @Field("bank") bank: String//
    ): Observable<BaseEntity<String>>


    @FormUrlEncoded
    @POST("shipper/bankCard/delete")//删除银行卡
    fun bankCardDelete(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("cardId") cardId: String
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/checkCardType")//检查银行卡类型
    fun checkCardType(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("cardNumber") cardNumber: String
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/bankCard/list")//银行卡列表
    fun bankCardList(
            @Field("userId") id: String = Constant.userInfo.id
    ): Observable<BaseEntity<MutableList<BankCardInfo>>>

    /**
     * 道认证
     */
    @FormUrlEncoded
    @POST("auth/shipper/dao")//道认证提交
    fun daoAuth(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("dao") dao: String,
            @Field("name") name: String,
            @Field("taxNumber") taxNumber: String,
            @Field("address") address: String,
            @Field("phone") phone: String,
            @Field("bank") bank: String,
            @Field("bankNumber") bankNumber: String,
            @Field("comments") comments: String
    ): Observable<BaseEntity<String>>


    @Multipart
    @POST("auth/shipper/dao")//道认证提交
    fun daoAuth(
            @Part partList: MutableList<MultipartBody.Part>
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/invoice/info")//发票资质信息
    fun invoiceInfo(
            @Field("shipperId") id: String = Constant.userInfo.id
    ): Observable<BaseEntity<InvoiceInfo>>

    @FormUrlEncoded
    @POST("shipper/invoice/insert")//通过道认证后填写发票信息
    fun invoiceInsert(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("name") name: String,
            @Field("taxNumber") taxNumber: String,
            @Field("address") address: String,
            @Field("phone") phone: String,
            @Field("bank") bank: String,
            @Field("bankNumber") bankNumber: String,
            @Field("comments") comments: String
    ): Observable<BaseEntity<String>>

//    dao	必填	string	法人授权书
//    name	必填	string	页发票抬头
//    taxNumber	必填	string	税号
//    address	必填	string	单位地址
//    phone	必填	string	电话号码
//    bank	必填	string	开户银行
//    bankNumber	必填	string	银行账户
//    comments	非必填	string	商品明细

    @FormUrlEncoded
    @POST("shipper/invoice/update")//更新发票信息
    fun invoiceUpdate(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("name") name: String,
            @Field("taxNumber") taxNumber: String,
            @Field("address") address: String,
            @Field("phone") phone: String,
            @Field("bank") bank: String,
            @Field("bankNumber") bankNumber: String,
            @Field("comments") comments: String
    ): Observable<BaseEntity<String>>


    /**
     * 发票
     */
    @FormUrlEncoded
    @POST("shipper/invoice/drawBill")//开发票
    fun invoiceBill(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("receiverName") receiverName: String,
            @Field("receiverPhone") receiverPhone: String,
            @Field("receiverAddress") receiverAddress: String,
            @Field("name") name: String,
            @Field("taxNumber") taxNumber: String,
            @Field("address") address: String,
            @Field("phone") phone: String,
            @Field("bank") bank: String,
            @Field("bankNumber") bankNumber: String,
            @Field("comments") comments: String,
            @Field("orderIds") orderIds: String,
            @Field("addressId") addressId: String
    ): Observable<BaseEntity<String>>

//    shipperId	必填	string	货主id
//    receiverName	必填	string	收件人姓名
//    receiverPhone	必填	string	收件人手机
//    receiverAddress	必填	string	收件地址
//    name	必填	string	发票抬头
//    taxNumber	必填	string	税号
//    address	必填	string	单位地址
//    phone	必填	string	电话号码
//    bank	必填	string	开户银行
//    bankNumber	必填	string	银行账户
//    comments	非必填	string	商品明细
//    orderIds	必填	string	开票的订单id，多个使用逗号相连接
//    addressId	必填	string	地址id


    @FormUrlEncoded
    @POST("shipper/order/ableDraw")//查看可开票订单列表
    fun invoiceOrderList(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("page") page: Int
    ): Observable<BaseEntity<PageInfo<InvoiceOrderInfo>>>

    @FormUrlEncoded
    @POST("shipper/invoiceRecord/list")//开票历史
    fun invoiceList(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("page") page: Int
    ): Observable<BaseEntity<PageInfo<InvoiceInfo>>>

    @FormUrlEncoded
    @POST("shipper/invoiceRecord/info")//发票记录详情
    fun invoiceDetail(
            @Field("shipperId") id: String = Constant.userInfo.id,
            @Field("invoiceRecordId") invoiceRecordId: String
    ): Observable<BaseEntity<InvoiceInfo>>

    /**
     * 货源
     */
    @FormUrlEncoded
    @POST("goods/shipper/getList")//获取货源列表（发货中）
    fun goodsNowList(
            @Field("id") id: String = Constant.userInfo.id,
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("loadAreaCode") loadAreaCode: String,
            @Field("unloadAreaCode") unloadAreaCode: String,
            @Field("sortType") sortType: String = "1",
            @Field("type") type: String,
            @Field("loadTime") loadTime: String,
            @Field("carLength") carLength: String,
            @Field("carType") carType: String,
            @Field("page") page: Int
    ): Observable<BaseEntity<PageInfo<SourceInfo>>>

//    id	必填	string	货主表主键
//    loadAreaCode	非必填	string	装货地区码
//    unloadAreaCode	非必填	string	收货地区码
//    sortType	必填	string	排序类型（1创建时间排序，2待定）
//    type	非必填	string	用车类型（整车-1、零担-2）
//    loadTime	非必填	string	装货时间（123今天明天后天，使用逗号相连）
//    carLength	非必填	string	车长id（使用逗号相连）
//    carType	非必填	string	车型id（使用逗号相连）
//    page	必填	string	页数（第一页为0）
//    size	必填	string	每页大小


    @FormUrlEncoded
    @POST("shipper/center")//发票资质信息
    fun mineInfo(
            @Field("userId") id: String = Constant.userInfo.id
    ): Observable<BaseEntity<MineInfo>>


    @FormUrlEncoded
    @POST("shipper/feedback")//意见反馈
    fun feedback(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("content") content: String
    ): Observable<BaseEntity<String>>


    @FormUrlEncoded
    @POST("shipper/center/account")//钱包
    fun walletAccount(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("page") page: Int
    ): Observable<BaseEntity<WalletInfo>>

    @FormUrlEncoded
    @POST("shipper/getCompanyAccount")//充值时展示公司账户信息
    fun getCompanyAccount(
            @Field("userId") id: String = Constant.userInfo.id
    ): Observable<BaseEntity<MutableList<BankInfo>>>

    @FormUrlEncoded
    @POST("shipper/account/balance")//获取账户余额
    fun accountBalance(
            @Field("userId") id: String = Constant.userInfo.id
    ): Observable<BaseEntity<AccountInfo>>

    @FormUrlEncoded
    @POST("shipper/account/cash")//提现
    fun accountCash(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("sum") sum: String,
            @Field("bankId") bankId: String
    ): Observable<BaseEntity<String>>

    @FormUrlEncoded
    @POST("shipper/account/cashList")//提现记录
    fun cashList(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("size") size: Int = Constant.PAGE_SIZE,
            @Field("page") page: Int
    ): Observable<BaseEntity<PageInfo<CashInfo>>>


    @FormUrlEncoded
    @POST("shipper/info/check")//用户检测
    fun infoCheck(
            @Field("userId") id: String = Constant.userInfo.id
    ): Observable<BaseEntity<UserInfo>>

    @FormUrlEncoded
    @POST("shipper/point/add")//上传轨迹点
    fun pointAdd(
            @Field("userId") id: String = Constant.userInfo.id,
            @Field("latitude") latitude: String,
            @Field("longitude") longitude: String,
            @Field("loc_time") loc_time: String = System.currentTimeMillis().toString(),
            @Field("coord_type_input") coord_type_input: String = "GCJ02"
    ): Observable<BaseEntity<String>>

    //    latitude	必填	string	纬度
//    longitude	必填	string	经度
//    loc_time	必填	string	设备定位时间
//    coord_type_input	必填	string	坐标的类型
//根据经纬度查询天气
    @FormUrlEncoded
    @POST("shipper/weather")
    fun requestWeather(
            @Field("userId") userId: String = Constant.userInfo.id,//	车主id
            @Field("formType") formType: String,//	坐标类型
            @Field("lat") lat: String,//	纬度
            @Field("lng") lng: String,//	经度
            @Field("need3HourForcast") need3HourForcast: String,//	是否需要当天每3或6小时一次的天气预报列表
            @Field("needAlarm") needAlarm: String,//	是否需要天气预警
            @Field("needHourData") needHourData: String,//	是否需要每小时数据的累积数组
            @Field("needIndex") needIndex: String,//	是否需要返回指数数据
            @Field("needMoreDay") needMoreDay: String//	是否需要返回7天数据中的后4天
    ): Observable<BaseEntity<String>>
    //查询司机位置
    @FormUrlEncoded
    @POST("shipper/getDriverLastPosition")
    fun requestDriverPostion(
            @Field("userId") userId: String = Constant.userInfo.id,//	车主id
            @Field("targetId") driverid: String//	司机id
    ): Observable<BaseEntity<DriverPostionVo>>

}