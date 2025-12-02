package com.example.levelup

// Asegúrate de que todas estas importaciones están presentes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.levelup.data.AppDatabase
import com.example.levelup.data.ProductosRepository
import com.example.levelup.ui.theme.LevelUpTheme
import com.example.levelup.viewmodel.PostViewModelFactory
import com.example.levelup.ui.LevelUpApp // <-- ¡ESTA ES LA CORRECCIÓN!


// 1. La clase empieza aquí
class MainActivity : ComponentActivity() {

    // 2. 'onCreate' debe estar DENTRO de la clase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // --- CONFIGURACIÓN DE LA BASE DE DATOS Y VIEWMODEL ---
        val database = AppDatabase.getDatabase(applicationContext)
        val productDao = database.productDao()
        val productosRepository = ProductosRepository(productDao)
        val postViewModelFactory = PostViewModelFactory(productosRepository)
        // --- FIN DE LA CONFIGURACIÓN ---

        setContent {
            LevelUpTheme {
                // Pasamos la Factory a nuestra app principal.
                // LevelUpApp se encargará de distribuirla a las pantallas que la necesiten.
                LevelUpApp(postViewModelFactory = postViewModelFactory)
            }
        }
    }
} // 3. La clase termina aquí
