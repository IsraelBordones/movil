package com.example.levelup.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.levelup.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    carritoViewModel: CarritoViewModel = hiltViewModel() // Inyectado con Hilt
) {
    val carritoItems by carritoViewModel.carritoItems.collectAsState()
    val context = LocalContext.current

    val total = carritoItems.sumOf { it.precio * it.cantidad }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mi Carrito") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (carritoItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tu carrito está vacío")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(carritoItems, key = { it.id }) { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(item.nombre, fontWeight = FontWeight.Bold)
                                Text("Cantidad: ${item.cantidad}")
                            }
                            Text(String.format("$%.2f", item.precio * item.cantidad), fontSize = 16.sp)
                            IconButton(onClick = { carritoViewModel.removeProductoFromCarrito(item) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar producto")
                            }
                        }
                        Divider()
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(String.format("$%.2f", total), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            carritoViewModel.procesarCompra {
                                Toast.makeText(context, "¡Compra realizada con éxito!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = carritoItems.isNotEmpty()
                    ) {
                        Text("Confirmar Compra")
                    }
                }
            }
        }
    }
}
