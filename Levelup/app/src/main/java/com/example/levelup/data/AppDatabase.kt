package com.example.levelup.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.levelup.data.dao.CarritoDao
import com.example.levelup.data.dao.ProductDao
import com.example.levelup.data.dao.UserDao
import com.example.levelup.data.model.CarritoItem
import com.example.levelup.data.model.Producto
import com.example.levelup.data.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Producto::class, Usuario::class, CarritoItem::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun carritoDao(): CarritoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "levelup_database"
                )
                    .addCallback(AppDatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.productDao(), database.userDao())
                }
            }
        }

        suspend fun populateDatabase(productDao: ProductDao, userDao: UserDao) {
            val initialUsers = listOf(
                Usuario(nombreUsuario = "israel_dev", email = "israel@example.com", password = "123456", role = "CLIENTE"),
                // AÑADIDO: Usuario Administrador
                Usuario(nombreUsuario = "admin", email = "admin@levelup.com", password = "admin123", role = "ADMIN")
            )
            userDao.insertAll(initialUsers)

            val initialProducts = listOf(
                Producto(
                    id = 1, nombre = "Elden Ring", descripcion = "Juego de rol de acción...", precio = 59.99, categoria = "RPG", stock = 50,
                    imagen = "https://i.imgur.com/PqzEmqe.png"
                ),
                Producto(
                    id = 2, nombre = "Cyberpunk 2077", descripcion = "Aventura de acción y rol...", precio = 49.99, categoria = "RPG", stock = 30,
                    imagen = "https://i.imgur.com/BzTMKDW.png"
                ),
                Producto(
                    id = 3, nombre = "Baldur's Gate 3", descripcion = "El aclamado RPG...", precio = 59.99, categoria = "RPG", stock = 100,
                    imagen = "https://i.imgur.com/NaxaUtl.png"
                ),
                Producto(
                    id = 4, nombre = "Stardew Valley", descripcion = "Simulador de granja...", precio = 14.99, categoria = "Simulación", stock = 200,
                    imagen = "https://i.imgur.com/zZ8mJdY.png"
                )
            )
            productDao.insertAll(initialProducts)
        }
    }
}