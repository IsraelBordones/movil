package com.example.levelup.data.Remote

import com.example.levelup.data.model.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST // 👈 Importante

interface ApiService {
    @GET(value = "/posts")
    suspend fun getPosts(): List<Post>

    // 👇 ASEGÚRATE DE QUE ESTO ESTÉ AQUÍ
    @POST(value = "/posts")
    suspend fun createPost(@Body post: Post): Post
}