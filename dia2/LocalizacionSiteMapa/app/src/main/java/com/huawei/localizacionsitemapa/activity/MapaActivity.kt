package com.huawei.localizacionsitemapa.activity

import com.huawei.localizacionsitemapa.R
import com.huawei.localizacionsitemapa.base.BaseActivity
import com.huawei.localizacionsitemapa.base.BaseFragment
import com.huawei.localizacionsitemapa.fragment.MapaFragment

class MapaActivity: BaseActivity() {

    private var fragment: BaseFragment? = null

    override fun getLayout(): Int = R.layout.activity_mapa

    override fun inicializar() {
        inicializarPrimerFragment()
    }

    private fun inicializarPrimerFragment() {
        fragment = MapaFragment()
        fragment?.arguments = intent.extras
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment as MapaFragment).commit()
    }

}