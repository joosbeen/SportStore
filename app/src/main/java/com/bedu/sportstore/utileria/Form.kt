package com.bedu.sportstore.utileria

import android.util.Patterns
import android.widget.EditText
import java.util.regex.Pattern


object Form {

    /**
     * Validar si texto no es null, vacio o solo contiene caracteres de espacios.
     *  text: texto a validar.
     */
    fun isInvalidText(text: String) = (text.isNullOrBlank() || text.isEmpty())

    /**
     * Validar Si el formato de correo valido
     */
    fun validarEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    /**
     * Obtener texto de EditText aplicando trim.
     */
    fun getTextFromEdit(editText: EditText): String  = editText.text.toString().trim()

}