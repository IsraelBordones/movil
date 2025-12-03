package com.example.levelup.ui.screen

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.levelup.viewmodel.CarritoViewModel
import com.example.levelup.viewmodel.CarritoViewModelFactory
import com.example.levelup.viewmodel.PostViewModel

@Composable
fun InicioScreen(
    viewModel: PostViewModel,
    navController: NavHostController,
    // AÑADIDO: Obtenemos el ViewModel del carrito
    carritoViewModel: CarritoViewModel = viewModel(factory = CarritoViewModelFactory(LocalContext.current))
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
