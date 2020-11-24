package com.huawei.localizacionsitemapa

import android.app.Application
import com.huawei.hms.maps.MapsInitializer

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // MapsInitializer nos ayuda a inizializar Huawei Map Kit utilizando el Api
        // El Api Key se obtiene del archivo agconnect-services.json
        MapsInitializer.setApiKey("CgB6e3x9+e8gi+qXxnUTADmk6LHh8n0QqykumF3mbS/NuMQkGBsmOweRINR2pazf9Txctu+dnkiZRXRA8PV6s7C3")
    }

}