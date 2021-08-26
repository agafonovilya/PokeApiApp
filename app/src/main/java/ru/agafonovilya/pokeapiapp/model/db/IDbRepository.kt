package ru.agafonovilya.pokeapiapp.model.db

import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB

interface IDbRepository {
    suspend fun savePokemon(pokemonFromDB: PokemonFromDB)
    suspend fun getAllPokemons(): List<PokemonFromDB>
}