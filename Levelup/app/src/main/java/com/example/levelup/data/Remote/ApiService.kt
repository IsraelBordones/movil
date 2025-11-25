import com.example.apirest.data.model.Post
import retrofit2.http.GET

interface ApiService {
    // Define una solicitud GET al endpoint /posts [cite: 90]
    @GET(value = "/posts")
    suspend fun getPosts(): List<Post>
}