package com.example.levelup.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.levelup.navigation.BottomBarScreen

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val screens = listOf(
        BottomBarScreen.HomeScreen,
        BottomBarScreen.CatalogoScreen,
        BottomBarScreen.SearchScreen,
        BottomBarScreen.CarritoScreen,
        BottomBarScreen.FavsScreen,
        BottomBarScreen.PerfilScreen
    )

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = { onNavigate(screen.route) }
            )
        }
    }
}