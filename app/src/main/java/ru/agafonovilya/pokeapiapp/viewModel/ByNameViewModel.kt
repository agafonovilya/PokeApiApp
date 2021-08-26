package ru.agafonovilya.pokeapiapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.agafonovilya.pokeapiapp.model.db.IDbRepository
import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon
import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB
import ru.agafonovilya.pokeapiapp.model.repository.IRepository

class ByNameViewModel(
    private val repository: IRepository,
    private val dbRepository: IDbRepository,
) : ViewModel() {

    private var lastPokemon: Pokemon? = null

    suspend fun getPokemonByName(name: String): Pokemon {
        return repository.getPokemonByName(name).also { lastPokemon = it }
    }

    suspend fun getRandomPokemon(): Pokemon {
        return repository.getRandomPokemon().also { lastPokemon = it }
    }

    fun savePokemon() {
        lastPokemon?.let {
            viewModelScope.launch {
                dbRepository.savePokemon(PokemonFromDB(name = it.name))
            }
        }
    }
}

class ByNameViewModelFactory(
    private val repository: IRepository, private val dbRepository: IDbRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ByNameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ByNameViewModel(repository, dbRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}