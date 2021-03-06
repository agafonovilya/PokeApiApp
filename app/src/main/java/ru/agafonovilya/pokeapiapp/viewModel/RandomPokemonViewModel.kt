package ru.agafonovilya.pokeapiapp.viewModel

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.agafonovilya.pokeapiapp.R
import ru.agafonovilya.pokeapiapp.model.db.IDbRepository
import ru.agafonovilya.pokeapiapp.model.entity.DataCode
import ru.agafonovilya.pokeapiapp.model.entity.RepoResult
import ru.agafonovilya.pokeapiapp.model.entity.ViewModelResult
import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon
import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB
import ru.agafonovilya.pokeapiapp.model.repository.IRepository

class RandomPokemonViewModel(
    private val repository: IRepository,
    private val dbRepository: IDbRepository,
    private val resources: Resources
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewModelResult>(ViewModelResult.Loading)
    val uiState: StateFlow<ViewModelResult> = _uiState

    private var lastPokemon: Pokemon? = null

    fun getRandomPokemon() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getRandomPokemon().also {
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