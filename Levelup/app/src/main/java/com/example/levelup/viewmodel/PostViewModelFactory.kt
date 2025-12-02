package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
// 1. CORRECCIÓN: Separar los imports
import com.example.levelup.data.ProductosRepository

class PostViewModelFactory(private val repository: ProductosRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(repository) as T
        } else { // 2. CORRECCIÓN: Añadir el 'else' que faltaba
            // Si no, lanza un error porque no sabe cómo construir otros ViewModels
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
