package com.huawei.localizacionsitemapa.fragment

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.model.*
import com.huawei.localizacionsitemapa.Constante
import com.huawei.localizacionsitemapa.R
import com.huawei.localizacionsitemapa.base.BaseFragment


class MapaFragment : BaseFragment(), OnMapReadyCallback, HuaweiMap.OnMapLoadedCallback, HuaweiMap.OnMarkerClickListener {

    private val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    private var mMapView: MapView? = null
    private var hMap: HuaweiMap? = null
    private var circulo: Circle? = null

    override fun getLayout(): Int = R.layout.fragment_mapa

    override fun inicializar(view: View, savedInstanceState: Bundle?) {

        solicitarPermisosLocalizacion()

        var mapViewBundle: Bundle? = null
        mMapView = view.findViewById(R.id.mapViewMain)
        savedInstanceState?.let {
            mapViewBundle = it.getBundle(MAPVIEW_BUNDLE_KEY)
        }

        mMapView?.onCreate(mapViewBundle)
        mMapView?.getMapAsync(this)

    }

    override fun onMapReady(map: HuaweiMap?) {
        hMap = map
        context?.let { activarDesActivarBotonLocalizacion(esGPSActivado(it)) }
        hMap?.setOnMapLoadedCallback(this)
    }

    override fun onMapLoaded() {
        obtenerBundle()
        hMap?.setOnMarkerClickListener(this)
    }

    private fun obtenerBundle() {
        var latitudData = arguments?.getString(Constante.LATITUD)
        var longitudData = arguments?.getString(Constante.LONGITUD)
        longitudData?.let {
            latitudData?.let {
                    it1 -> agregarMaker(it1, it)
                agregarCirculo(it1, it)
            }
        }
    }

    private fun activarDesActivarBotonLocalizacion(boolean: Boolean) {
        hMap?.isMyLocationEnabled = boolean // Superposicion localizacion
        hMap?.uiSettings?.isMyLocationButtonEnabled = boolean // Icono de localizacion
    }

    private fun agregarCirculo(latitud: String, longitud: String) {
        hMap?.let {
            if (null != circulo) {
                circulo?.remove()
            }
            circulo = hMap?.addCircle(
                CircleOptions()
                    .center(LatLng(latitud.toDouble(), longitud.toDouble()))
                    .radius(250.0)
                    .strokeColor(Color.RED)
            )
        }
    }

    private fun agregarMaker(latitud: String, longitud: String) {
        val zoom = 12f
        val positionLatLng = LatLng(latitud.toDouble(), longitud.toDouble())
        val mapOption = MarkerOptions().position(positionLatLng)
        hMap?.addMarker(mapOption)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(positionLatLng, zoom)
        hMap?.animateCamera(cameraUpdate)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView?.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        mMapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
    }

    override fun onStop() {
        super.onStop()
        mMapView?.onStop()
    }

    private fun solicitarPermisosLocalizacion() {
        if (!hasLocationPermission()) {
            activity?.let { ActivityCompat.requestPermissions(it, LISTA_PERMISSION, Constante.REQUEST_CODE) }
        }
    }

    private fun hasLocationPermission(): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for (permission in LISTA_PERMISSION) {
                if (activity?.let { ActivityCompat.checkSelfPermission(it, permission) } != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        Toast.makeText(context, "Click Marcador", Toast.LENGTH_SHORT).show()
        return false
    }


}