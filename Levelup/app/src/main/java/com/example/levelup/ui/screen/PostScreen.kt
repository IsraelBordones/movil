import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apirest.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(viewModel: PostViewModel) {
    // Observamos el flujo de datos del ViewModel
    val posts = viewModel.postList.collectAsState().value [cite: 178]

    // Scaffold con TopAppBar [cite: 179]
    Scaffold(
        topBar = {
            TopAppBar (title = { Text(text = "Listado de Posts") }) [cite: 183, 184]
        }
    ) { innerPadding ->
        // Aplicamos el padding de seguridad del sistema [cite: 186]
        Box(modifier = Modifier
            .fillMaxSize() [cite: 189]
        .padding(innerPadding) // Uso de edge-to-edge [cite: 191]
        ) {
        // Lista de publicaciones [cite: 192]
        LazyColumn(
            modifier = Modifier
                .fillMaxSize() [cite: 199]
        .padding(all = 16.dp) // Espaciado interior [cite: 200]
        ) {
        items(items = posts) { post -> [cite: 201]
            Card(
                modifier = Modifier
                    .fillMaxWidth() [cite: 206]
            .padding(vertical = 8.dp), [cite: 207]
            elevation = CardDefaults.cardElevation (defaultElevation = 4.dp) [cite: 208]
            ) {
                Column(modifier = Modifier.padding (all = 10.dp)) { [cite: 209, 211]
                    Text(
                        text = "Titulo: ${post.title}", [cite: 212]
                    style = MaterialTheme.typography.titleMedium [cite: 213]
                    )
                    Spacer (modifier = Modifier.height(4.dp)) [cite: 214]
                    Text(
                        text = post.body, [cite: 216]
                    style = MaterialTheme.typography.bodyMedium [cite: 217]
                    )
                }
            }
        }
    }
    }
    }
}