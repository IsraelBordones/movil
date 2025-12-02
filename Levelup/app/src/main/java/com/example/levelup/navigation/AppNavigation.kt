package com.example.levelup.navigation

// --- Tus importaciones actuales ---
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.levelup.ui.components.BottomNavigationBar // Tu barra inferior

// --- ¡NUEVAS IMPORTACIONES NECESARIAS! ---
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.levelup.ui.screen.InicioScreen
import com.example.levelup.viewmodel.PostViewModel
import com.example.levelup.viewmodel.PostViewModelFactory
import com.example.levelup.navigation.Destinations // <-- ¡AÑADE ESTA LÍNEA!


// --- Tus otras importaciones de pantallas ---
// import com.example.levelup.views.FavsScreen
// import com.example.levelup.views.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// 1. CAMBIO CLAVE: Hacemos que LevelUpApp acepte la Factory como parámetro.
fun LevelUpApp(postViewModelFactory: PostViewModelFactory) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                navController = navController,
                currentRoute = currentRoute
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = currentRoute
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // 2. PASO CRÍTICO: Pasamos la Factory hacia el NavigationHost.
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
    // 3. CAMBIO CLAVE: NavigationHost también debe aceptar la Factory.
    postViewModelFactory: PostViewModelFactory
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.HomeScreen.route
    ) {
        composable(Destinations.HomeScreen.route) {
            // 4. ¡AQUÍ CREAMOS EL VIEWMODEL!
            // Usamos la Factory que hemos ido pasando para crear una instancia
            // de PostViewModel con acceso a la base de datos.
            val postViewModel: PostViewModel = viewModel(factory = postViewModelFactory)

            // 5. Pasamos el ViewModel ya creado a la pantalla que lo necesita.
            InicioScreen(viewModel = postViewModel, navController = navController)
        }

        // El resto de tus pantallas, que no necesitan el ViewModel, se quedan igual.
        // composable(Destinations.FavsScreen.route) { ... }
        // composable(Destinations.SearchScreen.route) { ... }
    }
}
