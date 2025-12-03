package com.example.levelup.di

import android.content.Context
import com.example.levelup.data.AppDatabase
import com.example.levelup.data.PreferencesManager
import com.example.levelup.data.ProductosRepository
import com.example.levelup.data.dao.CarritoDao
import com.example.levelup.data.dao.ProductDao
import com.example.levelup.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()

    @Provides
    fun provideCarritoDao(db: AppDatabase): CarritoDao = db.carritoDao()

    // CORREGIDO: Ahora inyectamos directamente el ProductDao
    @Provides
    @Singleton
    fun provideProductosRepository(productDao: ProductDao): ProductosRepository = ProductosRepository(productDao)

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager = PreferencesManager(context)
}
