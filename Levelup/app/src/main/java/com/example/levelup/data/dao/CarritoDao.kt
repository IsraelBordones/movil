package com.example.levelup.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.levelup.data.model.CarritoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {

    // Obtiene todos los items del carrito y los emite como un Flow
    @Query("SELECT * FROM carrito_items")
    fun getCarritoItems(): Flow<List<CarritoItem>>

    // Busca un item específico por el ID del producto original
    @Query("SELECT * FROM carrito_items WHERE productoId = :productoId LIMIT 1")
    suspend fun getItemByProductoId(productoId: Int): CarritoItem?

    // Inserta un nuevo item. Si ya existe, lo reemplaza.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CarritoItem)

    // Actualiza un item existente (ej. para cambiar la cantidad)
    @Update
    suspend fun updateItem(item: CarritoItem)

    // Elimina un item del carrito
    @Delete
    suspend fun deleteItem(item: CarritoItem)

    // Limpia todos los items del carrito (ej. después de una compra)
    @Query("DELETE FROM carrito_items")
    suspend fun clearCarrito()
}