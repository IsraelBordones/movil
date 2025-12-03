package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.PreferencesManager
import com.example.levelup.data.model.MainUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(preferencesManager: PreferencesManager) : ViewModel() {

    // MODIFICADO: Expone el estado de la UI principal (Loading, LoggedIn, LoggedOut)
    val uiState: StateFlow<MainUiState> = preferencesManager.isLoggedIn
        .map { isLoggedIn ->
            if (isLoggedIn) {
                MainUiState.LoggedIn
            } else {
                MainUiState.LoggedOut
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            // El estado inicial ahora es `Loading`
            initialValue = MainUiState.Loading
        )
}