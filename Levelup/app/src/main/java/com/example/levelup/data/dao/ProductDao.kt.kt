// 1. VERIFICACIÓN DE PAQUETE: Asegúrate de que esta línea es correcta.
package com.example.levelup.data.dao

// 2. VERIFICACIÓN DE IMPORTS: Asegúrate de que solo se importen estas clases.
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.levelup.model.Producto // <-- ¡IMPORTANTE! Debe apuntar a data.model
import kotlinx.coroutines.flow.Flow

// 3. VERIFICACIÓN DE LA DECLARACIÓN: ¿Tiene la anotación @Dao? ¿Es una 'interface'?
@Dao
interface ProductDao {

    // 4. VERIFICACIÓN DE LAS FUNCIONES: Revisa que no haya errores de tipeo aquí.
    @Query("SELECT * FROM productos")
    fun getAllProducts(): Flow<List<Producto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(productos: List<Producto>)

}
