package com.example.levelup.data.model

data class FormularioError(
    var nombre: String? = null,
    var apellido: String? = null,
    var email: String? = null,
    var password: String? = null,
    var confirmarPassword: String? = null,
    var telefono: String? = null,
    var direccion: String? = null,
    var ciudad: String? = null,
    var errorGeneral: String? = null
) {
    val tieneErrores: Boolean
        get() = nombre != null || apellido != null || email != null ||
                password != null || confirmarPassword != null ||
                telefono != null || direccion != null || ciudad != null ||
                errorGeneral != null
}
