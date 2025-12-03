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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelup.viewmodel.AuthViewModel

@Composable
fun LoginScreen(authViewModel: AuthViewModel = viewModel()) {

    // Variables para capturar lo que escribe el usuario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Observamos el mensaje del ViewModel (Error o Éxito)
    val mensaje by authViewModel.mensajeError.collectAsState()

    // Observamos si hay usuario logueado
    val usuarioLogueado by authViewModel.usuarioLogueado.collectAsState()

    // Efecto secundario: Si el login es exitoso o hay error, mostramos un Toast
    LaunchedEffect(mensaje) {
        if (mensaje.isNotEmpty()) {
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

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
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(), // Oculta la clave con puntitos
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.login(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        // Mostrar datos si ya entró
        if (usuarioLogueado != null) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "¡Hola, ${usuarioLogueado!!.nombreUsuario}!", color = MaterialTheme.colorScheme.primary)
            Text(text = "Tu ID es: ${usuarioLogueado!!.id}")
        }
    }
}