package com.example.levelup.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.levelup.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel() // Inyectado con Hilt
) {

    val uiState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (uiState.esLogin) {
            authViewModel.toggleModoFormulario()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Crear Cuenta", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.nombre,
            onValueChange = { authViewModel.actualizarNombre(it) },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.nombre != null,
            singleLine = true
        )
        if (uiState.errores.nombre != null) {
            Text(text = uiState.errores.nombre!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.apellido,
            onValueChange = { authViewModel.actualizarApellido(it) },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.apellido != null,
            singleLine = true
        )
        if (uiState.errores.apellido != null) {
            Text(text = uiState.errores.apellido!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { authViewModel.actualizarEmail(it) },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.email != null,
            singleLine = true
        )
        if (uiState.errores.email != null) {
            Text(text = uiState.errores.email!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.telefono,
            onValueChange = { authViewModel.actualizarTelefono(it) },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.telefono != null,
            singleLine = true
        )
        if (uiState.errores.telefono != null) {
            Text(text = uiState.errores.telefono!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.direccion,
            onValueChange = { authViewModel.actualizarDireccion(it) },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.direccion != null,
            singleLine = true
        )
        if (uiState.errores.direccion != null) {
            Text(text = uiState.errores.direccion!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.ciudad,
            onValueChange = { authViewModel.actualizarCiudad(it) },
            label = { Text("Ciudad") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.ciudad != null,
            singleLine = true
        )
        if (uiState.errores.ciudad != null) {
            Text(text = uiState.errores.ciudad!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { authViewModel.actualizarPassword(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.password != null,
            singleLine = true
        )
        if (uiState.errores.password != null) {
            Text(text = uiState.errores.password!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.confirmarPassword,
            onValueChange = { authViewModel.actualizarConfirmarPassword(it) },
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.confirmarPassword != null,
            singleLine = true
        )
        if (uiState.errores.confirmarPassword != null) {
            Text(text = uiState.errores.confirmarPassword!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.errores.errorGeneral != null) {
            Text(
                text = uiState.errores.errorGeneral!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                authViewModel.registrar {
                    Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                    onRegisterSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.esFormularioValido && !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Registrarse")
            }
        }
    }
}