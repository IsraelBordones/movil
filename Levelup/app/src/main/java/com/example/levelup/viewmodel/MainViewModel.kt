package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.PreferencesManager
import com.example.levelup.data.model.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    preferencesManager: PreferencesManager
) : ViewModel() {

    // MODIFICADO: Ahora combina el estado de login y el rol del usuario
    val uiState: StateFlow<MainUiState> = combine(
        preferencesManager.isLoggedIn,
        preferencesManager.userRole
    ) { isLoggedIn, userRole ->
        if (isLoggedIn) {
            MainUiState.LoggedIn(userRole ?: "CLIENTE") // Si el rol es nulo, es un cliente
        } else {
            MainUiState.LoggedOut
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainUiState.Loading
    )
}