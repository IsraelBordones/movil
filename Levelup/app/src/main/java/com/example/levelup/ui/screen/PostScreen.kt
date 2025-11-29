package com.example.levelup.ui.screen // ⚠️ "screen" en singular


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState // Importante para observar el StateFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.levelup.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(viewModel: PostViewModel) {
    // El composable se recompone cada vez que postList cambia
    val posts = viewModel.postList.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar (title = { Text(text = "Listado de Posts") })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp)
            ) {
                items(items = posts) { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation (defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding (all = 10.dp)) {
                            Text(
                                text = "Titulo: ${post.title}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer (modifier = Modifier.height(4.dp))
                            Text(
                                text = post.body,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}