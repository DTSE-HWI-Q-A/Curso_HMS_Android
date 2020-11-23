package com.dtse.demo.tictactoe

import android.app.Application

class TicTacToeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // HuaweiMobileServicesUtil.setApplication(this)
        Preference.init(this)
    }
}