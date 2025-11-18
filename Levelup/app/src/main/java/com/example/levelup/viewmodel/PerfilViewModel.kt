package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.PreferencesManager
import com.example.levelup.model.PerfilUiState
import com.example.levelup.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilViewModel(private val preferencesManager: PreferencesManager) : ViewModel() {
    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        cargarUsuario()
    }

    fun cargarUsuario() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val datos = preferencesManager.getUserData()
                if (datos["id"]?.isNotBlank() == true) {
                    val usuario = Usuario(
                        id = datos["id"] ?: "",
                        nombre = datos["nombre"] ?: "",
                        apellido = datos["apellido"] ?: "",
                        email = datos["email"] ?: "",
                        telefono = datos["telefono"] ?: "",
                        direccion = datos["direccion"] ?: "",
                        ciudad = datos["ciudad"] ?: ""
                    )
                    _uiState.update {
                        it.copy(
                            usuario = usuario,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "No hay sesión activa"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar perfil: ${e.message}"
                    )
                }
            }
        }
    }

    fun cerrarSesion(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                preferencesManager.saveLoginState(isLoggedIn = false)
                onSuccess()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al cerrar sesión: ${e.message}") }
            }
        }
    }

    fun toggleDialogoEditar() {
        _uiState.update { it.copy(mostrarDialogoEditar = !it.mostrarDialogoEditar) }
    }
}

