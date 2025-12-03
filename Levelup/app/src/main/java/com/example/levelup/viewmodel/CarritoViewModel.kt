package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.dao.CarritoDao
import com.example.levelup.data.dao.ProductDao
import com.example.levelup.data.model.CarritoItem
import com.example.levelup.data.model.Producto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarritoViewModel @Inject constructor(
    private val carritoDao: CarritoDao,
    private val productDao: ProductDao
) : ViewModel() {

    val carritoItems: StateFlow<List<CarritoItem>> = carritoDao.getCarritoItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun addProductoToCarrito(producto: Producto) {
        viewModelScope.launch {
            val existingItem = carritoDao.getItemByProductoId(producto.id)
            if (existingItem != null) {
                val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + 1)
                carritoDao.updateItem(updatedItem)
            } else {
                val newItem = CarritoItem(
                    productoId = producto.id,
                    nombre = producto.nombre,
                    precio = producto.precio,
                    cantidad = 1
                )
                carritoDao.insertItem(newItem)
            }
        }
    }

    fun removeProductoFromCarrito(item: CarritoItem) {
        viewModelScope.launch {
            carritoDao.deleteItem(item)
        }
    }

    fun procesarCompra(onCompraRealizada: () -> Unit) {
        viewModelScope.launch {
            val itemsAComprar = carritoItems.value
            if (itemsAComprar.isEmpty()) return@launch

            try {
                itemsAComprar.forEach { item ->
                    productDao.updateStock(item.productoId, item.cantidad)
                }
                carritoDao.clearCarrito()
                onCompraRealizada()
            } catch (e: Exception) {
                println("Error al procesar la compra: ${e.message}")
            }
        }
    }
}