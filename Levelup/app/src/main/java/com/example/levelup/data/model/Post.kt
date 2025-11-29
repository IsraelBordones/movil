package com.example.levelup.data.model // 👈 ¡ESTA ES LA LÍNEA CLAVE QUE FALTABA!

data class Post(
    val userId: Int,    // ID del usuario que creo el post
    val id: Int,        // ID del post
    val title: String,  // Titulo del post
    val body: String    // Cuerpo o contenido del post
)