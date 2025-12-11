package com.example.levelup.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
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
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit, // AÑADIDO
    authViewModel: AuthViewModel = hiltViewModel() // Inyectado con Hilt
) {

    val uiState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { authViewModel.actualizarEmail(it) },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errores.email != null,
            singleLine = true
        )
        if (uiState.errores.email != null) {
            Text(
                text = uiState.errores.email!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
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
            Text(
                text = uiState.errores.password!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.iniciarSesion {
                    Toast.makeText(context, "Login Exitoso", Toast.LENGTH_SHORT).show()
                    onLoginSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.esFormularioValido && !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Entrar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // AÑADIDO: Botón para ir a la pantalla de registro
        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes una cuenta? Regístrate aquí")
        }
    }
}