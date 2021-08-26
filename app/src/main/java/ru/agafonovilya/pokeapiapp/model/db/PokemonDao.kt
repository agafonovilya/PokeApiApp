package ru.agafonovilya.pokeapiapp.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonFromDB: PokemonFromDB)

    @Query("SELECT * FROM PokemonFromDB")
    suspend fun getAllPokemon(): List<PokemonFromDB>
}