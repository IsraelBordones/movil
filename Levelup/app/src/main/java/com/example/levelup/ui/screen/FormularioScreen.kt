package com.example.levelup.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelup.ui.components.CampoTexto
import com.example.levelup.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (uiState.esLogin) "Iniciar Sesión" else "Crear Cuenta",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = if (uiState.esLogin) 
                "Bienvenido a LevelUp"
            else 
                "Hazte Gamer",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 32.dp)
        )


        if (!uiState.esLogin) {
            CampoTexto(
                valor = uiState.nombre,
                onValorChange = { viewModel.actualizarNombre(it) },
                etiqueta = "Nombre",
                error = uiState.errores.nombre,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            CampoTexto(
                valor = uiState.apellido,
                onValorChange = { viewModel.actualizarApellido(it) },
                etiqueta = "Apellido",
                error = uiState.errores.apellido,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        CampoTexto(
            valor = uiState.email,
            onValorChange = { viewModel.actualizarEmail(it) },
            etiqueta = "Email",
            error = uiState.errores.email,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        CampoTexto(
            valor = uiState.password,
            onValorChange = { viewModel.actualizarPassword(it) },
            etiqueta = "Contraseña",
            error = uiState.errores.password,
            esContrasena = true,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        AnimatedVisibility(
            visible = !uiState.esLogin,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column {
                CampoTexto(
                    valor = uiState.confirmarPassword,
                    onValorChange = { viewModel.actualizarConfirmarPassword(it) },
                    etiqueta = "Confirmar Contraseña",
                    error = uiState.errores.confirmarPassword,
                    esContrasena = true,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CampoTexto(
                    valor = uiState.telefono,
                    onValorChange = { viewModel.actualizarTelefono(it) },
                    etiqueta = "Teléfono",
                    error = uiState.errores.telefono,
                    keyboardType = KeyboardType.Phone,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CampoTexto(
                    valor = uiState.direccion,
                    onValorChange = { viewModel.actualizarDireccion(it) },
                    etiqueta = "Dirección",
                    error = uiState.errores.direccion,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                CampoTexto(
                    valor = uiState.ciudad,
                    onValorChange = { viewModel.actualizarCiudad(it) },
                    etiqueta = "Ciudad",
                    error = uiState.errores.ciudad,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de acción
        Button(
            onClick = {
                if (uiState.esLogin) {
                    viewModel.iniciarSesion(onLoginSuccess)
                } else {
                    viewModel.registrar(onLoginSuccess)
                }
            },
            enabled = uiState.esFormularioValido && !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = if (uiState.esLogin) "Iniciar Sesión" else "Registrarse",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Toggle entre Login y Registro
        TextButton(
            onClick = { viewModel.toggleModoFormulario() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (uiState.esLogin) 
                    "¿No tienes cuenta? Regístrate" 
                else 
                    "¿Ya tienes cuenta? Inicia sesión",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

