package ru.agafonovilya.pokeapiapp.model.repository

import retrofit2.HttpException
import ru.agafonovilya.pokeapiapp.model.entity.RepoResult
import ru.agafonovilya.pokeapiapp.model.entity.api.*
import ru.agafonovilya.pokeapiapp.model.retrofit.PokeApiService
import kotlin.random.Random

class PokeApiRepository(private val apiService: PokeApiService) : IRepository {

    override suspend fun getPokemonByName(name: String): RepoResult<Pokemon> {
        return try {
            val pokemon = apiService.getPokemonByName(name)
            RepoResult.Success(pokemon)
        } catch (e: HttpException) {
            RepoResult.Error(RuntimeException(e.message()))
        } catch (t: Throwable) {
            RepoResult.Error(t)
        }
    }

    override suspend fun getRandomPokemon(): RepoResult<Pokemon> {
        return try {
            val pokemonList = apiService.getPokemonCount()
            val random = Random.nextInt(pokemonList.count)
            val pokemon = apiService.getPokemonById(random)
            RepoResult.Success(pokemon)
        } catch (e: HttpException) {
            RepoResult.Error(RuntimeException(e.message()))
        } catch (t: Throwable) {
            RepoResult.Error(t)
        }
    }

    override suspend fun getListPokemonName(): RepoResult<List<String>> {
        return try {
            val pokemonList: PokemonList = apiService.getPokemonList()
            val pokemonNameList = mutableListOf<String>()
            for (i in 0 until (pokemonList.count)) {
                pokemonList.results[i].name?.let {
                    pokemonNameList.add(it)
                }
            }
            RepoResult.Success(pokemonNameList)
        } catch (e: HttpException) {
            RepoResult.Error(RuntimeException(e.message()))
        } catch (t: Throwable) {
            RepoResult.Error(t)
        }
    }

}