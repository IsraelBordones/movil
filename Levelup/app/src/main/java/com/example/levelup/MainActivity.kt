package com.example.levelup // 1. Tu paquete base (minúscula)


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
// 2. Importaciones corregidas apuntando a tus carpetas reales
import com.example.levelup.ui.screen.PostScreen
import com.example.levelup.viewmodel.PostViewModel
import com.example.levelup.ui.theme.LevelUpTheme // 3. Posible nombre de tu tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración Edge-to-Edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // 4. Aquí aplicamos el tema. Si "LevelupTheme" sale en rojo, revisa el paso extra abajo.
            LevelUpTheme {
                // Inyectamos el ViewModel
                val postViewModel: PostViewModel = viewModel()

                // Mostramos la pantalla
                PostScreen(viewModel = postViewModel)
            }
        }
    }
}