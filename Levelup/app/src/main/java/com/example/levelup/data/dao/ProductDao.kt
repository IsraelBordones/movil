package com.example.levelup.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.levelup.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM productos")
    fun getAllProducts(): Flow<List<Producto>>

    // AÑADIDO: Obtiene un solo producto por su ID. No necesita ser un Flow si solo lo leemos una vez.
    @Query("SELECT * FROM productos WHERE id = :productId LIMIT 1")
    suspend fun getProductById(productId: Int): Producto?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(productos: List<Producto>)

    @Query("UPDATE productos SET stock = stock - :cantidad WHERE id = :productoId")
    suspend fun updateStock(productoId: Int, cantidad: Int)

    @Update
    suspend fun updateProduct(product: Producto)

    @Delete
    suspend fun deleteProduct(product: Producto)
}