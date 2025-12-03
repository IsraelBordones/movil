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

@Database(entities = [Producto::class, Usuario::class, CarritoItem::class], version = 5, exportSchema = false)
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
                    imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202110/2000/aG3I2Lp4bS02S0f2n1vGjY28.png"
                ),
                Producto(
                    id = 2, nombre = "Cyberpunk 2077", descripcion = "Aventura de acción y rol...", precio = 49.99, categoria = "RPG", stock = 30,
                    imagen = "https://image.api.playstation.com/vulcan/img/rnd/202107/0812/65M2A0sA1uFwGish2yMKs3b4.png"
                ),
                Producto(
                    id = 3, nombre = "Baldur's Gate 3", descripcion = "El aclamado RPG...", precio = 59.99, categoria = "RPG", stock = 100,
                    imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202302/2321/306b7a73132a76a2732a365319999a385417a26248e3fb76.png"
                ),
                Producto(
                    id = 4, nombre = "Stardew Valley", descripcion = "Simulador de granja...", precio = 14.99, categoria = "Simulación", stock = 200,
                    imagen = "https://image.api.playstation.com/vulcan/img/rnd/202010/2614/jSoF5pc2A5aDA2aI5y2o3j2e.png"
                )
            )
            productDao.insertAll(initialProducts)
        }
    }
}