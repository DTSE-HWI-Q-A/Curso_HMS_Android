package com.herovitamin.hms.huaweiuniversity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsInstance
import com.huawei.hms.analytics.HiAnalyticsTools
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() , OnTokenReceived{

    var instance: HiAnalyticsInstance? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // PASO 7: Registrar Receiver
        val receiver = MyReceiver(this@MainActivity)
        val filter = IntentFilter()
        filter.addAction("com.huawei.codelabpush.ON_NEW_TOKEN")
        this@MainActivity.registerReceiver(receiver, filter)

        //PASO 9: Obtener una instancia de Analytics
        HiAnalyticsTools.enableLog()
        instance = HiAnalytics.getInstance(this)
    }

    // PASO 10: Reportar evento en Analytics
    private fun reportTokenReceived() {
        val bundle = Bundle()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        bundle.putString("Tiempo", sdf.format(Date()))

        instance?.onEvent("Token Received", bundle)
        Log.i("AnalyticsEvent", "Evento Reportado")
    }

    // PASO 6: Crear Receiver para reportar el token
    class MyReceiver(val listener: OnTokenReceived) : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent) {
            if ("com.huawei.codelabpush.ON_NEW_TOKEN" == intent.action) {
                val token = intent.getStringExtra("token")
                listener.onTokenReceived()
            }
        }
    }

    override fun onTokenReceived() {
        reportTokenReceived()
    }
}