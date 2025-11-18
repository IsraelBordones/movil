package com.example.levelup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.levelup.data.PreferencesManager
import com.example.levelup.data.ProductosRepository
import com.example.levelup.ui.components.BottomNavigationBar
import com.example.levelup.ui.screen.*
import com.example.levelup.viewmodel.AuthViewModel
import com.example.levelup.viewmodel.CatalogoViewModel
import com.example.levelup.viewmodel.HomeViewModel
import com.example.levelup.viewmodel.PerfilViewModel

sealed class Screen(val route: String) {
    object Inicio : Screen("inicio")
    object Login : Screen("login")
    object Catalogo : Screen("catalogo")
    object Perfil : Screen("perfil")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val isLoggedIn by preferencesManager.isLoggedIn.collectAsState(initial = false)

    val startRoute = when {
        isLoggedIn -> Screen.Catalogo.route
        else -> Screen.Inicio.route
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in listOf(Screen.Catalogo.route, Screen.Perfil.route)

    androidx.compose.material3.Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Catalogo.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Inicio.route) {
                InicioScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Inicio.route) { inclusive = true }
                        }
                    },
                    viewModel = viewModel()
                )
            }

            composable(Screen.Login.route) {
                val authViewModel = viewModel<AuthViewModel>(
                    factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
                            return AuthViewModel(preferencesManager) as T
                        }
                    }
                )

                FormularioScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.Catalogo.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    viewModel = authViewModel
                )
            }

            composable(Screen.Catalogo.route) {
                val catalogoViewModel = viewModel<CatalogoViewModel>(
                    factory = CatalogoViewModelFactory(ProductosRepository())
                )

                CatalogoScreen(
                    onNavigateToPerfil = {
                        navController.navigate(Screen.Perfil.route)
                    },
                    viewModel = catalogoViewModel
                )
            }

            composable(Screen.Perfil.route) {
                val perfilViewModel = viewModel<PerfilViewModel>(
                    factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
                            return PerfilViewModel(preferencesManager) as T
                        }
                    }
                )

                PerfilScreen(
                    onLogout = {
                        navController.navigate(Screen.Inicio.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onNavigateToCatalogo = {
                        navController.navigate(Screen.Catalogo.route)
                    },
                    viewModel = perfilViewModel
                )
            }
        }
    }
}

