package ru.agafonovilya.pokeapiapp.model.repository

import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon
import ru.agafonovilya.pokeapiapp.model.entity.api.PokemonList
import ru.agafonovilya.pokeapiapp.model.retrofit.PokeApiService
import kotlin.random.Random

class PokeApiRepository(private val apiService: PokeApiService) : IRepository {

    override suspend fun getPokemonByName(name: String): Pokemon {
        return apiService.getPokemonByName(name)
    }

    override suspend fun getRandomPokemon(): Pokemon {
        val pokemonList = apiService.getPokemonCount()
        val random = Random.nextInt(pokemonList.count)
        return apiService.getPokemonById(random)
    }

    override suspend fun getListPokemonName(): List<String>? {
        val pokemonList: PokemonList = apiService.getPokemonList()
        val pokemonNameList = mutableListOf<String>()

        return if (!pokemonList.results.isNullOrEmpty()) {
            for (i in 0 until (pokemonList.count)) {
                pokemonList.results[i].name?.let {
                    pokemonNameList.add(it)
                }
            }
            pokemonNameList
        } else {
            null
        }
    }
}