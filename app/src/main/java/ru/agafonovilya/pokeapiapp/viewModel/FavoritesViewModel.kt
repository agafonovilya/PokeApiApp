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

class FavoritesViewModel(
    private val dbRepository: IDbRepository, private val resources: Resources
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewModelResult>(ViewModelResult.Loading)
    val uiState: StateFlow<ViewModelResult> = _uiState

    fun getPokemonList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dbRepository.getAllPokemon().let {
                    when (it) {
                        is RepoResult.Success -> {
                            _uiState.value =
                                ViewModelResult.Success(DataCode.LIST_OF_DB_POKEMON, it.value)
                        }
                        is RepoResult.Error -> {
                            _uiState.value =
                                ViewModelResult.Error(Throwable(resources.getString(R.string.error_list_pokemon_from_db)))
                        }
                    }
                }
            }
        }
    }
}