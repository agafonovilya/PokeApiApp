package ru.agafonovilya.pokeapiapp.viewModel

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.agafonovilya.pokeapiapp.R
import ru.agafonovilya.pokeapiapp.model.db.IDbRepository
import ru.agafonovilya.pokeapiapp.model.entity.DataCode
import ru.agafonovilya.pokeapiapp.model.entity.RepoResult
import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon
import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB
import ru.agafonovilya.pokeapiapp.model.repository.IRepository
import ru.agafonovilya.pokeapiapp.model.entity.ViewModelResult

class ByNameViewModel(
    private val repository: IRepository,
    private val dbRepository: IDbRepository,
    private val resources: Resources
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewModelResult>(ViewModelResult.Loading)
    val uiState: StateFlow<ViewModelResult> = _uiState

    private var lastPokemon: Pokemon? = null
    private var pokemonNameList: List<String>? = null

    fun getItemsForAutoCompleteTextView() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getListPokemonName().also {
                    when (it) {
                        is RepoResult.Success -> {
                            pokemonNameList = it.value
                            _uiState.value =
                                ViewModelResult.Success(DataCode.LIST_OF_POKEMON_NAME, it.value)
                        }
                        is RepoResult.Error -> {
                            _uiState.value =
                                ViewModelResult.Error(Throwable(resources.getString(R.string.error_list_pokemon_name)))
                        }
                    }
                }
            }
        }
    }

    fun getPokemonByName(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getPokemonByName(name).also {
                    when (it) {
                        is RepoResult.Success -> {
                            lastPokemon = it.value
                            _uiState.value = ViewModelResult.Success(DataCode.POKEMON, it.value)
                        }
                        is RepoResult.Error -> {
                            _uiState.value =
                                ViewModelResult.Error(Throwable(resources.getString(R.string.check_name)))
                        }
                    }
                }
            }
        }
    }

    fun savePokemon() {
        lastPokemon?.let {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    dbRepository.savePokemon(PokemonFromDB(name = it.name,
                        imageUrl = it.sprites.other.officialArtwork.front_default))
                }
            }
        }
    }

}