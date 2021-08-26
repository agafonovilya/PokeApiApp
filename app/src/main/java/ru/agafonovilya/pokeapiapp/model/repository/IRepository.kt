package ru.agafonovilya.pokeapiapp.model.repository

import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon

interface IRepository {
    suspend fun getPokemonByName(name: String): Pokemon
    suspend fun getRandomPokemon(): Pokemon
}