package com.example.levelup.data.model // <-- PAQUETE CORREGIDO

data class FormularioError(
    val nombre: String? = null,
    val apellido: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirmarPassword: String? = null,
    val telefono: String? = null,
    val direccion: String? = null,
    val ciudad: String? = null,
    val errorGeneral: String? = null // <-- CAMPO AÑADIDO
) {
    val tieneErrores: Boolean
        get() = nombre != null || apellido != null || email != null ||
                password != null || confirmarPassword != null ||
                telefono != null || direccion != null || ciudad != null ||
                errorGeneral != null
}
