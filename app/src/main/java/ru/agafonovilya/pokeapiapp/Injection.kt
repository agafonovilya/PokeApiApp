package ru.agafonovilya.pokeapiapp

import ru.agafonovilya.pokeapiapp.model.retrofit.PokeApiService
import ru.agafonovilya.pokeapiapp.repository.PokeApiRepository
import ru.agafonovilya.pokeapiapp.util.imageLoader.GlideImageLoader
import ru.agafonovilya.pokeapiapp.util.imageLoader.IImageLoader

object Injection {

    /**
     * Creates an instance of [PokeApiRepository] based on the [PokeApiService]
     */
    private fun providePokeApiRepository(): PokeApiRepository {
        return PokeApiRepository(PokeApiService.create())
    }

    /**
     * Creates an instance of [GlideImageLoader]
     */
    fun provideImageLoader(): IImageLoader {
        return GlideImageLoader()
    }



}