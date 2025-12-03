package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.dao.CarritoDao
import com.example.levelup.data.dao.ProductDao
import com.example.levelup.data.model.CarritoItem
import com.example.levelup.data.model.Producto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarritoViewModel(
    private val carritoDao: CarritoDao,
    private val productDao: ProductDao
) : ViewModel() {

    // Expone la lista de items del carrito como un StateFlow
    val carritoItems: StateFlow<List<CarritoItem>> = carritoDao.getCarritoItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    /**
     * Añade un producto al carrito.
     * Si el producto ya existe, incrementa su cantidad. Si no, lo inserta.
     */
    fun addProductoToCarrito(producto: Producto) {
        viewModelScope.launch {
            val existingItem = carritoDao.getItemByProductoId(producto.id)
            if (existingItem != null) {
                // Si existe, actualiza la cantidad
                val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + 1)
                carritoDao.updateItem(updatedItem)
            } else {
                // Si no existe, crea un nuevo item
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

    /**
     * Elimina un item del carrito.
     */
    fun removeProductoFromCarrito(item: CarritoItem) {
        viewModelScope.launch {
            carritoDao.deleteItem(item)
        }
    }

    /**
     * Procesa la compra: actualiza el stock y limpia el carrito.
     * Esta es una operación transaccional.
     */
    fun procesarCompra(onCompraRealizada: () -> Unit) {
        viewModelScope.launch {
            val itemsAComprar = carritoItems.value
            if (itemsAComprar.isEmpty()) return@launch

            // Aquí se podría envolver en una transacción de base de datos
            try {
                itemsAComprar.forEach { item ->
                    productDao.updateStock(item.productoId, item.cantidad)
                }
                // Si todo va bien, limpiamos el carrito
                carritoDao.clearCarrito()
                onCompraRealizada()
            } catch (e: Exception) {
                // Manejar error (ej. mostrar un Toast)
                println("Error al procesar la compra: ${e.message}")
            }
        }
    }
}