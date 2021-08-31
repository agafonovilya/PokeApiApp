package ru.agafonovilya.pokeapiapp.model.entity

sealed class ViewModelResult {
    object Loading : ViewModelResult()
    data class Success<out T>(val dataCode: DataCode, val data: T): ViewModelResult()
    data class Error(val error: Throwable): ViewModelResult()
}

enum class DataCode{
    POKEMON,
    LIST_OF_POKEMON_NAME,
    LIST_OF_DB_POKEMON
}