package com.herovitamin.hms.huaweiuniversity

import android.content.Intent
import android.util.Log

import com.huawei.hms.push.HmsMessageService

//PASO 5: Crear Servicio para recibir el token
class MyPushService : HmsMessageService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i(TAG, "receive token:$token")
        sendTokenToDisplay(token)
    }

    private fun sendTokenToDisplay(token: String) {
        val intent = Intent("com.huawei.codelabpush.ON_NEW_TOKEN")
        intent.putExtra("token", token)
        sendBroadcast(intent)
    }

    companion object {
        private const val TAG = "PushDemoLog"
    }
}