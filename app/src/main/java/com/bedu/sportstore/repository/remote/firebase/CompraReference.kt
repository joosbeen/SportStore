package com.bedu.sportstore.repository.remote.firebase

import com.bedu.sportstore.model.entity.CarritoEntity
import com.google.firebase.firestore.FirebaseFirestore

class CompraReference(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : FireBaseCrud<CompraCollection>(firestore.collection("compras"), CompraCollection::class.java) {

    fun findByUser(userId: String, onComplete: (task: TaskResources) -> Unit) {
        firestore.collection("compras")
            .whereEqualTo("usuario", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                onComplete(
                    TaskResources(
                        true,
                        querySnapshot.toObjects(CompraCollection::class.java)
                    )
                )
            }
            .addOnFailureListener { e ->
                onComplete(
                    TaskResources(
                        false
                    )
                )
            }
    }

}

data class CompraCollection(
    val uid: String? = null,
    val folio: Long = 0,
    val usuario: String = "",
    val subtotal: Float = 0f,
    val envio: Float = 0f,
    val estado: String = "",
    val productos: List<CarritoEntity> = listOf()
)

data class TaskResources(val isSuccessful: Boolean, val compras: List<CompraCollection>? = null)