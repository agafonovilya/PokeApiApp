package ru.agafonovilya.pokeapiapp.model.repository

import ru.agafonovilya.pokeapiapp.model.entity.RepoResult
import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon

interface IRepository {
    suspend fun getPokemonByName(name: String): RepoResult<Pokemon>
    suspend fun getRandomPokemon(): RepoResult<Pokemon>
    suspend fun getListPokemonName(): RepoResult<List<String>>
}