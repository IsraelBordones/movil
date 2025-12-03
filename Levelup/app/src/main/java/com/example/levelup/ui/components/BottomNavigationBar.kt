package com.example.levelup.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.levelup.navigation.Destinations

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    // Ahora la lista de items se basa directamente en nuestra clase sellada `Destinations`
    val items = listOf(
        Destinations.HomeScreen,
        Destinations.CatalogoScreen,
        Destinations.SearchScreen,
        Destinations.CarritoScreen, // <-- AÑADIDO
        Destinations.FavsScreen,
        Destinations.PerfilScreen
    )

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = { onNavigate(screen.route) }
            )
        }
    }
}