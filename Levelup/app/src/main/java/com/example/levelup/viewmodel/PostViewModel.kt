package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// 1. IMPORTAMOS LAS CLASES CORRECTAS
import com.example.levelup.data.ProductosRepository // <-- Tu repositorio de productos
import com.example.levelup.model.Producto             // <-- Tu modelo de producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 2. CAMBIO CLAVE: El ViewModel ahora pide ProductosRepository en su constructor.
//    Esto es la "inyección de dependencias".
class PostViewModel(private val repository: ProductosRepository) : ViewModel() {

    // 3. CAMBIAMOS EL ESTADO: Ahora guardará una lista de 'Producto', no de 'Post'.
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    // El bloque 'init' se ejecuta una sola vez cuando el ViewModel se crea.
    init {
        // 4. CARGAMOS LOS PRODUCTOS: Iniciamos una corutina para obtener los productos.
        viewModelScope.launch {
            // 'repository.todosLosProductos' es el Flow que viene directamente de la base de datos.
            // '.collect' se quedará "escuchando" para siempre. Si un producto se añade,
            // elimina o actualiza en la base de datos, este código se ejecutará de nuevo
            // y la UI se actualizará automáticamente.
            repository.todosLosProductos.collect { listaDeProductosDeLaBD ->
                _productos.value = listaDeProductosDeLaBD
            }
        }
    }

    // Por ahora, dejamos las funciones de crear post fuera.
    // Más adelante, podríamos añadir funciones para "añadir al carrito", etc.
}
