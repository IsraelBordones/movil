package com.example.levelup.data.model

data class PerfilUiState(
    val usuario: Usuario? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val mostrarDialogoEditar: Boolean = false // <-- AÑADIDO
)
