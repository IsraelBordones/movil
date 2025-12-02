package com.example.levelup.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.levelup.navigation.Destinations
// 1. IMPORTAMOS ÚNICAMENTE LOS COMPONENTES QUE EXISTEN
import com.example.levelup.ui.components.BottomNavigationBar // Tu barra inferior
import com.example.levelup.ui.screen.InicioScreen
import com.example.levelup.viewmodel.PostViewModel
import com.example.levelup.viewmodel.PostViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpApp(postViewModelFactory: PostViewModelFactory) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        // 2. HEMOS ELIMINADO POR COMPLETO LA SECCIÓN 'topBar'.
        //    Si tu app no tiene una barra superior aquí, no la necesitamos.

        // 3. LLAMAMOS A TU BOTTOMNAVIGATION: Esta función sí existe.
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = currentRoute
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Destinations.HomeScreen.route
            ) {
                composable(Destinations.HomeScreen.route) {
                    val postViewModel: PostViewModel = viewModel(factory = postViewModelFactory)
                    InicioScreen(viewModel = postViewModel, navController = navController)
                }

                // Aquí defines el resto de tus pantallas...
                // composable(Destinations.FavsScreen.route) { FavsScreen(...) }
            }
        }
    }
}
