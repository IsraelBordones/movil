package com.example.levelup.model

data class PerfilUiState(
    val usuario: Usuario? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val mostrarDialogoEditar: Boolean = false
)

