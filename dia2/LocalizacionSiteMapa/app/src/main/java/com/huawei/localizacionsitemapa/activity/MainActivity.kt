package com.huawei.localizacionsitemapa.activity

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.hms.location.ActivityIdentification
import com.huawei.hms.location.ActivityIdentificationService
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationServices
import com.huawei.hms.site.api.SearchResultListener
import com.huawei.hms.site.api.SearchService
import com.huawei.hms.site.api.SearchServiceFactory
import com.huawei.hms.site.api.model.*
import com.huawei.hms.site.widget.SearchFilter
import com.huawei.hms.site.widget.SearchFragment
import com.huawei.hms.site.widget.SiteSelectionListener
import com.huawei.localizacionsitemapa.Constante
import com.huawei.localizacionsitemapa.R
import com.huawei.localizacionsitemapa.adapter.SiteAdapter
import com.huawei.localizacionsitemapa.base.BaseActivity
import com.huawei.localizacionsitemapa.broadcast.LocationBroadcastReceiver
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URLEncoder
import java.util.*


class MainActivity : BaseActivity(), SiteAdapter.SiteInterface {

    private var mFusedLocationClient : FusedLocationProviderClient? = null
    private var searchService: SearchService? = null
    private var pendingInt: PendingIntent? = null
    private var activityIdentificationService: ActivityIdentificationService? = null
    private var fragment : SearchFragment? = null
    private var latitud = Constante.EMPTY
    private var longitud = Constante.EMPTY

    private var adapterSite : SiteAdapter? = null

    init {
        activityIdent = this
    }

    companion object {
        lateinit var activityIdent: MainActivity
    }

    override fun getLayout(): Int = R.layout.activity_main

    // El metodo inicializar se ejecutara en onCreate
    override fun inicializar() {
        inicializarBusqueda()
        inicializarDetectorDeActividad()
        inicializarBotones()
        inicializarConfiguracionYProvider()
        verificarPermisos()
    }

    private fun inicializarDetectorDeActividad() {
        // Crear una actividad Identificacion
        activityIdentificationService = ActivityIdentification.getService(this);
        // Obtener instancia obtener Pending
        pendingInt = getPendingIntent()

    }

    private fun inicializarBusqueda() {
        // Para realizar la busqueda de lugares de Site Kit requerimos el Api key del archivo agconnect-services
        // debemos aplicar un encode al Api Key para evitar errores
        val encodedurl: String = URLEncoder.encode(getString(R.string.API_KEY_SITE), "UTF-8")
        searchService = SearchServiceFactory.create(baseContext, encodedurl)
        inicializarWidgetBusqueda(encodedurl)
    }

    private fun inicializarWidgetBusqueda(encodedurl: String) {
        // Para realizar la busqueda de lugares utilizando el fragmento predeterminado Site Kit
        // requerimos el Api key del archivo agconnect-services
        // debemos aplicar un encode al Api Key para evitar errores
        fragment = supportFragmentManager.findFragmentById(R.id.widget_fragment) as SearchFragment?
        fragment?.setApiKey(encodedurl)
        fragment?.setSearchFilter(obtenerOpcionesFiltro())
        fragment?.setOnSiteSelectedListener(object : SiteSelectionListener {
            override fun onSiteSelected(data: Site) {
                mostrarMensaje(data.getName())
                localizarSite(data)
            }

            override fun onError(status: SearchStatus) {
                mostrarMensaje("""${status.getErrorCode()}${status.getErrorMessage()}""".trimIndent())
            }
        })
    }

    private fun obtenerOpcionesFiltro(): SearchFilter {

        // Se puede personalizar la busqueda de lugares con SearchFilter
        // ejem indica el radio de busqueda de lugares o
        // el pais exclusivo en donde realizara la busqueda
        // en esta caso solo busquedas de Peru (PE)

        var search = SearchFilter()
        search.countryCode = "PE"
        search.radius = 800
        return search
    }

    private fun inicializarBotones() {

        bt_localizador.setOnClickListener {
            activarLocalizacion(Constante.REQUEST_MAP)
        }

        bt_buscarDireccion.setOnClickListener {
            activarLocalizacion(Constante.REQUEST_SITE)
        }

        bt_activarAct.setOnClickListener {
            activarReconocimientoActividades()
        }

        bt_desactivarAct.setOnClickListener {
            desactivarReconocimientoActividades()
        }

        bt_localizador_callback.setOnClickListener {
            irALocationCallBack()
        }

    }

