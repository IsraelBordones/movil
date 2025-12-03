package com.example.levelup.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelup.data.AppDatabase

class CatalogoViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatalogoViewModel::class.java)) {
            val productDao = AppDatabase.getDatabase(context).productDao()
            @Suppress("UNCHECKED_CAST")
            return CatalogoViewModel(productDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}