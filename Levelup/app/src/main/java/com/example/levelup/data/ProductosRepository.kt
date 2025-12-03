package com.example.levelup.data

import com.example.levelup.data.dao.ProductDao
import com.example.levelup.data.model.Producto
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio que maneja el acceso a los datos de los productos.
 * Es la única fuente de verdad para los datos de productos en la app.
 */
class ProductosRepository(private val productDao: ProductDao) {

    /**
     * Obtiene todos los productos de la base de datos como un Flow.
     * La UI puede observar este Flow para reaccionar a los cambios automáticamente.
     */
    fun getAllProducts(): Flow<List<Producto>> = productDao.getAllProducts()

    /**
     * Inserta una lista de productos en la base de datos.
     * Esta función se podría usar para poblar la base de datos inicialmente o para actualizaciones.
     */
    suspend fun insertAll(productos: List<Producto>) {
        productDao.insertAll(productos)
    }
}
