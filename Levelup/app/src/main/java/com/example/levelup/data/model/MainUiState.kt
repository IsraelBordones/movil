package com.example.levelup.data.model

/**
 * Representa los posibles estados de la UI principal al arrancar la app.
 */
sealed class MainUiState {
    object Loading : MainUiState()
    // MODIFICADO: Ahora contiene el rol del usuario
    data class LoggedIn(val userRole: String) : MainUiState()
    object LoggedOut : MainUiState()
}
