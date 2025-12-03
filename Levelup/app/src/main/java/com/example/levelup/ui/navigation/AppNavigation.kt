package com.example.levelup.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.levelup.ui.LevelUpApp
import com.example.levelup.ui.screen.LoginScreen
import com.example.levelup.ui.screen.RegisterScreen
import com.example.levelup.viewmodel.MainViewModel
import com.example.levelup.viewmodel.MainViewModelFactory
import com.example.levelup.viewmodel.PostViewModelFactory

object Routes {
    const val AUTH_GRAPH = "auth_graph"
    const val LOGIN = "login"
    const val REGISTER = "register"

    const val MAIN_GRAPH = "main_graph"
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(LocalContext.current)),
    postViewModelFactory: PostViewModelFactory // <-- AÑADIDO: Factory para la app principal
) {
    val isLoggedIn by mainViewModel.isLoggedIn.collectAsState()

    val startDestination = if (isLoggedIn) Routes.MAIN_GRAPH else Routes.AUTH_GRAPH

    NavHost(navController = navController, startDestination = startDestination) {

        // --- GRAFO DE AUTENTICACIÓN (LOGIN Y REGISTRO) ---
        navigation(startDestination = Routes.LOGIN, route = Routes.AUTH_GRAPH) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Routes.MAIN_GRAPH) {
                            popUpTo(Routes.AUTH_GRAPH) { inclusive = true }
                        }
                    }
                )
            }
            composable(Routes.REGISTER) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(Routes.MAIN_GRAPH) {
                            popUpTo(Routes.AUTH_GRAPH) { inclusive = true }
                        }
                    }
                )
            }
        }

        // --- GRAFO PRINCIPAL (LA APP COMPLETA) ---
        composable(Routes.MAIN_GRAPH) {
            LevelUpApp(
                postViewModelFactory = postViewModelFactory,
                onLogout = {
                    navController.navigate(Routes.AUTH_GRAPH) {
                        popUpTo(Routes.MAIN_GRAPH) { inclusive = true }
                    }
                }
            )
        }
    }
}