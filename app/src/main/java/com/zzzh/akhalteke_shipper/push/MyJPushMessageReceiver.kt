package com.zzzh.akhalteke_shipper.push

import cn.jpush.android.service.JPushMessageReceiver
import android.support.v4.content.LocalBroadcastManager
import com.zzzh.akhalteke_shipper.MainActivity
import com.zzzh.akhalteke_shipper.utils.ExampleUtil
import org.greenrobot.eventbus.util.ErrorDialogManager.KEY_MESSAGE
import android.content.Intent
import android.R.id.message
import android.content.Context
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.api.CmdMessage
import cn.jpush.android.api.NotificationMessage
import android.support.v4.content.ContextCompat.startActivity
import cn.jpush.android.api.JPushInterface
import android.os.Bundle
import android.util.Log
import com.zzzh.akhalteke_shipper.BaseApplication
import com.zzzh.akhalteke_shipper.utils.RouterTo


/**
 * @Author : ZFB  is Creating a porject in akhalteke-Android-shipper
 * @Package com.zzzh.akhalteke_shipper.receiver
 * @Email : 879736112@qq.com
 * @Time :2019/6/4 10:04
 * @Purpose :极光推送的接收类
 */
class MyJPushMessageReceiver: JPushMessageReceiver() {

    private val TAG = "PushMessageReceiver"
   override fun onMessage(context: Context, customMessage: CustomMessage) {
        Log.e(TAG, "[onMessage] $customMessage")
        processCustomMessage(context, customMessage)
    }

    override fun onNotifyMessageOpened(context: Context, message: NotificationMessage) {
        Log.e(TAG, "[onNotifyMessageOpened] $message")
        try {
            if(!BaseApplication.ifAppFront){
                RouterTo(context).jumpToMain()
            }
//            //打开自定义的Activity
//            val i = Intent(context, TestActivity::class.java)
//            val bundle = Bundle()
//            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE, message.notificationTitle)
//            bundle.putString(JPushInterface.EXTRA_ALERT, message.notificationContent)
//            i.putExtras(bundle)
//            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            context.startActivity(i)
        } catch (throwable: Throwable) {

        }

    }

    override fun onNotifyMessageArrived(context: Context, message: NotificationMessage) {
        Log.e(TAG, "[onNotifyMessageArrived] $message")
    }

    override fun onNotifyMessageDismiss(context: Context, message: NotificationMessage) {
        Log.e(TAG, "[onNotifyMessageDismiss] $message")
    }

    override fun onRegister(context: Context, registrationId: String) {
        Log.e(TAG, "[onRegister] $registrationId")
    }

    override fun onConnected(context: Context, isConnected: Boolean) {
        Log.e(TAG, "[onConnected] $isConnected")
    }

    override fun onCommandResult(context: Context, cmdMessage: CmdMessage) {
        Log.e(TAG, "[onCommandResult] $cmdMessage")
    }

    override fun onTagOperatorResult(context: Context, jPushMessage: JPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context, jPushMessage)
        super.onTagOperatorResult(context, jPushMessage)
    }

    override fun onCheckTagOperatorResult(context: Context, jPushMessage: JPushMessage) {
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context, jPushMessage)
        super.onCheckTagOperatorResult(context, jPushMessage)
    }

    override fun onAliasOperatorResult(context: Context, jPushMessage: JPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context, jPushMessage)
        super.onAliasOperatorResult(context, jPushMessage)
    }

    override fun onMobileNumberOperatorResult(context: Context, jPushMessage: JPushMessage) {
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context, jPushMessage)
        super.onMobileNumberOperatorResult(context, jPushMessage)
    }

    //send msg to MainActivity
    private fun processCustomMessage(context: Context, customMessage: CustomMessage) {
//        if (MainActivity.isForeground) {
//            val message = customMessage.message
//            val extras = customMessage.extra
//            val msgIntent = Intent(MainActivity.MESSAGE_RECEIVED_ACTION)
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message)
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    val extraJson = JSONObject(extras)
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras)
//                    }
//                } catch (e: JSONException) {
//
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent)
//        }
    }

}