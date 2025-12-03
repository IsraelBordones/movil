package com.example.levelup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.levelup.data.model.Usuario

@Dao
interface UserDao {

    // 1. Para el Registro: Inserta un solo usuario
    // Si el usuario ya existe (por ID), lo reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario)

    // 2. Para el Login: Busca un usuario que tenga ESE email y ESA contraseña
    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :pass LIMIT 1")
    suspend fun login(email: String, pass: String): Usuario?

    // 3. Validación: Revisa si el correo ya existe antes de dejarlo registrarse
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUsuarioByEmail(email: String): Usuario?

    // (Opcional) Dejamos tu función original por si acaso la usan en otro lado
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(usuarios: List<Usuario>)
}