package com.example.levelup.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.levelup.ui.components.BottomNavigationBar
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
        topBar = {
            TopAppBar(
                title = { Text(currentRoute ?: "") }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavigationHost(
                navController = navController,
                postViewModelFactory = postViewModelFactory
            )
        }
    }
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    postViewModelFactory: PostViewModelFactory
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Catalogo.route // Assuming Catalogo is the starting screen
    ) {
        composable(Screen.Catalogo.route) {
            val postViewModel: PostViewModel = viewModel(factory = postViewModelFactory)
            InicioScreen(viewModel = postViewModel, navController = navController)
        }

        composable(Screen.Perfil.route) {
            // Placeholder for PerfilScreen
            // PerfilScreen()
        }
    }
}

// Define your screen routes here
sealed class Screen(val route: String) {
    object Catalogo : Screen("catalogo")
    object Perfil : Screen("perfil")
}