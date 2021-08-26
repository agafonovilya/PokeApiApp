package ru.agafonovilya.pokeapiapp.model.repository

import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon
import ru.agafonovilya.pokeapiapp.model.retrofit.PokeApiService
import kotlin.random.Random

class PokeApiRepository(private val apiService: PokeApiService): IRepository {

    override suspend fun getPokemonByName(name: String): Pokemon {
        return apiService.getPokemonByName(name)
    }

    override suspend fun getRandomPokemon(): Pokemon {
        val pokemonList = apiService.getPokemonList()
        val random = Random.nextInt(pokemonList.count)
        return apiService.getPokemonById(random)
    }

}