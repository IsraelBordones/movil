package com.example.levelup.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class PreferencesManager(context: Context) {

    private val appContext = context.applicationContext

    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_NOMBRE = stringPreferencesKey("user_nombre")
        private val USER_APELLIDO = stringPreferencesKey("user_apellido")
        private val USER_TELEFONO = stringPreferencesKey("user_telefono")
        private val USER_DIRECCION = stringPreferencesKey("user_direccion")
        private val USER_CIUDAD = stringPreferencesKey("user_ciudad")
        private val USER_ROLE = stringPreferencesKey("user_role")
    }

    val isLoggedIn: Flow<Boolean> =
        appContext.dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }

    val userId: Flow<String?> =
        appContext.dataStore.data.map { preferences ->
            preferences[USER_ID]
        }

    val userRole: Flow<String?> =
        appContext.dataStore.data.map { preferences ->
            preferences[USER_ROLE]
        }

    suspend fun saveLoginState(
        isLoggedIn: Boolean,
        userId: String,
        email: String,
        nombre: String,
        apellido: String,
        telefono: String,
        direccion: String,
        ciudad: String,
        role: String
    ) {
        appContext.dataStore.edit { prefs -> // Corregido el nombre del parámetro
            prefs[IS_LOGGED_IN] = isLoggedIn
            prefs[USER_ID] = userId
            prefs[USER_EMAIL] = email
            prefs[USER_NOMBRE] = nombre
            prefs[USER_APELLIDO] = apellido
            prefs[USER_TELEFONO] = telefono
            prefs[USER_DIRECCION] = direccion
            prefs[USER_CIUDAD] = ciudad
            prefs[USER_ROLE] = role
        }
    }

    suspend fun clearLoginState() {
        appContext.dataStore.edit {
            it.clear()
        }
    }
}