package com.example.levelup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.levelup.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    // Obtiene todos los productos y los emite como un Flow
    @Query("SELECT * FROM productos")
    fun getAllProducts(): Flow<List<Producto>>

    // Inserta una lista de productos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(productos: List<Producto>)

    // Actualiza el stock de un producto específico por su ID
    @Query("UPDATE productos SET stock = stock - :cantidad WHERE id = :productoId")
    suspend fun updateStock(productoId: Int, cantidad: Int)

}