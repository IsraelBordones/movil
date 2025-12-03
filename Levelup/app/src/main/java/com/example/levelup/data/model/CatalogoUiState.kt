package com.example.levelup.data.model // <-- PAQUETE CORREGIDO

import com.example.levelup.data.model.Producto

data class CatalogoUiState(
    val productos: List<Producto> = emptyList()
)
