package com.bedu.sportstore.model.response

data class AuthResponse(val message: String, val success: Boolean, val token: String?, val usuario: UsuarioResponse?)
