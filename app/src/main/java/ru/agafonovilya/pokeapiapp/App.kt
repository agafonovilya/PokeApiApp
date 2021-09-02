package ru.agafonovilya.pokeapiapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.agafonovilya.pokeapiapp.di.application
import ru.agafonovilya.pokeapiapp.di.byNameScreen
import ru.agafonovilya.pokeapiapp.di.favoritesScreen
import ru.agafonovilya.pokeapiapp.di.randomScreen

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(application, byNameScreen, randomScreen, favoritesScreen))
        }
    }
}