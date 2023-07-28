package com.bedu.sportstore.repository.remote.firebase

import com.bedu.sportstore.model.entity.CarritoEntity
import com.google.firebase.firestore.FirebaseFirestore

class CompraDetalleReference(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : FireBaseCrud<CompraDetalle>(firestore.collection("compras_detalles"), CompraDetalle::class.java) {

}

data class CompraDetalle(
    val uid: String? = null,
    val compra: String,
    val productos: List<CarritoEntity>
)
