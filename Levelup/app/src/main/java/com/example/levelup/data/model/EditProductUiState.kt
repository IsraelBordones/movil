package com.example.levelup.data.model

/**
 * Representa el estado de la UI para la pantalla de Edición de Producto.
 */
data class EditProductUiState(
    val producto: Producto? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)
