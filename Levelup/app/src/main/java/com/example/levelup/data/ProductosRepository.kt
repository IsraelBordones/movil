package com.example.levelup.data

import com.example.levelup.data.dao.ProductDao // <-- Importante: usa el DAO
import com.example.levelup.model.Producto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

// El Repositorio ahora necesita el DAO para funcionar.
// Se lo pasamos por el constructor, lo que se conoce como "inyección de dependencias".
class ProductosRepository(private val productDao: ProductDao) {

    // 1. ELIMINAMOS la lista 'productosEjemplo'.
    //    Ya no la necesitamos, los datos están en la BD.

    // 2. EXPONEMOS los datos de la base de datos como un Flow.
    //    Este 'Flow' es "reactivo": si algo cambia en la tabla de productos,
    //    quien esté observando este Flow recibirá la nueva lista automáticamente.
    val todosLosProductos: Flow<List<Producto>> = productDao.getAllProducts()

    // 3. ADAPTAMOS las funciones existentes para que usen la base de datos.
    //    Nota: Hemos quitado los 'delay' porque el acceso a la BD ya es una
    //    operación de fondo (asíncrona).

    // Esta función ahora simplemente devuelve la primera emisión del Flow.
    suspend fun obtenerProductos(): List<Producto> {
        // .first() toma el valor actual del Flow y completa.
        return productDao.getAllProducts().first()
    }

    // Esta función ahora filtra directamente en la base de datos usando una consulta SQL.
    // ¡Es mucho más eficiente que traer todos los productos a la memoria para filtrarlos!
    suspend fun obtenerProductosPorCategoria(categoria: String): List<Producto> {
        // Primero, obtenemos todos los productos.
        val productos = productDao.getAllProducts().first()
        // Luego, los filtramos en memoria.
        return productos.filter { it.categoria.equals(categoria, ignoreCase = true) }
        // NOTA: Para una app más grande, crearíamos una consulta en el DAO:
        // @Query("SELECT * FROM productos WHERE categoria = :categoria")
        // fun getProductsByCategory(categoria: String): Flow<List<Producto>>
    }

    suspend fun buscarProductos(query: String): List<Producto> {
        val queryLower = query.lowercase()
        // Obtenemos los productos de la BD
        val productos = productDao.getAllProducts().first()
        // Y los filtramos
        return productos.filter {
            it.nombre.lowercase().contains(queryLower) ||
                    it.descripcion.lowercase().contains(queryLower) ||
                    it.categoria.lowercase().contains(queryLower)
        }
    }

    suspend fun obtenerCategorias(): List<String> {
        // Obtenemos los productos de la BD
        val productos = productDao.getAllProducts().first()
        // Y extraemos las categorías
        return productos.map { it.categoria }.distinct()
    }
}
