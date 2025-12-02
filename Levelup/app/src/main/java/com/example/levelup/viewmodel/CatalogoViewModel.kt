package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.ProductosRepository
import com.example.levelup.data.model.CatalogoUiState // <-- ¡CORREGIDO!
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogoViewModel(private val repository: ProductosRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CatalogoUiState())
    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    // 1. AÑADIMOS EL STATEFLOW PARA LAS CATEGORÍAS
    private val _categorias = MutableStateFlow<List<String>>(emptyList())
    val categorias: StateFlow<List<String>> = _categorias.asStateFlow()

    init {
        cargarProductos()
        // 2. LLAMAMOS A LA FUNCIÓN PARA CARGAR LAS CATEGORÍAS
        cargarCategorias()
    }

    // 3. CREAMOS LA FUNCIÓN QUE CARGA LAS CATEGORÍAS DESDE EL REPOSITORIO
    private fun cargarCategorias() {
        viewModelScope.launch {
            try {
                _categorias.value = repository.obtenerCategorias()
            } catch (e: Exception) {
                // En caso de error, la lista de categorías estará vacía
                _categorias.value = emptyList()
            }
        }
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val productos = repository.obtenerProductos()
                _uiState.update {
                    it.copy(
                        productos = productos,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar productos: ${e.message}"
                    )
                }
            }
        }
    }

    fun filtrarPorCategoria(categoria: String?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    categoriaSeleccionada = categoria,
                    isLoading = true
                )
            }
            try {
                val productos = if (categoria != null) {
                    repository.obtenerProductosPorCategoria(categoria)
                } else {
                    repository.obtenerProductos()
                }
                _uiState.update {
                    it.copy(
                        productos = productos,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al filtrar productos: ${e.message}"
                    )
                }
            }
        }
    }

    fun buscarProductos(query: String) {
        _uiState.update { it.copy(busqueda = query) }
        viewModelScope.launch {
            if (query.isBlank()) {
                cargarProductos()
            } else {
                try {
                    val productos = repository.buscarProductos(query)
                    _uiState.update {
                        it.copy(
                            productos = productos,
                            isLoading = false
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Error al buscar productos: ${e.message}"
                        )
                    }
                }
            }
        }
    }

    // 4. ELIMINAMOS LA FUNCIÓN ANTIGUA, YA NO SE NECESITA
    // fun obtenerCategorias(): List<String> { ... }
}
