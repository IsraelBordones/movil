package com.example.levelup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.levelup.data.model.Usuario // <-- ¡CORRECCIÓN FINAL!

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(usuarios: List<Usuario>)
}
