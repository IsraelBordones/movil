package com.example.levelup.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelup.data.AppDatabase
import com.example.levelup.data.PreferencesManager

class AuthViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            val userDao = AppDatabase.getDatabase(context).userDao()
            val preferencesManager = PreferencesManager(context)
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(userDao, preferencesManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}