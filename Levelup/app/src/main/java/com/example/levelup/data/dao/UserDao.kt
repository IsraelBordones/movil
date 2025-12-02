package com.example.levelup.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.levelup.model.Usuario

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(usuarios: List<Usuario>)
}
