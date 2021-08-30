package ru.agafonovilya.pokeapiapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.agafonovilya.pokeapiapp.model.db.IDbRepository
import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon
import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB
import ru.agafonovilya.pokeapiapp.model.repository.IRepository
import ru.agafonovilya.pokeapiapp.model.entity.Result

class ByNameViewModel(
    private val repository: IRepository,
    private val dbRepository: IDbRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<Result>(Result.Loading)
    val uiState: StateFlow<Result> = _uiState

    private var lastPokemon: Pokemon? = null
    private var pokemonNameList: List<String>? = null

    val itemsForAutoCompleteTextView: Flow<List<String>?> = flow {
        pokemonNameList = repository.getListPokemonName()
        emit(pokemonNameList)
    }

    fun getPokemonByName(name: String) {
        viewModelScope.launch {
            repository.getPokemonByName(name).also {
                lastPokemon = it
                _uiState.value = Result.Success(it)
            }
        }
    }

    fun savePokemon() {
        lastPokemon?.let {
            viewModelScope.launch {
                dbRepository.savePokemon(PokemonFromDB(name = it.name,
                    imageUrl = it.sprites.other.officialArtwork.front_default))
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