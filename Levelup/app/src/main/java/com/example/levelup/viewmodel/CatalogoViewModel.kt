package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.dao.ProductDao
import com.example.levelup.data.model.CatalogoUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CatalogoViewModel(productDao: ProductDao) : ViewModel() {

    // Expone el estado del catálogo, convirtiendo la lista de productos en un `CatalogoUiState`.
    val uiState: StateFlow<CatalogoUiState> = productDao.getAllProducts()
        .map { productos -> CatalogoUiState(productos) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CatalogoUiState() // Estado inicial con lista vacía
        )
}
