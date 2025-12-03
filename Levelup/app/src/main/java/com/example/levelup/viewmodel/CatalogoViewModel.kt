package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.ProductosRepository
import com.example.levelup.data.model.CatalogoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CatalogoViewModel @Inject constructor(
    productosRepository: ProductosRepository
) : ViewModel() {

    val uiState: StateFlow<CatalogoUiState> = productosRepository.getAllProducts()
        .map { productos -> CatalogoUiState(productos) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CatalogoUiState()
        )
}
