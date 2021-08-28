package ru.agafonovilya.pokeapiapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.agafonovilya.pokeapiapp.model.db.IDbRepository
import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB

class FavoritesViewModel(private val dbRepository: IDbRepository) : ViewModel() {

    suspend fun getPokemonList(): List<PokemonFromDB> {
        return dbRepository.getAllPokemons()
    }
}

class FavoritesViewModelFactory(private val dbRepository: IDbRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(dbRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}