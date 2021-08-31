package ru.agafonovilya.pokeapiapp.model.entity

sealed class RepoResult <out T> {
    data class Success<T>(val value: T): RepoResult<T>()
    data class Error(val throwable: Throwable): RepoResult<Nothing>()
}