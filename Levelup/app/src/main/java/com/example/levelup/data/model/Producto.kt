package com.example.levelup.data.model // <-- ¡CORRECCIÓN CRÍTICA!

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,
    val imagen: String = "",
    val categoria: String = "",
    val disponible: Boolean = true,
    val stock: Int = 0
)
