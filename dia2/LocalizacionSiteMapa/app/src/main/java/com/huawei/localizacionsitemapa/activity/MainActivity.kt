package com.huawei.localizacionsitemapa.activity

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.huawei.localizacionsitemapa.Constante
import com.huawei.localizacionsitemapa.R
import com.huawei.localizacionsitemapa.base.BaseActivity

class MainActivity : BaseActivity() {

    init {
        activityIdent = this
    }

    companion object {
        lateinit var activityIdent: MainActivity
    }

    override fun getLayout(): Int = R.layout.activity_main

    override fun inicializar() {

    }

    private fun verificarPermisos() {
        if(!tieneLocationPermisos()) {
            ActivityCompat.requestPermissions(this, LISTA_PERMISSION, Constante.REQUEST_CODE)
        }
    }

    private fun tieneLocationPermisos() : Boolean {
        for (permisos in LISTA_PERMISSION) {
            if(baseContext?.let { ActivityCompat.checkSelfPermission(it, permisos) }
                != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

}