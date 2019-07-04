package com.zzzh.akhalteke_shipper.utils

//import com.igexin.sdk.PushManager
import android.animation.ValueAnimator
import android.app.Activity
import android.app.Dialog
import android.content.*
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.lipo.utils.Arith
import com.lipo.utils.DisplayUtil
import com.lipo.views.ToastView
import com.zzzh.akhalteke_shipper.BuildConfig
import com.zzzh.akhalteke_shipper.R
import com.zzzh.akhalteke_shipper.realm.AddressBean
import com.zzzh.akhalteke_shipper.realm.AddressRealm
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import org.json.JSONArray
import org.json.JSONException
import java.io.*
import java.math.BigDecimal
import java.util.*
import java.util.regex.Pattern

object ToolUtils {

    fun log(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.i("zfb_json", msg)
        }
    }


    fun pathCache(): File {
        return Environment.getExternalStoragePublicDirectory("zzzh")
    }

    /**
     * 跳转到权限设置界面
     */
    fun getAppDetailSettingIntent(context: Activity) {

        // vivo 点击设置图标>加速白名单>我的app
        //      点击软件管理>软件管理权限>软件>我的app>信任该软件
        var appIntent = context.packageManager.getLaunchIntentForPackage("com.iqoo.secure")
        if (appIntent != null) {
            context.startActivity(appIntent)

            return
        }

        // oppo 点击设置图标>应用权限管理>按应用程序管理>我的app>我信任该应用
        //      点击权限隐私>自启动管理>我的app
        appIntent = context.packageManager.getLaunchIntentForPackage("com.oppo.safe")
        if (appIntent != null) {
            context.startActivity(appIntent)

            return
        }

        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = (Uri.fromParts("package", context.packageName, null))
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.action = Intent.ACTION_VIEW
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
            intent.putExtra("com.android.settings.ApplicationPkgName", context.packageName)
        }
        context.startActivity(intent)
    }

    /**
     * 复制到粘贴板
     */
    fun copyData(context: Context, copyStr: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Label", copyStr)
        clipboardManager.primaryClip = clipData
        ToastView.setToasd(context, "复制成功")
    }

    /**
     * 获取屏幕的尺寸信息
     */
    fun obtainScreenWH(mContext: Context) {
        var statusBarHeight = -1
        val resourceId = mContext.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = mContext.resources.getDimensionPixelSize(resourceId)
        }

        Constant.statusHeight = statusBarHeight

        val metrics: DisplayMetrics = mContext.resources.displayMetrics
        Constant.screenHeight = metrics.heightPixels
        Constant.screenWidth = metrics.widthPixels

    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun dpTopx(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }


    /**
     * 字符串是否为空
     * @param name
     * @return
     */
    fun isEmpty(name: String?): Boolean {
        return (name == null || "" == name || "null" == name)
    }

