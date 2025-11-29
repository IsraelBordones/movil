package com.example.levelup.Repository

import com.example.levelup.data.model.Post
import com.example.levelup.data.Remote.RetrofitInstance

class PostRepository {
    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }

    suspend fun createPost(post: Post): Post {
        return RetrofitInstance.api.createPost(post)
    }
}