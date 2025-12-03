package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.ProductosRepository
import com.example.levelup.data.model.Producto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PostViewModel(repository: ProductosRepository) : ViewModel() {

    // Expone el flujo de productos directamente desde el repositorio.
    // El `stateIn` lo convierte en un StateFlow que se puede observar en la UI.
    val productos: StateFlow<List<Producto>> = repository.getAllProducts()
        .stateIn(
            scope = viewModelScope,
            // `WhileSubscribed` hace que el flujo esté activo solo cuando hay observadores.
            started = SharingStarted.WhileSubscribed(5000L),
            // El valor inicial es una lista vacía hasta que la base de datos emita el primer valor.
            initialValue = emptyList()
        )
}
