package com.example.levelup.model

data class FormularioError(
    val nombre: String? = null,
    val apellido: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirmarPassword: String? = null,
    val telefono: String? = null,
    val direccion: String? = null,
    val ciudad: String? = null
) {
    val tieneErrores: Boolean
        get() = nombre != null || apellido != null || email != null || 
                password != null || confirmarPassword != null || 
                telefono != null || direccion != null || ciudad != null
}

