package com.example.levelup.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa un item individual en el carrito de compras.
 * Se usa como una tabla en la base de datos Room.
 */
@Entity(tableName = "carrito_items")
data class CarritoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productoId: Int,       // ID del producto añadido
    val nombre: String,          // Guardamos el nombre para mostrarlo fácilmente
    val precio: Double,          // Y el precio
    var cantidad: Int,         // La cantidad que el usuario quiere comprar
    // val userId: String,     // Opcional: Si quisiéramos carritos persistentes por usuario
)
