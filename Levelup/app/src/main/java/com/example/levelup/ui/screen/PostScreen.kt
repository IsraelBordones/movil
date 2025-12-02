package com.example.levelup.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.levelup.model.Producto // ¡Importamos el modelo Producto!

@Composable
// 1. CAMBIO CLAVE: Renombramos la función y cambiamos los parámetros.
//    Ahora acepta un 'Producto' en lugar de parámetros sueltos.
fun PostScreen(producto: Producto) { // Le quitamos el NavController por ahora para simplificar
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { /* Futura navegación a detalles */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // 2. Usamos los datos del 'producto' para mostrar la información.
            AsyncImage(
                model = producto.imagen, // Usa la URL de la imagen del producto
                contentDescription = "Imagen de ${producto.nombre}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = producto.nombre, // Muestra el nombre del producto
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = producto.descripcion, // Muestra la descripción
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis // Pone "..." si el texto es muy largo
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Precio: ${producto.precio}€", // Muestra el precio
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
    