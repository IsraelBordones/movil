package com.example.levelup.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.levelup.viewmodel.PerfilViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    perfilViewModel: PerfilViewModel = hiltViewModel() // Inyectado con Hilt
) {
    val uiState by perfilViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mi Perfil") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (uiState.enModoEdicion) {
                    perfilViewModel.guardarCambios()
                    Toast.makeText(context, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                } else {
                    perfilViewModel.toggleModoEdicion()
                }
            }) {
                Icon(
                    imageVector = if (uiState.enModoEdicion) Icons.Default.Save else Icons.Default.Edit,
                    contentDescription = if (uiState.enModoEdicion) "Guardar" else "Editar"
                )
            }
        }
    ) { paddingValues ->

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${uiState.error}")
                }
            }
            uiState.usuario != null -> {
                val usuario = uiState.usuario!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Icono de perfil", modifier = Modifier.size(120.dp).clip(CircleShape))
                    Spacer(modifier = Modifier.height(16.dp))

                    // Nombre de Usuario
                    ProfileTextField(
                        label = "Nombre de Usuario",
                        value = usuario.nombreUsuario,
                        onValueChange = { perfilViewModel.onDataChange(it, usuario.email, usuario.telefono, usuario.direccion, usuario.ciudad) },
                        enabled = uiState.enModoEdicion
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Email
                    ProfileTextField(
                        label = "Email",
                        value = usuario.email,
                        onValueChange = { perfilViewModel.onDataChange(usuario.nombreUsuario, it, usuario.telefono, usuario.direccion, usuario.ciudad) },
                        enabled = uiState.enModoEdicion
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Teléfono
                    ProfileTextField(
                        label = "Teléfono",
                        value = usuario.telefono,
                        onValueChange = { perfilViewModel.onDataChange(usuario.nombreUsuario, usuario.email, it, usuario.direccion, usuario.ciudad) },
                        enabled = uiState.enModoEdicion
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Dirección
                    ProfileTextField(
                        label = "Dirección",
                        value = usuario.direccion,
                        onValueChange = { perfilViewModel.onDataChange(usuario.nombreUsuario, usuario.email, usuario.telefono, it, usuario.ciudad) },
                        enabled = uiState.enModoEdicion
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Ciudad
                    ProfileTextField(
                        label = "Ciudad",
                        value = usuario.ciudad,
                        onValueChange = { perfilViewModel.onDataChange(usuario.nombreUsuario, usuario.email, usuario.telefono, usuario.direccion, it) },
                        enabled = uiState.enModoEdicion
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo no editable
                    ProfileInfo(label = "Apellido", value = usuario.apellido)
                }
            }
        }
    }
}

@Composable
private fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit, enabled: Boolean) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        readOnly = !enabled,
        singleLine = true
    )
}

@Composable
fun ProfileInfo(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}
