package com.example.levelup.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.levelup.viewmodel.PostViewModel
// ... tus importaciones ...


@Composable
fun InicioScreen(viewModel: PostViewModel, navController: NavHostController) {
    val listaDeProductos by viewModel.productos.collectAsState()

    LazyColumn {
        items(listaDeProductos) { producto ->
            // ¡CAMBIO CLAVE! Llamamos a PostScreen, que ahora es nuestra tarjeta.
            PostScreen(producto = producto)
        }
    }
}

