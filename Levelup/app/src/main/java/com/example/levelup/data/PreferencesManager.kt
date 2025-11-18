package com.example.levelup.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "levelup_prefs")

class PreferencesManager(private val context: Context) {
    companion object {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val USER_ID = stringPreferencesKey("user_id")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_NOMBRE = stringPreferencesKey("user_nombre")
        val USER_APELLIDO = stringPreferencesKey("user_apellido")
        val USER_TELEFONO = stringPreferencesKey("user_telefono")
        val USER_DIRECCION = stringPreferencesKey("user_direccion")
        val USER_CIUDAD = stringPreferencesKey("user_ciudad")
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val userId: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_ID] ?: ""
    }

    suspend fun saveLoginState(
        isLoggedIn: Boolean,
        userId: String = "",
        email: String = "",
        nombre: String = "",
        apellido: String = "",
        telefono: String = "",
        direccion: String = "",
        ciudad: String = ""
    ) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
            if (isLoggedIn) {
                preferences[USER_ID] = userId
                preferences[USER_EMAIL] = email
                preferences[USER_NOMBRE] = nombre
                preferences[USER_APELLIDO] = apellido
                preferences[USER_TELEFONO] = telefono
                preferences[USER_DIRECCION] = direccion
                preferences[USER_CIUDAD] = ciudad
            } else {
                preferences.remove(USER_ID)
                preferences.remove(USER_EMAIL)
                preferences.remove(USER_NOMBRE)
                preferences.remove(USER_APELLIDO)
                preferences.remove(USER_TELEFONO)
                preferences.remove(USER_DIRECCION)
                preferences.remove(USER_CIUDAD)
            }
        }
    }

    suspend fun getUserData(): Map<String, String> {
        val prefs = context.dataStore.data.first()
        return mapOf(
            "id" to (prefs[USER_ID] ?: ""),
            "email" to (prefs[USER_EMAIL] ?: ""),
            "nombre" to (prefs[USER_NOMBRE] ?: ""),
            "apellido" to (prefs[USER_APELLIDO] ?: ""),
            "telefono" to (prefs[USER_TELEFONO] ?: ""),
            "direccion" to (prefs[USER_DIRECCION] ?: ""),
            "ciudad" to (prefs[USER_CIUDAD] ?: "")
        )
    }
}

