package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.PreferencesManager
import com.example.levelup.model.FormularioError
import com.example.levelup.model.FormularioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class AuthViewModel(private val preferencesManager: PreferencesManager) : ViewModel() {
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
        val estado = _uiState.value
        val errores = mutableMapOf<String, String?>()

        if (!estado.esLogin) {
            if (estado.nombre.isBlank()) {
                errores["nombre"] = "El nombre es requerido"
            } else if (estado.nombre.length < 2) {
                errores["nombre"] = "El nombre debe tener al menos 2 caracteres"
            }

            if (estado.apellido.isBlank()) {
                errores["apellido"] = "El apellido es requerido"
            }

            if (estado.telefono.isBlank()) {
                errores["telefono"] = "El teléfono es requerido"
            } else if (!estado.telefono.matches(Regex("^[0-9]{8,9}$"))) {
                errores["telefono"] = "Teléfono inválido"
            }

            if (estado.direccion.isBlank()) {
                errores["direccion"] = "La dirección es requerida"
            }

            if (estado.ciudad.isBlank()) {
                errores["ciudad"] = "La ciudad es requerida"
            }
        }

        if (estado.email.isBlank()) {
            errores["email"] = "El email es requerido"
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(estado.email).matches()) {
            errores["email"] = "Email inválido"
        }

        if (estado.password.isBlank()) {
            errores["password"] = "La contraseña es requerida"
        } else if (estado.password.length < 6) {
            errores["password"] = "La contraseña debe tener al menos 6 caracteres"
        }

        if (!estado.esLogin) {
            if (estado.confirmarPassword.isBlank()) {
                errores["confirmarPassword"] = "Confirma tu contraseña"
            } else if (estado.password != estado.confirmarPassword) {
                errores["confirmarPassword"] = "Las contraseñas no coinciden"
            }
        }

        val esValido = errores.isEmpty()
        _uiState.update {
            it.copy(
                errores = FormularioError(
                    nombre = errores["nombre"],
                    apellido = errores["apellido"],
                    email = errores["email"],
                    password = errores["password"],
                    confirmarPassword = errores["confirmarPassword"],
                    telefono = errores["telefono"],
                    direccion = errores["direccion"],
                    ciudad = errores["ciudad"]
                ),
                esFormularioValido = esValido
            )
        }
    }

    fun iniciarSesion(onSuccess: () -> Unit) {
        val estado = _uiState.value
        if (!estado.esFormularioValido || !estado.esLogin) {
            validarFormulario()
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {

                kotlinx.coroutines.delay(900)

                val userId = UUID.randomUUID().toString()
                preferencesManager.saveLoginState(
                    isLoggedIn = true,
                    userId = userId,
                    email = estado.email,
                    nombre = estado.nombre.ifEmpty { "Usuario" },
                    apellido = estado.apellido.ifEmpty { "" },
                    telefono = estado.telefono.ifEmpty { "" },
                    direccion = estado.direccion.ifEmpty { "" },
                    ciudad = estado.ciudad.ifEmpty { "" }
                )

                _uiState.update { it.copy(isLoading = false) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun registrar(onSuccess: () -> Unit) {
        val estado = _uiState.value
        if (!estado.esFormularioValido || estado.esLogin) {
            validarFormulario()
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {

                kotlinx.coroutines.delay(1000)

                val userId = UUID.randomUUID().toString()
                preferencesManager.saveLoginState(
                    isLoggedIn = true,
                    userId = userId,
                    email = estado.email,
                    nombre = estado.nombre,
                    apellido = estado.apellido,
                    telefono = estado.telefono,
                    direccion = estado.direccion,
                    ciudad = estado.ciudad
                )

                _uiState.update { it.copy(isLoading = false) }
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}

