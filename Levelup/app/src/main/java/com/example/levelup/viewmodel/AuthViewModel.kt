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

@HiltViewModel // 1. Anotado para Hilt
class AuthViewModel @Inject constructor( // 2. Constructor inyectado
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
        val estado = _uiState.value
        val errores = mutableMapOf<String, String?>()

        if (!estado.esLogin) {
            if (estado.nombre.isBlank()) {
                errores["nombre"] = "El nombre es requerido"
            } else if (estado.nombre.length < 2) {
                errores["nombre"] = "Mínimo 2 caracteres"
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
            errores["password"] = "Mínimo 6 caracteres"
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
                    ciudad = errores["ciudad"],
                    errorGeneral = errores["general"]
                ),
                esFormularioValido = esValido
            )
        }
    }

    fun iniciarSesion(onSuccess: () -> Unit) {
        validarFormulario()
        val estado = _uiState.value
        if (!estado.esFormularioValido || !estado.esLogin) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errores = FormularioError()) }
            try {
                val usuario = userDao.login(estado.email, estado.password)

                if (usuario != null) {
                    preferencesManager.saveLoginState(
                        isLoggedIn = true,
                        userId = usuario.id.toString(),
                        email = usuario.email,
                        nombre = usuario.nombreUsuario,
                        apellido = usuario.apellido,
                        telefono = usuario.telefono,
                        direccion = usuario.direccion,
                        ciudad = usuario.ciudad
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
        validarFormulario()
        val estado = _uiState.value
        if (!estado.esFormularioValido || estado.esLogin) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errores = FormularioError()) }
            try {
                val existingUser = userDao.getUsuarioByEmail(estado.email)
                if (existingUser != null) {
                    _uiState.update { it.copy(isLoading = false, errores = FormularioError(email = "Este email ya está registrado")) }
                    return@launch
                }

                val nuevoUsuario = Usuario(
                    nombreUsuario = estado.nombre,
                    apellido = estado.apellido,
                    email = estado.email,
                    password = estado.password,
                    telefono = estado.telefono,
                    direccion = estado.direccion,
                    ciudad = estado.ciudad
                )
                userDao.insertUsuario(nuevoUsuario)

                iniciarSesion(onSuccess)

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errores = FormularioError(errorGeneral = "Error: ${e.message}")) }
            }
        }
    }
}