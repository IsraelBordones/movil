package com.example.levelup.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Set the form mode to register if it's currently in login mode
    LaunchedEffect(uiState.esLogin) {
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
        Text(text = "Registro de Usuario", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Name
        OutlinedTextField(
            value = uiState.nombre,
            onValueChange = { authViewModel.actualizarNombre(it) },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.nombre != null,
            singleLine = true
        )
        uiState.errores.nombre?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Apellido
        OutlinedTextField(
            value = uiState.apellido,
            onValueChange = { authViewModel.actualizarApellido(it) },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.apellido != null,
            singleLine = true
        )
        uiState.errores.apellido?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Email
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { authViewModel.actualizarEmail(it) },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.email != null,
            singleLine = true
        )
        uiState.errores.email?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Password
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { authViewModel.actualizarPassword(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.password != null,
            singleLine = true
        )
        uiState.errores.password?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password
        OutlinedTextField(
            value = uiState.confirmarPassword,
            onValueChange = { authViewModel.actualizarConfirmarPassword(it) },
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.confirmarPassword != null,
            singleLine = true
        )
        uiState.errores.confirmarPassword?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Telefono
        OutlinedTextField(
            value = uiState.telefono,
            onValueChange = { authViewModel.actualizarTelefono(it) },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.telefono != null,
            singleLine = true
        )
        uiState.errores.telefono?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Direccion
        OutlinedTextField(
            value = uiState.direccion,
            onValueChange = { authViewModel.actualizarDireccion(it) },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.direccion != null,
            singleLine = true
        )
        uiState.errores.direccion?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))
        
        // Ciudad
        OutlinedTextField(
            value = uiState.ciudad,
            onValueChange = { authViewModel.actualizarCiudad(it) },
            label = { Text("Ciudad") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.ciudad != null,
            singleLine = true
        )
        uiState.errores.ciudad?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(16.dp))

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
        
        uiState.errores.errorGeneral?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
        }
    }
}