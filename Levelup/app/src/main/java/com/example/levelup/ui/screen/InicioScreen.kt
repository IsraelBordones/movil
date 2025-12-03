package com.example.levelup.ui.screen

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.levelup.viewmodel.CarritoViewModel
import com.example.levelup.viewmodel.PostViewModel

@Composable
fun InicioScreen(
    // MODIFICADO: El viewModel ahora se inyecta con Hilt
    navController: NavHostController,
    viewModel: PostViewModel = hiltViewModel(),
    carritoViewModel: CarritoViewModel = hiltViewModel()
) {
    val listaDeProductos by viewModel.productos.collectAsState()
    val context = LocalContext.current

    LazyColumn {
        items(listaDeProductos) { producto ->
            PostScreen(
                producto = producto,
                onAddToCartClicked = { prod ->
                    carritoViewModel.addProductoToCarrito(prod)
                    Toast.makeText(context, "${prod.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
