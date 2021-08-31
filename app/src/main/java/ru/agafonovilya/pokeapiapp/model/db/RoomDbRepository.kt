package ru.agafonovilya.pokeapiapp.model.db

import ru.agafonovilya.pokeapiapp.model.entity.RepoResult
import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB

class RoomDbRepository(db: PokemonDatabase): IDbRepository {

    private val pokemonDao = db.pokemonDao()

    override suspend fun savePokemon(pokemonFromDB: PokemonFromDB) {
        pokemonDao.insertPokemon(pokemonFromDB)
    }

    override suspend fun getAllPokemon(): RepoResult<List<PokemonFromDB>> {
        return try {
            val pokemonList = pokemonDao.getAllPokemon()
            RepoResult.Success(pokemonList)
        } catch (e: Exception) {
            RepoResult.Error(e)
        }
    }
}