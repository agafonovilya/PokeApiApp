package ru.agafonovilya.pokeapiapp.repository

import ru.agafonovilya.pokeapiapp.model.entity.Pokemon

interface IRepository {
    suspend fun getPokemonByName(name: String): Pokemon
    suspend fun getRandomPokemon(): Pokemon
}