package com.example.levelup.model

data class CatalogoUiState(
    val productos: List<Producto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val categoriaSeleccionada: String? = null,
    val busqueda: String = ""
)

