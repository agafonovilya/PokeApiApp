package ru.agafonovilya.pokeapiapp.model.db

import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB

class RoomDbRepository(db: PokemonDatabase): IDbRepository {

    private val pokemonDao = db.pokemonDao()

    override suspend fun savePokemon(pokemonFromDB: PokemonFromDB) {
        pokemonDao.insertPokemon(pokemonFromDB)
    }

    override suspend fun getAllPokemons(): List<PokemonFromDB> {
        return pokemonDao.getAllPokemon()
    }
}