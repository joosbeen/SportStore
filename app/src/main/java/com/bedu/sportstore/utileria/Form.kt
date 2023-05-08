package com.bedu.sportstore.utileria

object Form {

    /**
     * Validar si texto no es null, vacio o solo contiene caracteres de espacios.
     *  text: texto a validar.
     */
    fun isInvalidText(text: String) = (text.isNullOrBlank() || text.isEmpty())

}