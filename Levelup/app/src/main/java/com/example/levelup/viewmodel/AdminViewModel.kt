package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.dao.ProductDao
import com.example.levelup.data.model.EditProductUiState
import com.example.levelup.data.model.Producto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {

    private val _editUiState = MutableStateFlow(EditProductUiState())
    val editUiState: StateFlow<EditProductUiState> = _editUiState.asStateFlow()

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _editUiState.update { it.copy(isLoading = true) }
            val product = productDao.getProductById(productId)
            if (product != null) {
                _editUiState.update { it.copy(producto = product, isLoading = false) }
            } else {
                _editUiState.update { it.copy(error = "Producto no encontrado", isLoading = false) }
            }
        }
    }

    fun onProductChange(nombre: String, descripcion: String, precio: String, stock: String) {
        _editUiState.value.producto?.let {
            val updatedProduct = it.copy(
                nombre = nombre,
                descripcion = descripcion,
                precio = precio.toDoubleOrNull() ?: 0.0,
                stock = stock.toIntOrNull() ?: 0
            )
            _editUiState.update { state -> state.copy(producto = updatedProduct) }
        }
    }

    fun updateProduct(onSuccess: () -> Unit) {
        _editUiState.value.producto?.let {
            viewModelScope.launch {
                productDao.updateProduct(it)
                onSuccess()
            }
        }
    }

    fun deleteProduct(product: Producto) {
        viewModelScope.launch {
            productDao.deleteProduct(product)
        }
    }
}