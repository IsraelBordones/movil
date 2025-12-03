package com.example.levelup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.levelup.data.AppDatabase
import com.example.levelup.data.ProductosRepository
import com.example.levelup.ui.navigation.AppNavigation
import com.example.levelup.ui.theme.LevelUpTheme
import com.example.levelup.viewmodel.PostViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- Construcción de Dependencias ---
        val database = AppDatabase.getDatabase(applicationContext)
        val productDao = database.productDao()
        val productosRepository = ProductosRepository(productDao)
        val postViewModelFactory = PostViewModelFactory(productosRepository)

        setContent {
            LevelUpTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(
                        navController = navController,
                        postViewModelFactory = postViewModelFactory
                    )
                }
            }
        }
    }
}