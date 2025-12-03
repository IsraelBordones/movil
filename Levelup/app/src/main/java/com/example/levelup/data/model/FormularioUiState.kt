package com.example.levelup.data.model

// NO SE NECESITA IMPORTAR FormularioError PORQUE ESTÁN EN EL MISMO PAQUETE

data class FormularioUiState(
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val password: String = "",
    val confirmarPassword: String = "",
    val telefono: String = "",
    val direccion: String = "",
    val ciudad: String = "",
    val esLogin: Boolean = true, // true = Login, false = Registro
    val errores: FormularioError = FormularioError(),
    val isLoading: Boolean = false,
    val esFormularioValido: Boolean = false
)
