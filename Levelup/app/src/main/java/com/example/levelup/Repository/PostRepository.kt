import com.example.apirest.data.model.Post
import com.example.apirest.data.remote.RetrofitInstance

class PostRepository {
    // Función que obtiene los posts desde la API [cite: 117]
    suspend fun getPosts(): List<Post> { [cite: 119]
        return RetrofitInstance.api.getPosts() [cite: 120]
    }
}