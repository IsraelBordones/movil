package com.example.levelup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.levelup.data.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :pass LIMIT 1")
    suspend fun login(email: String, pass: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUsuarioByEmail(email: String): Usuario?

    // AÑADIDO: Obtiene un usuario por su ID y lo emite como un Flow
    @Query("SELECT * FROM usuarios WHERE id = :userId")
    fun getUserById(userId: Int): Flow<Usuario?>

    // AÑADIDO: Actualiza un usuario existente
    @Update
    suspend fun updateUser(usuario: Usuario)

    // Función original, la mantenemos por si se usa en otro lugar
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(usuarios: List<Usuario>)
}