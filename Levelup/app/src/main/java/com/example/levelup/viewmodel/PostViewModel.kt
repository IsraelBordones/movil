import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apirest.data.model.Post
import com.example.apirest.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel: ViewModel() {
    private val repository = PostRepository() [cite: 136, 137]

    // Flujo mutable que contiene la lista de posts [cite: 138]
    private val _postList = MutableStateFlow<List<Post>>(emptyList()) [cite: 140]

    // Flujo público de solo lectura [cite: 141]
    val postList: StateFlow<List<Post>> = _postList [cite: 143]

    init { // Se llama automáticamente al iniciar [cite: 144, 145]
        fetchPosts() [cite: 147]
    }

    // Función que obtiene los datos en segundo plano [cite: 148]
    private fun fetchPosts() {
        viewModelScope.launch { [cite: 151]
            try { [cite: 152]
                _postList.value = repository.getPosts() [cite: 153]
            } catch (e: Exception) { [cite: 154]
                println("Error al obtener datos: ${e.localizedMessage}") [cite: 155]
            }
        }
    }
}