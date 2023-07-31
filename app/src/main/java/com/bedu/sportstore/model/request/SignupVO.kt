package com.bedu.sportstore.model.request

data class SignupVO(
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val contrasena_c: String,
    val user: String
)