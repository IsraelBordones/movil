package com.example.levelup.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelup.data.AppDatabase

class CarritoViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarritoViewModel::class.java)) {
            val database = AppDatabase.getDatabase(context)
            val carritoDao = database.carritoDao()
            val productDao = database.productDao()
            @Suppress("UNCHECKED_CAST")
            return CarritoViewModel(carritoDao, productDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}