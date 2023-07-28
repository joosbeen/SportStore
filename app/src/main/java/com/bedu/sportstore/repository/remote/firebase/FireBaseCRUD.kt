package com.bedu.sportstore.repository.remote.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

open class FireBaseCrud<T : Any>(private val collection: CollectionReference, private val dataType: Class<T>) {

    // Agregar un documento a la colección
    fun create(data: T, onComplete: (task: TaskResource) -> Unit) {
        collection
            .add(data)
            .addOnSuccessListener { documentReference ->
                onComplete(TaskResource(true, documentReference))
            }
            .addOnFailureListener { exception ->
                onComplete(TaskResource(false))
            }
    }

    // Obtener todos los documentos de la colección
    fun readAll(onComplete: (data: List<T>) -> Unit) {
        collection
            .get()
            .addOnSuccessListener { querySnapshot ->
                val dataList = querySnapshot.toObjects(dataType)
                onComplete(dataList)
            }
            .addOnFailureListener { exception ->
                onComplete(emptyList())
            }
    }

    // Actualizar un documento en la colección
    fun update(documentId: String, data: T, onComplete: (isSuccessful: Boolean) -> Unit) {
        collection
            .document(documentId)
            .set(data)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }

    // Eliminar un documento de la colección
    fun delete(documentId: String, onComplete: (isSuccessful: Boolean) -> Unit) {
        collection
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }
}

data class TaskResource(val isSuccessful: Boolean, val documentReference: DocumentReference? = null)