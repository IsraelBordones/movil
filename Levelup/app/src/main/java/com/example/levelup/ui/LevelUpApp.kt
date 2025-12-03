package com.example.levelup.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.levelup.navigation.Destinations
import com.example.levelup.ui.components.BottomNavigationBar
import com.example.levelup.ui.screen.CatalogoScreen
import com.example.levelup.ui.screen.FavsScreen
import com.example.levelup.ui.screen.InicioScreen
import com.example.levelup.ui.screen.PerfilScreen
import com.example.levelup.ui.screen.SearchScreen
import com.example.levelup.ui.screen.CarritoScreen
import com.example.levelup.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpApp(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Destinations.HomeScreen.route
            ) {
                composable(Destinations.HomeScreen.route) {
                    // El PostViewModel ahora se obtiene con Hilt
                    val postViewModel: PostViewModel = hiltViewModel()
                    InicioScreen(viewModel = postViewModel, navController = navController)
                }
                composable(Destinations.CatalogoScreen.route) {
                    CatalogoScreen(onLogout = onLogout)
                }
                composable(Destinations.SearchScreen.route) { SearchScreen() }
                composable(Destinations.CarritoScreen.route) { CarritoScreen() }
                composable(Destinations.FavsScreen.route) { FavsScreen() }
                composable(Destinations.PerfilScreen.route) { PerfilScreen() }
            }
        }
    }
}