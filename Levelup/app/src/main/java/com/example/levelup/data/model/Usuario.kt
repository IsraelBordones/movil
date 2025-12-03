package com.example.levelup.data.model // <-- ¡CORRECCIÓN CRÍTICA!

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreUsuario: String = "",
    val password: String = "",
    val apellido: String = "",
    val email: String = "",
    val telefono: String = "",
    val direccion: String = "",
    val ciudad: String = ""
)