    private fun irALocationCallBack() {
        var intent = Intent(this, LocationCallBackActivity::class.java)
        startActivity(intent)
    }

    private fun getPendingIntent(): PendingIntent? {
        val intent = Intent(this, LocationBroadcastReceiver::class.java)
        intent.action = Constante.ACTION_PROCESS_LOCATION
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun obtenerActividad(mensaje: String) {
        tv_actividad.text = mensaje
    }

    fun inicializarAdaptador(lista: ArrayList<Site>) {
        adapterSite = SiteAdapter(baseContext, lista)
        adapterSite?.mOnClickListener = this
        rv_sites.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(baseContext)
            adapter = adapterSite
        }
    }

    fun obtenerRequest() : QuerySuggestionRequest {

        // Se puede personalizar la busqueda de lugares con SearchFilter
        // ejem indica el radio de busqueda de lugares o
        // el pais exclusivo en donde realizara la busqueda
        // en esta caso solo busquedas de Peru (PE)

        var request = QuerySuggestionRequest()
        val location = Coordinate(latitud.toDouble(), longitud.toDouble())
        request.query = et_buscador.text.toString()
        request.setLocation(location)
        request.setCountryCode("PE")
        request.setRadius(800)
        return request

    }

    fun buscarLugares() {
        mostrarProgressBar()
        searchService?.querySuggestion(
            obtenerRequest(),
            object : SearchResultListener<QuerySuggestionResponse> {
                override fun onSearchResult(querySuggestionResponse: QuerySuggestionResponse?) {
                    ocultarProgressBar()
                    val siteList: List<Site>? = querySuggestionResponse?.getSites()
                    if (querySuggestionResponse == null || querySuggestionResponse.sites.isEmpty() || siteList.isNullOrEmpty()) {
                        mostrarMensaje("No hay lugares de busqueda")
                        return
                    }
                    siteList?.let {
                        inicializarAdaptador(it as ArrayList<Site>)
                    }
                }

                override fun onSearchError(searchStatus: SearchStatus) {
                    ocultarProgressBar()
                    mostrarMensaje("Error de Busqueda: " + searchStatus.errorCode)
                }
            })
    }

    private fun activarLocalizacion(request: String) {
        mostrarProgressBar()
        if(!tieneLocationPermisos()) {
            ocultarProgressBar()
            ActivityCompat.requestPermissions(this, LISTA_PERMISSION, Constante.REQUEST_CODE)
        } else {
            ocultarProgressBar()
            mFusedLocationClient?.lastLocation?.addOnSuccessListener {
                it?.let {
                    when (request) {
                        Constante.REQUEST_MAP -> {
                            // Ir a la actividad Mapa
                            enviarMapa(it)
                        }
                        Constante.REQUEST_SITE -> {
                            // Realizar busqueda de lugares
                            obtenerLatitudLongitud(it)
                            buscarLugares()
                        }
                    }
                }
            }
        }
    }

    private fun enviarMapa(it: Location) {
        obtenerLatitudLongitud(it)
        iniciarActividadMapa(latitud, longitud)
    }

    private fun obtenerLatitudLongitud(it: Location) {
        latitud = it.latitude.toString()
        longitud = it.longitude.toString()
    }

    private fun inicializarConfiguracionYProvider() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun desactivarReconocimientoActividades() {
        obtenerActividad("Actividad")
        activityIdentificationService?.deleteActivityIdentificationUpdates(pendingInt)
            ?.addOnSuccessListener {
                mostrarMensaje("Desactivar Actividad")
            }
    }

    private fun activarReconocimientoActividades() {
        activityIdentificationService?.createActivityIdentificationUpdates(1000, pendingInt)
            ?.addOnSuccessListener {
                mostrarMensaje("Activar Actividad")
            }
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

    override fun onClickSite(site: Site) {
        localizarSite(site)
    }

    private fun localizarSite(site: Site) {
        latitud = site.location.lat.toString()
        longitud = site.location.lng.toString()
        iniciarActividadMapa(latitud, longitud)
    }

    private fun iniciarActividadMapa(latitud: String, longitud: String) {
        var intent = Intent(this, MapaActivity::class.java).apply {
            putExtra("latitud", latitud)
            putExtra("longitud", longitud)
        }
        startActivity(intent)
    }

    fun mostrarProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun ocultarProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        if (pendingInt != null) {
            desactivarReconocimientoActividades()
        }
        super.onDestroy()
    }

}