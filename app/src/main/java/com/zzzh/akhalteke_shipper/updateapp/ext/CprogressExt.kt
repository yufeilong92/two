package com.zzzh.akhalteke_shipper.updateapp.ext

import android.app.Activity
import com.zzzh.akhalteke_shipper.updateapp.util.CProgressDialogUtils

/**
 * Created by Vector
 * on 2017/7/18 0018.
 */
inline fun Activity.showProgressDialog(m:Activity) {
    CProgressDialogUtils.showProgressDialog(m)
}

inline fun Activity.cancelProgressDialog(m:Activity) {
    CProgressDialogUtils.cancelProgressDialog(m)
}