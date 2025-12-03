package com.example.levelup.data.model

/**
 * Representa los posibles estados de la UI principal al arrancar la app.
 */
sealed class MainUiState {
    object Loading : MainUiState()    // Estado inicial, mientras se verifica la sesión
    object LoggedIn : MainUiState()   // El usuario tiene una sesión activa
    object LoggedOut : MainUiState()  // El usuario no tiene una sesión activa
}
