package com.example.levelup.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object HomeScreen : Destinations("home", "Inicio", Icons.Default.Home)
    object CatalogoScreen : Destinations("catalogo", "Catálogo", Icons.Default.MenuBook)
    object SearchScreen : Destinations("search", "Buscar", Icons.Default.Search)
    object FavsScreen : Destinations("favs", "Favoritos", Icons.Default.Favorite)
    object CarritoScreen : Destinations("carrito", "Carrito", Icons.Default.ShoppingCart) // <-- AÑADIDO
    object PerfilScreen : Destinations("perfil", "Perfil", Icons.Default.Person)
}
