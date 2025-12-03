package com.example.levelup

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase de aplicación personalizada, necesaria para inicializar Hilt.
 * La anotación @HiltAndroidApp genera el código necesario para la inyección de dependencias.
 */
@HiltAndroidApp
class LevelUpApplication : Application()