package com.example.levelup.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

// Objeto sellado para las rutas de la barra de navegación inferior
sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object HomeScreen : BottomBarScreen("home", "Inicio", Icons.Default.Home)
    object CatalogoScreen : BottomBarScreen("catalogo", "Catálogo", Icons.Default.MenuBook)
    object SearchScreen : BottomBarScreen("search", "Buscar", Icons.Default.Search)
    object FavsScreen : BottomBarScreen("favs", "Favoritos", Icons.Default.Favorite)
    object CarritoScreen : BottomBarScreen("carrito", "Carrito", Icons.Default.ShoppingCart)
    object PerfilScreen : BottomBarScreen("perfil", "Perfil", Icons.Default.Person)
}

// Objeto para todas las demás rutas y grafos de navegación
object Routes {
    // Grafo de Autenticación
    const val AUTH_GRAPH = "auth_graph"
    const val LOGIN = "login"
    const val REGISTER = "register"

    // Grafo Principal
    const val MAIN_GRAPH = "main_graph"

    // Pantalla de Edición de Producto (con argumento)
    const val EDIT_PRODUCT = "edit_product/{productId}"

    // Función para construir la ruta de edición con un ID específico
    fun getEditProductRoute(productId: Int) = "edit_product/$productId"
}
