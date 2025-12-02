package com.example.levelup.data

import com.example.levelup.data.dao.ProductDao
import com.example.levelup.data.model.Producto // <-- ¡ESTA ES LA CORRECCIÓN DEFINITIVA!
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ProductosRepository(private val productDao: ProductDao) {

    val todosLosProductos: Flow<List<Producto>> = productDao.getAllProducts()

    suspend fun obtenerProductos(): List<Producto> {
        return productDao.getAllProducts().first()
    }

    suspend fun obtenerProductosPorCategoria(categoria: String): List<Producto> {
        val productos = productDao.getAllProducts().first()
        return productos.filter { it.categoria.equals(categoria, ignoreCase = true) }
    }

    suspend fun buscarProductos(query: String): List<Producto> {
        val queryLower = query.lowercase()
        val productos = productDao.getAllProducts().first()
        return productos.filter {
            it.nombre.lowercase().contains(queryLower) ||
                    it.descripcion.lowercase().contains(queryLower) ||
                    it.categoria.lowercase().contains(queryLower)
        }
    }

    suspend fun obtenerCategorias(): List<String> {
        val productos = productDao.getAllProducts().first()
        return productos.map { it.categoria }.distinct()
    }
}
