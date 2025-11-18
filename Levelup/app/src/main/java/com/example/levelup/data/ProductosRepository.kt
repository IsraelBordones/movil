package com.example.levelup.data

import com.example.levelup.model.Producto
import kotlinx.coroutines.delay

class ProductosRepository {

    private val productosEjemplo = listOf(
        Producto(
            id = "1",
            nombre = "SilkSong",
            descripcion = "Metroidvania / Indie",
            precio = 10500.0,
            categoria = "NOVEDADES",
            stock = 10
        ),
        Producto(
            id = "2",
            nombre = "Hades II",
            descripcion = "Rogueliike / INDIE",
            precio = 15500.0,
            categoria = "NOVEDADES",
            stock = 10
        ),
        Producto(
            id = "3",
            nombre = "Battlefield 6",
            descripcion = "Shoother / Accion",
            precio = 60000.0,
            categoria = "NOVEDADES",
            stock = 10
        ),
        Producto(
            id = "4",
            nombre = "ARC RIDERS",
            descripcion = "Shooters / PvP",
            precio = 30000.0,
            categoria = "NOVEDADES",
            stock = 10
        ),
        Producto(
            id = "5",
            nombre = "Hollow knight",
            descripcion = "Metroidvania / Accion",
            precio = 4500.0,
            categoria = "OFERTAS",
            stock = 10
        ),
        Producto(
            id = "6",
            nombre = "Call of duty",
            descripcion = "Shooters / PvP",
            precio = 39990.0,
            categoria = "OFERTAS",
            stock = 10
        ),
        Producto(
            id = "7",
            nombre = "Minecraft",
            descripcion = "Sandbox",
            precio = 9990.0,
            categoria = "OFERTAS",
            stock = 10

        )
    )

    suspend fun obtenerProductos(): List<Producto> {
        delay(500) // Simular carga de red
        return productosEjemplo
    }

    suspend fun obtenerProductosPorCategoria(categoria: String): List<Producto> {
        delay(300)
        return productosEjemplo.filter { it.categoria == categoria }
    }

    suspend fun buscarProductos(query: String): List<Producto> {
        delay(300)
        val queryLower = query.lowercase()
        return productosEjemplo.filter {
            it.nombre.lowercase().contains(queryLower) ||
                    it.descripcion.lowercase().contains(queryLower) ||
                    it.categoria.lowercase().contains(queryLower)
        }
    }

    fun obtenerCategorias(): List<String> {
        return productosEjemplo.map { it.categoria }.distinct()
    }
}

