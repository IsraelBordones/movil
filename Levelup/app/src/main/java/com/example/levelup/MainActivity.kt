import android.os.Bundle [cite: 223]
import androidx.activity.ComponentActivity [cite: 224]
import androidx.activity.compose.setContent [cite: 225]
import androidx.core.view.WindowCompat // Necesario para edge-to-edge [cite: 227]
import androidx.lifecycle.viewmodel.compose.viewModel [cite: 228]
import com.example.apirest.ui.screens.PostScreen [cite: 229]
import com.example.apirest.ui.theme.ApiRestTheme [cite: 230]
import com.example.apirest.viewmodel.PostViewModel // Importar la clase

class MainActivity: ComponentActivity() { [cite: 232]
    override fun onCreate(savedInstanceState: Bundle?) { [cite: 233]
        super.onCreate(savedInstanceState) [cite: 234]

        // Permite que la app dibuje contenido debajo de las barras del sistema [cite: 235]
        WindowCompat.setDecorFitsSystemWindows(window, false) [cite: 236]

        setContent { // Aquí inicia Jetpack Compose [cite: 237, 238]
            ApiRestTheme { // Aplicamos el tema Material 3 [cite: 239]
                // Inyectamos el ViewModel [cite: 240]
                val postViewModel: PostViewModel = viewModel() [cite: 241, 243]

                // Llamamos a la pantalla principal y pasamos el ViewModel [cite: 242]
                PostScreen(viewModel = postViewModel)
            }
        }
    }
}