package com.example.levelup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup.data.model.Post
import com.example.levelup.Repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val repository = PostRepository()

    private val _postList = MutableStateFlow<List<Post>>(emptyList())
    val postList: StateFlow<List<Post>> = _postList

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                _postList.value = repository.getPosts()
            } catch (e: Exception) {
                println("Error: ${e.localizedMessage}")
            }
        }
    }

    // 👇 NUEVA FUNCIÓN para llamar desde la UI
    fun crearNuevoPost(titulo: String, contenido: String) {
        viewModelScope.launch {
            try {
                // Creamos el objeto Post (el ID 0 suele indicar "nuevo" en muchas APIs)
                val nuevoPost = Post(userId = 1, id = 0, title = titulo, body = contenido)

                // Llamamos al repositorio
                val respuesta = repository.createPost(nuevoPost)

                println("Post creado con éxito: ${respuesta.title}")

                // Opcional: Podrías volver a llamar a fetchPosts() aquí para actualizar la lista
            } catch (e: Exception) {
                println("Error al crear post: ${e.localizedMessage}")
            }
        }
    }
}