package com.bedu.sportstore.model.response

import com.bedu.sportstore.model.entity.UsuarioEntity

data class AuthResponse(val message: String, val success: Boolean, val token: String?, val usuario: UsuarioEntity?)
