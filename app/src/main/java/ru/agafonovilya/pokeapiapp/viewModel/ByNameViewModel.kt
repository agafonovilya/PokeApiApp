package ru.agafonovilya.pokeapiapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.agafonovilya.pokeapiapp.model.entity.Pokemon
import ru.agafonovilya.pokeapiapp.repository.IRepository

class ByNameViewModel(private val repository: IRepository) : ViewModel() {

    suspend fun getPokemonByName(name: String) : Pokemon {
        return repository.getPokemonByName(name)
    }

    suspend fun getRandomPokemon() : Pokemon {
        return repository.getRandomPokemon()
    }
}

class ByNameViewModelFactory(private val repository: IRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ByNameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ByNameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}