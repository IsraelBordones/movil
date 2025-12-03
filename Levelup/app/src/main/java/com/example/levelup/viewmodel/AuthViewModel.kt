package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.PreferencesManager
import com.example.levelup.data.dao.UserDao
import com.example.levelup.data.model.FormularioError
import com.example.levelup.data.model.FormularioUiState
import com.example.levelup.data.model.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userDao: UserDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormularioUiState())
    val uiState: StateFlow<FormularioUiState> = _uiState.asStateFlow()

    fun actualizarNombre(nombre: String) {
        _uiState.update { it.copy(nombre = nombre) }
        validarFormulario()
    }

    fun actualizarApellido(apellido: String) {
        _uiState.update { it.copy(apellido = apellido) }
        validarFormulario()
    }

    fun actualizarEmail(email: String) {
        _uiState.update { it.copy(email = email) }
        validarFormulario()
    }

    fun actualizarPassword(password: String) {
        _uiState.update { it.copy(password = password) }
        validarFormulario()
    }

    fun actualizarConfirmarPassword(confirmarPassword: String) {
        _uiState.update { it.copy(confirmarPassword = confirmarPassword) }
        validarFormulario()
    }

    fun actualizarTelefono(telefono: String) {
        _uiState.update { it.copy(telefono = telefono) }
        validarFormulario()
    }

    fun actualizarDireccion(direccion: String) {
        _uiState.update { it.copy(direccion = direccion) }
        validarFormulario()
    }

    fun actualizarCiudad(ciudad: String) {
        _uiState.update { it.copy(ciudad = ciudad) }
        validarFormulario()
    }

    fun toggleModoFormulario() {
        _uiState.update {
            it.copy(
                esLogin = !it.esLogin,
                errores = FormularioError()
            )
        }
        validarFormulario()
    }

    private fun validarFormulario() {
        val currentState = _uiState.value
        var errores = FormularioError()

        // Validaciones para el formulario de registro
        if (!currentState.esLogin) {
            if (currentState.nombre.isBlank()) errores.nombre = "El nombre no puede estar vacío"
            if (currentState.apellido.isBlank()) errores.apellido = "El apellido no puede estar vacío"
            if (currentState.password != currentState.confirmarPassword) errores.confirmarPassword = "Las contraseñas no coinciden"
            if (currentState.telefono.isBlank()) errores.telefono = "El teléfono no puede estar vacío"
            if (currentState.direccion.isBlank()) errores.direccion = "La dirección no puede estar vacía"
            if (currentState.ciudad.isBlank()) errores.ciudad = "La ciudad no puede estar vacía"
        }

        // Validaciones comunes para login y registro
        if (currentState.email.isBlank()) {
            errores.email = "El email no puede estar vacío"
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            errores.email = "El formato del email no es válido"
        }

        if (currentState.password.isBlank()) {
            errores.password = "La contraseña no puede estar vacía"
        } else if (currentState.password.length < 6) {
            errores.password = "La contraseña debe tener al menos 6 caracteres"
        }

        val esValido = if (currentState.esLogin) {
            errores.email == null && errores.password == null
        } else {
            errores.nombre == null && errores.apellido == null && errores.email == null && errores.password == null && errores.confirmarPassword == null && errores.telefono == null && errores.direccion == null && errores.ciudad == null
        }

        _uiState.update { it.copy(errores = errores, esFormularioValido = esValido) }
    }

    fun iniciarSesion(onSuccess: () -> Unit) {
        // ...
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errores = FormularioError()) }
            try {
                val usuario = userDao.login(uiState.value.email, uiState.value.password)

                if (usuario != null) {
                    preferencesManager.saveLoginState(
                        isLoggedIn = true,
                        userId = usuario.id.toString(),
                        email = usuario.email,
                        nombre = usuario.nombreUsuario,
                        apellido = usuario.apellido,
                        telefono = usuario.telefono,
                        direccion = usuario.direccion,
                        ciudad = usuario.ciudad,
                        role = usuario.role // <-- GUARDAMOS EL ROL
                    )
                    _uiState.update { it.copy(isLoading = false) }
                    onSuccess()
                } else {
                    _uiState.update { it.copy(isLoading = false, errores = FormularioError(errorGeneral = "Email o contraseña incorrectos")) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errores = FormularioError(errorGeneral = "Error: ${e.message}")) }
            }
        }
    }

    fun registrar(onSuccess: () -> Unit) {
        // ...
        viewModelScope.launch {
            // ...
            try {
                // ...
                val nuevoUsuario = Usuario(
                    nombreUsuario = uiState.value.nombre,
                    apellido = uiState.value.apellido,
                    email = uiState.value.email,
                    password = uiState.value.password,
                    telefono = uiState.value.telefono,
                    direccion = uiState.value.direccion,
                    ciudad = uiState.value.ciudad,
                    role = "CLIENTE" // Por defecto, todos los registros son de clientes
                )
                userDao.insertUsuario(nuevoUsuario)

                iniciarSesion(onSuccess)

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errores = FormularioError(errorGeneral = "Error: ${e.message}")) }
            }
        }
    }
}
