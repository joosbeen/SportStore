package com.bedu.sportstore.ui.main.historial_compra.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bedu.sportstore.R
import com.bedu.sportstore.db.Compra
import com.bedu.sportstore.repository.remote.firebase.CompraCollection
import com.bedu.sportstore.utileria.Utility
import java.util.Date

class HistorialCompraAdapter(
    private val productos: List<CompraCollection>
) : RecyclerView.Adapter<HistorialCompraAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_historial_compra, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = productos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val compra = productos[position]
        holder.bind(compra)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val histCompOutFolio: TextView = view.findViewById(R.id.histCompOutFolio)
        private val histCompOutCosto: TextView = view.findViewById(R.id.histCompOutCosto)
        private val histCompOutFecha: TextView = view.findViewById(R.id.histCompOutFecha)
        private val histCompOutEstado: TextView = view.findViewById(R.id.histCompOutEstado)

        fun bind(compra: CompraCollection) {
            Log.i("loadhistory", "cargarHistorialCompras: adapter = $compra")
            histCompOutFolio.text = compra.folio.toString()
            histCompOutCosto.text = "$ ${compra.subtotal+compra.envio} MXN"
            histCompOutFecha.text = Utility.getDate_ddMMYYYY(Date(compra.folio))
            histCompOutEstado.text = compra.estado
        }

    }

}