//    fun String.isEmpty(): Boolean {
//        return (this == null || "" == this || "null" == this)
//    }

    /**
     * 得到当前包信息
     *
     * packInfo.versionName
     * packInfo.versionCode
     * packInfo.packageName
     *
     */
    fun getVersionCode(activity: Context): PackageInfo? {
        val packageManager = activity.packageManager
        val packInfo: PackageInfo
        try {
            packInfo = packageManager.getPackageInfo(
                activity.packageName,
                0
            )
            return packInfo
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    fun isAvilible(context: Context, packageName: String): Boolean {
        //获取packagemanager
        val packageManager = context.packageManager
        //获取所有已安装程序的包信息
        val packageInfos = packageManager.getInstalledPackages(0)
        //用于存储所有已安装程序的包名
        val packageNames = ArrayList<String>()
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (i in packageInfos.indices) {
                val packName = packageInfos[i].packageName
                packageNames.add(packName)
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName)
    }


    /**
     * 正则匹配 是否是数字
     *
     * @param str
     * @return
     */
    fun isNumber(str: String): Boolean {
        var str = str
        if (str.startsWith("-")) {
            str = str.substring(1)
        }
        if (str == null || "" == str) {
            return false
        }
        val pattern = Pattern.compile("[0-9]*")
        val matcher = pattern.matcher(str)
        return matcher.matches()
    }

    fun stringToDouble(str: String?): Double {
        var doubleValue = 0.0
        if (str == null || "" == str) {
            return 0.0
        }
        try {
            doubleValue = java.lang.Double.valueOf(str.replace(",", "")).toDouble()
        } catch (e: NumberFormatException) {
            doubleValue = 0.0
        } finally {

        }
        return doubleValue
    }

    fun stringToIntM(str: String): Int {
        return if (isNumber(str)) {
            Integer.valueOf(str).toInt()
        } else 0
    }

    fun String.stringToInt(): Int {
        return if (isNumber(this)) {
            Integer.valueOf(this).toInt()
        } else 0
    }

    fun stringToLong(str: String): Long {
        return if (isNumber(str)) {
            java.lang.Long.valueOf(str).toLong()
        } else 0L
    }

    /**
     * 保留n位小数
     * @param value
     * @param scale
     * @return
     */
    fun converScale(value: String, scale: Int): String {
        var value = value
        if (stringToDouble(value) == 0.0) {
            value = "0"
        }
        val decimal = BigDecimal(value)
        return decimal.setScale(scale, BigDecimal.ROUND_HALF_DOWN).toString()
    }

    /**
     * 保留n位小数 先前进位
     * @param value
     * @param scale
     * @return
     */
    fun converScaleROUNDUP(value: Double, scale: Int): Double {
        val decimal = BigDecimal(value)
        return decimal.setScale(scale, BigDecimal.ROUND_UP).toDouble()
    }

    /**
     * 展示dialog
     * @param dialog
     */
    fun showDialog(dialog: Dialog?) {
        if (dialog != null && !dialog.isShowing) {
            dialog.show()
        }
    }

    /**
     * 取消dialog
     * @param dialog
     */
    fun dismissDialog(dialog: Dialog?) {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }


// 判断email格式是否正确

    fun isEmail(email: String): Boolean {
        val str =
            "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
        val p = Pattern.compile(str)
        val m = p.matcher(email)
        return m.matches()
    }

    /**
     * 获取9位随机数
     * @return
     */
    fun getRandCodeStr(): String {
        var randInt = Random().nextInt()
        if (randInt < 0) {
            randInt = -randInt
        }
        val randStr = randInt.toString()
        if (randStr.length > 9) {
            return randStr.substring(0, 9)
        } else {
            val zeroStr = "000000000"
            return zeroStr.substring(0, 9 - randStr.length) + randStr
        }
    }

    /**
     * 获取9位随机数
     * @param number 范围随机数
     * @return
     */
    fun getOneInt(number: Int): Int {
        var randInt = Random().nextInt(number)
        return randInt
    }

    /**
     * 获取随机背景
     */
    fun getImageDrawer(mContext: Context): Drawable {
        val oneInt = getOneInt(3)
        when (oneInt) {
            0 -> {
                return mContext.resources.getDrawable(R.drawable.bg_order_detail_sky_blue)
            }
            1 -> {
                return mContext.resources.getDrawable(R.drawable.bg_order_detail_qian_yellow)
            }
            2 -> {
                return mContext.resources.getDrawable(R.drawable.bg_order_detail_sky_fen)
            }
            else -> {
                return mContext.resources.getDrawable(R.drawable.bg_order_detail_sky_blue)
            }
        }

    }


    /**
     * 点击加号
     */
    fun addNumber(editNum: String, limitNum: String = ""): Int {
        var addNum: Int = editNum.stringToInt()
        if (!limitNum.isEmpty() && limitNum.stringToInt() != 0) {
            if (addNum >= limitNum.stringToInt()) {
                return addNum
            }
        }
        return addNum + 1
    }

    /**
     * 点击减号
     */
    fun reduceNumber(editNum: String): Int {
        var addNum: Int = editNum.stringToInt()
        if (addNum <= 0) {
            return addNum
        } else {
            return addNum - 1
        }
    }

    /**
     * 向下箭头的旋转
     * temp 0是向下，1是向上
     */
    fun toRotateDownView(temp: Int, downView: View) {
//        if (temp == 0) {
////            RotateAnimation(
////                0f, 180f,
////                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
////            ).apply {
////                interpolator = LinearInterpolator()
////                duration = 300
////                fillAfter = true
////                downView.startAnimation(this)
////            }
////        } else {
////            RotateAnimation(
////                180f, 0f,
////                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
////            ).apply {
////                interpolator = LinearInterpolator()
////                duration = 300
////                fillAfter = true
////                downView.startAnimation(this)
////            }
////        }

        when (temp) {
            0 -> {
                ValueAnimator.ofFloat(0f, -180f).apply {
                    duration = 300
                    addUpdateListener {
                        downView.rotation = it.animatedValue as Float
                    }
                    start()
                }
            }
            1 -> {
                ValueAnimator.ofFloat(-180f, 0f).apply {
                    duration = 300
                    addUpdateListener {
                        downView.rotation = it.animatedValue as Float
                    }
                    start()
                }
            }
        }

    }


    /**
     * 转换到商品规格
     */
    fun toGetGoodsSpec(specNames: MutableList<String>?, specValues: MutableList<String>?): String {
        var lenght = 0
        if (specNames != null && specNames.size.also { lenght = it } > 0) {
            val specBuffer = StringBuffer()
            for (i in 0..(lenght - 1)) {
                specBuffer.append(specNames[i] + ":" + specValues!![i])
                if (lenght > 1 && i == 0) {
                    specBuffer.append(", ")
                }
            }
            return specBuffer.toString()
        }
        return ""
    }

    fun toInputType(editInput: String): Int {
        return when (editInput) {
            "phone" -> InputType.TYPE_CLASS_PHONE
            "textPassword" -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            "number" -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
            "numberSigned" -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            "numberDecimal" -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
            "numberPassword" -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            else -> {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            }
        }
    }

    fun isToLogin(mContext: Context): Boolean {
        return isEmpty(Constant.token)
    }

    /**
     * 转换成距离
     */
    fun toSwitchDistance(distance: Double): String {
        if (distance < 800) {
            return "$distance m"
        } else {
            return Arith.div(distance, 1000.0, 2).toString() + " km"
        }
    }

    /**
     * 启动高德App进行导航
     *
     * //     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname             非必填 目的地名称
     * @param lat              必填 终点纬度
     * @param lon              必填 终点经度
     * //     * @param dev               必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style             必填 预设的导航方式 t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）
     */
    fun goToNaviActivity(mContext: Context, poiname: String, lat: String, lon: String, style: String) {
        //启动路径规划页面
        try {
            val uri = "amapuri://route/plan/?dlat=$lat&dlon=$lon&dname=$poiname&dev=0&t=$style"
            val intent = Intent("android.intent.action.VIEW", android.net.Uri.parse(uri))
            intent.setPackage("com.autonavi.minimap")
            mContext.startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(mContext, "该手机不支持打开地图进行导航", Toast.LENGTH_LONG).show()
        }

    }

    /**
     * 启动腾讯地图App进行导航
     * @param address 目的地
     * @param lat 必填 纬度
     * @param lon 必填 经度
     */
    fun gotoTengxun(context: Context, address: String, lat: Double, lon: Double) {
        // 启动路径规划页面
        val naviIntent = Intent(
            "android.intent.action.VIEW",
            android.net.Uri.parse("qqmap://map/routeplan?type=drive&from=&fromcoord=&to=$address&tocoord=$lat,$lon&policy=0&referer=appName")
        )
        context.startActivity(naviIntent)
    }

    /**
     * 启动百度App进行导航
     * @param address 目的地
     * @param lat 必填 纬度
     * @param lon 必填 经度
     *
     * mode driving 驾车 riding
     *
     */
    fun goToBaiduActivity(context: Context, address: String, lat: Double, lon: Double) {
        val doubles = gcj02_To_Bd09(lat, lon)
        //启动路径规划页面
        val naviIntent = Intent(
            "android.intent.action.VIEW",
            android.net.Uri.parse("baidumap://map/direction?destination=" + doubles[0] + "," + doubles[1] + "&region=" + address + "&mode=riding")
        )
        context.startActivity(naviIntent)
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param lat
     * @param lon
     */
    fun gcj02_To_Bd09(lat: Double, lon: Double): DoubleArray {
        val x_pi = 3.14159265358979324
        val z = Math.sqrt(lon * lon + lat * lat) + 0.00002 * Math.sin(lat * x_pi)
        val theta = Math.atan2(lat, lon) + 0.000003 * Math.cos(lon * x_pi)
        val tempLat = z * Math.sin(theta) + 0.006
        val tempLon = z * Math.cos(theta) + 0.0065
        return doubleArrayOf(tempLat, tempLon)
    }

    /**
     *
     * @param distance
     * @return
     */
    fun transformDistance(distance: String): String {
        var strDistance = 0.0
        try {
            strDistance = java.lang.Double.valueOf(distance)!!
        } catch (e: Exception) {

        }

        if (strDistance < 1) {
            val distanceBig = BigDecimal(strDistance).multiply(BigDecimal(1000))

            return distanceBig.setScale(1, BigDecimal.ROUND_HALF_DOWN).toString() + "m"
        } else {
            return strDistance.toString() + "km"
        }
    }

    fun transformInt(temp: String): Int {
        var t = 0
        try {
            t = Integer.valueOf(temp).toInt()
        } catch (e: Exception) {
            t = 0
        }

        return t
    }

    /**
     * 文件转Base64.
     * @param filePath
     * @return
     */
    fun file2Base64(filePath: String): String {
        var objFileIS: FileInputStream? = null
        try {
            objFileIS = FileInputStream(filePath)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        val objByteArrayOS = ByteArrayOutputStream()
        val byteBufferString = ByteArray(1024)
        try {
            var readNum: Int
            while ((objFileIS!!.read(byteBufferString)).also { readNum = it } != -1) {
                objByteArrayOS.write(byteBufferString, 0, readNum)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return android.util.Base64.encodeToString(objByteArrayOS.toByteArray(), android.util.Base64.DEFAULT)
    }


    fun addressToAddRealm(context: Context) {
        if (PreferencesUtils().toGetAddressFristRealm()) {
            Single.fromCallable {
                val sb: StringBuffer = StringBuffer()
                try {
                    val ins = context.assets.open("area.json")
                    val br = BufferedReader(InputStreamReader(ins))
                    var temp: String? = ""
                    while ((br.readLine()).also { temp = it } != null) {
                        sb.append(temp)
                    }
                    br.close()
                    ins.close()
                    val addressList: MutableList<AddressBean> = mutableListOf()
                    areaToAddRealm(addressList, JSONArray(sb.toString()), 0)

                    return@fromCallable addressList
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                return@fromCallable mutableListOf<AddressBean>()
            }.flatMap { addressList ->
                AddressRealm().addAdrList(addressList)
            }.observeOn(AndroidSchedulers.mainThread()).subscribe({ addressList ->
                addressList.clear()
//                ToastView.setToasd(context, "添加成功")
            }, {})
        }
    }

    private fun areaToAddRealm(addressList: MutableList<AddressBean>, jsonArray: JSONArray, pid: Int) {
        if (jsonArray != null) {
            for (i in 0 until jsonArray.length()) {
                val json = jsonArray.optJSONObject(i)
                if (json != null) {
                    val id = json.optInt("id")
                    addressList.add(
                        AddressBean(
                            id = id, area_name = json.optString("area_name"),
                            pid = pid, position = i
                        )
                    )

                    val jArray = json.optJSONArray("citys")
                    if (jArray != null && jArray.length() > 0) {
                        areaToAddRealm(addressList, jArray, id)
                    }

                    val zones = json.optJSONArray("zones")
                    if (zones != null && zones.length() > 0) {
                        areaToAddRealm(addressList, zones, id)
                    }
                }
            }
        }
    }

    /**
     * assets拷贝
     */
    fun copyAssetAndWrite(mContext: Context, fileName: String): Boolean {
        try {
            val cacheDir = pathCache()
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            val outFile = File(cacheDir, fileName)
            if (!outFile.exists()) {
                val res = outFile.createNewFile()
                if (!res) {
                    return false
                }
            } else {
                if (outFile.length() > 10) {//表示已经写入一次
                    return true
                }
            }
            val ins = mContext.assets.open(fileName)
            val fos = FileOutputStream(outFile)

            val buffer = ByteArray(1024)
            var byteCount = 0
            while (ins.read(buffer).also { byteCount = it } != -1) {
                fos.write(buffer, 0, byteCount)
            }
            fos.flush()
            ins.close()
            fos.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }


    fun phoneEncry(phoneStr: String): String {
        if (isEmpty(phoneStr)) {
            return ""
        }

        if (phoneStr.length != 11) {
            return phoneStr
        }

        val str01 = phoneStr.substring(0, 3)
        val str02 = phoneStr.substring(7, 11)
        return "$str01****$str02"
    }

    fun adrSpannStr(mContext: Context, fromPath: String, toPath: String): SpannableStringBuilder {
        val spanText = SpannableStringBuilder()
        spanText.append(fromPath)
        spanText.append("     ")
        spanText.append(toPath)
        val wh = DisplayUtil.dip2px(mContext, 14f)
        val mDrawable = ContextCompat.getDrawable(mContext, R.mipmap.icon_to)
        mDrawable!!.setBounds(0, 0, wh, wh)
        val drawableSpan = object : ImageSpan(mDrawable) {
            override fun draw(
                canvas: Canvas,
                text: CharSequence?,
                start: Int,
                end: Int,
                x: Float,
                top: Int,
                y: Int,
                bottom: Int,
                paint: Paint
            ) {

                val fm = paint.fontMetricsInt
                val transY = (y + fm.descent + y + fm.ascent) / 2 - drawable.bounds.bottom / 2//计算y方向的位移
                canvas.save()
                canvas.translate(x, transY.toFloat())//绘制图片位移一段距离
                drawable.draw(canvas)
                canvas.restore()
            }
        }

        val length = fromPath.length + 2
        spanText.setSpan(drawableSpan, length, (length + 1), ImageSpan.ALIGN_BASELINE)
        return spanText
    }

    /**
     * 限制输入小数点
     */
    fun limitEditDit(editText: EditText, digits: Int) {
        var str = editText.text.toString().trim()
        //删除“.”后面超过2位后的数据
        if (str.contains(".")) {
            if (str.length - 1 - str.indexOf(".") > digits) {
                str = str.subSequence(
                    0,
                    ((str.indexOf(".")) + digits + 1)
                ).toString()
                editText.setText(str)
                editText.setSelection(str.length) //光标移到最后
            }
        }
        //如果"."在起始位置,则起始位置自动补0
        if (str.substring(0).equals(".")) {
            str = "0$str"
            editText.setText(str)
            editText.setSelection(2)
        }

        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if (str.startsWith("0")
            && str.length > 1
        ) {
            if (str.substring(1, 2) != ".") {
                editText.setText(str.subSequence(0, 1))
                editText.setSelection(1)
                return
            }
        }
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    fun getVersionName(context: Context): String {
        try {
            return context.packageManager.getPackageInfo(
                    context.packageName, 0
            ).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }

}