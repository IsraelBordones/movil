package com.example.levelup.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.levelup.data.dao.ProductDao
import com.example.levelup.data.dao.UserDao
import com.example.levelup.data.model.Producto
import com.example.levelup.data.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ⚠️ CAMBIO 1: Subimos la version a 2
@Database(entities = [Producto::class, Usuario::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Si la base de datos cambia de estructura, .fallbackToDestructiveMigration() evita que la app crashee
                // borrando la base antigua y creando una nueva.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "levelup_database"
                )
                    .addCallback(AppDatabaseCallback())
                    .fallbackToDestructiveMigration() // Agregamos esto por seguridad al cambiar versiones
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
            // ⚠️ CAMBIO 2: Le ponemos password al usuario inicial para poder probar el Login
            val initialUsers = listOf(
                Usuario(
                    nombreUsuario = "israel_dev",
                    email = "israel@example.com",
                    password = "123456" // ¡Contraseña necesaria!
                )
            )
            userDao.insertAll(initialUsers)

            val initialProducts = listOf(
                Producto(nombre = "Elden Ring", descripcion = "Juego de rol de acción...", precio = 59.99, categoria = "RPG", stock = 50),
                Producto(nombre = "Cyberpunk 2077", descripcion = "Aventura de acción y rol...", precio = 49.99, categoria = "RPG", stock = 30),
                Producto(nombre = "Baldur's Gate 3", descripcion = "El aclamado RPG...", precio = 59.99, categoria = "RPG", stock = 100),
                Producto(nombre = "Stardew Valley", descripcion = "Simulador de granja...", precio = 14.99, categoria = "Simulación", stock = 200)
            )
            productDao.insertAll(initialProducts)
        }
    }
}