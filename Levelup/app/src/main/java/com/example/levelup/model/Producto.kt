package com.example.levelup.model

data class Producto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String = "",
    val categoria: String,
    val disponible: Boolean = true,
    val stock: Number


)

