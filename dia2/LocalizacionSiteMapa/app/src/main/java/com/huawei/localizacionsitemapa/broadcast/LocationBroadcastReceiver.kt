package com.huawei.localizacionsitemapa.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.huawei.hms.location.ActivityIdentificationResponse
import com.huawei.localizacionsitemapa.Constante


class LocationBroadcastReceiver : BroadcastReceiver()  {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (Constante.ACTION_PROCESS_LOCATION == action) {

                val activityIdentificationResponse = ActivityIdentificationResponse.getDataFromIntent(intent)
                val list = activityIdentificationResponse.activityIdentificationDatas
                for ( actIdent in list) {
                    var action = when(actIdent?.identificationActivity){
                        Constante.VEHICLE_CODE -> Constante.VEHICLE_NAME
                        Constante.BIKE_CODE -> Constante.BIKE_NAME
                        Constante.FOOT_CODE -> Constante.FOOT_NAME
                        Constante.STILL_CODE -> Constante.STILL_NAME
                        Constante.OTHERS_CODE -> Constante.OTHERS_NAME
                        Constante.WALKING_CODE -> Constante.WALKING_NAME
                        Constante.RUNNING_CODE -> Constante.RUNNING_NAME
                        else -> Constante.EMPTY
                    }
                }

            }
        }
    }

}