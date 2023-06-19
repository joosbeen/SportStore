package com.bedu.sportstore.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bedu.sportstore.R
import com.bedu.sportstore.db.Compra

class HistorialCompraAdapter(
    private val productos: List<Compra>
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

        fun bind(compra: Compra) {
            histCompOutFolio.text = compra.folio
            histCompOutCosto.text = "$ ${compra.costo.toString()} MXN"
            histCompOutFecha.text = compra.fecha
            histCompOutEstado.text = compra.estado
        }

    }

}