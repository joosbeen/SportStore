package com.bedu.sportstore.core.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class CartCounterReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // Aquí se maneja la recepción del broadcast y se actualiza el contador del carrito
        // Puedes obtener información adicional del intent si es necesario
        // Actualiza el contador del carrito según tus necesidades
        Log.i("BroadcastReceiver", "onReceive: recepción del broadcast")
    }

}