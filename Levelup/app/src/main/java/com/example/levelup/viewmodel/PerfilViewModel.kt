package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.PreferencesManager
import com.example.levelup.data.dao.UserDao
import com.example.levelup.data.model.PerfilUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val userDao: UserDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesManager.userId.collectLatest { userId ->
                if (userId != null && userId.isNotEmpty()) {
                    userDao.getUserById(userId.toInt()).collectLatest { usuarioDb ->
                        _uiState.update { it.copy(usuario = usuarioDb, isLoading = false) }
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "No se encontró el ID del usuario") }
                }
            }
        }
    }

    fun toggleModoEdicion() {
        _uiState.update { it.copy(enModoEdicion = !it.enModoEdicion) }
    }

    fun onDataChange(nombre: String, email: String, telefono: String, direccion: String, ciudad: String) {
        _uiState.value.usuario?.let {
            val usuarioActualizado = it.copy(
                nombreUsuario = nombre,
                email = email,
                telefono = telefono,
                direccion = direccion,
                ciudad = ciudad
            )
            _uiState.update { state -> state.copy(usuario = usuarioActualizado) }
        }
    }

    fun guardarCambios() {
        _uiState.value.usuario?.let {
            viewModelScope.launch {
                userDao.updateUser(it)
                toggleModoEdicion()
            }
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            preferencesManager.clearLoginState()
        }
    }
}