package com.huawei.localizacionsitemapa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huawei.hms.site.api.model.Site
import com.huawei.localizacionsitemapa.R
import kotlinx.android.synthetic.main.adapter_item.view.*

class SiteAdapter(var context: Context, private var lista : ArrayList<Site>) :
    RecyclerView.Adapter<SiteAdapter.SiteHolder>() {

    var mOnClickListener : SiteInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteHolder {
        return SiteHolder(LayoutInflater.from(context).inflate(R.layout.adapter_item,
            parent, false))
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: SiteHolder, position: Int) {
        holder?.tvNombre?.text = lista[position].name + " " + lista[position].formatAddress.toString()
        holder?.tvNombre?.setOnClickListener {
            mOnClickListener?.onClickSite(lista[position])
        }
    }

    fun clear(){
        lista.clear()
        notifyDataSetChanged()
    }

    class SiteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre = itemView.tv_nombre
    }

    interface SiteInterface {
        fun onClickSite(site: Site)
    }

}