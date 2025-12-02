package com.example.levelup.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.levelup.navigation.Destinations

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        NavigationItem(Destinations.HomeScreen.route, "Home", Icons.Default.Home),
        NavigationItem(Destinations.FavsScreen.route, "Favs", Icons.Default.Star),
        NavigationItem(Destinations.SearchScreen.route, "Search", Icons.Default.Search),
        NavigationItem(Destinations.PerfilScreen.route, "Perfil", Icons.Default.Person),
        NavigationItem(Destinations.CatalogoScreen.route, "Catálogo", Icons.Default.ShoppingCart)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) }
            )
        }
    }
}

data class NavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
