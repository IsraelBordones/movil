import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // se instancia el servicio de la API una sola vez [cite: 104]
    val api: ApiService by lazy {
        Retrofit.Builder() [cite: 107]
        .baseUrl("https://jsonplaceholder.typicode.com") // URL base de la API [cite: 109, 247]
        .addConverterFactory(GsonConverterFactory.create()) // Conversor JSON [cite: 109]
        .build()
        .create(ApiService::class.java) // Implementa la interfaz ApiService [cite: 110]
    }
}