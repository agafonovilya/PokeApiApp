package ru.agafonovilya.pokeapiapp.model.entity

import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon

sealed class Result {
    object Loading : Result()
    data class Success(val pokemon: Pokemon): Result()
    data class Error(val error: Throwable): Result()
}