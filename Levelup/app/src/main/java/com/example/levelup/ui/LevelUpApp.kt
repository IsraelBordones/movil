package com.example.levelup.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.levelup.navigation.BottomBarScreen
import com.example.levelup.navigation.Routes
import com.example.levelup.ui.components.BottomNavigationBar
import com.example.levelup.ui.screen.*
import com.example.levelup.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelUpApp(
    userRole: String,
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
                startDestination = BottomBarScreen.HomeScreen.route
            ) {
                composable(BottomBarScreen.HomeScreen.route) {
                    val postViewModel: PostViewModel = hiltViewModel()
                    InicioScreen(viewModel = postViewModel, navController = navController)
                }
                composable(BottomBarScreen.CatalogoScreen.route) {
                    CatalogoScreen(
                        userRole = userRole,
                        onLogout = onLogout,
                        // AÑADIDO: Pasamos el NavController para que pueda navegar
                        navController = navController
                    )
                }
                composable(BottomBarScreen.SearchScreen.route) { SearchScreen() }
                composable(BottomBarScreen.CarritoScreen.route) { CarritoScreen() }
                composable(BottomBarScreen.FavsScreen.route) { FavsScreen() }
                composable(BottomBarScreen.PerfilScreen.route) { PerfilScreen() }

                // AÑADIDO: Composable para la pantalla de edición
                composable(
                    route = Routes.EDIT_PRODUCT,
                    arguments = listOf(navArgument("productId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                    EditProductScreen(
                        productId = productId,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}