package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.PreferencesManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(preferencesManager: PreferencesManager) : ViewModel() {

    // Expone un flujo que indica si el usuario está logueado o no.
    // El `stateIn` lo convierte en un `StateFlow`, que es ideal para la UI.
    val isLoggedIn: StateFlow<Boolean> = preferencesManager.isLoggedIn.map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false // El valor inicial es `false` hasta que se lea de DataStore
        )
}