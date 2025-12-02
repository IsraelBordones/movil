package com.example.levelup.data.model // <-- CORREGIDO

// No se necesita importar Producto si está en el mismo paquete

data class CatalogoUiState(
    val productos: List<Producto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val categoriaSeleccionada: String? = null,
    val busqueda: String = ""
)
