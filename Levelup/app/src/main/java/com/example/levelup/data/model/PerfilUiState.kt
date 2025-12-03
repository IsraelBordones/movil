package com.example.levelup.data.model

/**
 * Representa el estado de la UI para la pantalla de Perfil.
 */
data class PerfilUiState(
    val usuario: Usuario? = null,         // Los datos del usuario actual
    val enModoEdicion: Boolean = false,    // Controla si la UI está en modo vista o edición
    val isLoading: Boolean = true,         // Para mostrar un indicador de carga mientras se obtienen los datos
    val error: String? = null              // Para mostrar cualquier error que ocurra
)
