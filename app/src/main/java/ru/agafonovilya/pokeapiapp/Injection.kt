package ru.agafonovilya.pokeapiapp

import android.content.Context
import ru.agafonovilya.pokeapiapp.model.db.IDbRepository
import ru.agafonovilya.pokeapiapp.model.db.PokemonDatabase
import ru.agafonovilya.pokeapiapp.model.db.RoomDbRepository
import ru.agafonovilya.pokeapiapp.model.repository.IRepository
import ru.agafonovilya.pokeapiapp.model.retrofit.PokeApiService
import ru.agafonovilya.pokeapiapp.model.repository.PokeApiRepository
import ru.agafonovilya.pokeapiapp.util.imageLoader.GlideImageLoader
import ru.agafonovilya.pokeapiapp.util.imageLoader.IImageLoader

object Injection {

    /**
     * Creates an instance of [PokeApiRepository] based on the [PokeApiService]
     */
    private fun providePokeApiRepository(): IRepository {
        return PokeApiRepository(PokeApiService.create())
    }

    /**
     * Creates an instance of [PokemonDatabase]
     */
    private fun provideDatabaseRepository(context: Context): IDbRepository {
        return RoomDbRepository(PokemonDatabase.getInstance(context))
    }

    /**
     * Creates an instance of [GlideImageLoader]
     */
    fun provideImageLoader(): IImageLoader {
        return GlideImageLoader()
    }



}