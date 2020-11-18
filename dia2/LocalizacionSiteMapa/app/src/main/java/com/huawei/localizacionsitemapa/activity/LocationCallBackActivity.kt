package com.huawei.localizacionsitemapa.activity

import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.huawei.hmf.tasks.Task
import com.huawei.hms.location.*
import com.huawei.localizacionsitemapa.Constante
import com.huawei.localizacionsitemapa.R
import com.huawei.localizacionsitemapa.base.BaseActivity
import kotlinx.android.synthetic.main.activity_location.*

class LocationCallBackActivity : BaseActivity() {

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    var mLocationCallback: LocationCallback? = null
    var mLocationRequest: LocationRequest? = null

    override fun getLayout(): Int = R.layout.activity_location

    override fun inicializar() {
        inicializarBotones()
        inicializarVariables()
        verificarPermisos()
    }

    private fun inicializarBotones() {

        bt_act_location.setOnClickListener {
            activarLocalizacionPorCallBack()
        }

        bt_desac_location.setOnClickListener {
            desactivarLocalizacionPorCallBack()
        }

    }

    private fun inicializarVariables() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsClient = LocationServices.getSettingsClient(this)
        mLocationCallback = obtenerLocationCallback()
        mLocationRequest = obtenerLocationRequest()
    }

    private fun desactivarLocalizacionPorCallBack() {
        val voidTask: Task<Void>? = mFusedLocationProviderClient?.removeLocationUpdates(mLocationCallback)
        voidTask?.addOnSuccessListener {
            mostrarMensaje("Desactivar Localizacion")
        }
    }

    private fun activarLocalizacionPorCallBack() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()
        val locationSettingsResponseTask = mSettingsClient?.checkLocationSettings(locationSettingsRequest)
        locationSettingsResponseTask?.addOnSuccessListener {

            mFusedLocationProviderClient?.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                Looper.getMainLooper())
                ?.addOnSuccessListener {
                    mostrarMensaje("Activar Localizacion")
                }

        }
    }

    private fun obtenerLocationRequest() : LocationRequest {
        var mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        return mLocationRequest
    }

    private fun obtenerLocationCallback() : LocationCallback {
        var mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult != null) {
                    val locations = locationResult.locations
                    if (locations.isNotEmpty()) {
                        for (location in locations) {
                            tv_lat_long.text = location.longitude.toString() + " " + location.latitude.toString()
                        }
                    }
                }
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                if (locationAvailability != null) {
                    val flag = locationAvailability.isLocationAvailable
                    tv_lat_long.text = flag.toString()
                }
            }
        }
        return mLocationCallback
    }

    override fun onDestroy() {
        desactivarLocalizacionPorCallBack()
        super.onDestroy()
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